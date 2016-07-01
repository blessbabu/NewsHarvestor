package com.factweavers;

import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class NewsCrawler {
	CrawlController controller = null;
	String crawlStorageFolder = "/root/crawler";
	Logger logger = Logger.getLogger(NewsCrawler.class.getName());

	public NewsCrawler() {
		CrawlConfig config = new CrawlConfig();
		config.setCrawlStorageFolder(crawlStorageFolder);
		config.setMaxConnectionsPerHost(1);
		config.setMaxDepthOfCrawling(1);
		config.setResumableCrawling(true);
		config.setFollowRedirects(true);
		config.setMaxDownloadSize(1);
		config.setPolitenessDelay(1000);
		
		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig,pageFetcher);
		try {
			logger.info("Starting cralwer");
			controller = new CrawlController(config, pageFetcher, robotstxtServer);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void submitArtciles(List<Article> articles) {
		for (Article article : articles) {
			try {
				
				controller.addSeed(article.getlink(),-1,article.toString());
				logger.info("article"+article.toString());
				logger.info("Adding URL " + article.getlink());
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		logger.info("start crawlling");
		controller.start(Crawlerprocessor.class, 50);
	}

}
