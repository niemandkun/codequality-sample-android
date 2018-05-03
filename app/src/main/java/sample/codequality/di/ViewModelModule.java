package sample.codequality.di;

import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import sample.codequality.domain.CalculatorInputUseCase;
import sample.codequality.domain.GetFactUseCase;
import sample.codequality.view.CalculatorViewModel;

@Module
public class ViewModelModule {
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
