package ui.controller;

import app.App;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isFocused;
import static org.testfx.matcher.base.NodeMatchers.isVisible;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LoginControllerTest extends ApplicationTest {
    
    private TextField loginTextField, passwordTextField;
    private PasswordField passwordField;
    private Button confirmButton, showPasswordButton;

    private Label loginErrorLabel, passwordErrorLabel;
    
    private final String EMPTY_TEXT = ""; // used to check empty values

   
    /**
     * this method is executed before the rest, links the elements
     * of the controller in order to test them
     */ 
    @Override
    public void start(Stage stage) throws Exception {
        new App().start(stage);
        loginTextField = lookup("#loginTextField").query();
        passwordField = lookup("#passwordField").query();
        passwordTextField = lookup("#passwordTextField").query();
        confirmButton = lookup("#confirmButton").query();
        showPasswordButton = lookup("#showPasswordButton").query();
        loginErrorLabel = lookup("#loginErrorLabel").query();
        passwordErrorLabel = lookup("#passwordErrorLabel").query();
    }
    /**
     * test the "server error" works properly. only when server is shut down
     */
    @Ignore
    @Test
    public void test1_ServerError() {
        clickOn(loginTextField);
        write("ejem@gmail.com");
        clickOn(passwordField);
        write("abcd*1234");
        clickOn(confirmButton);
        verifyThat("Error", isVisible());
        push(KeyCode.SPACE);
    }
    /**
     * Check that the login field is focused, and that the
     * confirm button is disabled.
     */
    @Test
    public void test2_InitialState() {
        verifyThat(loginTextField, isFocused());

        assertEquals(EMPTY_TEXT, loginTextField.getText());
        assertEquals(EMPTY_TEXT, passwordField.getText());
    }
    /**
     * Test alert if username or password format are wrong
    */
    @Test
    public void test3_FormatErrors() {
        clickOn(loginTextField);
        write("abcdefghijklmnopqrstuvwxyz.org");
        assertTrue(loginErrorLabel.getText().equals("Username must be a valid email"));
        
        eraseText(50);

        clickOn(passwordField);
        write("abcd");
        System.out.println(passwordErrorLabel.getText());
        assertTrue(passwordErrorLabel.getText().equalsIgnoreCase("Password must be between 8 and 25 characters long"));

    }
    /**
     * Test  incorrect password error.
     */
    @Test
    public void test4_IncorrectPasswordError() {
        clickOn(loginTextField);
        write("ejem@gmail.com");
        clickOn(passwordField);
        write("whatTheHellBruh");
        clickOn(confirmButton);
        assertTrue(passwordErrorLabel.getText().equalsIgnoreCase("Incorrect password"));
    }
    /**
     * tests the username and email formats
     */
    @Test
    public void test5_LoginNotExistError() {
        clickOn(loginTextField);
        write("beetlejuice@horror.com");
        clickOn(passwordField);
        write("abcd*1234");
        clickOn(confirmButton);
        //verifyThat(loginErrorLabel, isVisible());
        assertTrue(loginErrorLabel.getText().equalsIgnoreCase("Username does not exist"));

        clickOn(loginTextField);
        eraseText(60);
        write("Beetle Juice");
        clickOn(confirmButton);
        //verifyThat(loginErrorLabel, isVisible());
        assertTrue(loginErrorLabel.getText().equalsIgnoreCase("Username must be a valid email"));
    }
    @Test
    public void test6_ShowPasswordButton() {
        clickOn(passwordField);
        write("4 c0mpl3t3ly n0rm4l p4ssw0rd");
        String pwd = passwordField.getText();
        clickOn(showPasswordButton);
        assertTrue(passwordTextField.getText().equals(pwd));
    }
}   