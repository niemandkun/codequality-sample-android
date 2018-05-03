package sample.codequality.di;

import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.internal.schedulers.IoScheduler;
import io.reactivex.schedulers.Schedulers;
import sample.codequality.domain.GetFactUseCase;
import sample.codequality.domain.facts.FactRepository;
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
    IoScheduler provideIoScheduler() {
        return (IoScheduler) Schedulers.io();
    }

    @Provides
    @NonNull
    @Singleton
    GetFactUseCase provideGetFactUseCase(@NonNull FactRepository factRepository, @NonNull IoScheduler scheduler) {
        return new GetFactUseCase(factRepository, scheduler);
    }
}
