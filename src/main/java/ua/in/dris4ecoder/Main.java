package ua.in.dris4ecoder;

import java.util.List;
import java.util.Scanner;

/**
 * Created by Alex Korneyko on 15.05.2016.
 */
public class Main {

    public static void main(String[] args) {

        List<ExpressionElement> expression;

        Parser parser = new Parser();
        SimpleCalculator simpleCalculator = new SimpleCalculator();
        MultiOperandAddon multiOperandAddon = new MultiOperandAddon();
        ParenthesesAddon parenthesesAddon = new ParenthesesAddon();

        String line = "";
        while (!line.equals("0")) {
            System.out.print("(Type 0 for exit) --> ");
            Scanner scanner = new Scanner(System.in);
            line = scanner.nextLine();

            expression = parser.toExpressionElementSet(line);
            parenthesesAddon.compute(expression);
            multiOperandAddon.compute(expression);

            System.out.println(simpleCalculator.compute(expression));
        }
    }
}
