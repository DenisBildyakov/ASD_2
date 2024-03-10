package SimpleTree;

import java.util.ArrayList;
import java.util.List;


public class SimpleTree<T> {
    public SimpleTreeNode<T> Root;
    private int count;

    public SimpleTree() {
        count = 0;
    }

    public SimpleTree(SimpleTreeNode<T> root) {
        Root = root;
        if (Root == null) {
            count = 0;
        } else {
            count = 1;
        }
    }

    public void AddChild(SimpleTreeNode<T> ParentNode, SimpleTreeNode<T> newChild) {
        if (Root == null && ParentNode != null && newChild != null) {
            Root = ParentNode;
            count++;
        }
        if (Root == null && newChild != null) {
            Root = newChild;
            count++;
            return;
        }
        if (ParentNode != null && newChild != null) {
            newChild.Parent = ParentNode;
            List<SimpleTreeNode<T>> list = ParentNode.Children;
            if (list == null) {
                list = new ArrayList<>();
                ParentNode.Children = list;
            }
            list.add(newChild);
            count++;
        }
        if (ParentNode == null && newChild != null) {
            newChild.Parent = Root;
            List<SimpleTreeNode<T>> list = Root.Children;
            if (list == null) {
                list = new ArrayList<>();
                Root.Children = list;
            }
            list.add(newChild);
            count++;
        }
    }

    public void DeleteNode(SimpleTreeNode<T> NodeToDelete) {
        if (NodeToDelete != null && NodeToDelete.Parent != null) {
            NodeToDelete.Parent.Children.remove(NodeToDelete);
            NodeToDelete.Parent = null;
            count--;
        }
    }

    public List<SimpleTreeNode<T>> GetAllNodes() {
        if (Root == null) return List.of();
        if (Root.Children == null) return List.of(Root);
        List<SimpleTreeNode<T>> children = new ArrayList<>(Root.Children);
        children.addAll(findChildren(children));
        children.add(Root);
        return children;
    }

    private List<SimpleTreeNode<T>> findChildren(List<SimpleTreeNode<T>> children) {
        if (children == null || children.isEmpty()) return new ArrayList<>();
        List<SimpleTreeNode<T>> list = new ArrayList<>();
        for (SimpleTreeNode<T> child : children) {
            list.addAll(child.Children == null ? new ArrayList<>() : child.Children);
        }
        list.addAll(findChildren(list));
        return list;
    }

    public List<SimpleTreeNode<T>> FindNodesByValue(T val) {
        List<SimpleTreeNode<T>> list = GetAllNodes();
        List<SimpleTreeNode<T>> result = new ArrayList<>();
        for (SimpleTreeNode<T> node : list) {
            if (node.NodeValue.equals(val)) result.add(node);
        }
        return result;
    }

    public void MoveNode(SimpleTreeNode<T> OriginalNode, SimpleTreeNode<T> NewParent) {
        if (OriginalNode == null || NewParent == null) return;
        List<SimpleTreeNode<T>> parent = OriginalNode.Parent.Children;
        parent.remove(OriginalNode);
        count--;
        OriginalNode.Parent = NewParent;
        AddChild(NewParent, OriginalNode);
    }

    public int Count() {
        return count;
    }

    public int LeafCount() {
        long leafs = GetAllNodes().stream().filter(n -> n.Children == null || n.Children.isEmpty()).count();
        return Math.toIntExact(leafs);
    }
}

class SimpleTreeNode<T> {
    public T NodeValue;
    public SimpleTreeNode<T> Parent;
    public List<SimpleTreeNode<T>> Children;

    public SimpleTreeNode(T val, SimpleTreeNode<T> parent) {
        NodeValue = val;
        Parent = parent;
        Children = null;
    }
}