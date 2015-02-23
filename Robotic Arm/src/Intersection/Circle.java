/*
 * Finding intersection between two circles. Equations taken from
 * http://www.ambrsoft.com/TrigoCalc/Circles2/Circle2.htm
 */
package Intersection;

public class Circle {
    private Point center;
    private float radius;
    
    public Circle(Point center, float radius) {
        this.center = center;
        this.radius = radius;
    }
    
    public Circle(float centerX, float centerY, float radius) {
        this.center = new Point(centerX, centerY);
        this.radius = radius;
    }
    
    public Point getCenter() {
        return center;
    }
    
    public float getRadius() {
        return radius;
    }
    
    public float getCenterX() {
        return center.getX();
    }
    
    public float getCenterY() {
        return center.getY();
    }
    
    /**
     * Function returns intersecting points.
     * An array containing two points is returned when two intersecting points
     * are available.
     * An array containing one point is returned when single intersecting point
     * is available.
     * An array of length zero is returned when two circles do not intersect.
     */
    public static Point[] getIntersectionPoints(Circle circle0, Circle circle1) {
        Point[] intersections;
        
        float D = Point.getDistance(circle0.getCenter(), circle1.getCenter());
        float r0 = circle0.getRadius();
        float r1 = circle1.getRadius();
        
        float a = circle0.getCenterX();
        float b = circle0.getCenterY();
        
        float c = circle1.getCenterX();
        float d = circle1.getCenterY();
        
        float alphaX = (a+c) / 2.0f + (c-a) * (float)(Math.pow(r0, 2.0d) - Math.pow(r1, 2.0d)) / (float)(2.0d * Math.pow(D, 2.0d));
        float alphaY = (b+d) / 2.0f + (d-b) * (float)(Math.pow(r0, 2.0d) - Math.pow(r1, 2.0d)) / (float)(2.0d * Math.pow(D, 2.0d));
        
        // Single intersection point
        if (r0 + r1 == D) {
            intersections = new Point[1];
            intersections[0] = new Point(alphaX, alphaY);
            System.out.println("One intersection point");
        // Two intersection points
        } else if (r0 + r1 > D && D > Math.abs(r0 - r1)) {
            intersections = new Point[2];
            float gamma = 1.0f/4.0f * (float)Math.sqrt((D+r0+r1) * (D+r0-r1) * (D-r0+r1) * (-D+r0+r1));
            float betaX = 2.0f * (b-d) / (float)Math.pow(D, 2.0f) * gamma;
            float betaY = 2.0f * (a-c) / (float)Math.pow(D, 2.0f) * gamma;
            float x1 = alphaX + betaX;
            float y1 = alphaY - betaY;
            float x2 = alphaX - betaX;
            float y2 = alphaY + betaY;
            intersections[0] = new Point(x1, y1);
            intersections[1] = new Point(x2, y2);
            System.out.println("Two intersection points");
        // No intersection points
        } else {
            intersections = new Point[0];
            System.out.println("No intersection points available");
        }
        
        return intersections;
    }
}
