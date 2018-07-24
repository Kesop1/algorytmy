import edu.princeton.cs.algs4.StdRandom;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by iid on 3/4/18.
 * A randomized queue is similar to a stack or queue, except that the item removed is chosen uniformly at random
 * from items in the data structure
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    /**
     * array of Items
     */
    private Item[] queue;


    /**
     * number of Items in the array
     */
    private int n;

    /**
     * do not add or remove items once an iterator is created
     */
    private boolean invalidateIterator;

    /**
     * construct an empty randomized queue
     */
    public RandomizedQueue(){
        queue = (Item[]) new Object[2];
        n = 0;
        invalidateIterator = true;
    }

    /**
     * is the randomized queue empty?
     * @return
     */
    public boolean isEmpty(){return n==0;}

    /**
     * return the number of items on the randomized queue
     * @return
     */
    public int size(){return n;}

    /**
     * add the item
     * Throw a java.lang.IllegalArgumentException if the client calls enqueue() with a null argument.
     * @param item
     */
    public void enqueue(Item item){
        if(item==null)
            throw new java.lang.IllegalArgumentException("cannot add null Item");
        if(n>=queue.length){
            boolean ok;
            int s = 1;
            do {
                s*=2;
                ok = resizeArray(queue.length * s);
            }while(!ok);
        }
        queue[n++] = item;
        invalidateIterator = true;
    }

    /**
     * remove and return a random item
     * Throw a java.util.NoSuchElementException if the client calls either sample() or dequeue() when the randomized queue is empty.
     * generate a pseudo-random integer between 0 and n−1 using StdRandom.uniform(n) from StdRandom.
     * @return
     */
    public Item dequeue(){
        if(size()==0)
            throw new java.util.NoSuchElementException("The queue is empty");
        Item item;
        int i;
        do{
            i = StdRandom.uniform(queue.length);
            item = queue[i];
        }while(item==null);
        queue[i] = null;
        n--;
        if(n>0 && n<=(queue.length/4)){
            resizeArray(queue.length/2);
        }
        invalidateIterator = true;
        return item;
    }

    /**
     * return a random item (but do not remove it)
     * Throw a java.util.NoSuchElementException if the client calls either sample() or dequeue() when the randomized queue is empty.
     * generate a pseudo-random integer between 0 and n−1 using StdRandom.uniform(n) from StdRandom.
     * @return
     */
    public Item sample(){
        if(size()==0)
            throw new java.util.NoSuchElementException("The queue is empty");
        Item item;
        int i;
        do{
            i = StdRandom.uniform(queue.length);
            item = queue[i];
        }while(item==null);
        return item;
    }

    /**
     * return an independent iterator over items in random order
     * Each iterator must return the items in uniformly random order.
     * The order of two or more iterators to the same randomized queue must be mutually independent;
     * each iterator must maintain its own random order.
     * Throw a java.util.NoSuchElementException if the client calls the next() method in the iterator when there
     * are no more items to return.
     * Throw a java.lang.UnsupportedOperationException if the client calls the remove() method in the iterator.
     * generate a pseudo-random integer between 0 and n−1 using StdRandom.uniform(n) from StdRandom.
     * StdRandom.shuffle()
     * throw a java.lang.ConcurrentModificationException
     * @return
     */
    public Iterator<Item> iterator() {
        return new RandomQueueIterator();
    }

    private class RandomQueueIterator implements Iterator<Item>{

        private int[] randomQueue;

        private int p;

        private RandomQueueIterator(){
            randomQueue = new int[n];
            int s = 0;
            for(int i=0; i<queue.length; i++){
                if(queue[i]!=null){
                    randomQueue[s++] = i;
                }
            }
            p = 0;
            invalidateIterator = false;
            StdRandom.shuffle(randomQueue);
        }

        public boolean hasNext() {
            if(invalidateIterator)
                throw new ConcurrentModificationException();
            return p<n;
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            return queue[randomQueue[p++]];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * create a new array of newSize and copy all the elements removing the null objects
     * if newSize is less than the elements count, return false
     * @param newSize
     * @return
     */
    private boolean resizeArray(int newSize){
        if(newSize<n){
            return false;
        }
        Item[] currentArray = queue;
        queue = (Item[]) new Object[newSize];
        int index = 0;
        for(int i=0; i<currentArray.length; i++){
            if(currentArray[i]!=null){
                queue[index++] = currentArray[i];
            }
        }
        return true;
    }

    public static void main(String[] args){}
}
