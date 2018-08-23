package org.softuni.traveltogether.common.exception;

public final class ExceptionUtils {
    private ExceptionUtils(){}

    public static Throwable getRootCause(Exception e){
        Throwable cause = e;
        while (cause.getCause() != null)
            cause = cause.getCause();
        return cause;
    }

    public static String getRootCauseMessage(Exception e){
        return getRootCause(e).getMessage();
    }
}
