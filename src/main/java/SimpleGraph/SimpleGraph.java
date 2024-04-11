import java.util.*;

class Vertex {
    public int Value;
    public boolean Hit;

    public Vertex(int val) {
        Value = val;
        Hit = false;
    }
}

class SimpleGraph {
    Vertex[] vertex;
    int[][] m_adjacency;
    int max_vertex;

    public SimpleGraph(int size) {
        max_vertex = Math.abs(size);
        m_adjacency = new int[max_vertex][max_vertex];
        vertex = new Vertex[max_vertex];
    }

    public void AddVertex(int value) {
        Integer empty = findEmpty();
        if (empty == null) return;
        Vertex v = new Vertex(value);
        vertex[empty] = v;
    }

    public void RemoveVertex(int v) {
        vertex[v] = null;
        int[] edges = m_adjacency[v];
        Arrays.fill(edges, 0);
    }

    public boolean IsEdge(int v1, int v2) {
        return v1 >= 0 && v1 < m_adjacency.length && v2 >= 0 && v2 < m_adjacency.length && m_adjacency[v1][v2] == 1;
    }

    public void AddEdge(int v1, int v2) {
        if (v1 >= 0 && v1 < m_adjacency.length && v2 >= 0 && v2 < m_adjacency.length) {
            m_adjacency[v1][v2] = 1;
        }
    }

    public void RemoveEdge(int v1, int v2) {
        if (v1 >= 0 && v1 < m_adjacency.length && v2 >= 0 && v2 < m_adjacency.length) {
            m_adjacency[v1][v2] = 0;
        }
    }

    private Integer findEmpty() {
        Integer index = null;
        for (int i = 0; i < vertex.length; i++) {
            if (vertex[i] == null) {
                index = i;
                break;
            }
        }
        return index;
    }

    public ArrayList<Vertex> DepthFirstSearch(int VFrom, int VTo) {
        ArrayList<Vertex> list = new ArrayList<>();
        makeAllVertexUnhit();
        int index = findVertex(VFrom);
        if (index < 0 || index >= vertex.length) return list;
        Vertex from = vertex[index];
        from.Hit = true;
        list.add(from);
        depthSearch(VTo, list, index);
        return list;
    }

    private void depthSearch(int VTo, ArrayList<Vertex> stack, int index) {
        int[] edges = m_adjacency[index];
        for (int i = 0; i < edges.length; i++) {
            if (edges[i] == 1) {
                Vertex v = vertex[i];
                if (v.Hit) continue;
                v.Hit = true;
                if (v.Value == VTo) {
                    stack.add(v);
                    return;
                }
                depthSearch(VTo, stack, i);
            }
        }

    }

    private void makeAllVertexUnhit() {
        for (int i = 0; i < vertex.length; i++) {
            vertex[i].Hit = false;
        }
    }

    private int findVertex(int value) {
        int result = -1;
        for (int i = 0; i < vertex.length; i++) {
            if (vertex[i].Value == value) {
                result = i;
            }
        }
        return result;
    }
}