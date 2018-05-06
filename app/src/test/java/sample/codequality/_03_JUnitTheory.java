package sample.codequality;

import junit.framework.Assert;

import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.experimental.theories.suppliers.TestedOn;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;

import sample.codequality.domain.calculator.Calculator;
import sample.codequality.domain.calculator.CalculatorImpl;
import sample.codequality.domain.calculator.Operator;

@RunWith(Theories.class)
public class _03_JUnitTheory {
    @Theory
    public void addition_worksCorrectly(
            @TestedOn(ints = {4, 5, 6}) int firstOperand,
            @TestedOn(ints = {1, 2, 3}) int secondOperand
    ) {
        Map<String, Operator> operators = new HashMap<>();
        operators.put("+", (left, right) -> left + right);
        Calculator calculator = new CalculatorImpl(operators);

        calculator.appendNumber(String.valueOf(firstOperand));
        calculator.appendOperator("+");
        calculator.appendNumber(String.valueOf(secondOperand));
        calculator.evaluate();

        Assert.assertEquals(String.valueOf(firstOperand + secondOperand), calculator.getResult());
    }
}
