/**
 * Created by month on 25/8/2559.
 */
public class View {
    private char[][] table = new char[3][3];
    public int blank_position_x = 0;
    public int blank_position_y = 0;
    public View(char[][] table){
        this.table = table;
    }
    public View(View view){
        for(int i = 0; i < view.getTable().length; i++){
            this.table[i] = view.getTable()[i].clone();
        }
        this.blank_position_x = view.getBlank_position_x();
        this.blank_position_y = view.getBlank_position_y();
    }
    public View(){
        setValueInitial();
    }
    public char[][] getTable() {
        return table;
    }

    public void setTable(char[][] table) {
        this.table = table;
    }

    public int getBlank_position_x() {
        return blank_position_x;
    }

    public void setBlank_position_x(int blank_position_x) {
        this.blank_position_x = blank_position_x;
    }

    public int getBlank_position_y() {
        return blank_position_y;
    }

    public void setBlank_position_y(int blank_position_y) {
        this.blank_position_y = blank_position_y;
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
