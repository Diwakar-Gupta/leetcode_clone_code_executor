package com.example.CodeExecutor.executor;

import com.example.CodeExecutor.RunCodeUpdateDTOBuilder;

public interface Executor {
    void start();

    void prepare();

    void run(RunCodeUpdateDTOBuilder runCodeUpdateDTOBuilder);

    void clean();

    void dispose();
}
