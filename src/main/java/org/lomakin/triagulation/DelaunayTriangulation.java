package org.lomakin.triagulation;

import java.awt.*;
import java.util.Set;
import java.util.TreeSet;

public class DelaunayTriangulation {
    int[][] adjMatrix;

    public DelaunayTriangulation(int size)
    {
        this.adjMatrix = new int[size][size];
    }

    public int[][] getAdj() {
        return this.adjMatrix;
    }

    /**
     * @param size - points count
     * @param x - x dimension of points
     * @param y - y dimension of points
     * @param z - z dimension of points
     * @return - set of points edges
     */
    public TreeSet<GraphEdge> getEdges(int size, int[] x, int[] y, int[] z)
    {
        TreeSet<GraphEdge> result = new TreeSet<>();

        if (size == 2)
        {
            this.adjMatrix[0][1] = 1;
            this.adjMatrix[1][0] = 1;
            result.add(new GraphEdge(new GraphPoint(x[0], y[0]), new GraphPoint(x[1], y[1])));

            return result;
        }

        for (int i = 0; i < size - 2; i++) {
            for (int j = i + 1; j < size; j++) {
                for (int k = i + 1; k < size; k++)
                {
                    if (j == k) {
                        continue;
                    }
                    int xn = (y[j] - y[i]) * (z[k] - z[i]) - (y[k] - y[i]) * (z[j] - z[i]);

                    int yn = (x[k] - x[i]) * (z[j] - z[i]) - (x[j] - x[i]) * (z[k] - z[i]);

                    int zn = (x[j] - x[i]) * (y[k] - y[i]) - (x[k] - x[i]) * (y[j] - y[i]);
                    boolean flag;
                    if (flag = (zn < 0 ? 1 : 0) != 0) {
                        for (int m = 0; m < size; m++) {
                            flag = (flag) && ((x[m] - x[i]) * xn + (y[m] - y[i]) * yn + (z[m] - z[i]) * zn <= 0);
                        }

                    }

                    if (!flag)
                    {
                        continue;
                    }
                    result.add(new GraphEdge(new GraphPoint(x[i], y[i]), new GraphPoint(x[j], y[j])));
                    //System.out.println("----------");
                    //System.out.println(x[i]+" "+ y[i] +"----"+x[j]+" "+y[j]);

                    result.add(new GraphEdge(new GraphPoint(x[j], y[j]), new GraphPoint(x[k], y[k])));
                    //System.out.println(x[j]+" "+ y[j] +"----"+x[k]+" "+y[k]);
                    result.add(new GraphEdge(new GraphPoint(x[k], y[k]), new GraphPoint(x[i], y[i])));
                    //System.out.println(x[k]+" "+ y[k] +"----"+x[i]+" "+y[i]);
                    this.adjMatrix[i][j] = 1;
                    this.adjMatrix[j][i] = 1;
                    this.adjMatrix[k][i] = 1;
                    this.adjMatrix[i][k] = 1;
                    this.adjMatrix[j][k] = 1;
                    this.adjMatrix[k][j] = 1;
                }

            }

        }

        return result;
    }

    public Set<GraphEdge> getEdges(Set<Point> pointsSet)
    {
        if ((pointsSet == null) || (pointsSet.size() <= 0)) {
            return null;
        }

        int size = pointsSet.size();

        int[] x = new int[size];
        int[] y = new int[size];
        int[] z = new int[size];

        int i = 0;

        for (Point point : pointsSet) {
            x[i] = (int) point.getX();
            y[i] = (int) point.getY();
            z[i] = (x[i] * x[i] + y[i] * y[i]);

            i++;
        }

        return getEdges(size, x, y, z);

    }
}
