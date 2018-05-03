package sample.codequality;

import android.app.Application;
import android.support.annotation.Nullable;

import sample.codequality.di.CalculatorComponent;
import sample.codequality.di.CalculatorModule;
import sample.codequality.di.DaggerCalculatorComponent;
import sample.codequality.di.ViewModelFactory;

public class CalculatorApplication extends Application {
    @Nullable
    private CalculatorComponent mCalculatorComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mCalculatorComponent = DaggerCalculatorComponent.builder()
                .calculatorModule(new CalculatorModule())
                .build();
    }

    @Nullable
    public ViewModelFactory getViewModelFactory() {
        if (mCalculatorComponent == null) {
            throw new IllegalStateException("Dagger component is not initialized. Something went wrong.");
        }
        return mCalculatorComponent.getViewModelFactory();
    }
}
