package sample.codequality.domain.facts;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.internal.schedulers.IoScheduler;

public class GetFactUseCase {
    @NonNull
    private final FactRepository mFactRepository;

    @NonNull
    private final IoScheduler mIoScheduler;

    @Inject
    public GetFactUseCase(@NonNull FactRepository factRepository, @NonNull IoScheduler ioScheduler) {
        mFactRepository = factRepository;
        mIoScheduler = ioScheduler;
    }

    @NonNull
    public Single<String> getTriviaFact(double number) {
        return Single.fromCallable(() -> mFactRepository.getTriviaFact(number)).subscribeOn(mIoScheduler);
    }
}
