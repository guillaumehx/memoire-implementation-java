package be.guho;

import be.guho.parser.ShuntingYard;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

public class ShuntingYardTest {

    @Test
    public void testImplicitMultiplication1() {
        String input = "3(5)";
        String output = convertListToString(ShuntingYard.infixToRPN(input));
        Assert.assertEquals("3 5 *", output);
    }

    @Test
    public void testImplicitMultiplication2() {
        String input = "(2+3)(4+1)";
        String output = convertListToString(ShuntingYard.infixToRPN(input));
        Assert.assertEquals("2 3 + 4 1 + *", output);
    }

    @Test
    public void testImplicitMultiplication3() {
        String input = "6 + (3+1)(2+2)";
        String output = convertListToString(ShuntingYard.infixToRPN(input));
        Assert.assertEquals("6 3 1 + 2 2 + * +", output);
    }

    @Test
    public void test1() {
        String input = "2 + 4 * 10 ^ 2 / 16 - 3";
        String output = convertListToString(ShuntingYard.infixToRPN(input));
        Assert.assertEquals("2 4 10 2 ^ * 16 / + 3 -", output);
    }

    @Test
    public void test2() {
        String input = "(2 + 1) * (8 / 2) / (18 * 2)";
        String output = convertListToString(ShuntingYard.infixToRPN(input));
        Assert.assertEquals("2 1 + 8 2 / * 18 2 * /", output);
    }

    @Test
    public void test3() {
        String input = "(2 + (4 - 3)) * (10 / 5)";
        String output = convertListToString(ShuntingYard.infixToRPN(input));
        Assert.assertEquals("2 4 3 - + 10 5 / *", output);
    }

    @Test
    public void test4() {
        String input = "((15 * 2^3 - (48 / 6 + 2^2)) * 3) / ((100 / 25 * 3) + (7^2 - 40))";
        String output = convertListToString(ShuntingYard.infixToRPN(input));
        Assert.assertEquals("15 2 3 ^ * 48 6 / 2 2 ^ + - 3 * 100 25 / 3 * 7 2 ^ 40 - + /", output);
    }

    @Test
    public void test5() {
        String input = "(((3 + 5) * (2 - 1)) + ((6 / 2) ^ 2)) * (4 + 1)";
        String output = convertListToString(ShuntingYard.infixToRPN(input));
        Assert.assertEquals("3 5 + 2 1 - * 6 2 / 2 ^ + 4 1 + *", output);
    }

    @Test
    public void test6() {
        String input = "(((((1 + 2) * 3) + 4) * (5 + (6 - 2))) ^ 2)";
        String output = convertListToString(ShuntingYard.infixToRPN(input));
        Assert.assertEquals("1 2 + 3 * 4 + 5 6 2 - + * 2 ^", output);
    }

    @Test
    public void test7() {
        String input = "((2^3^2) + ((4 + 1) * (6 - 2))) / ((7 + 3) * (2 + 2))";
        String output = convertListToString(ShuntingYard.infixToRPN(input));
        Assert.assertEquals("2 3 2 ^ ^ 4 1 + 6 2 - * + 7 3 + 2 2 + * /", output);
    }

    @Test
    public void test8() {
        String input = "((((1 + 2) + 3) + 4) + 5) * (((6 * 7) * 8) * 9)";
        String output = convertListToString(ShuntingYard.infixToRPN(input));
        Assert.assertEquals("1 2 + 3 + 4 + 5 + 6 7 * 8 * 9 * *", output);
    }

    @Test
    public void test9() {
        String input = "(((((10 - 2)^2) + ((3 + 1)^3)) * ((8 / 4)^2)))";
        String output = convertListToString(ShuntingYard.infixToRPN(input));
        Assert.assertEquals("10 2 - 2 ^ 3 1 + 3 ^ + 8 4 / 2 ^ *", output);
    }

    @Test
    public void test10() {
        String input = "(((((1+2)*(3+4))-((5+6)*(7+8)))+((9+10)*(11+12)))^2)";
        String output = convertListToString(ShuntingYard.infixToRPN(input));
        Assert.assertEquals("1 2 + 3 4 + * 5 6 + 7 8 + * - 9 10 + 11 12 + * + 2 ^", output);
    }

    @Test
    public void test11() {
        String input = "sqrt(12996)";
        String output = convertListToString(ShuntingYard.infixToRPN(input));
        Assert.assertEquals("12996 sqrt", output);
    }

    @Test
    public void test12() {
        String input = "(((sqrt(12996) + sqrt(9) - sqrt(4)) * sqrt(16)) / sqrt(25)) + 1";
        String output = convertListToString(ShuntingYard.infixToRPN(input));
        Assert.assertEquals("12996 sqrt 9 sqrt + 4 sqrt - 16 sqrt * 25 sqrt / 1 +", output);
    }

    @Test
    public void test13() {
        String input = "-3";
        String output = convertListToString(ShuntingYard.infixToRPN(input));
        Assert.assertEquals("3 neg", output);
    }

    @Test
    public void test14() {
        String input = "-3-4";
        String output = convertListToString(ShuntingYard.infixToRPN(input));
        Assert.assertEquals("3 neg 4 -", output);
    }
    
    private String convertListToString(final List<Object> input) {
        return input.stream().map(Object::toString).collect(Collectors.joining(" "));
    }

}
