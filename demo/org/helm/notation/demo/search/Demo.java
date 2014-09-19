package org.helm.notation.demo.search;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.helm.notation.search.Constants.Connector;
import org.helm.notation.search.Constants.SearchType;
import org.helm.notation.search.Constants.SequenceType;
import org.helm.notation.search.Constants.SmilesLevel;
import org.helm.notation.search.CombinedSearch;
import org.helm.notation.search.Grouping;
import org.helm.notation.search.Matches;
import org.helm.notation.search.Query;
import org.helm.notation.search.SequenceQuery;
import org.helm.notation.search.StructureQuery;

/**
 * Class to illustrate manual creation of query objects, grouping and passing these to search functions.
 * 
 * @author Andrea Chlebikova
 *
 */
public class Demo {

	static Grouping grouping;
	static List<Object> queryList = new ArrayList<Object>();
	static List<String> notationList;
	static Matches matches;
	static Query query;
	
	public static void main(String[] args) {

		try {
			notationList = Files.readAllLines(
					Paths.get("test/org/helm/notation/search/HELMStrings.txt"),
					Charset.forName("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		query = new SequenceQuery("A-C", SequenceType.PEPTIDE, SearchType.EXACT);
		queryList.add(query);
		query = new SequenceQuery("G", SequenceType.NUCLEOTIDE, SearchType.EXACT); 
		queryList.add(query);
		query = new StructureQuery("C(=O)CC", SmilesLevel.PEPTIDE, SearchType.EXACT);
		queryList.add(query);
		
		grouping = new Grouping(Connector.AND, queryList);
		matches = CombinedSearch.performSearch(grouping, notationList);
		
		System.out.println(matches.indicesOfInterest);
		
		query = new StructureQuery("S", SmilesLevel.CHEM, SearchType.EXACT);
		queryList.add(query);
		grouping = new Grouping(Connector.OR, queryList);
		matches = CombinedSearch.performSearch(grouping, notationList);
		
		System.out.println(matches.indicesOfInterest);
	}	
}
