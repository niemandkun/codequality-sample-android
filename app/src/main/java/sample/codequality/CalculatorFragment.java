package sample.codequality;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import sample.codequality.numpad.NumpadView;

public class CalculatorFragment extends Fragment {
    @BindView(R.id.calculator_numpad)
    NumpadView mNumpad;

    @BindView(R.id.calculator_equal_button)
    View mEqualButton;

    @BindView(R.id.calculator_fact)
    View mFact;

    @Nullable
    private Unbinder mUnbinder;

    @NonNull
    public static CalculatorFragment newInstance() {
        return new CalculatorFragment();
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.fragment_calculator, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);
        mEqualButton.setOnClickListener(v -> { });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }
}
