package com.tyshkun.calculatorapplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class CalculatorApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CalculatorApplication.class.getResource("calc-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add((Objects.requireNonNull(getClass().getResource("/com/tyshkun/calculatorapplication/style.css"))).toExternalForm());
        stage.setScene(scene);
        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/com/tyshkun/calculatorapplication/calculate.png"))));
        stage.setTitle("Best calculator!");
        stage.setMinWidth(306);
        stage.setMinHeight(435);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}