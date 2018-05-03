package sample.codequality.view.numpad;

import android.support.annotation.NonNull;

import java.util.Arrays;
import java.util.List;

public final class NumpadLayout {
    @NonNull
    static final NumpadLayout PORTRAIT = new NumpadLayout(
            Arrays.asList(
                    NumpadButton.NUM_7, NumpadButton.NUM_8, NumpadButton.NUM_9,  NumpadButton.ADD,
                    NumpadButton.NUM_4,  NumpadButton.NUM_5,  NumpadButton.NUM_6,   NumpadButton.SUBTRACT,
                    NumpadButton.NUM_1,   NumpadButton.NUM_2,   NumpadButton.NUM_3, NumpadButton.MULTIPLY,
                    NumpadButton.COMMA, NumpadButton.NUM_0,  NumpadButton.DEL,   NumpadButton.DIVIDE
            ), 4
    );

    @NonNull
    static final NumpadLayout LANDSCAPE = new NumpadLayout(
            Arrays.asList(
                    NumpadButton.NUM_5,  NumpadButton.NUM_6,   NumpadButton.NUM_7,     NumpadButton.NUM_8,
                    NumpadButton.NUM_9,  NumpadButton.DEL,   NumpadButton.ADD,       NumpadButton.SUBTRACT,
                    NumpadButton.NUM_0,  NumpadButton.NUM_1,   NumpadButton.NUM_2,       NumpadButton.NUM_3,
                    NumpadButton.NUM_4,  NumpadButton.COMMA, NumpadButton.MULTIPLY,  NumpadButton.DIVIDE
            ), 8
    );

    @NonNull
    private final List<NumpadButton> mNumpadButtons;

    private final int mColumnsCount;

    private NumpadLayout(@NonNull List<NumpadButton> numpadButtons, int columnsCount) {
        mNumpadButtons = numpadButtons;
        mColumnsCount = columnsCount;
    }

    public int getColumnsCount() {
        return mColumnsCount;
    }

    @NonNull
    public List<NumpadButton> getButtons() {
        return mNumpadButtons;
    }
}
