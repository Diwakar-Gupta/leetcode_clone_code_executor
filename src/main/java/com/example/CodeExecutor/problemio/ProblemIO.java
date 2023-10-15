package com.example.CodeExecutor.problemio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public interface ProblemIO {
    int getNumberOfTestCases();

    BufferedReader readInput(int testCaseNumber);

    BufferedReader readOutput(int testCaseNumber);

    default void copyToFile(BufferedReader sourceReader, File destinationFile) throws IOException {
        BufferedWriter destinationWriter = new BufferedWriter(new FileWriter(destinationFile));

        char[] buffer = new char[4096]; // 4KB buffer size (adjust as needed)

        int bytesRead;
        while ((bytesRead = sourceReader.read(buffer)) != -1) {
            destinationWriter.write(buffer, 0, bytesRead);
        }

        // Close the readers and writers
        sourceReader.close();
        destinationWriter.close();
    }
}
