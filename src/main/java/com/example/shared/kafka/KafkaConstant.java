package com.example.shared.kafka;

public class KafkaConstant {
    public static final String KAFKA_LOCAL_SERVER_CONFIG = "localhost:9092";
    public static final String KAFKA_CODE_UPDATE_CONSUMER_CONTAINER_FACTORY = "kafkaCodeListenerContainerFactory";
    public static final String KAFKA_RUN_CODE_CONSUMER_CONTAINER_FACTORY = "kafkaRunSolutionContainerFactory";
    public static final String CODE_EXECUTION_UPDATE_TOPIC_NAME = "CodeExecutionUpdate";
    public static final String CODE_RUN_TOPIC_NAME = "ProblemSubmittion";
    public static final String GROUP_ID_CODE_UPDATE_CONSUMER = "group-id-code-update-consumer";
    public static final String GROUP_ID_RUN_CODE_CONSUMER = "group-id-run-code-consumer";
}
