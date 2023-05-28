package it.zombie;

public class Arma {

		public int danno;
		public String nomeArma;

		public Arma(int tipo) {
			setTipo(tipo);
		}

		public void setTipo(int tipo) {
			switch(tipo) {
			case 1://pugni
				nomeArma = "Pugni";
				danno = 20;
				break;
			case 2://cacciavito
				nomeArma = "Cacciavite";
				danno = 30;
				break;
			case 3://coltello
				nomeArma = "Coltello";
				danno = 50;
			}
		}
	}