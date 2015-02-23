package Intersection;

public class Point {
    private float x;
    private float y;
    
    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    public float getX() {
        return x;
    }
    
    public float getY() {
        return y;
    }
    
    static public float getDistance(Point point1, Point point) {
    	System.out.println("Distance between is: " + Math.sqrt(Math.pow(point1.getX() - point.getX(), 2) + Math.pow(point1.getY() - point.getY(), 2)));
    	return (float)Math.sqrt(Math.pow(point1.getX() - point.getX(), 2) + Math.pow(point1.getY() - point.getY(), 2));
    } 
}
