package seng202.team5.Control;

import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class calController {

    private FXMLLoader loader = new FXMLLoader();
    private Class c = getClass();

    public void homeButtonPress() throws IOException {
        System.out.println("Home button pressed");
        appController.changeScene("/View/firstPage.fxml", c);
    }
}
