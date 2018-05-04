package sample.codequality.domain;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
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
    public Observable<String> getTriviaFact(double number) {
        return Observable.fromCallable(() -> mFactRepository.getTriviaFact(number)).subscribeOn(mIoScheduler);
    }
}
