package ua.in.dris4ecoder.expression;

import static ua.in.dris4ecoder.expression.ElementType.*;

/**
 * Created by Alex Korneyko on 15.05.2016.
 */
public class ExpressionElement {
    private ElementType elementType;

    public double number = 0;

    private int floatDigits = 0;

    public ExpressionElement(ElementType elementType) {
        this.elementType = elementType;
    }

    public ExpressionElement(double number) {

        double floatPart = number - ((long) number);

        if(floatPart == 0.0) {
            elementType = INT;
        } else {
            elementType = DOUBLE;
            StringBuilder stringNumber = new StringBuilder(Double.toString(floatPart));
            floatDigits = stringNumber.length() - 2;
        }

        this.number = number;
    }

    public ElementType getElementSpecies(){
        if(elementType == INT || elementType == DOUBLE) return NUMBER;

        if(elementType == PLUS || elementType == MINUS || elementType == MULTIPLY || elementType == DIVIDE)
            return OPERATOR;

        return PARENTHESIS;
    }

    public ElementType getElementType(){
        return elementType;
    }

    public void addDigitToIntPart(int digit) {
        number = number * 10 + digit;
    }

    public void addDigitToFloatPart(int digit) {
        elementType = DOUBLE;
        floatDigits++;
        double floatPart = digit * Math.pow(10, (floatDigits * (-1)));
        number += floatPart;
        double rounded = Math.round(number * Math.pow(10, floatDigits));
        number = rounded * (Math.pow(10, (floatDigits * (-1))));
    }

    public static ExpressionElement getExpressionElement(char symbol) {
        switch (symbol) {
            case '+':
                return new ExpressionElement(PLUS);
            case '-':
                return new ExpressionElement(MINUS);
            case '*':
                return new ExpressionElement(MULTIPLY);
            case '/':
                return new ExpressionElement(DIVIDE);
        }

        throw new IllegalArgumentException("Incorrect operator");
    }

    @Override
    public String toString() {
        if (elementType == INT || elementType == DOUBLE) {
            return String.valueOf(number);
        }

        switch (elementType) {
            case PLUS:
                return "+";
            case MINUS:
                return "-";
            case DIVIDE:
                return "/";
            case MULTIPLY:
                return "*";
            case EQUALLY:
                return "=";
            case OPEN_PARENTHESIS:
                return "(";
            case CLOSE_PARENTHESIS:
                return ")";
            default:
                return "";
        }
    }

    @Override
    public ExpressionElement clone() {

        return new ExpressionElement(this.number);
    }

    public boolean equals(Object element) {
        return this.elementType == ((ExpressionElement) element).elementType
                && number == ((ExpressionElement) element).number;
    }

}
