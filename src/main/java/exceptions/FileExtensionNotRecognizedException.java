package exceptions;

public class FileExtensionNotRecognizedException extends Throwable {

    public FileExtensionNotRecognizedException(String message) {
        System.out.println(message);
    }
}
