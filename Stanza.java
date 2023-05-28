package it.zombie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Stanza {
	public Zombie zombie;
	public Arma arma;
	public ArmaDaFuoco armaDaFuoco;
	public KitMedico kitmedico;

	public NomiStanze nomiStanze;

	public List<Zombie> zombieDellaStanza;

	public Stanza(int stanza, NomiStanze nomestanza) {
		switch(stanza) {
		case 1: //corridoio
			this.nomiStanze = nomestanza;
			zombieDellaStanza = new ArrayList<>();
			zombieDellaStanza.addAll(Arrays.asList(new Zombie(1), new Zombie(1) ) );
			break;
		case 2: //mensa
			this.nomiStanze = nomestanza;
			zombieDellaStanza = new ArrayList<>();
			zombieDellaStanza.addAll(Arrays.asList(new Zombie(2), new Zombie(2) ) );
			arma = new Arma(3);
			break;
		case 3: //armeria
			this.nomiStanze = nomestanza;
			zombieDellaStanza = new ArrayList<>();
			zombieDellaStanza.addAll(Arrays.asList(new Zombie(2), new Zombie(2) ) );
			armaDaFuoco = new ArmaDaFuoco();
			break;
		case 4: //infermeria
			this.nomiStanze = nomestanza;
			zombieDellaStanza = new ArrayList<>();
			zombieDellaStanza.addAll(Arrays.asList(new Zombie(2), new Zombie(2) ) );
			kitmedico = new KitMedico(3);
			break;
		case 5://cortile (boss)
			this.nomiStanze = nomestanza;
			zombieDellaStanza = new ArrayList<>();
			zombieDellaStanza.add(new Zombie(4));
			break;
		default: //cella
			this.nomiStanze = nomestanza;
			zombieDellaStanza = new ArrayList<>();
			arma = new Arma(2);
			break;
		}
	}

}
