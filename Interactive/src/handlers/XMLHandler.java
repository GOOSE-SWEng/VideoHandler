package handlers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javafx.scene.SubScene;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaView;
import slide.Slide;

public class XMLHandler {
	
	private Pane slidePane; //Pane of a single slide (make into a list when get to making more than one slide)
	private NodeList slideList; //NOT SURE IF I NEED THIS FIELD
	
	public XMLHandler(File inputFile) throws ParserConfigurationException, SAXException, IOException, InterruptedException {
		
		//Makes the XML file into a readable file
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc;
		doc = dBuilder.parse(inputFile);
        doc.getDocumentElement().normalize();
        
        //Get all slides into a node list (everything contained within a slide tag is a node)
        slideList = doc.getElementsByTagName("slide");
 
        //Go through the slide tags and separate all the media
        for(int i = 0; i < slideList.getLength(); i++) {
        	
        	//Separate all the nodes into respective media:

        	//Video handling:
            //Video node list store all info tagged within "video" tag
        	Element slideElement = (Element) slideList.item(i);//Need to make it an element so i could use get tags
        	NodeList videoNList = slideElement.getElementsByTagName("video");
        	
        	//Pass on the video list to the Video handler, which creates a list of videos with a media player
            if(videoNList != null) {
            	 VideoHandler videoHandler = new VideoHandler(videoNList);
            	 
            	 //Create a slide by passing in the video list
            	// slidePane = new Slide(videoHandler.getMediaList()).getSlide();
            	 slidePane = new Slide(videoHandler.getSceneList()).getSlide();
            } else {//If the slide contains no videos print out the message
            }
        }
	}
	
	//Returns the slide to the controller class to be displyed
	public Pane getSlide() {
		return slidePane;
	}
}
