package homework.soft.activity.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Description
 *
 * @author 30597
 * @since 2024-12-21 21:12
 */
public class MapUtils {

    public static String AK = "MnhXPdbt18isp5V45wJNeqvZz5KzuTji";

    public static double PI = 3.141592653589793 * 3000.0 / 180.0;


    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Point {
        @Schema(description = "纬度")
        private double lat;
        @Schema(description = "经度")
        private double lng;

        public String toString() {
            // 小数点不能超过6位
            return String.format("%.6f,%.6f", lat, lng);
        }
    }

    /**
     * 坐标转换URL
     */
    public static String GEOCODING_URL = "https://api.map.baidu.com/geoconv/v2/?";


    /**
     * 坐标转换
     * 转换方式可选值：
     * 1：amap/tencent to bd09ll
     * 2：gps to bd09ll
     * 3：bd09ll to bd09mc
     * 4：bd09mc to bd09ll
     * 5：bd09ll to amap/tencent
     * 6：bd09mc to amap/tencent
     *
     * @param coords 坐标
     * @return 转换后的坐标
     */
    public static Point convert(Point coords, int mode) throws IOException {
        System.out.printf("coords: %s\n", coords);
        HttpRequestUtils.Params params = HttpRequestUtils.Params.builder()
                .url(GEOCODING_URL)
                .params(Map.of(
                        "ak", AK,
                        "mode", mode,
                        "coords", coords.toString()
                ))
                .build();
        try {
            HttpResponse response = HttpRequestUtils.get(params);
            String responseBody = new String(response.getEntity().getContent().readAllBytes(), StandardCharsets.UTF_8);
            System.out.printf("responseBody: %s\n", responseBody);
            // 解析 JSON 响应
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            return Point.builder()
                    .lat(jsonNode.get("result").get(0).get("y").asDouble())
                    .lng(jsonNode.get("result").get(0).get("x").asDouble())
                    .build();

        } catch (IOException e) {
            throw new IOException("坐标转换失败");
        }
    }

    /**
     * 火星坐标系 (GCJ-02) 与百度坐标系 (BD-09) 的转换算法 将 GCJ-02 坐标转换成 BD-09 坐标
     *
     * @param Gcj02Latitude  纬度
     * @param Gcj02Longitude 经度
     * @return
     */
    public static Point Gcj02ToBd09(double Gcj02Latitude, double Gcj02Longitude) {
        double x = Gcj02Longitude, y = Gcj02Latitude;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * PI);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * PI);
        double bd_lng = z * Math.cos(theta) + 0.0065;
        double bd_lat = z * Math.sin(theta) + 0.006;
        return Point.builder().lng(bd_lng).lat(bd_lat).build();
    }

    /**
     * 获取两点之间的距离URL
     */
    public static String DIRECTION_LITE_URL = "https://api.map.baidu.com/directionlite/v1/walking?";

    /**
     * 获取两点之间的距离
     *
     * @param origin      起点
     * @param destination 终点
     * @return 距离
     */
    public static double getDistance(Point origin, Point destination) throws IOException {
        System.out.printf("origin: %s, destination: %s\n", origin, destination);
        HttpRequestUtils.Params params = HttpRequestUtils.Params.builder()
                .url(DIRECTION_LITE_URL)
                .params(Map.of(
                        "ak", AK,
                        "origin", origin.toString(),
                        "destination", destination.toString()
                ))
                .build();
        try {
            HttpResponse response = HttpRequestUtils.get(params);
            String responseBody = new String(response.getEntity().getContent().readAllBytes(), StandardCharsets.UTF_8);
            System.out.printf("responseBody: %s\n", responseBody);
            // 解析 JSON 响应
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            return jsonNode.get("result").get("routes").get(0).get("distance").asDouble();
        } catch (IOException e) {
            throw new IOException("获取距离失败");
        }
    }

}
