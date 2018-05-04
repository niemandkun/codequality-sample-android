package sample.codequality.domain;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import sample.codequality.domain.calculator.Calculator;

@RunWith(RobolectricTestRunner.class)
public class CalculatorEvaluationTest {
    private static final String EMPTY_STRING = "";

    private static ExecutorService sExecutor;

    private Calculator mCalculator;

    private CalculatorInputUseCase mCalculatorInputUseCase;

    @BeforeClass
    public static void runBeforeAllTests() {
        sExecutor = Executors.newSingleThreadExecutor();
    }

    @AfterClass
    public static void runAfterAllTests() {
        sExecutor.shutdown();
        sExecutor = null;
    }

    @Before
    public void runBeforeAnyTest() {
        mCalculator = Mockito.mock(Calculator.class);
        Mockito.when(mCalculator.getFirstOperand()).thenReturn(EMPTY_STRING);
        Mockito.when(mCalculator.getOperator()).thenReturn(EMPTY_STRING);
        Mockito.when(mCalculator.getSecondOperand()).thenReturn(EMPTY_STRING);
        Mockito.when(mCalculator.getResult()).thenReturn(EMPTY_STRING);
        mCalculatorInputUseCase = new CalculatorInputUseCase(mCalculator, sExecutor);
    }

    @Test
    public void evaluateExpression_shouldWorkCorrectly() {
        CalculatorInputUseCase.EvaluationCallback callback =
                Mockito.mock(CalculatorInputUseCase.EvaluationCallback.class);

        mCalculatorInputUseCase.evaluateExpression(callback);

        Robolectric.flushBackgroundThreadScheduler();

        Mockito.verify(mCalculator).evaluate();
        Mockito.verify(callback).onSuccess("", null);
        Mockito.verify(callback, Mockito.never()).onError(Mockito.any());
    }
}
