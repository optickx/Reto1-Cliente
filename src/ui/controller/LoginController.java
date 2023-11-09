package ui.controller;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import app.App;
import exceptions.BadCredentialsException;
import exceptions.EmptyFieldException;
import exceptions.IncorrectFormatException;
import exceptions.NoSuchUserException;
import exceptions.PasswordTooShortException;
import exceptions.ServerCapacityException;
import exceptions.ServerErrorException;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import logic.business.SignableFactory;
import packets.User;

/**
 * @author dani
 * @author alex
 */
public class LoginController extends GenericController {

    /**
     * Label for displaying error messages for both login and password.
     */
    @FXML
    private Label loginErrorLabel, passwordErrorLabel;
    /**
     * Text fields where user will input the login password.
     */
    @FXML
    private TextField loginTextField, passwordTextField;
    /**
     * Buttons of the window: confirmation, exit and show password.
     */
    @FXML
    private Button confirmButton, exitButton, showPasswordButton;
    /**
     * Password field (where text is hidden)
     */
    @FXML
    private PasswordField passwordField;
    /**
     * Hyper link that opens the Registration window
     */
    @FXML
    private Hyperlink signUpLink;

    /**
     * method that initiates the stage and sets/prepares the values inside of
     * it.
     *
     * @param root
     */
    public void initStage(Parent root) {
        LOGGER.info("Initialazing " + " window.");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        signable = SignableFactory.getImplementation();

        //set the the default button
        confirmButton.setDefaultButton(true);

        // Set properties
        // Window dimensions are set
        stage.setResizable(false);
        stage.setMaximized(false);

        stage.setTitle("Login Window");
        // Window name is set.
        stage.setResizable(false);

        // Elements and properties are set.
        passwordTextField.setVisible(false);
        // Button texts
        confirmButton.setText("Confirm");
        exitButton.setText("Exit");
        // Password text field (used if the user wants to see password) is hidden
        passwordTextField.setVisible(false);
        // Buttons are set disabled
        confirmButton.setDisable(false);
        exitButton.setDisable(false);
        showPasswordButton.setDisable(false);
        // Error labels are set hidden
        loginErrorLabel.setVisible(false);
        passwordErrorLabel.setVisible(false);

        loginTextField.textProperty().addListener(this::handleUsername);
        passwordField.textProperty().addListener(this::handlePassword);
        showPasswordButton.setOnAction(this::handleShowPassword);
        exitButton.setOnAction(this::handleExitButton);
        confirmButton.setOnAction(this::handleConfirmButtonAction);
        signUpLink.setOnAction(this::handleHyperlinkPressed);

        stage.setOnCloseRequest(this::handleCloseRequest);

        // This code allows closing the window with Esc key
        stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            // Adds an event handler that records every
            // time the escape key is pressed
            if (KeyCode.ESCAPE == event.getCode()) {
                closeRequest();
            }

        });
        stage.show();
        // Logger update.
        LOGGER.info("Window opened.");
    }

    /**
     * case of changing the username field
     *
     * @param observable in this case, username
     * @param oldValue previous username in field
     * @param newValue new username in field.
     */
    protected void handleUsername(ObservableValue observable,
            String oldValue, String newValue) {
        try {
            newValue = newValue.trim();
            if (isNotEmpty(newValue) || !isTooLong(newValue)) { // correct format
                validateUsername(newValue); // custom method to handle
                loginErrorLabel.setVisible(false);
            }
        } catch (EmptyFieldException e) {
            loginErrorLabel.setText("Username cannot be empty");
            loginErrorLabel.setVisible(true);
        } catch (IncorrectFormatException e) {
            loginErrorLabel.setText("Username must be a valid email");
            loginErrorLabel.setVisible(true);
        }
    }

    /**
     * case of changing the password text field
     *
     * @param observable in this case, password
     * @param oldValue previous password in password field
     * @param newValue new password in field
     */
    private void handlePassword(ObservableValue observable,
            String oldValue,
            String newValue) {
        try {
            validatePassword(newValue);
            passwordErrorLabel.setVisible(false);
        } catch (IncorrectFormatException e) {
            passwordErrorLabel.setText("Password too long");
            passwordErrorLabel.setVisible(true);
        } catch (PasswordTooShortException e) {
            passwordErrorLabel.setText("Password too short");
            passwordErrorLabel.setVisible(true);
        } catch (EmptyFieldException e) {
            passwordErrorLabel.setText("Password cannot be empty");
            passwordErrorLabel.setVisible(true);
        }
    }

    /**
     * case of pressing exit button.
     *
     * @param event
     */
    private void handleExitButton(ActionEvent event) {
        Logger.getLogger(App.class.getName()).info("Exit Button pressed");
        closeRequest();
    }

    /**
     * case of "show password" button being pressed
     *
     * @param event
     */
    private void handleShowPassword(ActionEvent event) {
        Logger.getLogger(App.class.getName()).info("Show Password button pressed");
        if (passwordTextField.isVisible()) {
            // handled in custom method
            showPassword(true);
        } else {
            showPassword(false);
        }
    }

    /**
     * method that shows the password (hide Password Field and show Text Field)
     *
     * @param visible
     */
    private void showPassword(boolean visible) {
        if (visible) {
            passwordField.setText(passwordTextField.getText());
        } else {
            passwordTextField.setText(passwordField.getText());
        }
        passwordField.setVisible(visible);
        passwordTextField.setVisible(!visible);
    }

    /**
     * method that closes the window.
     */
    @Override
    public void closeRequest() {
        Optional<ButtonType> action = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to exit the application?").showAndWait();
        if (action.get() == ButtonType.OK) {
            stage.close();
        }
    }

    /**
     * handle the case of pressing the HyperLink
     *
     * @param event
     */
    private void handleHyperlinkPressed(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/view/Registration.fxml"));
            Parent root = (Parent) loader.load();
            // Obtain the Sign In window controller
            RegistrationController controller = RegistrationController.class
                    .cast(loader.getController());
            controller.setStage(stage);
            controller.initStage(root);

        } catch (IOException ex) {
            Logger.getLogger(App.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * handles the pressing of the confirm button. is necessary. this method is
     * executed whenever that happens. 
     *
     * @param event The action event object.
     */
    @FXML
    private void handleConfirmButtonAction(ActionEvent event) {

        Logger.getLogger(App.class.getName()).info("Confirm Button pressed");
        if (loginErrorLabel.isVisible() || passwordErrorLabel.isVisible() || loginTextField.getText().isEmpty() || passwordField.getText().isEmpty()) {
            new Alert(Alert.AlertType.INFORMATION, "Your login information has errors").showAndWait();
        } else {
            showMainWindow();
        }

    }

    /**
     * method that launches the main window. handles EVERY exception.
     */
    private void showMainWindow() {
        try {
            User user = new User(loginTextField.getText(), passwordField.getText());
            user = signable.signIn(user);

            FXMLLoader loader
                    = new FXMLLoader(getClass().getClassLoader().getResource("ui/view/RegisteredView.fxml"));
            Parent root = (Parent) loader.load();
            //Obtain the Sign In window controller
            RegisteredController controller
                    = RegisteredController.class
                            .cast(loader.getController());
            controller.setStage(stage);
            controller.setLoggedUser(user);
            controller.initStage(root);

            // validate the values set in loginTextField and passwordTextField
        } catch (BadCredentialsException e) {
            passwordErrorLabel.setText("Incorrect password");
            passwordErrorLabel.setVisible(true);
        } catch (NoSuchUserException e) {
            loginErrorLabel.setText("Username does not exist");
            loginErrorLabel.setVisible(true);
        } catch (ServerCapacityException e) {
            new Alert(Alert.AlertType.ERROR,
                    "Server is at full capacity at the moment, try again in a few seconds")
                    .showAndWait();
            LOGGER.log(Level.SEVERE, e.getMessage());
        } catch (ServerErrorException e) {
            new Alert(Alert.AlertType.ERROR,
                    e.getMessage()).showAndWait();
            LOGGER.log(Level.SEVERE, e.getMessage());
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR,
                    "Error loading main window").showAndWait();
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

}
