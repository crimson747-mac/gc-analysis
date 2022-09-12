package com.gc.analysis.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EnvParserTest {

    @BeforeEach
    void beforeEach() {
        System.setProperty("serverStartTime", "2022-09-04 13:15:45");
    }

    @Test
    void getServerStartTime() {
        //given

        //when
        long serverStartTime = EnvParser.getServerStartTime();

        //then
        assertThat(serverStartTime).isEqualTo(1662264945000L);
    }
}