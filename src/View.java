/**
 * Created by month on 25/8/2559.
 */
public class View {
    private char[][] table = new char[3][3];
    public View(char[][] table){
        this.table = table;
    }
    public View(){
        setValueInitial();
    }
    public char[][] getTable() {
        return table;
    }
    private void setValueInitial(){
        this.table = new char[][]{{'1', '2', '3'}, {'4', '5', '6'}, {'7', '8', ' '}};
    }
    public void setValue(char[][] tableoutside){
                table = tableoutside;
    }
    public boolean compareTo(View compareView){
        char[][] currentState_table = compareView.getTable();
        if(currentState_table[0][0] != table[0][0]) return false;
        else if(currentState_table[0][1] != table[0][1]) return false;
        else if(currentState_table[0][2] != table[0][2]) return false;
        else if(currentState_table[1][0] != table[1][0]) return false;
        else if(currentState_table[1][1] != table[1][1]) return false;
        else if(currentState_table[1][2] != table[1][2]) return false;
        else if(currentState_table[2][0] != table[2][0]) return false;
        else if(currentState_table[2][1] != table[2][1]) return false;
        else if(currentState_table[2][2] != table[2][2]) return false;
        else return true;
    }
}
