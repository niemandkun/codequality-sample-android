package sample.codequality;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import sample.codequality.view.CalculatorFragment;
import sample.codequality.view.CalculatorViewModel;
import sample.codequality.view.numpad.NumpadButton;

@RunWith(RobolectricTestRunner.class)
public class _07_RobolectricFragmentTest {
    @Test
    public void fragmentCallbacks_shouldBeInvokedCorrectly() {
        TestingCalculatorFragment fragment = new TestingCalculatorFragment();
        SupportFragmentTestUtil.startVisibleFragment(fragment);

        RecyclerView recyclerView = fragment.getView().findViewById(R.id.calculator_numpad);
        View button = recyclerView.getChildAt(0);

        if (!Shadows.shadowOf(button).checkedPerformClick()) {
            Assert.fail("Cannot click numpad button, something went wrong.");
        }

        Mockito.verify(fragment.getViewModel()).onNumpadButtonPressed(NumpadButton.NUM_7);
    }

    public static class TestingCalculatorFragment extends CalculatorFragment {
        @NonNull
        @Override
        protected CalculatorViewModel createViewModel(@NonNull Class<CalculatorViewModel> viewModelClass) {
            CalculatorViewModel viewModel = Mockito.mock(viewModelClass);
            Mockito.when(viewModel.getDisplayText()).thenReturn(new MutableLiveData<>());
            Mockito.when(viewModel.getFactText()).thenReturn(new MutableLiveData<>());
            return mViewModel = viewModel;
        }
    }
}
