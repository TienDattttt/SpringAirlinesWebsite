package com.example.SwipeFlight.server.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CastUtils {

	public static <T> List<T> castList(Class<? extends T> desiredClass, Collection<?> rawCollection)
			throws IllegalArgumentException {
		List<T> result = new ArrayList<>(rawCollection.size());
		for (Object o : rawCollection) {
			try {
				result.add(desiredClass.cast(o));
			} catch (ClassCastException e) {
				System.err.println("\n*** Error ***\nClass: CastUtils\t Method: castList "
						+ "\nDetails: " + e.getMessage());
				throw new IllegalArgumentException("Casting failure.", e);
			}
		}
		return result;
	}

}
