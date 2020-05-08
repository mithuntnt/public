import java.util.ArrayList;

/**
 * Mean heap
 * https://www.tutorialspoint.com/data_structures_algorithms/heap_data_structure.htm
 * @param <T>
 */
public class Heap<T extends Comparable<T>> {
    public static final int ROOT_INDEX = 0;
    ArrayList<T> list;

    public Heap() {
        this.list = new ArrayList<T>();
    }

    public Heap(int initialSize) {
        this.list = new ArrayList<T>(initialSize);
    }

    protected static int parentIndex(int index) {
        if (ROOT_INDEX == index) {
            throw new RuntimeException("No parent for index 0");
        }

        return (index - 1) / 2;
    }

    protected static int leftChildIndex(int index) {
        return index * 2 + 1;
    }

    protected static int rightChildIndex(int index) {
        return index * 2 + 2;
    }

    public void insert(T n) {
        this.list.add(n);

        int currentIndex = this.list.size() - 1;
        T current = n;

        while (currentIndex != ROOT_INDEX) {
            int parentIndex = parentIndex(currentIndex);
            T parent = list.get(parentIndex);

            if (parent.compareTo(current) > 0) {
                // Min heap
                this.list.set(parentIndex, current);
                this.list.set(currentIndex, parent);

                currentIndex = parentIndex;
            } else {
                currentIndex = parentIndex;
                current = parent;
            }
        }
    }

    public T remove() {
        int size = this.list.size();

        if (size == 0) {
            throw new RuntimeException("Heap empty. Nothing to remove");
        }

        if (size == 1) {
            return this.list.remove(0);
        }

        T root = this.list.get(ROOT_INDEX);
        T last = this.list.remove(size - 1);

        size -= 1;
        this.list.set(ROOT_INDEX, last);

        int currentIndex = ROOT_INDEX;
        T current = last;

        while (true) {
            int leftIndex = leftChildIndex(currentIndex);
            int rightIndex = rightChildIndex(currentIndex);

            T leftChild = (leftIndex < size) ? this.list.get(leftIndex) : null;
            T rightChild = (rightIndex < size) ? this.list.get(rightIndex) : null;

            boolean swapLeft = leftChild != null && current.compareTo(leftChild) > 0;
            boolean swapRight = rightChild != null && current.compareTo(rightChild) > 0;

            if (swapLeft && swapRight) {
                // Pick only the bigger one
                if (leftChild.compareTo(rightChild) < 0) {
                    swapRight = false;
                } else {
                    swapLeft = false;
                }
            }

            if (swapLeft) {
                // Min hip
                this.list.set(currentIndex, leftChild);
                this.list.set(leftIndex, current);

                currentIndex = leftIndex;
                continue;
            }

            if (swapRight) {
                // Min hip
                this.list.set(currentIndex, rightChild);
                this.list.set(rightIndex, current);

                currentIndex = rightIndex;
                continue;
            }

            break;
        }

        return root;
    }

    public int size() {
        return this.list.size();
    }

    public void dump() {
        dump(0);
        System.out.println();
    }

    private void dump(int index) {
        if (index >= this.list.size()) {
            return;
        }

        System.out.print('[');
        System.out.print(this.list.get(index));
        System.out.print(' ');
        dump(leftChildIndex(index));
        System.out.print(' ');
        dump(rightChildIndex(index));
        System.out.print(']');
    }

    public static void main(String[] args) {
        System.out.println("Heap");
    }
}
