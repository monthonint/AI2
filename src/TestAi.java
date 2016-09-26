/**
 * Created by month on 25/8/2559.
 */
import java.util.*;

public class TestAi implements Runnable {
    static View testview1;
    static View testviewgoal;

    static {
        char[][] data = {{'2', ' ', '1'}, {'3', '5', '4'}, {'7', '8', '6'}};
        //char[][] data = {{'1', '2', '3'}, {'4', '5', '6'}, {'7', ' ', '8'}};
        char[][] goal = {{'1', '2', '3'}, {'4', '5', '6'}, {'7', '8', ' '}};
        testview1 = new View(data);
        testviewgoal = new View(goal);
        Scanner input = new Scanner(System.in);
        System.out.print("Do you want to set table (y,n)? : ");
        char userinput = input.next().charAt(0);
        if(userinput=='y'){
            String number;
            while(true){
                number = input.nextLine();
                if(number.length()==9)
                    break;
                else
                    System.out.print("Put number 0-8 ex.012345678 : ");
            }
            int tempcount = 0;
            for(int i=0;i<data.length;i++){
                for(int j=0;j<data[i].length;j++){
                    if(number.charAt(tempcount)=='0'){
                        data[i][j] = ' ';
                    }
                    else{
                        data[i][j] = number.charAt(tempcount);
                    }
                    tempcount+=1;
                }
            }
            testview1.setValue(data);
        }
        else{
            testview1.setValue(randomtable(testview1.getTable()));
        }
        System.out.println("tablecurrent");
        printTable(-1, testview1.getTable());
        System.out.println("tablegoal");
        printTable(-1, testviewgoal.getTable());
//        System.out.println("heuristic_h1 : " + heuristic_h1(testview1, testviewgoal));
//        System.out.println("heuristic_h2 : " + heuristic_h2(testview1, testviewgoal));
    }

    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();
        //Do search and get last child answer Node
        Node lastnode_answer = search(testview1);
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println((totalTime / (1000 * 60)) + " min. " + ((totalTime / (1000)) % 60) + " sec. " + (totalTime % 1000) + " millisec.");
        System.out.println((totalTime) + " millisec.");

