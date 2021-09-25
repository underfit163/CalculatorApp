package com.tyshkun.calculatorapplication;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class CalculatorController {
    public TextField calcTextField;
    public Button sevenButton;
    public Button eightButton;
    public Button nineButton;
    public Button fourButton;
    public Button fiveButton;
    public Button sixButton;
    public Button oneButton;
    public Button twoButton;
    public Button threeButton;
    public Button zeroButton;
    public Button equalButton;
    public Button plusButton;
    public Button minusButton;
    public Button divButton;
    public Button mulButton;
    public Button commaButton;
    public Button cleanButton;

    private double firstOp = 0;
    private double secondOp = 0;
    private String result = "";
    private String lastOperation = "";

    private boolean decimalClick = true;
    private boolean firstDigitClick = true;
    private boolean countOpClick = false;
    private boolean isFirstOpClick = true;
    private boolean newDigit = false;
    private boolean flagZero = false;

    public void handlerDigitAction(ActionEvent actionEvent) {
        String digit = ((Button) actionEvent.getSource()).getText();

        if (lastOperation.equals("√") || lastOperation.equals("x²") || lastOperation.equals("=")) {
            secondOp = 0;
            firstOp = 0;
            //Будет введена первая цифра?
            firstDigitClick = true;
            //Была ли уже нажата вторая операция?
            countOpClick = false;
            //Можно ли нажать запятую?
            decimalClick = true;
            //Это самая первая нажатая операция?
            isFirstOpClick = true;
            newDigit = false;
            flagZero = false;
            calcTextField.setText(digit);
            result = "";
            lastOperation = "";
        }

        if (digit.equals("0") && firstDigitClick) {
            digit = "";
        } else if (firstDigitClick) {
            calcTextField.setText("");
            firstDigitClick = false;
        } else if (newDigit) {
            if (digit.equals("0")) {
                if (!flagZero) {
                    calcTextField.setText("");
                    decimalClick = true;
                    flagZero = true;
                } else {
                    digit = "";
                }
            } else {
                if (!flagZero) {
                    calcTextField.setText("");
                } else if (decimalClick) {
                    calcTextField.setText("");
                }
                newDigit = false;
                flagZero = false;
                decimalClick = true;
            }
        }

        String oldText = calcTextField.getText();
        String newText = oldText + digit;
        calcTextField.setText(newText);
        calcTextField.positionCaret(calcTextField.getText().length());
        countOpClick = false;
    }

    public void handlerDecimalAction(ActionEvent actionEvent) {
        if (decimalClick) {
            String decimalObject = ((Button) actionEvent.getSource()).getText();
            String oldText = calcTextField.getText();
            String newText = oldText + decimalObject;
            calcTextField.setText(newText);
            firstDigitClick = false;
            decimalClick = false;
            newDigit = false;
            flagZero = false;
        }
    }

    public void handlerGeneralAction(ActionEvent actionEvent) {
        String opText = Model.replaceOnDot(calcTextField.getText());
        if (isFirstOpClick) {
            secondOp = Double.parseDouble(opText);
            //Была ли уже нажата операция?
            countOpClick = true;
            //Это самая первая нажатая операция?
            isFirstOpClick = false;
        }
        String operation = ((Button) actionEvent.getSource()).getText();
        switch (operation) {
            case "C" -> {
                secondOp = 0;
                firstOp = 0;
                calcTextField.setText("0");
                //Будет введена первая цифра?
                firstDigitClick = true;
                //Была ли уже нажата вторая операция?
                countOpClick = false;
                //Была ли нажата запятая?
                decimalClick = true;
                //Это самая первая нажатая операция?
                isFirstOpClick = true;
                newDigit = false;
                flagZero = false;
                result = "";
                lastOperation = "";
            }
            case "x²" -> {
                if (!isFirstOpClick && !lastOperation.equals("x²") && !lastOperation.equals("√") && !countOpClick) {
                    firstOp = Double.parseDouble(opText);
                    secondOp = Model.getSecondOp(secondOp, firstOp, lastOperation);
                }
                secondOp = Model.getSecondOp(secondOp, firstOp, operation);
                result = String.valueOf(secondOp);
                if (Model.tryParseInt(result.split("\\.")[1])) {
                    result = result.split("\\.")[0];
                }
                calcTextField.setText(Model.replaceOnComma(String.valueOf(result)));
                newDigit = true;
                flagZero = false;
                //Будет введена первая цифра?
                firstDigitClick = false;
                //Была ли нажата запятая?
                decimalClick = false;
                lastOperation = operation;
            }
            case "√" -> {
                if (!isFirstOpClick && !lastOperation.equals("x²") && !lastOperation.equals("√") && !countOpClick) {
                    firstOp = Double.parseDouble(opText);
                    secondOp = Model.getSecondOp(secondOp, firstOp, lastOperation);
                }
                try {
                    secondOp = Model.getSecondOp(secondOp, firstOp, operation);
                } catch (ArithmeticException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                    alert.showAndWait();
                }
                result = String.valueOf(secondOp);
                if (Model.tryParseInt(result.split("\\.")[1])) {
                    result = result.split("\\.")[0];
                }
                calcTextField.setText(Model.replaceOnComma(String.valueOf(result)));
                newDigit = true;
                flagZero = false;
                //Будет введена первая цифра?
                firstDigitClick = false;
                //Была ли нажата запятая?
                decimalClick = false;
                lastOperation = operation;
            }
            case "÷", "-", "+", "×", "=" -> {
                if (!lastOperation.equals("x²") && !lastOperation.equals("√") && !lastOperation.equals("=")) {
                    if (!countOpClick) {
                        firstOp = Double.parseDouble(opText);
                        try {
                            secondOp = Model.getSecondOp(secondOp, firstOp, lastOperation);
                        } catch (ArithmeticException e) {
                            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                            alert.showAndWait();
                        }
                        result = String.valueOf(secondOp);
                        if (Model.tryParseInt(result.split("\\.")[1])) {
                            result = result.split("\\.")[0];
                        }
                        calcTextField.setText(Model.replaceOnComma(String.valueOf(result)));
                        countOpClick = true;
                    }
                    newDigit = true;
                    flagZero = false;
                    //Будет введена первая цифра?
                    firstDigitClick = false;
                    //Была ли нажата запятая?
                    decimalClick = false;
                }
                lastOperation = operation;
            }
        }
    }

    public void pressKey(KeyEvent keyEvent) {
        KeyCode keyCode = keyEvent.getCode();
        switch (keyCode) {
            case DIGIT0, NUMPAD0 -> zeroButton.fire();
            case DIGIT1, NUMPAD1 -> oneButton.fire();
            case DIGIT2, NUMPAD2 -> twoButton.fire();
            case DIGIT3, NUMPAD3 -> threeButton.fire();
            case DIGIT4, NUMPAD4 -> fourButton.fire();
            case DIGIT5, NUMPAD5 -> fiveButton.fire();
            case DIGIT6, NUMPAD6 -> sixButton.fire();
            case DIGIT7, NUMPAD7 -> sevenButton.fire();
            case DIGIT8, NUMPAD8 -> eightButton.fire();
            case DIGIT9, NUMPAD9 -> nineButton.fire();
            case DELETE -> cleanButton.fire();
            case ADD -> plusButton.fire();
            case SUBTRACT -> minusButton.fire();
            case MULTIPLY -> mulButton.fire();
            case DIVIDE -> divButton.fire();
            case COMMA, PERIOD -> commaButton.fire();
            case EQUALS -> equalButton.fire();
        }
    }
}