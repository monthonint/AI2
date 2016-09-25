
/**
 * Created by Nut on 8/23/2016 AD.
 */
public class Node {
    View currentView;
    Node parent;
    char performed_action;
    int weight;
    int level;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public char getPerformed_action() {
        return performed_action;
    }

    public void setPerformed_action(char performed_action) {
        this.performed_action = performed_action;
    }

    public Node(View currentView, Node parent, char performed_action, int weight, int level) {
        this.currentView = currentView;
        this.parent = parent;
        this.performed_action = performed_action;
        this.weight = weight;
        this.level = level;
    }

    public Node(View currentView, Node parent, char performed_action, int level) {
        this.currentView = currentView;
        this.parent = parent;
        this.performed_action = performed_action;
        this.level = level;
        this.weight = level*1;
        this.weight += TestAi.heuristic_h1(currentView, TestAi.testviewgoal);
        this.weight += TestAi.heuristic_h2(currentView, TestAi.testviewgoal);

    }

    public View getCurrentView() {
        return currentView;
    }

    public void setCurrentView(View currentView) {
        this.currentView = currentView;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

}
