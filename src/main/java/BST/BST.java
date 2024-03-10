import java.io.*;
import java.util.*;

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
        ArrayList<BSTNode> resultList = new ArrayList<>();
        if (this.Root.LeftChild != null) {
            recursion_inOrder(this.Root.LeftChild, resultList);
        }
        resultList.add(this.Root);
        if (this.Root.RightChild != null) {
            recursion_inOrder(this.Root.RightChild, resultList);
        }
        return resultList;
    }

    private void recursion_inOrder(BSTNode<T> node, ArrayList<BSTNode> resultList) {
        if (node.LeftChild != null) {
            recursion_inOrder(node.LeftChild, resultList);
        }
        resultList.add(node);
        if (node.RightChild != null) {
            recursion_inOrder(node.RightChild, resultList);
        }
    }

    private ArrayList<BSTNode> postOrder() {
        if (this.Root == null) return new ArrayList<>();
        ArrayList<BSTNode> resultList = new ArrayList<>();
        recursion_postOrder(this.Root, resultList);
        return resultList;
    }

    private void recursion_postOrder(BSTNode<T> node, ArrayList<BSTNode> resultList) {
        if (node.LeftChild != null) {
            recursion_postOrder(node.LeftChild, resultList);
        }
        if (node.RightChild != null) {
            recursion_postOrder(node.RightChild, resultList);
        }
        resultList.add(node);
    }

    private ArrayList<BSTNode> preOrder() {
        if (this.Root == null) return new ArrayList<>();
        ArrayList<BSTNode> resultList = new ArrayList<>();
        recursion_preOrder(this.Root, resultList);
        return resultList;
    }

    private void recursion_preOrder(BSTNode<T> node, ArrayList<BSTNode> resultList) {
        resultList.add(node);
        if (node.LeftChild != null) {
            recursion_preOrder(node.LeftChild, resultList);
        }
        if (node.RightChild != null) {
            recursion_preOrder(node.RightChild, resultList);
        }
    }
}