package com.example.CodeExecutor.executor;

import com.example.CodeExecutor.problemio.ProblemIO;
import java.io.ByteArrayInputStream;

import java.util.Random;

public class MockExecutor implements Executor {

    private ProblemIO problemIO;

    public MockExecutor(ProblemIO problemIO) {
        this.problemIO = problemIO;
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
        return new ExecutorRunResult(ExecutorRunResult.Status.CompilationSuccess, null, null);
    }

    @Override
    public ExecutorRunResult prepareAndRun(int testCaseNumber) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int type = new Random().nextInt(3);

        if (type == 0) {
            return ExecutorRunResult.accepted(new ByteArrayInputStream("mock output".getBytes()));
        } else if (type == 1) {
            return ExecutorRunResult.notAccepted(new ByteArrayInputStream("mock output".getBytes()));
        } else {
            return ExecutorRunResult.runtimeError(new ByteArrayInputStream("mock error output".getBytes()));
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
