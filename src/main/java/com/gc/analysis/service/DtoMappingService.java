package com.gc.analysis.service;

import com.gc.analysis.dto.GcCapacityDto;
import com.gc.analysis.dto.GcUtilDto;
import com.gc.analysis.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    public List<GcUtilDto> getGcUtilDtoList(MultipartFile file) {
        List<String> lines = fileUtils.readFile(file);
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

    public List<GcCapacityDto> getGcCapacityDtoList(MultipartFile file) {
        List<String> lines = fileUtils.readFile(file);
        return lines.stream()
                .map(GcCapacityDto::new)
                .collect(Collectors.toList());
    }
}
