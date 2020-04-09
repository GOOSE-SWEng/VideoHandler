package slide;

import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.SubScene;
import javafx.scene.layout.Border;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;

//Class to create slides
public class Slide {
	
	private Pane slide = new Pane();
	//private double slideX = 0;//default slide start position
	//private double slideY = 40;
	
	public Slide(List<SubScene> list) {

		//add all the media view into a pane
		for(int i = 0; i < list.size(); i++) {
			slide.getChildren().add(list.get(i));
		}
	}
	public Pane getSlide() {
		return slide;
	}
}
