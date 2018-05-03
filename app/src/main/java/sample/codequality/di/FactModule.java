package sample.codequality.di;

import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.internal.schedulers.IoScheduler;
import io.reactivex.schedulers.Schedulers;
import sample.codequality.domain.GetFactUseCase;
import sample.codequality.domain.fact.FactRepository;
import sample.codequality.repository.FactRepositoryImpl;

@Module
public class FactModule {
    @Provides
    @NonNull
    @Singleton
        FactRepository provideFactRepository() {
        return new FactRepositoryImpl();
    }

    @Provides
    @NonNull
    @Singleton
    GetFactUseCase provideGetFactUseCase(@NonNull FactRepository factRepository) {
        return new GetFactUseCase(factRepository, Schedulers.io());
    }
}
