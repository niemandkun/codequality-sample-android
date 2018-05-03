package sample.codequality.domain;

import android.support.annotation.NonNull;
import android.util.Log;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.internal.schedulers.IoScheduler;
import sample.codequality.domain.facts.FactRepository;

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
        Log.e("GetFactUseCase", "getTriviaFact");
        return Single.fromCallable(() -> mFactRepository.getTriviaFact(number)).subscribeOn(mIoScheduler);
    }
}
