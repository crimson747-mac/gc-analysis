package com.gc.analysis.utils;

import com.gc.analysis.dto.GcCapacityDto;
import com.gc.analysis.dto.GcUtilDto;
import com.gc.analysis.enums.ServerType;
import org.influxdb.dto.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PointBuilder {

    public static List<Point> getGcUtilPoints(ServerType serverType, List<GcUtilDto> gcUtilDtoList, long serverStartTime) {
        List<Point> result = new ArrayList<>();

        for (GcUtilDto dto : gcUtilDtoList)
            result.add(getGcUtilPoint(serverType, dto, serverStartTime));

        return result;
    }

    private static Point getGcUtilPoint(ServerType serverType, GcUtilDto dto, long serverStartTime) {
        return Point.measurement("gc_util")
                .time(serverStartTime + dto.getTimestamp(), TimeUnit.MILLISECONDS)
                .tag("serverType", serverType.name())
                .addField("S0", dto.getS0())
                .addField("S1", dto.getS1())
                .addField("E", dto.getE())
                .addField("O", dto.getO())
                .addField("YGC", dto.getYgc())
                .addField("YGCT", dto.getYgct())
                .addField("AVG_YGCT", dto.getAvgYgcTime())
                .addField("FGC", dto.getFgc())
                .addField("FGCT", dto.getFgct())
                .addField("AVG_FGCT", dto.getAvgFgcTime())
                .addField("GCT", dto.getGct()).build();
    }

    public static List<Point> getGcCapacityPoints(ServerType serverType, List<GcCapacityDto> gcCapacityDtoList, long serverStartTime) {
        List<Point> result = new ArrayList<>();

        for (GcCapacityDto dto : gcCapacityDtoList)
            result.add(getGcCapacityPoint(serverType, dto, serverStartTime));

        return result;
    }

    private static Point getGcCapacityPoint(ServerType serverType, GcCapacityDto dto, long serverStartTime) {
        return Point.measurement("gc_capacity")
                .time(serverStartTime + dto.getTimestamp(), TimeUnit.MILLISECONDS)
                .tag("serverType", serverType.name())
                .addField("S0C", dto.getS0c())
                .addField("S1C", dto.getS1c())
                .addField("EC", dto.getEc())
                .addField("OC", dto.getOc()).build();
    }
}
