package ui.controller;

import app.App;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import exceptions.EmptyFieldException;
import exceptions.IncorrectFormatException;
import exceptions.PasswordTooShortException;
import interfaces.Signable;

import java.util.Optional;
import javafx.stage.WindowEvent;

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.control.ButtonType;

public abstract class GenericController {

    protected final int MAX_TEXT_LENGTH = 255;

    protected static final Logger LOGGER = Logger.getLogger("package view.controller\":");

    /**
     * maximum text fields length.
     */
    protected final int MAX_LENGTH = 25;
    /**
     * the business logic object containing all business methods.
     */
    protected Signable signable;
    /**
     * the Stage object associated to the Scene controlled by this controller.
     * This is an utility method reference that provides quick access inside the
     * controller to the Stage object in order to make its initialization. Note
     * that this makes Application, Controller and Stage being tightly coupled.
     */
    protected Stage stage;

    /**
     * gets the Stage object related to this controller.
     *
     * @return The Stage object initialized by this controller.
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * sets the Stage object related to this controller.
     *
     * @param stage The Stage object to be initialized.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * shows an error message in an alert dialog.
     *
     * @param errorMsg The error message to be shown.
     */
    protected void showErrorAlert(String errorMsg) {
        // Shows error dialog.
        Alert alert = new Alert(Alert.AlertType.ERROR, errorMsg, ButtonType.OK);
        // alert.getDialogPane().getStylesheets().add(getClass().getResource("/javafxapplicationud3example/ui/view/customCascadeStyleSheet.css").toExternalForm());
        alert.showAndWait();

    }

    /**
     * Checks if the provided field is not empty.
     *
     * @param field The field to be checked for emptiness.
     * @throws EmptyFieldException If the field is empty, this exception is null
     *
     */
    protected boolean isNotEmpty(String password) throws EmptyFieldException {
        if (password.isEmpty()) {
            throw new EmptyFieldException();
        }
        return true;
    }

    /**
     * Checks if the input string is too long.
     *
     * @param input The input string to be checked for length.
     * @throws IncorrectFormatException If the input string is too long, this
     */
    protected boolean isTooLong(String input) throws IncorrectFormatException {
        if (input.length() > 255) {
            throw new IncorrectFormatException();
        }
        return false;
    }

    /**
     * Checks if the provided password is too short.
     *
     * @param password The password to be checked for its length.
     * @throws PasswordTooShortException If the password is too short (less than
     */
    protected boolean isTooShort(String password) throws PasswordTooShortException {
        if (password.length() < 8) {
            throw new PasswordTooShortException();
        }
        return false;
    }
    /**
     * method that analyses the username
     * @param username username to be checked
     * @return true if value is correct
     * @throws IncorrectFormatException if value doesn't match condition.
     */
    protected boolean validateUsername(String username) throws IncorrectFormatException {
        Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
                Pattern.CASE_INSENSITIVE);

        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(username);
        if (matcher.matches()) {
            return true;
        }
        throw new IncorrectFormatException();
    }

    /**
     * Validates a password for correct format, length, and emptiness.
     *
     * @param password The password to be validated.
     * @throws IncorrectFormatException If the password format is incorrect,
     * this exception is thrown.
     * @throws PasswordTooShortException If the password is too short, this
     * exception is thrown.
     * @throws EmptyFieldException If the password is empty, this exception is
     * thrown. =======
     */
    protected void validatePassword(String password)
            throws IncorrectFormatException, PasswordTooShortException, EmptyFieldException {
        isNotEmpty(password);

        isTooLong(password);
    }

    /**
     * action that will be executed when the user tries to close the
     * application.
     */
    protected void closeRequest() {
        Optional<ButtonType> action = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to exit the application?").showAndWait();
        if (action.get() == ButtonType.OK) {
            Logger.getLogger(App.class.getName()).info("Exiting application");
            stage.close();
        }
    }

    /**
     * Method to handle the close request
     */
    protected void handleCloseRequest(WindowEvent event) {
        event.consume();
        if (event.getEventType().equals(WindowEvent.WINDOW_CLOSE_REQUEST)) {
            closeRequest();
        }
    }
}
