package homework.soft.activity.task;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import homework.soft.activity.entity.dto.ActivityAiReviewCreateParam;
import homework.soft.activity.entity.po.ActivityAiReview;
import homework.soft.activity.entity.po.Comment;
import homework.soft.activity.service.ActivityAiReviewService;
import homework.soft.activity.service.CommentService;
import homework.soft.activity.service.ActivityService;
import homework.soft.activity.util.HttpRequestUtils;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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

import static java.lang.Thread.sleep;

@Component
public class AiTask {

    private static final Logger log = LoggerFactory.getLogger(AiTask.class);

    @Autowired
    private CommentService commentService;
    @Autowired
    private ActivityAiReviewService activityAiReviewService;
    @Autowired
    private ActivityService activityService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String API_KEY = "zMlvrb3EOANUsotZ7p0FzD2C";
    private static final String SECRET_KEY = "x0aN5kZ5nT3HjREeMWXM5P0YY06UTRFa";
    //    一个月后失效2025-01-01
    private static final String ACCESS_TOKEN = "24.eb417cb37ffa093ff69f8fec8ef6bf82.2592000.1735657327.282335-116474815";

    @Scheduled(fixedRate = 1800000) // 每半小时执行一次，将数据写入缓存中
    public void cacheComments() {
        log.info("定时任务开始执行");
        try {
            List<Comment> comments = commentService.list();
            log.info("获取到 {} 条评论", comments.size());
            
            Map<Integer, List<Comment>> groupedComments = comments.stream()
                    .collect(Collectors.groupingBy(Comment::getActivityId));

            groupedComments.forEach((activityId, commentList) -> {
                // 首先检查活动是否存在
                if (activityService.getById(activityId) == null) {
                    log.warn("活动ID {} 不存在，跳过处理", activityId);
                    return;
                }
                
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
                System.out.println(activityAiReview);
                ActivityAiReviewCreateParam createParam = new ActivityAiReviewCreateParam();
                BeanUtils.copyProperties(activityAiReview, createParam);
                createParam.setActivityId(activityAiReview.getActivityId());
//                从数据库中删除原有的数据
                activityAiReviewService.deleteById(activityId);
//                插入新的数据
                activityAiReviewService.insertEntry(activityAiReview);

                // 将 ActivityAiReview 对象转换为 Map
                Map<String, Object> reviewMap = new HashMap<>();
                reviewMap.put("activityId", activityAiReview.getActivityId());
                reviewMap.put("aiAnalysis", activityAiReview.getAiAnalysis());
                reviewMap.put("goodNum", activityAiReview.getGoodNum());
                reviewMap.put("mediumNum", activityAiReview.getMediumNum());
                reviewMap.put("poorNum", activityAiReview.getPoorNum());
                reviewMap.put("averageScore", activityAiReview.getAverageScore());

                // 修改缓存key的格式，与Service层保持一致
                String cacheKey = "activityAiReview:" + activityId;

                // 使用 Hash 结构存储，与 Service 层保持一致
                redisTemplate.opsForHash().putAll(cacheKey, reviewMap);
                // 设置过期时间
                redisTemplate.expire(cacheKey, 1800, TimeUnit.SECONDS);
            });
        } catch (Exception e) {
            log.error("定时任务执行异常", e);
        }
        log.info("定时任务执行完成");
    }
}