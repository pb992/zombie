package it.zombie;

import java.util.Random;

public class ArmaDaFuoco {

	public int munizioni;
    public int danni;
    
    
    Random random = new Random();
	public String nomeArma;

    public ArmaDaFuoco() {
        this.danni = 100;
        this.munizioni = random.nextInt(7) + 1;
    }
    
    public ArmaDaFuoco(int livello) {
    	switch(livello) {
    	case 0:
    		this.danni = 0;
    		this.munizioni = 0;
    		break;
    	case 1: 
    		this.danni = 100;
            this.munizioni = random.nextInt(7) + 1;
            break;
        default:
        	this.danni = 0;
    		this.munizioni = 0;
    		break;
    	}
    }

//    public ArmaDaFuoco(int munizioni, int danni) {
//    	this.munizioni = munizioni;
//    	this.danni = danni;    	
//    }
    
    public void controlloMunizioni() {
        System.out.println("Munzioni nel caricatore " + munizioni);
    }
    
    public int spara() {    	
    	return munizioni;
    }

}
