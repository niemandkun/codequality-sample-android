package sample.codequality.view;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import sample.codequality.CalculatorApplication;

public class BaseFragment<T extends ViewModel> extends Fragment {

    @Nullable
    protected T mViewModel;

    @NonNull
    public T getViewModel() {
        if (mViewModel == null) {
            throw new IllegalStateException();
        }
        return mViewModel;
    }

    @NonNull
    protected T createViewModel(@NonNull Class<T> viewModelClass) {
        FragmentActivity activity = getActivity();
        if (activity == null) {
            throw new IllegalStateException();
        }
        CalculatorApplication application = ((CalculatorApplication) activity.getApplication());
        ViewModelProvider.Factory factory = application.getViewModelFactory();
        return mViewModel = ViewModelProviders.of(activity, factory).get(viewModelClass);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mViewModel = null;
    }
}
