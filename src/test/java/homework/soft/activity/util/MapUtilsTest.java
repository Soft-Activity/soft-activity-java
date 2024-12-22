package homework.soft.activity.util;

import org.junit.jupiter.api.Test;

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

}