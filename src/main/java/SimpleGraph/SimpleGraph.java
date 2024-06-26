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
        makeAllVertexUnhit();
        if (VFrom < 0 || VFrom >= vertex.length) return new ArrayList<>();
        Stack<Integer> stack = new Stack<>();
        depthSearch(VFrom, VTo, stack);
        return stack.stream().map(i -> vertex[i]).collect(Collectors.toCollection(ArrayList::new));
    }

    private Stack<Integer> depthSearch(int VFrom, int VTo, Stack<Integer> stack) {
        vertex[VFrom].Hit = true;
        stack.push(VFrom);
        int[] adj = m_adjacency[VFrom];
        for (int i = 0; i < adj.length; i++) {
            if (adj[i] == 1 && vertex[i].Value == vertex[VTo].Value) {
                stack.push(i);
                return stack;
            }
        }
        LinkedList<Integer> list = gatherAllAdjs(adj);
        while (!list.isEmpty()) {
            return depthSearch(list.removeLast(), VTo, stack);
        }
        stack.pop();
        if (stack.isEmpty()) return stack;

        return depthSearch(stack.pop(), VTo, stack);
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

    private void makeAllVertexUnhit() {
        for (int i = 0; i < vertex.length; i++) {
            vertex[i].Hit = false;
        }
    }

    public ArrayList<Vertex> BreadthFirstSearch(int VFrom, int VTo) {
        makeAllVertexUnhit();
        if (VFrom < 0 || VFrom >= vertex.length) return new ArrayList<>();
        LinkedList<Integer> queue = new LinkedList<Integer>();
        return breadthSearch(VFrom, VTo, queue);
    }

    private ArrayList<Vertex> breadthSearch(int VFrom, int VTo, LinkedList<Integer> queue) {
        int[] from = new int[max_vertex];
        Arrays.fill(from, -1);
        vertex[VFrom].Hit = true;
        queue.addLast(VFrom);

        while (!queue.isEmpty()) {
            int v = queue.removeFirst();
            int[] adj = m_adjacency[v];
            for (int i = 0; i < adj.length; i++) {
                if (adj[i] == 1 && vertex[i].Value == vertex[VTo].Value) {
                    vertex[i].Hit = true;
                    from[i] = v;
                    return getPath(from, VTo);
                }
                if (adj[i] == 1 && !vertex[i].Hit) {
                    vertex[i].Hit = true;
                    queue.addLast(i);
                    from[i] = v;
                }
            }
        }
        return new ArrayList<>();
    }

    private ArrayList<Vertex> getPath(int[] from, int VTo) {
        LinkedList<Integer> result = new LinkedList<>();
        int current = VTo;
        while (current != -1) {
            current = from[current];
            if (current == -1) break;
            result.addFirst(current);
        }
        result.addLast(VTo);
        return result.stream().map(i -> vertex[i]).collect(Collectors.toCollection(ArrayList::new));
    }

    private Integer getFirstUnhit(int[] adj) {
        Integer result = null;
        for (int i = 0; i < adj.length; i++) {
            if (adj[i] == 1 && !vertex[i].Hit) {
                result = i;
                break;
            }
        }
        return result;
    }

    public ArrayList<Vertex> WeakVertices() {
        makeAllVertexUnhit();
        int[] triangle = new int[max_vertex];
        Arrays.fill(triangle, -1);

        for (int i = 0; i < vertex.length; i++) {
            if (vertex[i] != null && !vertex[i].Hit) {
                ArrayList<Integer> adj = getAdjsList(m_adjacency[i]);
                checkForTriangles(adj, triangle, i);
            }
        }
        return getWeakVertices(triangle);
    }

    private ArrayList<Integer> getAdjsList(int[] adjs) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < adjs.length; i++) {
            if (adjs[i] == 1 && !vertex[i].Hit) {
                result.add(i);
            }
        }
        return result;
    }

    private void checkForTriangles(ArrayList<Integer> adj, int[] triangle, int index) {
        vertex[index].Hit = true;
        for (Integer a : adj) {
            for (Integer i : adj) {
                if (IsEdge(a, i)) {
                    vertex[a].Hit = true;
                    vertex[i].Hit = true;
                    triangle[index] = 1;
                    triangle[a] = 1;
                    triangle[i] = 1;
                }
            }
        }
    }

    private ArrayList<Vertex> getWeakVertices(int[] triangle) {
        ArrayList<Vertex> result = new ArrayList<>();
        for (int i = 0; i < triangle.length; i++) {
            if (triangle[i] == -1) {
                result.add(vertex[i]);
            }
        }
        return result;
    }
}