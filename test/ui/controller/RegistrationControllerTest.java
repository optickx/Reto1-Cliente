package ui.controller;

import app.App;
import java.io.IOException;
import static java.rmi.Naming.lookup;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import ui.controller.RegistrationController;

import javafx.stage.Stage;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isInvisible;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.base.NodeMatchers.*;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;
import ui.controller.LoginController;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.junit.Test;
import javafx.stage.Stage;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;

import static org.testfx.matcher.control.TextInputControlMatchers.hasText;

/**
 *
 * @author 2dam
 */
public class RegistrationControllerTest extends ApplicationTest {

    private TextField emailTextField;
    
    @BeforeClass
    @Override
    public void start(Stage stage) throws Exception {
        
        emailTextField = lookup("#emailTextField").query();
        
    }

    @Test
    public void test1_InitialState() {
        clickOn(emailTextField);
        //Clicks on hyperlink
        //Checks textfields
        // verifyThat("#emailErrorLabel", isInvisible());
        // verifyThat("#confirmPasswordErrorLabel", isInvisible());
        // verifyThat("#zipErrorLabel", isInvisible());
        /* verifyThat("#personalInfoErrorLabel", isInvisible());
        verifyThat("#emailTextField", hasText(""));
        verifyThat("#passwordField", hasText(""));
        verifyThat("#confirmPasswordField", hasText(""));
        verifyThat("#passwordTextField", hasText(""));
        verifyThat("#confirmPasswordTextField", hasText(""));
        verifyThat("#fullNameTextField", hasText(""));
        verifyThat("#addressTextField", hasText(""));
        verifyThat("#zipTextField", hasText(""));
        verifyThat("#cityTextField", hasText(""));
        verifyThat("#phoneTextField", hasText(""));
         */
        //Check SignUp button and hyperlink

        //Check if error labels are desabled
    }

    /* private void clickOn(String signUpButton) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
     */
}
