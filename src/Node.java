public class Node implements Comparable<Node> {
    String code;
    double g;
    double f;
    Node parent;

    public Node(String code, double g, double f, Node parent) {
        this.code = code;
        this.g = g;
        this.f = f;
        this.parent = parent;
    }

    @Override
    public int compareTo(Node o) {
        return Double.compare(this.f, o.f);
    }
}
