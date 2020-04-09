package handlers;

import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.net.URI;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

public class VideoHandler {
	
	private List<MediaView> videoList = new ArrayList();
	private List<SubScene> vidlist = new ArrayList();
	
	public VideoHandler(NodeList videoNList) throws InterruptedException, IOException {
		//TODO create media files, media players and media view
		//Depending on the number of video files uploaded will create media view files
		//and add them to a list. Then return the list and use that to create slides
		for(int i = 0; i < videoNList.getLength(); i++) {
			Element element = (Element) videoNList.item(i);
		    
			int playDelay = Integer.parseInt(element.getElementsByTagName("starttime").item(0).getTextContent());
			boolean loop = Boolean.parseBoolean(element.getElementsByTagName("loop").item(0).getTextContent());
			int xPosition = Integer.parseInt(element.getElementsByTagName("xstart").item(0).getTextContent());
			int yPosition = Integer.parseInt(element.getElementsByTagName("ystart").item(0).getTextContent());
	
			//Create video media file
			File tempFile = new File(element.getElementsByTagName("urlname").item(0).getTextContent());//Create file using the url tag in xml file
			Media media = new Media(tempFile.toURI().toString());//Create a media file using the temp file URI
		
			//Create media player
			MediaPlayer mp = new MediaPlayer(media); //Create a media Player
			//Set the video to auto play
			mp.setAutoPlay(true);
			//set to loop
			mp.setCycleCount(MediaPlayer.INDEFINITE);
			
			
			
			//MediaView mv = new MediaView(mp);//Create a media view
			
			
			//Set the size of the media player(not resisable)
			mv.setFitHeight(600);
			mv.setFitWidth(360);
			
			//Loads the media player layout from a fxml file
			AnchorPane root = FXMLLoader.load(getClass().getClassLoader().getResource("videoPlayer.fxml"));
			((MediaView) root.getChildren().get(0)).setMediaPlayer(mp);
			
			MediaView mv = (MediaView) root.getChildren().get(0);
			//MediaView mv = new MediaView(mp);
			//DoubleProperty widthMV =  mv.fitWidthProperty();
			//DoubleProperty heightMV = mv.fitHeightProperty();
			//widthMV.bind(Bindings.selectDouble(mv.sceneProperty(), "width"));
			//heightMV.bind(Bindings.selectDouble(mv.sceneProperty(), "height"));

			
			//Setting start playing delay
			//Duration delay = new Duration(100000);
			//mp.setStartTime(delay);
			
			
			//newPane.getChildren().add(mv);
			//Create a subscene
			SubScene videoScene = new SubScene(root, 600 , 400);
			
			//Set the position
			videoScene.setLayoutX(xPosition);
			videoScene.setLayoutY(yPosition);

			
			
			vidlist.add(videoScene);
			//videoList.add(mv); //Add the media view to the list
		}
	}
	//Gets the media view list
	public List<MediaView> getMediaList(){
		return videoList;
	}
	
	public List<SubScene> getSceneList(){
		return vidlist;
	}
}
