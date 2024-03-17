package BalancedBST;

import java.util.Arrays;

class BSTNode {
    public int NodeKey;
    public BSTNode Parent;
    public BSTNode LeftChild;
    public BSTNode RightChild;
    public int Level;

    public BSTNode(int key, BSTNode parent) {
        NodeKey = key;
        Parent = parent;
        LeftChild = null;
        RightChild = null;
    }
}

class BalancedBST {
    public BSTNode Root;

    public BalancedBST() {
        Root = null;
    }

    public void GenerateTree(int[] a) {
        Arrays.sort(a);
        fillTree(null, a);
    }

    private void fillTree(BSTNode parent, int[] arr) {
        if (arr.length < 1) return;
        int index = arr.length / 2;
        BSTNode node;
        if (parent == null) {
            this.Root = new BSTNode(arr[index], null);
            this.Root.Level = 0;
            node = this.Root;
        } else {
            node = new BSTNode(arr[index], parent);
            if (node.NodeKey > parent.NodeKey) {
                parent.RightChild = node;
            } else {
                parent.LeftChild = node;
            }
            node.Level = parent.Level + 1;
        }
        int[] leftPart = Arrays.copyOfRange(arr, 0, index);
        int[] rightPart = Arrays.copyOfRange(arr, index + 1, arr.length);
        fillTree(node, leftPart);
        fillTree(node, rightPart);
    }

    public boolean IsBalanced(BSTNode root_node) {
        Integer[] leftLevel = new Integer[]{null};
        Integer[] rightLevel = new Integer[]{null};
        isBalanced_rec(root_node.LeftChild, leftLevel);
        isBalanced_rec(root_node.RightChild, rightLevel);
        return Math.abs(leftLevel[0] - rightLevel[0]) <= 1;
    }

    private void isBalanced_rec(BSTNode node, Integer[] level) {
        if (node == null) return;
        if (level[0] == null || node.Level > level[0]) level[0] = node.Level;
        isBalanced_rec(node.LeftChild, level);
        isBalanced_rec(node.RightChild, level);
    }
}