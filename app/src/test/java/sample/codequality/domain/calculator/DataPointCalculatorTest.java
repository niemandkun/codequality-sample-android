package sample.codequality.domain.calculator;

import junit.framework.Assert;

import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.experimental.theories.suppliers.TestedOn;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;

@RunWith(Theories.class)
public class DataPointCalculatorTest {
    @DataPoint
    public static final Operator ADDITION = (left, right) -> left + right;

    @DataPoint
    public static final Operator MULTIPLICATION = (left, right) -> left * right;

    @Theory
    public void additionAndMultiplication_workCorrectly(
            @TestedOn(ints = {4, 5, 6}) int firstOperand,
            @TestedOn(ints = {1, 2, 3}) int secondOperand,
            Operator operator
    ) {
        Map<String, Operator> operators = new HashMap<>();
        operators.put("@", operator);
        Calculator calculator = new CalculatorImpl(operators);

        calculator.appendNumber(String.valueOf(firstOperand));
        calculator.appendOperator("@");
        calculator.appendNumber(String.valueOf(secondOperand));
        calculator.evaluate();

        int expectedResult = (int) operator.apply(firstOperand, secondOperand);
        Assert.assertEquals(String.valueOf(expectedResult), calculator.getResult());
    }
}
