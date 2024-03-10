package BST;

import java.util.ArrayList;
import java.util.LinkedList;

class BSTNode<T> {
    public int NodeKey;
    public T NodeValue;
    public BSTNode<T> Parent;
    public BSTNode<T> LeftChild;
    public BSTNode<T> RightChild;

    public BSTNode(int key, T val, BSTNode<T> parent) {
        NodeKey = key;
        NodeValue = val;
        Parent = parent;
        LeftChild = null;
        RightChild = null;
    }
}


class BSTFind<T> {

    public BSTNode<T> Node;


    public boolean NodeHasKey;


    public boolean ToLeft;

    public BSTFind() {
        Node = null;
    }
}

class BST<T> {
    BSTNode<T> Root;
    private int count;

    public BST(BSTNode<T> node) {
        Root = node;
        if (Root == null) {
            count = 0;
        } else {
            count = 1;
        }
    }

    public BSTFind<T> FindNodeByKey(int key) {
        if (Root == null) return null;
        return findById(Root, key);
    }

    private BSTFind<T> findById(BSTNode<T> node, int key) {
        BSTNode<T> nextNode;
        if (key > node.NodeKey) {
            nextNode = node.RightChild;
        } else {
            nextNode = node.LeftChild;
        }
        if (nextNode == null || node.NodeKey == key) {
            BSTFind<T> result = new BSTFind<T>();
            result.Node = node;
            result.NodeHasKey = node.NodeKey == key;
            result.ToLeft = key < node.NodeKey;
            return result;
        }
        return findById(nextNode, key);
    }

    public boolean AddKeyValue(int key, T val) {
        BSTFind<T> result = FindNodeByKey(key);
        if (result == null) {
            this.Root = new BSTNode<T>(key, val, null);
            count++;
            return true;
        }
        BSTNode<T> newNode = new BSTNode<T>(key, val, result.Node);
        if (!result.NodeHasKey) {
            if (result.ToLeft) {
                result.Node.LeftChild = newNode;
            } else {
                result.Node.RightChild = newNode;
            }
            count++;
            return true;
        }
        return false;
    }

    public BSTNode<T> FinMinMax(BSTNode<T> FromNode, boolean FindMax) {
        BSTNode<T> nodeToStart = FromNode;
        if (nodeToStart == null) nodeToStart = Root;
        return FindMax ? findMax(nodeToStart) : findMin(nodeToStart);
    }

    private BSTNode<T> findMin(BSTNode<T> node) {
        if (node == null) return null;
        if (node.LeftChild == null) return node;
        return findMin(node.LeftChild);
    }

    private BSTNode<T> findMax(BSTNode<T> node) {
        if (node == null) return null;
        if (node.RightChild == null) return node;
        return findMax(node.RightChild);
    }

    public boolean DeleteNodeByKey(int key) {
        BSTFind<T> findToDelete = FindNodeByKey(key);
        if (!findToDelete.NodeHasKey) return false;
        BSTNode<T> nodeToDelete = findToDelete.Node;
        if (nodeToDelete.equals(this.Root)) {
            this.Root = null;
            count--;
            return true;
        }
        if (nodeToDelete.RightChild == null && nodeToDelete.LeftChild == null) {
            BSTNode<T> parent = nodeToDelete.Parent;
            if (parent.LeftChild.NodeKey == key) {
                parent.LeftChild = null;
            } else if (parent.RightChild.NodeKey == key) {
                parent.RightChild = null;
            }
            nodeToDelete.Parent = null;
            count--;
            return true;
        }
        BSTNode<T> minChild;
        if (nodeToDelete.RightChild != null) {
            minChild = FinMinMax(nodeToDelete.RightChild, false);
        } else {
            minChild = nodeToDelete.LeftChild;
        }
        minChild.Parent = nodeToDelete.Parent;
        minChild.RightChild = nodeToDelete.RightChild;
        minChild.LeftChild = nodeToDelete.LeftChild;
        nodeToDelete.Parent.RightChild = minChild;
        count--;
        return true;
    }

    public int Count() {
        return count;
    }

