package sample.codequality.domain.calculator;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.internal.schedulers.ComputationScheduler;
import sample.codequality.view.numpad.NumpadButton;

public class CalculatorInputUseCase {
    @NonNull
    private final Calculator mCalculator;

    @NonNull
    private final ComputationScheduler mComputationScheduler;

    @Inject
    public CalculatorInputUseCase(@NonNull Calculator calculator, @NonNull ComputationScheduler computationScheduler) {
        mCalculator = calculator;
        mComputationScheduler = computationScheduler;
    }

    @NonNull
    public Single<String> handleInput(@NonNull NumpadButton numpadButton) {
        return Single.fromCallable(() -> {
            dispatchInput(numpadButton, mCalculator);
            return renderCalculatorText(mCalculator);
        }).subscribeOn(mComputationScheduler);
    }

    @NonNull
    public Single<String> evaluateExpression() {
        return Single.fromCallable(() -> {
            mCalculator.execute();
            return renderCalculatorText(mCalculator);
        }).subscribeOn(mComputationScheduler);
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
}
