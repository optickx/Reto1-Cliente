/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package package1;

import app.App;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import ui.controller.RegistrationController;

/**
 *
 * @author 2dam
 */
public class launchRegistration extends javafx.application.Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/view/Registration.fxml"));
            Parent root = (Parent) loader.load();
            // Obtain the Sign In window controller
            RegistrationController controller = RegistrationController.class
                    .cast(loader.getController());
            controller.setStage(primaryStage);
            controller.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public static void main(String args[]){
        launch(args);
    }
}
