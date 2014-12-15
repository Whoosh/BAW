package logic.query.exceptions;

public class LoadPhotoException extends EmptySourceDetectException {

    public LoadPhotoException(String message, Class<?> clazz) {
        super(message,clazz);
    }
}
