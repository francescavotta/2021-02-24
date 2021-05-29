package it.polito.tdp.PremierLeague.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	private PremierLeagueDAO dao;
	private Graph<Player, DefaultWeightedEdge > grafo;
	private Map <Integer, Player> idMap;
	private Simulazione sim ;
	
	public Model() {
		this.dao = new PremierLeagueDAO();
		this.idMap = new HashMap<>();
		this.dao.listAllPlayers(idMap);
		sim = new Simulazione();
	}
	
	public Graph<Player, DefaultWeightedEdge> creaGrafo(Match m) {
		grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(grafo, dao.getVertici(idMap, m));
		
		for(Adiacenza a : dao.getAdiacenza(m, idMap)) {
			if(grafo.containsVertex(a.getP1()) && grafo.containsVertex(a.getP2()))
				if(a.getPeso() >= 0) {
					//p1 meglio di p2
					Graphs.addEdgeWithVertices(grafo, a.getP1(), a.getP2(), a.getPeso());
				}else {
					//p2 meglio di p1
					Graphs.addEdgeWithVertices(grafo, a.getP2(), a.getP1(), -a.getPeso());
				}
		}
		
		System.out.println("Grafo creato con successo: \n");
		System.out.println("Grafo con vertici: \n" + grafo.vertexSet().size());
		System.out.println("Grafo con archi: \n" + grafo.edgeSet().size());
		
		return grafo;
	}
	
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int nArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public List<Match> getTuttiIMatch(){
		List <Match> matches = dao.listAllMatches();
		Collections.sort(matches, new Comparator< Match>() {

			@Override
			public int compare(Match o1, Match o2) {
				// TODO Auto-generated method stub
				return o1.getMatchID() - o2.getMatchID();
			}
		}
		);
		return matches;
	}
	
	public GiocatoreMigliore getMigliore() {
		if(grafo == null) {
			return null;
		}
		
		Player best = null;
		Double maxDelta = (double) Integer.MIN_VALUE;
		
		for(Player p: this.grafo.vertexSet()) {
			double pesoUscente = 0.0;
			for(DefaultWeightedEdge edge : grafo.outgoingEdgesOf(p)) {
				pesoUscente += this.grafo.getEdgeWeight(edge);
			}
			
			double pesoEntrante = 0.0;
			for(DefaultWeightedEdge edge : grafo.incomingEdgesOf(p)) {
				pesoEntrante += this.grafo.getEdgeWeight(edge);
			}
			
			double delta = pesoUscente - pesoEntrante;
			if(delta > maxDelta) {
				best = p;
				maxDelta = delta;
			}
		}
		
		return new GiocatoreMigliore(best, maxDelta);
	}

	public Graph<Player, DefaultWeightedEdge> getGrafo() {
		// TODO Auto-generated method stub
		return grafo;
	}
	
	public void simula(Match m, int N) {
		String squadraBest = dao.getNomeSquadra(this.getMigliore().getP().getPlayerID(), m.getMatchID());
		sim.simula(m, N, squadraBest);
	}
	
	public int getGolWorst() {
		return sim.getGolWorst();
	}


	public int getGolBest() {
		return sim.getGolBest();
	}


	public int getEspulsiWorst() {
		return sim.getEspulsiWorst();
	}

	public int getEspulsiBest() {
		return sim.getEspulsiBest();
	}
	
	public String getSquadraWorst() {
		return sim.getSquadraWorst();
	}
	
	public String getSquadraBest() {
		return sim.getSquadraBest();
	}

	
	
	
	
	
}
