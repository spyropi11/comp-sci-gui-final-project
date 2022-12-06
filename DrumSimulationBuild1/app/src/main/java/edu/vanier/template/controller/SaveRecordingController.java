package edu.vanier.template.controller;

import edu.vanier.template.save.SaveEnvelope;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SaveRecordingController {
    
    @FXML
    TextField nameTxt;
    
    @FXML
    Button saveBtn;
    
    private boolean cancelled = true;
    
    SaveEnvelope saver;
    
    @SuppressWarnings("LeakingThisInConstructor")
    public SaveRecordingController(Stage owner, CreateNewDrumController controller) throws IOException {
        Stage stage = new Stage();
        stage.initOwner(owner);
        stage.initModality(Modality.WINDOW_MODAL);
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SaveRecording.fxml"));
        loader.setController(this);
        BorderPane root = loader.load();
        stage.setScene(new Scene(root, root.getPrefWidth(), root.getPrefHeight()));
        stage.show();
        
        double prevDeltaTime = CreateNewDrumController.deltaTimeValue;
        CreateNewDrumController.deltaTimeValue = 0;
        
        saveBtn.setOnAction((evenet) -> {
            try {
                if(!nameTxt.getText().isEmpty()) {
                    String folderName = nameTxt.getText();
                    saver = new SaveEnvelope(folderName);
                    saver.create();
                    controller.simulation.physics.startRecording();
                    cancelled = false;
                }
            } catch(IOException e) {}
            stage.close();
        });
        
        stage.setOnCloseRequest((event) -> {
            if(!cancelled) {
                controller.btnStopRecord.setDisable(false);
                controller.btnStartRecord.setDisable(true);
                controller.currentSaveEnvelope = saver;
            }
            CreateNewDrumController.deltaTimeValue = prevDeltaTime;
        });
        
    }
    
}