package com.factweavers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class Article {

	String link;
	ArrayList<String> authors;
	String title;
	Date date;
	
	


	

	public Article(String link) {
		super();
		this.link = link;
	}
	
	public Article(JSONObject json) {
		super();
		this.link = link;
	}


	public ArrayList<String> getAuthors() {
		return authors;
	}

	public void setAuthors(ArrayList<String> authors) {
		this.authors = authors;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getdate() {
		return date;
	}

	public void setdate(Date date) {
		this.date = date;
	}

	public String getlink() {
		return link;
	}
	
	@Override
	public String toString(){
		JSONObject jsonData=new JSONObject();
		try {
			jsonData.put("url",link);
			jsonData.put("authors", authors);
			jsonData.put("title", title);
			jsonData.put("publisheddate", date);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonData.toString();
	}

	
	
	
	
}
