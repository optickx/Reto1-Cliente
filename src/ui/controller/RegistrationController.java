package ui.controller;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.xml.ws.Response;

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
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

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
  private PasswordField passwordTextField;

  /**
   * Text field for confirming the password.
   */
  @FXML
  private PasswordField confirmPasswordTextField;

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
   * Button for revealing the entered confirmation password.
   */
  @FXML
  private Button showConfirmPasswordButton;

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
   * The method initStage() initializes the stage and sets its containing values
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

    // We set the texts for all the Labels
    emailErrorLabel.setText("Username must be an email");
    confirmPasswordErrorLabel.setText("Passwords must be the same");
    zipErrorLabel.setText("ZIP must be numerical");
    personalInfoErrorLabel.setText("The personal information cannot be empty");

    // Disable the SignUp and show passwords buttons so that we can control that
    // they are only enabled when the fields are filled in
    signUpButton.setDisable(true);
    showPasswordButton.setDisable(true);
    showConfirmPasswordButton.setDisable(true);

    // We make the error labels not visible to control that they are only visible
    // when the field in question loses focus and is erroneous
    emailErrorLabel.setVisible(false);
    confirmPasswordErrorLabel.setVisible(false);
    zipErrorLabel.setVisible(false);
    personalInfoErrorLabel.setVisible(false);

    // We set listeners in the emailTextField, passwordTextField and
    // confirmPasswordTextField text/password fields to detect changes to the text
    // entered by the user
    emailTextField.onActionProperty();
    passwordTextField.textProperty().addListener(this::handlePassword);
    confirmPasswordTextField.textProperty().addListener(this::handleUsername);
    fullNameTextField.textProperty().addListener(this::handleFullName);
    phoneTextField.textProperty().addListener(this::handlePhoneNumber);
    cityTextField.textProperty().addListener(this::handleCityAddress);
    addressTextField.textProperty().addListener(this::handleCityAddress);
    zipTextField.textProperty().addListener(this::handleZip);
    signUpButton.setOnAction(null);

    /**
     * Adds a key release event handler for the "ESCAPE" key, closes the request if
     * pressed,
     * and then displays the stage. Logs a message indicating the window has been
     * opened.
     *
     * @param stage The JavaFX stage to be displayed.
     */
    stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
      if (KeyCode.ESCAPE == event.getCode())
        closeRequest();
    });
    stage.show();
    LOGGER.info("Window Opened.");
  }

  /**
   * Handles changes to the username field, validates the input, and throws
   * exceptions when necessary.
   *
   * @param observable The observable value associated with the username field.
   * @param oldValue   The previous value of the username.
   * @param newValue   The new value of the username to be validated.
   * @throws IncorrectFormatException If the username format is incorrect, this
   *                                  exception is thrown.
   * @throws EmptyFieldException      If the username is empty, this exception is
   *                                  thrown.
   */
  protected void handleUsername(ObservableValue observable,
      String oldValue, String newValue) {
    try {
      isNotEmpty(newValue);
      isTooLong(newValue);

      if (!validateUsername(newValue)) {
        throw new IncorrectFormatException();
      }
    } catch (EmptyFieldException e) {
      emailErrorLabel.setText("Username cannot be empty");
    } catch (IncorrectFormatException e) {
      emailErrorLabel.setText("Username must be a valid email");
    } finally {
      emailErrorLabel.setVisible(true);
    }
  }

  /**
   * Validates a username for correct email format.
   *
   * @param username The username to be validated as an email address.
   * @return true if the username is a valid email address, false otherwise.
   * @throws IncorrectFormatException If the username does not match the expected
   *                                  email format, this exception is thrown.
   */
  private boolean validateUsername(String username) throws IncorrectFormatException {
    Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
        Pattern.CASE_INSENSITIVE);

    Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(username);
    if (matcher.matches())
      return true;
    throw new IncorrectFormatException();
  }

  /**
   * Handles changes to the password field and validates the input.
   *
   * @param observable The observable value associated with the password field.
   * @param oldValue   The previous value of the password.
   * @param newValue   The new value of the password to be validated.
   */
  private void handlePassword(ObservableValue observable,
      String oldValue,
      String newValue) {
    try {
      validatePassword(newValue);
    } catch (IncorrectFormatException e) {
      confirmPasswordErrorLabel.setText("Password too long");
    } catch (PasswordTooShortException e) {
      confirmPasswordErrorLabel.setText("Password too short");
    } catch (EmptyFieldException e) {
      confirmPasswordErrorLabel.setText("Password is empty");
    } finally {
      confirmPasswordErrorLabel.setVisible(true);
    }
  }

  /**
 * Handles the validation of password matching when the password field is updated.
 *
 * @param observable The observable property that triggered the change.
 * @param oldValue The previous value of the password field.
 * @param newValue The new value of the password field.
 */
  private void handlePasswordMatching(ObservableValue observable, String oldValue, String newValue) {
    try {
      validatePasswordMatching(newValue);
    } catch (PasswordsDoNotMatchException e) {
      confirmPasswordErrorLabel.setText("Passwords do not match");
    } finally {
      confirmPasswordErrorLabel.setVisible(true);
    }
  }

  /**
   * Validates a password for correct format, length, and emptiness.
   *
   * @param password The password to be validated.
   * @throws IncorrectFormatException  If the password format is incorrect, this
   *                                   exception is thrown.
   * @throws PasswordTooShortException If the password is too short, this
   *                                   exception is thrown.
   * @throws EmptyFieldException       If the password is empty, this exception is
   *                                   thrown.
   */
  private void validatePassword(String password)
      throws IncorrectFormatException, PasswordTooShortException, EmptyFieldException {
    isNotEmpty(password);
    isTooShort(password);
    isTooLong(password);
  }

  /**
   * Validates whether the provided password matches the confirmation password.
   *
   * @param password The password to be validated.
   * @return true if the password matches the confirmation password, false
   *         otherwise.
   * @throws PasswordsDoNotMatchException If the password and confirmation
   *                                      password do not match.
   */
  private boolean validatePasswordMatching(String password) throws PasswordsDoNotMatchException {
    if (passwordTextField.getText().equals(confirmPasswordTextField.getText()))
      return true;

    throw new PasswordsDoNotMatchException();

  }

  /**
   * Handles changes to the full name field, validates the input, and updates
   * error messages accordingly.
   *
   * @param observable The observable value associated with the full name field.
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
    } catch (IncorrectFormatException e) {
      personalInfoErrorLabel.setText("There are errors in personal info");
    } finally {
      personalInfoErrorLabel.setVisible(true);
    }
  }

  /**
   * Validates a full name for correct format.
   *
   * @param input The full name to be validated.
   * @return true if the full name is valid, false otherwise.
   * @throws IncorrectFormatException If the full name does not match the expected
   *                                  format, this exception is thrown.
   */
  public static boolean validateFullName(String input) throws IncorrectFormatException {
    String regex = "^[\\p{L}]+(\\s+[\\p{L}]+){1,}$";

    Pattern pattern = Pattern.compile(regex, Pattern.UNICODE_CHARACTER_CLASS);
    Matcher matcher = pattern.matcher(input);

    if (matcher.matches())
      return true;

    throw new IncorrectFormatException();
  }

  /**
   * Handles changes to the phone number field and validates the input.
   *
   * @param observable The observable value associated with the phone number
   *                   field.
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
    } catch (IncorrectFormatException e) {
      personalInfoErrorLabel.setText("There are errors in personal info");
    } finally {
      personalInfoErrorLabel.setVisible(true);
    }
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

    Pattern pattern = Pattern.compile(patron);

    Matcher matcher = pattern.matcher(numero);

    if (matcher.matches())
      return true;

    throw new IncorrectFormatException();
  }

  /**
   * Handles changes in the city/address field and validates the input.
   *
   * @param observable An ObservableValue representing the change.
   * @param oldValue   The old value of the city/address.
   * @param newValue   The new value of the city/address.
   */
  private void handleCityAddress(ObservableValue observable, String oldValue, String newValue) {
    try {
      isNotEmpty(newValue);
      validateCityAddress(newValue);

      personalInfoErrorLabel.setVisible(false);
    } catch (EmptyFieldException e) {
      personalInfoErrorLabel.setText("There are errors in personal info");
    } catch (IncorrectFormatException e) {
      personalInfoErrorLabel.setText("There are errors in personal info");
    } finally {
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
   * Handles the ZIP code field when its value changes.
   *
   * @param observable The observable value associated with the ZIP code field.
   * @param oldValue   The previous value of the ZIP code.
   * @param newValue   The new value of the ZIP code.
   */
  private void handleZip(ObservableValue observable,
      String oldValue,
      String newValue) {
    try {
      isNotEmpty(newValue);
      validateZip(newValue);
    } catch (IncorrectFormatException e) {
      zipErrorLabel.setText("ZIP with incorrect format");
    } catch (EmptyFieldException e) {
      zipErrorLabel.setText("ZIP is empty");
    } finally {
      zipErrorLabel.setVisible(true);
    }
  }

  /**
   * Validates a ZIP code for correct format.
   *
   * @param zip The ZIP code to be validated.
   * @return true if the ZIP code is valid, false otherwise.
   * @throws IncorrectFormatException If the ZIP code does not match the expected
   *                                  format, this exception is thrown.
   */
  public static boolean validateZip(String zip) throws IncorrectFormatException {
    String regex = "\\d{1,5}";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(zip);
    if (matcher.matches())
      return true;

    throw new IncorrectFormatException();
  }

  /**
   * Handles the action of the "Sign Up" button when pressed.
   *
   * @param event The action event that triggers the function.
   */
  @FXML
  private void handleSignUpButtonAction(ActionEvent event) {
    try {
      Response response = null;
      LOGGER.info("Sign Up button has been pressed.");

    } catch (Exception e) {

    }
  }

  /**
   * Checks if the provided field is not empty.
   *
   * @param field The field to be checked for emptiness.
   * @throws EmptyFieldException If the field is empty, this exception is thrown.
   */
  private void isNotEmpty(String password) throws EmptyFieldException {
    if (password.isEmpty())
      throw new EmptyFieldException();
  }

  /**
   * Checks if the provided password is too short.
   *
   * @param password The password to be checked for its length.
   * @throws PasswordTooShortException If the password is too short (less than 8
   *                                   characters), this exception is thrown.
   */
  private void isTooShort(String password) throws PasswordTooShortException {
    if (password.length() < 8) {
      throw new PasswordTooShortException();
    }
  }

  /**
   * Checks if the input string is too long.
   *
   * @param input The input string to be checked for length.
   * @throws IncorrectFormatException If the input string is too long, this
   *                                  exception is thrown.
   */
  private void isTooLong(String input) throws IncorrectFormatException {
    if (input.length() > 255)
      throw new IncorrectFormatException();
  }

  /**
   * We display a confirmation dialogue box asking the user if they are sure they
   * want to exit the application
   * 
   */
  public void closeRequest() {
    Optional<ButtonType> action = new Alert(Alert.AlertType.CONFIRMATION,
        "Are you sure you want to exit the application?").showAndWait();
    if (action.get() == ButtonType.OK)
      stage.close();
  }

}
