/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.template.controller;

import edu.vanier.template.save.SaveEnvelope;
import edu.vanier.template.simulation.Simulation;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Spyros
 */
public class UniformMassSettingController {
    
    private final Stage owner;
    private final Stage stage;
    
    private final CreateNewDrumController controller;
    
    //@SuppressWarnings("LeakingThisInConstructor")
    public UniformMassSettingController(Stage owner, CreateNewDrumController controller) throws IOException {
        this.owner = owner;
        this.controller = controller;
        stage = new Stage();
        stage.initOwner(owner);
        stage.initModality(Modality.WINDOW_MODAL);
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/uniformMass.fxml"));
        loader.setController(this);
        BorderPane root = loader.load();
        stage.setScene(new Scene(root, root.getPrefWidth(), root.getPrefHeight()));
        stage.setTitle(owner.getTitle());
        stage.show();
    }
    
    @FXML
    public void initialize() {
        
        
        
    }
    
}
