package sample.codequality.view;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import sample.codequality.domain.CalculatorInputUseCase;
import sample.codequality.domain.GetFactUseCase;
import sample.codequality.view.numpad.NumpadButton;

public class CalculatorViewModel extends ViewModel {
    @NonNull
    private final MutableLiveData<String> mDisplayText = new MutableLiveData<>();

    @NonNull
    private final MutableLiveData<String> mFactText = new MutableLiveData<>();

    @NonNull
    private final MutableLiveData<String> mErrorMessage = new MutableLiveData<>();

    @NonNull
    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @NonNull
    private final CalculatorInputUseCase mCalculatorInputUseCase;

    @NonNull
    private final GetFactUseCase mGetFactUseCase;

    @Inject
    public CalculatorViewModel(
            @NonNull CalculatorInputUseCase calculatorInputUseCase,
            @NonNull GetFactUseCase factUseCase
    ) {
        mCalculatorInputUseCase = calculatorInputUseCase;
        mGetFactUseCase = factUseCase;
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
        mCalculatorInputUseCase.handleInput(button, new CalculatorInputUseCase.Callback() {
            @Override
            public void onSuccess(@NonNull String displayText) {
                mDisplayText.postValue(displayText);
            }

            @Override
            public void onError(@NonNull Throwable throwable) {
                mErrorMessage.postValue(throwable.getMessage());
            }
        });
    }

    public void onEqualsButtonPressed() {
        mCalculatorInputUseCase.evaluateExpression(new CalculatorInputUseCase.EvaluationCallback() {
            @Override
            public void onSuccess(@NonNull String displayText, @Nullable Double evaluationResult) {
                mDisplayText.postValue(displayText);
                if (evaluationResult == null) {
                    return;
                }
                Disposable disposable = mGetFactUseCase
                        .getTriviaFact(evaluationResult)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(mFactText::postValue, t -> mErrorMessage.postValue(t.getMessage()));
                mCompositeDisposable.add(disposable);
            }

            @Override
            public void onError(@NonNull Throwable throwable) {
                mErrorMessage.postValue(throwable.getMessage());
            }
        });
    }
}
