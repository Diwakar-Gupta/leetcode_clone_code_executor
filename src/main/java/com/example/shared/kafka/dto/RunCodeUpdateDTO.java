package com.example.shared.kafka.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RunCodeUpdateDTO {
    private Date updateTime;
    private String uuid;

    public RunCodeUpdateDTO(){
        updateTime = new Date();
    }

    private UpdateType updateType;

    // for PreProcessingError and RunTimeError
    private String heading;
    private String message;

    // for test case
    private Long testCaseId;
    private TestCaseStatus testCaseStatus;
    private String testCaseOutput;

    public static enum TestCaseStatus{
        Accepted,
        NotAccepted,
    }
    public static enum UpdateType{
        Preprocessing,
        PreProcessingError,
        Running,
        RunTimeError,
        TestCase,
        Done,
    }
}
