package nameInverter;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;


public class nameInverterTest {

	@Before
	public void setUp() throws Exception {
	}

	
	private String invertName(String name) {
		if(name==null || name.length()<0){
			return "";
		}
		else {
			return formatName(removeHonorifics(splitNames(name)));
		}
		
		
	}


	private String formatName(List<String> names) {
		if(names.size() == 1) {
			return names.get(0);
		}
		else {
			String formattedName = formatMultiElementName(names);
			return formattedName;
		}
	}


	private String formatMultiElementName(List<String> names) {
		String postNominal = "";
		if(names.size()>2) {
			postNominal = getPostNominals(names);
		}
		String formattedName = formatName(names, postNominal);
		return formattedName;
	}


	private String formatName(List<String> names, String postNominal) {
		return String.format("%s, %s %s", names.get(1),names.get(0), postNominal).trim();
	}


	private List<String> removeHonorifics(List<String> names) {
		if(names.size()>1&& isHonorific(names.get(0))) {
			names.remove(0);
		}
		return names;
	}


	private String getPostNominals(List<String> names) {
		List<String> postNominals;
		postNominals = names.subList(2, names.size());
		String postNominal = "";
		for(String pn : postNominals) {
			postNominal += pn+ " ";
		}
		return postNominal;
	}


	private boolean isHonorific(String word) {
		return word.matches("Mr\\.|Mrs\\.");
	}


	private ArrayList<String> splitNames(String name) {
		return new ArrayList<String>(Arrays.asList(name.trim().split("\\s+")));
	}
	
	private void assertInverted(String originalName, String invertedName) {
		assertEquals(invertedName,invertName(originalName));
	}
	
	
	@Test
	public void givenNull_returnsEmptyString() {
		assertInverted(null, "");
	}


	
	@Test
	public void givenEmptyString_returnEmptyString() {
		assertInverted("","");
		
	}
	@Test
	public void givenSimpleName() {
		assertInverted("Name","Name");
	}
	
	@Test
	public void givenFirstLast() {
		assertInverted("First Last", "Last, First");
	}
	
	@Test
	public void givenASimpleNameWithSapces_returnSimpleNameWithoutSpaces() {
		assertInverted(" Name ", "Name");
	}
	
	public void givenFirstLastWithExtraSpaces_returnLastFirst() {
		assertInverted(" First  Last ", "Last, First");
	}
	
	@Test
	public void ignoreHonorifics() {
		assertInverted("Mr. First Last", "Last, First");
		assertInverted("Mrs. First Last", "Last, First");
	}
	
	@Test
	public void psotNominal_stayAtEnd() {
		assertInverted("First Last Sr.", "Last, First Sr.");
		assertInverted("First Last BS. Phd.", "Last, First BS. Phd.");
	}
	
	@Test
	public void integration() {
		assertInverted("  Robert Martin III esq.  ","Martin, Robert III esq.");
	}

}
