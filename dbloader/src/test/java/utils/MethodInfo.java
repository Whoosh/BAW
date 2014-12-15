package utils;


import logic.parsers.methods.markers.OnStringFinder;

public class MethodInfo implements OnStringFinder {

    private final static OnStringFinder INFO = new MethodInfo();

    public static String getCurrentMethodName() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String fullPath = stackTrace[2].toString();
        return INFO.lastBetween(fullPath, ".", "(");
    }
}
