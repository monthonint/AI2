
/**
 * Created by Nut on 8/23/2016 AD.
 */
public class Node {
    View currentView;
    Node parent;
    char performed_action;

    public char getPerformed_action() {
        return performed_action;
    }

    public void setPerformed_action(char performed_action) {
        this.performed_action = performed_action;
    }

    public Node(View currentView, Node parent, char performed_action) {
        this.currentView = currentView;
        this.parent = parent;
        this.performed_action = performed_action;
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