    public ArrayList<BSTNode> WideAllNodes() {
        if (this.Root == null) return new ArrayList<>();
        LinkedList<BSTNode> resultList = new LinkedList<>();
        resultList.add(this.Root);

        treeSwipeRecursive(getChildren(this.Root), resultList, true);
        return new ArrayList<>(resultList);
    }

    private void treeSwipeRecursive(LinkedList<BSTNode> children, LinkedList<BSTNode> resultList, boolean inOrder) {
        if (children.isEmpty()) return;
        LinkedList<BSTNode> tmpList = new LinkedList<>();
        for (BSTNode<T> child : children) {
            if (inOrder) {
                resultList.addLast(child);
            } else {
                resultList.addFirst(child);
            }
            tmpList.addAll(getChildren(child));
        }
        treeSwipeRecursive(tmpList, resultList, inOrder);
    }

    private LinkedList<BSTNode> getChildren(BSTNode<T> parent) {
        if (parent.RightChild == null && parent.LeftChild == null) return new LinkedList<>();
        LinkedList<BSTNode> list = new LinkedList<>();
        if (parent.LeftChild != null) list.add(parent.LeftChild);
        if (parent.RightChild != null) list.add(parent.RightChild);
        return list;
    }

    public ArrayList<BSTNode> DeepAllNodes(int i) {
        if (this.Root == null) return new ArrayList<>();
        if (i == 0) return inOrder();
        if (i == 1) return postOrder();
        if (i == 2) return preOrder();

        return new ArrayList<>();
    }

    private ArrayList<BSTNode> inOrder() {
        if (this.Root == null) return new ArrayList<>();
        LinkedList<BSTNode> resultList = new LinkedList<>();
        if (this.Root.LeftChild != null) {
            rec(this.Root.LeftChild, resultList);
        }
        resultList.add(this.Root);
        if (this.Root.RightChild != null) {
            rec(this.Root.RightChild, resultList);
        }
        return new ArrayList<>(resultList);
    }

    private void rec(BSTNode<T> node, LinkedList<BSTNode> resultList) {
        if (node.LeftChild != null) {
            rec(node.LeftChild, resultList);
        }
        resultList.add(node);
        if (node.RightChild != null) {
            rec(node.RightChild, resultList);
        }
    }

    private ArrayList<BSTNode> postOrder() {
        LinkedList<BSTNode> resultList = new LinkedList<>();
        treeSwipeRecursive(getChildren(this.Root), resultList, false);
        resultList.add(this.Root);
        return new ArrayList<>(resultList);
    }

    private ArrayList<BSTNode> preOrder() {
        LinkedList<BSTNode> resultList = new LinkedList<>();
        resultList.add(this.Root);
        treeSwipeRecursive(getChildren(this.Root), resultList, true);
        return new ArrayList<>(resultList);
    }

//    private ArrayList<BSTNode> getSubTreeList(BSTNode<T> fromNode) {
//        LinkedList<BSTNode> resultList = new LinkedList<>();
//        BSTNode<T> min = FinMinMax(fromNode, false);
//        resultList.add(min);
//        fromMinSwipe(min, resultList);
//        return new ArrayList<>(resultList);
//    }
//
//    private void fromMinSwipe(BSTNode<T> node, LinkedList<BSTNode> resultList) {
//        if (node.Parent.equals(this.Root)) return;
//        resultList.add(node.Parent);
//        if (node.Parent.RightChild != null) resultList.add(node.Parent.RightChild);
//        fromMinSwipe(node.Parent, resultList);
//    }

//    private ArrayList<BSTNode> postOrder() {
//        LinkedList<BSTNode> resultList = new LinkedList<>();
//        treeSwipeRecursive(getChildren(this.Root), resultList, false);
//        resultList.add(this.Root);
//        return new ArrayList<>(resultList);
//    }
//
//    private ArrayList<BSTNode> preOrder() {
//        LinkedList<BSTNode> resultList = new LinkedList<>();
//        resultList.add(this.Root);
//        treeSwipeRecursive(getChildren(this.Root), resultList, true);
//        return new ArrayList<>(resultList);
//    }

}