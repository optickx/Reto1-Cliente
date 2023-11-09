package app;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import ui.controller.LoginController;

/**
 * Starting point of the program
 *
 * @author dani
 */
public class App extends Application {

    /**
     * This method is the entry point for the JavaFX application. It loads the
     * login view from an FXML file, initializes the LoginController, and sets
     * up the application's primary stage.
     *
     * @param stage The primary stage of the JavaFX application.
     */
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader loader
                    = new FXMLLoader(getClass().getClassLoader().getResource("ui/view/LoginView.fxml"));
            Parent root = (Parent) loader.load();
            // Obtain the Sign In window controller
            LoginController controller
                    = LoginController.class
                            .cast(loader.getController());
            controller.setStage(stage);
            controller.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * The main method for launching the JavaFX application. It's the starting
     * point of the application.
     *
     * @param args Command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
