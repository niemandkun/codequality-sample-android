package sample.codequality.domain.facts;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import sample.codequality.web.NumbersFactsApi;

public class Fact {
    @NonNull
    @SuppressWarnings("NullableProblems")
    @SerializedName("text")
    private String mText;

    @NonNull
    @SuppressWarnings("NullableProblems")
    @SerializedName("found")
    private boolean mIsFound;

    @NonNull
    @SuppressWarnings("NullableProblems")
    @SerializedName("number")
    private int mNumber;

    @NonNull
    @SuppressWarnings("NullableProblems")
    @SerializedName("type")
    @NumbersFactsApi.FactCategory
    private String mCategory;

    @Nullable
    @SerializedName("date")
    private String mDate;

    @Nullable
    @SerializedName("year")
    private String mYear;

    @NonNull
    public String getText() {
        return mText;
    }
}
