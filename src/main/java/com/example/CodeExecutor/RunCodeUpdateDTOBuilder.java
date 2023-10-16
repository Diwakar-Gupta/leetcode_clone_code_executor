package com.example.CodeExecutor;

import com.example.shared.kafka.dto.RunCodeUpdateDTO;
import com.example.shared.kafka.dto.RunCodeUpdateDTO.Judgement;
import com.example.shared.kafka.dto.RunCodeUpdateDTO.Preprocessing;
import com.example.shared.kafka.dto.RunCodeUpdateDTO.TestCase;
import com.example.shared.kafka.dto.RunCodeUpdateDTO.TestCase.TestCaseStatus;

interface Task {
    void execute(RunCodeUpdateDTO runCodeUpdateDTO);
}

public class RunCodeUpdateDTOBuilder {
    private RunCodeUpdateDTO response;
    private Task onUpdate;
    private Task onComplete;

    RunCodeUpdateDTOBuilder(String uuid, Task onUpdate, Task onComplete) {
        response = new RunCodeUpdateDTO();
        response.setUuid(uuid);

        this.onUpdate = onUpdate;
        this.onComplete = onComplete;
    }

    public void setPreprocessing(Preprocessing preprocessing) {
        response.setPreprocessing(preprocessing);
        this.onUpdate.execute(response);
    }

    public void addTestCase(TestCase testCase) {
        response.addTestCase(testCase);
        this.onUpdate.execute(response);
    }

    public void addTestCase(TestCaseStatus status, String output) {
        response.addTestCase(status, output);
        this.onUpdate.execute(response);
    }

    public void setJudgement(Judgement judgement) {
        response.setJudgement(judgement);
        this.onUpdate.execute(response);
        this.onComplete.execute(response);
    }

    public void addTestCaseWithRuntimeError(String content) {
        response.addTestCaseWithRuntimeError(content);
    }
}
