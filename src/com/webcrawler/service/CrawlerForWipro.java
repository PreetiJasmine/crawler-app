package com.webcrawler.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * This main class contains the logic of reading through the page within a domain and display all related
 * links (links to other pages within the same domain and external links)
 * 
 * @author Preeti.Jasmine
 *
 */
public class CrawlerForWipro {

	static List<String> processedUrls = null;
	static int count = 0;
	static String parentURL = null;
	static Map<String, List<String>> siteTree = null;
	static String WIPRO_DIGITAL = "wiprodigital";
	static String filePath = null;
	
	//Default constructor
	public CrawlerForWipro() {

	}
	
	// Main method to kick start the program
	public static void main(String[] args) throws IOException{
		processedUrls = new ArrayList<>();
		siteTree = new LinkedHashMap<String, List<String>>();
		System.out.println("==================Welcome to Wipro Digital Site Map====================");
		// Scanner to accept user input
		Scanner scanner = new Scanner(System.in);
			
			
					System.out.println("Please enter the path where you want to store the output HTML file");
					filePath = scanner.nextLine().concat("\\");
	
			
			System.out.println("We are processing your request....Please wait."
					+ " Note that we do not hit any external websites detected. "
					+ "We just display the link details. Once the output file name is displayed,"
					+ " please open the output html file in a browser");
			//Do web crawl
	 		processPage(null,"http://wiprodigital.com/", true);
	 		
	 		//Print results
	 		printSiteMap();
 	}
 
	/**
	 * Method to do the web crawling and store the url and and its parent url in a 
	 * linked hash map to display in a tabular format
	 * 
	 * @param parentURL
	 * @param URL
	 * @param readPage
	 */
 	public static void processPage(String parentURL, String URL, boolean readPage){
 		//Check if the url is already processed so that we dont go to an infinite loop by 
 		//hitting the home page of the domain
		if(!processedUrls.contains(URL)){
			processedUrls.add(URL);
			addUrlToSiteTree(parentURL, URL);
			//Read the link/page only if it is internal url to the domain
			if(readPage){
		 		Document doc = null;
				try {
					//Reading page source
					doc = Jsoup.connect(URL).get();
				} catch (IOException e) {
					//Not doing anything as we do not want to crash the program as few websites might be not reachable
				}
				//If the page source is not null
				if(doc != null){
					//Get all links of href
					Elements links = doc.select("a[href]");
					for(Element link: links){
						//Check if the link is internal to domain
						if(link.attr("href").contains("://"+WIPRO_DIGITAL))
							//If yes process that link by reading that page
							processPage(URL, link.attr("abs:href"), true);
						else if(link.attr("href").contains(WIPRO_DIGITAL)){
							//If not just print the url and dont read that page(ex: facebook,twitter)
							processPage(URL, link.attr("abs:href"), false);
						}
					}
				}
			}
		}
	}
	
 	/**
 	 * Method to print the site map in a tabular format
 	 * @throws IOException 
 	 * 
 	 */
 	private static void printSiteMap() throws IOException {
 		
 		
 		BufferedWriter htmlOutputWriter = null;
 			try {
 	      // Opening the file writer streams with append = true
 	      htmlOutputWriter = new BufferedWriter(new FileWriter(filePath+"output.html", false));
 		  htmlOutputWriter.newLine();
 	      // Writing to the file
 				htmlOutputWriter.append("<!DOCTYPE html><html><body>");
 				for(String parentUrlToPrint : siteTree.keySet()){
 					htmlOutputWriter.append("<table style=\"width:100%\" border=\"1\"><tr><th>");
 					htmlOutputWriter.append(parentUrlToPrint);
 					for(String site : siteTree.get(parentUrlToPrint)){
 						htmlOutputWriter.append("<td>"+site+"</td>");
 					}
 					htmlOutputWriter.append("</td></tr></table>");
 				}
 				htmlOutputWriter.append("</body></html>");
 				System.out.println("Processing completed.. Please open the file "+filePath+"output.html");
 			} catch (IOException e) {
 				throw new IOException("Sorry!! Technical error while writing to the html file");
 	    }
 	    finally {
 	      try {
 	    	  if(htmlOutputWriter != null){
 	    		  htmlOutputWriter.close();
 	    	  }
 	      }
 	      catch (IOException e) {
 	    	  throw new IOException("Sorry!! Technical error while writing to the html file");
 	      }
 			}
 		
	}
 	
	
	/**
	 * Add the page url against its parent url into a linked has map
	 * 
	 * @param parentURL
	 * @param URL
	 */
	private static void addUrlToSiteTree(String parentURL, String URL){
		
		if(parentURL != null){
			if(!siteTree.containsKey(parentURL)){
				List<String> sites = new ArrayList<String>();
				siteTree.put(parentURL, sites);
			}
			siteTree.get(parentURL).add(URL);
		}
	}
	
}