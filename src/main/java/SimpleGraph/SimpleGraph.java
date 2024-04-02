import java.util.*;

class Vertex {
    public int Value;

    public Vertex(int val) {
        Value = val;
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
}