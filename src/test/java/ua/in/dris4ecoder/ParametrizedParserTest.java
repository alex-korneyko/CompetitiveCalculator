package ua.in.dris4ecoder;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

import static ua.in.dris4ecoder.ElementType.*;

/**
 * Parser test
 * Created by Alex Korneyko on 15.05.2016.
 */
@RunWith(value = Parameterized.class)
public class ParametrizedParserTest {

    //Объект парсера
    private final Parser parser = new Parser();

    String stringExpression;                    //Строка с выражением
    List<ExpressionElement> expression;         //Выражение в виде коллекции мат-объектов ExpressionElement

    public ParametrizedParserTest(String stringExpression, List<ExpressionElement> expression) {
        this.stringExpression = stringExpression;
        this.expression = expression;
    }

    /**
     * Список входных и ожидаемых выходных параметорв для параметризованных тестов.
     * Выходным параметром является коллекция объектов ExpressionElement,
     * полученная в результате парсинга входной строки String,
     * введённая пользователем с клавиатуры.
     *
     * @return Перечисление одномерных массивов с параметрами
     */
    @Parameterized.Parameters
    public static Iterable<Object[]> getParametrizedData() {
        return Arrays.asList(new Object[][]{
                //Тест на опредиление числа
                {"12582", new ArrayList<ExpressionElement>() {{
                    add(new ExpressionElement(12582));
                }}},

                {"12582.123", new ArrayList<ExpressionElement>() {{
                    add(new ExpressionElement(12582.123));
                }}},

                {"12582.0123", new ArrayList<ExpressionElement>() {{
                    add(new ExpressionElement(12582.0123));
                }}},

                //Тест на игнорирование мат-операций без чисел
                {"+-*/", new ArrayList<ExpressionElement>()},

                //Тест на опредиление скобок
                {"()", new ArrayList<ExpressionElement>() {{
                    add(new ExpressionElement(OPEN_PARENTHESIS));
                    add(new ExpressionElement(CLOSE_PARENTHESIS));
                }}},

                //Тест на опредиление отрицательного числа
                {"-5", new ArrayList<ExpressionElement>() {{
                    add(new ExpressionElement(-5));
                }}},

                //Тест на сложение с отрицательным числом, которое не в скобках
                {"5+-4", new ArrayList<ExpressionElement>() {{
                    add(new ExpressionElement(5));
                    add(new ExpressionElement(PLUS));
                    add(new ExpressionElement(-4));
                }}},

                //Тест на сложение с отрицательным числом, которое в скобках
                {"5+(-4)", new ArrayList<ExpressionElement>() {{
                    add(new ExpressionElement(5));
                    add(new ExpressionElement(PLUS));
                    add(new ExpressionElement(OPEN_PARENTHESIS));
                    add(new ExpressionElement(-4));
                    add(new ExpressionElement(CLOSE_PARENTHESIS));
                }}},

                //Тест на опредиление всех типов мат-операций и скобок
                {"((5+(7-4))*2)/3", new ArrayList<ExpressionElement>() {{
                    add(new ExpressionElement(OPEN_PARENTHESIS));
                    add(new ExpressionElement(OPEN_PARENTHESIS));
                    add(new ExpressionElement(5));
                    add(new ExpressionElement(PLUS));
                    add(new ExpressionElement(OPEN_PARENTHESIS));
                    add(new ExpressionElement(7));
                    add(new ExpressionElement(MINUS));
                    add(new ExpressionElement((4)));
                    add(new ExpressionElement(CLOSE_PARENTHESIS));
                    add(new ExpressionElement(CLOSE_PARENTHESIS));
                    add(new ExpressionElement(MULTIPLY));
                    add(new ExpressionElement((2)));
                    add(new ExpressionElement(CLOSE_PARENTHESIS));
                    add(new ExpressionElement(DIVIDE));
                    add(new ExpressionElement((3)));
                }}}
        });
    }

    /**
     * Тесты с нормальными числовыми объектами и объектами мат-операций
     *
     * @throws Exception
     */
    @Test(timeout = 1000)
    public void test1toExpressionElementSet() throws Exception {

        assertEquals(expression, parser.toExpressionElementSet(stringExpression));
    }

    /**
     * Тест в котором проверяется генерация исключения в случае нахождения
     * в строковом выражении неизвестных символов
     *
     * @throws Exception
     */
    @Test(timeout = 1000, expected = IllegalArgumentException.class)
    public void test2toExpressionElementSet() throws Exception {

        assertEquals(expression, parser.toExpressionElementSet("1+a"));
    }
}