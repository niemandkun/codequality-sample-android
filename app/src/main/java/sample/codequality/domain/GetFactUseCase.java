package sample.codequality.domain;

import android.support.annotation.NonNull;
import android.util.Log;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import sample.codequality.domain.fact.FactRepository;

public class GetFactUseCase {
    @NonNull
    private final FactRepository mFactRepository;

    @NonNull
    private final Scheduler mIoScheduler;

    @Inject
    public GetFactUseCase(@NonNull FactRepository factRepository, @NonNull Scheduler ioScheduler) {
        mFactRepository = factRepository;
        mIoScheduler = ioScheduler;
    }

    @NonNull
    public Single<String> getTriviaFact(double number) {
        return Single.fromCallable(() -> mFactRepository.getTriviaFact(number)).subscribeOn(mIoScheduler);
    }
}
