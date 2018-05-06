package sample.codequality;

import android.support.annotation.NonNull;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import sample.codequality.domain.calculator.Calculator;
import sample.codequality.domain.calculator.CalculatorImpl;
import sample.codequality.domain.calculator.Operator;

public class _01_JUnitTest {
    private static Map<String, Operator> sOperators;

    private Calculator mCalculator;

    @BeforeClass
    public static void runBeforeAllTests() {
        sOperators = new HashMap<>();
        sOperators.put("+", (left, right) -> left + right);
    }

    @AfterClass
    public static void runAfterAllTests() {
        sOperators = null;
    }

    @Before
    public void runBeforeEachTest() {
        mCalculator = new CalculatorImpl(sOperators);
    }

    @Test
    public void appendNumber_shouldWork_withFirstOperand() {
        mCalculator.appendNumber("1");
        Assert.assertEquals(mCalculator.getFirstOperand(), "1");
    }

    @Test
    public void appendNumber_shouldAppend_longStrings() {
        mCalculator.appendNumber("111");
        assertHasState("111", "", "", "");
    }

    @Test
    public void appendNumber_shouldAppend_multipleStrings() {
        mCalculator.appendNumber("1");
        mCalculator.appendNumber("1");
        mCalculator.appendNumber("1");
        assertHasState("111", "", "", "");
    }

    @Test
    public void appendOperator_shouldDoNothing_ifCalculatorEmpty() {
        mCalculator.appendOperator("+");
        assertHasState("", "", "", "");
    }

    @Test
    public void appendOperator_shouldWork_ifHasFirstOperand() {
        mCalculator.appendNumber("1");
        mCalculator.appendOperator("+");
        assertHasState("1", "+", "", "");
    }

    @Test
    public void appendNumber_shouldWork_withSecondOperand() {
        mCalculator.appendNumber("2");
        mCalculator.appendOperator("+");
        mCalculator.appendNumber("2");
        assertHasState("2", "+", "2", "");
    }

    @Test
    public void appendOperator_shouldReplaceOperator_ifExisting() {
        mCalculator.appendNumber("1");
        mCalculator.appendOperator("-");
        mCalculator.appendOperator("+");
        assertHasState("1", "+", "", "");
    }

    @Test
    public void appendOperator_shouldEvaluateExpression_ifHasSecondOperand() {
        mCalculator.appendNumber("1");
        mCalculator.appendOperator("+");
        mCalculator.appendNumber("2");
        mCalculator.appendOperator("-");
        assertHasState("3", "-", "", "");
    }

    @Test
    public void appendOperator_shouldUseResultAsOperand_ifHasResult() {
        mCalculator.appendNumber("1");
        mCalculator.appendOperator("+");
        mCalculator.appendNumber("2");
        mCalculator.evaluate();
        mCalculator.appendOperator("-");
        assertHasState("3", "-", "", "");
    }

    @Test
    public void undo_shouldDoNothing_ifEmpty() {
        mCalculator.undo();
        assertHasState("", "", "", "");
    }

    @Test
    public void undo_shouldWork_withFirstOperand() {
        mCalculator.appendNumber("1");
        mCalculator.undo();
        assertHasState("", "", "", "");
    }

    @Test
    public void undo_shouldWork_ifUndoDeleteTwice() {
        mCalculator.appendNumber("1");
        mCalculator.undo();
        mCalculator.undo();
        assertHasState("", "", "", "");
    }

    @Test
    public void undo_shouldWork_withLongFirstOperand() {
        mCalculator.appendNumber("111");
        mCalculator.undo();
        assertHasState("11", "", "", "");
    }

    @Test
    public void undo_shouldWork_withSecondOperand() {
        mCalculator.appendNumber("1");
        mCalculator.appendOperator("+");
        mCalculator.appendNumber("1");
        mCalculator.undo();
        assertHasState("1", "+", "", "");
    }

    @Test
    public void undo_shouldWork_withLongSecondOperand() {
        mCalculator.appendNumber("1");
        mCalculator.appendOperator("+");
        mCalculator.appendNumber("12");
        mCalculator.undo();
        assertHasState("1", "+", "1", "");
    }

    @Test
    public void undo_shouldWork_withOperator() {
        mCalculator.appendNumber("1");
        mCalculator.appendOperator("+");
        mCalculator.undo();
        assertHasState("1", "", "", "");
    }

    @Test
    public void undo_shouldDoClean_ifHasResult() {
        mCalculator.appendNumber("1");
        mCalculator.appendOperator("+");
        mCalculator.appendNumber("1");
        mCalculator.evaluate();
        mCalculator.undo();
        assertHasState("", "", "", "");
    }

    @Test
    public void comma_shouldWork() {
        mCalculator.appendNumber("1");
        mCalculator.appendComma();
        assertHasState("1.", "", "", "");
    }

    @Test
    public void comma_isAllowed_ifAppendedFirst() {
        mCalculator.appendComma();
        mCalculator.appendNumber("1");
        assertHasState(".1", "", "", "");
    }

    @Test
    public void comma_shouldBeEnteredOnlyOnce() {
        mCalculator.appendNumber("1");
        mCalculator.appendComma();
        mCalculator.appendComma();
        mCalculator.appendComma();
        assertHasState("1.", "", "", "");
    }

    @Test
    public void comma_shouldDeletePreviousComma_ifAny() {
        mCalculator.appendNumber("1");
        mCalculator.appendComma();
        mCalculator.appendNumber("1");
        mCalculator.appendComma();
        assertHasState("11.", "", "", "");
    }

    @Test
    public void evaluate_shouldCalculateResult_ifHasTwoOperands() {
        mCalculator.appendNumber("1");
        mCalculator.appendOperator("+");
        mCalculator.appendNumber("1");
        mCalculator.evaluate();
        assertHasState("1", "+", "1", "2");
    }

    @Test
    public void evaluate_shouldCopyFirstOperand_ifHasOnlyOneOperand() {
        mCalculator.appendNumber("1");
        mCalculator.evaluate();
        assertHasState("1", "", "", "1");
    }

    @Test
    public void evaluate_shouldDoNothing_ifHasOperandAndOperator() {
        mCalculator.appendNumber("1");
        mCalculator.appendOperator("+");
        mCalculator.evaluate();
        assertHasState("1", "+", "", "");
    }

    @Test
    public void evaluate_shouldDoNothing_ifEvaluatedTwice() {
        mCalculator.appendNumber("1");
        mCalculator.appendOperator("+");
        mCalculator.appendNumber("1");
        mCalculator.evaluate();
        mCalculator.evaluate();
        assertHasState("1", "+", "1", "2");
    }

    @Test
    public void evaluate_shouldDoNothing_ifEmpty() {
        mCalculator.evaluate();
        assertHasState("", "", "", "");
    }

    private void assertHasState(
            @NonNull String firstOperand,
            @NonNull String operator,
            @NonNull String secondOperand,
            @NonNull String result
    ) {
        Assert.assertEquals(firstOperand, mCalculator.getFirstOperand());
        Assert.assertEquals(operator, mCalculator.getOperator());
        Assert.assertEquals(secondOperand, mCalculator.getSecondOperand());
        Assert.assertEquals(result, mCalculator.getResult());
    }
}