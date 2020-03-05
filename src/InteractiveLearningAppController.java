import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import handlers.XMLHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

//This class to control all the buttons
public class InteractiveLearningAppController implements Initializable{

	@FXML
	private Button openBT;
	
	@FXML
	private AnchorPane anchorPane;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
			
	}
	
	@FXML
	public void openFile(ActionEvent event) throws ParserConfigurationException, SAXException, IOException {
		FileChooser fc = new FileChooser();
		fc.setInitialDirectory(new File("M:\\3rdYear\\SwEngProject\\InteraActiveApp"));
		fc.getExtensionFilters().addAll(new ExtensionFilter("txt Files", "*.txt","*.TXT"),
										new ExtensionFilter("XML Files", "*.xml", "*.XML"));
		File selectedFile = fc.showOpenDialog(null);
		
		if(selectedFile != null) {
			//TODO Load file onto XML handler
			XMLHandler xmlHandler = new XMLHandler(selectedFile);
			
			//xmlHandler.getSlide().getSlide();
			
			AnchorPane pane = FXMLLoader.load(getClass().getResource("Main.fxml"));
			
			pane.getChildren().add(0,xmlHandler.getSlide().getSlide());
			
			anchorPane.getChildren().set(0, pane);
			System.out.println("File loaded successfuly");
		} else {
			//TODO make a label to display a message that the file was not opened
			System.out.println("404");
		}
	}
}
