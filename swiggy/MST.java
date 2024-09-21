/*
Problem link - https://www.hackerrank.com/challenges/mohit-and-swiggy/problem?h_l=interview&isFullScreen=true&playlist_slugs%5B%5D=swiggy
*/

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class Edge {
    int u, v, weight;
    
    Edge(int u, int v, int weight) {
        this.u = u;
        this.v = v;
        this.weight = weight;
    }
}

class UnionFind {
    private int n;
    private int[] rank;
    private int[] root;
    
    UnionFind(int n) {
        this.n = n;
        init();
    }
    
    private void init() {
        this.root = new int[n];
        this.rank = new int[n];
        for(int i = 0; i < n; i++) {
            root[i] = i;
            rank[i] = 1;
        }
    }
    
    public void union(int u, int v) {
        int rootU = find(u);
        int rootV = find(v);
        
        if(rootU == rootV) return ;
        
        root[rootU] = rootV;
        if(rank[rootU] < rank[rootV]) {
            root[rootU] = rootV;
        } else if(rank[rootV] < rank[rootU]) {
            root[rootV] = rootU;
        } else {
            root[rootU] = rootV;
            rank[rootU]++;
        }
    }
    
    public int find(int x) {
        if(root[x] == x) {
            return x;
        }
        return root[x] = find(root[x]);
    }
    
    public boolean connected(int u, int v) {
        return find(u) == find(v);
    }
}

class Graph {
    private int n;
    private Map<Integer, List<Edge>> edges;

    public Graph(int n) {
        this.n = n;
        init();
    }    
    
    private void init() {
        this.edges = new HashMap<>();
        for(int i = 0; i < n; i++) {
            edges.put(i, new ArrayList<>());
        }
    }
    
    public void addEdge(int u, int v, int weight) {
        Edge e1 = new Edge(u, v, weight);
        // Edge e2 = new Edge(v, u, weight);
        
        edges.get(u).add(e1);
        // edges.get(v).add(e2);
    }
    
    private PriorityQueue<Edge> createEdgeHeap() {
        PriorityQueue<Edge> edgeHeap = new PriorityQueue<>((a, b) -> a.weight - b.weight);
        for(int node : edges.keySet()) {
            for(Edge edge : edges.get(node)) {
                edgeHeap.add(edge);
            }
        }
        return edgeHeap;
    }
    
    public int minimumDistance() {
        PriorityQueue<Edge> edgeHeap = createEdgeHeap();
        UnionFind uf = new UnionFind(n);
        
        int distance = 0;
        int count = 0;
        while(count < n - 1) {
            Edge edge = edgeHeap.poll();
            int u = edge.u;
            int v = edge.v;
            int weight = edge.weight;
            if(uf.connected(edge.u, edge.v)) continue;
            
            uf.union(u, v);
            distance += weight;
            count++;
        }
        return 2 * distance;
    }
}

class Result {

    /*
     * Complete the 'solve' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER n
     *  2. 2D_INTEGER_ARRAY roads
     */

    public static int solve(int n, List<List<Integer>> roads) {
        Graph graph = new Graph(n + 1);
        for(List<Integer> road : roads) {
            int u = road.get(0), v = road.get(1), weight = road.get(2);
            
            graph.addEdge(u, v, weight);
        }
        
        return graph.minimumDistance();
    }

}

public class MST {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int n = Integer.parseInt(firstMultipleInput[0]);

        int m = Integer.parseInt(firstMultipleInput[1]);

        List<List<Integer>> roads = new ArrayList<>();

        IntStream.range(0, m).forEach(i -> {
            try {
                roads.add(
                    Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                        .map(Integer::parseInt)
                        .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        int result = Result.solve(n, roads);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
