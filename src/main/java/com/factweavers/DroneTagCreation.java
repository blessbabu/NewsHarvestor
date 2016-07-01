package com.factweavers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Random;

public class DroneTagCreation {
	private int randomGenerator(int max){
		Random random=new Random();
		return(random.nextInt(max-0+1)-0);
	}
	public ArrayList<String> createTag() throws Exception{
		ArrayList<String> tags=new ArrayList<String>();
		ArrayList<String> randomList=new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader(new File("/home/weavers/git/newshunter/data/test/dronetags.txt")));
		int count=0;
		while(true){
			String tag=br.readLine();
			if(tag==null){
				break;
			}
			tags.add(tag);
			count=count+1;
		}
		int length=randomGenerator(4);
		for(int i=0;i<length;i++){
			randomList.add(tags.get(randomGenerator(count-1)));
			}
		ArrayList<String>finalTags=new ArrayList<String>(new LinkedHashSet<String>(randomList));
		if(finalTags.isEmpty()){
			finalTags.add(tags.get(randomGenerator(count-1)));
			finalTags.add(tags.get(randomGenerator(count-1)));
		}
		return(finalTags);
	}

}
