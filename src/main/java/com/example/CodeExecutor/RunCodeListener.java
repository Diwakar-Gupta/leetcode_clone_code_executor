package com.example.CodeExecutor;

import com.example.shared.kafka.KafkaConstant;
import com.example.shared.kafka.dto.RunCodeUpdateDTO;
import com.example.shared.kafka.dto.RunSolutionDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class RunCodeListener {
    @Autowired
    public KafkaTemplate<String, RunCodeUpdateDTO> codeExecutionUpdateDTO;

    @KafkaListener(groupId = KafkaConstant.GROUP_ID_RUN_CODE_CONSUMER, topics = KafkaConstant.CODE_RUN_TOPIC_NAME, containerFactory = KafkaConstant.KAFKA_RUN_CODE_CONSUMER_CONTAINER_FACTORY)
    public void receivedCodeUpdate(RunSolutionDTO runSolutionDTO) throws JsonProcessingException {
        System.out.println("Code Run Request for uuid: " + runSolutionDTO.getUuid());

        RunCodeUpdateDTOBuilder runCodeUpdateDTOBuilder = new RunCodeUpdateDTOBuilder(runSolutionDTO.getUuid(),
                (RunCodeUpdateDTO update) -> {
                    codeExecutionUpdateDTO.send(KafkaConstant.CODE_EXECUTION_UPDATE_TOPIC_NAME, update);
                }, (RunCodeUpdateDTO update) -> {
                    // codeExecutionUpdateDTO.send(KafkaConstant.CODE_EXECUTION_UPDATE_TOPIC_NAME,
                    // update);
                });

        new Runner(runSolutionDTO, runCodeUpdateDTOBuilder).run();
    }
}
