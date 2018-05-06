package sample.codequality.di;

import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.internal.schedulers.IoScheduler;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sample.codequality.domain.GetFactUseCase;
import sample.codequality.domain.fact.FactRepository;
import sample.codequality.repository.FactRepositoryImpl;
import sample.codequality.repository.web.NumbersFactsApi;

@Module
public class FactModule {
    @Provides
    @NonNull
    @Singleton
    NumbersFactsApi provideNumbersFactsApi() {
        return new Retrofit.Builder()
                .baseUrl(NumbersFactsApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NumbersFactsApi.class);
    }

    @Provides
    @NonNull
    @Singleton
        FactRepository provideFactRepository(@NonNull NumbersFactsApi numbersFactsApi) {
        return new FactRepositoryImpl(numbersFactsApi);
    }

    @Provides
    @NonNull
    @Singleton
    GetFactUseCase provideGetFactUseCase(@NonNull FactRepository factRepository) {
        return new GetFactUseCase(factRepository, Schedulers.io());
    }
}
