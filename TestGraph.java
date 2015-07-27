/**
 * @author <Yi-Ching Oun; Tzu-Wei Chuang>
 * @UWNetID <youn0001; twc5>
 * @studentID <1267202; 1238519>
 * @email <cathy810218@gmail.com; vivi51123@gmail.com>
 * 
 * This TestGraph class tests our graph algorithm and see if it gives 
 * us the expected results.
 * 
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.*;
public class TestGraph {
	
	/**
	 * This method checks if the results are correct by printing them out
	 * @param args The input arguments of this program
	 */
	public static void main(String[] args) {

	    // expected result from AVA -> MGH: 4 <AVA -> EEB -> MGH>
	    System.out.println("**This tests two paths with different weights**");
		MyGraph g1 = test(new String[] {"AVA", "EEB", "HUB", "MGH"}, new String[] {"AVA", "EEB", "3", "AVA", "HUB", "3", "EEB", "MGH", "1", "HUB", "MGH", "9"});
	    testEachGraph(new Vertex("AVA"), new Vertex("MGH"), g1); 
	    
	    // expected result from AVA -> MGH: 4 <both paths have the same weights>
	    System.out.println("**This tests two paths with equal weight**");
		MyGraph g2 = test(new String[] {"AVA", "EEB", "HUB", "MGH"}, new String[] {"AVA", "EEB", "3", "AVA", "HUB", "3", "EEB", "MGH", "1", "HUB", "MGH", "1"});
	    testEachGraph(new Vertex("AVA"), new Vertex("MGH"), g2); 
	    
	    // expected result from HUB -> AVA: Does not exist
	    System.out.println("**Test an non-existing edge**");
		MyGraph g3 = test(new String[] {"AVA", "EEB", "HUB"}, new String[] {"AVA", "EEB", "3", "EEB", "HUB", "1"});
	    testEachGraph(new Vertex("HUB"), new Vertex("AVA"), g3); 
	    
	    // expected result from AVA -> AVA: 0
	    System.out.println("**Test self-edge vertex**");
		MyGraph g4 = test(new String[] {"AVA", "EEB"}, new String[] {"AVA", "EEB", "3", "AVA", "AVA", "4"});
	    testEachGraph(new Vertex("AVA"), new Vertex("AVA"), g4); 
	    
	    //************************Throw Exception case**********************************
//	    //expected result from AVA -> EEB: Throw an exception (same edge with different cost)
//	    System.out.println("This tests same edge with different weights");
//		MyGraph g4 = test(new String[] {"AVA", "EEB"}, new String[] {"AVA", "EEB", "3", "AVA", "EEB", "1"});
//	    testEachGraph(new Vertex("AVA"), new Vertex("EEB"), g4); 
	    
//	    //expected result from AVA -> EEB: Throw an exception (negative cost)
//		System.out.println("This tries to add a negative edge");
//		MyGraph g5 = test(new String[] {"AVA", "EEB"}, new String[] {"AVA", "EEB", "-3", "EEB", "AVA", "5"});
//	    testEachGraph(new Vertex("AVA"), new Vertex("EEB"), g5); 
	    
//	    //expected result from HUB -> NONE: Throw an exception (non-existing vertex)
//	    System.out.println("Test an non-existing vertex");
//		MyGraph g6 = test(new String[] {"AVA", "EEB", "HUB"}, new String[] {"AVA", "EEB", "3", "EEB", "HUB", "1"});
//	    testEachGraph(new Vertex("HUB"), new Vertex("NONE"), g6); 
	    
	    // *******************************TEST EDGES**************************************
	    System.out.println("Given 4 vertices: AVA, EEB, HUB, MGH");
	    Collection<Vertex> v = new HashSet<Vertex>();
	    Collection<Edge> e = new HashSet<Edge>();
	    Vertex AVA = new Vertex("AVA");
	    Vertex EEB = new Vertex("EEB");
	    Vertex HUB = new Vertex("HUB");
	    Vertex MGH = new Vertex("MGH");
	    
	    Edge e1 = new Edge(AVA, EEB, 15);
		Edge e2 = new Edge(AVA, HUB, 20);
		Edge e3 = new Edge(AVA, MGH, 3);
		Edge e4 = new Edge(MGH, EEB, 5);
		Edge e5 = new Edge(EEB, HUB, 9);
		
		v.add(AVA); v.add(EEB); v.add(HUB); v.add(MGH);
		e.add(e1); e.add(e2); e.add(e3); e.add(e4); e.add(e5);
		MyGraph g6 = new MyGraph(v, e);

		Collection<Vertex> vt = g6.vertices();
		Collection<Edge> ed = g6.edges();
		Path path_AVA2EEB = g6.shortestPath(AVA, EEB);
		// test vertices
		System.out.println("Size of the graph: "+ vt.size());
		System.out.println("Edges are: "+ ed);
		// test adjacentVertices
		System.out.println("How many adjacent vertices of AVA: "+ g6.adjacentVertices(AVA).size() + " vertice(s)");
		System.out.println("Adjacent vertices of AVA: "+ g6.adjacentVertices(AVA));
		System.out.println("How many adjacent vertices of EEB: "+ g6.adjacentVertices(EEB).size() + " vertice(s)");
		System.out.println("Adjacent vertices of EEB: "+ g6.adjacentVertices(EEB));
		System.out.println("How many adjacent vertices of HUB: "+ g6.adjacentVertices(HUB).size() + " vertice(s)");
		System.out.println("Adjacent vertices of HUB: "+ g6.adjacentVertices(HUB));
		System.out.println("How many adjacent vertices of MGH: "+ g6.adjacentVertices(MGH).size() + " vertice(s)");
		System.out.println("Adjacent vertices of MGH: "+ g6.adjacentVertices(MGH));
		System.out.println("Smallest cost: " + g6.edgeCost(AVA, EEB));
		System.out.println("Shortest path: " + path_AVA2EEB.vertices);
	}
	
	public static MyGraph test (String[] vertexInput, String[] edgeInput) {
		MyGraph g = buildGraph(vertexInput, edgeInput);
		Collection<Vertex> vertex = g.vertices();
        Collection<Edge> edge = g.edges();
		System.out.println("Vertices are " + vertex);
		System.out.println("Edges are " + edge);
		return g; 
	}
	
	public static MyGraph buildGraph(String[] vertexInput, String[] edgeInput) {
		Collection<Vertex> v = new ArrayList<Vertex>();
		for (int i = 0; i < vertexInput.length; i++) {
			v.add(new Vertex(vertexInput[i]));
		}
		
		Collection<Edge> e = new ArrayList<Edge>();
		for (int i = 0; i < edgeInput.length; i= i+3) {
			Vertex a = new Vertex(edgeInput[i]);
			Vertex b = new Vertex(edgeInput[i+1]);
			int w = Integer.parseInt(edgeInput[i+2]);
			e.add(new Edge(a,b,w));
		}
		return new MyGraph(v, e); 
	}

	public static void testEachGraph(Vertex a, Vertex b, MyGraph g) {
		Path p = g.shortestPath(a, b);
		System.out.print("Shortest path from " + a.toString() + " to " + b.toString() + " : ");
		if (p == null) { // if shortestPath returns a null: means that there b
							// is not reachable from a
			System.out.println("does not exist");
		} else { // else it has the shortest path
			for (Vertex step : p.vertices) {
				System.out.print(step.getLabel() + " ");
			}
			System.out.println("with the cost of " + p.cost);
		}	
		System.out.println("--------------------------------------------------------------");
	}
}
