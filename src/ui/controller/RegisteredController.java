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

/**
 * A controller class for managing the registered user view in a JavaFX
 * application. It is responsible for handling the UI components for the main
 * window
 *
 * @author Alex Epelde
 * @author Dani
 */
public class RegisteredController extends GenericController {

    /**
     * Represents a JavaFX Label associated with a greeting message in the user
     * interface.
     */
    @FXML
    private Label greetingLabel;

    /**
     * Represents a JavaFX Button designed for user logout functionality.
     */
    @FXML
    private Button logoutButton;

    /**
     * Represents a static instance of the User class, storing information about
     * the currently logged-in user. This field is intended for internal use
     * within the class or its subclasses.
     */
    protected static User loggedUser;

    /**
     * Sets the currently logged-in user, updating the 'loggedUser' field with
     * the provided User object.
     *
     * @param loggedUser The User object representing the user to be set as
     * logged-in.
     */
    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    /**
     * Method that initialises the window on login, sets a title and greets the
     * user.
     *
     * @param root
     */
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

    /**
     * Method to handle the event when the Log Out button is pressed. What it
     * does is to open the LoginView window
     *
     * @param event
     */
    private void handleLogoutButton(ActionEvent event) {
        try {
            FXMLLoader loader
                    = new FXMLLoader(getClass().getClassLoader().getResource("ui/view/LoginView.fxml"));
            Parent root = (Parent) loader.load();
            //Obtain the Sign In window controller
            LoginController controller
                    = LoginController.class
                            .cast(loader.getController());
            controller.setStage(stage);
            controller.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
