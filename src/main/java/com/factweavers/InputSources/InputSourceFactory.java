package com.factweavers.InputSources;

import java.util.Map;

public class InputSourceFactory {

	public static InputSource getInputSourceInstance(String type,Map<String,Object> config){
		if(type.equals("rss")){
			return new LinkInputSource(config);
		}
		return null;	
	}
	
}


