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
 * Created by Alex Korneyko on 16.05.2016.
 */
@RunWith(value = Parameterized.class)
public class ParametrizedMultiOperandAddonTest {

    MultiOperandAddon multiOperandAddon = new MultiOperandAddon();
    List<ExpressionElement> innerExpression = new ArrayList<>();
    List<ExpressionElement> outerExpression = new ArrayList<>();

    public ParametrizedMultiOperandAddonTest(List<ExpressionElement> innerExpression, List<ExpressionElement> outerExpression) {
        this.innerExpression = innerExpression;
        this.outerExpression = outerExpression;
    }

    @Parameterized.Parameters
    public static Iterable<Object[]> getParameters() {
        return Arrays.asList(new Object[][]{
                {new ArrayList<ExpressionElement>() {{
                    add(new ExpressionElement(10));
                    add(new ExpressionElement(MULTIPLY));
                    add(new ExpressionElement(OPEN_PARENTHESIS));
                    add(new ExpressionElement(15));
                    add(new ExpressionElement(PLUS));
                    add(new ExpressionElement(25));
                    add(new ExpressionElement(CLOSE_PARENTHESIS));
                }}
                },
                {new ArrayList<ExpressionElement>() {{
                    add(new ExpressionElement(10));
                    add(new ExpressionElement(MULTIPLY));
                    add(new ExpressionElement(40));
                }}
                }
        });
    }

    @Test
    public void testCompute() throws Exception {

        assertEquals(outerExpression, multiOperandAddon.compute(innerExpression));
    }
}