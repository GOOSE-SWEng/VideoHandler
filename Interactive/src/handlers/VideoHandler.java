package handlers;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.fxml.FXMLLoader;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.util.Duration;


public class VideoHandler {
	
	private MediaPlayer mp;
	private Duration totTime;
	private Pane toolbarNode;
	private Slider volumeSlider;
	private Label currentTimeLabel;
	private	Label totTimeLabel;
	
	
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
			
//Loads the media player layout from a FXML file
			BorderPane root = FXMLLoader.load(getClass().getClassLoader().getResource("videoPlayer.fxml"));
			
//Video control bar, retrieve from the root;
			toolbarNode = (Pane) root.getBottom();
			
			currentTimeLabel = (Label) toolbarNode.getChildren().get(7);
			totTimeLabel = (Label) toolbarNode.getChildren().get(9);

			
//Create video media file
			File tempFile = new File(element.getElementsByTagName("urlname").item(0).getTextContent());//Create file using the url tag in xml file
			Media media = new Media(tempFile.toURI().toString());//Create a media file using the temp file URI
		
//Create media player
			mp = new MediaPlayer(media);
			
			//Volume slider, 
			volumeSlider = (Slider) toolbarNode.getChildren().get(6);
			volumeSlider.setValue(mp.getVolume()*100);

			volumeSlider.valueProperty().addListener(new InvalidationListener() {
				@Override
				public void invalidated(Observable arg0) {
					mp.setVolume(volumeSlider.getValue()/100);
				}
			});
			
			
			
//Set the video to auto play[Not sure if needed, video is auto played when the there is a delay set]
			//mp.setAutoPlay(true);
			
			
//Set video to loop
			if(loop) {
				mp.setCycleCount(MediaPlayer.INDEFINITE);
			}
			
			
//Playback delay, set the video to start playing after the specified time when the video is loaded
			ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
			Runnable setPlayDelay = () -> mp.play();
			executorService.schedule(setPlayDelay, playDelay, TimeUnit.MILLISECONDS);
			
//Video control bar
			toolbarNode = (Pane) root.getBottom();
			

			
//Playback slider		
			Slider playbackSlider = (Slider) toolbarNode.getChildren().get(5);
			
//Set the current play time to the time specified by the playback slider[sometimes the current time label is not set properly, maybe due to seek function doesnt change the current video time fast enough and the set time label gets the wrong time]
																		
			playbackSlider.valueProperty().addListener(new InvalidationListener() {
				@Override
				public void invalidated(Observable arg0) {
					
					if(playbackSlider.isValueChanging() || playbackSlider.isPressed()) {
						if(mp.getStatus() == Status.PLAYING  || mp.getStatus() == Status.PAUSED) {

							
							mp.seek(totTime.multiply(playbackSlider.getValue()/100));
							setTimeLabel(mp.getCurrentTime(), currentTimeLabel);
						}
						//When the video is stopped and the slider is moved then set media player to paused since seek function cannot be used while the status is STOPPED
						else if(mp.getStatus() == Status.STOPPED) {
							
							mp.pause();
							mp.seek(totTime.multiply(playbackSlider.getValue()/100));
						}	
					}
				}
			});
			
//Set on paused: when the video is paused, change the icon of the play button to play icon
			mp.setOnPaused(new Runnable() {
				
				@Override
				public void run() {
					Button play = (Button) toolbarNode.getChildren().get(0);
					//play.setText("Play");
					
					
					
					ImageView playImg = (ImageView) play.getGraphic();
					playImg.setImage(new Image(getClass().getResourceAsStream("/graphics/play.png")));
					//TODO: change icon
					
					
					setTimeLabel(mp.getCurrentTime(), currentTimeLabel);
					
					
				}
				
			});
//Set on halted: when critical error occurs
			mp.setOnHalted(new Runnable() {
				@Override
				public void run() {
					// TODO: critical error. figure out what that would be and try to manage it
					System.out.println("HALTED");
					
				}
			});
			
//Set on stopped: [might not need to to do anything]
			mp.setOnStopped(new Runnable() {
				@Override
				public void run() {	
					playbackSlider.setValue(0);
					//Sets the current time to 0
					setTimeLabel(mp.getCurrentTime(), currentTimeLabel);
					
					
					//TODO: change icon to play
					Button play = (Button) toolbarNode.getChildren().get(0);
					ImageView playImg = (ImageView) play.getGraphic();
					playImg.setImage(new Image(getClass().getResourceAsStream("/graphics/play.png")));
				}
			});
			
//Set on ready: when the video is ready, set the total time of the video label
			mp.setOnReady(new Runnable() {
	            public void run() {
	                Platform.runLater(new Runnable() {
						public void run() {
							totTime = mp.getMedia().getDuration();
							setTimeLabel(totTime, totTimeLabel);
						}
					});
	            }
	        });
			
//Set on end of media: set the slider to the beginning after video ends and stop the player
			mp.setOnEndOfMedia( new Runnable() {
				@Override
				public void run() {
					if (!loop) {
						mp.stop();
					}
					//Play button displays play icon
					Button play = (Button) toolbarNode.getChildren().get(0);
					play.setText("Play");
					//TODO: Change the icon
					
					
					
					mp.seek(totTime.multiply(playbackSlider.getValue()/100));
				}
			});
			
//Set on playing: Set the play button to display pause icon when the video is playing
			mp.setOnPlaying(new Runnable() {
				@Override
				public void run() {
					Button play = (Button) toolbarNode.getChildren().get(0);
					play.setText("Pause");	
					
					
					ImageView playImg = (ImageView) play.getGraphic();
					playImg.setImage(new Image(getClass().getResourceAsStream("/graphics/pause.png")));
				}
			});
			
			
			
//Set the current time label and play back slider to follow the current time of the video being played
			mp.currentTimeProperty().addListener(new InvalidationListener() {
				@Override
				public void invalidated(Observable arg0) {
							Duration currentTime = mp.getCurrentTime();
							playbackSlider.setValue(currentTime.toMillis()/totTime.toMillis()*100);
							setTimeLabel(currentTime, currentTimeLabel);
				}
			});
			
//Add media player to media view
			MediaView mv = (MediaView) root.getCenter();
			mv.setMediaPlayer(mp);
//Create a sub scene
			SubScene videoScene = new SubScene(root, 600 , 400);
//Set sub scene position
			videoScene.setLayoutX(xPosition);
			videoScene.setLayoutY(yPosition);
			
			vidlist.add(videoScene);
		}
	}
//Set the text of the label to display the time
	private void setTimeLabel(Duration time, Label label) {
		System.out.println("Setting time label in function: " + time);
		int min = (int) time.toMinutes();
		int sec= (int) (time.toSeconds()-60*min);
		if(sec < 10) {
			label.setText(min+ ":0" + sec);
		}else {
			label.setText(min+ ":" + sec);
		}
	}
	
	public List<SubScene> getSceneList(){
		return vidlist;
	}
}
