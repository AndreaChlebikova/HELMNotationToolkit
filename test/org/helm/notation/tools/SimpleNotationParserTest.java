package org.helm.notation.tools;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.helm.notation.MonomerException;
import org.helm.notation.MonomerFactory;
import org.helm.notation.MonomerStore;
import org.helm.notation.NotationException;
import org.helm.notation.NucleotideFactory;
import org.helm.notation.StructureException;
import org.helm.notation.model.MoleculeInfo;
import org.helm.notation.model.Monomer;
import org.helm.notation.model.Nucleotide;
import org.jdom.JDOMException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import chemaxon.marvin.plugin.PluginException;

public class SimpleNotationParserTest {

	
	public String getSimpleRNANotation(){		
		return "P.R(A)[sP].RP.R(G)P.[LR]([5meC])";
	}
	
	
	public String getInlineSmilesRNANotation(){		
		//Smiles is P
		return "[OP([*])([*])=O |$;;_R1;_R2;$|].R(A)[sP].RP.R(G)[OP([*])([*])=O |$;;_R1;_R2;$|].[LR]([5meC])";
	}
	
	
	
	public String getSimplePeptideNotation(){		
		return "G.G.K.A.A.[seC]";
	}
	
	
	public String getInlineSmilesPeptideNotation(){		
	//Smiles is A
		return "G.G.K.A.[C[C@H](N[*])C([*])=O |$;;;_R1;;_R2;$|].[seC]";
	}
	
	public String getSimpleChemNotation(){
		return "PEG2";		
	}
	
	public String getSmilesNotation(){
		return "[*]OCCOCCOCCO[*] |$_R1;;;;;;;;;;;_R3$|";		
		
	}
	
	
	
	
	
