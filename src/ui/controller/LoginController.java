package ui.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.ws.Response;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.WindowEvent;

public class LoginController extends GenericController {

    private final int MAX_TEXT_LENGTH = 25;

    @FXML
    private Label loginLabel, passwordLabel, loginErrorLabel, passwordErrorLabel;
    @FXML
    private TextField loginTextField, passwordTextField;
    @FXML
    private Button confirmButton, exitButton, showPasswordButton;
    // TODO: HyperLink to SignUp window???
    @FXML
    private PasswordField passwordField;
     /**
     * method that initiates the stage and sets/prepares the values
     * inside of it.
     * @param root
     */
    public void initStage(Parent root) {
       LOGGER.info("Initialazing "  + " window."); 
       Scene scene = new Scene(root);
       stage.setScene(scene);
        // Set properties
        // se establece el nombre de la ventana
        stage.setTitle("Ventana");
        // se establece la ventana como no redimensionable
        stage.setResizable(false);
        // se establecen las celdas de las tablas

         confirmButton.setDisable(true);
        exitButton.setDisable(true);
        showPasswordButton.setDisable(true);

        
        loginErrorLabel.setVisible(false);
        passwordErrorLabel.setVisible(false);

        loginTextField.textProperty().addListener(this::textPropertyChange);
        passwordTextField.textProperty().addListener(this::textPropertyChange);


        // alineamos los elementos
        
        

        // por si acaso se pide hacer algo con teclas
        /* stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> { // Adds an event handler that records every time the escape key is pressed
            if (KeyCode.ESCAPE == event.getCode()) 
                closeRequest();
        }); */
        stage.show();
        LOGGER.info("Window opened.");
    }
    /**
     * init void
     * creates and initializes the window, also setting the
     * name and defining it's elements.
     * 
     * @param root
     */
    private void initLogin(Parent root) {
        LOGGER.info("Initializing login window.");
        Scene scene = new Scene(root);

        stage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("ui.view.style.css").toExternalForm());

        //scene.getStylesheets().add(getClass().getResource("ui/view/stylesheet.css").toExternalForm());
        stage.setTitle("Login Window");
        stage.setMaximized(false);
        stage.setResizable(false);

        loginLabel.setText("Login");
        passwordLabel.setText("Password");

        confirmButton.setText("Confirm");
        exitButton.setText("Exit");

        // stage.setOnShowing(this::); TODO: create handlers
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
     * @param oldValue old value of the observable.
     * @param newValue new value of the observable.
     */
    protected void textPropertyChange(ObservableValue observable,
            String oldValue,
            String newValue) {
        // if the text is valid by format
        boolean textIsValid = 
            8 <= loginTextField.getText().length() && loginTextField.getText().length() <= MAX_TEXT_LENGTH &&
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
        Pattern VALID_EMAIL_ADDRESS_REGEX = 
        Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

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