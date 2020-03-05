package slide;

import java.util.List;

import javafx.scene.SubScene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaView;

public class Slide {
	SubScene slide;
	
	public Slide(List<MediaView> videoList) {
		System.out.println("Creating a slide");
		//Should add fields: lists of all the elements of a slide
		AnchorPane newPane = new AnchorPane();
		newPane.getChildren().addAll(videoList);
		
		//TODO add all the elements to the slide
		
	}
	
	public SubScene getSlide() {
		return slide;
	}
}
