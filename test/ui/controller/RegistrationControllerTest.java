package ui.controller;

import app.App;


import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import org.junit.Test;
import javafx.stage.Stage;
import org.testfx.framework.junit.ApplicationTest;


/**
 *
 * @author Alex Irusta
 */
public class RegistrationControllerTest extends ApplicationTest {

    private Hyperlink signUpLink;
    private TextField passwordTextField;
    private TextField confirmPasswordTextField;
    private TextField fullNameTextField;
    private TextField phoneTextField;
    private TextField cityTextField;
    private TextField addressTextField;
    private TextField zipTextField;
    private Button showPasswordButton;
    private Button signUpButton;

    /**
     * Method that calls the App's start() to open the Log In window and
     * initialises its Hyperlink
     *
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        new App().start(stage);

        signUpLink = lookup("#signUpLink").query();

    }

    /**
     * This method tests all functionalities of the SignUp window, including
     * cases where a field is left empty or incorrectly typed and the SignUp
     * button is pressed.
     */
    @Test
    public void test1_InitialState() {
        clickOn(signUpLink);

        passwordTextField = lookup("#passwordTextField").query();
        confirmPasswordTextField = lookup("#confirmPasswordTextField").query();
        fullNameTextField = lookup("#fullNameTextField").query();
        phoneTextField = lookup("#phoneTextField").query();
        cityTextField = lookup("#cityTextField").query();
        addressTextField = lookup("#addressTextField").query();
        zipTextField = lookup("#zipTextField").query();
        showPasswordButton = lookup("#showPasswordButton").query();
        signUpButton = lookup("#signUpButton").query();

        write("jfaoifjodinfmvad");
        eraseText(17);
        write("alexirustaprof@gmail.com");
        clickOn(passwordTextField);
        write("hola");
        clickOn(confirmPasswordTextField);
        write("hola");
        clickOn(passwordTextField);
        eraseText(5);
        clickOn(confirmPasswordTextField);
        eraseText(5);
        clickOn(passwordTextField);
        write("Cr7Ronaldo7");
        clickOn(confirmPasswordTextField);
        write("Cr7Ronaldo7");
        clickOn(showPasswordButton);

        clickOn(phoneTextField);
        write("kfk4fkdl");
        eraseText(9);
        write("640782345");
        eraseText(10);
        write("+34640782345");
        clickOn(cityTextField);
        write("Lemoniz");
        clickOn(addressTextField);
        write("Barrio Urizar 54");
        clickOn(signUpButton);
        clickOn("Aceptar");

        clickOn(fullNameTextField);
        write("feilkdfkdfkf");
        eraseText(14);
        write("Alex Irusta Mintegui");
        clickOn(showPasswordButton);
        clickOn(zipTextField);
        write("754948620");

        clickOn(signUpButton);
        clickOn("Aceptar");

        clickOn(zipTextField);
        eraseText(12);
        clickOn(signUpButton);

    }

}
