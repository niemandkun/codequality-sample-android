package sample.codequality.di;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import sample.codequality.domain.CalculatorInputUseCase;
import sample.codequality.domain.calculator.Calculator;
import sample.codequality.domain.calculator.CalculatorImpl;
import sample.codequality.domain.calculator.Operator;

@Module
public class CalculatorModule {
    @Provides
    @NonNull
    @Singleton
    Calculator provideCalculator() {
        Map<String, Operator> operators = new HashMap<>();
        operators.put("+", (left, right) -> left + right);
        operators.put("-", (left, right) -> left - right);
        operators.put("*", (left, right) -> left * right);
        operators.put("/", (left, right) -> left / right);
        return new CalculatorImpl(operators);
    }

    @Provides
    @NonNull
    @Singleton
    ExecutorService provideExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    @Provides
    @NonNull
    @Singleton
    CalculatorInputUseCase provideCalculatorInputUseCase(
            @NonNull Calculator calculator,
            @NonNull ExecutorService executor
    ) {
        return new CalculatorInputUseCase(calculator, executor);
    }
}
