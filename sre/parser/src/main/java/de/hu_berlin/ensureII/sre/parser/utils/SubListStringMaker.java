package de.hu_berlin.ensureII.sre.parser.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SubListStringMaker {
	private SubListStringMaker() {
	}

	private static Map<List<String>, List<List<String>>> substringCacheDistinct = new HashMap<List<String>, List<List<String>>>();
	private static Map<List<String>, List<List<String>>> substringCacheAll = new HashMap<List<String>, List<List<String>>>();

	public static List<List<String>> getAllPossibleSubstrings(List<String> string) {
		return getAllPossibleSubstrings(string, true, true);
	}

	/**
	 * Computes a {@link List} of all possible Substrings of a {@link String}.
	 * Class is caching calculated Lists internally.
	 * 
	 * @param string
	 *            The orignal {@link String}
	 * @param distinct If the list should only contain distinct Strings
	 * @return {@link List} with all substrings
	 */
	public static List<List<String>> getAllPossibleSubstrings(List<String> string,
			boolean distinct, boolean empty) {
		if (distinct) {
			if (substringCacheDistinct.containsKey(string)) {
				return substringCacheDistinct.get(string);
			}
		} else {
			if (substringCacheAll.containsKey(string)) {
				return substringCacheAll.get(string);
			}
		}

		int length = string.size();
		List<List<String>> result = new ArrayList<List<String>>();
		for (int c = 0; c < length; c++) {
			for (int i = 1; i <= length - c; i++) {
				List<String> s = substring(string, c, c+i);
				if(!distinct || !result.contains(s)){
					result.add(s);
				}
			}
		}
		if(empty){
			result.add(new LinkedList<String>());
		}
		if(distinct){
			substringCacheDistinct.put(string, result);
		} else {
			substringCacheAll.put(string, result);
		}
		return result;
	}

	public static <T> List<T> substring(List<T> string, int start, int end) {
		List<T> result = new LinkedList<T>();
		for(int i = start; i<end; i++){
			result.add(string.get(i));
		}
		return result;
	}

	public static <T> List<T> substring(List<T> string, int start) {
		List<T> result = new LinkedList<T>();
		for(int i = start; i<string.size(); i++){
			result.add(string.get(i));
		}
		return result;
	}
	
	
}
