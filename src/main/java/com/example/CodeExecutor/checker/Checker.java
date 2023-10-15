package com.example.CodeExecutor.checker;

import java.util.Scanner;

public interface Checker {
    boolean match(Scanner expectedOutput, Scanner programOutput);
}
