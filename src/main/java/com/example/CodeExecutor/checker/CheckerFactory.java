package com.example.CodeExecutor.checker;

import com.example.shared.kafka.dto.RunSolutionDTO;

public class CheckerFactory {

    public static Checker getInstance(RunSolutionDTO runSolutionDTO) {
        return new ExactMatchChecker();
    }

}
