package sample.codequality;

import android.support.annotation.NonNull;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import sample.codequality.domain.CalculatorInputUseCase;
import sample.codequality.domain.calculator.Calculator;
import sample.codequality.view.numpad.NumpadButton;

public class _08_ExecutorTest {
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
    public void handleInput_worksCorrectly_withNumbers() {
        CalculatorInputUseCase.Callback callback = Mockito.mock(CalculatorInputUseCase.Callback.class);
        Future future = mCalculatorInputUseCase.handleInput(NumpadButton.NUM_0, callback);
        await(future);
        Mockito.verify(callback).onSuccess(EMPTY_STRING);
        Mockito.verify(callback, Mockito.never()).onError(Mockito.any());
        Mockito.verify(mCalculator).appendNumber("0");
    }

    @Test
    public void handleInput_worksCorrectly_withOperators() {
        CalculatorInputUseCase.Callback callback = Mockito.mock(CalculatorInputUseCase.Callback.class);
        Future future = mCalculatorInputUseCase.handleInput(NumpadButton.ADD, callback);
        await(future);
        Mockito.verify(callback).onSuccess(EMPTY_STRING);
        Mockito.verify(callback, Mockito.never()).onError(Mockito.any());
        Mockito.verify(mCalculator).appendOperator("+");
    }

    @Test
    public void handleInput_worksCorrectly_withUndo() {
        CalculatorInputUseCase.Callback callback = Mockito.mock(CalculatorInputUseCase.Callback.class);
        Future future = mCalculatorInputUseCase.handleInput(NumpadButton.UNDO, callback);
        await(future);
        Mockito.verify(callback).onSuccess(EMPTY_STRING);
        Mockito.verify(callback, Mockito.never()).onError(Mockito.any());
        Mockito.verify(mCalculator).undo();
    }

    private void await(@NonNull Future future) {
        try {
            future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
