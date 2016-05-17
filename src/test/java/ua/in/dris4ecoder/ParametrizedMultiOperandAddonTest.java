package ua.in.dris4ecoder;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ua.in.dris4ecoder.expression.ExpressionElement;
import ua.in.dris4ecoder.mathematics.MultiOperandAddon;
import ua.in.dris4ecoder.parsers.Parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Alex Korneyko on 16.05.2016.
 */
@RunWith(value = Parameterized.class)
public class ParametrizedMultiOperandAddonTest {

    MultiOperandAddon multiOperandAddon = new MultiOperandAddon();
    static Parser parser = new Parser();

    List<ExpressionElement> innerExpression = new ArrayList<>();
    List<ExpressionElement> outerExpression = new ArrayList<>();

    public ParametrizedMultiOperandAddonTest(List<ExpressionElement> innerExpression, List<ExpressionElement> outerExpression) {
        this.innerExpression = innerExpression;
        this.outerExpression = outerExpression;
    }

    @Test
    public void testCompute() throws Exception {

        assertEquals(outerExpression, multiOperandAddon.compute(innerExpression));
    }

    @Parameterized.Parameters
    public static Iterable<Object[]> getParameters() {
        return Arrays.asList(new Object[][]{
                {parser.toExpressionElementSet("2+3"), parser.toExpressionElementSet("2+3")},
                {parser.toExpressionElementSet("4+2+3"), parser.toExpressionElementSet("6+3")},
                {parser.toExpressionElementSet("4*2+3"), parser.toExpressionElementSet("8+3")},
                {parser.toExpressionElementSet("4+2+3"), parser.toExpressionElementSet("6+3")},
                {parser.toExpressionElementSet("2*3+5-2*3+12/4"), parser.toExpressionElementSet("5+3")}
        });
    }
}