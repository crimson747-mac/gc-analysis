package com.gc.analysis.controller;

import com.gc.analysis.dto.GcCapacityDto;
import com.gc.analysis.dto.GcUtilDto;
import com.gc.analysis.enums.ServerType;
import com.gc.analysis.service.DtoMappingService;
import com.gc.analysis.service.InfluxService;
import com.gc.analysis.utils.EnvParser;
import com.gc.analysis.utils.PointBuilder;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.influxdb.dto.Point;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class Controller {

    private final DtoMappingService DTO_MAPPING_SERVICE;

    private final InfluxService INFLUX_SERVICE;

    @GetMapping("/gc/util/{serverName}")
    @ApiOperation(
            value = "gc_util 로그 파일을 찾아서 INFLUX_DB 에 업데이트 하는 API",
            notes = "com.gc.analysis.enums 경로에 있는 ServerType Enum 클래스에 있는 정보를 참조한다.")
    @ApiImplicitParam(
            name = "serverName",
            value = "INFLUX_DB 의 gc_util measurements 에 어떠한 서버의 정보인지 구분하기 위한 컬럼에 삽입되는 정보")
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
    @ApiOperation(
            value = "gc_capacity 로그 파일을 찾아서 INFLUX_DB 에 업데이트 하는 API",
            notes = "com.gc.analysis.enums 경로에 있는 ServerType Enum 클래스에 있는 정보를 참조한다.")
    @ApiImplicitParam(
            name = "serverName",
            value = "INFLUX_DB 의 gc_capacity measurements 에 어떠한 서버의 정보인지 구분하기 위한 컬럼에 삽입되는 정보")
    public ResponseEntity<String> gcCapacity(@PathVariable String serverName) {
        ServerType serverType = ServerType.getServerType(serverName);
        List<GcCapacityDto> gcCapacityDtoList = DTO_MAPPING_SERVICE.getGcCapacityDtoList(serverType.getCapacityPath());
        List<Point> gcCapacityPoints = PointBuilder.getGcCapacityPoints(serverType, gcCapacityDtoList, EnvParser.getServerStartTime());
        INFLUX_SERVICE.insert(gcCapacityPoints);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("OK");
    }

    @PostMapping("/file/gc/util/{serverName}")
    @ApiOperation(
            value = "gc_util 로그 파일을 받아서 INFLUX_DB 에 업데이트 하는 API",
            notes = "요청 예: curl -F file=@\"gc-util.txt\" -X POST http://localhost:8080/v1/file/gc/util/${serverName}")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "serverName", value = "INFLUX_DB 의 gc_util measurements 에 어떠한 서버의 정보인지 구분하기 위한 컬럼에 삽입되는 정보"),
        @ApiImplicitParam(name = "file", value = "INFLUX_DB에 gc_util 정보가 담긴 로그 파일")})
    public ResponseEntity<String> fileGcUtil(@PathVariable String serverName, @RequestParam MultipartFile file) {
        log.info("serverName: {}, file: {}", serverName, file);
        ServerType serverType = ServerType.getServerType(serverName);
        List<GcUtilDto> gcUtilDtoList = DTO_MAPPING_SERVICE.getGcUtilDtoList(file);
        List<Point> gcUtilPoints = PointBuilder.getGcUtilPoints(serverType, gcUtilDtoList, EnvParser.getServerStartTime());
        INFLUX_SERVICE.insert(gcUtilPoints);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("OK");
    }

    @PostMapping("/file/gc/capacity/{serverName}")
    @ApiOperation(
            value = "gc_capacity 로그 파일을 받아서 INFLUX_DB 에 업데이트 하는 API",
            notes = "요청 예: curl -F file=@\"gc-util.txt\" -X POST http://localhost:8080/v1/file/gc/capacity/${serverName}")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "serverName", value = "INFLUX_DB 의 gc_capacity measurements 에 어떠한 서버의 정보인지 구분하기 위한 컬럼에 삽입되는 정보"),
        @ApiImplicitParam(name = "file", value = "INFLUX_DB에 gc_capacity 정보가 담긴 로그 파일")})
    public ResponseEntity<String> fileGcCapacity(@PathVariable String serverName, @RequestParam MultipartFile file) {
        log.info("serverName: {}, file: {}", serverName, file);
        ServerType serverType = ServerType.getServerType(serverName);
        List<GcCapacityDto> gcCapacityDtoList = DTO_MAPPING_SERVICE.getGcCapacityDtoList(file);
        List<Point> gcCapacityPoints = PointBuilder.getGcCapacityPoints(serverType, gcCapacityDtoList, EnvParser.getServerStartTime());
        INFLUX_SERVICE.insert(gcCapacityPoints);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("OK");
    }
}
