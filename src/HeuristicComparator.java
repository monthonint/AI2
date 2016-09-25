import java.util.Comparator;

/**
 * Created by Mineralmink on 25/9/2559.
 */
public class HeuristicComparator implements Comparator<Node> {
    @Override
    public int compare(Node o1, Node o2) {
        return o1.getWeight()-o2.getWeight();
    }
}
