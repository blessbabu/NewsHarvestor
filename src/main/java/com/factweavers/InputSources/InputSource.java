package com.factweavers.InputSources;

import java.util.List;
import java.util.Map;

import com.factweavers.Article;


public interface InputSource {
	
	public Map<String,Object> getConfig();
	public List<Article> getArticles();

}
