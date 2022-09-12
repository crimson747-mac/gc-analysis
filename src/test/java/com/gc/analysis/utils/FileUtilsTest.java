package com.gc.analysis.utils;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FileUtilsTest {

    FileUtils fileUtils = new FileUtils();

    @Test
    void readFile() {
        //given
        String fileName = "/Users/zeros/Desktop/gc/gc-analysis/src/main/resources/static/gc-util.txt";

        //when
        fileUtils = new FileUtils();
        List<String> result = fileUtils.readFile(fileName);

        for (String s : result) {
            System.out.println(s);
        }

        //then
        assertThat(result.size()).isEqualTo(20);
    }

    @Test
    void readFile2() {
        //given
        String fileName = "/Users/zeros/Desktop/gc/gc-analysis/src/main/resources/static/gc-capacity.txt";

        //when
        fileUtils = new FileUtils();
        List<String> result = fileUtils.readFile(fileName);

        for (String s : result) {
            System.out.println(s);
        }

        //then
        assertThat(result.size()).isEqualTo(30);
    }
}
