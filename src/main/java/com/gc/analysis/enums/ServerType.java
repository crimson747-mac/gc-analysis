package com.gc.analysis.enums;

import lombok.Getter;

import java.util.NoSuchElementException;

@Getter
public enum ServerType {

    CLICK(
            "/Users/zeros/Desktop/Project/analysis/src/main/resources/static/gc-util.txt",
            "/Users/zeros/Desktop/Project/analysis/src/main/resources/static/gc-capacity.txt"),
    SERVER_BATCH(
            "/Users/zeros/Desktop/Project/analysis/src/main/resources/static/gc-util.txt",
            "/Users/zeros/Desktop/Project/analysis/src/main/resources/static/gc-capacity.txt"),
    SDK_CONFIG(
            "/Users/zeros/Desktop/Project/analysis/src/main/resources/static/gc-util.txt",
            "/Users/zeros/Desktop/Project/analysis/src/main/resources/static/gc-capacity.txt");

    private String utilPath;
    private String capacityPath;

    ServerType(String utilPath, String capacityPath) {
        this.utilPath = utilPath;
        this.capacityPath = capacityPath;
    }

    public static ServerType getServerType(String serverName) {
        switch (serverName.toLowerCase()) {
            case "click":
                return CLICK;
            case "server-batch":
                return SERVER_BATCH;
            case "sdk-config":
                return SDK_CONFIG;
            default:
                throw new NoSuchElementException("일치하는 이름을 가진 서버가 존재하지 않습니다.");
        }
    }
}
