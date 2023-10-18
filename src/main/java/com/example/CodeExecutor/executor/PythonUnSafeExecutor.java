package com.example.CodeExecutor.executor;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.BufferedReader;

import com.example.CodeExecutor.checker.Checker;
import com.example.CodeExecutor.problemio.ProblemIO;
import com.example.shared.kafka.dto.RunSolutionDTO;

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
    public ExecutorRunResult preProcess() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ExecutorRunResult.compilationSuccess();
    }

    @Override
    public ExecutorRunResult prepareAndRun(int testCaseNumber) {
        try {
            // Get the temporary directory
            String tempDir = System.getProperty("java.io.tmpdir");

            File pythonFile = new File(tempDir, "main.py");
            String pythonCode = runSolutionDTO.getCode();
            java.nio.file.Files.write(pythonFile.toPath(), pythonCode.getBytes());

            ProcessBuilder processBuilder = new ProcessBuilder("python3.8",
                    pythonFile.getAbsolutePath());
            // processBuilder.redirectOutput(new File(tempDir, "output"));
            Process process = processBuilder.start();

            BufferedReader inputReader = problemIO.readInput(testCaseNumber);
            OutputStream processInput = process.getOutputStream();

            String line;
            while ((line = inputReader.readLine()) != null) {
                // Write the input data to the process
                processInput.write(line.getBytes());
                processInput.write('\n');
            }

            // Close the input stream
            processInput.close();

            // Wait for the process to complete
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                if (checker.matchMock()) {
                    return ExecutorRunResult.accepted(process.getInputStream());
                } else {
                    return ExecutorRunResult.notAccepted(process.getInputStream());
                }
            } else {
                return ExecutorRunResult.runtimeError(process.getErrorStream());
            }
        } catch (IOException | InterruptedException e) {
            return ExecutorRunResult.opsError();
        }
    }

    @Override
    public int getNumberOfTestCases() {
        return this.problemIO.getNumberOfTestCases();
    }

    @Override
    public void clean() {
    }

    @Override
    public void dispose() {
    }

}
