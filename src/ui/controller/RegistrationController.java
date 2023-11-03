package ui.controller;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import exceptions.EmptyFieldException;
import exceptions.IncorrectFormatException;
import exceptions.PasswordTooShortException;
import exceptions.PasswordsDoNotMatchException;
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

public class RegistrationController extends GenericController {

    /**
     * Label for displaying an error message related to the email field.
     */
    @FXML
    private Label emailErrorLabel;

    /**
     * Label for informing of an error with the confirmation of the password.
     */
    @FXML
    private Label confirmPasswordErrorLabel;

    /**
     * Label for informing of an error related to the ZIP code.
     */
    @FXML
    private Label zipErrorLabel;

    /**
     * Label for informing of an error related to the phone number.
     */
    @FXML
    private Label personalInfoErrorLabel;

    /**
     * Text field for entering the email address.
     */
    @FXML
    private TextField emailTextField;

    /**
     * Text field for entering the password.
     */
    @FXML
    private PasswordField passwordField;

    /**
     * Text field for confirming the password.
     */
    @FXML
    private PasswordField confirmPasswordField;

    /**
     * This is a 'PasswordField' element used for entering a password.
     */
    @FXML
    private TextField passwordTextField;

    /**
     * This is another 'PasswordField' element intended for confirming the
     * entered password.
     */
    @FXML
    private TextField confirmPasswordTextField;

    /**
     * Text field for entering the user's full name.
     */
    @FXML
    private TextField fullNameTextField;

    /**
     * Text field for entering the user's address.
     */
    @FXML
    private TextField addressTextField;

    /**
     * Text field for entering the ZIP code.
     */
    @FXML
    private TextField zipTextField;

    /**
     * Text field for entering the city name.
     */
    @FXML
    private TextField cityTextField;

    /**
     * Text field for entering the phone number.
     */
    @FXML
    private TextField phoneTextField;

    /**
     * Button for revealing the entered password.
     */
    @FXML
    private Button showPasswordButton;

    /**
     * Button for returning to a previous step or page.
     */
    @FXML
    private Button goBackButton;

    /**
     * Button for initiating the sign-up process.
     */
    @FXML
    private Button signUpButton;

