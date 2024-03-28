import java.util.*;

class Heap {
    public int[] HeapArray;

    public Heap() {
        HeapArray = null;
    }

    public void MakeHeap(int[] a, int depth) {
        HeapArray = new int[calculateTreeSize(depth)];
        Arrays.fill(HeapArray, -1);
        for (int i = 0; i < a.length; i++) {
            Add(a[i]);
        }
    }

    public int GetMax() {
        if (HeapArray == null) return -1;
        return deleteMax();
    }

    public boolean Add(int key) {
        if (HeapArray[0] == -1) {
            HeapArray[0] = key;
            return true;
        }
        int indexOfLast = findIndexOfLast() + 1;
        if (HeapArray[HeapArray.length - 1] != -1 || indexOfLast >= HeapArray.length) return false;
        HeapArray[indexOfLast] = key;
        insertElement(indexOfLast);
        return true;
    }

    private void insertElement(int elementIndex) {
        int parentIndex = (elementIndex - 1) / 2;
        if (parentIndex == elementIndex) return;
        if (HeapArray[elementIndex] > HeapArray[parentIndex]) {
            int tmp = HeapArray[parentIndex];
            HeapArray[parentIndex] = HeapArray[elementIndex];
            HeapArray[elementIndex] = tmp;
        }
        insertElement(parentIndex);
    }

    private int calculateTreeSize(int depth) {
        return (int) Math.pow(2, Math.abs(depth) + 1) - 1;
    }

    private int deleteMax() {
        int max = HeapArray[0];
        int indexOfLastElement = findIndexOfLast();
        HeapArray[0] = HeapArray[indexOfLastElement];
        HeapArray[indexOfLastElement] = -1;
        reorganizeHeap(0);
        return max;
    }

    private void reorganizeHeap(int parentIndex) {
        int leftChildIndex = 2 * parentIndex + 1;
        int rightChildIndex = 2 * parentIndex + 2;
        if (leftChildIndex >= HeapArray.length || rightChildIndex >= HeapArray.length) return;
        int max = 0;

        if (HeapArray[leftChildIndex] > HeapArray[rightChildIndex]) {
            max = leftChildIndex;
        } else {
            max = rightChildIndex;
        }

        if (HeapArray[parentIndex] < HeapArray[max]) {
            int tmp = HeapArray[parentIndex];
            HeapArray[parentIndex] = HeapArray[max];
            HeapArray[max] = tmp;
        }
        reorganizeHeap(max);
    }

    private int findIndexOfLast() {
        if (HeapArray[HeapArray.length - 1] != -1) return HeapArray.length - 1;
        int index = HeapArray.length - 1;
        for (int i = HeapArray.length - 2; i >= 0; i--) {
            if (HeapArray[i] != -1) {
                index = i;
                break;
            }
        }
        return index;
    }

}