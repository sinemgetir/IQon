package de.hu_berlin.ensureII.sre.parser.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubStringMaker {
	private SubStringMaker() {
	}

	private static Map<String, List<String>> substringCacheDistinct = new HashMap<String, List<String>>();
	private static Map<String, List<String>> substringCacheAll = new HashMap<String, List<String>>();

	public static List<String> getAllPossibleSubstrings(String string) {
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
	public static List<String> getAllPossibleSubstrings(String string,
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

		int length = string.length();
		List<String> result = new ArrayList<String>();
		for (int c = 0; c < length; c++) {
			for (int i = 1; i <= length - c; i++) {
				String s = string.substring(c, c + i);
				if(!distinct || !result.contains(s)){
					result.add(s);
				}
			}
		}
		if(empty){
			result.add("");
		}
		if(distinct){
			substringCacheDistinct.put(string, result);
		} else {
			substringCacheAll.put(string, result);
		}
		return result;
	}
}
