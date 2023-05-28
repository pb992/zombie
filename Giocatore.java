package it.zombie;

import java.util.ArrayList;
import java.util.List;

public class Giocatore {

	public static int pv;
	public static String nomeUtente;
	public static Arma arma;
	public static ArmaDaFuoco armaDaFuoco;
	public static int locationPlayer;
	public static KitMedico kitMedico;

	public static List<KitMedico> listaKitMedici = new ArrayList<>();

	Giocatore () {
		pv = 100;
		locationPlayer = 0;
		arma = new Arma(3);
		armaDaFuoco = new ArmaDaFuoco(0);
	}
	
	public static void stampaPv() {
        System.out.println("PV " + nomeUtente + " : " + pv);
    }
	
	public void getArmaDaFuoco() {
		
	}
}
