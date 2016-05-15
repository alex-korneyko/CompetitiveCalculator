package ua.in.dris4ecoder;

import java.util.List;

import static ua.in.dris4ecoder.ElementType.*;

/**
 * Created by Alex Korneyko on 15.05.2016.
 */
public class SimpleCalculator {

    /**
     * Метод производящий только простые арифметические операции ('+', '-', '*', '/'
     * с двумя операндами
     *
     * @param expression expression for calculate
     * @return result of the expression
     */
    public double compute(List<ExpressionElement> expression) {
        double operand1 = 0;
        double operand2 = 0;
        double result = 0;


        if (expression == null || expression.size() == 0) {
            return 0;
        }

        if (expression.size() == 1) {
            return expression.get(0).number;
        }

        if (expression.size() == 2) {

            if (expression.get(0).getElementSpecies() == NUMBER) {
                return expression.get(0).number;
            } else if (expression.get(0).getElementType() == MINUS) {
                return expression.get(1).number * (-1);
            } else {
                return expression.get(1).number;
            }
        }

        if (expression.get(0).getElementSpecies() == NUMBER) {
            operand1 = expression.get(0).number;
        }

        if (expression.get(2).getElementSpecies() == NUMBER) {
            operand2 = expression.get(2).number;
        }

        switch (expression.get(1).getElementType()) {
            case PLUS:
                result = operand1 + operand2;
                break;
            case MINUS:
                result = operand1 - operand2;
                break;
            case MULTIPLY:
                result = operand1 * operand2;
                break;
            case DIVIDE:
                if (operand2 != 0) {
                    result = operand1 / operand2;
                } else {
                    throw new IllegalArgumentException("Error. Division by zero");
                }
                break;
        }

        return result;
    }
}
