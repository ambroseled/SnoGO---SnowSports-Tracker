package seng202.team5.Control;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class alertController {

    private FXMLLoader loader = new FXMLLoader();
    private Class c = getClass();

    public void homeButtonPress() throws IOException {
        System.out.println("Home button pressed");
        appController.changeScene("/View/firstPage.fxml", c);
    }
}
