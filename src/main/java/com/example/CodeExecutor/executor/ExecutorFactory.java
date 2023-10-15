package com.example.CodeExecutor.executor;

import com.example.CodeExecutor.checker.CheckerFactory;
import com.example.CodeExecutor.problemio.ProblemIOFactory;
import com.example.shared.kafka.dto.RunSolutionDTO;

public class ExecutorFactory {
    public static Executor getInstance(RunSolutionDTO runSolutionDTO) {
        if (runSolutionDTO.getLanguageSlug().equalsIgnoreCase("py38")) {
            return getPythonInstance(runSolutionDTO);
        }
        return new MockExecutor(runSolutionDTO);
    }

    private static Executor getPythonInstance(RunSolutionDTO runSolutionDTO) {
        new PythonUnSafeExecutor(
                runSolutionDTO,
                ProblemIOFactory.getInstance(runSolutionDTO),
                CheckerFactory.getInstance(runSolutionDTO));
        return null;
    }
}
