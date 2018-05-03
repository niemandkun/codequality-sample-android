package sample.codequality.domain.fact;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;
import sample.codequality.domain.GetFactUseCase;

public class GetFactUseCaseTest {
    private final static String TESTING_FACT = "not x! but fact";

    private static TestScheduler sScheduler;

    private FactRepository mFactRepository;

    private GetFactUseCase mGetFactUseCase;

    @BeforeClass
    public static void runBeforeAllTests() {
        sScheduler = new TestScheduler();
    }

    @Before
    public void runBeforeEachTest() {
        mFactRepository = Mockito.mock(FactRepository.class);
        mGetFactUseCase = new GetFactUseCase(mFactRepository, sScheduler);
    }

    @Test
    public void getTriviaFact_actuallyReturnsTriviaFact() {
        Mockito.when(mFactRepository.getTriviaFact(0)).thenReturn(TESTING_FACT);
        TestObserver<String> observer = TestObserver.create();

        mGetFactUseCase.getTriviaFact(0).subscribe(observer);
        sScheduler.triggerActions();

        observer.assertComplete();
        observer.assertNoErrors();
        observer.assertValue(TESTING_FACT);

        Mockito.verify(mFactRepository).getTriviaFact(0);
    }
}
