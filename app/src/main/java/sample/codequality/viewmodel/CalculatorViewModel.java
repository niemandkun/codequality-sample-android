package sample.codequality.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

import sample.codequality.model.Calculator;
import sample.codequality.model.CalculatorImpl;
import sample.codequality.model.Operator;
import sample.codequality.numpad.NumpadButton;

public class CalculatorViewModel extends ViewModel {
    @NonNull
    private final MutableLiveData<String> mDisplayText;

    @NonNull
    private final MutableLiveData<String> mFactText;

    @NonNull
    private final Calculator mCalculator;

    public CalculatorViewModel() {
        mDisplayText = new MutableLiveData<>();
        mFactText = new MutableLiveData<>();

        Map<String, Operator> operators = new HashMap<>();

        operators.put(NumpadButton.ADD.getButtonLabel(), (left, right) -> left + right);
        operators.put(NumpadButton.SUBTRACT.getButtonLabel(), (left, right) -> left - right);
        operators.put(NumpadButton.MULTIPLY.getButtonLabel(), (left, right) -> left * right);
        operators.put(NumpadButton.DIVIDE.getButtonLabel(), (left, right) -> left / right);

        mCalculator = new CalculatorImpl(operators);
    }

    @NonNull
    public MutableLiveData<String> getDisplayText() {
        return mDisplayText;
    }

    @NonNull
    public MutableLiveData<String> getFactText() {
        return mFactText;
    }

    public void onNumpadButtonPressed(@NonNull NumpadButton button) {
        switch (button) {
            case DEL:
                mCalculator.undo();
                break;
            case COMMA:
                mCalculator.appendComma();
                break;
            case ADD:
            case SUBTRACT:
            case MULTIPLY:
            case DIVIDE:
                mCalculator.appendOperator(button.getButtonLabel());
                break;
            default:
                mCalculator.appendNumber(button.getButtonLabel());
                break;
        }
        renderCalculatorText();
    }

    public void onEqualsButtonPressed() {
        mCalculator.execute();
        renderCalculatorText();
        mFactText.postValue(mCalculator.getResult());
    }

    private void renderCalculatorText() {
        StringBuilder text = new StringBuilder();
        String firstOperand = mCalculator.getFirstOperand();
        if (!firstOperand.isEmpty()) {
            text.append(firstOperand);
        }
        String operator = mCalculator.getOperator();
        if (!operator.isEmpty()) {
            text.append(" ").append(operator);
        }
        String secondOperand = mCalculator.getSecondOperand();
        if (!secondOperand.isEmpty()) {
            text.append(" ").append(secondOperand);
        }
        String result = mCalculator.getResult();
        if (!result.isEmpty()) {
            text.append("\n= ").append(result);
        }
        mDisplayText.postValue(text.toString());
    }
}
