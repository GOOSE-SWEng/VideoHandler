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

import javafx.scene.media.MediaView;
import slide.Slide;

public class XMLHandler {
	List<MediaView> videoList = new ArrayList<MediaView>();
	//List<> imgList = new ArrayList<>();
	//List<> audioList = new ArrayList<>();
	//List<> graphicsList = new ArrayList<>();
	//List<> textList = new ArrayList<>();
	
	Slide newSlide;
	
	private NodeList slideList;
	
	public XMLHandler(File inputFile) throws ParserConfigurationException, SAXException, IOException {
		System.out.println("XMLHandler started");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc;
		doc = dBuilder.parse(inputFile);
        doc.getDocumentElement().normalize();
        //Get all slides into a node list
        slideList = doc.getElementsByTagName("slide");
        
        
        List<Node> tempNodeList = new ArrayList<Node>(); //to store the information of the current slide being created
        
        //Go through the slide tags and separate all the media
        for(int i = 0; i < slideList.getLength(); i++) {
        	
        	tempNodeList.add(slideList.item(i));//Should store all the items for a slide to this list
        	
        	//Separate all the nodes into respective media:
        	
        	//Video handling
            //Video node list: store all info tagged within "video" tag
        	
        	Element slideElement = (Element) slideList.item(i);//Need to make it an element so i could use get tags
        	NodeList videoNList = slideElement.getElementsByTagName("video");
        	
            if(videoNList != null) {
            	 VideoHandler videoHandler = new VideoHandler(videoNList);
            	 
            	 videoHandler.getMediaList();//Where to put this?
            	 
            	 List<MediaView> videoList = videoHandler.getMediaList();
            	 for(int j = 0; i < videoList.size(); i++) {
            		 //TODO add media view files to mewdia now>?
            	 }
            	 
            	 //TODO 
            } else {
            	
            }
            
            newSlide = new Slide(videoList);
        }
	}
	
	public Slide getSlide() {
		return newSlide;
	}
}
