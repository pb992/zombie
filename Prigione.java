package it.zombie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Prigione {


	Stanza stanza;
	
	public static List<Stanza> stanzeDellaPrigione = new ArrayList<>(Arrays.asList(
		  	new Stanza(0, NomiStanze.CELLA),
		  	new Stanza(1, NomiStanze.CORRIDOIO),
		  	new Stanza(2, NomiStanze.MENSA),
		  	new Stanza(3, NomiStanze.ARMERIA),
		  	new Stanza(4, NomiStanze.INFERMERIA),
		  	new Stanza(5, NomiStanze.CORTILE)));

	
}
