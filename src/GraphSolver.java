import java.util.*;

public class GraphSolver {
    private final Map<String, List<Edge>> adjList = new HashMap<>();
    private final double MI_TO_KM = 1.60934;

    public void addEdge(String from, String to, double value, String unit) {
        double dist = unit.equalsIgnoreCase("mi") ? value * MI_TO_KM : value;
        adjList.computeIfAbsent(from, k -> new ArrayList<>()).add(new Edge(to, dist));
    }

    public void solveDijkstra(String start, String end, String label) {
        System.out.println("Dijkstra pro: " + label);
        compute(start, end, false);
    }

    public void solveAStar(String start, String end, String label) {
        System.out.println("A* pro: " + label);
        compute(start, end, true);
    }

    private double h(String current, String goal) {
        return 0;
    }

    private void compute(String start, String end, boolean useHeuristic) {
        PriorityQueue<Node> open = new PriorityQueue<>();
        Map<String, Double> minCost = new HashMap<>();

        double startH = useHeuristic ? h(start, end) : 0;
        open.add(new Node(start, 0, startH, null));

        Node targetNode = null;

        while (!open.isEmpty()) {
            Node current = open.poll();

            if (current.code.equals(end)) {
                targetNode = current;
                break;
            }

            if (minCost.containsKey(current.code) && minCost.get(current.code) <= current.g) continue;
            minCost.put(current.code, current.g);

            for (Edge edge : adjList.getOrDefault(current.code, new ArrayList<>())) {
                double newG = current.g + edge.weight;
                double f = newG + (useHeuristic ? h(edge.target, end) : 0);
                open.add(new Node(edge.target, newG, f, current));
            }
        }
        printResult(targetNode);
    }

    private void printResult(Node target) {
        if (target == null) {
            System.out.println("Cesta nenalezena.");
            return;
        }
        List<Node> path = new ArrayList<>();
        Node temp = target;
        while (temp != null) {
            path.add(0, temp);
            temp = temp.parent;
        }

        for (int i = 0; i < path.size(); i++) {
            Node n = path.get(i);
            if (i == 0) System.out.print(n.code);
            else {
                double diff = n.g - path.get(i-1).g;
                System.out.print(String.format(" -> %s(%.2f, %.2f)", n.code, n.g, diff));
            }
        }
        System.out.println("\nCelková délka: " + String.format("%.2f km", target.g));
        System.out.println(" ");

    }
}