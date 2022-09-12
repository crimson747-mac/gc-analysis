package com.gc.analysis.dto;

import com.gc.analysis.enums.ServerType;
import org.junit.jupiter.api.Test;

public class ServerTypeTest {

    @Test
    void name() {
        //given
        ServerType click = ServerType.CLICK;
        ServerType serverBatch = ServerType.SERVER_BATCH;
        ServerType sdkConfig = ServerType.SDK_CONFIG;

        //when
        System.out.println(click.name());
        System.out.println(serverBatch.name());
        System.out.println(sdkConfig.name());

        //then
    }
}
