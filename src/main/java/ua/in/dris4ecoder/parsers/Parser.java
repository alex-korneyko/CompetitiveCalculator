package ua.in.dris4ecoder.parsers;

import ua.in.dris4ecoder.expression.ElementType;
import ua.in.dris4ecoder.expression.ExpressionElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex Korneyko on 15.05.2016.
 */
public class Parser {

    /**
     * Преобразование строки с выражением в набор "математических" объектов,
     * который и будет служить для дальнейших приведений и расчётов
     *
     * @param stringExpression выражение в виде строки
     */
    public List<ExpressionElement> toExpressionElementSet(String stringExpression) throws IllegalArgumentException {

        List<ExpressionElement> expression = new ArrayList<>();

        char[] charExpression = stringExpression.toCharArray();
        boolean newNumber = true;               //флаг начала формирования очередного числа
        boolean negativeNumber = false;         //флаг отрицательного числа
        boolean dot = false;                    //Флаг десятичной точки

        for (char symbol : charExpression) {

            if(symbol == ' ') continue;         //Игнор пробелов

            //формирование чисел
            if (symbol >= 48 && symbol <= 57) {
                if (newNumber) {
                    expression.add(new ExpressionElement(symbol - 48));
                    newNumber = false;
                } else {
                    if (dot) {
                        expression.get(expression.size() - 1).addDigitToFloatPart(symbol - 48);
                    } else {
                        expression.get(expression.size() - 1).addDigitToIntPart(symbol - 48);
                    }
                }
                continue;
            }
            //Найдена десятичная точка
            if (symbol == '.' || symbol == ',') {
                dot = true;
                continue;
            }

            //определение мат. операций
            if (symbol == '+' || symbol == '-'
                    || symbol == '*' || symbol == '/') {
                if (newNumber) {
                    if (symbol == '-') {
                        negativeNumber = true;
                    }
                } else {
                    ExpressionElement tmp = expression.get(expression.size() - 1);
                    tmp.number = tmp.number * (negativeNumber ? -1 : 1);
                    negativeNumber = false;
                    newNumber = true;
                    dot = false;
                    expression.add(ExpressionElement.getExpressionElement(symbol));
                }
                continue;
            }

            //определение скобок
            if (symbol == '(') {
                expression.add(new ExpressionElement(ElementType.OPEN_PARENTHESIS));
                newNumber = true;
                continue;
            }

            if (symbol == ')') {
                ExpressionElement tmp = expression.get(expression.size() - 1);
                tmp.number = tmp.number * (negativeNumber ? -1 : 1);
                negativeNumber = false;
                expression.add(new ExpressionElement(ElementType.CLOSE_PARENTHESIS));
                continue;
            }

            throw new IllegalArgumentException("Unknown symbol: " + symbol);
        }

        if ((expression.size() > 0
                && (expression.get(expression.size() - 1)).getElementSpecies() == ElementType.NUMBER
                && negativeNumber)) {
            expression.set(expression.size() - 1, new ExpressionElement(expression.get(expression.size() - 1).number * (-1)));
        }

        return expression;
    }


}
