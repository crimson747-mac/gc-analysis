package com.gc.analysis.service;

import com.gc.analysis.dto.GcCapacityDto;
import com.gc.analysis.dto.GcUtilDto;
import com.gc.analysis.utils.FileUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DtoMappingServiceTest {

    DtoMappingService DTO_MAPPING_SERVICE = new DtoMappingService(new FileUtils());

    @Test
    void getGcUtilDtoList() {
        //given
        String fileName = "/Users/zeros/Desktop/gc/gc-analysis/src/main/resources/static/gc-util.txt";

        //when
        List<GcUtilDto> result = DTO_MAPPING_SERVICE.getGcUtilDtoList(fileName);

        //then
        assertThat(result.size()).isEqualTo(20);
    }

    @Test
    void getGcCapacityDtoList() {
        //given
        String fileName = "/Users/zeros/Desktop/gc/gc-analysis/src/main/resources/static/gc-capacity.txt";

        //when
        List<GcCapacityDto> result = DTO_MAPPING_SERVICE.getGcCapacityDtoList(fileName);

        //then
        assertThat(result.size()).isEqualTo(30);
    }
}