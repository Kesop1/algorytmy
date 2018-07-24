import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by iid on 3/3/18.
 */
public class Deque<Item> implements Iterable<Item> {

    /**
     * first Node
     */
    private Node firstNode;

    /**
     * Last Node
     */
    private Node lastNode;

    /**
     * size
     */
    private int size;

    /**
     * construct an empty deque
     */
    public Deque() {
        firstNode = null;
        lastNode = null;
        size = 0;
    }

    /**
     * is the deque empty?
     *
     * @return
     */
    public boolean isEmpty() {
        return size==0;
    }

    /**
     * return the number of items on the deque
     *
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * add the item to the front
     * Throw a java.lang.IllegalArgumentException if the client calls either addFirst() or addLast() with a null argument.
     *
     * @param item
     */
    public void addFirst(Item item) {
        if(item==null)
            throw new java.lang.IllegalArgumentException("cannot add a null item");
        Node node = new Node(item);
        if(isEmpty()){
            firstNode = node;
            lastNode = node;
        }else{
            Node previousFirst = firstNode;
            firstNode = node;
            firstNode.setNext(previousFirst);
            previousFirst.setPrevious(firstNode);
        }
        size++;
    }

    /**
     * add the item to the end
     * Throw a java.lang.IllegalArgumentException if the client calls either addFirst() or addLast() with a null argument.
     *
     * @param item
     */
    public void addLast(Item item) {
        if(item==null)
            throw new java.lang.IllegalArgumentException("cannot add a null item");
        Node node = new Node(item);
        if(isEmpty()){
            firstNode = node;
            lastNode = node;
        }else{
            Node previousLast = lastNode;
            lastNode = node;
            previousLast.setNext(lastNode);
            lastNode.setPrevious(previousLast);
        }
        size++;
    }

    /**
     * remove and return the item from the front
     * Throw a java.util.NoSuchElementException if the client calls either removeFirst() or removeLast when the deque is empty.
     *
     * @return
     */
    public Item removeFirst() {
        if(isEmpty())
            throw new java.util.NoSuchElementException("The deque is empty");
        Node currentFirst = firstNode;
        firstNode = currentFirst.getNext();
        firstNode.setPrevious(null);
        size--;
        return currentFirst.getItem();
    }

    /**
     * remove and return the item from the end
     * Throw a java.util.NoSuchElementException if the client calls either removeFirst() or removeLast when the deque is empty.
     *
     * @return
     */
    public Item removeLast() {
        if(isEmpty())
            throw new java.util.NoSuchElementException("The deque is empty");
        Node currentLast = lastNode;
        lastNode = currentLast.getPrevious();
        lastNode.setNext(null);
        size--;
        return currentLast.getItem();
    }

    /**
     * return an iterator over items in order from front to end
     * Throw a java.util.NoSuchElementException if the client calls the next() method in the iterator when there
     * are no more items to return.
     * Throw a java.lang.UnsupportedOperationException if the client calls the remove() method in the iterator.
     * throw a java.lang.ConcurrentModificationException as soon as this is detected. - if any item is added or
     * deleted after the iterator was created
     *
     * @return
     */
    public Iterator<Item> iterator() {
        return new LinkedListIterator();
    }

    public static void main(String[] args) {
    }

    private class LinkedListIterator implements Iterator<Item>{

        private Node currentNode;
        private final int iteratorSize;

        private LinkedListIterator(){
            currentNode = firstNode;
            iteratorSize = size;
        }

        @Override
        public boolean hasNext() {
            if(iteratorSize !=size)
                throw new ConcurrentModificationException();
            return currentNode!=null;
        }

        @Override
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            Item item = currentNode.getItem();
            currentNode = currentNode.getNext();
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private class Node{
        private final Item item;
        private Node next;
        private Node previous;

        private Node(Item item){
            this.item = item;
            next = null;
            previous = null;
        }

        private Node getPrevious() {
            return previous;
        }

        private void setPrevious(Node previous) {
            this.previous = previous;
        }

        private Item getItem() {
            return item;
        }

        private Node getNext() {
            return next;
        }

        private void setNext(Node next) {
            this.next = next;
        }
    }

}

