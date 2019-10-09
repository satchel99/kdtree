import java.awt.geom.Point2D;
import edu.princeton.cs.introcs.StdDraw;
import edu.princeton.cs.introcs.Draw;
import edu.princeton.cs.introcs.DrawListener;
import java.util.ArrayList;

public class SpatialTree implements DrawListener 
{ 
//change back to private later
public SpatialTreeNode root;

private Draw draw;
private int size; 
public SpatialTree()
{
root = new SpatialTreeNode(new Point2D.Double(0.0, 0.0), null, null, null, true); 
draw = new Draw();
size = 0; 
draw.addListener(this); 

}
//helper method for add: add point p to subtree rooted at node
private SpatialTreeNode add(SpatialTreeNode node, Point2D p, boolean isXNode)
{
//if new node, create it 
if (node == null)
{
return new SpatialTreeNode(p, null, null, null, isXNode);
}

//if already in, return it 
if(node.point.getX() == p.getX() && node.point.getY() == p.getY())
{
return node;
}

//else, insert it according to BST rules (left - right recursive call) 
if(node.isXNode && p.getX() < node.point.getX())
{
node.left = add(node.left, p, !node.isXNode);
}
else if(!node.isXNode && p.getY() < node.point.getY())
{
node.left = add(node.left, p, node.isXNode);
}	
else if(node.isXNode && p.getX() > node.point.getX())
{
node.right = add(node.right, p, !node.isXNode);
}
else
{
node.right = add(node.right, p, !node.isXNode);
}
return node;
}
//add the point to the tree, if not already in the tree
public void add(Point2D point)
{
root = add(root, point, true);
}
//Draw all points and partition lines to standard draw.
public void draw() 
{
    StdDraw.setXscale(0.0, 30.0);
    StdDraw.setYscale(0.0, 30.0);

    draw(root, 0.0, 30.0, 0.0, 30.0);
}

public void draw(SpatialTreeNode n, double xBoundStart, double xBoundEnd, double yBoundStart, double yBoundEnd) {
    if(n != null) {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        StdDraw.point(n.point.getX(), n.point.getY());
        if(!n.isXNode) {
            //draw horizontal line.
            StdDraw.setPenRadius(0.01);
            StdDraw.setPenRadius();
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(n.point.getX(), yBoundStart, n.point.getX(), yBoundEnd);
            draw(n.left, xBoundStart, n.point.getX(), yBoundStart, yBoundEnd);
            draw(n.right, n.point.getX(), xBoundEnd, yBoundStart, yBoundEnd);
        }
        else {
            StdDraw.setPenRadius(0.01);
            StdDraw.setPenRadius();
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(xBoundStart, n.point.getY(), xBoundEnd, n.point.getY());
            draw(n.left, xBoundStart, xBoundEnd, yBoundStart, n.point.getY());
            draw(n.right, xBoundStart, xBoundEnd, n.point.getY(), yBoundEnd);
        }
    }
}





//SpatialTreeNode inner class
private class SpatialTreeNode
{
private RectHV rect = null;
private SpatialTreeNode left; 
private SpatialTreeNode right;
private SpatialTreeNode parent;
private Point2D point;
boolean isXNode; //returns true if it is X node, false if it is y node

//constructor
public SpatialTreeNode(Point2D point, SpatialTreeNode left, SpatialTreeNode right, SpatialTreeNode parent, boolean isXNode)
{
this.left = left; 
this.right = right; 
this.parent = parent;
this.isXNode = isXNode; 
this.point = point; //constructor for Point2D 
}

public String toString() {
    return "(" + point.getX() + "," + point.getY() + ")";
}
}

public void selectionSortX(ArrayList<Point2D> points) {
        for(int i = 0; i < points.size(); i++) {
            int j = i;
            while((j > 0) && (points.get(j).getX() < points.get(j-1).getX())) {
                Point2D temp = points.get(j);
                points.remove(j);
                points.add(j, points.get(j-1));
                points.remove(j-1);
                points.add(j-1, temp);
                j--;
            }
        }
}

public void selectionSortY(ArrayList<Point2D> points) {
        for(int i = 0; i < points.size(); i++) {
            int j = i;
            while((j > 0) && (points.get(j).getY() < points.get(j-1).getY())) {
                Point2D temp = points.get(j);
                points.remove(j);
                points.add(j, points.get(j-1));
                points.remove(j-1);
                points.add(j-1, temp);
                j--;
            }
        }
}

public void createTree(ArrayList<Point2D> points) {
    root = createTreeHelper(points, true);
}


public SpatialTreeNode createTreeHelper(ArrayList<Point2D> points, boolean isX) {
    if(points.size() == 0) {
        return null;
    }
    if(isX) {
        selectionSortX(points);
    }
    else {
        selectionSortY(points);
    }
    int medianIndex = points.size()/2;
    SpatialTreeNode medianNode = new SpatialTreeNode(points.get(medianIndex),null,null,null,true);
    if(points.size() == 1) {
        return medianNode;
    }
    else {
        ArrayList<Point2D> leftNodes = new ArrayList<Point2D>();
        for(int i = 0; i < medianIndex;i++) {
            leftNodes.add(points.get(i));
        }
        ArrayList<Point2D> rightNodes = new ArrayList<Point2D>();
        for(int i = medianIndex+1; i < points.size();i++) {
            rightNodes.add(points.get(i));
        }
        medianNode = new SpatialTreeNode(points.get(medianIndex),createTreeHelper(leftNodes, !isX),createTreeHelper(rightNodes, !isX),null,isX);
        return medianNode;
    }
}

@Override
public String toString() {
    display(root);
    return "printed the tree";
}

public void display(SpatialTreeNode startingNode) {
    String rs = "";
    SpatialTreeNode currentNode = startingNode;
    if(currentNode == null) {
        //return "";
    }
    else {
        System.out.println("---------");
        System.out.println("Node: " + currentNode.point);
        System.out.println("is X Node: " + currentNode.isXNode);
        System.out.println(">>>>>>" + currentNode + " " + "Left Children>>>>>>");
        display(currentNode.left);
        System.out.println("<<<<<<" + currentNode + " " + "Right Children<<<<<");
        display(currentNode.right);
    }
}


@Override
public void mousePressed(double x, double y) {
// TODO Auto-generated method stub

}
@Override
public void mouseDragged(double x, double y) {
// TODO Auto-generated method stub

}
@Override
public void mouseReleased(double x, double y) {
// TODO Auto-generated method stub

}
@Override
public void keyTyped(char c) {
// TODO Auto-generated method stub

}
@Override
public void keyPressed(int keycode) {
// TODO Auto-generated method stub

}
@Override
public void keyReleased(int keycode) {
// TODO Auto-generated method stub

}
}