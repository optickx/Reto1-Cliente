package ui.controller;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.ws.Response;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.WindowEvent;

public class LoginController extends GenericController {

    @FXML
    private Label loginLabel, passwordLabel, loginErrorLabel, passwordErrorLabel;
    @FXML
    private TextField loginTextField, passwordTextField;
    @FXML
    private Button confirmButton, exitButton, showPasswordButton;
    // TODO: HyperLink to SignUp window???
    @FXML
    private PasswordField passwordField;
    // window name
    private static final String WINDOW_NAME = "Login";

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

        confirmButton.setDisable(true);
        exitButton.setDisable(true);
        showPasswordButton.setDisable(true);

        loginErrorLabel.setVisible(false);
        passwordErrorLabel.setVisible(false);

        loginTextField.textProperty().addListener(this::textPropertyChange);
        passwordTextField.textProperty().addListener(this::textPropertyChange);

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
     * @param event void that runs when the window is shown
     */
    private void handlerWindowShowing(WindowEvent event) {
        LOGGER.info("Initializing ---------whatever");
        loginTextField.setText("");
        loginTextField.requestFocus();
        // focus is placed directly in the login field
        passwordTextField.setText("");

        System.out.println("well well well");
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
        boolean textIsValid = 8 <= loginTextField.getText().length()
                && loginTextField.getText().length() <= MAX_TEXT_LENGTH &&
                8 <= passwordTextField.getText().length() && passwordField.getText().length() <= MAX_TEXT_LENGTH &&
                usernameIsValid(loginTextField.getText());

        confirmButton.setDisable(!textIsValid);
    }

    /**
     * @param username text input by user in username field
     * @return true if the username matches the email pattern. false if not.
     */
    private boolean usernameIsValid(String username) {
        // TODO:
        Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
                Pattern.CASE_INSENSITIVE);

        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(username);
        return matcher.matches();
    }

    /**
     * @param password
     * @return
     */
    private boolean passwordIsValid(String password) {
        return true;
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
            Response response = null;
            LOGGER.info("Confirm button has been pressed.");

            // validate the values set in loginTextField and passwordTextField
        } catch (Exception e) {

        }
    }
}