package sample.codequality.view.numpad;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

import sample.codequality.R;

public enum NumpadButton {
    NUM_0,
    NUM_1,
    NUM_2,
    NUM_3,
    NUM_4,
    NUM_5,
    NUM_6,
    NUM_7,
    NUM_8,
    NUM_9,
    COMMA,
    ADD,
    SUBTRACT,
    MULTIPLY,
    DIVIDE,
    DEL;

    @NonNull
    private static final Map<NumpadButton, String> BUTTON_LABELS = new HashMap<>();

    static {
        BUTTON_LABELS.put(NUM_1, "1");
        BUTTON_LABELS.put(NUM_2, "2");
        BUTTON_LABELS.put(NUM_3, "3");
        BUTTON_LABELS.put(NUM_4, "4");
        BUTTON_LABELS.put(NUM_5, "5");
        BUTTON_LABELS.put(NUM_6, "6");
        BUTTON_LABELS.put(NUM_7, "7");
        BUTTON_LABELS.put(NUM_8, "8");
        BUTTON_LABELS.put(NUM_9, "9");
        BUTTON_LABELS.put(NUM_0, "0");

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
