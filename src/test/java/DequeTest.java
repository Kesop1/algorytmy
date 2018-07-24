
import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by iid on 3/3/18.
 */
public class DequeTest {

    @Test
    public void testAddFirstToEmpty(){
        String s = "first";
        Deque<String> deque = new Deque<String>();
        assertTrue(deque.isEmpty());
        assertEquals(deque.size(), 0);
        deque.addFirst(s);
        assertEquals(deque.size(), 1);
        assertFalse(deque.isEmpty());
    }

    @Test
    public void testAddLastToEmpty(){
        String s = "last";
        Deque<String> deque = new Deque<String>();
        deque.addLast(s);
        assertEquals(deque.size(), 1);
        assertFalse(deque.isEmpty());
    }

    @Test
    public void testAddFirstAndFirst(){
        String f = "first";
        String s = "second";
        Deque<String> deque = new Deque<String>();
        deque.addFirst(f);
        deque.addFirst(s);
        assertEquals(2, deque.size());
        assertEquals(s, deque.removeFirst());
        assertEquals(1, deque.size());
    }

    @Test
    public void testAddFirstAndLast(){
        String f = "first";
        String s = "second";
        Deque<String> deque = new Deque<String>();
        deque.addFirst(f);
        deque.addLast(s);
        assertEquals(2, deque.size());
        assertEquals(s, deque.removeLast());
        assertEquals(1, deque.size());
        assertEquals(f, deque.removeLast());
        assertEquals(0, deque.size());
    }

    @Test
    public void testAddToTwoDeques(){
        Deque<String> deque1 = new Deque<String>();
        Deque<String> deque2 = new Deque<String>();
        String f = "first";
        deque1.addFirst(f);
        deque2.addLast(f);
        assertEquals(deque1.removeLast(), deque2.removeFirst());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExceptionOnAddNull() {
        Deque<String> deque = new Deque<String>();
        String s = null;
        deque.addFirst(s);
    }

    @Test(expected = NoSuchElementException.class)
    public void testExceptionOnRemoveFromEmpty() {
        Deque<String> deque = new Deque<String>();
        deque.removeFirst();
    }

    @Test(expected = NoSuchElementException.class)
    public void testIterator(){
        Deque<String> deque = new Deque<String>();
        String first = "first";
        String second = "second";
        String third = "third";
        String fourth = "fourth";
        deque.addFirst(first);
        deque.addLast(second);
        deque.addFirst(third);
        deque.addLast(fourth);
        Iterator<String> it = deque.iterator();
        assertEquals(third, it.next());
        assertEquals(first, it.next());
        assertEquals(second, it.next());
        assertEquals(fourth, it.next());
        assertFalse(it.hasNext());
        it.next();
    }

    @Test(expected = ConcurrentModificationException.class)
    public void testIteratorModified(){
        Deque<String> deque = new Deque<String>();
        String first = "first";
        String second = "second";
        String third = "third";
        String fourth = "fourth";
        deque.addFirst(first);
        deque.addLast(second);
        deque.addFirst(third);
        Iterator<String> it = deque.iterator();
        assertEquals(third, it.next());
        deque.addLast(fourth);
        assertEquals(first, it.next());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testIteratorRemove(){
        Deque<String> deque = new Deque<String>();
        Iterator<String> it = deque.iterator();
        it.remove();
    }

}