	@Before
	public  void init() {
		try {
			MonomerFactory.finalizeMonomerCache();
			MonomerFactory.getInstance();
			NucleotideFactory.getInstance();	
			SimpleNotationParser.resetSeed();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@After
	public  void finish() {
		MonomerFactory.finalizeMonomerCache();
	}


	
	
	@Test
	public void testMonomerCount() throws NotationException {
		
		    
        int count=SimpleNotationParser.getMonomerCount(getSimpleRNANotation(), Monomer.NUCLIEC_ACID_POLYMER_TYPE);
        assertEquals(11,count);
        
        count=SimpleNotationParser.getMonomerCount(getInlineSmilesRNANotation(), Monomer.NUCLIEC_ACID_POLYMER_TYPE);
        assertEquals(11,count);
        
        
        count=SimpleNotationParser.getMonomerCount(getSimplePeptideNotation(), Monomer.PEPTIDE_POLYMER_TYPE);
        assertEquals(6,count);
        
        count=SimpleNotationParser.getMonomerCount(getInlineSmilesPeptideNotation(), Monomer.PEPTIDE_POLYMER_TYPE);
        assertEquals(6,count);
        
        
        count=SimpleNotationParser.getMonomerCount(getSimpleChemNotation(), Monomer.CHEMICAL_POLYMER_TYPE);
        assertEquals(1,count);
        
        
        count=SimpleNotationParser.getMonomerCount(getSmilesNotation(), Monomer.CHEMICAL_POLYMER_TYPE);
        assertEquals(1,count);
        
        
	    
	}
	@Test
	public void testCanonicalNotation() throws NotationException, MonomerException, StructureException, JDOMException, IOException {
		
		String canonicalNotation=SimpleNotationParser.getSimpleCanonicalNotation(getSimpleRNANotation(), Monomer.NUCLIEC_ACID_POLYMER_TYPE);
        assertEquals("R(A)[sP].RP.R(G)P.[LR]([5meC])P",canonicalNotation);
        Map.Entry entry = SimpleNotationParser.getSimpleCanonicalNotationMapEntry(getSimpleRNANotation(), Monomer.NUCLIEC_ACID_POLYMER_TYPE);
        assertEquals("1",entry.getKey().toString());
        assertEquals("R(A)[sP].RP.R(G)P.[LR]([5meC])P",entry.getValue());
        
        
        canonicalNotation=SimpleNotationParser.getSimpleCanonicalNotation(getInlineSmilesRNANotation(), Monomer.NUCLIEC_ACID_POLYMER_TYPE);
        assertEquals("R(A)[sP].RP.R(G)[OP([*])([*])=O |$;;_R1;_R2;$|].[LR]([5meC])[OP([*])([*])=O |$;;_R1;_R2;$|]",canonicalNotation);
        
    	
		canonicalNotation=SimpleNotationParser.getSimpleCanonicalNotation(getSimplePeptideNotation(), Monomer.PEPTIDE_POLYMER_TYPE);
        assertEquals("A.A.[seC].G.G.K",canonicalNotation);
        entry = SimpleNotationParser.getSimpleCanonicalNotationMapEntry(getSimplePeptideNotation(), Monomer.PEPTIDE_POLYMER_TYPE);
        assertEquals("3",entry.getKey().toString());
        assertEquals("A.A.[seC].G.G.K",entry.getValue());
        
        canonicalNotation=SimpleNotationParser.getSimpleCanonicalNotation(getInlineSmilesPeptideNotation(), Monomer.PEPTIDE_POLYMER_TYPE);
        assertEquals("A.[C[C@H](N[*])C([*])=O |$;;;_R1;;_R2;$|].[seC].G.G.K", canonicalNotation);

        
        
        
        
        
        canonicalNotation=SimpleNotationParser.getSimpleCanonicalNotation(getSimpleChemNotation(), Monomer.CHEMICAL_POLYMER_TYPE);
        assertEquals("PEG2",canonicalNotation);
        entry = SimpleNotationParser.getSimpleCanonicalNotationMapEntry(getSimpleChemNotation(), Monomer.CHEMICAL_POLYMER_TYPE);
        assertEquals("0",entry.getKey().toString());
        assertEquals("PEG2",entry.getValue());

        
        canonicalNotation=SimpleNotationParser.getSimpleCanonicalNotation(getSmilesNotation(), Monomer.CHEMICAL_POLYMER_TYPE);
        assertEquals("[*]OCCOCCOCCO[*] |$_R1;;;;;;;;;;;_R3$|",canonicalNotation);
        
        entry = SimpleNotationParser.getSimpleCanonicalNotationMapEntry(getSmilesNotation(), Monomer.CHEMICAL_POLYMER_TYPE);
        //assertEquals("0",entry.getKey().toString());
        //index depends on how often SimpleNotationParser.generateNextChemMonomerID is called before. 
        //assertEquals("CM#1",entry.getValue());
        
        
        
	}
	@Test
	public void testGetComplexNotation() throws NotationException, MonomerException, StructureException, JDOMException, IOException{
		
		String complexNotation=SimpleNotationParser.getComplexNotation(getSimpleRNANotation(), Monomer.NUCLIEC_ACID_POLYMER_TYPE);
        assertEquals("RNA1{P.R(A)[sP].RP.R(G)P.[LR]([5meC])}$$$$",complexNotation);

        
        complexNotation=SimpleNotationParser.getComplexNotation(getInlineSmilesRNANotation(), Monomer.NUCLIEC_ACID_POLYMER_TYPE);
        assertEquals("RNA1{[OP([*])([*])=O |$;;_R1;_R2;$|].R(A)[sP].RP.R(G)[OP([*])([*])=O |$;;_R1;_R2;$|].[LR]([5meC])}$$$$",complexNotation);
        
		
        complexNotation=SimpleNotationParser.getComplexNotation(getSimplePeptideNotation(), Monomer.PEPTIDE_POLYMER_TYPE);
        assertEquals("PEPTIDE1{G.G.K.A.A.[seC]}$$$$",complexNotation);
        
        
        complexNotation=SimpleNotationParser.getComplexNotation(getInlineSmilesPeptideNotation(), Monomer.PEPTIDE_POLYMER_TYPE);
        assertEquals("PEPTIDE1{G.G.K.A.[C[C@H](N[*])C([*])=O |$;;;_R1;;_R2;$|].[seC]}$$$$",complexNotation);        
        
        
        
        complexNotation=SimpleNotationParser.getComplexNotation(getSimpleChemNotation(), Monomer.CHEMICAL_POLYMER_TYPE);
        assertEquals("CHEM1{PEG2}$$$$",complexNotation);
        
        
        complexNotation=SimpleNotationParser.getComplexNotation(getSmilesNotation(), Monomer.CHEMICAL_POLYMER_TYPE);
        assertEquals("CHEM1{[*]OCCOCCOCCO[*] |$_R1;;;;;;;;;;;_R3$|}$$$$",complexNotation);
        
        
	}

	@Test
	public void testReplaceMonomer() throws MonomerException, IOException, JDOMException, NotationException{
		
		String result=SimpleNotationParser.replaceMonomer(getSimpleRNANotation(), Monomer.NUCLIEC_ACID_POLYMER_TYPE, "P", "sP");
		assertEquals("[sP].R(A)[sP].R[sP].R(G)[sP].[LR]([5meC])",result);
		
		result=SimpleNotationParser.replaceMonomer(getSimplePeptideNotation(), Monomer.PEPTIDE_POLYMER_TYPE,"A", "Q");
		assertEquals("G.G.K.Q.Q.[seC]",result);
		
		result=SimpleNotationParser.replaceMonomer(getSimpleChemNotation(), Monomer.CHEMICAL_POLYMER_TYPE, "PEG2", "SS3");
		assertEquals("SS3",result);			
		
	}
	
	@Test
	public void testGetSmiles() throws IOException, NotationException, StructureException, MonomerException, JDOMException{
		
		String result=SimpleNotationParser.getSimplePolymerSMILES(getSimpleRNANotation(), Monomer.NUCLIEC_ACID_POLYMER_TYPE);
		assertEquals("Cc1cn([C@H]2O[C@@]3(COP(O)(=O)O[C@@H]4[C@@H](COP(O)(=O)O[C@@H]5[C@@H](COP(S)(=O)O[C@@H]6[C@@H](COP(O)([*])=O)O[C@@H]([C@@H]6O)n6cnc7c(N)ncnc67)O[C@@H]([*])[C@@H]5O)O[C@@H]([C@@H]4O)n4cnc5c4nc(N)[nH]c5=O)CO[C@@H]2[C@@H]3O[*])c(=O)nc1N |r,$;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;_R1;;;;;;;;;;;;;;;;;;_R3;;;;;;;;;;;;;;;;;;;;;;;_R2;;;;;$|",result);
		
		result=SimpleNotationParser.getSimplePolymerSMILES(getInlineSmilesRNANotation(), Monomer.NUCLIEC_ACID_POLYMER_TYPE);
		assertEquals("Cc1cn([C@H]2O[C@@]3(COP(O)(=O)O[C@@H]4[C@@H](COP(O)(=O)O[C@@H]5[C@@H](COP(S)(=O)O[C@@H]6[C@@H](COP(O)([*])=O)O[C@@H]([C@@H]6O)n6cnc7c(N)ncnc67)O[C@@H]([*])[C@@H]5O)O[C@@H]([C@@H]4O)n4cnc5c4nc(N)[nH]c5=O)CO[C@@H]2[C@@H]3O[*])c(=O)nc1N |r,$;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;_R1;;;;;;;;;;;;;;;;;;_R3;;;;;;;;;;;;;;;;;;;;;;;_R2;;;;;$|",result);
		
		
		result=SimpleNotationParser.getSimplePolymerSMILES(getSimplePeptideNotation(), Monomer.PEPTIDE_POLYMER_TYPE);
		assertEquals("C[C@H](NC(=O)[C@H](C)NC(=O)[C@H](CCCCN[*])NC(=O)CNC(=O)CN[*])C(=O)N[C@@H](C[SeH])C([*])=O |r,$;;;;;;;;;;;;;;;;_R3;;;;;;;;;;_R1;;;;;;;;_R2;$|",result);
		
		result=SimpleNotationParser.getSimplePolymerSMILES(getInlineSmilesPeptideNotation(), Monomer.PEPTIDE_POLYMER_TYPE);
		assertEquals("C[C@H](NC(=O)[C@H](C)NC(=O)[C@H](CCCCN[*])NC(=O)CNC(=O)CN[*])C(=O)N[C@@H](C[SeH])C([*])=O |r,$;;;;;;;;;;;;;;;;_R3;;;;;;;;;;_R1;;;;;;;;_R2;$|",result);
		
		
		result=SimpleNotationParser.getSimplePolymerSMILES(getSimpleChemNotation(), Monomer.CHEMICAL_POLYMER_TYPE);
		assertEquals("[*]OCCOCCO[*] |$_R1;;;;;;;;_R2$|",result);
		
		result=SimpleNotationParser.getSimplePolymerSMILES(getSmilesNotation(), Monomer.CHEMICAL_POLYMER_TYPE);
		assertEquals(getSmilesNotation(),result);
		
	}
	@Test
	public void testValidation() throws IOException, NotationException, MonomerException, StructureException, JDOMException{
		
		assertTrue(SimpleNotationParser.validateSimpleNotation(getSimpleRNANotation(), Monomer.NUCLIEC_ACID_POLYMER_TYPE));
		
		assertTrue(SimpleNotationParser.validateSimpleNotation(getInlineSmilesRNANotation(), Monomer.NUCLIEC_ACID_POLYMER_TYPE));
		
		assertTrue(SimpleNotationParser.validateSimpleNotation(getSimplePeptideNotation(), Monomer.PEPTIDE_POLYMER_TYPE));
		
		assertTrue(SimpleNotationParser.validateSimpleNotation(getInlineSmilesPeptideNotation(), Monomer.PEPTIDE_POLYMER_TYPE));
		
		assertTrue(SimpleNotationParser.validateSimpleNotation(getSimpleChemNotation(), Monomer.CHEMICAL_POLYMER_TYPE));
		
		assertTrue(SimpleNotationParser.validateSimpleNotation(getSmilesNotation(), Monomer.CHEMICAL_POLYMER_TYPE));		
		
	}
	
	@Test
	public void testGetMoleculeInfo() throws NotationException, MonomerException, IOException, JDOMException, PluginException, StructureException{
				
        MoleculeInfo mi=SimpleNotationParser.getMoleculeInfo(getSimpleRNANotation(), Monomer.NUCLIEC_ACID_POLYMER_TYPE);
        assertEquals(1251.805,mi.getMolecularWeight(),1e-15);
        assertEquals("C36H49N13O27P4S1",mi.getMolecularFormula());
        assertEquals(1251.153200165,mi.getExactMass(),1e-15);
        
        mi=SimpleNotationParser.getMoleculeInfo(getInlineSmilesRNANotation(), Monomer.NUCLIEC_ACID_POLYMER_TYPE);
        assertEquals(1251.805,mi.getMolecularWeight(),1e-15);
        assertEquals("C36H49N13O27P4S1",mi.getMolecularFormula());
        assertEquals(1251.153200165,mi.getExactMass(),1e-15);
        
        
        mi=SimpleNotationParser.getMoleculeInfo(getSimplePeptideNotation(), Monomer.PEPTIDE_POLYMER_TYPE);
        assertEquals(552.48,mi.getMolecularWeight(),1e-15);
        assertEquals("C19H35N7O7Se1",mi.getMolecularFormula());
        assertEquals(553.176318337,mi.getExactMass(),1e-15);
        
        mi=SimpleNotationParser.getMoleculeInfo(getInlineSmilesPeptideNotation(), Monomer.PEPTIDE_POLYMER_TYPE);
        assertEquals(552.48,mi.getMolecularWeight(),1e-15);
        assertEquals("C19H35N7O7Se1",mi.getMolecularFormula());
        assertEquals(553.176318337,mi.getExactMass(),1e-15);
        
        
        mi=SimpleNotationParser.getMoleculeInfo(getSimpleChemNotation(), Monomer.CHEMICAL_POLYMER_TYPE);
        assertEquals(106.1204,mi.getMolecularWeight(),1e-15);
        assertEquals("C4H10O3",mi.getMolecularFormula());
        assertEquals(106.062994186,mi.getExactMass(),1e-15);
        
        
        mi=SimpleNotationParser.getMoleculeInfo(getSmilesNotation(), Monomer.CHEMICAL_POLYMER_TYPE);
        assertEquals(149.16506,mi.getMolecularWeight(),1e-15);
        assertEquals("C6H13O4",mi.getMolecularFormula());
        assertEquals(149.081383904,mi.getExactMass(),1e-15);
        
        
	}
	
	
	
	
	@Test
	public void testGetNucleotideList() throws NotationException, MonomerException, IOException, JDOMException, StructureException{
      String notation = "R(C)P.R(G)P.R(A)P.R(U)P.R(A)P.R(U)P.R(G)P.R(G)P.R(G)P.R(C)P.R(U)P.R(G)P.R(A)P.R(A)P.R(U)P.R(A)P.R(C)P.R(A)P.R(A)P.[dR](U)P.[dR](U)";
      
      System.out.println("getNucleotideList Start: " + System.currentTimeMillis());
      List<Nucleotide> nucleotideList = SimpleNotationParser.getNucleotideList(notation);      
      System.out.println("getNucleotideList End: " + System.currentTimeMillis());

      System.out.println("getNucleotideList wo validation Start : " + System.currentTimeMillis());
      nucleotideList = SimpleNotationParser.getNucleotideList(notation, false);
      System.out.println("getNucleotideList wo validation End: " + System.currentTimeMillis());

      assertEquals(21,nucleotideList.size());
      
      StringBuffer outputList = new StringBuffer();
      for (int i = 0; i < nucleotideList.size(); i++) {
          Nucleotide nuc = nucleotideList.get(i);
          System.out.println("Symbol: " + nuc.getSymbol());
          System.out.println("Modified: " + nuc.isModified());
          System.out.println("Notation: " + nuc.getNotation());
          outputList.append(nuc.getSymbol());
      }
      
      
      assertEquals("CGAUAUGGGCUGAAUACAAdUdU",outputList.toString());
      
            

      notation = "R(A)[sP].R(G)P.R(C).PEG.[LR]([5meC])[sP].R(PEG)";
           
      
      
      nucleotideList = SimpleNotationParser.getStrictNucleotideList(notation, false);
      outputList = new StringBuffer();
      for (int i = 0; i < nucleotideList.size(); i++) {
          Nucleotide nuc = nucleotideList.get(i);
          System.out.println("Symbol: " + nuc.getSymbol());
          System.out.println("Modified: " + nuc.isModified());
          System.out.println("Notation: " + nuc.getNotation());
          outputList.append(nuc.getSymbol());
      }
      assertEquals(6,nucleotideList.size());
      
      assertEquals("modAGmodCXmodCendX",outputList.toString());
		
	}
	
	
	
	@Test
	public void testGetNucleotideSequence() throws NotationException, MonomerException, IOException, JDOMException, StructureException{
		
		String notation = "R(C)P.R(G)P.R(A)P.R(U)P.R(A)P.R(U)P.R(G)P.R(G)P.R(G)P.R(C)P.R(U)P.R(G)P.R(A)P.R(A)P.R(U)P.R(A)P.R(C)P.R(A)P.R(A)P.[dR](U)P.[dR](U)";
		String nucleotideSeq =SimpleNotationParser.getNucleotideSequence(notation);
	      
	    assertEquals("CGAUAUGGGCUGAAUACAAUU",nucleotideSeq);
	    
	    //replaced a C with 5meC (modified C)
	    notation = "R(C)P.R(G)P.R(A)P.R(U)P.R(A)P.R(U)P.R(G)P.R(G)P.R(G)P.R([5meC])P.R(U)P.R(G)P.R(A)P.R(A)P.R(U)P.R(A)P.R(C)P.R(A)P.R(A)P.[dR](U)P.[dR](U)";
		nucleotideSeq =SimpleNotationParser.getNucleotideSequence(notation);
	      
	    assertEquals("CGAUAUGGGCUGAAUACAAUU",nucleotideSeq);
	    
	    
	    //replace a C with Inline Smiles 
	    //TODO not working yet
	    /*notation = "R([Cc1cn([*])c(=O)nc1N |$;;;;_R1;;;;;$|])P.R(G)P.R(A)P.R(U)P.R(A)P.R(U)P.R(G)P.R(G)P.R(G)P.R([5meC])P.R(U)P.R(G)P.R(A)P.R(A)P.R(U)P.R(A)P.R(C)P.R(A)P.R(A)P.[dR](U)P.[dR](U)";
		nucleotideSeq =SimpleNotationParser.getNucleotideSequence(notation);
	    
		assertEquals("CGAUAUGGGCUGAAUACAAUU",nucleotideSeq);*/
	    

	}
	
	
	
	@Test
	public void testGetPeptideSequence() throws NotationException,
			MonomerException, IOException, JDOMException, StructureException {
		
	
		
		String notation = "K.C.C.C.W.K.[seC]";
		
		assertEquals("KCCCWKC",
				SimpleNotationParser.getPeptideSequence(notation));
		
		assertEquals("K|C|C|C|W|K|seC",
				SimpleNotationParser.getModifiedPeptideSequence(notation, "|"));
	
		//Smiles is Alanine 
		String peptide="G.G.K.A.[C[C@H](N[*])C([*])=O |$;;;_R1;;_R2;$|].[seC]";
		assertEquals("GGKAAC",
				SimpleNotationParser.getPeptideSequence(peptide));
	
		
		assertEquals("G|G|K|A|A|seC",
				SimpleNotationParser.getModifiedPeptideSequence(peptide,"|"));
	
		
		//Smiles is not in local db
		peptide="G.G.K.A.[C[C@H](N[*])C(=O)C[*] |$;;;_R1;;;;_R2$|].[seC]";
		assertEquals("GGKAXC",
				SimpleNotationParser.getPeptideSequence(peptide));
		
		assertEquals("G|G|K|A|AM#1|seC",
				SimpleNotationParser.getModifiedPeptideSequence(peptide,"|"));
	
		
	}
	
	@Test
	public void testReplaceSmiles() throws NotationException, MonomerException, JDOMException, IOException{
		String notation = "[C[C@H](N[*])C(=O)C[*] |$;;;_R1;;;;_R2$|].G.G.G.C.C.K.K.K.K";
		String newNotation=SimpleNotationParser.getNotationByReplacingSmiles(notation,"PEPTIDE",MonomerFactory.getInstance().getMonomerStore());
		assertEquals("[AM#1].G.G.G.C.C.K.K.K.K",newNotation);
		
		
		

	}
	
	
	
	
	
		 
	    


	    

}
