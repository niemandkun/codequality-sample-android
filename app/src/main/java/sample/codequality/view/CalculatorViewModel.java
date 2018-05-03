package sample.codequality.view;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import sample.codequality.domain.calculator.CalculatorInputUseCase;
import sample.codequality.domain.facts.GetFactUseCase;
import sample.codequality.view.numpad.NumpadButton;

public class CalculatorViewModel extends ViewModel {
    @NonNull
    private final MutableLiveData<String> mDisplayText;

    @NonNull
    private final MutableLiveData<String> mFactText;

    @NonNull
    private final MutableLiveData<String> mErrorMessage;

    @NonNull
    private final CalculatorInputUseCase mCalculatorInputUseCase;

    @NonNull
    private final GetFactUseCase mGetFactUseCase;

    public CalculatorViewModel() {
        mDisplayText = new MutableLiveData<>();
        mFactText = new MutableLiveData<>();
        mErrorMessage = new MutableLiveData<>();
        mCalculatorInputUseCase = null;
        mGetFactUseCase = null;
    }

    @NonNull
    public MutableLiveData<String> getDisplayText() {
        return mDisplayText;
    }

    @NonNull
    public MutableLiveData<String> getFactText() {
        return mFactText;
    }

    public void onNumpadButtonPressed(@NonNull NumpadButton button) {
        mCalculatorInputUseCase.handleInput(button).subscribe(mDisplayText::postValue);
    }

    public void onEqualsButtonPressed() {
        mCalculatorInputUseCase.execute(new CalculatorInputUseCase.Callback() {
            @Override
            public void onSuccess(@NonNull String displayText) {
                mDisplayText.postValue(displayText);
                mGetFactUseCase.getTriviaFact(Double.parseDouble(displayText));
            }

            @Override
            public void onError(@NonNull Throwable throwable) {
                mErrorMessage.postValue(throwable.getMessage());
            }
        });
    }
}
