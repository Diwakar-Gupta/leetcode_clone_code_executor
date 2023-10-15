package com.example.CodeExecutor.executor;

import com.example.CodeExecutor.RunCodeUpdateDTOBuilder;
import com.example.shared.kafka.dto.RunCodeUpdateDTO;
import com.example.shared.kafka.dto.RunCodeUpdateDTO.Runtime.RuntimeStatus;
import com.example.shared.kafka.dto.RunSolutionDTO;

import java.util.Random;

public class MockExecutor implements Executor {

    private RunSolutionDTO runSolutionDTO;

    public MockExecutor(RunSolutionDTO runSolutionDTO) {
        this.runSolutionDTO = runSolutionDTO;
    }

    @Override
    public void start() {
    }

    @Override
    public void prepare() {
    }

    @Override
    public void run(RunCodeUpdateDTOBuilder runCodeUpdateDTOBuilder) {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        runCodeUpdateDTOBuilder.setPreprocessing(new RunCodeUpdateDTO.Preprocessing(
                RunCodeUpdateDTO.Preprocessing.PreprocessingStatus.Accepted,
                null,
                null));

        if (runSolutionDTO.getTestCases() != null) {
            for (RunSolutionDTO.TestCase testcase : runSolutionDTO.getTestCases()) {

                RunCodeUpdateDTO.TestCase.TestCaseStatus testCaseStatus = RunCodeUpdateDTO.TestCase.TestCaseStatus
                        .values()[new Random().nextInt(RunCodeUpdateDTO.TestCase.TestCaseStatus
                                .values().length)];
                RunCodeUpdateDTO.Runtime.RuntimeStatus runtimeStatus = RunCodeUpdateDTO.Runtime.RuntimeStatus
                        .values()[new Random().nextInt(RunCodeUpdateDTO.Runtime.RuntimeStatus
                                .values().length)];

                runCodeUpdateDTOBuilder.addTestCase(
                        new RunCodeUpdateDTO.TestCase(
                                testcase.getId(),
                                testCaseStatus,
                                null,
                                new RunCodeUpdateDTO.Runtime(
                                        runtimeStatus,
                                        runtimeStatus == RuntimeStatus.Error
                                                ? "array index out of bound"
                                                : null,
                                        runtimeStatus == RuntimeStatus.Error
                                                ? "array index out of bound at line 89"
                                                : null)));

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        runCodeUpdateDTOBuilder.setJudgement(
                RunCodeUpdateDTO.Judgement.values()[new Random()
                        .nextInt(RunCodeUpdateDTO.Judgement.values().length)]);
    }

    @Override
    public void clean() {
    }

    @Override
    public void dispose() {
    }
}
