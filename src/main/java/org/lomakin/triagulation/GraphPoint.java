package org.lomakin.triagulation;

import lombok.Getter;

@Getter
public class GraphPoint implements Comparable<GraphPoint> {
    int x;
    int y;

    public GraphPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int compareTo(GraphPoint o) {
        return (x - o.x) + (y - o.y);
    }
}
