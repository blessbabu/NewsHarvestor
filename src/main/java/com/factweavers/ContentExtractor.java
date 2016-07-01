package com.factweavers;

import com.factweavers.extractor.ContentSelector;
import com.factweavers.extractor.MyOutputFormatter;

import de.jetwick.snacktory.ArticleTextExtractor;
import de.jetwick.snacktory.HtmlFetcher;
import de.jetwick.snacktory.JResult;
import de.jetwick.snacktory.OutputFormatter;

public class ContentExtractor {
	HtmlFetcher fecther=new HtmlFetcher();
	 OutputFormatter formatter = new MyOutputFormatter();
	 ArticleTextExtractor extractor = new ArticleTextExtractor();
	 ContentSelector select=new ContentSelector();
	 String image;
	 public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String extractContent(String html) throws Exception{
		 extractor.setOutputFormatter(formatter);
		 fecther.setExtractor(extractor);
		 JResult result=extractor.extractContent(html);
		 String textContent=select.selectContent(html, result.getText());
		 setImage(result.getImageUrl());
		 return textContent;
	 }

}
