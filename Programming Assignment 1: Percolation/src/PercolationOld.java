/**
 * Created by iid on 2/11/18.
 */
import edu.princeton.cs.algs4.StdRandom;


public class PercolationOld {

    private boolean debug = false;
    private int openNumber = 0;
    private int[][] root;

    private boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public PercolationOld(int n){
        if(n<=0)
            throw new IllegalArgumentException();
        this.root = new int[n][n];
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                this.root[i][j] = 0;
            }
        }
    }

    public void open(int row, int col){
        if(row<1 || col<1 || row>this.root.length-1 || col>this.root[0].length-1)
            throw new IllegalArgumentException();
        if(!isOpen(row, col)) {
            if (isDebug())
                System.out.println(row + ",\t" + col + "\topened");
            setRoot(row, col);
            openNumber++;
        }else{
            if (isDebug())
                System.out.println(row + ",\t" + col + "\tis already opened");
        }
    }

    public boolean isOpen(int row, int col){
        if(row<1 || col<1 || row>this.root.length-1 || col>this.root[0].length-1)
            throw new IllegalArgumentException();
        boolean open = false;
        if(this.root[row][col]>0)
            open = true;
        if(isDebug()) {
            String result = open ? "\tis open" : "\tis closed";
            System.out.println(row + ",\t" + col + result);
        }
        return open;
    }

    public boolean isFull(int row, int col){
        if(row<1 || col<1 || row>this.root.length-1 || col>this.root[0].length-1)
            throw new IllegalArgumentException();
        boolean closed = false;
        if(this.root[row][col]==0)
            closed = true;
        if(isDebug()) {
            String result = closed ? "\tis closed" : "\tis open";
            System.out.println(row + ",\t" + col + result);
        }
        return closed;
    }

    public int numberOfOpenSites(){
        return this.openNumber;
    }

    public boolean percolates(){
        for(int i = 1; i<this.root.length-1; i++){
            if(this.root[this.root.length-2][i]==1){
                return true;
            }
        }
        return false;
    }

    private void setRoot(int row, int col){
        if(isDebug()) {
            System.out.println("Setting root for \t" + row + "\t" + col);
        }
        this.root[row][col] = row;
        //get top
        for(int i=0; i<2; i++) {
            if (row > 1) {
                if (checkTop(row, col)) {
                    setRoot(row - 1, col);
                }
            }
            //get right
            if (col < this.root[0].length - 2) {
                if (checkRight(row, col)) {
                    setRoot(row, col + 1);
                }
            }
            //get left
            if (col > 1) {
                if (checkLeft(row, col)) {
                    setRoot(row, col - 1);
                }
            }
            //get bottom
            if (row < this.root.length - 2) {
                if (checkBottom(row, col)) {
                    setRoot(row + 1, col);
                }
            }
        }
    }



    private int getRoot(int row, int col){
        return this.root[row][col];
    }

    private boolean checkTop(int row, int col){
        if(isDebug()) {
            System.out.println("Checking top");
        }
        if (isOpen(row - 1, col)) {
            if(getRoot(row, col)< getRoot(row-1, col)){
                if(isDebug()) {
                    System.out.println("Top's root is higher");
                }
                return true;
            }else {
                this.root[row][col] = getRoot(row - 1, col);
                if (isDebug()) {
                    System.out.println("Root set to \t" + getRoot(row - 1, col));
                }
            }
        }
        return false;
    }

    private boolean checkRight(int row, int col){
        if(isDebug()) {
            System.out.println("Checking right");
        }
        if (isOpen(row, col+1)) {
            if(getRoot(row, col+1)<=getRoot(row, col)){
                this.root[row][col] = getRoot(row, col+1);
                if(isDebug()) {
                    System.out.println("Root set to \t" + getRoot(row, col + 1));
                }
            }else{
                if(isDebug()) {
                    System.out.println("Right's root is higher");
                }
                return true;
            }
        }
        return false;
    }

    private boolean checkLeft(int row, int col){
        if(isDebug()) {
            System.out.println("Checking left");
        }
        if (isOpen(row, col-1)) {
            if(getRoot(row, col-1)<=getRoot(row, col)){
                this.root[row][col] = getRoot(row, col-1);
                if(isDebug()) {
                    System.out.println("Root set to \t" + getRoot(row, col-1));
                }
            }else{
                if(isDebug()) {
                    System.out.println("Left's root is higher");
                }
                return true;
            }
        }
        return false;
    }

    private boolean checkBottom(int row, int col){
        if(isDebug()) {
            System.out.println("Checking bottom");
        }
        if (isOpen(row+1, col)) {
            if(getRoot(row+1, col)>getRoot(row, col)){
                if(isDebug()) {
                    System.out.println("Bottom's root is higher");
                }
                return true;
            } else{
                this.root[row][col] = getRoot(row+1, col);
            }
        }
        return false;
    }






    public static void main(String[] args){
        int n = 1000;
        PercolationOld p = new PercolationOld(n+2);
        p.setDebug(false);
        do{
//            p.printRoot();
            int x = StdRandom.uniform(n);
            int y = StdRandom.uniform(n);
            p.open(x+1, y+1);
            p.setRoot(x+1, y+1);
//            System.out.println("////////////////////////////////////////////////////////////////");
        }while (!p.percolates());
        System.out.println("Processing finished. Number of open sites= "+p.numberOfOpenSites());
//        p.printRoot();
    }

    private void printRoot(){
        System.out.println("+++++++++++++++++++++++++");
        for(int[] i: root){
            for(int j: i){
                System.out.print(j + "\t");
            }
            System.out.println();
        }
        System.out.println("+++++++++++++++++++++++++");
    }
}
