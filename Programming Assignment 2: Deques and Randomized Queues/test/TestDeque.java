import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by iid on 3/3/18.
 */

public class TestDeque {

    @Test
    public static void testAddFirst(){
        String s = "first";
        Deque<String> deque = new Deque<>();
        deque.addFirst(s);
        assertEquals(deque.size(), 1);
        assertEquals(s, deque.removeFirst());
        assertTrue(deque.isEmpty());
    }

    @Test
    public static void testAddLastToEmpty(){
        String s = "last";
        Deque<String> deque = new Deque<>();
        assert deque.size()==0;
        assert deque.isEmpty();
        deque.addLast(s);
        assert deque.size()==1;
        assert s.equals(deque.removeLast());
        assert deque.isEmpty();
    }

}
