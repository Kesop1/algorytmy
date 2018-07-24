import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by iid on 3/24/18.
 */
public class Board {

    private final int[] board1d;
    private int manhattan;
    private int dimension;
    private Board[] neighbors;
    private Board twin;

    /**
     * construct a board1d from an n-by-n array of blocks
     * (where blocks[i][j] = block in row i, column j)
     * the constructor receives an n-by-n array containing the n2 integers between 0 and n2 − 1, where 0 represents the blank square.
     * The constructor should throw a java.lang.IllegalArgumentException if passed a null argument.
     * @param blocks
     */
    public Board(int[][] blocks){
        if(blocks==null){
            throw new java.lang.IllegalArgumentException("Null blocks passed!!!");
        }
        board1d = convert2Dto1D(blocks);
        dimension = (int) Math.sqrt(board1d.length);
        neighbors = null;
    }

    /**
     * board1d dimension n
     * @return
     */
    public int dimension(){
        return dimension;
    }

    /**
     * number of blocks out of place
     * The number of blocks in the wrong position.
     * Intuitively, a search node with a small number of blocks in the wrong position is close to the goal,
     * and we prefer a search node that have been reached using a small number of moves.
     * @return
     */
    public int hamming(){
        int bad = 0;
        for(int i=0; i<board1d.length; i++){
            if(board1d[i]==0){
                continue;
            }
            if(board1d[i]!=i+1){
                bad++;
            }
        }
        return bad;
    }

    /**
     * sum of Manhattan distances between blocks and goal
     * The sum of the Manhattan distances (sum of the vertical and horizontal distance) from the blocks to their goal positions.
     * To avoid recomputing the Manhattan priority of a search node from scratch each time during various priority queue operations,
     * pre-compute its value when you construct the search node; save it in an instance variable; and return the saved value as needed.
     * @return
     */
    public int manhattan(){
        if(manhattan==0){
            manhattan = calculateManhattan();
        }
        return manhattan;
    }

    private int calculateManhattan(){
        int bad = 0;
        for(int i=0; i<board1d.length; i++){
            if(board1d[i]==0){
                continue;
            }
//            int val = board1d[i];
//            int curRow = (i/dimension())+1;
//            int expRow = val/dimension();
//            int curCol = (i+1)%dimension();
//            int expCol = val%dimension();
//            int pos = Math.abs(curRow-expRow) + Math.abs(curCol-expCol);
            bad+=getDistance(i);
        }
        return bad;
    }

    /**
     * create neighboring boards with Zero moving around up to 4 directions
     * manhattan is automatically recalculated when new Board is created
     * @param zeroPoition
     * @return
     */
    private Board[] createNeighbors(int zeroPoition){
        if(board1d[zeroPoition]!=0){
            throw new java.lang.IllegalArgumentException("Can only create neighbors next to a Zero block!!!");
        }
        ArrayList<Board> boards = new ArrayList<Board>(0);
        int zeroCol = getCol(zeroPoition);
        int zeroRow = getRow(zeroPoition);
        if(zeroCol>0){ //go left
            boards.add(swap(zeroPoition, zeroPoition-1));
        }
        if(zeroCol<(dimension()-1)){
            boards.add(swap(zeroPoition, zeroPoition+1));
        }
        if(zeroRow>0){
            boards.add(swap(zeroPoition, zeroPoition-dimension()));
        }
        if(zeroRow<dimension()-1){
            boards.add(swap(zeroPoition, zeroPoition+dimension()));
        }
        return boards.toArray(new Board[0]);
    }

    /**
     * get element's distance from it's desired position
     * @param i
     * @return
     */
    private int getDistance(int i){
        int val = board1d[i];
        int expRow = getRow(val-1);
        int curRow = getRow(i);
        int expCol = getCol(val-1);
        int curCol = getCol(i);
        return Math.abs(curRow-expRow) + Math.abs(curCol-expCol);
    }

    private int getRow(int a){
        return a/dimension();
    }

    private int getCol(int a){
        return a%dimension();
    }

