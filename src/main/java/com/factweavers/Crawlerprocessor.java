package com.factweavers;

import java.util.Random;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.elasticsearch.client.Client;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class Crawlerprocessor extends WebCrawler {
	private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg"
			+ "|png|mp3|mp3|zip|gz))$");
	ContentExtractor extract=new ContentExtractor();
	Logger logger=Logger.getLogger(Crawlerprocessor.class.getName());

	@Override
	public boolean shouldVisit(Page referringPage, WebURL url) {
		String href = url.getURL().toLowerCase();
		return !FILTERS.matcher(href).matches();
	}
	@Override
	public void visit(Page page) {
		logger.info("Recieved page");
		String url=page.getWebURL().getURL();
        String info = page.getWebURL().getInfo();
        logger.info("visit"+url);
        logger.info("info"+info);

        JSONObject jsonInfo=new JSONObject();
        try {
			JSONObject articleInfo=new JSONObject(page.getWebURL().getInfo());
			int docId=page.getWebURL().getDocid();
			System.out.println("article-info "+articleInfo);
			for(String key : articleInfo.keySet()){
				jsonInfo.put(key, articleInfo.get(key));
			}
			if (page.getParseData() instanceof HtmlParseData) { 
				logger.info("parsing data");
				HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
				String html=htmlParseData.getHtml();
				
				String text=extract.extractContent(html);
				String rawText=text.replaceAll("<.*?>", "");
				jsonInfo.put("html", text);
				jsonInfo.put("content",rawText);
				jsonInfo.put("docid", docId);
				jsonInfo.put("topImage",extract.getImage());
				jsonInfo.put("source", "curator");
				pushToElasticSearch(jsonInfo);
				
			}
			else{
				logger.info("cannot parse data");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
	public void pushToElasticSearch(JSONObject jsonData){
		Random random=new Random();
		logger.info("entering into elasticsearch");
		int port = 9300;
		String host = "localhost";
		String cluster = "elasticsearch";
		String index="bigdata-search-01";
		String type="items";
		String title=(String) jsonData.get("title");
		String id=String.valueOf(title.hashCode());
		ElasticSearch es = new ElasticSearch();
		logger.info("calling create client");
		Client client = es.createClient(host, port, cluster);
		es.createIndex(index);
		logger.info("indexcreated");
		boolean isDocumentExist=es.isDocumentExist(index, type, id);
		logger.info("element exist"+isDocumentExist);
		if(isDocumentExist==false){
			logger.info("indexing");
			boolean indexed=es.indexDocument(index, type, jsonData, id);
			System.out.println(indexed);
			logger.info("entered into elasticsearch");
		}
	}

}
