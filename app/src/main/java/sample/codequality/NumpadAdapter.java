package sample.codequality;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class NumpadAdapter extends RecyclerView.Adapter<NumpadAdapter.ViewHolder> {
    @NonNull
    private final List<NumpadButton> mButtons;

    NumpadAdapter(@NonNull List<NumpadButton> buttons) {
        mButtons = buttons;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_numpad_button, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NumpadButton value = mButtons.get(position);
        holder.mTextView.setBackgroundResource(value.getButtonBackground());
        holder.mTextView.setText(value.getButtonLabel());
        holder.mTextView.setOnClickListener(view -> { });
    }

    @Override
    public int getItemCount() {
        return mButtons.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @NonNull
        private final TextView mTextView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = (TextView) itemView;
        }
    }
}
