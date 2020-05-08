import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("ForLoopReplaceableByForEach")
public class HeapTests {

    @Test
    public void success() {
        assertTrue(true);
    }

    @Test
    public void indexTests() {
        int[][] array = new int[][] {
                new int[] {0, 1, 2},
                new int[] {1, 3, 4},
                new int[] {2, 5, 6},
                new int[] {6, 13, 14},
        };

        for (int i = 0; i < array.length; i++) {
            int[] row = array[i];

            int parent = row[0];
            int leftChild = row[1];
            int rightChild = row[2];

            assertEquals(leftChild, Heap.leftChildIndex(parent), "Parent.LeftChild");
            assertEquals(rightChild, Heap.rightChildIndex(parent), "Parent.RightChild");

            assertEquals(parent, Heap.parentIndex(leftChild), "LeftChild.Parent");
            assertEquals(parent, Heap.parentIndex(rightChild), "RightChild.Parent");
        }
    }

    @Test
    public void test001() {
        Heap<Character> minHeap = new Heap<Character>(20);

        minHeap.insert('1');
        minHeap.insert('2');
        minHeap.dump();

        minHeap.insert('5');
        minHeap.insert('6');
        minHeap.dump();
        System.out.println();

        minHeap.insert('3');
        minHeap.insert('4');
        minHeap.dump();
        System.out.println();

        minHeap.insert('0');
        minHeap.dump();
        System.out.println();

        assertEquals('0', minHeap.remove());
        minHeap.dump();
        System.out.println();
        assertEquals('1', minHeap.remove());
        assertEquals('2', minHeap.remove());
        assertEquals('3', minHeap.remove());
        assertEquals('4', minHeap.remove());
        assertEquals('5', minHeap.remove());
        assertEquals('6', minHeap.remove());
    }
}
