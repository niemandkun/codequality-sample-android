package sample.codequality.domain.facts;

import android.support.annotation.NonNull;

import java.io.IOException;

public interface FactRepository {
    @NonNull
    String getTriviaFact(double number) throws IOException;
}
