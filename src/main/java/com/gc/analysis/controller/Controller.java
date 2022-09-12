package com.gc.analysis.controller;

import com.gc.analysis.dto.GcCapacityDto;
import com.gc.analysis.dto.GcUtilDto;
import com.gc.analysis.enums.ServerType;
import com.gc.analysis.service.DtoMappingService;
import com.gc.analysis.service.InfluxService;
import com.gc.analysis.utils.EnvParser;
import com.gc.analysis.utils.PointBuilder;
import lombok.RequiredArgsConstructor;
import org.influxdb.dto.Point;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class Controller {

    private final DtoMappingService DTO_MAPPING_SERVICE;

    private final InfluxService INFLUX_SERVICE;

    @GetMapping("/gc/util/{serverName}")
    public ResponseEntity<String> gcUtil(@PathVariable String serverName) {
        ServerType serverType = ServerType.getServerType(serverName);
        List<GcUtilDto> gcUtilDtoList = DTO_MAPPING_SERVICE.getGcUtilDtoList(serverType.getUtilPath());
        List<Point> gcUtilPoints = PointBuilder.getGcUtilPoints(serverType, gcUtilDtoList, EnvParser.getServerStartTime());
        INFLUX_SERVICE.insert(gcUtilPoints);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("OK");
    }

    @GetMapping("/gc/capacity/{serverName}")
    public ResponseEntity<String> gcCapacity(@PathVariable String serverName) {
        ServerType serverType = ServerType.getServerType(serverName);
        List<GcCapacityDto> gcCapacityDtoList = DTO_MAPPING_SERVICE.getGcCapacityDtoList(serverType.getCapacityPath());
        List<Point> gcCapacityPoints = PointBuilder.getGcCapacityPoints(serverType, gcCapacityDtoList, EnvParser.getServerStartTime());
        INFLUX_SERVICE.insert(gcCapacityPoints);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("OK");
    }

}
