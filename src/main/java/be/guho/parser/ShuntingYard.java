package be.guho.parser;

import java.util.*;

public class ShuntingYard {

    private static final Map<String, Integer> precedence = Map.of(
            "+", 1, "-", 1, "*", 2, "/", 2, "^", 4
    );

    private static final Set<String> functions = Set.of("sqrt");

    public static List<Object> infixToRPN(String expression) {
        expression = preprocess(expression);
        List<Object> outputQueue = new ArrayList<>();
        Stack<String> operatorStack = new Stack<>();

        String[] tokens = expression.split("(?<=[-+*/^()])|(?=[-+*/^()])|(?=sqrt)|(?<=sqrt)");

        for (String token : tokens) {
            if (token.isBlank()) {
                continue;
            }

            if (token.matches("\\d+")) {
                outputQueue.add(Integer.parseInt(token));
            } else if (functions.contains(token)) {
                operatorStack.push(token);
            } else if (precedence.containsKey(token)) {
                while (!operatorStack.isEmpty()
                        && !"(".equals(operatorStack.peek())
                        && precedence.containsKey(operatorStack.peek())
                        && (precedence.get(operatorStack.peek()) > precedence.get(token)
                        || (precedence.get(operatorStack.peek()).equals(precedence.get(token)) && !token.equals("^")))) {
                    outputQueue.add(operatorStack.pop());
                }
                operatorStack.push(token);
            } else if ("(".equals(token)) {
                operatorStack.push(token);
            } else if (")".equals(token)) {
                while (!operatorStack.isEmpty() && !"(".equals(operatorStack.peek())) {
                    outputQueue.add(operatorStack.pop());
                }
                if (!operatorStack.isEmpty() && "(".equals(operatorStack.peek())) {
                    operatorStack.pop();
                }
                if (!operatorStack.isEmpty() && functions.contains(operatorStack.peek())) {
                    outputQueue.add(operatorStack.pop());
                }
            }
        }

        while (!operatorStack.isEmpty()) {
            outputQueue.add(operatorStack.pop());
        }

        return outputQueue;
    }

    private static String preprocess(String expression) {
        return expression
                .replaceAll("\\s+", "")
                .replaceAll("(\\d)(\\()", "$1*$2")
                .replaceAll("(\\))(\\d)", "$1*$2")
                .replaceAll("(\\))(\\()", "$1*$2");
    }
}
