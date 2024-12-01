import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import homework.soft.activity.entity.dto.ActivityAiReviewCreateParam;
import homework.soft.activity.entity.po.ActivityAiReview;
import homework.soft.activity.entity.po.Comment;
import homework.soft.activity.service.ActivityAiReviewService;
import homework.soft.activity.service.CommentService;
import homework.soft.activity.util.HttpRequestUtils;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class AiTask {

    @Autowired
    private CommentService commentService;
    @Autowired
    private ActivityAiReviewService activityAiReviewService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String API_KEY = "zMlvrb3EOANUsotZ7p0FzD2C";
    private static final String SECRET_KEY = "x0aN5kZ5nT3HjREeMWXM5P0YY06UTRFa";
    //    一个月后失效2025-01-01
    private static final String ACCESS_TOKEN = "24.eb417cb37ffa093ff69f8fec8ef6bf82.2592000.1735657327.282335-116474815";

    @Scheduled(fixedRate = 1800000) // 每半小时执行一次，将数据写入缓存中
    public void cacheComments() {
        List<Comment> comments = commentService.list();
        Map<Integer, List<Comment>> groupedComments = comments.stream()
                .collect(Collectors.groupingBy(Comment::getActivityId));

        groupedComments.forEach((activityId, commentList) -> {
            ActivityAiReview activityAiReview = new ActivityAiReview();
            // 统计评论中rating > 3, rating == 3, rating < 3的个数
            long countGreaterThan3 = commentList.stream().filter(comment -> comment.getRating() > 3).count();
            long countEqualTo3 = commentList.stream().filter(comment -> comment.getRating() == 3).count();
            long countLessThan3 = commentList.stream().filter(comment -> comment.getRating() < 3).count();

            // 计算平均分
            double averageRating = commentList.stream()
                    .mapToInt(Comment::getRating)
                    .average()
                    .orElse(0.0);

            // 拼接活动评论内容
            String concatenatedContent = commentList.stream()
                    .map(Comment::getContent)
                    .collect(Collectors.joining(" "));

// 构建请求URL
            String apiUrl = "https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/ernie-3.5-128k-preview";
            URI uri = UriComponentsBuilder.fromHttpUrl(apiUrl)
                    .queryParam("access_token", ACCESS_TOKEN)
                    .build()
                    .toUri();

// 设置请求头
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");

// 构建请求体
            String requestBody = "{\"messages\": [{\"role\": \"user\", \"content\": \"" + "你是一个聪明的ai，请你从这个活动评论中提炼要点,不需要分段。" + concatenatedContent + "\"}]}";

// 封装参数
            HttpRequestUtils.Params params = HttpRequestUtils.Params.builder()
                    .url(uri.toString())
                    .contentType("application/json")
                    .dataEntity(new StringEntity(requestBody, ContentType.APPLICATION_JSON))
                    .headers(List.of(new BasicHeader("Content-Type", "application/json")))
                    .build();
// 发送请求
            try {
                HttpResponse response = HttpRequestUtils.request(HttpMethod.POST, params);
                String responseBody = new String(response.getEntity().getContent().readAllBytes(), StandardCharsets.UTF_8);

                // 解析 JSON 响应
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(responseBody);
                String result = jsonNode.get("result").asText();
                activityAiReview.setAiAnalysis(result);
            } catch (IOException e) {
                System.out.println("ai分析调用异常");
            }
            activityAiReview.setActivityId(activityId);
            activityAiReview.setGoodNum((int) countGreaterThan3);
            activityAiReview.setMediumNum((int) countEqualTo3);
            activityAiReview.setPoorNum((int) countLessThan3);
            activityAiReview.setAverageScore(averageRating);
//            写回数据库
            activityAiReviewService.insert((ActivityAiReviewCreateParam) activityAiReview);

            // 将 ActivityAiReview 对象转换为 Map
            Map<String, Object> reviewMap = new HashMap<>();
            reviewMap.put("activityId", activityAiReview.getActivityId());
            reviewMap.put("aiAnalysis", activityAiReview.getAiAnalysis());
            reviewMap.put("goodNum", activityAiReview.getGoodNum());
            reviewMap.put("mediumNum", activityAiReview.getMediumNum());
            reviewMap.put("poorNum", activityAiReview.getPoorNum());
            reviewMap.put("averageScore", activityAiReview.getAverageScore());

            // 将拼接的内容存储到Redis
            redisTemplate.opsForValue().set("comments:" + activityId, reviewMap, 1800, TimeUnit.SECONDS);
        });
    }
}