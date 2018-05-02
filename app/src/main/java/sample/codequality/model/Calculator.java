package sample.codequality.model;

import android.support.annotation.NonNull;

public interface Calculator {
    @NonNull
    String getFirstOperand();

    @NonNull
    String getSecondOperand();

    @NonNull
    String getOperator();

    @NonNull
    String getResult();

    void appendNumber(@NonNull String number);

    void appendComma();

    void appendOperator(@NonNull String operator);

    void undo();

    void clear();

    void execute();
}
