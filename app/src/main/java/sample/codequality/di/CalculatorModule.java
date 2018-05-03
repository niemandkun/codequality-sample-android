package sample.codequality.di;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.internal.schedulers.IoScheduler;
import io.reactivex.schedulers.Schedulers;
import sample.codequality.domain.calculator.Calculator;
import sample.codequality.domain.calculator.CalculatorImpl;
import sample.codequality.domain.CalculatorInputUseCase;
import sample.codequality.domain.calculator.Operator;
import sample.codequality.domain.facts.FactRepository;
import sample.codequality.domain.GetFactUseCase;
import sample.codequality.repository.FactRepositoryImpl;
import sample.codequality.view.CalculatorViewModel;

@Module
public class CalculatorModule {
    @Provides
    @NonNull
    @Singleton
    FactRepository provideFactRepository() {
        return new FactRepositoryImpl();
    }

    @Provides
    @NonNull
    @Singleton
    IoScheduler provideIoScheduler() {
        return (IoScheduler) Schedulers.io();
    }

    @Provides
    @NonNull
    @Singleton
    GetFactUseCase provideGetFactUseCase(@NonNull FactRepository factRepository, @NonNull IoScheduler scheduler) {
        return new GetFactUseCase(factRepository, scheduler);
    }

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
    Executor provideExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    @Provides
    @NonNull
    @Singleton
    CalculatorInputUseCase provideCalculatorInputUseCase(@NonNull Executor executor) {
        return new CalculatorInputUseCase(provideCalculator(), executor);
    }

    @Provides
    @NonNull
    @Singleton
    CalculatorViewModel provideCalculatorViewModel(
            @NonNull CalculatorInputUseCase calculatorInputUseCase,
            @NonNull GetFactUseCase getFactUseCase
    ) {
        return new CalculatorViewModel(calculatorInputUseCase, getFactUseCase);
    }

    @Provides
    @NonNull
    @Singleton
    ViewModelFactory provideViewModelFactory(@NonNull CalculatorViewModel calculatorViewModel) {
        return new ViewModelFactory(calculatorViewModel);
    }
}
