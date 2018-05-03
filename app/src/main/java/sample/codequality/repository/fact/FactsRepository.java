package sample.codequality.repository.fact;

import android.support.annotation.NonNull;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sample.codequality.domain.facts.Fact;
import sample.codequality.web.EmptyBodyException;
import sample.codequality.web.HttpResponseCodeException;
import sample.codequality.web.NumbersFactsApi;

public class FactsRepository {
    private final NumberFormat mFormat = new DecimalFormat("###");

    @NonNull
    private final NumbersFactsApi mApi;

    public FactsRepository() {
        mApi = new Retrofit.Builder()
                .baseUrl(NumbersFactsApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NumbersFactsApi.class);
    }

    @NonNull
    public String getTriviaFact(double number) throws IOException {
        String numberStr = mFormat.format(number);
        Response<Fact> response = mApi.getFact(numberStr, NumbersFactsApi.CATEGORY_TRIVIA).execute();
        if (!response.isSuccessful()) {
            throw new HttpResponseCodeException();
        }
        Fact fact = response.body();
        if (fact == null) {
            throw new EmptyBodyException();
        }
        return fact.getText();
    }
}
