/**
 * Created by month on 25/8/2559.
 */
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class TestAi implements Runnable{
    private static volatile boolean finished = false;
    static View testview1;
    private static volatile ArrayList<Integer> level = new ArrayList<>();
    private static volatile boolean lock = false;
    private static volatile boolean checklist = false;
    private static volatile ArrayList<Character> listaction = new ArrayList<>();
    static{
        for(int i = 0; i < 100; i++){
            level.add(i);
        }
        char[][] data = {{'6','4','7'},{'8','5',' '},{'3','2','1'} };
        testview1 = new View(data);
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
        //testview1 = new View(data);
       //testview1.setValue(randomtable(testview1.getTable()));
        printTable(-1,testview1.getTable());
    }
    public void run(){
        if(!finished) {
            if (level.size() > 0) {
                accuireLock();
                int current_level = level.remove(0);
                release();
                search(testview1, current_level);
                System.out.println("Level " + current_level + " searched");
                if(!finished) new Thread(new TestAi()).start();
            }
        }
    }
    public static void main(String[] args) {

            long startTime = System.currentTimeMillis();
            new Thread(new TestAi()).start();
            while (true) {
                if (checklist) {
                    break;
                }
            }
            listaction = swapaction(listaction);
            printalltable(listaction, testview1);
            long endTime = System.currentTimeMillis();
            long totalTime = endTime - startTime;
            System.out.println((totalTime / (1000 * 60)) + " min. " + ((totalTime / (1000)) % 60) + " sec. " + (totalTime % 1000) + " millisec.");
            System.out.println((totalTime) + " millisec.");

    }

    public static void printTable(int time,char[][] table){
        System.out.println("Time : "+time);
        System.out.print(" ");
        for(int i = 0;i<table.length;i++)
            System.out.print("- ");
        System.out.println(" ");
        for(int i = 0;i<table.length;i++){
            System.out.print("|");
            for(int j = 0;j<table[i].length;j++){
                System.out.print(table[i][j]);
                System.out.print("|");
            }
            System.out.println();
            System.out.print(" ");
            for(int k = 0;k<table.length;k++)
                System.out.print("- ");
            System.out.println(" ");
        }
    }
    public static int[] findblank(char[][] table){
        int coordinate[] = new int[2];
        for(int i=0;i<table.length;i++){
            for(int j=0;j<table[i].length;j++){
                if(table[i][j]==' '){
                    coordinate[0] = i;
                    coordinate[1] = j;
                    return coordinate;
                }
            }
        }
        return null;
    }
    public static char[][] slideUp(char[][] table){
        char[][] new_table = new char[3][3];
        for(int i = 0; i < table.length; i++){
            new_table[i] = table[i].clone();
        }
        int coordinate[] =  findblank(new_table);
        int x = coordinate[0];
        int y = coordinate[1];
        if(x>0){
            char temp = new_table[x-1][y];
            new_table[x-1][y] = ' ';
            new_table[x][y] = temp;
        }
        return new_table;
    }
    public static char[][] slideDown(char[][] table){
        char[][] new_table = new char[3][3];
        for(int i = 0; i < table.length; i++){
            new_table[i] = table[i].clone();
        }
        int coordinate[] =  findblank(new_table);
        int x = coordinate[0];
        int y = coordinate[1];
        if(x<2){
            char temp = new_table[x+1][y];
            new_table[x+1][y] = ' ';
            new_table[x][y] = temp;
        }
        return new_table;
    }
    public static char[][] slideLeft(char[][] table){
        char[][] new_table = new char[3][3];
        for(int i = 0; i < table.length; i++){
            new_table[i] = table[i].clone();
        }
        int coordinate[] =  findblank(new_table);
        int x = coordinate[0];
        int y = coordinate[1];
        if(y>0){
            char temp = new_table[x][y-1];
            new_table[x][y-1] = ' ';
            new_table[x][y] = temp;
        }
        return new_table;
    }
    public static char[][] slideRight(char[][] table){
        char[][] new_table = new char[3][3];
        for(int i = 0; i < table.length; i++){
            new_table[i] = table[i].clone();
        }
        int coordinate[] =  findblank(new_table);
        int x = coordinate[0];
        int y = coordinate[1];
        if(y<2){
            char temp = new_table[x][y+1];
            new_table[x][y+1] = ' ';
            new_table[x][y] = temp;
        }
        return new_table;
    }
    //random
    public static char[][] randomtable(char[][] table){
        Random random = new Random(Calendar.getInstance().getTimeInMillis());
        int round = (int)((random.nextDouble()*1000)+1);
        for(int i=0;i<round;i++){
            int direction = (int)((random.nextDouble()*100)+1);
            if(direction<=25)
                table = slideRight(table);
            else if(direction>25&&direction<=50)
                table = slideDown(table);
            else if(direction>50&&direction<=75)
                table = slideLeft(table);
            else
                table = slideUp(table);
        }
        return table;
    }
    //get performActions
    /**
     * This is the blind search limit depth first search
     * @param initialState  initial state of 8 puzzle to be solve.
     * @return  ArrayList of char of actions to be perform.
     */
    public static void search(View initialState, int levellimit){
        System.out.println("Current level limit: " + levellimit);

        if(isGoal(initialState)) System.out.println("Initial state is goal state.");
        else if(levellimit > 0 && !finished) {
            Node initialNode = new Node(initialState, null, ' ');
            int[] cooridinate = findblank(initialState.getTable());
            int x = cooridinate[1];
            int y = cooridinate[0];
            if (y > 0) {  //able to slide up
                expand(levellimit, 1, initialNode, 'u');
            }
            if (y < 2) {  //able to slide down
                expand(levellimit, 1, initialNode, 'd');
            }
            if (x > 0) {  //able to slide left
                expand(levellimit, 1, initialNode, 'l');
            }
            if (x < 2) {  //able to slide right
                expand(levellimit, 1, initialNode, 'r');
            }
        }
    }

    public static boolean expand(int levellimit, int depth, Node parentNode, char action){
        if(finished) return false;
        View newView = new View(parentNode.getCurrentView().getTable());
        switch (action){
            case 'u':
                newView = new View(slideUp(newView.getTable()));
                break;
            case 'd':
                newView = new View(slideDown(newView.getTable()));
                break;
            case 'l':
                newView = new View(slideLeft(newView.getTable()));
                break;
            case 'r':
                newView = new View(slideRight(newView.getTable()));
                break;
        }
        Node currentNode = new Node(newView, parentNode, action);
        int[] cooridinate = findblank(newView.getTable());
        int x = cooridinate[1];
        int y = cooridinate[0];
        if(depth < levellimit/* && !finished*/){
            ++depth;
            if(y > 0 && action != 'd'){  //able to slide up
                expand(levellimit, depth, currentNode, 'u');
            }
            if(y < 2 && action != 'u'){  //able to slide down
                expand(levellimit, depth, currentNode, 'd');
            }
            if(x > 0 && action != 'r'){  //able to slide left
                expand(levellimit, depth, currentNode, 'l');
            }
            if(x < 2 && action != 'l'){  //able to slide right
                expand(levellimit, depth, currentNode, 'r');
            }
        }
        else{
            if(isGoal(currentNode.getCurrentView())/* && !finished*/){
                ArrayList<Character> answerlist = new ArrayList<>();
                while(currentNode.getParent() != null){
                    answerlist.add(currentNode.getPerformed_action());
                    currentNode = currentNode.getParent();
                }
                //if(finished) return false;
                finished = true;
                System.out.print("Answer found!!! @level " + depth + " : ");
                //File file = new File("file.txt");
                //FileWriter writer = new FileWriter(file, true);
                for(int i = answerlist.size()-1; i >= 0; i--){
                    System.out.print(answerlist.get(i) + " ");
                    //writer.append(answerlist.get(i) + " ");
                }
                /*if(listaction.size() > 0 && answerlist.size() < listaction.size()) listaction = answerlist;
                else*/
                listaction = answerlist;
                //writer.append('\n');
                //writer.close();
                System.out.println();
                //System.exit(99);
                checklist = true;
                return true;
            }
            else return false;
        }
        return false;
    }

    /**
     * This is to be used to check whether the input state is reach the goal state, by compare every tile.
     * @param currentState  current view (state) of the puzzle.
     * @return  true, if the input state reaches the goal state.
     */
    public static boolean isGoal(View currentState){
        char[][] currentState_table = currentState.getTable();
        if(currentState_table[0][0] != '1') return false;
        else if(currentState_table[0][1] != '2') return false;
        else if(currentState_table[0][2] != '3') return false;
        else if(currentState_table[1][0] != '4') return false;
        else if(currentState_table[1][1] != '5') return false;
        else if(currentState_table[1][2] != '6') return false;
        else if(currentState_table[2][0] != '7') return false;
        else if(currentState_table[2][1] != '8') return false;
        else if(currentState_table[2][2] != ' ') return false;
        else return true;
    }

    public void release(){
        lock = false;
    }

    public void accuireLock(){
        while(!lock){
            if(!lock) lock=true;
        }
    }
    public static ArrayList<Character> swapaction(ArrayList<Character> actionlist){
        ArrayList<Character> swap = new ArrayList<>();
        for(int i=actionlist.size()-1;i>=0;i--){
            char tempaction = actionlist.remove(i);
            swap.add(tempaction);
        }
        return swap;
    }
    public static void printaction(String word,ArrayList<Character> actionlist){
        System.out.print(word + " : ");
        for(int i=0;i<actionlist.size();i++){
            System.out.print(actionlist.get(i)+" ");
        }
        System.out.println();
    }
    public static void printalltable(ArrayList<Character> actionlist, View current_view){
        int time = 0;
        printTable(time,current_view.getTable());
        for(Character action : actionlist){
            if(action=='u')
                current_view = new View(slideUp(current_view.getTable()));
            else if(action=='d')
                current_view = new View(slideDown(current_view.getTable()));
            else if(action=='l')
                current_view = new View(slideLeft(current_view.getTable()));
            else if(action=='r')
                current_view = new View(slideRight(current_view.getTable()));
            time += 1;
            printTable(time,current_view.getTable());
        }
    }
}
