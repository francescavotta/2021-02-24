package it.polito.tdp.PremierLeague.model;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class Simulazione {
	List<String> eventi;
	//Parametri di output
	private int golWorst;
	private int golBest;
	private int espulsiWorst;
	private int espulsiBest;
	
	//Parametri di input
	private int N;
	private Match m;
	
	//Parametri del mondo
	private int giocatoriBest;
	private int giocatoriWorst;
	
	private String squadraBest;
	private String squadraWorst;
	
	
	
	public void simula(Match m, int N, String squadraBest) {
		this.m = m;
		this.N = N;
		
		this.squadraBest = squadraBest;
		
		if(m.getTeamHomeNAME().equals(squadraBest)) {
			squadraWorst = m.getTeamAwayNAME();
		}else {
			squadraWorst = m.getTeamHomeNAME();
		}
		
		this.golWorst = 0;
		this.golBest = 0;
		this.espulsiWorst = 0;
		this.espulsiBest = 0;
		
		this.giocatoriBest = 11;
		this.giocatoriWorst = 11;
		
		this.eventi = new LinkedList<>();
		
		while(N != 0) {
			double prob = Math.random();
			if(prob <= 0.5) {
				//caso goal
				if(giocatoriWorst > giocatoriBest) {
					golWorst ++;
				}else {
					golBest ++;
				}	
				eventi.add("GOAL");
			}
			if(prob> 0.5 && prob<= 0.8) {
				//caso espulsione
				double probEspulsione = Math.random();
				if(probEspulsione <= 0.6) {
					//caso squadra best
					giocatoriBest --;
					espulsiBest ++;
				}else {
					giocatoriWorst --;
					espulsiWorst ++;
				}
				eventi.add("ESPULSIONE");
			}
			if(prob> 0.8) {
				//caso infortunio
				double probInf = Math.random();
				if(probInf <= 0.5) {
					N = N+2;
				}else
					N = N+3;
				
				eventi.add("INFORTUNIO");
				
			}
			N --;
			
		}
		
		System.out.print(eventi.toString());
		
	}

	public int getGolWorst() {
		return golWorst;
	}

	public void setGolWorst(int golWorst) {
		this.golWorst = golWorst;
	}

	public int getGolBest() {
		return golBest;
	}

	public void setGolBest(int golBest) {
		this.golBest = golBest;
	}

	public int getEspulsiWorst() {
		return espulsiWorst;
	}

	public void setEspulsiWorst(int espulsiWorst) {
		this.espulsiWorst = espulsiWorst;
	}

	public int getEspulsiBest() {
		return espulsiBest;
	}

	public void setEspulsiBest(int espulsiBest) {
		this.espulsiBest = espulsiBest;
	}
	
	public String getSquadraWorst() {
		return this.squadraWorst;
	}
	
	public String getSquadraBest() {
		return this.squadraBest;
	}

}
