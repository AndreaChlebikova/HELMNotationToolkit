package org.helm.notation.search;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Andrea Chlebikova
 *
 */
public class CombinedSearchTests {

	List<String> notationList = new ArrayList<String>();

	@Before
	public void init() {
		try {
			notationList = Files.readAllLines(
					Paths.get("test/org/helm/notation/search/HELMStrings.txt"),
					Charset.forName("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@After
	public void finish() {
		notationList.clear();
	}

	@Test
	public void testCombinedSearch() {
		Query q = new Query("A-SY.V", true, true);
		Matches m = CombinedSearch.performSearch(q, notationList);
		System.out.println(m.indicesOfInterest);

		q = new Query("YA", true, true);
		m = CombinedSearch.performSearch(q, notationList);
		System.out.println(m.indicesOfInterest);

		// q = new Query("SCCCOOCCCS",false,'c');
		// m = CombinedSearch.performSearch(q, notationList);
		// System.out.println(m.indicesOfInterest);

		Query q2 = new Query("C(=O)N", false, 'm');
		Matches m2 = CombinedSearch.performSearch(q2, notationList);
		System.out.println(m2.indicesOfInterest);

		q2.negation = true;
		m2 = CombinedSearch.performSearch(q2, notationList);
		System.out.println(m2.indicesOfInterest);

		Matches m3 = CombinedSearch.combineSearches(m, m2, true);
		System.out.println(m3.indicesOfInterest);

		m3 = CombinedSearch.combineSearches(m, m2, false);
		System.out.println(m3.indicesOfInterest);
		m3.printout();
	}

}