    /**
     * swap two elements in the board and return the new board
     * any of the arguments can be Zero element or not
     * calculate manhattan for the board
     * @param a
     * @param b
     * @return
     */
    private Board swap(int a, int b){
        if(a==b){
            throw new java.lang.IllegalArgumentException("Why swapping the element with itself???");
        }
        int[][] seq = convert1Dto2D();
        int aRow = getRow(a);
        int bRow = getRow(b);
        int aCol = getCol(a);
        int bCol = getCol(b);
        int c = seq[aRow][aCol];
        seq[aRow][aCol] = seq[bRow][bCol];
        seq[bRow][bCol] = c;
        Board board = new Board(seq);
        board.manhattan = manhattan();
        if(board1d[a]==0){
            //if element's distance is now smaller
            if(getDistance(b)>board.getDistance(a)){
                board.manhattan--;
            }else{
                board.manhattan++;
            }
        }else if(board1d[b]==0){
            if(getDistance(a)>board.getDistance(b)){
                board.manhattan--;
            }else{
                board.manhattan++;
            }
        }
        //if none of the swapping elements is Zero recalculate manhattan from scratch, used for twin() method
        else{
            board.manhattan = board.calculateManhattan();
        }

        return board;
    }

    /**
     * is this board the goal board?
     * @return
     */
    public boolean isGoal(){
        return manhattan()==0;
    }

    /**
     * a board that is obtained by exchanging any pair of non-zero blocks
     * @return
     */
    public Board twin(){
        if(twin==null){
            twin = generateTwin();
        }
        return twin;
    }

    private Board generateTwin(){
        int a;
        int b;
        do {
            a = StdRandom.uniform(board1d.length);
            b = StdRandom.uniform(board1d.length);
        }
        while (b == a || board1d[b] == 0 || board1d[a] == 0);
        Board board = swap(a, b);
        return board;
    }

    /**
     * does this board equal y?
     * @param y
     * @return
     */
    public boolean equals(Object y){
        if(this==y) {
            return true;
        }
        if (y==null) {
            return false;
        }
        if (y.getClass() != this.getClass()) {
            return false;
        }
        Board that = (Board) y;
        return this.toString().equals(that.toString());
    }

    /**
     * all neighboring boards
     * @return
     */
    public Iterable<Board> neighbors() {
        if (neighbors == null) {
        neighbors = createNeighbors(findZero());
    }
        return new BoardIterable();
    }

    private class BoardIterable implements Iterable<Board>{

        public Iterator<Board> iterator() {
            return new BoardIterator();
        }
    }

    private class BoardIterator implements Iterator<Board>{

        private int n;

        private BoardIterator(){
            n=0;
        }

        public boolean hasNext() {
            return n<neighbors.length;
        }

        public Board next() {
            return neighbors[n++];
        }

        public void remove() {
            //invalid
        }
    }

    /**
     * get position of the element Zero
     * @return
     */
    private int findZero(){
        int pos = 0;
        for(int i=0; i<board1d.length; i++){
            if(board1d[i]==0){
                pos = i;
                break;
            }
        }
        return pos;
    }

    /**
     * convert sequence of blocks to 2D array
     * @return
     */
    private int[][] convert1Dto2D(){
        int[][] twoDimensions = new int[dimension()][dimension()];
        int pos = 0;
        for(int i=0; i<dimension(); i++){
            for(int j=0; j<dimension(); j++){
                twoDimensions[i][j] = board1d[pos++];
            }
        }
        return twoDimensions;
    }

    /**
     * convert sequence of blocks to 1D array
     * @return
     */
    private int[] convert2Dto1D(int[][] blocks){
        int[] board1D = new int[blocks.length*blocks.length];
        int x = 0;
        for(int i=0; i<blocks.length; i++){
            for( int j=0; j<blocks[i].length; j++){
                board1D[x++] = blocks[i][j];
            }
        }
        return board1D;
    }

    /**
     * string representation of this board1d (in the output format specified below)
     * @return
     */

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(dimension());
        for (int i = 0; i < board1d.length; i++) {
            if(i%dimension()==0) {
                s.append("\n");
            }
            s.append(String.format("%2d ", board1d[i]));
        }
        return s.toString();
    }
}
