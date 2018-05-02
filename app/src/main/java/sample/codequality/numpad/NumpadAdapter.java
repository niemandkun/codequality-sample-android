package sample.codequality.numpad;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import sample.codequality.R;

final class NumpadAdapter extends RecyclerView.Adapter<NumpadAdapter.ViewHolder> {
    @NonNull
    private final List<NumpadButton> mButtons;

    @NonNull
    private final OnClickListener mOnClickListener;

    NumpadAdapter(@NonNull List<NumpadButton> buttons, @NonNull OnClickListener onClickListener) {
        mButtons = buttons;
        mOnClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_numpad_button, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(mButtons.get(position));
    }

    @Override
    public int getItemCount() {
        return mButtons.size();
    }

    final class ViewHolder extends RecyclerView.ViewHolder {
        @NonNull
        private final TextView mButtonTitle;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mButtonTitle = (TextView) itemView;
        }

        void bind(@NonNull NumpadButton item) {
            mButtonTitle.setBackgroundResource(item.getButtonBackground());
            mButtonTitle.setText(item.getButtonLabel());
            mButtonTitle.setOnClickListener(view -> mOnClickListener.onClick(item));
        }
    }

    public interface OnClickListener {
        void onClick(@NonNull NumpadButton numpadButton);
    }
}
