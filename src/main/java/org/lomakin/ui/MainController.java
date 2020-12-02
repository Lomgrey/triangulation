package org.lomakin.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import org.lomakin.triagulation.DelaunayTriangulation;
import org.lomakin.triagulation.GraphEdge;
import org.lomakin.ui.utils.ListenerUtils;

import java.awt.*;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MainController {

    private static final int POINT_RADIUS = 4;
    private static final int BORDER = 40;

    private static final Random RAND = new Random();

    @FXML
    public Pane canvas;
    @FXML
    public TextField pointsCount;


    public void initialize(){
        ListenerUtils.addDigitChecker(pointsCount);
        ListenerUtils.addTextLimiter(pointsCount, 3);

        canvas.setPrefSize(AppContext.WIDTH, AppContext.HEIGHT);
    }

    @FXML
    private void drawPoint(MouseEvent e) {
        drawPointAt((int) e.getX(), (int) e.getY());
        triangle();
    }

    private void drawPointAt(double x, double y) {
        Circle point = newPoint(x, y);
        canvas.getChildren().add(point);
    }

    private void drawEdges(Set<GraphEdge> edges) {
        for (GraphEdge edge : edges) {
            Line line = new Line(
                    edge.getFirst().getX(), edge.getFirst().getY(),
                    edge.getSecond().getX(), edge.getSecond().getY());
            line.setStroke(Color.VIOLET);
            canvas.getChildren().add(line);
        }
    }

    private Circle newPoint(double x, double y) {
        Circle point = new Circle(x, y, POINT_RADIUS);
        point.setFill(Color.YELLOWGREEN);
        point.setStroke(Color.YELLOW);
        point.setStrokeWidth(1);
        return point;
    }

    @FXML
    private void clearCanvas(){
        clearPoints();
        clearEdges();
    }

    private void clearPoints(){
        canvas.getChildren().removeAll(getAllPoints());
    }

    private void clearEdges(){
        canvas.getChildren().removeAll(getAllEdges());
    }

    private List<Node> getAllPoints() {
        return canvas.getChildren()
                .filtered(n -> n instanceof Circle);
    }

    private List<Node> getAllEdges() {
        return canvas.getChildren()
                .filtered(n -> n instanceof Line);
    }

    private Stream<Circle> allPointsAsCircle() {
        return getAllPoints().stream()
                .map(Circle.class::cast);
    }

    public void triangle() {
        clearEdges();
        Set<Point> pointSet = allPointsAsCircle()
                .map(circle -> new Point((int) circle.getCenterX(), (int) circle.getCenterY()))
                .collect(Collectors.toSet());

        DelaunayTriangulation triangulation = new DelaunayTriangulation(pointSet.size());
        Set<GraphEdge> edges = triangulation.getEdges(pointSet);
        drawEdges(edges);
    }

    public void drawRandomPoints() {
        final int count = Integer.parseInt(pointsCount.getText());
        IntStream.range(0, count)
                .forEach(ignore -> drawPointAt(
                        RAND.nextDouble() * (AppContext.WIDTH - BORDER * 2) + BORDER,
                        RAND.nextDouble() * (AppContext.HEIGHT - BORDER * 2) + BORDER));
    }

}
