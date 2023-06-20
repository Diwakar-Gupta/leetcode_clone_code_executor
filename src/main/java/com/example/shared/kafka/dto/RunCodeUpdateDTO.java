package com.example.shared.kafka.dto;

import java.util.ArrayList;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class RunCodeUpdateDTO {
    private Date updateTime;
    private String uuid;

    public RunCodeUpdateDTO(){
        updateTime = new Date();
    }

    private Preprocessing preprocessing;
    
    private ArrayList<TestCase> testCases;
    private Judgement judgement;

    public void addTestCase(TestCase testCase){
        if(this.testCases == null)this.testCases = new ArrayList<>();
        this.testCases.add(testCase);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TestCase{
        private Long id;
        private TestCaseStatus status;
        private String output;
        private Runtime runtime;

        public static enum TestCaseStatus{
            Accepted,
            NotAccepted,
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Runtime{
        private RuntimeStatus status;
        private String header;
        private String content;
        
        public static enum RuntimeStatus{
            Accepted,
            Error,
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Preprocessing{
        private PreprocessingStatus status;
        private String header;
        private String content;
        
        public static enum PreprocessingStatus{
            Skipped,
            Accepted,
            Error,
        }
    }

    public static enum Judgement{
        Accepted,
        CompilationError,
        NotAccepted,
    }
}
