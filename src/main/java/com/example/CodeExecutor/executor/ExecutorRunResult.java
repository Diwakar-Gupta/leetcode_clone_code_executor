package com.example.CodeExecutor.executor;

import java.io.InputStream;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExecutorRunResult {
    private Status status;
    private InputStream outputStream;
    private InputStream errorStream;

    public static enum Status {
        Accepted,
        NotAccepted,
        RunTimeError,
        CompilationSuccess,
        CompilationError,
        NotAbleToRun,
    }

    public boolean isPreprocessingFailed() {
        return status.equals(Status.CompilationError);
    }

    public boolean isTestCase() {
        if (this.status.equals(Status.Accepted) || this.status.equals(Status.NotAbleToRun)
                || this.status.equals(Status.RunTimeError)) {
            return true;
        } else {
            return false;
        }
    }

    public static ExecutorRunResult accepted(InputStream outputStream) {
        return new ExecutorRunResult(Status.Accepted, outputStream, null);
    }

    public static ExecutorRunResult notAccepted(InputStream outputStream) {
        return new ExecutorRunResult(Status.NotAccepted, outputStream, null);
    }

    public static ExecutorRunResult runtimeError(InputStream errorStream) {
        return new ExecutorRunResult(Status.RunTimeError, null, errorStream);
    }

    public static ExecutorRunResult opsError() {
        return new ExecutorRunResult(Status.NotAbleToRun, null, null);
    }

    public static ExecutorRunResult compilationSuccess() {
        return new ExecutorRunResult(Status.CompilationSuccess, null, null);
    }

    public static ExecutorRunResult compilationError(InputStream errorStream) {
        return new ExecutorRunResult(Status.CompilationError, null, errorStream);
    }

}
