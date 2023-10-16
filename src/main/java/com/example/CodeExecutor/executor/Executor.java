package com.example.CodeExecutor.executor;

import com.example.CodeExecutor.RunCodeUpdateDTOBuilder;
import com.example.shared.kafka.dto.RunCodeUpdateDTO;

public interface Executor {
    void start();

    ExecutorRunResult preProcess();

    ExecutorRunResult prepareAndRun(int testCaseNumber);

    int getNumberOfTestCases();

    default void run(RunCodeUpdateDTOBuilder runCodeUpdateDTOBuilder) {
        start();
        ExecutorRunResult executorRunResultP = preProcess();
        RunCodeUpdateDTO.Preprocessing preprocessing = com.example.CodeExecutor.utils.ConvertToUpdateDTO
                .getPreprocessing(executorRunResultP);
        runCodeUpdateDTOBuilder.setPreprocessing(preprocessing);

        if (executorRunResultP.isPreprocessingFailed()) {
            RunCodeUpdateDTO.Judgement judgement = com.example.CodeExecutor.utils.ConvertToUpdateDTO
                    .getJudgement(executorRunResultP);
            runCodeUpdateDTOBuilder.setJudgement(judgement);
            dispose();
            return;
        }

        boolean allAccepted = true;
        for (int testCaseNumber = 0; testCaseNumber < getNumberOfTestCases(); testCaseNumber++) {
            ExecutorRunResult executorRunResult = prepareAndRun(testCaseNumber);

            RunCodeUpdateDTO.TestCase testCase = com.example.CodeExecutor.utils.ConvertToUpdateDTO
                    .getUpdateTestCase(executorRunResult);
            runCodeUpdateDTOBuilder.addTestCase(testCase);
            if (testCase.getStatus().equals(RunCodeUpdateDTO.TestCase.TestCaseStatus.Accepted)) {
            } else {
                allAccepted = false;
            }

            clean();
        }
        if (allAccepted) {
            runCodeUpdateDTOBuilder.setJudgement(RunCodeUpdateDTO.Judgement.Accepted);
        } else {
            runCodeUpdateDTOBuilder.setJudgement(RunCodeUpdateDTO.Judgement.NotAccepted);
        }
        dispose();
    }

    void clean();

    void dispose();
}
