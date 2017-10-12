package DijkstraGraph;

import basics.Connection;
import es.usc.citius.hipster.algorithm.Hipster;
import es.usc.citius.hipster.graph.GraphBuilder;
import es.usc.citius.hipster.graph.GraphSearchProblem;
import es.usc.citius.hipster.graph.HipsterDirectedGraph;
import es.usc.citius.hipster.model.problem.SearchProblem;
import java.util.HashMap;
import java.util.Map;

public class Dijkstra {

    private String path;

    /**
     * Creates a graph and run dijkstra's algorithm to get the optimal path of a connection(made with Hipster) 
     * @param conmap HashMap
     * @param from String
     * @param to String
     */
    public Dijkstra(HashMap<String, Connection> conmap, String from, String to) {
        GraphBuilder b = GraphBuilder.create();
        for (Map.Entry<String, Connection> con : conmap.entrySet()) {
            b.connect(con.getValue().getFrom().getStation().getName()).to(con.getValue().getTo().getStation().getName()).withEdge(1d);
        }
        HipsterDirectedGraph<String, Object> graph = b.createDirectedGraph();
        SearchProblem p = GraphSearchProblem.startingFrom(from).in(graph).takeCostsFromEdges().build();
        path = Hipster.createDijkstra(p).search(to).getOptimalPaths().toString();
        
    }

    /**
     * Getter for path field. Returns a string with the result of dijkstra
     * @return String
     */
    public String getPath() {

        return path;
    }
}
