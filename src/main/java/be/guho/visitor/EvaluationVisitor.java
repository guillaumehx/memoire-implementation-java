package be.guho.visitor;

import be.guho.tree.BinaryOperationNode;
import be.guho.tree.NumberNode;
import be.guho.tree.UnaryOperationNode;

public class EvaluationVisitor implements NodeVisitor {

    @Override
    public double visitNumberNode(NumberNode node) {
        return node.getValue();
    }

    @Override
    public double visitBinaryOperationNode(BinaryOperationNode node) {
        double left = node.getLeft().accept(this);
        double right = node.getRight().accept(this);
        return switch (node.getOperator()) {
            case "+" -> left + right;
            case "-" -> left - right;
            case "*" -> left * right;
            case "/" -> left / right;
            case "^" -> Math.pow(left, right);
            default -> throw new IllegalArgumentException("Bad input : " + node.getOperator());
        };
    }

    @Override
    public double visitUnaryOperationNode(UnaryOperationNode node) {
        double operand = node.getOperand().accept(this);
        if ("sqrt".equals(node.getOperator())) {
            return Math.sqrt(operand);
        } else if ("neg".equals(node.getOperator())) {
            return -operand;
        } else {
            throw new IllegalArgumentException("Bad input : " + node.getOperator());
        }
    }
}
