package com.factweavers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;

import com.factweavers.InputSources.InputSource;
import com.factweavers.InputSources.InputSourceFactory;

public class Application {

	
	public static void main(String args[]) throws Exception{
		Logger logger = Logger.getLogger(Application.class.getName());
		Options options = new Options();
		options.addOption("url", true, "Fetch news from this URL and push to Elasticsearch");
		options.addOption("rss", true, "Find all news from this RSS and push to Elasticsearch");
		options.addOption("rssfile",true, "find all news from rss contained in the file");
		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = null;
		try {
			cmd = parser.parse( options, args);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}
		// Validating the command line arguments received
		Map<String,Object> config = new HashMap<String,Object>();

		if(cmd.hasOption("rss")){
			config.put("rss", cmd.getParsedOptionValue("rss"));

			InputSource source = InputSourceFactory.getInputSourceInstance("rss", config);
			
			List<Article> articles = source.getArticles();
			
			NewsCrawler crawler = new NewsCrawler();
			crawler.submitArtciles(articles);
			
		}
		else if(cmd.hasOption("rssfile")){
			String filePath=cmd.getOptionValue("rssfile");
			BufferedReader br=new BufferedReader(new FileReader(new File(filePath)));
			logger.info("file path-"+filePath);
			while(true){
				String rsslink=br.readLine();
				if(rsslink==null){
					break;
				}
				config.put("rss", rsslink);
				InputSource source = InputSourceFactory.getInputSourceInstance("rss", config);
				List<Article> articles = source.getArticles();
				NewsCrawler crawler = new NewsCrawler();
				crawler.submitArtciles(articles);
			}
		}
		else{
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp( "NewsHarvestor", options );
		}
		

		
	

	}
}
