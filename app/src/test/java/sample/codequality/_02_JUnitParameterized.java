package sample.codequality;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.HashMap;
import java.util.Map;

import sample.codequality.domain.calculator.Calculator;
import sample.codequality.domain.calculator.CalculatorImpl;
import sample.codequality.domain.calculator.Operator;

@RunWith(Parameterized.class)
public class _02_JUnitParameterized {
    @Parameterized.Parameters(name = "{0} + {1} = {2}")
    public static String[][] provideParameters() {
        return new String[][] {
                {"2", "2", "4"},
                {"2", "1", "3"},
                {"10", "1", "11"},
        };
    }

    @Parameterized.Parameter(0)
    public String mFirstOperand;

    @Parameterized.Parameter(1)
    public String mSecondOperand;

    @Parameterized.Parameter(2)
    public String mResult;

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
    public void runParameterizedCalculatorTest() {
        mCalculator.appendNumber(mFirstOperand);
        mCalculator.appendOperator("+");
        mCalculator.appendNumber(mSecondOperand);
        mCalculator.evaluate();
        Assert.assertEquals(mResult, mCalculator.getResult());
    }
}
