package com.tjeason.mbz;

import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.StringReader;
import java.util.*;

/**
 * Primary handler for user inputs, outputs, and manipulating data.
 * @author T.J. Eason <tjeason@gmail.com>
 * @version 1.0
 */
public class Main {

    /**
     * Main method for handling user's command line arguments, and providing output.
     * @param args
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    public static void main(String[] args) throws ParserConfigurationException, SAXException {

        Help help = new Help();

        // Check if user entered an option.
        if(args.length > 0) {

            // Check if the artist option is used.
            if(args[0].equals("--artist")) {

                // Check if user entered the name of an artist.
                if(args.length >= 2) {
                    System.out.println("Searching tracks for artist, " + args[1] + "...");

                    // Use web service to find artist's tracks. XML response is returned.
                    Artist artist = new Artist();
                    String xmlResult = artist.searchTracksByArtistName(args[1]);

                   parseXMLResult(xmlResult, "track", "title");
                }

                // No artist name found. Show tips on how to use the artist option.
                else {
                    System.out.println("Please enter an artist name.");
                    help.showArtistOptions();
                }
            }

            // Check if user entered the help option.
            else if(args[0].equals("--help"))
                help.showOptions();

            // User entered unknown option.
            else {
                System.out.println("Please use one of the available options...");
                help.showOptions();
            }
        }

        // No options were used.
        else {
            System.out.println("Need help with commands?");
            help.showOptions();
        }
    }

    /**
     * Display the total number and name of the tracks found.
     * @param resultList
     */
    private static void displayResult(List<String> resultList) {

        int numberOfTracks = resultList.size();

        System.out.println("Tracks found: " + numberOfTracks);
        System.out.println("-------------------------------");

        for(String result : resultList)
            System.out.println(result);
    }

    /**
     * Retrieve the text found in the XML DOM element.
     * @param element
     * @return
     */
    private static String getCharacterDataFromElement(Element element) {

        Node child = element.getFirstChild();

        // Check if the child object is the specified type, character data.
        if (child instanceof CharacterData) {
            CharacterData characterData = (CharacterData) child;
            return characterData.getData();
        }

        return "";
    }

    /**
     * Parse the XML DOM elements to find an artist's tracks.
     * @param xmlResult
     * @param parentElem
     * @param childElem
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    private static void parseXMLResult(String xmlResult, String parentElem, String childElem) throws ParserConfigurationException, SAXException {

        // Use a list hash set to maintain a linked list of entries in the order they were inserted.
        List<String> trackList = new ArrayList<String>();
        trackList = new ArrayList<String>(new LinkedHashSet<String>(trackList));

        DocumentBuilder documentBuilder;

        try {
            // Create a new XML document.
            documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

            // Read the XML data into a character stream for parsing.
            Document document;
            InputSource inputSource = new InputSource();
            inputSource.setCharacterStream(new StringReader(xmlResult));

            try {
                // Parse the XML.
                document = documentBuilder.parse(inputSource);

                // Add all track elements in the XML and abstract them into a node collection.
                NodeList nodes = document.getElementsByTagName(parentElem);

                // Iterate through each node, and retrieve the track's title element and character data.
                for (int i = 0; i < nodes.getLength(); i++) {
                    Element element = (Element) nodes.item(i);
                    NodeList title = element.getElementsByTagName(childElem);
                    Element line = (Element) title.item(0);
                    trackList.add(getCharacterDataFromElement(line));
                }

                // Check if at least one or more tracks are found.
                if(!trackList.isEmpty())
                    removeDuplicates(trackList);

                // No tracks found
                else
                    System.out.println("No results found.");
            }

            // Throw SAX DOM exception.
            catch (Exception e) {
                System.out.println("ERROR >>> " + e.getMessage());
            }
        }

        // Throw parse configuration exception.
        catch(Exception e) {
            System.out.println("ERROR >>> " + e.getMessage());
        }
    }

    /**
     * Remove duplicate elements in a queried result list.
     * @param list
     */
    private static void removeDuplicates(List<String> list) {

        // Use tree set to remove duplicate elements and maintain order. Performance is O(log n).
        // Add the set back to the list.
        Set<String> set = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        set.addAll(list);
        list = new ArrayList<String>(set);

        displayResult(list);
    }
}
