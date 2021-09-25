package com.tyshkun.calculatorapplication;

import org.apache.commons.lang3.StringUtils;

public class Model {

    public static double getSecondOp(double op2, double op1, String operation) {
        switch (operation) {
            case "x²" -> op2 = Math.pow(op2, 2);
            case "√" -> {
                if (op2 < 0) throw new ArithmeticException("Ошибка! Корень из отрицательного числа");
                op2 = Math.sqrt(op2);
            }
            case "÷" -> {
                if (op1 == 0) throw new ArithmeticException("Ошибка! Деление на ноль");
                op2 = op2 / op1;
            }
            case "×" -> op2 = op2 * op1;
            case "-" -> op2 = op2 - op1;
            case "+" -> op2 = op2 + op1;
        }
        return op2;
    }

    public static String replaceOnDot(String str) {
        if (str.contains(",")) {
            str = str.replace(",", ".");
        }
        return str;
    }

    public static String replaceOnComma(String str) {
        if (str.contains(".")) {
            str = str.replace(".", ",");
        }
        return str;
    }

    public static boolean tryParseInt(String value) {
        return StringUtils.indexOfAny(value, '1', '2', '3', '4', '5', '6', '7', '8', '9') == -1;
    }
}
