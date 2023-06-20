package com.example.CodeExecutor;

import com.example.shared.kafka.KafkaConstant;
import com.example.shared.kafka.dto.RunCodeUpdateDTO;
import com.example.shared.kafka.dto.RunSolutionDTO;
import com.example.shared.kafka.dto.RunCodeUpdateDTO.Runtime.RuntimeStatus;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Component
public class RunCodeListener {
    @Autowired
    public KafkaTemplate<String, RunCodeUpdateDTO> codeExecutionUpdateDTO;

    @KafkaListener(groupId = KafkaConstant.GROUP_ID_RUN_CODE_CONSUMER,topics = KafkaConstant.CODE_RUN_TOPIC_NAME, containerFactory = KafkaConstant.KAFKA_RUN_CODE_CONSUMER_CONTAINER_FACTORY)
    public void receivedCodeUpdate(RunSolutionDTO runSolutionDTO) throws JsonProcessingException, InterruptedException {
        System.out.println("Code Run Request for uuid: "+runSolutionDTO.getUuid());

        Thread.sleep(10000);

        RunCodeUpdateDTO response = new RunCodeUpdateDTO();
        response.setUuid(runSolutionDTO.getUuid());
        
        response.setPreprocessing(new RunCodeUpdateDTO.Preprocessing(
            RunCodeUpdateDTO.Preprocessing.PreprocessingStatus.Accepted,
            null,
            null
        ));
        codeExecutionUpdateDTO.send(KafkaConstant.CODE_EXECUTION_UPDATE_TOPIC_NAME, response);        
        
        if(runSolutionDTO.getTestCases() != null){
            for(RunSolutionDTO.TestCase testcase: runSolutionDTO.getTestCases()){

                RunCodeUpdateDTO.TestCase.TestCaseStatus testCaseStatus = RunCodeUpdateDTO.TestCase.TestCaseStatus.values()[new Random().nextInt(RunCodeUpdateDTO.TestCase.TestCaseStatus.values().length)];
                RunCodeUpdateDTO.Runtime.RuntimeStatus runtimeStatus = RunCodeUpdateDTO.Runtime.RuntimeStatus.values()[new Random().nextInt(RunCodeUpdateDTO.Runtime.RuntimeStatus.values().length)];
                
                response.addTestCase(
                    new RunCodeUpdateDTO.TestCase(
                        testcase.getId(),
                        testCaseStatus,
                        null,
                        new RunCodeUpdateDTO.Runtime(
                            runtimeStatus,
                            runtimeStatus==RuntimeStatus.Error?"array index out of bound": null,
                            runtimeStatus==RuntimeStatus.Error?"array index out of bound at line 89": null
                        )
                    )
                );
        
                Thread.sleep(1000);
                codeExecutionUpdateDTO.send(KafkaConstant.CODE_EXECUTION_UPDATE_TOPIC_NAME, response);
            }
        }

        Thread.sleep(1000);
        response.setJudgement(RunCodeUpdateDTO.Judgement.values()[new Random().nextInt(RunCodeUpdateDTO.Judgement.values().length)]);
        codeExecutionUpdateDTO.send(KafkaConstant.CODE_EXECUTION_UPDATE_TOPIC_NAME, response);        
    }
}
