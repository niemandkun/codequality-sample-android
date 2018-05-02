package sample.codequality.numpad;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public final class NumpadView extends RecyclerView {
    @Nullable
    private OnClickListener mOnClickListener;

    public NumpadView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public NumpadView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NumpadView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(@NonNull Context context) {
        NumpadLayout layout = getNumpadLayout(context);
        setLayoutManager(new GridLayoutManager(context, layout.getColumnsCount()));
        setAdapter(new NumpadAdapter(layout.getButtons(), numpadButton -> {
            if (mOnClickListener != null) {
                mOnClickListener.onClick(numpadButton);
            }
        }));
    }

    @NonNull
    private NumpadLayout getNumpadLayout(@NonNull Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT
                ? NumpadLayout.PORTRAIT
                : NumpadLayout.LANDSCAPE;
    }

    public void setOnClickListener(@Nullable OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onClick(@NonNull NumpadButton numpadButton);
    }
}
