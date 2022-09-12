package com.gc.analysis.service;

import lombok.RequiredArgsConstructor;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InfluxService implements ApplicationRunner {

    @Value("${influx.server}")
    private String serverURL;

    @Value("${influx.database}")
    private String databaseName;

    private InfluxDB influxDB;


    public void insert(List<Point> gcUtilPoints) {
        for (Point point : gcUtilPoints) {
            influxDB.write(point);
        }
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        influxDB = InfluxDBFactory.connect(this.serverURL, "zenon84850", "zenon84850").setDatabase(this.databaseName);
    }
}
