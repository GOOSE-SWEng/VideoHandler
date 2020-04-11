

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

public class VideoPlayerController{

		@FXML
		Button play;
		Button stop;
		Button fullscrn;
		Button caption;
		Slider ctrlbar;
		@FXML
		Slider volume;
		Label timing;
		@FXML
		MediaView mediaView;
		
		SubScene videoScene;
		
		private List<SubScene> vidlist = new ArrayList();
		
		public VideoPlayerController() {
			
			
		}
		public void play(ActionEvent event) {
			//TODO: make video play and pause with the same button.
			if(mediaView.getMediaPlayer().getStatus() == MediaPlayer.Status.PLAYING) {
				mediaView.getMediaPlayer().pause();
				play.setText("Play");//TODO: replace with an icon
			}
			else if(mediaView.getMediaPlayer().getStatus() != MediaPlayer.Status.PLAYING){
				mediaView.getMediaPlayer().play();
				play.setText("pause");//TODO: replace with an icon
			}
		}
		
		public void stop(ActionEvent event) {
			mediaView.getMediaPlayer().stop();
			//TODO: stop the video
		}
		
		public void setFullScreen(ActionEvent event) {
			//TODO: set video full screen and back
		}
		
		public void captionOn(ActionEvent event) {
			//TODO: turn on captions
		}
}
