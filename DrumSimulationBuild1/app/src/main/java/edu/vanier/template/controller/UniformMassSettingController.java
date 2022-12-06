/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.template.controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Spyros
 */
public class UniformMassSettingController {
    
    @FXML
    Button confirmButton;
            
    @FXML
    TextField massOneText;
    
    public double massOne = 1;
    
    private final Stage owner;
    private final Stage stage;
    
    private final CreateNewDrumController controller;
    
    public UniformMassSettingController(Stage owner, CreateNewDrumController controller) throws IOException {
        this.owner = owner;
        this.controller = controller;
        stage = new Stage();
        stage.initOwner(owner);
        stage.initModality(Modality.WINDOW_MODAL);
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/uniformMass.fxml"));
        loader.setController(this);
        AnchorPane root = loader.load();
        stage.setScene(new Scene(root, root.getPrefWidth(), root.getPrefHeight()));
        stage.setTitle(owner.getTitle());
        stage.show();
    }
    
    @FXML
    public void initialize() {
        
        confirmButton.setOnAction((event) -> {
            try {
                setMassOne(Double.valueOf(massOneText.getText()));
                if(massOne <= 0) {
                    throw new NumberFormatException("Please enter a positive mass.");
                }
                controller.setMassOneDC(getMassOne());
            } catch(NumberFormatException | NullPointerException e) {
                Alert massError = new Alert(Alert.AlertType.ERROR);
                massError.setHeaderText("Incorrect input for mass");
                massError.setContentText(e.getMessage());
                massError.setTitle(stage.getTitle());
                massError.showAndWait();
            }
            stage.close();
        });
    }

    public double getMassOne() {
        return this.massOne;
    }

    public void setMassOne(double massOne) {
        this.massOne = massOne;
    }

    
    
    
}
