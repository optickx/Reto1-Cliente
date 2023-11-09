package ui.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import app.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import packets.User;

public class RegisteredController extends GenericController {

    @FXML
    private Label greetingLabel;

    @FXML
    private Button logoutButton;

    protected static User loggedUser;

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    public void initStage(Parent root) {
        // We display a window opening information message with LOGGER
        LOGGER.info("Initialazing " + " window.");
        // We create a Scene containing the main content and set it in the main window
        // represented by the Stage object
        Scene scene = new Scene(root);
        stage.setScene(scene);

        // We title the window and make it not resizable
        stage.setTitle("Main Window");
        stage.setResizable(false);
        stage.setMaximized(false);

        greetingLabel.setText("Welcome, " + loggedUser.getFullName());
        // Disable the SignUp and show passwords buttons so that we can control that
        // they are only enabled when the fields are filled in
        logoutButton.setOnAction(this::handleLogoutButton);
    }

    private void handleLogoutButton(ActionEvent event){
        try {
            FXMLLoader loader = 
                new FXMLLoader(getClass().getClassLoader().getResource("ui/view/LoginView.fxml"));
            Parent root = (Parent) loader.load();
            //Obtain the Sign In window controller
            LoginController controller = 
                LoginController.class
                    .cast(loader.getController());
            controller.setStage(stage);
            controller.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}