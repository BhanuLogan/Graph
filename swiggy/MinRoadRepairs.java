/*
Problem link - https://www.hackerrank.com/challenges/road-repairing/problem?isFullScreen=true&h_l=interview&playlist_slugs%5B%5D=swiggy

Some of the roads in a state have been damaged due to recent flood. 
Your task is to repair just enough roads such that each city in the state is connected to every other city. 
You are given the list of functional roads and damaged roads. 
Each input line will contain the id of the road and two city which it connects. The roads are bidirectional.

Input Format

The first line contains an integer , denoting the number of cities.
The second line contains an integer , denoting the number of functional roads.
The next  lines contains two integers describing the endpoints (u, v) of each road. The m + 2 line contains an integer , 
denoting the number of damaged roads. The next  lines contains two integers describing the endpoints (u, v) of each road.

It is guaranteed that roads are different in the input.
Output Format

If answer doesn't exist, print -1.
Otherwise, print the minimum numbers of reconstructed roads such that every two cities connect to each other.

Sample Input 0
4
2
1 2
2 3
2
3 4
1 4

Sample Output 0
1

Explanation 0
In this example, city 1, 2 and 3 are connected to each other by the functional roads and we can reach city 4 by repairing any one of the damaged roads.

Sample Input 1
5
0
5
3 5
3 4
1 2
1 3
1 1

Sample Output 1
4

Explanation 1

In this example, there are no functional roads. We repair all the damaged roads except the last one which connects city 1 to itself.
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
        this.root = new int[n + 1];
        this.rank = new int[n + 1];
        for(int i = 1; i <= n; i++) {
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
    
    public int connectedComponents() {
        Set<Integer> set = new HashSet<>();
        for(int i = 1; i <= n; i++) {
            set.add(find(i));
        }
        return set.size();
    }
}

class Result {

    /*
     * Complete the 'solve' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER t
     *  2. 2D_INTEGER_ARRAY functional
     *  3. 2D_INTEGER_ARRAY damaged
     */

    public static int solve(int t, List<List<Integer>> functional, List<List<Integer>> damaged) {
        UnionFind uf = new UnionFind(t);
        for(List<Integer> road : functional) {
            int u = road.get(0), v = road.get(1);
            uf.union(u, v);
        }        
        
        int connectedComponents = uf.connectedComponents();
        
        for(List<Integer> road : damaged) {
            int u = road.get(0), v = road.get(1);
            uf.union(u, v);
        }
        if(uf.connectedComponents() > 1) return -1;
        
        return connectedComponents - 1;
    }

}

public class MinRoadRepairs {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int t = Integer.parseInt(bufferedReader.readLine().trim());

        int m = Integer.parseInt(bufferedReader.readLine().trim());

        List<List<Integer>> functional = new ArrayList<>();

        IntStream.range(0, m).forEach(i -> {
            try {
                functional.add(
                    Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                        .map(Integer::parseInt)
                        .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<List<Integer>> damaged = new ArrayList<>();

        IntStream.range(0, n).forEach(i -> {
            try {
                damaged.add(
                    Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                        .map(Integer::parseInt)
                        .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        int result = Result.solve(t, functional, damaged);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
