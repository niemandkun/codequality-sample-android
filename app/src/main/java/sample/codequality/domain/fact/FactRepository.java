package sample.codequality.domain.fact;

import android.support.annotation.NonNull;

public interface FactRepository {
    @NonNull
    String getTriviaFact(double number);
}
