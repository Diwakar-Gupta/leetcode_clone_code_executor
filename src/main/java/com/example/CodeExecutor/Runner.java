package com.example.CodeExecutor;

import com.example.CodeExecutor.executor.Executor;
import com.example.CodeExecutor.executor.ExecutorFactory;
import com.example.shared.kafka.dto.RunSolutionDTO;

public class Runner {

        private RunSolutionDTO runSolutionDTO;
        private RunCodeUpdateDTOBuilder runCodeUpdateDTOBuilder;

        Runner(RunSolutionDTO runSolutionDTO, RunCodeUpdateDTOBuilder runCodeUpdateDTOBuilder) {
                this.runSolutionDTO = runSolutionDTO;
                this.runCodeUpdateDTOBuilder = runCodeUpdateDTOBuilder;
        }

        public void run() {
                Executor executor = ExecutorFactory.getInstance(runSolutionDTO);
                executor.run(runCodeUpdateDTOBuilder);
        }
}
