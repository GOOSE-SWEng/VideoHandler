package handlers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javafx.fxml.FXMLLoader;
import javafx.scene.SubScene;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;


public class VideoHandler {
	
	static MediaPlayer mp;
	//private List<MediaView> videoList = new ArrayList();
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
			mp = new MediaPlayer(media); //Create a media Player
			//Set the video to auto play
			//mp.setAutoPlay(true);
			
			//set to loop
			if(loop) {
				mp.setCycleCount(MediaPlayer.INDEFINITE);
			}
			
			
			//Introduces the delay
			ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
			Runnable playDelay1 = () -> mp.play();
			executorService.schedule(playDelay1, 5, TimeUnit.SECONDS);
			
			
			//Loads the media player layout from a fxml file
			BorderPane root = FXMLLoader.load(getClass().getClassLoader().getResource("videoPlayer.fxml"));
			
			MediaView mv = (MediaView) root.getCenter();
			mv.setMediaPlayer(mp);
			
			//works...
			//MediaView mv = new MediaView(mp);
			//mv.setFitHeight(350);
			//mv.setFitWidth(600);
			//root.setCenter(mv);
			
			
			
			//Setting start playing delay
			
			

			//Create a sub scene
			SubScene videoScene = new SubScene(root, 600 , 400);
			
			//Set the position
			videoScene.setLayoutX(xPosition);
			videoScene.setLayoutY(yPosition);
			
			vidlist.add(videoScene);

		}
	}
	
	public static void delayPlay() {
		mp.play();
	}
	
	
	public List<SubScene> getSceneList(){
		return vidlist;
	}
}
