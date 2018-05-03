package sample.codequality.di;

import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        CalculatorModule.class,
        ViewModelModule.class,
        FactModule.class
})
public interface ApplicationComponent {
    @NonNull
    ViewModelFactory getViewModelFactory();
}
