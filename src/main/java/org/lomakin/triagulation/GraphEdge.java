package org.lomakin.triagulation;

import lombok.Getter;

@Getter
public class GraphEdge implements Comparable<GraphEdge>{
    GraphPoint first;
    GraphPoint second;

    public GraphEdge(GraphPoint first, GraphPoint second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public int compareTo(GraphEdge o) {
        return first.compareTo(o.first) + second.compareTo(o.second);
    }
}
