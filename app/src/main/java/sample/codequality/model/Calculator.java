package sample.codequality.model;

import android.support.annotation.NonNull;

public interface Calculator {
    void appendNumber(@NonNull String number);

    void appendComma();

    void appendOperator(@NonNull String operator);

    void undo();

    void clear();

    void execute();
}
