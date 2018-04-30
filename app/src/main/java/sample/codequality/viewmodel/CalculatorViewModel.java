package sample.codequality.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

public class CalculatorViewModel extends ViewModel {
    @NonNull
    private final MutableLiveData<String> mDisplayText;

    @NonNull
    private final MutableLiveData<String> mFactText;

    public CalculatorViewModel() {
        mDisplayText = new MutableLiveData<>();
        mFactText = new MutableLiveData<>();
    }
}
