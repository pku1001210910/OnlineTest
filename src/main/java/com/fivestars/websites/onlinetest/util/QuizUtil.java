package com.fivestars.websites.onlinetest.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class QuizUtil {

	public static final <T> List<T> sortByOrder(Set<T> originSet, Class<T> clazz) {
		Set<T> orderedSet = new TreeSet<>();
		for (T element : originSet) {
			orderedSet.add(element);
		}
		return new ArrayList<>(orderedSet);
	}
}
