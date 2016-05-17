package ua.in.dris4ecoder.mathematics;

import ua.in.dris4ecoder.expression.ExpressionElement;
import ua.in.dris4ecoder.parsers.Parser;

import java.util.List;

/**
 * Created by Alex Korneyko on 17.05.2016.
 */
public class Calculation {

    public static double getDoubleResult(String sExpression) throws IllegalArgumentException {
        if (sExpression == null) return 0;

        List<ExpressionElement> expression;

        Parser parser = new Parser();
        SimpleCalculator simpleCalculator = new SimpleCalculator();
        MultiOperandAddon multiOperandAddon = new MultiOperandAddon();
        ParenthesesAddon parenthesesAddon = new ParenthesesAddon();

        expression = parser.toExpressionElementSet(sExpression);
        parenthesesAddon.compute(expression);
        multiOperandAddon.compute(expression);
        return simpleCalculator.compute(expression);
    }
}
