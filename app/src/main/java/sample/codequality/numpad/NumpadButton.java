package sample.codequality.numpad;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

import sample.codequality.R;

public enum NumpadButton {
    SEVEN,  EIGHT,  NINE,       ADD,
    FOUR,   FIVE,   SIX,        SUBTRACT,
    ONE,    TWO,    THREE,      MULTIPLY,
    COMMA,  ZERO,   DEL,        DIVIDE;

    @NonNull
    private static final Map<NumpadButton, String> BUTTON_LABELS = new HashMap<>();

    static {
        BUTTON_LABELS.put(ONE, "1");
        BUTTON_LABELS.put(TWO, "2");
        BUTTON_LABELS.put(THREE, "3");
        BUTTON_LABELS.put(FOUR, "4");
        BUTTON_LABELS.put(FIVE, "5");
        BUTTON_LABELS.put(SIX, "6");
        BUTTON_LABELS.put(SEVEN, "7");
        BUTTON_LABELS.put(EIGHT, "8");
        BUTTON_LABELS.put(NINE, "9");
        BUTTON_LABELS.put(ZERO, "0");

        BUTTON_LABELS.put(COMMA, ".");
        BUTTON_LABELS.put(DEL, "<");

        BUTTON_LABELS.put(ADD, "+");
        BUTTON_LABELS.put(SUBTRACT, "-");
        BUTTON_LABELS.put(MULTIPLY, "*");
        BUTTON_LABELS.put(DIVIDE, "/");
    }

    @NonNull
    public String getButtonLabel() {
        return BUTTON_LABELS.get(this);
    }

    @DrawableRes
    int getButtonBackground() {
        String label = getButtonLabel();
        if (TextUtils.isDigitsOnly(label) || ".".equals(label)) {
            return R.drawable.bg_ripple_grey;
        }
        if ("<".equals(label)) {
            return R.drawable.bg_ripple_red;
        }
        return R.drawable.bg_ripple_blue;
    }
}
