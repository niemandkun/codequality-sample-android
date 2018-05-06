package sample.codequality.repository;

import android.support.annotation.NonNull;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sample.codequality.domain.fact.Fact;
import sample.codequality.domain.fact.FactRepository;
import sample.codequality.repository.web.ApiException;
import sample.codequality.repository.web.EmptyBodyException;
import sample.codequality.repository.web.HttpResponseCodeException;
import sample.codequality.repository.web.NumbersFactsApi;

public class FactRepositoryImpl implements FactRepository {
    private final NumberFormat mFormat = new DecimalFormat("###");

    @NonNull
    private final NumbersFactsApi mApi;

    public FactRepositoryImpl(@NonNull NumbersFactsApi numbersFactsApi) {
        mApi = numbersFactsApi;
    }

    @Override
    @NonNull
    public String getTriviaFact(double number) {
        String numberStr = mFormat.format(number);
        Response<Fact> response;
        try {
            response = mApi.getFact(numberStr, NumbersFactsApi.CATEGORY_TRIVIA).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (!response.isSuccessful()) {
            throw new HttpResponseCodeException();
        }
        Fact fact = response.body();
        if (fact == null) {
            throw new EmptyBodyException();
        }
        if (fact.getText().startsWith("ERROR")) {
            throw new ApiException(fact.getText());
        }
        return fact.getText();
    }
}
