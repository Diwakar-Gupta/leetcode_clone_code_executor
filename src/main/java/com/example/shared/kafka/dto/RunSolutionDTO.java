package com.example.shared.kafka.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;

@Getter
@Setter
public class RunSolutionDTO {
    private Date createdAt;
    private String problemSlug;
    private String languageSlug;
    private String code;
    private ArrayList<TestCase> testCases;
    private String uuid;

    @Getter
    @Setter
    public static class TestCase{
        private Long id;
        private String input;
    }
}