    /**
     * The method initStage() initializes the stage and sets its containing
     * values
     *
     * @param root
     * @author Alex Irusta
     */
    public void initStage(Parent root) {
        // We display a window opening information message with LOGGER
        LOGGER.info("Initialazing " + " window.");
        // We create a Scene containing the main content and set it in the main window
        // represented by the Stage object
        Scene scene = new Scene(root);
        stage.setScene(scene);

        // We title the window and make it not resizable
        stage.setTitle("Registration Window");
        stage.setResizable(false);
        stage.setMaximized(false);

        // Disable the SignUp and show passwords buttons so that we can control that
        // they are only enabled when the fields are filled in
        signUpButton.setDisable(true);

        // We make the error labels not visible to control that they are only visible
        // when the field in question loses focus and is erroneous
        emailErrorLabel.setVisible(false);
        confirmPasswordErrorLabel.setVisible(false);
        zipErrorLabel.setVisible(false);
        personalInfoErrorLabel.setVisible(false);

        passwordTextField.setVisible(false);
        confirmPasswordTextField.setVisible(false);

        // We set listeners in the emailTextField, passwordTextField and
        // confirmPasswordTextField text/password fields to detect changes to the text
        // entered by the user
        showPasswordButton.setOnAction(this::handleShowPassword);
        emailTextField.textProperty().addListener(this::handleUsername);
        passwordField.textProperty().addListener(this::handlePassword);
        confirmPasswordField.textProperty().addListener(this::handlePasswordMatching);
        fullNameTextField.textProperty().addListener(this::handleFullName);
        phoneTextField.textProperty().addListener(this::handlePhoneNumber);
        cityTextField.textProperty().addListener(this::handleCityAddress);
        addressTextField.textProperty().addListener(this::handleCityAddress);
        zipTextField.textProperty().addListener(this::handleZip);
        signUpButton.setOnAction(null);

        /**
         * Adds a key release event handler for the "ESCAPE" key, closes the
         * request if pressed, and then displays the stage. Logs a message
         * indicating the window has been opened.
         *
         * @param stage The JavaFX stage to be displayed.
         */
        stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.ESCAPE == event.getCode()) {
                closeRequest();
            }
        });
        stage.show();
        LOGGER.info("Window Opened.");
    }

    /**
     * This protected method handles and validates a username, often used in the
     * context of user registration or updates.
     *
     * @param observable An observable value, which may be associated with the
     *                   username field.
     * @param oldValue   The previous value of the username.
     * @param newValue   The new value of the username to be validated.
     */
    protected void handleUsername(ObservableValue observable,
            String oldValue, String newValue) {
        try {
            newValue = newValue.trim();
            if (isNotEmpty(newValue) || !isTooLong(newValue)) {
                validateUsername(newValue);
                emailErrorLabel.setVisible(false);
            }
        } catch (EmptyFieldException e) {
            emailErrorLabel.setText("Username cannot be empty");
            emailErrorLabel.setVisible(true);
        } catch (IncorrectFormatException e) {
            emailErrorLabel.setText("Username must be a valid email");
            emailErrorLabel.setVisible(true);
        }
    }

    /**
     * private void handleGoBackButtonAction(ActionEvent event) {
     * // Cerrar la ventana actual
     * Stage currentStage = (Stage) goBackButton.getScene().getWindow();
     * currentStage.close();
     * 
     * // Abrir la ventana de LoginController
     * try {
     * FXMLLoader loader = new
     * FXMLLoader(getClass().getResource("LoginController.fxml"));
     * Parent root = loader.load();
     * Stage loginStage = new Stage();
     * loginStage.setScene(new Scene(root));
     * loginStage.show();
     * } catch (IOException e) {
     * e.printStackTrace();
     * // Manejar errores al cargar la ventana de LoginController
     * }
     * }
     **/

    /**
     * This method is responsible for handling and validating a password input,
     * possibly during a user registration or update process.
     *
     * @param observable An observable value, which may be associated with the
     *                   password field.
     * @param oldValue   The previous value of the password.
     * @param newValue   The new value of the password to be validated.
     */
    private void handlePassword(ObservableValue observable,
            String oldValue,
            String newValue) {
        try {
            validatePassword(newValue);
            confirmPasswordErrorLabel.setVisible(false);
        } catch (IncorrectFormatException e) {
            confirmPasswordErrorLabel.setText("Password too long");
            confirmPasswordErrorLabel.setVisible(true);
        } catch (PasswordTooShortException e) {
            confirmPasswordErrorLabel.setText("Password too short");
            confirmPasswordErrorLabel.setVisible(true);
        } catch (EmptyFieldException e) {
            confirmPasswordErrorLabel.setText("Password is empty");
            confirmPasswordErrorLabel.setVisible(true);
        }
    }

    /**
     * This method is responsible for handling and validating the matching of
     * passwords in a user's registration or update process.
     *
     * @param observable An observable value, which may be related to the
     *                   password or confirm password fields.
     * @param oldValue   The previous value of the password or confirm password.
     * @param newValue   The new value of the password or confirm password to be
     *                   validated.
     */
    private void handlePasswordMatching(ObservableValue observable, String oldValue, String newValue) {
        try {
            validatePasswordMatching(newValue);
            confirmPasswordErrorLabel.setVisible(false);
        } catch (PasswordsDoNotMatchException e) {
            confirmPasswordErrorLabel.setText("Passwords do not match");
            confirmPasswordErrorLabel.setVisible(true);
        }
    }

    /**
     * Validates whether the provided password matches the confirmation
     * password.
     *
     * @param password The password to be validated.
     * @return true if the password matches the confirmation password, false
     *         otherwise.
     * @throws PasswordsDoNotMatchException If the password and confirmation
     *                                      password do not match.
     */
    private boolean validatePasswordMatching(String password) throws PasswordsDoNotMatchException {
        if (passwordField.getText().equals(confirmPasswordField.getText())) {
            return true;
        }

        throw new PasswordsDoNotMatchException();

    }

    /**
     * This method is responsible for handling and validating a full name input
     * in a user's personal information.
     *
     * @param observable An observable value, which may be associated with the
     *                   full name field.
     * @param oldValue   The previous value of the full name.
     * @param newValue   The new value of the full name to be validated.
     */
    private void handleFullName(ObservableValue observable,
            String oldValue,
            String newValue) {
        try {
            isTooLong(newValue);
            isNotEmpty(newValue);
            validateFullName(newValue);

            personalInfoErrorLabel.setVisible(false);
        } catch (EmptyFieldException e) {
            personalInfoErrorLabel.setText("There are errors in personal info");
            personalInfoErrorLabel.setVisible(true);
        } catch (IncorrectFormatException e) {
            personalInfoErrorLabel.setText("There are errors in personal info");
            personalInfoErrorLabel.setVisible(true);
        }
    }

    /**
     * Validates a full name for correct format.
     *
     * @param input The full name to be validated.
     * @return true if the full name is valid, false otherwise.
     * @throws IncorrectFormatException If the full name does not match the
     *                                  expected format, this exception is thrown.
     */
    public static boolean validateFullName(String input) throws IncorrectFormatException {
        String regex = "^[\\p{L}]+(\\s+[\\p{L}]+){1,}$";

        Pattern pattern = Pattern.compile(regex, Pattern.UNICODE_CHARACTER_CLASS);
        Matcher matcher = pattern.matcher(input);

        if (matcher.matches()) {
            return true;
        }

        throw new IncorrectFormatException();
    }

    /**
     * This method is responsible for handling and validating a phone number
     * input in a user's personal information.
     *
     * @param observable An observable value, which may be associated with the
     *                   phone number field.
     * @param oldValue   The previous value of the phone number.
     * @param newValue   The new value of the phone number to be validated.
     */
    private void handlePhoneNumber(ObservableValue observable,
            String oldValue,
            String newValue) {
        try {
            isNotEmpty(newValue);
            validatePhoneNumber(newValue);

            personalInfoErrorLabel.setVisible(false);
        } catch (EmptyFieldException e) {
            personalInfoErrorLabel.setText("There are errors in personal info");
            personalInfoErrorLabel.setVisible(true);
        } catch (IncorrectFormatException e) {
            personalInfoErrorLabel.setText("There are errors in personal info");
            personalInfoErrorLabel.setVisible(true);
        }
    }

    private void handleShowPassword(ActionEvent event) {
        if (passwordField.isVisible()) {
            showPassword(true);
        } else {
            showPassword(false);
        }
    }

    private void showPassword(boolean visible) {
        if (visible) {
            passwordTextField.setText(passwordField.getText());
            confirmPasswordTextField.setText(confirmPasswordField.getText());

        } else {
            passwordField.setText(passwordField.getText());
            confirmPasswordField.setText(confirmPasswordTextField.getText());
        }

        passwordField.setVisible(!visible);
        confirmPasswordField.setVisible(!visible);
        passwordTextField.setVisible(visible);
        confirmPasswordTextField.setVisible(visible);
    }

    /**
     * Validates a phone number for correct format.
     *
     * @param numero The phone number to be validated.
     * @return true if the phone number is valid, false otherwise.
     * @throws IncorrectFormatException If the phone number does not match the
     *                                  expected format, this exception is thrown.
     */
    public static boolean validatePhoneNumber(String numero) throws IncorrectFormatException {
        String patron = "^\\+\\d{2}\\d{9}$";
        String patron2 = "\\d{9}";

        Pattern pattern = Pattern.compile(patron);
        Pattern pattern2 = Pattern.compile(patron2);

        Matcher matcher = pattern.matcher(numero);
        Matcher matcher2 = pattern2.matcher(numero);

        if (matcher.matches() || matcher2.matches()) {
            return true;
        }

        throw new IncorrectFormatException();
    }

    /**
     * This method is responsible for handling and validating a city address
     * input in a user's personal information.
     *
     * @param observable An observable value, possibly related to the city
     *                   address field.
     * @param oldValue   The previous value of the city address.
     * @param newValue   The new value of the city address to be validated.
     */
    private void handleCityAddress(ObservableValue observable, String oldValue, String newValue) {
        try {
            isNotEmpty(newValue);
            validateCityAddress(newValue);

            personalInfoErrorLabel.setVisible(false);
        } catch (EmptyFieldException e) {
            personalInfoErrorLabel.setText("There are errors in personal info");
            personalInfoErrorLabel.setVisible(true);
        } catch (IncorrectFormatException e) {
            personalInfoErrorLabel.setText("There are errors in personal info");
            personalInfoErrorLabel.setVisible(true);
        }
    }

    /**
     * Validates a city address for correctness.
     *
     * @param value The city address to be validated.
     * @throws EmptyFieldException      If the city address is empty, this exception
     *                                  is thrown.
     * @throws IncorrectFormatException If the city address is too long, this
     *                                  exception is thrown.
     */
    private void validateCityAddress(String value) throws EmptyFieldException, IncorrectFormatException {
        isNotEmpty(value);
        isTooLong(value);
    }

    /**
     * This method is responsible for handling and validating a ZIP code input.
     *
     * @param observable An observable value, possibly related to the input
     *                   field.
     * @param oldValue   The previous value of the ZIP code.
     * @param newValue   The new value of the ZIP code to be validated.
     */
    private void handleZip(ObservableValue observable,
            String oldValue,
            String newValue) {
        try {
            isNotEmpty(newValue);
            validateZip(newValue);
            zipErrorLabel.setVisible(false);
        } catch (IncorrectFormatException e) {
            zipErrorLabel.setText("ZIP with incorrect format");
            zipErrorLabel.setVisible(true);
        } catch (EmptyFieldException e) {
            zipErrorLabel.setText("ZIP is empty");
            zipErrorLabel.setVisible(true);
        }
    }

    /**
     * Validates a ZIP code for correct format.
     *
     * @param zip The ZIP code to be validated.
     * @return true if the ZIP code is valid, false otherwise.
     * @throws IncorrectFormatException If the ZIP code does not match the
     *                                  expected format, this exception is thrown.
     */
    public static boolean validateZip(String zip) throws IncorrectFormatException {
        String regex = "\\d{5}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(zip);
        if (matcher.matches()) {
            return true;
        }

        throw new IncorrectFormatException();
    }

    /**
     * Handles the action of the "Sign Up" button when pressed.
     *
     * @param event The action event that triggers the function.
     */
    @FXML
    private void handleSignUpButtonAction(ActionEvent event) {
    }

    /**
     * We display a confirmation dialogue box asking the user if they are sure
     * they want to exit the application
     *
     */
    public void closeRequest() {
        Optional<ButtonType> action = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to exit the application?").showAndWait();
        if (action.get() == ButtonType.OK) {
            stage.close();
        }
    }

}
