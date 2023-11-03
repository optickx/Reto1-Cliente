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
import javafx.stage.WindowEvent;
import logic.business.SignableFactory;
import packets.User;

public class LoginController extends GenericController {

    @FXML
    private Label loginLabel, passwordLabel, loginErrorLabel, passwordErrorLabel;
    @FXML
    private TextField loginTextField, passwordTextField;
    @FXML
    private Button confirmButton, exitButton, showPasswordButton;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Hyperlink signUpLink;


    /**
     * method that initiates the stage and sets/prepares the values
     * inside of it.
     * 
     * @param root
     */
    public void initStage(Parent root) {
        LOGGER.info("Initialazing " + " window.");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        signable = SignableFactory.getImplementation();
        // Set properties
        // se establece el nombre de la ventana
        stage.setTitle("Ventana");
        // se establece la ventana como no redimensionable
        stage.setResizable(false);

        stage.setTitle("Login Window");
        stage.setMaximized(false);
        stage.setResizable(false);

        loginLabel.setText("Login");
        passwordLabel.setText("Password");

        confirmButton.setText("Confirm");
        exitButton.setText("Exit");

        passwordTextField.setVisible(false);

        confirmButton.setDisable(false);
        exitButton.setDisable(false);
        showPasswordButton.setDisable(false);

        loginErrorLabel.setVisible(false);
        passwordErrorLabel.setVisible(false);

        loginTextField.textProperty().addListener(this::handleUsername);
        passwordField.textProperty().addListener(this::handlePassword);
        showPasswordButton.setOnAction(this::handleShowPassword);
        exitButton.setOnAction(this::handleExitButton);
        confirmButton.setOnAction(this::handleConfirmButtonAction);
        signUpLink.setOnAction(this::handleHyperlinkPressed);

        // alineamos los elementos

        // cerrar ventana con Esc
        stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> { // Adds an event handler that records every
                                                                           // time the escape key is pressed
            if (KeyCode.ESCAPE == event.getCode())
                closeRequest();
        });
        stage.show();
        LOGGER.info("Window opened.");
    }

    /**
     * button that does the very same thing as the
     * close button on top of any window
     */
    public void handleExitButtonAction() {
        closeRequest();
    }

    protected void handleUsername(ObservableValue observable,
            String oldValue, String newValue) {
        try {
            newValue = newValue.trim();
            if (isNotEmpty(newValue) || !isTooLong(newValue)) {
                validateUsername(newValue);
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

    private void handleExitButton(ActionEvent event) {
        closeRequest();

    }

    private void handleShowPassword(ActionEvent event) {
        if (passwordTextField.isVisible())
            showPassword(true);
        else
            showPassword(false);
    }

    private void showPassword(boolean visible) {
        if (visible)
            passwordField.setText(passwordTextField.getText());
        else
            passwordTextField.setText(passwordField.getText());

        passwordField.setVisible(visible);
        passwordTextField.setVisible(!visible);
    }

    /**
     * action that will be executed when the
     * user tries to close the application.
     */
    public void closeRequest() {
        Optional<ButtonType> action = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to exit the application?").showAndWait();
        if (action.get() == ButtonType.OK)
            stage.close();
    }

    /**
     * whenever text changes. *
     * 
     * @param observable value being observed.
     * @param oldValue   old value of the observable.
     * @param newValue   new value of the observable.
     */
    protected void textPropertyChange(ObservableValue observable,
            String oldValue,
            String newValue) {
        // if the text is valid by format
        /*
         * boolean textIsValid =
         * 8 <= loginTextField.getText().length() && loginTextField.getText().length()
         * <= MAX_TEXT_LENGTH &&
         * 8 <= passwordTextField.getText().length() && passwordField.getText().length()
         * <= MAX_TEXT_LENGTH &&
         * usernameIsValid(loginTextField.getText());
         * 
         * confirmButton.setDisable(!textIsValid);
         */
    }

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
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * handles the pressing of the confirm button. is necessary.
     * this method is executed whenever that happens.
     * 
     * @param event The action event object
     */
    @FXML
    private void handleConfirmButtonAction(ActionEvent event) {

        try {
            User user = new User(loginTextField.getText(), passwordField.getText());
            user = signable.signIn(user);
            LOGGER.info("Confirm button has been pressed.");
            
            // validate the values set in loginTextField and passwordTextField
        } catch (BadCredentialsException e) {
            LOGGER.info("cONTRA");
            System.out.println("Contrasena incorrecta");
        } catch (NoSuchUserException e) {
            LOGGER.info("User");
            System.out.println("Usuario no existe");
        } catch (ServerCapacityException e) {
            LOGGER.info("Server");
            System.out.println("Mucho server ");
        } catch (ServerErrorException e) {
            LOGGER.info("Error");
            System.out.println("Error");
        }

    }
}