package com.factweavers;

import org.jsoup.nodes.Element;

import de.jetwick.snacktory.OutputFormatter;

public class MyOutputFormatter extends OutputFormatter {
	
	public String getFormattedText(Element topNode) {
    	return topNode.html();
    }

}
