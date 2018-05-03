package sample.codequality.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import sample.codequality.R;
import sample.codequality.view.numpad.NumpadView;

public class CalculatorFragment extends BaseFragment {
    @BindView(R.id.calculator_numpad)
    NumpadView mNumpad;

    @BindView(R.id.calculator_equal_button)
    View mEqualButton;

    @BindView(R.id.calculator_fact)
    TextView mFact;

    @BindView(R.id.calculator_display)
    TextView mDisplay;

    @Nullable
    private Unbinder mUnbinder;

    @Nullable
    private CalculatorViewModel mViewModel;

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
        FragmentActivity activity = getActivity();
        if (activity == null) {
            throw new IllegalStateException();
        }
        mViewModel = createViewModel(CalculatorViewModel.class);
        mViewModel.getDisplayText().observe(this, mDisplay::setText);
        mViewModel.getFactText().observe(this, mFact::setText);
        mEqualButton.setOnClickListener(v -> mViewModel.onEqualsButtonPressed());
        mNumpad.setOnClickListener(mViewModel::onNumpadButtonPressed);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }
}
