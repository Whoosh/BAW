package logic.query.exceptions;

import java.io.IOException;

public class EmptySourceDetectException extends IOException {

    public EmptySourceDetectException(String message, Class<?> clazz) {
        super(message + " in " + clazz.getCanonicalName());
    }

    public EmptySourceDetectException(String message) {
        super(message);
    }
}