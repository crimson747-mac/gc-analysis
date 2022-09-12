package com.gc.analysis.service;

import com.gc.analysis.dto.GcCapacityDto;
import com.gc.analysis.dto.GcUtilDto;
import com.gc.analysis.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DtoMappingService {
    private final FileUtils fileUtils;

    public List<GcUtilDto> getGcUtilDtoList(String fileName) {
        List<String> lines = fileUtils.readFile(fileName);
        return lines.stream()
                .map(GcUtilDto::new)
                .collect(Collectors.toList());
    }

    public List<GcCapacityDto> getGcCapacityDtoList(String fileName) {
        List<String> lines = fileUtils.readFile(fileName);
        return lines.stream()
                .map(GcCapacityDto::new)
                .collect(Collectors.toList());
    }

}
