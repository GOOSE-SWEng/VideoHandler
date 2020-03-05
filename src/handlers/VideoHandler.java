package handlers;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class VideoHandler {
	
	private List<MediaView> videoList = new ArrayList();
	
	public VideoHandler(NodeList videoNList) {
		//TODO create media files, media players and media view

		
		
		//Depending on the number of video files uploaded will create media view files
		//and add them to a list. Then return the list and use that to create slides
		for(int i = 0; i < videoNList.getLength(); i++) {
			Element element = (Element) videoNList.item(i);
			
			
			URI address = URI.create(element.getElementsByTagName("urlname").item(0).getTextContent());
			
			File file = new File(element.getElementsByTagName("urlname").item(0).getTextContent());
			int playDelay = Integer.parseInt(element.getElementsByTagName("starttime").item(0).getTextContent());
			boolean loop = Boolean.parseBoolean(element.getElementsByTagName("loop").item(0).getTextContent());
			int xPosition = Integer.parseInt(element.getElementsByTagName("xstart").item(0).getTextContent());
			int yPosition = Integer.parseInt(element.getElementsByTagName("ystart").item(0).getTextContent());
			
			System.out.println(address + ", " + playDelay + ", " + loop + ", " + xPosition + ", " +yPosition);

			address = file.toURI();
			
			
			
			
			
			
			File tempFile = new File(element.getElementsByTagName("urlname").item(0).getTextContent());//Create file using the url tag in xml file
			Media media = new Media(tempFile.toURI().toString());//Create a media file using the temp file URI
			MediaPlayer mp = new MediaPlayer(media); //Create a media Player
			MediaView mv = new MediaView(mp);//Create a media vie player

			videoList.add(mv); //Add the media view to the list
		}
	}
	//Gets the media view list
	public List<MediaView> getMediaList(){
		return videoList;
	}
}
