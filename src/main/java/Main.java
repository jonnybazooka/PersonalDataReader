import exceptions.FileExtensionNotRecognizedException;
import io.FileProcessor;
import io.FileProcessorFactory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please enter a path to a file to process.");
            String path = new Scanner(System.in).nextLine();
            try {
                LocalDateTime start = LocalDateTime.now();
                System.out.println("Processing file " + path + " ...");
                FileProcessor fileProcessor = FileProcessorFactory.getFileProcessor(path);
                fileProcessor.processFile(path);
                LocalDateTime end = LocalDateTime.now();
                System.out.println("File processed in " + Duration.between(start, end).getSeconds() + " s");
            } catch (FileExtensionNotRecognizedException e) {
                System.out.println(e.getMessage());
            }
        } else {
            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("-in")) {
                    try {
                        LocalDateTime start = LocalDateTime.now();
                        System.out.println("Processing file " + args[i+1] + " ...");
                        FileProcessor fileProcessor = FileProcessorFactory.getFileProcessor(args[i+1]);
                        fileProcessor.processFile(args[i+1]);
                        LocalDateTime end = LocalDateTime.now();
                        System.out.println("File processed in " + Duration.between(start, end).getSeconds() + " s");
                    } catch (FileExtensionNotRecognizedException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
    }
}
