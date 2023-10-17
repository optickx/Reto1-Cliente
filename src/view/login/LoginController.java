package view.login;

import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
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



        //stageLogin.setOnShowing(this::); TODO: create handlers
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
     * Text changed event handler. Validate that all the fields are not empty
     * and that they not surpass 25 characters. The Accept button is disabled if
     * either of those are not fulfilled
     *
     * @param observable The value being observed.
     * @param oldValue The old value of the observable.
     * @param newValue The new value of the observable.
     */
    private void textChanged(ObservableValue observable,
            String oldValue,
            String newValue) {
                
            }

}
