package io;

import exceptions.FileExtensionNotRecognizedException;
import io.impl.CSVProcessor;
import io.impl.XMLProcessor;

public class FileProcessorFactory {
    public static FileProcessor getFileProcessor(String filePath) throws FileExtensionNotRecognizedException {
        String[] split = filePath.split("\\.");
        if (split[split.length-1].equals("xml")) {
            return new XMLProcessor();
        }
        if (split[split.length-1].equals("txt") || split[split.length-1].equals("csv")) {
            return new CSVProcessor();
        }
        throw new FileExtensionNotRecognizedException("File extension not recognized by application. Passed filePath: "
                + filePath);
    }
}
