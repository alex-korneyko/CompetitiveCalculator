package ua.in.dris4ecoder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex Korneyko on 15.05.2016.
 */
public class ParenthesesAddon {

    MultiOperandAddon multiOperandAddon = new MultiOperandAddon();
    SimpleCalculator simpleCalculator = new SimpleCalculator();

    /**
     * Метод, раскрывает все скобки в выражении и передаёт это выражение
     * методу, работающему с любым количеством операндов
     * Принцип такой - в выражении ищется первая закрывающая скобка,
     * потом в обратном напрвлении ищется открывающая скобка. В результате
     * получается выражение между скобками, которое может иметь любое количество операндов
     * и простые математические операции. Это выражение передаётся классу более простых
     * выражений, работающему с выражениями без скобок.
     * Возвращаемое значение заносится в выражение на место найденной открывающей скобки,
     * а всё остальное до закрывающей скобки, и включая саму скобку удаляется.
     * Т.е. в результате вместо выражения в скобках вставляется его результат
     * Пример: a+(b+c+d)+e, алгоритм находит b+c+d, предаёт это выражение следующему алгоритму,
     * тот возвращает результат r, и он вставляется вместо (b+c+d), т.е. получаем a+r+e.
     * Потом опять поиск скобок, если больше скобок нет, то работа передаётся следующему алгоритму.
     *
     * @param expression выражение для преобразования
     * @return result of the expression
     */
    public List<ExpressionElement> compute(List<ExpressionElement> expression) throws IllegalArgumentException {
        //Раскрытие всех скобок в выражении

        //"Простое" выражение, т.е. без скобок. Оно будет пердаваться следующему алгоритму
        List<ExpressionElement> simpleExpression = new ArrayList<>();

        //Динамический размер главного выражения, он будет изменятся по мере замены
        //выражений в скобках их результатом
        int dynamicExpressionSize = expression.size();

        //Поиск первой ЗАКРЫВАЮЩЕЙ скобки
        for (int i = 0; i < dynamicExpressionSize; i++) {
            if (expression.get(i).getElementType() == ElementType.CLOSE_PARENTHESIS) {
                //Поиск "назад" ОТКРЫВАЮЩЕЙ скобки (которая соответствует найденной закрывающей)
                for (int j = i - 1; j >= 0; j--) {
                    if (expression.get(j).getElementType() != ElementType.OPEN_PARENTHESIS) {
                        //Если дошли до начала выражения, а открывающая не найдена, то исключение
                        if (j == 0) {
                            expression.clear();
                            throw new IllegalArgumentException("To many closing parentheses!");
                        }
                        //Если открывающая всё ещё не найдена, то заполнение "простого" выражения
                        //Элементы всё время вставляются на первое место, но т.к. в текущем цикле
                        //движение производится назад, то "простое" формируется в правильном порядке
                        simpleExpression.add(0, expression.get(j));
                    } else {
                        //когда найдена открывающая скобка, то "простое" выражение передаётся
                        //более простому алгоритму
                        final double simpleResult = simpleCalculator.compute(multiOperandAddon.compute(simpleExpression));
                        //результат записывается на место найденной ОТКРЫВАЮЩЕЙ скобки
                        expression.set(j, new ExpressionElement(simpleResult));
                        //всё остальное, включая найденную ЗАКРЫВАЮЩУЮ скобку удаляется
                        //а также соответственно уменьшается значение динамического размера выражения
                        for (int k = j + 1; k <= i; k++) {
                            expression.remove(j + 1);
                            --dynamicExpressionSize;
                        }
                        //индексу первого цикла (который ищет закрывающие) присваивается индекс второго цикла,
                        //по которому записан результат вычисления. И поиск ЗАКРЫВАЮЩЕЙ продолжится со
                        //следующего элемента в первом цикле
                        i = j;
                        simpleExpression.clear();
                        break;
                    }
                }
            }
        }

        //После циклов поиска не должно остатся ни одной открывающей, но если всё же
        //остались, то исключение
        if (expression.contains(new ExpressionElement(ElementType.OPEN_PARENTHESIS))) {
            expression.clear();
            throw new IllegalArgumentException("To many opening parentheses!");
        }

        //Выражение уже без скобок
        return expression;
    }
}
