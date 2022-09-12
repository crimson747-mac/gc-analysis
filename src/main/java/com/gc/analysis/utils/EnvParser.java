package com.gc.analysis.utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EnvParser {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static long getServerStartTime() {
        String serverStartTime = System.getenv("serverStartTime");
        LocalDateTime parse = LocalDateTime.parse(serverStartTime, formatter);
        Timestamp timestamp = Timestamp.valueOf(parse);
        return timestamp.getTime();
    }
}
