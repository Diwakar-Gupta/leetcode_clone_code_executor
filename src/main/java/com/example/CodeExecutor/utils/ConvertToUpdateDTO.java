package com.example.CodeExecutor.utils;

import com.example.CodeExecutor.executor.ExecutorRunResult;
import com.example.shared.kafka.dto.RunCodeUpdateDTO;
import com.example.shared.kafka.dto.RunCodeUpdateDTO.Judgement;

import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ConvertToUpdateDTO {

    public static RunCodeUpdateDTO.Preprocessing getPreprocessing(ExecutorRunResult executorRunResult) {
        RunCodeUpdateDTO.Preprocessing preprocessing = new RunCodeUpdateDTO.Preprocessing();
        RunCodeUpdateDTO.Preprocessing.PreprocessingStatus status;
        if (executorRunResult.getStatus().equals(ExecutorRunResult.Status.CompilationSuccess)) {
            status = RunCodeUpdateDTO.Preprocessing.PreprocessingStatus.Accepted;
        } else {
            status = RunCodeUpdateDTO.Preprocessing.PreprocessingStatus.Error;
        }
        preprocessing.setStatus(status);
        return preprocessing;
    }

    public static String convertInputStreamToString(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line).append('\n');
                }
            }

        } catch (Exception e) {
        }
        return stringBuilder.toString();
    }

    public static RunCodeUpdateDTO.TestCase getUpdateTestCase(ExecutorRunResult executorRunResult) {
        if (executorRunResult.getStatus().equals(ExecutorRunResult.Status.Accepted)) {
            RunCodeUpdateDTO.TestCase testCase = new RunCodeUpdateDTO.TestCase();
            testCase.setStatus(RunCodeUpdateDTO.TestCase.TestCaseStatus.Accepted);

            String output = convertInputStreamToString(executorRunResult.getOutputStream());
            testCase.setOutput(output);
            return testCase;
        } else if (executorRunResult.getStatus().equals(ExecutorRunResult.Status.NotAccepted)) {
            RunCodeUpdateDTO.TestCase testCase = new RunCodeUpdateDTO.TestCase();
            testCase.setStatus(RunCodeUpdateDTO.TestCase.TestCaseStatus.NotAccepted);

            String output = convertInputStreamToString(executorRunResult.getOutputStream());
            testCase.setOutput(output);
            return testCase;
        } else if (executorRunResult.getStatus().equals(ExecutorRunResult.Status.RunTimeError)) {
            RunCodeUpdateDTO.TestCase testCase = new RunCodeUpdateDTO.TestCase();
            testCase.setStatus(RunCodeUpdateDTO.TestCase.TestCaseStatus.RunTimeError);

            String content = convertInputStreamToString(executorRunResult.getErrorStream());
            testCase.setContent(content);
            return testCase;
        } else if (executorRunResult.getStatus().equals(ExecutorRunResult.Status.NotAbleToRun)) {
            RunCodeUpdateDTO.TestCase testCase = new RunCodeUpdateDTO.TestCase();
            testCase.setStatus(RunCodeUpdateDTO.TestCase.TestCaseStatus.RunTimeError);

            testCase.setContent("Not able to run");
            return testCase;
        } else {
            return null;
        }
    }

    public static Judgement getJudgement(ExecutorRunResult executorRunResult) {
        if (executorRunResult.getStatus().equals(ExecutorRunResult.Status.CompilationError)) {
            return Judgement.CompilationError;
        } else {
            return null;
        }
    }
}
