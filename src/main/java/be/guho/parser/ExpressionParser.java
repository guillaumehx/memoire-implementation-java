package be.guho.parser;

import be.guho.tree.BinaryOperationNode;
import be.guho.tree.ExpressionNode;
import be.guho.tree.NumberNode;
import be.guho.tree.UnaryOperationNode;

import java.util.List;
import java.util.Stack;

public class ExpressionParser {

    public ExpressionNode parse(String expression) {
        List<Object> outputQueue = ShuntingYard.infixToRPN(expression);
        Stack<ExpressionNode> stack = new Stack<>();

        for (Object item : outputQueue) {
            if (item instanceof Integer) {
                stack.push(new NumberNode((Integer) item));
            } else if (item instanceof String op) {
                if (op.equals("sqrt") || op.equals("neg")) {
                    ExpressionNode operand = stack.pop();
                    stack.push(new UnaryOperationNode(op, operand));
                } else {
                    ExpressionNode right = stack.pop();
                    ExpressionNode left = stack.pop();
                    stack.push(new BinaryOperationNode(op, left, right));
                }
            }
        }

        if (stack.size() != 1) {
            throw new IllegalArgumentException("Invalid expression");
        }

        return stack.pop();
    }
}
