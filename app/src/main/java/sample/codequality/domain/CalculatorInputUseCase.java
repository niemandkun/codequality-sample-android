package sample.codequality.domain;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import javax.inject.Inject;

import sample.codequality.domain.calculator.Calculator;
import sample.codequality.view.numpad.NumpadButton;

public class CalculatorInputUseCase {
    @NonNull
    private final Calculator mCalculator;

    @NonNull
    private final ExecutorService mExecutor;

    @Inject
    public CalculatorInputUseCase(@NonNull Calculator calculator, @NonNull ExecutorService executor) {
        mCalculator = calculator;
        mExecutor = executor;
    }

    @NonNull
    public Future handleInput(@NonNull NumpadButton numpadButton, @NonNull Callback callback) {
        return mExecutor.submit(() -> {
            try {
                dispatchInput(numpadButton, mCalculator);
                callback.onSuccess(renderCalculatorText(mCalculator));
            } catch (Throwable throwable) {
                callback.onError(throwable);
            }
        });
    }

    public void evaluateExpression(@NonNull EvaluationCallback callback) {
        new EvaluateCalculatorTask(mCalculator, callback).execute();
    }

    private static void dispatchInput(@NonNull NumpadButton numpadButton, @NonNull Calculator calculator) {
        switch (numpadButton) {
            case UNDO:
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

    private static class EvaluateCalculatorTask extends AsyncTask<Void, Void, Void> {
        @NonNull
        private final WeakReference<Calculator> mCalculator;

        @NonNull
        private final WeakReference<EvaluationCallback> mCallback;

        private EvaluateCalculatorTask(@NonNull Calculator calculator, @NonNull EvaluationCallback callback) {
            mCalculator = new WeakReference<>(calculator);
            mCallback = new WeakReference<>(callback);
        }

        @Override
        protected Void doInBackground(@NonNull Void... ignored) {
            Calculator calculator = mCalculator.get();
            EvaluationCallback callback = mCallback.get();
            try {
                calculator.evaluate();
                Double result = calculator.getResult().isEmpty()
                        ? null
                        : Double.parseDouble(calculator.getResult());
                callback.onSuccess(renderCalculatorText(calculator), result);
            } catch (Throwable throwable) {
                callback.onError(throwable);
            }
            return null;
        }
    }

    public interface Callback {
        void onSuccess(@NonNull String displayText);

        void onError(@NonNull Throwable throwable);
    }

    public interface EvaluationCallback {
        void onSuccess(@NonNull String displayText, @Nullable Double evaluationResult);

        void onError(@NonNull Throwable throwable);
    }
}
