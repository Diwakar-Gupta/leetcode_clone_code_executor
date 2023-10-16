package com.example.CodeExecutor.checker;

import java.util.Scanner;

public interface Checker {
    default boolean matchMock() {
        return Math.random() < 0.5;
    }

    boolean match(Scanner expectedOutput, Scanner programOutput);
}
