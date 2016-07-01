package com.factweavers.InputSources;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.factweavers.Article;
import com.factweavers.DroneTagCreation;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class LinkInputSource implements InputSource{

	Map<String,Object> config;
	
	public LinkInputSource(Map<String, Object> config) {
		this.config = config;
	}

	public List<Article> getArticles() {
		// TODO Auto-generated method stub
		List<Article>articles=new ArrayList<Article>();
		String url=(String) config.get("rss");
		try {
			URL feedUrl=new URL(url);
			HttpURLConnection httpcon = (HttpURLConnection)feedUrl.openConnection();
			SyndFeedInput input=new SyndFeedInput();
	        SyndFeed feed = input.build(new XmlReader(httpcon));
	        List entries=feed.getEntries();
			Iterator itEntries = entries.iterator();
			
			while(itEntries.hasNext()){
				String link=null;
				DroneTagCreation create=new DroneTagCreation();
				SyndEntry entry=(SyndEntry) itEntries.next();
				Pattern pattern=Pattern.compile("(url=)(http.*)(&ct)");
				Matcher match=pattern.matcher(entry.getLink());
				System.out.println(entry.getLink());
				if(match.find()){
					 link=match.group(2);
				}
				else{
					link=entry.getLink();
				}
				Article article=new Article(link);
				ArrayList<String>authors=new ArrayList<String>();
				
				
					String[] authorArray=entry.getAuthor().split("(,|and)");
					for(String author:authorArray){
						authors.add(author);
					}
				
				
				article.setdate(entry.getPublishedDate());
				String title=entry.getTitle();
				article.setTitle(title.replaceAll("<.*?>",""));
				article.setAuthors(authors);
				articles.add(article);
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return articles;
	}

	public void setConfig(Map<String, Object> config) {
		String url = (String) config.get("URL");
		// TODO Auto-generated method stub
		
	}

	public Map<String, Object> getConfig() {
		// TODO Auto-generated method stub
		return null;
	}

}
