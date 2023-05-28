package it.zombie;

public class Zombie {

	public int pv;
	public int danno;
	
	Zombie(int lvl) {
		switch(lvl) {
		case 1:
			pv = 30;
			danno = 10;
			break;
		case 2: 
			pv = 50;
			danno = 20;
			break;
		case 3:
			pv = 100;
			danno = 30;
		case 4:
			pv = 200;
			danno = 40;
		default:
			pv = 30;
			danno = 10;
		}
	}
}
