package ui.controller;

import java.util.logging.Logger;

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import logic.Signable;
import javafx.scene.control.ButtonType;

public abstract class GenericController {

    protected static final Logger LOGGER = 
        Logger.getLogger("package view.controller\":");

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
     * @return The Stage object initialized by this controller.
     */
    public Stage getStage(){
        return stage;
    }
    /**
     * sets the Stage object related to this controller. 
     * @param stage The Stage object to be initialized.
     */
    public void setStage(Stage stage){
        this.stage = stage;
    }
    /**
     * shows an error message in an alert dialog.
     * @param errorMsg The error message to be shown.
     */
    protected void showErrorAlert(String errorMsg){
        //Shows error dialog.
        Alert alert =
            new Alert(Alert.AlertType.ERROR, errorMsg, ButtonType.OK);
        // alert.getDialogPane().getStylesheets().add(getClass().getResource("/javafxapplicationud3example/ui/view/customCascadeStyleSheet.css").toExternalForm());
        alert.showAndWait();
        
    }    
}