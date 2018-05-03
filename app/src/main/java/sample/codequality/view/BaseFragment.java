package sample.codequality.view;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import sample.codequality.CalculatorApplication;

public class BaseFragment extends Fragment {
    @NonNull
    protected final <T extends ViewModel> T createViewModel(@NonNull Class<T> viewModelClass) {
        FragmentActivity activity = getActivity();
        if (activity == null) {
            throw new IllegalStateException();
        }
        CalculatorApplication application = ((CalculatorApplication) activity.getApplication());
        ViewModelProvider.Factory factory = application.getViewModelFactory();
        return ViewModelProviders.of(activity, factory).get(viewModelClass);
    }
}
