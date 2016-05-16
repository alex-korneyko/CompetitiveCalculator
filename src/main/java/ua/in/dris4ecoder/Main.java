package ua.in.dris4ecoder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import static spark.Spark.*;

/**
 * Created by Alex Korneyko on 15.05.2016.
 */
public class Main {

    public static void main(String[] args) {
        port(880);

        get("/", (request, response) -> {
            String stringExpression;
            Map<String, Object> model = new HashMap<>();

            stringExpression = request.queryParams("expression");

            double result = 0;

            try {
                result = dResult(stringExpression);
            } catch (IllegalArgumentException e) {
                stringExpression = stringExpression + " >>> Error! (" + e.getMessage() + ")";
            }

            if (stringExpression != null && stringExpression.charAt(stringExpression.length() - 1) != ')') {
                stringExpression = stringExpression + " = " + result;
            }

            if (stringExpression != null && stringExpression.length() > 2
                    && stringExpression.charAt(stringExpression.length() - 1) == '0'
                    && stringExpression.charAt(stringExpression.length() - 2) == '.') {
                stringExpression = stringExpression.substring(0, stringExpression.length() - 2);
            }

            if (stringExpression == null) stringExpression="";

            System.out.println(stringExpression);

            model.put("result", stringExpression);
            return new ModelAndView(model, "templates/calculator.vtl");
        }, new VelocityTemplateEngine());
    }

    public static double dResult(String sExpression) throws IllegalArgumentException {
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
