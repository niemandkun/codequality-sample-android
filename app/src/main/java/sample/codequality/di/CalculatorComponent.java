package sample.codequality.di;

import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {CalculatorModule.class})
public interface CalculatorComponent {
    @NonNull
    ViewModelFactory getViewModelFactory();
}
