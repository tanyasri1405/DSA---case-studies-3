import java.util.*;

public class DSURollbackConnectivity {

    static class DSU {

        int[] parent;
        int[] rank;
        Stack<int[]> history;

        DSU(int n) {

            parent = new int[n];
            rank = new int[n];
            history = new Stack<>();

            for (int i = 0; i < n; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
        }

        int find(int x) {

            while (parent[x] != x) {
                x = parent[x];
            }

            return x;
        }

        boolean union(int a, int b) {

            int rootA = find(a);
            int rootB = find(b);

            if (rootA == rootB) {

                history.push(new int[]{-1, -1, -1});
                return false;
            }

            if (rank[rootA] < rank[rootB]) {

                int temp = rootA;
                rootA = rootB;
                rootB = temp;
            }

            history.push(
                    new int[]{
                            rootB,
                            rootA,
                            rank[rootA]
                    });

            parent[rootB] = rootA;

            if (rank[rootA] == rank[rootB]) {
                rank[rootA]++;
            }

            return true;
        }

        void rollback() {

            if (history.isEmpty())
                return;

            int[] state = history.pop();

            if (state[0] == -1)
                return;

            parent[state[0]] = state[0];
            rank[state[1]] = state[2];
        }
    }

    public static void main(String[] args) {

        System.out.println("AIRLINE CONNECTIVITY ANALYSIS");
        System.out.println();

        DSU dsu = new DSU(8);

        System.out.println("Creating Airline Routes...");
        dsu.union(0, 1);
        dsu.union(1, 2);
        dsu.union(2, 3);

        System.out.println();
        System.out.println("Connectivity Check");

        boolean connected =
                dsu.find(0) == dsu.find(3);

        System.out.println(
                "Connected (0,3): "
                        + connected);

        System.out.println();
        System.out.println("Performing Rollback...");

        dsu.rollback();

        System.out.println();

        connected =
                dsu.find(0) == dsu.find(3);

        System.out.println(
                "Connected (0,3): "
                        + connected);

        System.out.println();
        System.out.println("TIME COMPLEXITY");
        System.out.println("Find      : O(log n)");
        System.out.println("Union     : O(log n)");
        System.out.println("Rollback  : O(1)");
    }
}