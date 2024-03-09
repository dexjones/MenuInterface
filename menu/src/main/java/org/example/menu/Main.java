package org.example.menu;

import javafx.application.Application;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.geometry.Insets;
import javafx.scene.control.Alert.AlertType;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Main extends Application {
    private MenuBar mBar;
    private Menu m1;
    private BorderPane bPane;
    private MenuItem item1;
    private MenuItem item2;
    private MenuItem item3;
    private MenuItem item4;
    private TextArea textArea = new TextArea();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss");
    private final int randomNum = new Random().nextInt(120) + 60;
    private final Color randomGreen = Color.hsb(randomNum, 1.0, 1.0);
    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("Main Menu");
        bPane = new BorderPane();
        Scene scene = null;

        mBar = new MenuBar();
        m1 = new Menu("Options");
        item1 = new MenuItem("Current Date/Time");
        item2 = new MenuItem("Log Date/Time");
        item3 = new MenuItem("Change Background Color");
        item4 = new MenuItem("Exit Program");
        m1.getItems().addAll(item1, item2, item3, item4);
        mBar.getMenus().add(m1);
        textArea.setEditable(false);

        bPane.setPadding(new Insets(10, 10, 10, 10));
        bPane.setTop(mBar);
        bPane.setCenter(textArea);

        scene = new Scene(bPane, 400, 300);
        stage.setScene(scene);
        stage.show();

        item1.setOnAction(e -> { // 1st menu item -Will add current date time in dd-MMM-yy HH:mm:ss and will append most current date time each time
            if (!textArea.getText().isEmpty()) {
                textArea.appendText("\n");
            }
            textArea.appendText("Date and Time: " + LocalDateTime.now().format(formatter));
            });
        item2.setOnAction(e -> logDateTime(textArea.getText())); // 2nd menu item - will log date time to log.txt
        item3.setOnAction(e -> bPane.setStyle("-fx-background-color: #" + randomGreen.toString().substring(2, 8) + ";")); // 3rd menu item - changes background random green for remainder of application
        item4.setOnAction(e -> System.exit(0)); // exits
    }
    private void logDateTime(String dateTime) { // logs date and time to log.txt
        try (FileWriter fw = new FileWriter("log.txt")) {
            fw.write(dateTime);
            Alert alert = new Alert(AlertType.INFORMATION, "Date and time logged to log.txt file"); // If logged correctly it will tell user
            alert.showAndWait();
        }
        catch (IOException e) {
            System.out.println("Error. Unable to write to file.");
            System.out.println(e.getMessage());
            Alert alert = new Alert(AlertType.WARNING, "Unable to log date and time to log.txt file"); // If not logged it will warn the user
            alert.showAndWait();
        }
    }
}
