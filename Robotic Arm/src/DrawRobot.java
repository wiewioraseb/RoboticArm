import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import javax.swing.JComponent;


@SuppressWarnings("serial")
public class DrawRobot extends JComponent {

	private int[] angle = new int[3];
	


	Point2D[] p = new Point2D[4];
	
	public DrawRobot() {

	}
	
	public void setAngle(int angle, int index) {
		this.angle[index] = angle;
	}

	public void setPoint(int x1, int y1, int index) {

		p[index] = new Point2D.Float(x1, y1);
	}

	// } // END OF CONSTRUCTOR

	@Override
	public void paintComponent(Graphics g) {

		Graphics2D graph = (Graphics2D) g;

		graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		graph.setColor(Color.BLACK);

		graph.draw(new Line2D.Float(p[0], p[1]));
		graph.draw(new Line2D.Float(p[1], p[2]));
		graph.draw(new Line2D.Float(p[2], p[3]));
		
		graph.draw(new Ellipse2D.Double(p[1].getX() - 6 / 2,
				p[1].getY() - 6 / 2, 6, 6));
		graph.draw(new Ellipse2D.Double(p[2].getX() - 6 / 2,
				p[2].getY() - 6 / 2, 6, 6));
		graph.draw(new Ellipse2D.Double(p[3].getX() - 6 / 2,
				p[3].getY() - 6 / 2, 6, 6));
		
		Graphics2D graph222 = (Graphics2D) g;
		graph222.setColor(Color.RED);
		
		graph222.translate(50, 50);
		
		AffineTransform savedTransform = graph222.getTransform();
		graph222.rotate(angle[0], p[0].getX(), p[0].getY());
		graph222.draw(new Line2D.Float(p[0], p[1]));  //(new Line2D.Float(5, 15+50, 20, 15+50));
		
		graph222.translate(p[2].getX(), p[2].getY());
		graph222.rotate(angle[1], p[1].getX(), p[1].getY());
		
		graph222.draw(new Line2D.Float(p[1], p[2]));//(new Line2D.Float(20, 15+50, 66, 66+50));
		graph222.setTransform(savedTransform);
		
	}

} // END OF CLASS DrawRobot