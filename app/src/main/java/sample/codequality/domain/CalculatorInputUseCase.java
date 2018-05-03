package sample.codequality.domain;

import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

import javax.inject.Inject;

import sample.codequality.domain.calculator.Calculator;
import sample.codequality.view.numpad.NumpadButton;

public class CalculatorInputUseCase {
    @NonNull
    private final Calculator mCalculator;

    @NonNull
    private final Executor mExecutor;

    @Inject
    public CalculatorInputUseCase(@NonNull Calculator calculator, @NonNull Executor executor) {
        mCalculator = calculator;
        mExecutor = executor;
    }

    public void handleInput(@NonNull NumpadButton numpadButton, @NonNull Callback callback) {
        mExecutor.execute(() -> {
            try {
                dispatchInput(numpadButton, mCalculator);
                callback.onSuccess(renderCalculatorText(mCalculator));
            } catch (Throwable throwable) {
                callback.onError(throwable);
            }
        });
    }

    public void evaluateExpression(@NonNull EvaluationCallback callback) {
        mExecutor.execute(() -> {
            try {
                mCalculator.evaluate();
                callback.onSuccess(renderCalculatorText(mCalculator), Double.parseDouble(mCalculator.getResult()));
            } catch (Throwable throwable) {
                callback.onError(throwable);
            }
        });
    }

    private static void dispatchInput(@NonNull NumpadButton numpadButton, @NonNull Calculator calculator) {
        switch (numpadButton) {
            case DEL:
                calculator.undo();
                break;
            case COMMA:
                calculator.appendComma();
                break;
            case ADD:
            case SUBTRACT:
            case MULTIPLY:
            case DIVIDE:
                calculator.appendOperator(numpadButton.getButtonLabel());
                break;
            default:
                calculator.appendNumber(numpadButton.getButtonLabel());
                break;
        }
    }

    @NonNull
    private static String renderCalculatorText(@NonNull Calculator calculator) {
        StringBuilder text = new StringBuilder();
        String firstOperand = calculator.getFirstOperand();
        if (!firstOperand.isEmpty()) {
            text.append(firstOperand);
        }
        String operator = calculator.getOperator();
        if (!operator.isEmpty()) {
            text.append(" ").append(operator);
        }
        String secondOperand = calculator.getSecondOperand();
        if (!secondOperand.isEmpty()) {
            text.append(" ").append(secondOperand);
        }
        String result = calculator.getResult();
        if (!result.isEmpty()) {
            text.append("\n= ").append(result);
        }
        return text.toString();
    }

    public interface Callback {
        void onSuccess(@NonNull String displayText);

        void onError(@NonNull Throwable throwable);
    }

    public interface EvaluationCallback {
        void onSuccess(@NonNull String displayText, double evaluationResult);

        void onError(@NonNull Throwable throwable);
    }
}
