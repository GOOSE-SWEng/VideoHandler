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
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import slide.Slide;

//This class to control all the buttons 
public class InteractiveLearningAppController implements Initializable{

	@FXML
	private Button openBT;
	
	@FXML
	private AnchorPane anchorPane;
	
	@FXML
	private Pane slidePane;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {		
	}
	
	@FXML
	public void openFile(ActionEvent event) throws ParserConfigurationException, SAXException, IOException, InterruptedException {
		FileChooser fc = new FileChooser();
		fc.setInitialDirectory(new File("D:\\GitHub\\Interactive"));
		fc.getExtensionFilters().addAll(new ExtensionFilter("txt Files", "*.txt","*.TXT"),
										new ExtensionFilter("XML Files", "*.xml", "*.XML"));
		File selectedFile = fc.showOpenDialog(null);
		
		if(selectedFile != null) {
			XMLHandler xmlHandler = new XMLHandler(selectedFile);
			
			Pane newSlide = xmlHandler.getSlide();	

			slidePane.getChildren().clear();
			slidePane.getChildren().add(newSlide);
		} else {
			//TODO make a label to display a message that the file was not opened
		}
	}
}
