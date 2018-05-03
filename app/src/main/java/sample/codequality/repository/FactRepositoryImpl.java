package sample.codequality.repository;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sample.codequality.domain.facts.Fact;
import sample.codequality.domain.facts.FactRepository;
import sample.codequality.repository.web.EmptyBodyException;
import sample.codequality.repository.web.HttpResponseCodeException;
import sample.codequality.repository.web.NumbersFactsApi;

public class FactRepositoryImpl implements FactRepository {
    private final NumberFormat mFormat = new DecimalFormat("###");

    @NonNull
    private final NumbersFactsApi mApi;

    public FactRepositoryImpl() {
        mApi = new Retrofit.Builder()
                .baseUrl(NumbersFactsApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NumbersFactsApi.class);
    }

    @NonNull
    public String getTriviaFact(double number) throws IOException {
        Log.e("FactRepo", "getTriviaFact");
        String numberStr = mFormat.format(number);
        Response<Fact> response = mApi.getFact(numberStr, NumbersFactsApi.CATEGORY_TRIVIA).execute();
        if (!response.isSuccessful()) {
            throw new HttpResponseCodeException();
        }
        Fact fact = response.body();
        if (fact == null) {
            throw new EmptyBodyException();
        }
        Log.e("FactRepo", "getTriviaFact ok");
        return fact.getText();
    }
}
