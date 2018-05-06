package sample.codequality;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import sample.codequality.domain.fact.Fact;
import sample.codequality.domain.fact.FactRepository;
import sample.codequality.repository.FactRepositoryImpl;
import sample.codequality.repository.web.NumbersFactsApi;

public class _05_MockitoTest {
    private FactRepository mFactRepository;

    private NumbersFactsApi mNumbersFactsApi;

    @Before
    public void runBeforeAnyTest() {
        mNumbersFactsApi = Mockito.mock(NumbersFactsApi.class);
        mFactRepository = new FactRepositoryImpl(mNumbersFactsApi);
    }

    @Test
    public void getTriviaFact_worksCorrectly() throws IOException {
        int numberToTest = 1;
        String numberStringRepresentation = Integer.toString(numberToTest);

        Call<Fact> call = Mockito.mock(Call.class);
        Mockito.when(call.execute()).thenReturn(Response.success(new Fact("ok")));
        Mockito.when(mNumbersFactsApi
                .getFact(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(call);

        String fact = mFactRepository.getTriviaFact(numberToTest);

        Assert.assertEquals(fact, "ok");
        Mockito.verify(call).execute();
        Mockito.verify(mNumbersFactsApi).getFact(numberStringRepresentation, NumbersFactsApi.CATEGORY_TRIVIA);
    }
}
