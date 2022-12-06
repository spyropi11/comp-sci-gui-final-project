package edu.vanier.template.controller;

import edu.vanier.template.save.SaveEnvelope;
import edu.vanier.template.simulation.Simulation;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DownloadSaveController {

    @FXML
    TextField nameTxt;
    
    @FXML
    Button loadBtn;
    
    SaveEnvelope downloader;
    
    private final Stage owner;
    private final Stage stage;
    
    private final CreateNewDrumController controller;
    
    @SuppressWarnings("LeakingThisInConstructor")
    public DownloadSaveController(Stage owner, CreateNewDrumController controller) throws IOException {
        this.owner = owner;
        this.controller = controller;
        stage = new Stage();
        stage.initOwner(owner);
        stage.initModality(Modality.WINDOW_MODAL);
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SaveRecording.fxml"));
        loader.setController(this);
        BorderPane root = loader.load();
        stage.setScene(new Scene(root, root.getPrefWidth(), root.getPrefHeight()));
        stage.setTitle(owner.getTitle());
        stage.show();
    }
    
    @FXML
    public void initialize() {
        loadBtn.setOnAction((event) -> {
            try {
                if(!nameTxt.getText().isEmpty()) {
                    String folderName = nameTxt.getText();
                    downloader = new SaveEnvelope(folderName);
                    downloader.download();
                    try {
                        owner.close();
                        try {
                            controller.simulation.physics.stopTimer();
                        } catch(NullPointerException e) {

                        }
                        Stage loadStage = new Stage();
                        FXMLLoader loadSaveDrum = new FXMLLoader(getClass().getResource("/fxml/Scene2NewDream.fxml"));
                        CreateNewDrumController mainController = new CreateNewDrumController(loadStage);
                        loadSaveDrum.setController(mainController);
                        BorderPane rootLoadDrum = loadSaveDrum.load();
                        Scene scene = new Scene(rootLoadDrum, rootLoadDrum.getPrefWidth(), rootLoadDrum.getPrefHeight());
                        loadStage.setScene(scene);
                        loadStage.setTitle("Drum Simulation.");
                        loadStage.sizeToScene();
                        loadStage.show();

                        mainController.setSimulation(new Simulation(downloader.getSaveDrum().getFormable()));

                        loadStage.addEventHandler(KeyEvent.KEY_PRESSED, (value) -> {
                            mainController.simulation.translate(value.getCode());
                        });

                        mainController.simulation.setCloseSim(stage);
                        rootLoadDrum.setCenter(mainController.simulation.getRoot());
                        mainController.btnConfirm.setVisible(false);
                        mainController.simulation.physics.startTimer();
                        mainController.disableSettings();
                        mainController.menuDuringSim();
                    } catch (IOException ex) {}
                    stage.close();
                }
            } catch(IOException e) {
                Alert fileError = new Alert(Alert.AlertType.ERROR);
                fileError.setHeaderText("Folder does not exist or is corrupted.");
                fileError.setContentText("Please verify that the following folder exists: " + System.getProperty("user.home") + "\\Drum Sim save folders");
                fileError.showAndWait();
            }
        });
    }
    
}