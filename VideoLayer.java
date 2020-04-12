package media;

import java.util.ArrayList;

import javafx.scene.SubScene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;

public class VideoLayer {
	int height;
	int width;
	StackPane sp = new StackPane();
	Canvas canvas = new Canvas(width,height);
	ArrayList<Video> video = new ArrayList<Video>();
	SubScene window = new SubScene(sp,width,height);
	
	public VideoLayer(int width,int height){
		this.height = height;
		this.width = width;
		sp.getChildren().add(canvas);
	}
	
	public void add() {
		//constructor for the audio object
	}
	
	public void remove(Video object) {
		sp.getChildren().remove(object);
	}
	
	public SubScene get() {
		return (window);
	}
}