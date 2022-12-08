package edu.vanier.template.controller;

import edu.vanier.template.save.SaveHandler;
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

    SaveHandler saver;

    private final Stage stage;

    private final CreateNewDrumController controller;

    private final double prevDeltaTime;

    @SuppressWarnings("LeakingThisInConstructor")
    public SaveRecordingController(Stage owner, CreateNewDrumController controller) throws IOException {
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

        prevDeltaTime = CreateNewDrumController.deltaTimeValue;
        CreateNewDrumController.deltaTimeValue = 0;
    }

    @FXML
    public void initialize() {
        saveBtn.setOnAction((event) -> {
            if(!nameTxt.getText().isEmpty()) {
                String folderName = nameTxt.getText();
                saver = new SaveHandler(folderName);
                saver.createTracker(controller.simulation.formable.deconstruct());
                controller.btnStopRecord.setDisable(false);
                controller.btnStartRecord.setDisable(true);
                controller.currentSaveHandler = saver;
                controller.simulation.getPhysics().setSaveHandler(saver);
                controller.simulation.physics.startRecording();
            }
            CreateNewDrumController.deltaTimeValue = prevDeltaTime;
            stage.close();
        });

        stage.setOnCloseRequest((event) -> {
            CreateNewDrumController.deltaTimeValue = prevDeltaTime;
        });
    }

}
