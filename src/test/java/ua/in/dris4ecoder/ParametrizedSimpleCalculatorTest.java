package ua.in.dris4ecoder;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ua.in.dris4ecoder.expression.ExpressionElement;
import ua.in.dris4ecoder.mathematics.SimpleCalculator;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

import static ua.in.dris4ecoder.expression.ElementType.*;

/**
 * SimpleCalculator Test
 * <p>
 * Created by Alex Korneyko on 15.05.2016.
 */
@RunWith(value = Parameterized.class)
public class ParametrizedSimpleCalculatorTest {

    //Проверочные операнды.
    public static final ExpressionElement OPERAND1 = new ExpressionElement( 60);
    public static final ExpressionElement OPERAND2 = new ExpressionElement( 15);

    //Объект простого калькулятора
    private final SimpleCalculator simpleCalculator = new SimpleCalculator();

    private ExpressionElement argument1;
    private ExpressionElement argument2;
    private ExpressionElement operation;
    private double expected;

    public ParametrizedSimpleCalculatorTest(ExpressionElement argument1, ExpressionElement argument2,
                                            ExpressionElement operation, int expected) {
        this.argument1 = argument1;
        this.argument2 = argument2;
        this.operation = operation;
        this.expected = expected;
    }

    /**
     * Список входных и ожидаемых выходных параметорв для параметризованных тестов
     *
     * @return Перечисление одномерных массивов с параметрами
     */
    @Parameterized.Parameters
    public static Iterable<Object[]> getParametrizedData() {
        return Arrays.asList(new Object[][]{
                {OPERAND1, OPERAND2, new ExpressionElement(PLUS), 75},      //проверка на сложение
                {OPERAND1, OPERAND2, new ExpressionElement(MINUS), 45},     //проверка на вычитание
                {OPERAND1, OPERAND2, new ExpressionElement(MULTIPLY), 900}, //проверка на умножение
                {OPERAND1, OPERAND2, new ExpressionElement(DIVIDE), 4},     //проверка на деление
        });
    }

    @Test(timeout = 1000)
    public void test1Compute() throws Exception {

        ArrayList<ExpressionElement> expression = new ArrayList<ExpressionElement>() {{
            add(argument1);
            add(operation);
            add(argument2);
        }};

        assertEquals(expected, simpleCalculator.compute(expression), 0.01);
    }

    /**
     * Проверка работы с выражением где нехватает последнего операнда,
     * т.е. а+ или b* . Метод должен вернуть только операнд
     *
     * @throws Exception
     */
    @Test(timeout = 1000)
    public void test2Compute() throws Exception {

        ArrayList<ExpressionElement> expression = new ArrayList<ExpressionElement>() {{
            add(argument1);
            add(operation);
        }};

        assertEquals(argument1.number, simpleCalculator.compute(expression), 0.01);
    }

    /**
     * Проверка работы с выражением где нехватает первого операнда,
     * т.е. +a -b . Метод должен вернуть значение операнда. Но если
     * перво стои мат. операция '-', то вернуть отрицательное
     * значение операнда.
     *
     * @throws Exception
     */
    @Test(timeout = 1000)
    public void test3Compute() throws Exception {

        ArrayList<ExpressionElement> expression = new ArrayList<ExpressionElement>() {{
            add(operation);
            add(argument1);
        }};

        if (operation.getElementType() != MINUS) {
            assertEquals(argument1.number, simpleCalculator.compute(expression), 0.01);
        } else {
            assertEquals(argument1.number * (-1), simpleCalculator.compute(expression), 0.01);
        }
    }

    /**
     * Проверка работы с выражением где присутствует только операнд.
     * Метод должен вернуть значение этого операнда
     *
     * @throws Exception
     */
    @Test(timeout = 1000)
    public void test4Compute() throws Exception {

        ArrayList<ExpressionElement> expression = new ArrayList<ExpressionElement>() {{
            add(argument1);
        }};

        assertEquals(argument1.number, simpleCalculator.compute(expression), 0.01);
    }

    /**
     * Проверка работы с выражением где рипсутствует только мат. операция (без операндов).
     * Метод должен вернуть '0'.
     *
     * @throws Exception
     */
    @Test(timeout = 1000)
    public void test5Compute() throws Exception {

        ArrayList<ExpressionElement> expression = new ArrayList<ExpressionElement>() {{
            add(operation);
        }};

        assertEquals(0, simpleCalculator.compute(expression), 0.01);
    }

    /**
     * Проверка деления на ноль.
     * Метод должен вернуть исключение IllegalArgumentException
     *
     * @throws Exception
     */
    @Test(timeout = 1000, expected = IllegalArgumentException.class)
    public void test6Compute() throws Exception {

        ArrayList<ExpressionElement> expression = new ArrayList<ExpressionElement>() {{
            add(argument1);
            add(new ExpressionElement(DIVIDE));
            add(new ExpressionElement(0));
        }};

        assertEquals(0, simpleCalculator.compute(expression), 0.01);
    }
}