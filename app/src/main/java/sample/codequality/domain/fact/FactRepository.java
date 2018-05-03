package sample.codequality.domain.fact;

import android.support.annotation.NonNull;

import java.io.IOException;

public interface FactRepository {
    @NonNull
    String getTriviaFact(double number) throws IOException;
}
