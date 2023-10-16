package com.example.shared.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;

@Getter
@Setter
public class RunSolutionDTO {
    private Date createdAt;
    private Long problemId;
    private String languageSlug;
    private String code;
    private ArrayList<TestCase> testCases;
    private String uuid;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TestCase {
        private Long id;
        private String input;
    }
}
