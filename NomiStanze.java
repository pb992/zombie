package it.zombie;

public enum NomiStanze {
	
	CELLA("Cella"),
	CORRIDOIO("Corridoio"),
	MENSA("Mensa"),
	ARMERIA("Armeria"),
	INFERMERIA("Infermeria"),
	CORTILE("Cortile");

	public String nomeDellaStanza; 

	NomiStanze (String nomeStanza) {
	nomeDellaStanza = nomeStanza;
	}
}
