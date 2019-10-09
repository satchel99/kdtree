import java.util.ArrayList;
import java.awt.geom.Point2D;
public class Test {
    public static void main(String [] args) {
        SpatialTree tree = new SpatialTree();
        ArrayList<Point2D> points = new ArrayList<Point2D>();
        points.add(new Point2D.Double(8,2));
        points.add(new Point2D.Double(3,6));
        points.add(new Point2D.Double(9,7));
        points.add(new Point2D.Double(4,12));
        points.add(new Point2D.Double(0,10));
        points.add(new Point2D.Double(11,9));
        points.add(new Point2D.Double(15,21));
        tree.selectionSortX(points);
        System.out.println(points);
        tree.selectionSortX(points);
        System.out.println(points);
        System.out.println("\n");
        tree.createTree(points);
        System.out.println(tree);
        tree.draw();
    }
}