import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

/**
 * Created by iid on 3/24/18.
 */
public class Solver {

    private int moves;
    private boolean solvable;
    private Node last;
    private final Node first;
    private Board[] boards;


    /**
     * find a solution to the initial board (using the A* algorithm)
     * compute the priority function in Solver by calling hamming() or manhattan() and adding to it the number of moves.
     * To implement the A* algorithm, you must use MinPQ from algs4.jar for the priority queue(s).
     * The constructor should throw a java.lang.IllegalArgumentException if passed a null argument.
     * @param initial
     */
    public Solver(Board initial){
        if(initial==null){
            throw new java.lang.IllegalArgumentException("Null board passed");
        }
        int moves = 0;
        initial.manhattan();
        first = new Node(initial, null, moves);
        checkBoardAndTwin();
    }

    /**
     * check symultanously if the initial board and its twin is solvable
     * if twin is then the original is not
     */
    private void checkBoardAndTwin(){
        MinPQ<Node> pq = new MinPQ<Node>();
        MinPQ<Node> pqTwin = new MinPQ<Node>();
        Board boardTwin = first.board.twin();
        Node nodeTwin = new Node(boardTwin, null, 0);
        Node node = first;
        pq.insert(node);
        pqTwin.insert(nodeTwin);

        do{
            node = nextMove(pq);
            nodeTwin = nextMove(pqTwin);

        }while(!node.board.isGoal() && !nodeTwin.board.isGoal());
        if(nodeTwin.board.isGoal()){ //twin is solvable, so the original cannot be
            solvable = false;
        }
        else {
            last = node;
            this.moves = last.moves;
            solvable = true;
        }
    }

    private Node nextMove(MinPQ<Node> pq){
        Node node = pq.delMin();
        Iterable<Board> boards = node.board.neighbors();
        for (Board board : boards) {
            Node node1 = new Node(board, node, node.moves+1);
            if((node.previous==null) || (node.previous!=null && !node1.board.equals(node.previous.board))){   //if new board is not equal to previous board
                pq.insert(node1);
            }
        }
        return node;
    }

    private class Node implements Comparable<Node>{

        private Board board;
        private int moves;
        private Node previous;
        private int manhattan;
        private int priority;

        private Node(Board b, Node p, int m){
            board = b;
            previous = p;
            moves = m;
            manhattan = b.manhattan();
            priority = manhattan + moves;
        }

        public int compareTo(Node that) {
            if(this.priority>(that.priority)){
                return 1;
            }else if(this.priority<(that.priority)){
                return -1;
            }else{
                if(this.manhattan>that.manhattan){
                    return 1;
                }else if(this.manhattan<that.manhattan){
                    return -1;
                }else{
                    return 0;
                }
            }

        }
    }

    /**
     * is the initial board solvable?
     * run the A* algorithm on two puzzle instances—one with the initial board and one with the initial
     * board modified by swapping a pair of blocks
     * @return
     */

    public boolean isSolvable(){
        return solvable;
    }

    /**
     * min number of moves to solve initial board; -1 if unsolvable
     * @return
     */
    public int moves(){
        if(!solvable)
            return -1;
        return moves;
    }

    /**
     * sequence of boards in a shortest solution; null if unsolvable
     * @return
     */
    public Iterable<Board> solution(){
        if(!solvable) {
            return null;
        }
        int i=moves+1;
        boards = new Board[i];
        Node node = last;
        while (node.previous!=null){
            boards[--i] = node.board;
            node = node.previous;
        }
        boards[--i]=first.board;

        return new Iterable<Board>() {
            public Iterator<Board> iterator() {
                return new BoardIterator();
            }
        };
    }

    private class BoardIterator implements Iterator<Board>{

        private int n;

        private BoardIterator(){
            n=0;
        }

        public boolean hasNext() {
            return n<boards.length;
        }

        public Board next() {
            return boards[n++];
        }

        public void remove() {
            //invalid
        }
    }
    int j = st.length();
    char temp = 0;
        for(int i=0; i<st.length(); i++){
        if(!stay[i]){
            while(j>=1 && !stay[--j]){
                temp = str[i];
                str[i] = str[j];
                str[j] = temp;
                stay[j] = true;
            }
        }
    }
        return new String(str);

    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
