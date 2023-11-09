package ui.controller;

import app.App;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import packets.User;

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
public class RegisteredControllerTest extends ApplicationTest {
    private TextField loginTextField;
    private PasswordField passwordField;
    private Button confirmButton;

    
    private Label greetingLabel;
    private Button logoutButton;
    private User loggedUser;
    
    private final String EMPTY_TEXT = ""; // used to check empty values
    /**
     * this method is executed before the rest, links the elements
     * of the controller in order to test them
     */ 
    @Override
    public void start(Stage stage) throws Exception {
        new App().start(stage);        
    }
    /**
     * test the "server error" works properly. only when server is shut down
     */
   
    @Test
    public void test1Access() {
        loginTextField = lookup("#loginTextField").query();
        passwordField  = lookup("#passwordField").query();
        confirmButton = lookup("#confirmButton").query();
        clickOn(loginTextField);
        write("ejem@gmail.com");
        clickOn(passwordField);
        write("abcd*1234");
        clickOn(confirmButton);


        greetingLabel = lookup("#greetingLabel").query();
        logoutButton = lookup("#logoutButton").query();
        loggedUser = RegisteredController.loggedUser;
        //System.out.println(loggedUser.getPassword());
    }
}
