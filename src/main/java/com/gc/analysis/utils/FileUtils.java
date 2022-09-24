package com.gc.analysis.utils;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileUtils {
    public List<String> readFile(String fileName) {
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)))) {
            return getFileLineContent(bufferedReader);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> readFile(MultipartFile file) {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            return getFileLineContent(bufferedReader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @NotNull
    private static List<String> getFileLineContent(BufferedReader bufferedReader) throws IOException {
        List<String> result = new ArrayList<>();
        String line = bufferedReader.readLine(); // 헤더 한 번 제거
        while((line = bufferedReader.readLine()) != null) {
            String trim = line.trim();
            byte[] bytes = trim.getBytes(StandardCharsets.US_ASCII);
            byte[] newBytes = new byte[bytes.length];

            int count = 0;
            for (byte aByte : bytes) {
                if (aByte != 32) {
                    newBytes[count] = aByte;
                } else {
                    if (newBytes[count - 1] == 44) continue;
                    newBytes[count] = 44;
                }
                count++;
            }

            result.add(new String(newBytes).trim());
        }

        return result;
    }
}
