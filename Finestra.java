package it.zombie.views;
//qui dentro ci devono SOLO essere dei metodi riguardanti la struttura del gioco in Finestra.
//Come sono gestite le finestre, chi lancia cosa alla sua chiusura o alla pressione di un 
//bottone ecc. Tutto il resto verrà gestito nella classe GiocoSwing

import java.awt.BorderLayout;  
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import it.zombie.Giocatore;
import it.zombie.Gioco;
import it.zombie.Prigione;

public class Finestra extends JFrame {

	private static final long serialVersionUID = 1L;

	public Finestra() {
		//con la finestra di pop up iniziale mi evito di dover gestire un ennesima finestra
		Giocatore.nomeUtente = JOptionPane.showInputDialog("Inserisci il tuo nome utente: ");
		menuNoZombie();
//		finestraCombattimentoCorpoACorpo();
	}
	public String combattimento;
	public JPanel panel;
	public JButton btn;
	public JTextField txt;
	public Giocatore giocatore;
	public Gioco gioco;
		
	public int finestraRollDice() {		
		setLocationRelativeTo(null);
		setResizable(false);
		
		JPanel panelDice = new JPanel();
		JButton btnDice = new JButton("Lancia!");
		btnDice.setIcon(new ImageIcon("./images/dadi.jpg"));
		
		btnDice.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Gioco.dado1 = Gioco.random.nextInt(1, 7);
				Gioco.dado2 = Gioco.random.nextInt(1, 7);
				
				JOptionPane.showMessageDialog(getRootPane(), Gioco.dado1 + " + " + Gioco.dado2 
						+ "\nTotale: " + (Gioco.dado1 + Gioco.dado2));
				dispose();
				remove(panelDice);
				finestraCombattimentoCorpoACorpo();
			}
		});
		
		panelDice.add(btnDice);
		add(panelDice);
		//la funzione 'pack' adatta la finestra al suo contenuto
		pack();
		setVisible(true);
		return Gioco.dado1 + Gioco.dado2;
	}

	public void menuNoZombie() {
		setName("Menù stanza senza Zombie");
		setLayout(new GridLayout(2,5));
		setSize(300,230);
		setLocationRelativeTo(null);
		setResizable(false);
		
		panel = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		JButton btn0 = new JButton("Ispeziona"); 
		JButton btn1 = new JButton("Stanza successiva"); 
		JButton btn2 = new JButton("Stanza precedente");
		JButton btn3 = new JButton("Dove mi trovo");
		JButton btn4 = new JButton("Punti Vita");
		JButton btn5 = new JButton("Check munizioni");
		JButton btn6 = new JButton("Inventario Kit Medici");
		JButton btn7 = new JButton("Arma equipaggiata");
		JButton btn8 = new JButton("Exit");
		
		//ispeziona stanza
		btn0.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {				
				//se il lancio del dado(!) da come risultato un numero maggiore di 4 (2 dadi -> 8)
				if(Giocatore.locationPlayer == 0 && Gioco.rollDice() > 4) {
					Giocatore.arma = Prigione.stanzeDellaPrigione.get(Giocatore.locationPlayer).arma;
	//un ciclo for per richiamare due finestre pop up 1. "ispeziono" - 2. "hai trovato un cacciavite
					for(int i = 0; i < 2; i++) {
						if(i == 0)JOptionPane.showMessageDialog(getRootPane(), "Ispeziono...");
						else JOptionPane.showMessageDialog(getRootPane(), "Hai trovato un'arma: "
							+ Prigione.stanzeDellaPrigione.get(Giocatore.locationPlayer).arma.nomeArma );
					}
					Prigione.stanzeDellaPrigione.get(Giocatore.locationPlayer).arma = null;
				} else {
	//un ciclo for per richiamare due finestre pop up 1. "ispeziono" - 2. finestra di errore
					for(int i = 0; i < 2; i++) {
						if(i == 0) JOptionPane.showMessageDialog(getRootPane(), "Ispeziono...");
						else JOptionPane.showMessageDialog(getRootPane(), "Non hai trovato nulla!",
								"Errore", JOptionPane.ERROR_MESSAGE);
					}
				}				
			}
		});
		
		//stanza successiva
		btn1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(Giocatore.locationPlayer == 0) {
					Giocatore.locationPlayer++;
					JOptionPane.showMessageDialog(getRootPane(), 
							"Un passo dopo l'altro superi la soglia della cella. Fa un bell'effetto"
							+ "\nesserne uscito, ma appena il tuo sguardo si posa su quello che vedi nel"
							+ "\ncorridoio il tuo primo istinto e' quello di tornarci dentro e nasconderti:"
							+ "\n\nDue esseri ingobbiti su una figura a terra che si dibatte. Fai per tornare"
							+ "\ndentro ma urti contro qualcosa e loro si girano lentamente verso di te,"
							+ "\nle bocche grondanti di sangue e bava distorte da quel ghigno affamato"
							+ "\nmandano dei suoni orrendi che ti fanno accapponare la pelle"
							+ "\nSi alzano barcollando verso di te, ora il loro bersaglio, il loro prossimo"
							+ "\npasto sei chiaramente tu!");
					dispose();
					remove(panel);
					remove(panel2);
					remove(panel3);
					finestraCombattimentoCorpoACorpo();
				} else {
					Giocatore.locationPlayer++;
					JOptionPane.showMessageDialog(getRootPane(), Giocatore.nomeUtente.toUpperCase()
							+ ", sei passato in: "
							+ Prigione.stanzeDellaPrigione.get(Giocatore.locationPlayer).nomiStanze);
					//se la stanza è vuota (senza zombie)
					if(Prigione.stanzeDellaPrigione.get(Giocatore.locationPlayer).zombieDellaStanza.isEmpty()) {
						dispose();
						remove(panel);
						remove(panel2);
						remove(panel3);
						menuNoZombie();
					}
				}
			}
		});
		
		//stanza precedente
		btn2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(Giocatore.locationPlayer == 0) {
					JOptionPane.showMessageDialog(getRootPane(), "Non ci sono stanze precedenti!",
							 "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					Giocatore.locationPlayer--;
					JOptionPane.showMessageDialog(getRootPane(), Giocatore.nomeUtente.toUpperCase()
							+ ", sei passato in: "
							+ Prigione.stanzeDellaPrigione.get(Giocatore.locationPlayer).nomiStanze);
					//se la stanza è vuota lancia la finestra menu no zombie
					if(Prigione.stanzeDellaPrigione.get(Giocatore.locationPlayer).zombieDellaStanza.isEmpty()) {
						dispose();
						remove(panel);
						menuNoZombie();						
					} else { //altrimenti lancia la finestra menu con zombie nella stanza
						dispose();
						remove(panel);
						menuZombie();
					}
				}				
			}
		});
		
		//dove mi trovo?
		btn3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(getRootPane(), Giocatore.nomeUtente.toUpperCase()
						+ ", ti trovi in: "
						+ Prigione.stanzeDellaPrigione.get(Giocatore.locationPlayer).nomiStanze);				
			}
		});
		//stampa punti vita
		btn4.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {				
				JOptionPane.showMessageDialog(getRootPane(), Giocatore.nomeUtente.toUpperCase()
						+ ", hai " + Giocatore.pv + " punti vita");
			}
		});
		//questa funzione l'ho già fatta in classe (recupera file)
		btn5.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//check munizioni
			}
		});
		
		//il bottone per l'utilizzo dei kit medici l'ho già scritto in classe
		btn6.addActionListener(new ActionListener() {
			
			@SuppressWarnings("unused")
			@Override
			public void actionPerformed(ActionEvent e) {
				//inventario kit medici				
				String[] sceltaKit = {"Piccolo", "Medio", "Grande"};
				if(!Giocatore.listaKitMedici.isEmpty()) {
					JOptionPane.showMessageDialog(getRootPane(), "Quale scegli?",
							"Inventario Kit Medici", JOptionPane.YES_NO_CANCEL_OPTION);
					//text field con all'interno i kit medici disponibili...o bottoni?
					//si potrebbe mettere direttamente il btn7(usa kit medico) qui, con
					//action listener
				} else {
					JOptionPane.showMessageDialog(getRootPane(), "Non hai kit medici a disposizione!",
							"Errore", JOptionPane.ERROR_MESSAGE);
				}				
			}
		});
		
		//arma equipaggiata
		btn7.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(getRootPane(), Giocatore.nomeUtente.toUpperCase()
						+ ", hai equipaggiato: " + Giocatore.arma.nomeArma);				
			}
		});
		//esci dal gioco
		btn8.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);			
			}
		});
		
		//inserisco un etichetta per dire dove mi trovo, ma si deve AGGIORNARE!
		 
		panel2.add(btn3);
		panel.add(btn0);
		panel2.add(btn1);
		panel.add(btn2);
		panel.add(btn4);
		panel2.add(btn5);
		panel.add(btn6);
		panel2.add(btn7);
		panel2.add(btn8);
		//da sistemare il posizionamento dei bottoni all'interno della finestra menu
		add(panel, BorderLayout.WEST);
		add(panel2, BorderLayout.EAST);
		pack();
		setVisible(true);
	}	
	//finestra di menu con zombie nella stanza
	@SuppressWarnings("unused")
	public void menuZombie() {
		setName("Menù stanza senza Zombie");
		setLayout(new GridLayout(2,5));
		setSize(300,290);
		setLocationRelativeTo(null);
		setResizable(false);
		
		panel = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		JButton btn0 = new JButton("Aggira gli Zombies"); 
		JButton btn2 = new JButton("Combattimento!"); 
		JButton btn1 = new JButton("Dove mi trovo");
		JButton btn3 = new JButton("Stanza precedente");
		JButton btn4 = new JButton("Punti Vita");
		JButton btn5 = new JButton("Check munizioni");
		JButton btn6 = new JButton("Inventario Kit Medici");
		JButton bnt7 = new JButton("Usa Kit Medico");
		JButton btn8 = new JButton("Arma equipaggiata");
		JButton btn9 = new JButton("Exit");
		
		//aggira gli zombie
		btn0.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(Gioco.rollDice() > 3) {
					JOptionPane.showMessageDialog(getRootPane(), Giocatore.nomeUtente.toUpperCase()
							+ " sei riuscito ad aggirare gli zombies!");
					Giocatore.locationPlayer++;
					if(Prigione.stanzeDellaPrigione.get(Giocatore.locationPlayer).zombieDellaStanza.isEmpty()) {
						dispose();
						remove(panel);
						remove(panel2);
						remove(panel3);
						menuNoZombie();
					} else {
						menuZombie();
					}
				} else {
					JOptionPane.showMessageDialog(getRootPane(), 
							"Hai fatto rumore, lo zombie ti ha sentito!\n\nInizia la caccia!",
							"Beccato!!!", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		//dove mi trovo nella "mappa"
		btn1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(getRootPane(), Giocatore.nomeUtente.toUpperCase()
						+ ", ti trovi in: "
						+ Prigione.stanzeDellaPrigione.get(Giocatore.locationPlayer).nomiStanze);
			}
		});
		//lancia la finestra del combattimento
		btn2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(Giocatore.armaDaFuoco != null) {
					
				} else {
					JOptionPane.showConfirmDialog(getRootPane(), "Inizia il combattimento!");
					dispose();
					remove(panel);
					finestraCombattimentoCorpoACorpo();
					
				}
			}
		});
		
		add(panel);
		add(panel2);
		add(panel3);
		
	}
	
	//finestra combattimento (ancora non si aggiorna)
	public void finestraCombattimentoCorpoACorpo() {
		setName("Combattimento Corpo a Corpo");
		setSize(300, 300);
		setLocationRelativeTo(null);
		setResizable(false);		
		
		JPanel panel = new JPanel();
		JPanel panel2 = new JPanel();
		JTextArea txtArea = new JTextArea();
		JButton btn = new JButton("Attacca!");
		
		txtArea.setRows(4);
		//la stringa non si aggiorna perchè una volta che è stata settata questa è
		//metodo che ricalcola la stringa ogni volta che mi serve
		combattimento = "Vita Zombie: " + Prigione.stanzeDellaPrigione.get(
				Giocatore.locationPlayer).zombieDellaStanza.get(0).pv
				+ "\nPunti Danno: " + Prigione.stanzeDellaPrigione.get(
						Giocatore.locationPlayer).zombieDellaStanza.get(0).danno
				+ "\n\nVita " + Giocatore.nomeUtente.toUpperCase() + ": " + Giocatore.pv
				+ "\nPunti Danno: " + Giocatore.arma.danno;
		txtArea.setText(combattimento);
		txtArea.setEditable(false);
		
		btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//SE danno > vita zombie
				if(Giocatore.arma.danno > Prigione.stanzeDellaPrigione.get(
						Giocatore.locationPlayer).zombieDellaStanza.get(0).pv) {
					JOptionPane.showMessageDialog(getRootPane(), "Tira i dadi per sferrare"
							+ " un colpo letale!");
					//invece che una finestra "rollDice" dovrà essere lanciato un pop-up che salverà
					//il dato numerico in una variabile che sarà successivamente passata all'if sotto
					//(così aspetterà la risposta del pop up prima di continuare la sua esecuzione)
					
					//finestraRollDice();
					ImageIcon icon = new ImageIcon("dadi.jpg");
					int dadi = JOptionPane.showOptionDialog(getRootPane(), "Lancia!", "RollDice",
							JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE,
							icon, null, 0);
					//nel caso in cui sia un colpo critico
					if(dadi > 10) {
						JOptionPane.showMessageDialog(getRootPane(), "Hai inferto un colpo letale allo zombie!");
						Prigione.stanzeDellaPrigione.get(
								Giocatore.locationPlayer).zombieDellaStanza.get(0).pv = 0;
						
						//rimuovi il bottone per attaccare, lo zombie dalla lista
						//e rilancia la finestra(dovrebbero esserci i parametri aggiornati con l'altro zombie)
						panel.remove(btn);
						Prigione.stanzeDellaPrigione.get(
								Giocatore.locationPlayer).zombieDellaStanza.remove(0);
					} else {
						JOptionPane.showMessageDialog(getRootPane(), "Niente da fare, il combattimento continua!");
						Prigione.stanzeDellaPrigione.get(
								Giocatore.locationPlayer).zombieDellaStanza.get(0).pv -= Giocatore.arma.danno;
						panel.remove(btn);
						
					}
					combattimento = "Vita Zombie: " + Prigione.stanzeDellaPrigione.get(
							Giocatore.locationPlayer).zombieDellaStanza.get(0).pv
							+ "\nPunti Danno: " + Prigione.stanzeDellaPrigione.get(
									Giocatore.locationPlayer).zombieDellaStanza.get(0).danno
							+ "\n\nVita " + Giocatore.nomeUtente.toUpperCase() + ": " + Giocatore.pv
							+ "\nPunti Danno: " + Giocatore.arma.danno;
					txtArea.setText(combattimento);
				//SE danno = vita zombie
				} else if (Giocatore.arma.danno == Prigione.stanzeDellaPrigione.get(
						Giocatore.locationPlayer).zombieDellaStanza.get(0).pv) {
					Prigione.stanzeDellaPrigione.get(
							Giocatore.locationPlayer).zombieDellaStanza.get(0).pv = 0;
				//SE danno < vita zombie
				} else if (Giocatore.arma.danno < Prigione.stanzeDellaPrigione.get(
						Giocatore.locationPlayer).zombieDellaStanza.get(0).pv) {
					Prigione.stanzeDellaPrigione.get(
							Giocatore.locationPlayer).zombieDellaStanza.
							
					get(0).pv -=Giocatore.arma.danno;
				}
				//SE lo zombie è morto
				//SE il giocatore è morto
				//SE il combattimento è finito
								
			}
		});

		panel.setBackground(Color.WHITE);
		txtArea.setBackground(Color.WHITE);
		panel.add(txtArea);
		panel2.add(btn, BorderLayout.PAGE_END);
		add(panel);
		add(panel2);
		
		setVisible(true);
	}
}