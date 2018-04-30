package sample.codequality.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public final class StringUtils {
    private StringUtils() {
    }

    /**
     * Append unique pattern to string builder.
     * This method will remove all entries of pattern from stringBuilder before appending.
     */
    public static void appendUnique(@NonNull StringBuilder stringBuilder, @NonNull String pattern) {
        if (pattern.isEmpty()) {
            return;
        }
        int index;
        while ((index = stringBuilder.indexOf(pattern)) >= 0) {
            stringBuilder.delete(index, index + pattern.length());
        }
        stringBuilder.append(pattern);
    }

    /**
     * Parse double from string.
     * If string is null or empty, method will return 0.
     */
    public static double parseDoubleSafely(@Nullable String string) {
        if (string == null || string.isEmpty()) {
            return 0;
        }
        return Double.parseDouble(string);
    }
}
