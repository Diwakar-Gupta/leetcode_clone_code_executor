package com.example.CodeExecutor.checker;

import java.util.Scanner;

public class ExactMatchChecker implements Checker {

    @Override
    public boolean match(Scanner expectedOutput, Scanner programOutput) {
        while (expectedOutput.hasNextLine() && programOutput.hasNextLine()) {
            if (!expectedOutput.nextLine().equals(programOutput.nextLine())) {
                return false;
            }
        }
        return expectedOutput.hasNext() == programOutput.hasNext();
    }

}
