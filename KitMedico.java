package it.zombie;

public class KitMedico {

	public int recuperoPv;
	public String nomeKit;

	public KitMedico(int tipo) {
		switch(tipo) {
		case 1: //piccolo
			recuperoPv = 25;
			nomeKit = "Piccolo";
			break;
		case 2: //medio
			recuperoPv = 50;
			nomeKit = "Medio";
			break;
		case 3: //grande
			recuperoPv = 100;
			nomeKit = "Grande";
			break;
		default:
			nomeKit = "Piccolo";
			recuperoPv = 25;
			break;
		}
	}
}
