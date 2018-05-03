package sample.codequality;

import android.app.Application;
import android.support.annotation.Nullable;

import sample.codequality.di.ApplicationComponent;
import sample.codequality.di.CalculatorModule;
import sample.codequality.di.DaggerApplicationComponent;
import sample.codequality.di.ViewModelFactory;

public class CalculatorApplication extends Application {
    @Nullable
    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationComponent = DaggerApplicationComponent.builder()
                .calculatorModule(new CalculatorModule())
                .build();
    }

    @Nullable
    public ViewModelFactory getViewModelFactory() {
        if (mApplicationComponent == null) {
            throw new IllegalStateException("Dagger component is not initialized. Something went wrong.");
        }
        return mApplicationComponent.getViewModelFactory();
    }
}
