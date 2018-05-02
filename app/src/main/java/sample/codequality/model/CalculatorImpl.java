package sample.codequality.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Map;
import java.util.Stack;

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
    private final Stack<StringBuilder> mInputStack;

    public CalculatorImpl(@NonNull Map<String, Operator> operators) {
        mOperators = operators;
        mFirstOperand = new StringBuilder();
        mSecondOperand = new StringBuilder();
        mOperator = new StringBuilder();
        mResult = new StringBuilder();
        mInputStack = new Stack<>();
        mInputStack.push(mFirstOperand);
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
        mInputStack.peek().append(number);
    }

    @Override
    public void appendComma() {
        prepareToInputOperand();
        @NonNull StringBuilder input = mInputStack.peek();
        StringUtils.appendUnique(input, ".");
    }

    @Override
    public void appendOperator(@NonNull String operator) {
        prepareToInputOperator();
        mOperator.append(operator);
        mInputStack.push(mOperator);
        mInputStack.push(mSecondOperand);
    }

    @Override
    public void undo() {
        StringBuilder lastInput = findLastInput();
        if (lastInput == mResult) {
            clear();
            return;
        }
        int lastInputLength = lastInput.length();
        if (lastInputLength == 0) {
            return;
        }
        lastInput.setLength(lastInputLength - 1);
        if (lastInput.length() == 0 && mInputStack.size() > 1) {
            mInputStack.pop();
        }
    }

    @Override
    public void execute() {
        if (mResult.length() > 0) {
            return;
        }
        if (mInputStack.peek() == mFirstOperand) {
            if (mFirstOperand.length() == 0) {
                return;
            }
            mResult.append(StringUtils.parseDoubleSafely(mFirstOperand.toString()));
            mInputStack.push(mResult);
            return;
        }
        if (mInputStack.peek() != mSecondOperand) {
            throw new IllegalStateException();
        }
        double firstOperand = StringUtils.parseDoubleSafely(mFirstOperand.toString());
        double secondOperand = StringUtils.parseDoubleSafely(mSecondOperand.toString());
        @Nullable Operator operator = mOperators.get(mOperator.toString());
        if (operator == null) {
            throw new NoSuchOperatorException();
        }
        mResult.append(String.valueOf(operator.apply(firstOperand, secondOperand)));
        mInputStack.push(mResult);
    }

    private void prepareToInputOperand() {
        @NonNull StringBuilder input = mInputStack.peek();
        if (input == mOperator) {
            throw new IllegalStateException();
        }
        if (input == mResult) {
            clear();
        }
    }

    private void prepareToInputOperator() {
        @NonNull StringBuilder input = mInputStack.peek();
        if (input == mOperator) {
            throw new IllegalStateException();
        }
        if (input == mSecondOperand) {
            if (input.length() == 0) {
                mInputStack.pop();
                mOperator.setLength(0);
                return;
            }
            execute();
            input = mInputStack.peek();
        }
        if (input == mResult) {
            @NonNull String result = mResult.toString();
            clear();
            mFirstOperand.append(result);
        }
    }

    @Override
    public void clear() {
        mFirstOperand.setLength(0);
        mSecondOperand.setLength(0);
        mOperator.setLength(0);
        mResult.setLength(0);
        mInputStack.clear();
        mInputStack.push(mFirstOperand);
    }

    @NonNull
    private StringBuilder findLastInput() {
        StringBuilder lastInput = mInputStack.peek();
        while (lastInput.length() == 0 && mInputStack.size() > 1) {
            mInputStack.pop();
            lastInput = mInputStack.peek();
        }
        return lastInput;
    }
}
