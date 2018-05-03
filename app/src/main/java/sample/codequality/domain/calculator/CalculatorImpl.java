package sample.codequality.domain.calculator;

import android.support.annotation.NonNull;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import sample.codequality.utils.StringUtils;

public class CalculatorImpl implements Calculator {
    @NonNull
    private final Map<String, Operator> mOperators;

    @NonNull
    private final StringBuilder mFirstOperand;

    @NonNull
    private final StringBuilder mSecondOperand;

    @NonNull
    private final StringBuilder mOperator;

    @NonNull
    private final StringBuilder mResult;

    @NonNull
    private StringBuilder mCurrentInput;

    public CalculatorImpl(@NonNull Map<String, Operator> operators) {
        mOperators = operators;
        mFirstOperand = new StringBuilder();
        mSecondOperand = new StringBuilder();
        mOperator = new StringBuilder();
        mResult = new StringBuilder();
        mCurrentInput = mFirstOperand;
    }

    @NonNull
    @Override
    public String getFirstOperand() {
        return mFirstOperand.toString();
    }

    @NonNull
    @Override
    public String getSecondOperand() {
        return mSecondOperand.toString();
    }

    @NonNull
    @Override
    public String getOperator() {
        return mOperator.toString();
    }

    @NonNull
    @Override
    public String getResult() {
        return mResult.toString();
    }

    @Override
    public String toString() {
        return "CalculatorImpl {"
                + "mFirstOperand=\"" + mFirstOperand.toString() + "\","
                + "mOperator=\"" + mOperator.toString() + "\","
                + "mSecondOperand=\"" + mSecondOperand.toString() + "\","
                + "mResult=\"" + mResult.toString() + "\",";
    }

    @Override
    public void appendNumber(@NonNull String number) {
        prepareToInputOperand();
        mCurrentInput.append(number);
    }

    @Override
    public void appendComma() {
        prepareToInputOperand();
        StringUtils.appendUnique(mCurrentInput, ".");
    }

    private void prepareToInputOperand() {
        if (mCurrentInput == mOperator) {
            throw new IllegalStateException();
        }
        if (mCurrentInput == mResult) {
            clear();
        }
    }

    @Override
    public void appendOperator(@NonNull String operator) {
        if (mCurrentInput == mOperator) {
            throw new IllegalStateException();
        }
        if (mCurrentInput == mSecondOperand) {
            if (mSecondOperand.length() == 0) {
                mOperator.setLength(0);
            } else {
                evaluate();
            }
        }
        if (mCurrentInput == mResult) {
            shift();
        }
        mOperator.append(operator);
        mCurrentInput = mSecondOperand;
    }

    private void shift() {
        String result = mResult.toString();
        clear();
        mFirstOperand.append(result);
    }

    @Override
    public void undo() {
        mCurrentInput = findLastNotEmptyInput();
        if (mCurrentInput == mResult) {
            clear();
            return;
        }
        int currentInputLength = mCurrentInput.length();
        if (currentInputLength == 0) {
            return;
        }
        mCurrentInput.setLength(currentInputLength - 1);
        if (mCurrentInput.length() == 0 && mCurrentInput == mOperator) {
            mCurrentInput = mFirstOperand;
        }
    }

    @Override
    public void evaluate() {
        if (mResult.length() > 0) {
            return;
        }
        if (mCurrentInput == mFirstOperand) {
            if (mFirstOperand.length() == 0) {
                return;
            }
            mResult.append(StringUtils.parseDoubleSafely(mFirstOperand.toString()));
            mCurrentInput = mResult;
            return;
        }
        if (mCurrentInput != mSecondOperand) {
            throw new IllegalStateException();
        }
        double firstOperand = StringUtils.parseDoubleSafely(mFirstOperand.toString());
        double secondOperand = StringUtils.parseDoubleSafely(mSecondOperand.toString());
        Operator operator = mOperators.get(mOperator.toString());
        if (operator == null) {
            throw new NoSuchOperatorException();
        }
        mResult.append(String.valueOf(operator.apply(firstOperand, secondOperand)));
        mCurrentInput = mResult;
    }

    @Override
    public void clear() {
        mFirstOperand.setLength(0);
        mSecondOperand.setLength(0);
        mOperator.setLength(0);
        mResult.setLength(0);
        mCurrentInput = mFirstOperand;
    }

    @NonNull
    private StringBuilder findLastNotEmptyInput() {
        List<StringBuilder> inputsInOrder = Arrays.asList(
                mResult,
                mSecondOperand,
                mOperator,
                mFirstOperand
        );
        for (StringBuilder input : inputsInOrder) {
            if (input.length() > 0) {
                return input;
            }
        }
        return mFirstOperand;
    }
}
