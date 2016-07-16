// outline of the code that I write for this lab

package com.flatironschool.javacs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import org.jsoup.select.Elements;

public class WikiPhilosophy {
	
	final static WikiFetcher wf = new WikiFetcher(); // creat *only one* wikifetcher
	
	/**
	 * Tests a conjecture about Wikipedia and Philosophy.
	 * 
	 * https://en.wikipedia.org/wiki/Wikipedia:Getting_to_Philosophy
	 * 
	 * 1. Clicking on the first non-parenthesized, non-italicized link
     * 2. Ignoring external links, links to the current page, or red links
     * 3. Stopping when reaching "Philosophy", a page with no links or a page
     *    that does not exist, or when a loop occurs
	 * 
	 * @param args
	 * @throws IOException
	 */

	// defines "valid" as "link test does not start with a capital letter"
	public static void main(String[] args) throws IOException {
		
        // some example code to get you started

        // GOALS
        // Takes a url for a wikipedia page, downloads it, and parses it
        // Traverse the resulting DOM tree to find the first "valid" link: 
        // ---> valid = in content text, not sidebar or boxout
        // -----------> not in italics or parens
        // -----------> skip external links, links to current page, red links
        // -----------> (in some versions) skip a link if text starts with uppercase letter
        // If a) page has no links, or b) the first link is one we've seen already, indicate failure and exit
        // If the found link matches the url for wiki page on philosophy, program should indicate success! and exit
        // Else, go back to step 1
        // ---> build a list of visited urls and display results at end (whether success or failure)

		String url = "https://en.wikipedia.org/wiki/Java_(programming_language)"; // start url
		url = "https://en.wikipedia.org/wiki/Williams_College"; // start url
		boolean stillSearching = true; // will switch to false as soon as our search either fails or succeeds
		boolean succeeded = false;
		LinkedList visited = new LinkedList();
		while (stillSearching){
			Elements paragraphs = wf.fetchWikipedia(url); // all the paragraphs in the page
			int numParagraphs = paragraphs.size();

			// iterate over every paragraph till first link is found
			boolean validLinkFound = false;
			String addressStub = "";
			for (int p = 0; p < numParagraphs && !validLinkFound; p++){
				// System.out.println("PARAGRAPH " + p);
				Element currentPara = paragraphs.get(p);
				Elements linksInParagraph = currentPara.select("a[href]");
				if (linksInParagraph.size() > 0){
					// iterate until we find a "valid" link, aka one that starts with a lowercase letter
					for (int l = 0; l < linksInParagraph.size() && !validLinkFound; l++){
						if (!Character.isUpperCase(linksInParagraph.get(l).text().charAt(0))){ // check if link text starts lowercase
							addressStub = linksInParagraph.get(l).attr("href"); // update to reflect that we found
							validLinkFound = true;
							// handle significance of finding link. Are we done? Did we hit a cycle?
							if (addressStub.equals("/wiki/Philosophy")){ 
								stillSearching = false; // no more searching
								succeeded = true; // we succeeded
							} // if we found philosophy, we're done
							if (visited.contains(addressStub)){
								stillSearching = false; // if we hit a cycle, we failed.
							}
							visited.add(addressStub);
						} // end if checking for link validity
					} // end for that finds a valid link, if one exists
				} // end if that checks if paragraph has any links in it
			} // end for that traverses each paragraph in page
			if (addressStub.length() > 0 && stillSearching){
				url = "https://en.wikipedia.org" + addressStub; // if we found a new address to pursue, keep searching
			}
			else{
				stillSearching = false; // if we didn't find any valid links, search over, we failed
			}
		} // end main loop that searched for philosophy
		System.out.println(visited);
		System.out.println(visited.size());

		// Element firstPara = paragraphs.get(0);
		// Element secondPara = paragraphs.get(1);
		
		

		// Iterable<Node> iter = new WikiNodeIterable(firstPara);
		// for (Node node: iter) {
		// 	//if (node instanceof TextNode) {
		// 	//	System.out.println("is text " + node);
		// 	//}
		// 	//else{
		// 		if (node instanceof Element){
		// 			System.out.println("element " + node);
		// 		}
		// 	//}
  //       }

  //       System.out.println("STARTING SECOND PARAGRAPH NOW");
  //       Iterable<Node> iter2 = new WikiNodeIterable(secondPara);
  //       for (Node node: iter2){
  //       	if (node instanceof Element){
  //       		System.out.println("element " + node);
  //       	}
  //       }

        // the following throws an exception so the test fails
        // until you update the code
        String msg = "Complete this lab by adding your code and removing this statement.";
        // well I did fix the code
	}
}
