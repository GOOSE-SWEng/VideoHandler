import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class InteractiveLearningApp extends Application{
	
	
	public static void main(String[] args) {
		System.out.println("Running...");
		launch(args);
		System.out.println("Finished...");
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		//Load fxml file that contains menu bar
		Parent root = FXMLLoader.load(getClass().getResource("InteractiveLearningApp.fxml"));
		Scene scene = new Scene(root);
		
		//Load the style sheet
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		//Set the minimum size of the window
		primaryStage.setMinHeight(400);
		primaryStage.setMinWidth(600);
		
		primaryStage.setScene(scene);
		
		primaryStage.show();	
		
		Stage newStage = (Stage) scene.getWindow();
		newStage.show();
		
	}
}