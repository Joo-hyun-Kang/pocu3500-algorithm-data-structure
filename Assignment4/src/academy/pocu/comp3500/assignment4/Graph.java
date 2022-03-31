package academy.pocu.comp3500.assignment4;// Java program to check if a given directed graph is strongly
// connected or not
import java.util.*;
import java.util.LinkedList;

// This class represents a directed graph using adjacency
// list representation
public class Graph
{
    private int V;   // No. of vertices
    private LinkedList<Integer> adj[]; //Adjacency List

    //Constructor
    public Graph(int v)
    {
        V = v;
        adj = new LinkedList[v];
        for (int i=0; i<v; ++i)
            adj[i] = new LinkedList();
    }

    //Function to add an edge into the graph
    public void addEdge(int v, int w) {  adj[v].add(w); }

    // A recursive function to print DFS starting from v
    void DFSUtil(int v,Boolean visited[])
    {
        // Mark the current node as visited and print it
        visited[v] = true;

        int n;

        // Recur for all the vertices adjacent to this vertex
        Iterator<Integer> i = adj[v].iterator();
        while (i.hasNext())
        {
            n = i.next();
            if (!visited[n])
                DFSUtil(n,visited);
        }
    }

    // Function that returns transpose of this graph
    Graph getTranspose()
    {
        Graph g = new Graph(V);
        for (int v = 0; v < V; v++)
        {
            // Recur for all the vertices adjacent to this vertex
            Iterator<Integer> i = adj[v].listIterator();
            while (i.hasNext())
                g.adj[i.next()].add(v);
        }
        return g;
    }

    // The main function that returns true if graph is strongly
    // connected
    public Boolean isSC()
    {
        // Step 1: Mark all the vertices as not visited
        // (For first DFS)
        Boolean visited[] = new Boolean[V];
        for (int i = 0; i < V; i++)
            visited[i] = false;

        // Step 2: Do DFS traversal starting from first vertex.
        DFSUtil(0, visited);

        // If DFS traversal doesn't visit all vertices, then
        // return false.
        for (int i = 0; i < V; i++)
            if (visited[i] == false)
                return false;

        // Step 3: Create a reversed graph
        Graph gr = getTranspose();

        // Step 4: Mark all the vertices as not visited (For
        // second DFS)
        for (int i = 0; i < V; i++)
            visited[i] = false;

        // Step 5: Do DFS for reversed graph starting from
        // first vertex. Starting Vertex must be same starting
        // point of first DFS
        gr.DFSUtil(0, visited);

        // If all vertices are not visited in second DFS, then
        // return false
        for (int i = 0; i < V; i++)
            if (visited[i] == false)
                return false;

        return true;
    }

    public static void main(String args[])
    {

    }
}
// This code is contributed by Aakash Hasija