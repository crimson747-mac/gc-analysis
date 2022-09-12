package com.gc.analysis.utils;

import com.gc.analysis.dto.GcCapacityDto;
import com.gc.analysis.dto.GcUtilDto;
import com.gc.analysis.enums.ServerType;
import com.gc.analysis.service.DtoMappingService;
import org.influxdb.dto.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PointBuilderTest {

    DtoMappingService DTO_MAPPING_SERVICE = new DtoMappingService(new FileUtils());

    @BeforeEach
    void beforeEach() {
        System.setProperty("serverStartTime", "2022-09-04 13:15:45");
    }

    @Test
    void getGcUtilPoints() {
        //given
        ServerType serverType = ServerType.CLICK;
        String fileName = "/Users/zeros/Desktop/gc/gc-analysis/src/main/resources/static/gc-util.txt";

        //when
        List<GcUtilDto> dtoList = DTO_MAPPING_SERVICE.getGcUtilDtoList(fileName);
        List<Point> result = PointBuilder.getGcUtilPoints(serverType, dtoList, EnvParser.getServerStartTime());

        //then
        assertThat(result.size()).isEqualTo(20);
    }

    @Test
    void getGcCapacityPoints() {
        //given
        ServerType serverType = ServerType.CLICK;
        String fileName = "/Users/zeros/Desktop/gc/gc-analysis/src/main/resources/static/gc-capacity.txt";

        //when
        List<GcCapacityDto> dtoList = DTO_MAPPING_SERVICE.getGcCapacityDtoList(fileName);
        List<Point> result = PointBuilder.getGcCapacityPoints(serverType, dtoList, EnvParser.getServerStartTime());

        //then
        assertThat(result.size()).isEqualTo(30);
    }
}