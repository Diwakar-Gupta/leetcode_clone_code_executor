package com.example.CodeExecutor.executor;

import java.io.File;
import java.io.IOException;

import com.example.CodeExecutor.RunCodeUpdateDTOBuilder;
import com.example.CodeExecutor.checker.Checker;
import com.example.CodeExecutor.problemio.ProblemIO;
import com.example.shared.kafka.dto.RunSolutionDTO;
import com.example.shared.kafka.dto.RunCodeUpdateDTO.TestCase;
import com.example.shared.kafka.dto.RunCodeUpdateDTO.TestCase.TestCaseStatus;

public class PythonUnSafeExecutor implements Executor {

    private RunSolutionDTO runSolutionDTO;
    private ProblemIO problemIO;
    private Checker checker;

    public PythonUnSafeExecutor(RunSolutionDTO runSolutionDTO, ProblemIO problemIO, Checker checker) {
        this.runSolutionDTO = runSolutionDTO;
        this.problemIO = problemIO;
        this.checker = checker;
    }

    @Override
    public void start() {
    }

    @Override
    public void prepare() {
    }

    @Override
    public void run(RunCodeUpdateDTOBuilder runCodeUpdateDTOBuilder) {

        for (int testCaseNumber = 0; testCaseNumber < problemIO.getNumberOfTestCases(); testCaseNumber++) {

            try {

                // Get the temporary directory
                String tempDir = System.getProperty("java.io.tmpdir");

                File pythonFile = new File(tempDir, "main.py");
                String pythonCode = runSolutionDTO.getCode();
                java.nio.file.Files.write(pythonFile.toPath(), pythonCode.getBytes());

                File inputFile = new File(tempDir, "input");
                problemIO.copyToFile(problemIO.readInput(testCaseNumber), inputFile);

                ProcessBuilder processBuilder = new ProcessBuilder("python3.8",
                        pythonFile.getAbsolutePath(), " < ", inputFile.getAbsolutePath());
                processBuilder.redirectOutput(new File(tempDir, "output"));
                Process process = processBuilder.start();

                // Wait for the process to complete
                int exitCode = process.waitFor();

                if (exitCode == 0) {

                    // runCodeUpdateDTOBuilder.addTestCase(TestCaseStatus.Accepted, );
                } else {
                    runCodeUpdateDTOBuilder.addTestCaseWithRuntimeError(process.getErrorStream().toString());
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void clean() {
    }

    @Override
    public void dispose() {
    }
}
