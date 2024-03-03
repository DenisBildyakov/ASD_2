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

}