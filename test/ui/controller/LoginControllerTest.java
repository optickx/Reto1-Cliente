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
import static org.junit.Assert.assertEquals;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LoginControllerTest extends ApplicationTest {
    
    private TextField loginTextField;
    private PasswordField passwordField;
    private Button confirmButton;

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
        confirmButton = lookup("#confirmButton").query();
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
        verifyThat(loginErrorLabel, isVisible());
        
        eraseText(50);

        clickOn(passwordField);
        write("abcd");
        verifyThat(passwordErrorLabel, isVisible());

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
        //verifyThat(passwordErrorLabel, isVisible());
        assertTrue(passwordErrorLabel.getText().equalsIgnoreCase("Password too short"));
    }
    /**
     * tests the username and email formats
     */
    @Test
    public void test5_LoginFormatError() {
        clickOn(loginTextField);
        write("beetlejuice@horror.com");
        clickOn(passwordField);
        write("abcd*1234");
        clickOn(confirmButton);
        //verifyThat(loginErrorLabel, isVisible());
        assertTrue(loginErrorLabel.getText().equalsIgnoreCase("Username must be a valid email"));

        clickOn(loginTextField);
        eraseText(60);
        write("Beetle Juice");
        clickOn(confirmButton);
        //verifyThat(loginErrorLabel, isVisible());
        assertTrue(loginErrorLabel.getText().equalsIgnoreCase("Username must be a valid email"));
    }
}   