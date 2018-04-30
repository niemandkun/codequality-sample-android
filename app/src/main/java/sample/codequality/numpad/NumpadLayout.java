package sample.codequality.numpad;

import android.support.annotation.NonNull;

import java.util.Arrays;
import java.util.List;

public final class NumpadLayout {
    @NonNull
    static final NumpadLayout PORTRAIT = new NumpadLayout(
            Arrays.asList(
                    NumpadButton.SEVEN, NumpadButton.EIGHT, NumpadButton.NINE,  NumpadButton.ADD,
                    NumpadButton.FOUR,  NumpadButton.FIVE,  NumpadButton.SIX,   NumpadButton.SUBTRACT,
                    NumpadButton.ONE,   NumpadButton.TWO,   NumpadButton.THREE, NumpadButton.MULTIPLY,
                    NumpadButton.COMMA, NumpadButton.ZERO,  NumpadButton.DEL,   NumpadButton.DIVIDE
            ), 4
    );

    @NonNull
    static final NumpadLayout LANDSCAPE = new NumpadLayout(
            Arrays.asList(
                    NumpadButton.FIVE,  NumpadButton.SIX,   NumpadButton.SEVEN,     NumpadButton.EIGHT,
                    NumpadButton.NINE,  NumpadButton.DEL,   NumpadButton.ADD,       NumpadButton.SUBTRACT,
                    NumpadButton.ZERO,  NumpadButton.ONE,   NumpadButton.TWO,       NumpadButton.THREE,
                    NumpadButton.FOUR,  NumpadButton.COMMA, NumpadButton.MULTIPLY,  NumpadButton.DIVIDE
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
