package de.hu_berlin.ensureII.sre.parser.utils;

import java.util.HashMap;
import java.util.Map;

public class TimeTracker {
	private static Map<String, Long> results = new HashMap<String, Long>();
	private static Map<String, Long> lastStart = new HashMap<String, Long>();
	private static String DEFAULT = "##DEFAULTTIMETRACKER##";
	
	private TimeTracker(){
		
	}
	
	public static void start(String entity){
		lastStart.put(entity, System.currentTimeMillis());
	}
	public static void start(){
		start(DEFAULT);
	}
	
	public static Long pause(String entity){
		if(!lastStart.containsKey(entity)){
			return -1L;
		}else{
			long prev = results.containsKey(entity) ? results.get(entity) : 0;
			long timeStart = lastStart.get(entity);
			long timePause = System.currentTimeMillis();
			long next = prev + timePause - timeStart;
			results.put(entity, next);
			lastStart.put(entity, timePause);
			return next;
		}
	}
	public static Long pause(){
		return pause(DEFAULT);
	}
	
	public static void reset(String entity){
		results.remove(entity);
		lastStart.remove(entity);
	}
	public static void reset(){
		reset(DEFAULT);
	}
	
	public static void resetAll(){
		results.clear();
		lastStart.clear();
	}
	
	public static String output(){
		return "TimeTracker results: "+results.toString();
	}
}
