package com.example.CodeExecutor;

import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.Test;

import com.example.shared.kafka.dto.RunCodeUpdateDTO;
import com.example.shared.kafka.dto.RunSolutionDTO;

class CodeExecutorApplicationTests {

	@Test
	void testRunner() {
		RunSolutionDTO runSolutionDTO = new RunSolutionDTO();

		runSolutionDTO.setUuid("random uuid");
		runSolutionDTO.setCreatedAt(new Date());
		runSolutionDTO.setProblemId(2L);
		runSolutionDTO.setLanguageSlug("py38");
		runSolutionDTO.setCode("print('Got input:', input())");
		runSolutionDTO.setTestCases(new ArrayList<RunSolutionDTO.TestCase>());
		runSolutionDTO.getTestCases().add(new RunSolutionDTO.TestCase(0L, "sample input 1"));
		runSolutionDTO.getTestCases().add(new RunSolutionDTO.TestCase(1L, "sample input 2"));

		RunCodeUpdateDTOBuilder runCodeUpdateDTOBuilder = new RunCodeUpdateDTOBuilder(runSolutionDTO.getUuid(),
				(RunCodeUpdateDTO update) -> {
					System.out.println(update.getTestCases());
				}, (RunCodeUpdateDTO update) -> {
					// codeExecutionUpdateDTO.send(KafkaConstant.CODE_EXECUTION_UPDATE_TOPIC_NAME,
					// update);
				});

		new Runner(runSolutionDTO, runCodeUpdateDTOBuilder).run();
	}

}
