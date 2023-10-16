package com.example.CodeExecutor.executor;

import com.example.CodeExecutor.checker.CheckerFactory;
import com.example.CodeExecutor.problemio.ProblemIOFactory;
import com.example.shared.kafka.dto.RunSolutionDTO;

public class ExecutorFactory {
    public static Executor getInstance(RunSolutionDTO runSolutionDTO) {
        if (runSolutionDTO.getLanguageSlug().equalsIgnoreCase("py38")) {
            return getPythonInstance(runSolutionDTO);
        } else {
            return getMockInstance(runSolutionDTO);
        }
    }

    private static Executor getMockInstance(RunSolutionDTO runSolutionDTO) {
        return new MockExecutor(ProblemIOFactory.getInstance(runSolutionDTO));
    }

    private static Executor getPythonInstance(RunSolutionDTO runSolutionDTO) {
        return new PythonUnSafeExecutor(
                runSolutionDTO,
                ProblemIOFactory.getInstance(runSolutionDTO),
                CheckerFactory.getInstance(runSolutionDTO));
    }
}
