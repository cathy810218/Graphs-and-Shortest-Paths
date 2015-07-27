/**
 * @author <Yi-Ching Oun; Tzu-Wei Chuang>
 * @UWNetID <youn0001; twc5>
 * @studentID <1267202; 1238519>
 * @email <cathy810218@gmail.com; vivi51123@gmail.com>
 * 
 * A representation of a graph. Assumes that we do not have negative cost edges
 * in the graph.
 */
import java.util.*;

public class MyGraph implements Graph {
	private final Map<Vertex, Set<Edge>> pair;  // a map that represents the graph 

	/**
	 * Creates a MyGraph object with the given collection of vertices and the
	 * given collection of edges.
	 * 
	 * @param v
	 *            a collection of the vertices in this graph
	 * @param e
	 *            a collection of the edges in this graph
	 * @throws IllegalArgumentException
	 *             if the weight of edge is negative.
	 * @throws IllegalArgumentException
	 *             if the collection of edges has the same directed edge
	 *             more than once with a different weight            
	 */
	public MyGraph(Collection<Vertex> v, Collection<Edge> e) {
		this.pair = new HashMap<Vertex, Set<Edge>>();
		for (Vertex vertex : v) {
			pair.put(vertex, new HashSet<Edge>());  //initialize a vertex and its set of edges
		}
		for (Edge edge : e) {
			if (edge.getWeight() <= 0) {
				throw new IllegalArgumentException("Edge weights should not be negative or zero."); //Edge weights should not be negative.
			}
			for(Edge edge2 :e) {
				if(edge2.getSource().equals(edge.getSource()) && 
					edge2.getDestination().equals(edge.getDestination()) && 
					edge2.getWeight() != edge.getWeight()){
					throw new IllegalArgumentException("Duplicate edges with a different weight.");
				}
			}
			//find the source and the destination
			Vertex source = edge.getSource(); 
			Vertex destination = edge.getSource();
			if (pair.containsKey(source) && pair.containsKey(destination)) {
				pair.get(source).add(edge); //get the vertex of the map and add the edge to it
			}	
		}
	}

	/**
	 * Return the collection of vertices of this graph
	 * 
	 * @return the vertices as a collection (which is anything iterable)
	 */
	@Override
	public Collection<Vertex> vertices() {
		return pair.keySet();
	}

	/**
	 * Return the collection of edges of this graph
	 * 
	 * @return the edges as a collection (which is anything iterable)
	 */
	@Override
	public Collection<Edge> edges() {
		Set<Edge> allEdges = new HashSet<Edge>();
		for (Set<Edge> edgeSet : pair.values()) { // pair.values() gives Set<Edge> too
			allEdges.addAll(edgeSet);
		}
		return allEdges;
	}

	/**
	 * Return a collection of vertices adjacent to a given vertex v. i.e., the
	 * set of all vertices w where edges v -> w exist in the graph. Return an
	 * empty collection if there are no adjacent vertices.
	 * 
	 * @param v
	 *            one of the vertices in the graph
	 * @return an iterable collection of vertices adjacent to v in the graph
	 * @throws IllegalArgumentException
	 *             if v does not exist.
	 */
	@Override
	public Collection<Vertex> adjacentVertices(Vertex v) {
		if (!pair.containsKey(v)) {
			throw new IllegalArgumentException("Vertex doesn't exist.");
		}
		Set<Vertex> adjacentVertex = new HashSet<Vertex>();	//a collection of vertices adjacent to a given vertex v
		Set<Edge> shareEdgeSet = pair.get(v); //set of all edges associated with the vertex v
		for (Edge e : shareEdgeSet) {
			adjacentVertex.add(e.getDestination());
		}
		return adjacentVertex; 
	}

	/**
	 * Test whether vertex b is adjacent to vertex a (i.e. a -> b) in a directed
	 * graph. Assumes that we do not have negative cost edges in the graph.
	 * 
	 * @param a
	 *            one vertex
	 * @param b
	 *            another vertex
	 * @return cost of edge if there is a directed edge from a to b in the
	 *         graph, return -1 otherwise.
	 * @throws IllegalArgumentException
	 *             if a or b do not exist.
	 */
	@Override
	public int edgeCost(Vertex a, Vertex b) {
		if (!pair.containsKey(a) || !pair.containsKey(b)) {
			throw new IllegalArgumentException("Vertex a or b does not exist.");
		}
		Set<Edge> shareEdgeSet = pair.get(a);//set of edges associated with that vertex a
		for (Edge e : shareEdgeSet)
			if (e.getDestination().equals(b)) {
				return e.getWeight();
			}
		return -1;
	}

	/**
	 * Returns the shortest path from a to b in the graph, or null if there is
	 * no such path. Assumes all edge weights are nonnegative. Uses Dijkstra's
	 * algorithm.
	 * 
	 * @param a
	 *            the starting vertex
	 * @param b
	 *            the destination vertex
	 * @return a Path where the vertices indicate the path from a to b in order
	 *         and contains a (first) and b (last) and the cost is the cost of
	 *         the path. Returns null if b is not reachable from a.
	 * @throws IllegalArgumentException
	 *             if a or b does not exist.
	 */
	public Path shortestPath(Vertex a, Vertex b) {
		if (!pair.containsKey(a) || !pair.containsKey(b)) {
			throw new IllegalArgumentException("Vertex a or b does not exist.");
		}
		/* Each element is a path from start to a given node. 
		 * A path's priority in the queue is the total cost of that path. 
		 * Nodes for which no path is known yet are not in the queue.
		 */
		Queue<Path> active = new PriorityQueue<Path>(10, new PathComparator()); 
        
		// Set of nodes for which we know the minimum-cost path from start.
		Set<Vertex> finished = new HashSet<Vertex>();
		
		// Initially we only know of the path from start to itself, which has
	    // a cost of zero because it contains no edges. Add a path from start to itself to active
		List<Vertex> selfList = new ArrayList<Vertex>();
		selfList.add(a);
		Path selfPath = new Path(selfList, 0);  
		active.add(selfPath);

		while (!active.isEmpty()) {
			// minPath is the lowest-cost path in active and is the minimum-cost path to some node
			Path minPath = active.remove(); 

			//minDest is the destination node in minPath
			Vertex minDest = minPath.vertices.get(minPath.vertices.size() - 1); 
			if (minDest.equals(b)) {
				return minPath;
			}

			if (!finished.contains(minDest)) {
				for (Vertex v : adjacentVertices(minDest)) {
					int curEdgeCost = edgeCost(minDest, v);
					// If we don't know the minimum-cost path from start to child, examine the path we've just found
					if (!finished.contains(v)) { 
						List<Vertex> vertices = new ArrayList<Vertex>(minPath.vertices);
						vertices.add(v);
						Path newPath = new Path(vertices, minPath.cost + curEdgeCost);
						active.add(newPath);
					}
				}
				finished.add(minDest);
			}
		} 
		// If the loop terminates, then no path exists from start to dest.
		return null; // Returns null if b is not reachable from a.
	}
	
	/**
	 * A Comparator for Path that sorts two paths in the order we need
	 * for Dijkstra's algorithm : Vertex with smaller cost is more important
	 * than vertex with higher cost.
	 */
	public class PathComparator implements Comparator<Path> {
		@Override
		public int compare(Path a, Path b) {
			if (a.cost < b.cost) {
				return -1;
			} else if (a.cost > b.cost) {
				return 1;
			} else {
				return 0;
			}
		}
	}
}