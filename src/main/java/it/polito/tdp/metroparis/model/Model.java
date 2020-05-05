package it.polito.tdp.metroparis.model;

import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import it.polito.tdp.metroparis.db.MetroDAO;

public class Model {

	private Graph<Fermata, DefaultEdge> graph;
	private List<Fermata> fermate;

	public Model() {
		this.graph = new SimpleDirectedGraph<>(DefaultEdge.class);
		MetroDAO dao = new MetroDAO();
		this.fermate = dao.getAllFermate();

		Graphs.addAllVertices(graph, fermate);
		//CREAZIONE ARCHI
		
		//Modo 1, più semplice, meno efficace
		
		// LENTISSIMOOOOOOOOOO
	/*	for(Fermata fp: this.fermate) { // fermata partenza   
			for( Fermata fa: this.fermate) { //fermata arrivo
				if(dao.fermateConnesse(fp, fa)) {
					this.graph.addEdge(fp, fa);
				}
			}
		}*/
		
		// Modo 2, tutte stazioni da stazione di partenza utile se il grado medio dei vertici è basso
		//rispetto al numero dei vertici, cioè se la densità del grafo bassa
		
		/*for(Fermata fp: this.fermate) {
			List<Fermata> connesse= dao.FermateConnesseAllaFP(fp);
			for (Fermata fa: connesse) {
				this.graph.addEdge(fp, fa);
			}
		}*/
		
		//Modo 3 farci dare dal database gli archi che ci servono
		List<CoppieFermate> coppie= dao.getCoppieFermate();
		for(CoppieFermate c: coppie) {
			this.graph.addEdge(c.getFp(), c.getFa());
		}
		
		
		
		
		System.out.format("Grafo caricato con %d vertici e %d archi", this.graph.vertexSet().size(),this.graph.edgeSet().size());
		//System.out.println(graph);
	}
	

	
	public static void main(String[] args) {
		Model model= new Model();
	}
}
