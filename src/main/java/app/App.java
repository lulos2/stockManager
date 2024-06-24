package app;

import app.util.DatabaseUtil;
import app.util.Paths;
import app.util.StageManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Objects;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        DatabaseUtil.createTablesIfNotExist();
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource(Paths.ICONAPP)).toExternalForm()));

        StageManager.setPrimaryStage(stage);

        FXMLLoader loader = new FXMLLoader(getClass().getResource(Paths.MAIN_FXML));
        AnchorPane pane = loader.load();

        Scene scene = new Scene(pane);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(Paths.STYLES_CSS)).toExternalForm());
        StageManager.getPrimaryStage().setScene(scene);
        stage.show();
    }
}