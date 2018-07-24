import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * Created by iid on 3/4/18.
 */
public class RandomizedQueueTest {

    @Test(expected = IllegalArgumentException.class)
    public void testEnqueueNull(){
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        queue.enqueue(null);
    }

    @Test
    public void testEnqueue(){
        String s = "string";
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        assertEquals(0, queue.size());
        assertTrue(queue.isEmpty());
        queue.enqueue(s);
        assertEquals(1, queue.size());
        assertFalse(queue.isEmpty());
        queue.enqueue(s);
//        assertEquals(2, queue.getLength());
        queue.enqueue(s);
//        assertEquals(4, queue.getLength());
        assertEquals(3, queue.size());
        queue.enqueue(s);
        queue.enqueue(s);
//        assertEquals(8, queue.getLength());
        assertEquals(5, queue.size());
    }

    @Test(expected = NoSuchElementException.class)
    public void testDequeue(){
        String s = "string";
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        assertEquals(0, queue.size());
        assertTrue(queue.isEmpty());
        queue.enqueue(s);
        assertEquals(1, queue.size());
        assertFalse(queue.isEmpty());
        queue.enqueue(s);
//        assertEquals(2, queue.getLength());
        queue.enqueue(s);
//        assertEquals(4, queue.getLength());
        assertEquals(3, queue.size());
        assertNotNull(queue.dequeue());
//        assertEquals(4, queue.getLength());
        assertEquals(2, queue.size());
        assertNotNull(queue.dequeue());
        assertEquals(1, queue.size());
//        assertEquals(2, queue.getLength());
        assertNotNull(queue.dequeue());
        assertEquals(0, queue.size());
        assertTrue(queue.isEmpty());
//        assertEquals(2, queue.getLength());
        assertNotNull(queue.dequeue());
    }

    @Test
    public void testSample(){
        String s = "string";
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        assertEquals(0, queue.size());
        assertTrue(queue.isEmpty());
        queue.enqueue(s);
        assertEquals(1, queue.size());
        assertFalse(queue.isEmpty());
        queue.enqueue(s);
//        assertEquals(2, queue.getLength());
        queue.enqueue(s);
//        assertEquals(4, queue.getLength());
        assertEquals(3, queue.size());
        assertNotNull(queue.sample());
        assertNotNull(queue.sample());
        assertNotNull(queue.sample());
//        assertEquals(4, queue.getLength());
        assertEquals(3, queue.size());
    }

    @Test
    public void testIterator(){
        String s = "string";
        int i = 0;
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        queue.enqueue(s + i++);
        queue.enqueue(s + i++);
        queue.enqueue(s + i++);
        Iterator<String> it = queue.iterator();
        assertTrue(it.hasNext());
        assertNotEquals(it.next(), it.next());
        assertNotNull(it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testTwoIterators(){
        String s = "string";
        int i = 0;
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        queue.enqueue(s + i++);
        queue.enqueue(s + i++);
        queue.enqueue(s + i++);
        queue.enqueue(s + i++);
        queue.enqueue(s + i++);
        Iterator<String> it = queue.iterator();
        Iterator<String> it2 = queue.iterator();
        StringBuilder sb = new StringBuilder();
        sb.append(it.next()).append(it.next()).append(it.next()).append(it.next()).append(it.next());
        StringBuilder sb2 = new StringBuilder();
        sb2.append(it2.next()).append(it2.next()).append(it2.next()).append(it2.next()).append(it2.next());
        assertNotEquals(sb, sb2);
    }
}
