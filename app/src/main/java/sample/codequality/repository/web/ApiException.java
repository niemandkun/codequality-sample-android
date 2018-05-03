package sample.codequality.repository.web;

import android.support.annotation.NonNull;

public class ApiException extends RuntimeException {
    public ApiException(@NonNull String message) {
        super(message);
    }
}
