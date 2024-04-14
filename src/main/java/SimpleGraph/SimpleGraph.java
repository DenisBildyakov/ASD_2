package SimpleGraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.Collectors;

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
        makeAllVertexUnhit();
        if (VFrom < 0 || VFrom >= vertex.length) return new ArrayList<>();
        LinkedList<Integer> stack = new LinkedList<>();
        depthSearch(VFrom, VTo, stack);
        return stack.stream().map(i -> vertex[i]).collect(Collectors.toCollection(ArrayList::new));
    }

    private void depthSearch(int VFrom, int VTo, LinkedList<Integer> stack) {
        vertex[VFrom].Hit = true;
        stack.addLast(VFrom);
        int[] adj = m_adjacency[VFrom];
        if (isAdjTarget(adj, stack, VTo)) return;

        LinkedList<Integer> adjs = gatherAllAdjs(adj);
        while (!adjs.isEmpty()) {
            int newFrom = adjs.removeLast();
            if (isAllHit(m_adjacency[VFrom])) {
                stack.removeLast();
            }
            if (!vertex[newFrom].Hit) {
                depthSearch(newFrom, VTo, stack);
            }
        }
    }

    private LinkedList<Integer> gatherAllAdjs(int[] adjs) {
        LinkedList<Integer> resultList = new LinkedList<>();
        for (int i = 0; i < adjs.length; i++) {
            if (adjs[i] == 1 && !vertex[i].Hit) {
                resultList.addLast(i);
            }
        }
        return resultList;
    }

    private boolean isAdjTarget(int[] adjs, LinkedList<Integer> stack, int VTo) {
        boolean isFinded = false;
        for (int i = 0; i < adjs.length; i++) {
            if (adjs[i] == 1 && vertex[i].Value == vertex[VTo].Value) {
                stack.addLast(i);
                isFinded = true;
                break;
            }
        }
        return isFinded;
    }

    private boolean isAllHit(int[] adjs) {
        boolean isAllHit = true;
        for (int i = 0; i < adjs.length; i++) {
            if (adjs[i] == 1 && !vertex[i].Hit) {
                isAllHit = false;
            }
        }
        return isAllHit;
    }


    private void makeAllVertexUnhit() {
        for (int i = 0; i < vertex.length; i++) {
            vertex[i].Hit = false;
        }
    }
}