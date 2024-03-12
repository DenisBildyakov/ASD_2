import java.util.*;

class aBST {
    public Integer Tree[];
    private int treeSize;

    public aBST(int depth) {
        int tree_size = calculateTreeSize(depth);
        treeSize = tree_size;
        Tree = new Integer[tree_size];
        for (int i = 0; i < tree_size; i++) Tree[i] = null;
    }

    public Integer FindKeyIndex(int key) {
        if (Tree[0] == null) return 0;
        return findIndexByKey(0, key);
    }

    private Integer findIndexByKey(int parentIndex, int key) {
        if (parentIndex > treeSize) return null;
        if (Tree[parentIndex] == null) return parentIndex * -1;
        if (Tree[parentIndex].equals(key)) return parentIndex;
        int childIndex = 0;
        if (key > Tree[parentIndex]) {
            childIndex = 2 * parentIndex + 2;
        } else {
            childIndex = 2 * parentIndex + 1;
        }
        return findIndexByKey(childIndex, key);
    }

    public int AddKey(int key) {
        Integer index = FindKeyIndex(key);
        if (index == null) return -1;
        if (index > 0 || index == 0 && Tree[0] != null) return index;
        if (index < 0 || index == 0 && Tree[0] == null) {
            index = Math.abs(index);
            Tree[index] = key;
            return index;
        }
        return -1;
    }

    private int calculateTreeSize(int depth) {
        return (int) Math.pow(2, depth+1) - 1;
    }
}