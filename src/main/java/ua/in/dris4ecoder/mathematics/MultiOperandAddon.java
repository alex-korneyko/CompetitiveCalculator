package ua.in.dris4ecoder.mathematics;

import ua.in.dris4ecoder.expression.ElementType;
import ua.in.dris4ecoder.expression.ExpressionElement;

import java.util.List;

/**
 * Created by Alex Korneyko on 15.05.2016.
 */
public class MultiOperandAddon {

    /**
     * Метод, определяющий приоритетность операций в выражениии
     * и передающий операнды парами в метод работающий только с парами операндов
     * Принцип такой - в выражении сначала берётся первые два операнда и операция
     * между ними, а потом, если это низкоприоритетная операция, ищется
     * высокоприоритетная операция, и если находится, то берутся операнды
     * этой операции. Далее найденые два операнда и операция передаются более простому
     * классу, который работает только с двумя операндами. Возвращённый результат
     * записывается в главное выражение вместо вычисленых двух операндов и операции
     * между ними. Так продолжается, пока главное выражение не сократится до
     * двух операндов и операции. Потом и оно передаётся в следующий,
     * класс, который и посчитает уже конечный результат.
     * Пример: a+b*c+d, алгоритм находит b*c и передаёт следующему алгоритму,
     * тот возвращает результат r, и он вставляется в главное выражение вместо b*c,
     * т.е. получаем a+r+d. Потом передаётся уже a+r, результат r2 вставляется в
     * главное выражение: r2+d, и наконец это выражение передаётся следующему алгоритму
     * и получается конечный результат
     *
     * @param expression выражение для вычесления (коллекция объектов выражения)
     * @return result of the expression
     */
    public List<ExpressionElement> compute(List<ExpressionElement> expression) {

        SimpleCalculator simpleCalculator = new SimpleCalculator();

        //Простое выражение из двух операндов
        List<ExpressionElement> simpleExpression;

        //Повторение, пока длина главного выражения больше 3-ёх
        while (expression.size() > 3) {

            int startIndexSimpleExp = 0;
            //Помещение в простое выражение первых двух операндов из главного выражения и операция между ними
            simpleExpression = expression.subList(0, 3);

            //Поиск более приоритетных операций в главном выражении начиная с четвёртого элемента
            for (int i = 3; i < expression.size(); i++) {

                //Если текущая операция в простом выражении плюс или минус, то ищем более приоритетные операции
                if (simpleExpression.get(1).getElementType() == ElementType.PLUS ||
                        simpleExpression.get(1).getElementType() == ElementType.MINUS) {

                    //Если найдена более приоритетная операция, то она заносится в простое выражение
                    //и операнды этой операции тоже
                    if (expression.get(i).getElementType() == ElementType.MULTIPLY ||
                            expression.get(i).getElementType() == ElementType.DIVIDE) {

                        simpleExpression = expression.subList(i - 1, i + 2);
                        //запоминание начал простого выражения в главном выражении
                        startIndexSimpleExp = i - 1;
                        //прекращение цикла, т.к. дальше искать приоритетные операции нет смысла
                        break;
                    }
                }
            }
            //Передача простого выражения в декорируемый класс, который работает только с двумя операндами
            double result = simpleCalculator.compute(simpleExpression);

            //Замена простого выражения в главном выражении результатом вычисления этого простого выражения
            expression.set(startIndexSimpleExp, new ExpressionElement(result));
            expression.remove(startIndexSimpleExp + 1);
            expression.remove(startIndexSimpleExp + 1);
        }

        //Когда длина главного выражения стала меньше или равна 3,
        return expression;
    }
}
