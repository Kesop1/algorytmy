/**
 * Created by iid on 3/11/18.
 */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

/**
 * a client program Permutation.java that takes an integer k as a command-line argument;
 * reads in a sequence of strings from standard input using StdIn.readString(); and prints exactly k of them,
 * uniformly at random. Print each item from the sequence at most once.
 * 0 ≤ k ≤ n, where n is the number of string on standard input.
 */
public class Permutation {

    public static void main(String[] args){
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            queue.enqueue(s);
        }
        if(k<=queue.size()){
            Iterator<String> it = queue.iterator();
            for(int i = 0; i<k; i++) {
                StdOut.println(it.next());
            }
        }
    }

}
