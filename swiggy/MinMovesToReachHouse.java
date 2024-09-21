/*
Problem link - https://www.hackerrank.com/challenges/food-delivery-service/problem?isFullScreen=true&h_l=interview&playlist_slugs%5B%5D=swiggy
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
import org.apache.commons.lang3.tuple.Pair;

class Result {

    /*
     * Complete the 'solve' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER n
     *  2. INTEGER x
     */
    
    
    public static int solve(int n, int x) {
        return BFS(n, x);
    }
    
    private static int BFS(int n, int x) {
        int[] moves = new int[n + 1];
        Arrays.fill(moves, (int) 1e9);
        
        PriorityQueue<Pair<Integer, Integer>> queue = new PriorityQueue<>((a, b) -> a.getValue() - b.getValue());
    
        queue.add(Pair.of(1, 0));
        while(!queue.isEmpty()) {
            Pair<Integer, Integer> pair = queue.poll();
            int cur = pair.getKey();
            int dist = pair.getValue();
            
            moves[cur] = dist;
            int[] next = { cur - 1, cur + 1, cur * 2, cur % 2 == 0 ? cur/2 : -1, sort(cur) };
            for(int val : next) {
                if(val >= 1 && val <= n && moves[val] > 1 + dist) {
                    moves[val] = 1 + dist;
                    queue.add(Pair.of(val, 1 + dist));
                }
            }
        }
        return moves[x];
    }
    
    private static int sort(int n) {
        List<Integer> digits = new ArrayList<>();
        while(n > 0) {
            int rem = n % 10;
            digits.add(rem);
            n /= 10;
        }
        
        Collections.sort(digits, (a, b) -> b - a);
        int ans = 0;
        for(int digit : digits) {
            ans = ans * 10 + digit;
        }
        return ans;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int t = Integer.parseInt(bufferedReader.readLine().trim());

        IntStream.range(0, t).forEach(tItr -> {
            try {
                String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

                int n = Integer.parseInt(firstMultipleInput[0]);

                int x = Integer.parseInt(firstMultipleInput[1]);

                int result = Result.solve(n, x);

                bufferedWriter.write(String.valueOf(result));
                bufferedWriter.newLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        bufferedReader.close();
        bufferedWriter.close();
    }
}
