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
	private boolean auxLine;
	private int armLength;

	Point2D[] p = new Point2D[4];
	Point2D[] pAux = new Point2D[4]; // auxiliary lines
	
	
	
	public DrawRobot() {
		
		auxLine = false;
		
		pAux[0] = new Point2D.Float(1,1);
		pAux[1] = new Point2D.Float(1,1);
		pAux[2] = new Point2D.Float(100,150);
		pAux[3] = new Point2D.Float(1,1);
	}
	
	public void setArmLength(int armLength) {
		this.armLength = armLength;
	}
	
	public void setAngle(int angle, int index) {
		this.angle[index] = angle;
	}

	public void setPoint(int x1, int y1, int index) {
		p[index] = new Point2D.Float(x1, y1);
	}

	public void setPointTEST(int x1, int y1, int index) {
		pAux[index] = new Point2D.Float(x1, y1);
	}

	@Override
	public void paintComponent(Graphics g) {

		if (auxLine == true){
			Graphics2D graphAux = (Graphics2D) g;
			graphAux.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			graphAux.setColor(Color.GREEN);
			
			graphAux.draw(new Ellipse2D.Double(p[1].getX() - (armLength),
					p[1].getY() - 90 / 2, 90, 90));
			graphAux.draw(new Ellipse2D.Double(p[3].getX() - (armLength),
					p[3].getY() - (armLength), (armLength*2), (armLength*2)));
			
			graphAux.setColor(Color.RED);
			graphAux.draw(new Ellipse2D.Double(pAux[2].getX() - 6 / 2,
					pAux[2].getY() - 6 / 2, 6, 6));
			
			
		
			graphAux.draw(new Line2D.Float(p[1], pAux[2]));
			graphAux.draw(new Line2D.Float(pAux[2], p[3]));
		}
		
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
		

		

		
		//AffineTransform savedTransform = graph.getTransform();
		//graph.rotate(angle[1]);
		//graph.translate(40, 0);
		
		// https://github.com/sheldonkwok/CSCI201-Factory-Project/tree/master/frontend 

		/*
		Graphics2D graph222 = (Graphics2D) g;
		graph222.setColor(Color.RED);
		
		graph222.translate(50, -15);
		
		
		AffineTransform savedTransform = graph222.getTransform();
		graph222.rotate(Math.toRadians(angle[0]), p[0].getX(), p[0].getY());
		graph222.draw(new Line2D.Float(p[0], p[1]));  //(new Line2D.Float(5, 15+50, 20, 15+50));
		
		//savedTransform.con
		
		//graph222.translate(p[1].getX(), p[1].getY()); // Niepotrzebne, bo to znowu przesuwa,a juz jest przesuniete na te pkt [1]
		graph222.rotate(Math.toRadians(angle[0]), p[1].getX(), p[1].getY());
		graph222.translate(p[1].getX(), p[1].getY());
		
		//graph222.draw(new Line2D.Float(p[1], p[2]));//(new Line2D.Float(20, 15+50, 66, 66+50));
		graph222.draw(new Line2D.Float(p[1], p[2]));
		graph222.setTransform(savedTransform);
		*/
		
		
		//System.out.println(angle[0] + " a drugi: " + angle[1]);
		
		//AffineTransform costam = new AffineTransform();
		//costam.setToTranslation(p[1].getX(), p[2].getY());
		
		//graph.draw
		
		// ****** AffineTransform
		// https://www.youtube.com/watch?v=vHfGiTFWoc4
		// https://www.youtube.com/watch?v=CA2U2dfc_yI 
		// http://docs.oracle.com/javase/tutorial/2d/advanced/transforming.html
		// http://stackoverflow.com/questions/2329934/robot-simulation-in-java !!!!!??????!!!!
		// http://www.parallemic.org/Java/3RRR.html <--- Ten dzia³a robot 2d
		// https://www.java.net/node/700254 <---- Koles pyta o ten sam problem, czy ktos mu 
		// nie pomoze z napisaniem enginu do inverse kinematics
		// http://www.umbc.edu/engineering/me/vrml/research/software/synthetica/document/SyntheticaAPI/synthetica/mechanism/JointR.html 
		//                   biblioteka z inverse kinematics dla revolute joint
		// http://stackoverflow.com/questions/3518130/how-to-calculate-inverse-kinematics <-- Linki do podrêczników o IK
		// http://odin.himinbi.org/classes/csc370/robot/Robot.java <-- Dla silniczka rotuj¹cego, w 3d i w ogole
		// http://www.societyofrobots.com/robot_arm_tutorial.shtml
		
		// http://hoshan.org/tag/inverse-kinematics/ <-- MUJ BORZE TUTAJ JEST ROZWIAZANIE PROSTE JAK CHUJ
		
		
		
	}

	public void setAuxLine(boolean auxLine) {
		this.auxLine = auxLine;
	}

} // END OF CLASS DrawRobot