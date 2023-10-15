package com.example.CodeExecutor.problemio;

import com.example.shared.kafka.dto.RunSolutionDTO;

public class ProblemIOFactory {
    public static ProblemIO getInstance(RunSolutionDTO runSolutionDTO) {
        return new FromRunSolutionDTOProblemIO(runSolutionDTO);
    }

}