        ArrayList<Node> answerlist = new ArrayList<>();
        //put every Node into a single list
        while (lastnode_answer != null) {
            answerlist.add(lastnode_answer);
            //printTable(++i, currentNode.getCurrentView().getTable());
            lastnode_answer = lastnode_answer.getParent();
        }
        //get actions order by first to last actions
        for(int i = answerlist.size()-1; i >= 0; i--){
            char action = answerlist.get(i).getPerformed_action();
            System.out.print(action + " ");
        }
        System.out.println();
        int counter = 0;
        //print table on the screen, show how moves lead to solution.
        for(int i = answerlist.size()-1; i >= 0; i--){
            printTable(counter++, answerlist.get(i).getCurrentView().getTable());
        }

    }

    public static void printTable(int time, char[][] table) {
        System.out.println("Time : " + time);
        System.out.print(" ");
        for (int i = 0; i < table.length; i++)
            System.out.print("- ");
        System.out.println(" ");
        for (int i = 0; i < table.length; i++) {
            System.out.print("|");
            for (int j = 0; j < table[i].length; j++) {
                System.out.print(table[i][j]);
                System.out.print("|");
            }
            System.out.println();
            System.out.print(" ");
            for (int k = 0; k < table.length; k++)
                System.out.print("- ");
            System.out.println(" ");
        }
    }

    public static int[] findblank(char[][] table) {
        int coordinate[] = new int[2];
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                if (table[i][j] == ' ') {
                    coordinate[0] = i;
                    coordinate[1] = j;
                    return coordinate;
                }
            }
        }
        return null;
    }

    public static char[][] slideUp(char[][] table) {
        char[][] new_table = new char[3][3];
        for (int i = 0; i < table.length; i++) {
            new_table[i] = table[i].clone();
        }
        int coordinate[] = findblank(new_table);
        int x = coordinate[0];
        int y = coordinate[1];
        if (x > 0) {
            char temp = new_table[x - 1][y];
            new_table[x - 1][y] = ' ';
            new_table[x][y] = temp;
        }
        return new_table;
    }

    public static View slideUp(View view) {
        View new_View = new View(view);
        int x = view.getBlank_position_x();
        int y = view.getBlank_position_y();
        if (y <= 0) {
            System.out.println("slideUp error!");
            return null;
        } else {
            char[][] table = new_View.getTable();
            char temp = table[--y][x];  //Get the character that is being moved
            table[y][x] = ' ';          //Put a new blank
            new_View.setBlank_position_y(y);
            table[++y][x] = temp;       //Put a character into a new place
            new_View.setTable(table);
            return new_View;
        }
    }

    public static char[][] slideDown(char[][] table) {
        char[][] new_table = new char[3][3];
        for (int i = 0; i < table.length; i++) {
            new_table[i] = table[i].clone();
        }
        int coordinate[] = findblank(new_table);
        int x = coordinate[0];
        int y = coordinate[1];
        if (x < 2) {
            char temp = new_table[x + 1][y];
            new_table[x + 1][y] = ' ';
            new_table[x][y] = temp;
        }
        return new_table;
    }

    public static View slideDown(View view) {
        View new_View = new View(view);
        int x = view.getBlank_position_x();
        int y = view.getBlank_position_y();
        if (y >= 2) {
            System.out.println("slideDown error!");
            return null;
        } else {
            char[][] table = new_View.getTable();
            char temp = table[++y][x];  //Get the character that is being moved
            table[y][x] = ' ';          //Put a new blank
            new_View.setBlank_position_y(y);
            table[--y][x] = temp;       //Put a character into a new place
            new_View.setTable(table);
            return new_View;
        }
    }

    public static char[][] slideLeft(char[][] table) {
        char[][] new_table = new char[3][3];
        for (int i = 0; i < table.length; i++) {
            new_table[i] = table[i].clone();
        }
        int coordinate[] = findblank(new_table);
        int x = coordinate[0];
        int y = coordinate[1];
        if (y > 0) {
            char temp = new_table[x][y - 1];
            new_table[x][y - 1] = ' ';
            new_table[x][y] = temp;
        }
        return new_table;
    }

    public static View slideLeft(View view) {
        View new_View = new View(view);
        int x = view.getBlank_position_x();
        int y = view.getBlank_position_y();
        if (x <= 0) {
            System.out.println("slideLeft error!");
            return null;
        } else {
            char[][] table = new_View.getTable();
            char temp = table[y][--x];  //Get the character that is being moved
            table[y][x] = ' ';          //Put a new blank
            new_View.setBlank_position_x(x);
            table[y][++x] = temp;       //Put a character into a new place
            new_View.setTable(table);
            return new_View;
        }
    }

    public static char[][] slideRight(char[][] table) {
        char[][] new_table = new char[3][3];
        for (int i = 0; i < table.length; i++) {
            new_table[i] = table[i].clone();
        }
        int coordinate[] = findblank(new_table);
        int x = coordinate[0];
        int y = coordinate[1];
        if (y < 2) {
            char temp = new_table[x][y + 1];
            new_table[x][y + 1] = ' ';
            new_table[x][y] = temp;
        }
        return new_table;
    }

    public static View slideRight(View view) {
        View new_View = new View(view);
        int x = view.getBlank_position_x();
        int y = view.getBlank_position_y();
        if (x >= 2) {
            System.out.println("slideRight error!");
            return null;
        } else {
            char[][] table = new_View.getTable();
            char temp = table[y][++x];  //Get the character that is being moved
            table[y][x] = ' ';          //Put a new blank
            new_View.setBlank_position_x(x);
            table[y][--x] = temp;       //Put a character into a new place
            new_View.setTable(table);
            return new_View;
        }
    }

    //random
    public static char[][] randomtable(char[][] table) {
        Random random = new Random(Calendar.getInstance().getTimeInMillis());
        int round = (int) ((random.nextDouble() * 1000) + 1);
        for (int i = 0; i < round; i++) {
            int direction = (int) ((random.nextDouble() * 100) + 1);
            if (direction <= 25)
                table = slideRight(table);
            else if (direction > 25 && direction <= 50)
                table = slideDown(table);
            else if (direction > 50 && direction <= 75)
                table = slideLeft(table);
            else
                table = slideUp(table);
        }
        return table;
    }

    /**
     * Using heuristic functions to search for a solution
     * @param initialState initial state of 8 puzzle to be solve.
     * @return latest Node of the solution
     */
    public static Node search(View initialState) {
        //Create an initial Node from an initial data
        int[] positions = findblank(initialState.getTable());
        initialState.setBlank_position_y(positions[0]);
        initialState.setBlank_position_x(positions[1]);
        Node initialNode = new Node(initialState, null, ' ', 0);
        initialNode.setWeight(heuristic_h1(initialState, TestAi.testviewgoal) + heuristic_h2(initialState, TestAi.testviewgoal));

        //PriorityQueue help sorting Node(View) by their weight, lowest is poll out first.
        PriorityQueue<Node> queue = new PriorityQueue(new HeuristicComparator());
        queue.add(initialNode);
        Node currentNode = null;
        View currentState = null;

        //Continue expand into directions until solution is found
        while (queue.size() > 0) {
            currentNode = queue.poll();
            currentState = currentNode.getCurrentView();
            if(isGoal(currentState)) break;
            int y = currentState.getBlank_position_y();
            int x = currentState.getBlank_position_x();
            if(y > 0) {
                queue.add(expand(currentNode, 'u'));
//                printTable(1, queue.poll().getCurrentView().getTable());
            }
            if(y < 2) {
                queue.add(expand(currentNode, 'd'));
//                printTable(2, queue.poll().getCurrentView().getTable());
            }
            if(x > 0) {
                queue.add(expand(currentNode, 'l'));
//                printTable(3, queue.poll().getCurrentView().getTable());
            }
            if(x < 2) {
                queue.add(expand(currentNode, 'r'));
//                printTable(4, queue.poll().getCurrentView().getTable());
            }
        }
        return currentNode;
    }
    //get performActions

    /**
     * expand current Node (which has a state inside) into directions and return a new Node if success
     * @param parentNode    current Node to be expand
     * @param action        directions which blank position should move
     * @return              Node contains a new state(table - view)
     */
    public static Node expand(Node parentNode, char action) {
        View new_View;
        switch (action) {
            case 'u':
                new_View = slideUp(parentNode.getCurrentView());
                break;
            case 'd':
                new_View = slideDown(parentNode.getCurrentView());
                break;
            case 'l':
                new_View = slideLeft(parentNode.getCurrentView());
                break;
            case 'r':
                new_View = slideRight(parentNode.getCurrentView());
                break;
            default:
                new_View = null;
        }
        if(new_View != null) {
            Node currentNode = new Node(new_View, parentNode, action, parentNode.getLevel() + 1);
            return currentNode;
        }
        else return null;
    }

    /**
     * This is to be used to check whether the input state is reach the goal state, by compare every tile.
     *
     * @param currentState current view (state) of the puzzle.
     * @return true, if the input state reaches the goal state.
     */
    public static boolean isGoal(View currentState) {
        if(currentState == null) return false;
        char[][] currentState_table = currentState.getTable();
        if (currentState_table[0][0] != '1') return false;
        else if (currentState_table[0][1] != '2') return false;
        else if (currentState_table[0][2] != '3') return false;
        else if (currentState_table[1][0] != '4') return false;
        else if (currentState_table[1][1] != '5') return false;
        else if (currentState_table[1][2] != '6') return false;
        else if (currentState_table[2][0] != '7') return false;
        else if (currentState_table[2][1] != '8') return false;
        else if (currentState_table[2][2] != ' ') return false;
        else return true;
    }

    public static ArrayList<Character> swapaction(ArrayList<Character> actionlist) {
        ArrayList<Character> swap = new ArrayList<>();
        for (int i = actionlist.size() - 1; i >= 0; i--) {
            char tempaction = actionlist.remove(i);
            swap.add(tempaction);
        }
        return swap;
    }

    public static void printaction(String word, ArrayList<Character> actionlist) {
        System.out.print(word + " : ");
        for (int i = 0; i < actionlist.size(); i++) {
            System.out.print(actionlist.get(i) + " ");
        }
        System.out.println();
    }

    public static void printalltable(ArrayList<Character> actionlist, View current_view) {
        int time = 0;
        printTable(time, current_view.getTable());
        for (Character action : actionlist) {
            if (action == 'u')
                current_view = new View(slideUp(current_view.getTable()));
            else if (action == 'd')
                current_view = new View(slideDown(current_view.getTable()));
            else if (action == 'l')
                current_view = new View(slideLeft(current_view.getTable()));
            else if (action == 'r')
                current_view = new View(slideRight(current_view.getTable()));
            time += 1;
            printTable(time, current_view.getTable());
        }
    }

    public static int heuristic_h1(View current_view, View goal_view) {
        int counterror = 0;
        for (int i = 0; i < goal_view.getTable().length; i++) {
            for (int j = 0; j < goal_view.getTable()[i].length; j++) {
                if (current_view.getTable()[i][j] != goal_view.getTable()[i][j]) {
                    counterror += 1;
                }
            }
        }
        return counterror;
    }

    public static int[] findpositionnumber(View view, char number) {
        int[] position = new int[2];
        for (int i = 0; i < view.getTable().length; i++) {
            for (int j = 0; j < view.getTable()[i].length; j++) {
                if (view.getTable()[i][j] == number) {
                    position[0] = i;
                    position[1] = j;
                    break;
                }
            }
        }
        return position;
    }

    public static int heuristic_h2(View current_view, View goal_view) {
        int sumofdiatance = 0;
        int count = 0;
        for (int i = 0; i < goal_view.getTable().length; i++) {
            for (int j = 0; j < goal_view.getTable()[i].length; j++) {
                int[] position_current;
                int[] position_goal;
                if (count == 0) {
                    position_current = findpositionnumber(current_view, ' ');
                    position_goal = findpositionnumber(goal_view, ' ');
                } else {
                    position_current = findpositionnumber(current_view, (char) ('0' + count));
                    position_goal = findpositionnumber(goal_view, (char) ('0' + count));
                }
                //System.out.println(count+" : x : "+Math.abs(position_current[0]-position_goal[0])+" : y : "+Math.abs(position_current[1]-position_goal[1]));
                sumofdiatance += (Math.abs(position_current[0] - position_goal[0]) + Math.abs(position_current[1] - position_goal[1]));
                count += 1;
            }
        }
        return sumofdiatance;
    }

    public void run() {
        search(testview1);
    }

}
