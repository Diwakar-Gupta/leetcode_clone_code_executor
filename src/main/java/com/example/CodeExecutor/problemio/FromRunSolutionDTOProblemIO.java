package com.example.CodeExecutor.problemio;

import java.io.BufferedReader;
import java.io.File;
import java.io.StringReader;
import java.util.Scanner;

import com.example.shared.kafka.dto.RunSolutionDTO;

public class FromRunSolutionDTOProblemIO implements ProblemIO {

    private RunSolutionDTO runSolutionDTO;

    public FromRunSolutionDTOProblemIO(RunSolutionDTO runSolutionDTO) {
    }

    @Override
    public int getNumberOfTestCases() {
        return runSolutionDTO.getTestCases() == null ? 0 : runSolutionDTO.getTestCases().size();
    }

    @Override
    public BufferedReader readInput(int testCaseNumber) {
        String sourceText = runSolutionDTO.getTestCases().get(testCaseNumber).getInput();
        BufferedReader sourceReader = new BufferedReader(
                new StringReader(sourceText));

        return sourceReader;
    }

    @Override
    public BufferedReader readOutput(int testCaseNumber) {
        String sourceText = "";
        BufferedReader sourceReader = new BufferedReader(
                new StringReader(sourceText));

        return sourceReader;
    }

}
