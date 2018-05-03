package sample.codequality.web;

import android.support.annotation.NonNull;
import android.support.annotation.StringDef;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import sample.codequality.domain.facts.Fact;

public interface NumbersFactsApi {
    @NonNull
    String BASE_URL = "http://numbersapi.com/";

    @NonNull
    String CATEGORY_TRIVIA = "trivia";

    @NonNull
    String CATEGORY_MATH = "math";

    @NonNull
    String CATEGORY_DATE = "date";

    @NonNull
    String CATEGORY_YEAR = "year";

    @StringDef({CATEGORY_TRIVIA, CATEGORY_MATH, CATEGORY_DATE, CATEGORY_YEAR})
    @interface FactCategory { }

    @GET("{number}/{category}?json")
    Call<Fact> getFact(@Path("number") String number, @Path("category") @FactCategory String category);
}
