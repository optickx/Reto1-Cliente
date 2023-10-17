package view.login;

import java.util.logging.Logger;
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
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class LoginController {


    private final int MAX_TEXT_LENGTH = 25;

    @FXML
    private Label loginLabel, passwordLabel, errorLabel;

    @FXML
    private TextField loginTextField, passwordTextField;

    @FXML
    private Button confirmButton, exitButton, showPasswordButton;

    // TODO: HyperLink to SignUp window???

    @FXML
    private PasswordField passwordField;

    private Stage stageLogin;

    private final static Logger LOGGER = Logger.getLogger("package view.login");

    /**
     * init void
     * creates and initializes the window, also setting the
     * name and defining it's elements.
     * 
     * @param root
     */
    public void initLogin(Parent root) {
        LOGGER.info("Initializing login window.");
        Scene scene = new Scene(root);

        stageLogin.setScene(scene);
        stageLogin.setTitle("Login Window");
        stageLogin.setResizable(false);

        loginLabel.setText("Login");
        passwordLabel.setText("Password");

        confirmButton.setText("Confirm");
        exitButton.setText("Exit");

        // stageLogin.setOnShowing(this::); TODO: create handlers
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

        confirmButton.setDisable(true);
        exitButton.setDisable(true);
        showPasswordButton.setDisable(true);
    }

    /**
     * whenever text changes. *
     * 
     * @param observable The value being observed.
     * @param oldValue   The old value of the observable.
     * @param newValue   The new value of the observable.
     */
    protected void textChanged(ObservableValue observable,
            String oldValue,
            String newValue) {

        // if the text is valid by format
        boolean textIsValid = loginTextField.getText().length() <= MAX_TEXT_LENGTH &&
                passwordField.getText().length() <= MAX_TEXT_LENGTH &&
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

    /**
     * prepare the stage for a change of scene
     * 
     * @param stage where the window shows
     */
    public void setStage(Stage stage) {
        this.stageLogin = stage;
    }
}
