package it.zombie;

import java.util.InputMismatchException; 
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Gioco {
	//valore dei dadi
	public static int dado1 = new Random().nextInt(1, 7);
	public static int dado2 = new Random().nextInt(1, 7);
	
	public Giocatore player;
	public Zombie zombie;
	public Prigione prigione;
	public KitMedico kitMedico;

	public static Random random = new Random();

	public Gioco() {
		prigione = new Prigione();
		player = new Giocatore();	
//		try {
//			titolo();
//		} catch (InterruptedException e) { 
//			e.printStackTrace();
//		}
	}

	
	public void titolo () throws InterruptedException {
        System.out.println("\t\t\t\t\t\t ##  ###   ##  ###  ###  ##  ### | #####");
        Thread.sleep(1000);
        System.out.println("\t\t\t\t\t\t#  # #  # #  #  #   #   #     #  |    # ");
        Thread.sleep(1000);
        System.out.println("\t\t\t\t\t\t###  ###  #  #  #   ### #     #  |   #  ");
        Thread.sleep(1000);
        System.out.println("\t\t\t\t\t\t#    # #  #  #  #   #   #     #  |  #   ");
        Thread.sleep(1000);
        System.out.println("\t\t\t\t\t\t#    #  #  ##  #    ###  ##   #  | #####");
        Thread.sleep(1000);            
    }
	
	public void stampaLento (String frase) {
        for(int i = 0; i < frase.length(); i++) {
            System.out.print(frase.charAt(i));
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println();
    }

	public int colpoCritico() {
		int colpo = random.nextInt(1, 11);
		System.out.println("numero uscito: " + colpo); //colpo critico..? (sleep)
		if(colpo == 10) System.out.println("Colpo Critico!");
		return colpo;
	}

	public int infetto() {
		int infetto = random.nextInt(1 , 21);
		System.out.println("numero uscito infetto: " + infetto); //lo zombie ti ha colpito...ti infetterà? (sleep)
		if(infetto == 20) System.out.println("Sei stato infettato");
		return infetto;
	}


	public static int rollDice() {
		int dado = random.nextInt(1, 7);
		return dado;
	}

	public void stealth() {
		if(rollDice() > 3) {
			stampaLento("Riesci ad aggirare lo zombie, non sei stato scoperto");			
			try {
				stanzaSuccessiva();
			} catch (InterruptedException e) {				
				e.printStackTrace();
			}
		} else {
			stampaLento("Hai fatto rumore, lo zombie ti ha sentito...inizia la caccia");			
			if(Giocatore.armaDaFuoco == null) {
				combattimentoCorpoACorpo(player, 
						Prigione.stanzeDellaPrigione.get(Giocatore.locationPlayer).zombieDellaStanza);				
			} else {
				combattimentoArmaDaFuoco(player, 
						Prigione.stanzeDellaPrigione.get(Giocatore.locationPlayer).zombieDellaStanza);
			}
		}
	}

	public static void ispezionaStanza() {
		//se nella stanza non ci sono zombie
		if(Prigione.stanzeDellaPrigione.get(
				Giocatore.locationPlayer).zombieDellaStanza.isEmpty()) {
			//ispezione
			System.out.println("Ispeziono....");
			//se arma, kitmedico e armadaFuoco danno risultato null, stampa non ho trovato nulla
			if(Prigione.stanzeDellaPrigione.get(Giocatore.locationPlayer).arma == null &&
					Prigione.stanzeDellaPrigione.get(Giocatore.locationPlayer).armaDaFuoco == null &&
					Prigione.stanzeDellaPrigione.get(Giocatore.locationPlayer).kitmedico == null) {
				System.out.println("Non hai trovato nulla :( ");
			}	//hai 2 possibilità su 6 di trovare un cacciavite
			if(Giocatore.locationPlayer == 0 && Prigione.stanzeDellaPrigione.get(
					Giocatore.locationPlayer).arma != null) {
				if(rollDice() > 4) {
					Giocatore.arma = Prigione.stanzeDellaPrigione.get(Giocatore.locationPlayer).arma;
					System.out.println("Hai trovato un arma: " +
							Prigione.stanzeDellaPrigione.get(Giocatore.locationPlayer).arma.nomeArma);
					Prigione.stanzeDellaPrigione.get(Giocatore.locationPlayer).arma = null;
				} else {
					System.out.println("Non hai trovato nulla!");
					Prigione.stanzeDellaPrigione.get(Giocatore.locationPlayer).arma = null;
				}
			}
			if(Prigione.stanzeDellaPrigione.get(Giocatore.locationPlayer).arma != null) {
				System.out.println("Hai trovato un arma: " +
						Prigione.stanzeDellaPrigione.get(Giocatore.locationPlayer).arma.nomeArma);
				Giocatore.arma = Prigione.stanzeDellaPrigione.get(Giocatore.locationPlayer).arma;
				Prigione.stanzeDellaPrigione.get(Giocatore.locationPlayer).arma = null;
			} 
			if(Prigione.stanzeDellaPrigione.get(Giocatore.locationPlayer).armaDaFuoco != null) {
				Giocatore.armaDaFuoco = Prigione.stanzeDellaPrigione.get(Giocatore.locationPlayer)
						.armaDaFuoco;
				System.out.println("Hai trovato un arma da fuoco! " +
						Prigione.stanzeDellaPrigione.get(Giocatore.locationPlayer).armaDaFuoco.nomeArma);
				Prigione.stanzeDellaPrigione.get(Giocatore.locationPlayer).armaDaFuoco = null;				
			}
			if(Prigione.stanzeDellaPrigione.get(Giocatore.locationPlayer).kitmedico != null) {
				Giocatore.listaKitMedici.add(Prigione.stanzeDellaPrigione.get(Giocatore.locationPlayer).kitmedico);
				System.out.println("Hai trovato un kit medico: " +
						Prigione.stanzeDellaPrigione.get(Giocatore.locationPlayer).kitmedico.nomeKit);
				Prigione.stanzeDellaPrigione.get(Giocatore.locationPlayer).kitmedico = null;
			}
		} 
	}


	public void stampaStanzaPlayer () {
		System.out.println("Ti trovi in: " +
				Prigione.stanzeDellaPrigione.get(Giocatore.locationPlayer).nomiStanze);
	}

	public int stanzaSuccessiva() throws InterruptedException {
		Giocatore.locationPlayer++;
		stampaLento("Sei passato in: " +
				Prigione.stanzeDellaPrigione.get(Giocatore.locationPlayer).nomiStanze);		
		if(Giocatore.locationPlayer == 1 && !Prigione.
				stanzeDellaPrigione.get(Giocatore.locationPlayer).zombieDellaStanza.isEmpty()) {
			stampaLento("Un passo dopo l'altro superi la soglia della cella. Fa un bell'effetto"
					+ "\nesserne uscito, ma appena il tuo sguardo si posa su quello che vedi nel"
					+ "\ncorridoio il tuo primo istinto e' quello di tornarci dentro e nasconderti."
					+ "\n\nDue esseri ingobbiti su una figura a terra che si dibatte. Fai per tornare"
					+ "\ndentro ma urti contro qualcosa e loro si girano lentamente verso di te,"
					+ "\nle bocche grondanti di sangue e bava distorte da quel ghigno affamato "
					+ "\nmandano dei suoni orrendi che ti fanno accapponare la pelle"
					+ "\nSi alzano barcollando verso di te, ora il loro bersaglio, il loro prossimo"
					+ "\npasto sei chiaramente tu!\n\n");
			if(Giocatore.armaDaFuoco != null) { combattimentoArmaDaFuoco(player, 
					Prigione.stanzeDellaPrigione.get(Giocatore.locationPlayer).zombieDellaStanza);
			//fine combattimento nel corridoio (il primo)
			stampaLento("Hai ucciso quegli esseri disgustosi. Non riesci a toglierti dalla testa"
					+ "\nil tonfo sordo dei colpi mortali che hai sferrato. Ritorna in te, e' solo l'inizio.."
					+ "\nProcediamo...");
			stanzaSuccessiva();
			}
			else { 
				combattimentoCorpoACorpo(player, 
						Prigione.stanzeDellaPrigione.get(Giocatore.locationPlayer).zombieDellaStanza);
				stampaLento("Hai ucciso quegli esseri disgustosi. Non riesci a toglierti dalla testa"
						+ "\nil tonfo sordo dei colpi mortali che hai sferrato. Ritorna in te, e' solo l'inizio.."
						+ "\nProcediamo...");				
				//fine combattimento nel corridoio (il primo)
				stanzaSuccessiva();
			}
		} else if(Giocatore.locationPlayer == 5 && !Prigione.stanzeDellaPrigione.get(Giocatore.locationPlayer).zombieDellaStanza.isEmpty()) {
			stampaLento("Apri la porta sul cortile che ha ospitato le tue ore d'aria.."
					+ "\nQuello che ti trovi davanti non ricorda nemmeno lontanamente un essere umano."
					+ "\nUn essere mostruoso, sbavando ti punta quello che una volta doveva essere un dito"
					+ "\nlanciando un urlo mostruoso correndo nella tua direzione");
						if(Giocatore.armaDaFuoco != null) combattimentoArmaDaFuoco(player, 
					Prigione.stanzeDellaPrigione.get(Giocatore.locationPlayer).zombieDellaStanza);
			else combattimentoCorpoACorpo(player, 
					Prigione.stanzeDellaPrigione.get(Giocatore.locationPlayer).zombieDellaStanza);
		}
		return Giocatore.locationPlayer;
	}

	public int stanzaPrecedente() {
		if(Giocatore.locationPlayer == 0) {
			System.out.println("Non ci sono stanze precedenti!");
			return Giocatore.locationPlayer;
		} else {
			Giocatore.locationPlayer--;
			stampaLento("Sei passato in: " +
					Prigione.stanzeDellaPrigione.get(Giocatore.locationPlayer).nomiStanze);			
			return Giocatore.locationPlayer;
		}
	}

	@SuppressWarnings("resource")
	public int combattimentoCorpoACorpo(Giocatore player,
			List<Zombie> zombieDellaStanza) throws InputMismatchException {
		for(int i = zombieDellaStanza.size() - 1; i >=0; i--) {
			stampaLento("\nZombies nella stanza: " + (i+1));			
			stampaLento("\nCombattimento iniziato!");			
			//combattimento singolo
			while(Giocatore.pv > 0 && zombieDellaStanza.get(i).pv > 0) {
				stampaLento("Vita del giocatore: "
						+ + Giocatore.pv + "\nVita zombie: " + zombieDellaStanza.get(i).pv);				
				//caso in cui il danno dell'arma sia uguale alla vita dello zombie
				if(Giocatore.arma.danno == zombieDellaStanza.get(i).pv) {
					//caso in cui esca il colpo critico al tiro dei dadi
					System.out.print("\nPremi 0 per lanciare il dado e provare "
							+ "\na sferrare un colpo letale!");
					int scelta = new Scanner(System.in).nextInt();
					System.out.println();
					if(scelta == 0)
						if(colpoCritico() == 10){
							System.out.println("Lo zombie e' morto sul colpo!");
							zombieDellaStanza.get(i).pv = 0;
						} else {
							//nel caso in cui il colpo critico non esca tirando i dadi:
							stampaLento("Niente da fare, il combattimento prosegue!");							
							//se il tiro del dado da un numero minore di 3 c'è un ulteriore lancio
							//che lancerà il metodo "infetto": una possibilità su 20 di venire
							//infettato:
							stampaLento("\nLo zombie ti attacca!"
									+ "\n\nPremi 0 per lanciare il dado e vedere"
									+ "\nse riesci a schivare!");							
							int scelta1 = new Scanner(System.in).nextInt();
							if(scelta1 == 0)
								if(rollDice() < 3) {
									stampaLento("\nIl Giocatore ha ricevuto del danno"
											+ " dallo Zombie");								
									Giocatore.pv -= zombieDellaStanza.get(i).danno;
									stampaLento("\nPremi 0 per lanciare i dadi e vedere "
											+ "\nse ti infetta!");								
									int scelta2 = new Scanner(System.in).nextInt();
									//se infetto da come risultato 20 il giocatore muore
									if(scelta2 == 0) 
										if(infetto() == 20) {
											stampaLento(Giocatore.nomeUtente
													+ " sei stato infettato!");											
											Giocatore.pv = 0; 
										}
									stampaLento("L'hai scampata bella!"
											+ "\nLo Zombie e' morto!");																		
									zombieDellaStanza.get(i).pv = 0;
								} else {
									//altrimenti è lo zombie a morire
									zombieDellaStanza.get(i).pv = 0;
								}
						}
					//Caso danno dell arma superiore alla vita dello zombie
				} else if (Giocatore.arma.danno > zombieDellaStanza.get(i).pv) {
					//lancio del dado per vedere se il colpo critico esce
					//si -> lo zombie muore
					stampaLento("\nPremi 0 per lanciare il dado e provare"
							+ "\na sferrare un colpo letale!");					
					int scelta = new Scanner(System.in).nextInt();
					if(scelta == 0)
						if(colpoCritico() == 10){
							stampaLento("\nLo zombie e' morto sul colpo");							
							zombieDellaStanza.get(i).pv = 0;
						} else {
							stampaLento("Niente da fare, il combattimento prosegue!"
									+ "\nLo zombie ti attacca!"
									+ "\n\nPremi 0 per lanciare il dado e vedere"
									+ "\nse riesci a schivare!");														
							int scelta1 = new Scanner(System.in).nextInt();
							//no -> lancio del dado per vedere se lo zombie ci infetterà
							if(scelta1 == 0)
								if(rollDice() == 1) {
									//si-> sia il giocatore che lo zombie muoiono
									stampaLento("Il Giocatore ha ricevuto del"
											+ " danno dallo Zombie");									
									Giocatore.pv -= zombieDellaStanza.get(i).danno;
									//interazione con l'utente (infetto)
									stampaLento("\nPremi 0 per lanciare i dadi e vedere "
											+ "\nse ti infetta!");									
									int scelta2 = new Scanner(System.in).nextInt();
									if(scelta2 == 0)
										if(infetto() == 20) {
											stampaLento(Giocatore.nomeUtente
													+ " sei stato infettato!");											
											Giocatore.pv = 0;
										}
									stampaLento("L'hai scampata bella!");									
									zombieDellaStanza.get(i).pv = 0;
								} else {
									//no -> è solo zombie a morire
									zombieDellaStanza.get(i).pv = 0;
								}
						}
					//nel caso in cui l'arma fa meno danno della vita dello zombie
				} else {
					//lancio del dado per vedere se c'è il colpo critico
					//si -> lo zombie muore sul colpo
					stampaLento("\nPremi 0 per lanciare il dado e provare "
							+ "\na sferrare un colpo letale!\n");					
					int scelta = new Scanner(System.in).nextInt();
					if(scelta == 0) 
						if(colpoCritico() == 10){
							stampaLento("\nBravissimo!\nLo zombie e' morto sul colpo");							
							zombieDellaStanza.get(i).pv = 0;
							//no -> sia lo zombie che il giocatore ricevono danno
						} else {
							stampaLento("Niente da fare, il combattimento prosegue!"
									+ "\nIl Giocatore ha ricevuto del danno dallo Zombie");							
							zombieDellaStanza.get(i).pv -= Giocatore.arma.danno;
							Giocatore.pv -= zombieDellaStanza.get(i).danno;
							//tiro del dado per vedere se ci infetta
							stampaLento("\nPremi 0 per lanciare i dadi e vedere "
									+ "\nse ti infetta!");							
							int scelta2 = new Scanner(System.in).nextInt();
							//se infetto da come risultato 20 il giocatore muore
							if(scelta2 == 0) 
								if(infetto() == 20) {
									stampaLento(Giocatore.nomeUtente
											+ " sei stato infettato!");									
									Giocatore.pv = 0;
								} else {
									stampaLento("L'hai scampata bella!");									
									zombieDellaStanza.get(i).pv = 0;
								}
						}
				}
			}
			//fuori da tutti i controlli, prima che il combattimento finisca
			//viene stampata la vita del giocatore e quella dello zombie
			stampaLento("Combattimento finito!\nVita del giocatore: "
					+ Giocatore.pv + "\nVita zombie: " + zombieDellaStanza.get(i).pv);			
			if(Giocatore.pv == 0) {
				//se la vita del giocatore è uguale a 0 il programma si chiude 
				stampaLento("Sei morto..GAME OVER!");				
				System.exit(0); 
			}
			if(zombieDellaStanza.get(i).pv == 0) zombieDellaStanza.remove(i);
		}
		return Giocatore.pv;
	}


	@SuppressWarnings("resource")
	public int combattimentoArmaDaFuoco(Giocatore player,
			List<Zombie> zombieDellaStanza) throws InputMismatchException { 
		//for per ripetere il combattimento finchè la stanza non sarà vuota
		for(int i = zombieDellaStanza.size() - 1; i >= 0; i--) {
			stampaLento("\nZombies nella stanza: " + (i+1)
					+ "\nCombattimento iniziato!");						
			//combattimento singolo
			while(Giocatore.pv > 0 && zombieDellaStanza.get(i).pv > 0) {
				stampaLento("Vita del giocatore: "
						+ Giocatore.pv + "\nVita zombie: " + zombieDellaStanza.get(i).pv);				
				if(Giocatore.armaDaFuoco.danni == zombieDellaStanza.get(i).pv) {
					stampaLento("\nPremi 0 per lanciare il dado e provare "
							+ "\na sferrare un colpo letale!");					
					int scelta = new Scanner(System.in).nextInt();
					System.out.println();
					if(scelta == 0)
						if(colpoCritico() == 10) {
							stampaLento("Colpo critico!"
									+ "\n\nLo zombie e' morto sul colpo");							
							Giocatore.armaDaFuoco.munizioni--;
							zombieDellaStanza.get(i).pv = 0;
						} else {
							stampaLento("Niente da fare, il combattimento prosegue!"
									+ "\nLo zombie ti attacca!"
									+ "\n\nPremi 0 per lanciare il dado  e vedere"
									+ "\nse riesci a schivare!");							
							int scelta1 = new Scanner(System.in).nextInt();
							if(scelta1 == 0)
								if(rollDice() < 3) {
									stampaLento("Il Giocatore ha ricevuto del"
											+ " danno dallo Zombie");									
									Giocatore.pv -= zombieDellaStanza.get(i).danno;
									stampaLento("\nPremi 0 per lanciare i dadi e vedere "
											+ "\\nse ti infetta!");									
									int scelta2 = new Scanner(System.in).nextInt();
									//se infetto da come risultato 20 il giocatore muore
									if(scelta2 == 0) 
										if(infetto() == 20) {
											stampaLento(Giocatore.nomeUtente
													+ " sei stato infettato!");											
											Giocatore.pv = 0;
										}
									stampaLento("Il giocatore e' sopravvissuto...\n");
									System.out.println("Bang!");
									stampaLento("\nLo Zombie è morto!");											
									Giocatore.armaDaFuoco.munizioni--;
									zombieDellaStanza.get(i).pv = 0;
								} else {				
									System.out.println("Bang!");
									Giocatore.armaDaFuoco.munizioni--;
									zombieDellaStanza.get(i).pv = 0;
								}
						}
					//Caso Danno dell arma superiore alla vita dello zombie
				} else if (Giocatore.armaDaFuoco.danni > zombieDellaStanza.get(i).pv) {
					stampaLento("\nPremi 0 per lanciare il dado e provare "
							+ "\na sferrare un colpo letale!");					
					int scelta = new Scanner(System.in).nextInt();				
					if(scelta == 0)
						if(colpoCritico() == 10) {
							stampaLento("Lo zombie e' morto sul colpo");							
							Giocatore.armaDaFuoco.munizioni--;
							zombieDellaStanza.get(i).pv = 0;
						} else {
							stampaLento("Niente da fare, il combattimento prosegue!"
									+ "\nLo zombie ti attacca!"
									+ "\nPremi 0 per lanciare il dado e vedere"
									+ "\nse riesci a schivare!");							
							int scelta1 = new Scanner(System.in).nextInt();
							if(scelta1 == 0)
								if(rollDice() == 1) {
									stampaLento("Il Giocatore ha ricevuto del danno"
											+ " dallo Zombie");									
									Giocatore.pv -= zombieDellaStanza.get(i).danno;
									stampaLento("\nPremi 0 per lanciare i dadi e vedere "
											+ "\nse ti infetta!");									
									int scelta2 = new Scanner(System.in).nextInt();
									//se infetto da come risultato 20 il giocatore muore
									if(scelta2 == 0) 
										if(infetto() == 20) {
											stampaLento(Giocatore.nomeUtente
													+ " sei stato infettato!");											
											Giocatore.pv = 0;
										}
									stampaLento("Il giocatore e' sopravvissuto...");
									System.out.println("Bang!");
									stampaLento("Lo Zombie e' morto!");									
									Giocatore.armaDaFuoco.munizioni--;
									zombieDellaStanza.get(i).pv = 0;
								} else {
									System.out.println("Bang!");
									Giocatore.armaDaFuoco.munizioni--;
									zombieDellaStanza.get(i).pv = 0;
								}
						}
				} else { //nel caso in cui l'arma fa meno danno della vita dello zombie
					stampaLento("\nPremi 0 per lanciare il dado e provare "
							+ "\na sferrare un colpo letale!");					
					int scelta = new Scanner(System.in).nextInt();					
					if(scelta == 0)
						if(colpoCritico() == 10) {
							stampaLento("Lo zombie e' morto sul colpo");							
							Giocatore.armaDaFuoco.munizioni--;
							zombieDellaStanza.get(i).pv = 0;
						} else {
							stampaLento("Niente da fare, il combattimento prosegue!"
									+ "\nLo zombie ti attacca!"
									+ "\nPremi 0 per lanciare il dado e vedere"
									+ "\nse riesci a schivare!");						
							int scelta1 = new Scanner(System.in).nextInt();
							if(scelta1 == 0)
								System.out.println("Bang!");
							Giocatore.pv -= zombieDellaStanza.get(i).danno;
							Giocatore.armaDaFuoco.munizioni--;
							zombieDellaStanza.get(i).pv -= Giocatore.armaDaFuoco.danni;
							stampaLento("Premi 0 per lanciare i dadi e vedere "
									+ "\nse ti infetta!");							
							int scelta2 = new Scanner(System.in).nextInt();
							//se infetto da come risultato 20 il giocatore muore
							if(scelta2 == 0) 
								if(infetto() == 20) {
									stampaLento(Giocatore.nomeUtente
											+ " sei stato infettato!");									
									Giocatore.pv = 0;
								}
							stampaLento("L'hai scampata bella!");							
						}
				}
			}
			stampaLento("Combattimento finito!\nVita del giocatore: " + Giocatore.pv
					+ "\nVita zombie: " + zombieDellaStanza.get(i).pv);			
			if(Giocatore.pv == 0) {
				stampaLento("Sei morto..GAME OVER!");				
				System.exit(0);
			}
			if(zombieDellaStanza.get(i).pv == 0) zombieDellaStanza.remove(i);
		}
		return Giocatore.pv;
	}

	public void menuKitMedico() {
		stampaLento("Quale kit medico vuoi usare?"
				+ "\nPremi: ");
		for(int i = 0; i < Giocatore.listaKitMedici.size(); i++) {		
			System.out.println(i + " per usare Kitmedico " + Giocatore.listaKitMedici.get(i).nomeKit );
		}
	}

	public void utilizzoKitMedico(int scelta) {
		stampaLento("hai utilizzato KitMedico "
				+ Giocatore.listaKitMedici.get(scelta).nomeKit);		
		Giocatore.pv += Giocatore.listaKitMedici.get(scelta).recuperoPv;
		if(Giocatore.pv > 100) Giocatore.pv = 100;
		stampaLento("Punti Vita recuperati: " + Giocatore.listaKitMedici.get(scelta).recuperoPv +
				"\n" + Giocatore.nomeUtente + ", adesso hai vita: " + Giocatore.pv);		
		Giocatore.listaKitMedici.remove(scelta);
	}
	@SuppressWarnings("resource")
	public void menuCheckStanza() {
		if(Prigione.stanzeDellaPrigione.get(
				Giocatore.locationPlayer).zombieDellaStanza.isEmpty()) {			
			System.out.println("Nella stanza non ci sono zombies"
					+ "\n\nPremi 0 per Ispezionare la stanza"
					+ "\nPremi 1 per andare alla stanza successiva"
					+ "\nPremi 2 per andare alla stanza precedente"
					+ "\nPremi 3 per vedere dove ti trovi"
					+ "\nPremi 4 per vedere i pv del giocatore"
					+ "\nPremi 5 per controllare le munizioni"
					+ "\nPremi 6 per controllare l'inventario kit medici"
					+ "\nPremi 7 per utilizzare un kit medico"
					+ "\nPremi 8 per vedere l'arma equipaggiata"
					+ "\nPremi 9 per uscire dal programma");
			int scelta = new Scanner(System.in).nextInt();
			interazioneStanzaSenzaZombie(scelta);
		} else {
			System.out.println("Nella stanza ci sono ancora " 
					+ Prigione.stanzeDellaPrigione.get(Giocatore.locationPlayer)
					.zombieDellaStanza.size() + " zombies"
					+ "\n\nPremi 0 per cercare di aggirare gli zombies"
					+ "\nPremi 1 per combattere"
					+ "\nPremi 2 per vedere dove ti trovi"
					+ "\nPremi 3 per vedere i pv del giocatore"
					+ "\nPremi 4 per controllare le munizioni"
					+ "\nPremi 5 per controllare l'inventario kit medici"
					+ "\nPremi 6 per utilizzare un kit medico"
					+ "\nPremi 7 per vedere l'arma da fuoco equipaggiata"
					+ "\nPremi 8 per vedere l'arma equipaggiata"
					+ "\nPremi 9 per uscire dal programma");
			int scelta = new Scanner(System.in).nextInt();
			interazioneStanzaConZombie(scelta);
		}
	}

	//stanza con zombie
	@SuppressWarnings("resource")
	public void interazioneStanzaConZombie (int scelta) {
		switch (scelta) {
		case 0://stealth
			stealth();
			break;
		case 1://combattimento
			if(Giocatore.armaDaFuoco == null) {
				combattimentoCorpoACorpo(player,
						Prigione.stanzeDellaPrigione.get(Giocatore.locationPlayer).zombieDellaStanza);
			} else {
				combattimentoArmaDaFuoco(player,
						Prigione.stanzeDellaPrigione.get(Giocatore.locationPlayer).zombieDellaStanza);
			}
			break;
		case 2: //stampa stanza
			stampaStanzaPlayer();
			break;
		case 3://stampa vita giocatore
			Giocatore.stampaPv();
			break;
		case 4://controllo munizioni
			if(Giocatore.armaDaFuoco != null) {
				Giocatore.armaDaFuoco.controlloMunizioni();
			} else stampaLento("Non hai un arma ne tantomeno munizioni "
						+ "\na disposizione, dovrai cavartela da solo");							
			break;
		case 5: //stampo kit medici
			if(!Giocatore.listaKitMedici.isEmpty()) {
				stampaLento("Kit medici del giocatore: ");				
				for(int i = 0; i < Giocatore.listaKitMedici.size(); i++) {
					System.out.print(Giocatore.listaKitMedici.get(i).nomeKit + " ");
				} System.out.println("\n");
			} else 	stampaLento("Non hai KitMedici a disposizione");							
			break;
		case 6: //utilizzo kit medico
			if(Giocatore.listaKitMedici.isEmpty()) {
				stampaLento("Non hai kit medici");				
			} else {
				menuKitMedico();
				int scelta1 = new Scanner(System.in).nextInt();
				utilizzoKitMedico(scelta1);	        		
			}
			break;
		case 7:
			if(Giocatore.armaDaFuoco != null) {
				stampaLento("Arma da fuoco equipaggiata: " + Giocatore.armaDaFuoco.nomeArma);				
			} else stampaLento("Non possiedi un arma da fuoco!"); 						
			break;
		case 8:
			stampaLento("Arma equipaggiata: " + Giocatore.arma.nomeArma);			
			break;
		case 9: //uscita dal programma
			System.out.println("Esco");
			System.exit(0);
			break;
		default:
			System.err.println("Devi inserire un numero da 0 a 9!");
			break;
		}
	}

	@SuppressWarnings("resource")
	public void interazioneStanzaSenzaZombie (int scelta) {
		switch (scelta) {
		case 0: //ispeziona
					ispezionaStanza();				
			break;
		case 1: //stanza successiva
			if(Giocatore.locationPlayer != 5)
				try {
					stanzaSuccessiva();
				} catch (InterruptedException e) {					
					e.printStackTrace();
				}
			else {
				stampaLento("Complimenti " + Giocatore.nomeUtente
						+ "! per ora puoi tirare un sospiro di sollievo..."
						+ "\nsei riuscito a SOPRAVVIVERE!");				
				System.exit(0);
			}
			break;
		case 2: //stanza precedente 
			stanzaPrecedente();
			break;
		case 3: //stampa stanza
			stampaStanzaPlayer();
			break;
		case 4://stampa vita giocatore
			Giocatore.stampaPv();
			break;
		case 5://controllo munizioni
			if(Giocatore.armaDaFuoco != null) {
				Giocatore.armaDaFuoco.controlloMunizioni();
			} else {
				stampaLento("Non hai un arma da fuoco ne tantomeno munizioni a disposizione,"
						+ "\n dovrai cavartela da solo");				
			}
			break;
		case 6: //stampo kit medici
			if(!Giocatore.listaKitMedici.isEmpty()) {
				stampaLento("Kit medici del giocatore: ");				
				for(int i = 0; i < Giocatore.listaKitMedici.size(); i++) {
					System.out.print(Giocatore.listaKitMedici.get(i).nomeKit + " ");
				} System.out.println("\n");
			} else 	stampaLento("Non hai KitMedici a disposizione");							
			break;
		case 7: //utilizzo kit medico
			if(Giocatore.listaKitMedici.isEmpty()) {
				stampaLento("Non hai kit medici");				
			} else {
				menuKitMedico();
				int scelta1 = new Scanner(System.in).nextInt();
				utilizzoKitMedico(scelta1);	        		
			}
			break;
		case 8: //arma equipaggiata :
			stampaLento("Arma equipaggiata: " + Giocatore.arma.nomeArma);			
			break;
		case 9: //esco dal programma
			System.out.println("Esco");
			System.exit(0);
			break;
		default:
			System.err.println("Devi inserire un numero da 0 a 9!");
			break;
		}
	}
}