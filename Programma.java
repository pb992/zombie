package it.zombie;
import java.util.Scanner;

import javax.swing.SwingUtilities;

import it.zombie.views.Finestra;

@SuppressWarnings("unused")
public class Programma {

	public static void main(String[] args) throws InterruptedException {
		Gioco gioco = new Gioco();
//		Giocatore.nomeUtente = "fabio";
//		Giocatore.locationPlayer++;
		SwingUtilities.invokeLater(Finestra::new);
//		Giocatore giocatore = new Giocatore();
//		Finestra finestra = new Finestra();
	}
}