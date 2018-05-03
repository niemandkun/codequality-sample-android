package sample.codequality.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

public class ViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    private final Map<Class<? extends ViewModel>, ViewModel> mViewModels;

    ViewModelFactory(ViewModel... viewModels) {
        mViewModels = new HashMap<>();
        for (ViewModel viewModel : viewModels) {
            mViewModels.put(viewModel.getClass(), viewModel);
        }
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) mViewModels.get(modelClass);
    }
}
