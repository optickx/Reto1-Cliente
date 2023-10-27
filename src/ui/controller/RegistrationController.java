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
    phoneErrorLabel.setText("The personal information cannot be empty");

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
    phoneErrorLabel.setVisible(false);

    // We set listeners in the emailTextField, passwordTextField and
    // confirmPasswordTextField text/password fields to detect changes to the text
    // entered by the user
    emailTextField.onActionProperty();
    passwordTextField.textProperty().addListener(this::handlePasswordField);
    confirmPasswordTextField.textProperty().addListener(this::textPropertyChange);
    // ¡¡¡FALTA POR AÑADIR LISTENERS PARA EL RESTO DE TEXFIELDS AQUÍ!!!

    /**
     * This method registers an event handler to detect when the Escape key is
     * released, and in that case, call the closeRequest() function
     * 
     * @param KeyEvent
     * @author Alex Irusta
     */
    stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
      if (KeyCode.ESCAPE == event.getCode())
        closeRequest();
    });
    stage.show();
    LOGGER.info("Window Opened.");
  }

  private void handlePasswordField(ObservableValue observable,
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

  private void handleFullName(ObservableValue observable,
      String oldValue,
      String newValue) {
    try {
      isTooLong(newValue);
      isNotEmpty(newValue);
      fullNameIsValid(newValue);

      personalInfoErrorLabel.setVisible(false);
    } catch (EmptyFieldException e) {
      personalInfoErrorLabel.setText("There are errors in personal info");
    } catch (IncorrectFormatException e) {
      personalInfoErrorLabel.setText("There are errors in personal info");
    } finally {
      personalInfoErrorLabel.setVisible(true);
    }
  }

  public static boolean fullNameIsValid(String input) throws IncorrectFormatException {
    String regex = "^[\\p{L}]+(\\s+[\\p{L}]+){1,}$";

    Pattern pattern = Pattern.compile(regex, Pattern.UNICODE_CHARACTER_CLASS);
    Matcher matcher = pattern.matcher(input);

    if (matcher.matches())
      return true;

    throw new IncorrectFormatException();
  }

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

  public static boolean validatePhoneNumber(String numero) throws IncorrectFormatException {
    String patron = "^\\+\\d{2}\\d{9}$";

    Pattern pattern = Pattern.compile(patron);

    Matcher matcher = pattern.matcher(numero);

    if (matcher.matches())
      return true;

    throw new IncorrectFormatException();
  }

  private void validateCity(String city) throws EmptyFieldException, IncorrectFormatException {
    isNotEmpty(city);
    isTooLong(city);
  }

  private void validateAddress(String address) throws EmptyFieldException, IncorrectFormatException {
    isNotEmpty(address);
    isTooLong(address);
  }

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

  public static boolean validateZip(String zip) throws IncorrectFormatException {
    String regex = "\\d{1,5}";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(zip);
    if (matcher.matches())
      return true;

    throw new IncorrectFormatException();
  }

  private void validatePassword(String password)
      throws IncorrectFormatException, PasswordTooShortException, EmptyFieldException {
    isNotEmpty(password);
    isTooShort(password);
    isTooLong(password);
  }

  private void isTooShort(String password) throws PasswordTooShortException {
    if (password.length() < 8) {
      throw new PasswordTooShortException();
    }
  }

  private void isNotEmpty(String password) throws EmptyFieldException {
    if (password.isEmpty())
      throw new EmptyFieldException();
  }

  private void isTooLong(String input) throws IncorrectFormatException {
    if (input.length() > 255)
      throw new IncorrectFormatException();
  }

  /**
   * This method is responsible for registering changes to TextFields and
   * PasswordFields and performing format/length checking where necessary. If
   * everything is OK, it enables the signUpButton
   * 
   * @param observable
   * @auhor Alex Irusta
   */
  protected void textPropertyChange(ObservableValue observable,
      String oldValue,
      String newValue) {
    boolean textIsValid = 8 <= emailTextField.getText().length()
        && emailTextField.getText().length() <= MAX_TEXT_LENGTH &&
        8 <= passwordTextField.getText().length() && passwordTextField.getText().length() <= MAX_TEXT_LENGTH
        && passwordTextField.getText() == confirmPasswordTextField.getText() &&
        usernameIsValid(emailTextField.getText());

    signUpButton.setDisable(!textIsValid);
  }

  /**
   * We display a confirmation dialogue box asking the user if they are sure they
   * want to exit the application
   * 
   * @auhor Alex Irusta
   */
  public void closeRequest() {
    Optional<ButtonType> action = new Alert(Alert.AlertType.CONFIRMATION,
        "Are you sure you want to exit the application?").showAndWait();
    if (action.get() == ButtonType.OK)
      stage.close();
  }

  /**
   * Method for checking correct email formatting
   * 
   * @param username
   * @auhor Alex Irusta
   */
  private boolean usernameIsValid(String username) throws IncorrectFormatException{
    Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
        Pattern.CASE_INSENSITIVE);

    Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(username);
    if (matcher.matches()) 
      return true;
    throw new IncorrectFormatException;
  }

  /**
   * ¡¡¡FALTA POR AVERIGUAR QUÉ HACE ESTE CÓDIGO Y SI ESTÁ INCOMPLETO!!!
   * 
   * @param event
   * @auhor Alex Irusta
   */
  @FXML
  private void handleConfirmButtonAction(ActionEvent event) {
    try {
      Response response = null;
      LOGGER.info("Sign Up button has been pressed.");

    } catch (Exception e) {

    }
  }

}
