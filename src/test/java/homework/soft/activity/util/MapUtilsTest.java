package homework.soft.activity.util;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Description
 *
 * @author 30597
 * @since 2024-12-21 21:27
 */
class MapUtilsTest {

    @Test
    void getDistance() {
        MapUtils.Point origin = MapUtils.Point.builder().lat(22.53907746361246).lng(113.94525039485168).build();

        MapUtils.Point destination = MapUtils.Point.builder().lat(22.538708616663268).lng(113.94540634366149).build();
        try {
            Double distance = MapUtils.getDistance(origin, destination);
            System.out.printf("distance: %f\n", distance);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void gcj02ToBd09() {
        MapUtils.Point point = MapUtils.Point.builder().lat(22.53344048394097).lng(113.9386962890625).build();
        MapUtils.Point destination = MapUtils.Point.builder().lat(22.538708616663268).lng(113.94540634366149).build();
        try {
            MapUtils.Point result = MapUtils.Gcj02ToBd09(point.getLat(), point.getLng());
            System.out.printf("result: %s\n", result);
            Double distance = MapUtils.getDistance(result, destination);
            System.out.printf("distance: %f\n", distance);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试Point类的getter和setter方法
     */
    @Test
    public void testPointGetterSetter() {
        MapUtils.Point point = new MapUtils.Point();
        point.setLat(40.0);
        point.setLng(120.0);

        assertEquals(40.0, point.getLat());
        assertEquals(120.0, point.getLng());
    }

    /**
     * 测试坐标转换的异常情况
     */
    @Test
    public void testConvertWithInvalidMode() {
        MapUtils.Point sourcePoint = MapUtils.Point.builder()
                .lat(39.915119)
                .lng(116.403963)
                .build();

        assertThrows(IOException.class, () -> {
            MapUtils.convert(sourcePoint, 999); // 使用无效的mode
        });
    }

    /**
     * 测试不同模式的坐标转换
     */
    @Test
    public void testConvertWithDifferentModes() throws IOException {
        MapUtils.Point sourcePoint = MapUtils.Point.builder()
                .lat(39.915119)
                .lng(116.403963)
                .build();
        // 测试GPS坐标转百度坐标(mode=2)
        MapUtils.Point gpsConverted = MapUtils.convert(sourcePoint, 2);
        assertNotNull(gpsConverted);
        // 测试百度经纬度坐标转百度墨卡托坐标(mode=3)
        MapUtils.Point mcConverted = MapUtils.convert(sourcePoint, 3);
        assertNotNull(mcConverted);
    }
    /**
     * 测试计算两点之间距离的边界情况
     */
    @Test
    public void testGetDistanceEdgeCases() throws IOException {
        // 测试相同点之间的距离
        MapUtils.Point samePoint = MapUtils.Point.builder()
                .lat(39.915119)
                .lng(116.403963)
                .build();
        double distance = MapUtils.getDistance(samePoint, samePoint);
        assertEquals(0, distance);
        // 测试较远距离的两点
        MapUtils.Point farPoint1 = MapUtils.Point.builder()
                .lat(31.239692)
                .lng(121.499755)
                .build(); // 上海
        MapUtils.Point farPoint2 = MapUtils.Point.builder()
                .lat(39.915119)
                .lng(116.403963)
                .build(); // 北京

        double longDistance = MapUtils.getDistance(farPoint1, farPoint2);
        assertTrue(longDistance > 1000000); // 距离应该大于1000公里
    }

}