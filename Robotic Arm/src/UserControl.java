import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jdom2.DocType;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Text;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import Intersection.Circle;
import Intersection.Point;


@SuppressWarnings("serial")
public class UserControl extends JFrame {
	// Controller

	private ArmModel aModel;
	private RobotView rView;
	private DrawRobot dRobot;
	
	
	Circle c1, c2; // Te klasy ktore maja obl intersection
	Point[] intersections;

	public UserControl(ArmModel a, RobotView r, DrawRobot d) {

		aModel = a;
		rView = r;
		dRobot = d;

		dRobot.setArmLength(aModel.ARM_LENGTH);
		
		aModel.setxPosition(150, 0);
		aModel.setyPosition(150, 0);

		assignCalculation();

		rView.addSpinnerListener(new SpinnerListener());
		rView.addButtonListener(new ButtonListener());
		rView.addSliderListener(new SliderListener());
		rView.addItemListener(new ItemListener());
		dRobot.addMouseMotionListener(new MouseMotionListener());
		dRobot.addMouseListener(new MouseListener());

		initWindowPanel();

	} // End of constructor

	private void initWindowPanel() {

		setLayout(new BorderLayout());
		add(rView, BorderLayout.EAST);
		add(rView.getPanelLeft().add(dRobot), BorderLayout.CENTER); 														
		setTitle("Robotic Arm");
		setSize(600, 325);
		setLocationRelativeTo(null);
		setResizable(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setVisible(true);

	} // End of init method

	private void assignCalculation() {

			// Calculates following points and stores in model
		
		aModel.calcXandY(aModel.getxPosition(0), aModel.getyPosition(0), 1);
		setDrawPoints();
		
		/*
		for (int idx = 1; idx < aModel.getxLength().length; idx++) { 
			aModel.calcXandY(aModel.getxPosition(idx - 1), aModel.getyPosition(idx - 1), idx);
			System.out.println("IDX: " +idx);
		}
		setDrawPoints();
		*/
	}

	private void setDrawPoints() {
		for (int i = 0; i < aModel.getxLength().length; i++) {
			dRobot.setPoint(aModel.getxPosition(i), aModel.getyPosition(i), i);
			 // << << <<
			// TUUUUUUUUUUTAJ
		}
		/*
		for (int j = 0; j < 3; j++) {
			dRobot.setAngle(aModel.getArmAngle(j), j);
		}
		*/ //*** TO POWODUJE CRASH
		
	}

	public void readXml() {

		SAXBuilder builder = new SAXBuilder();

		try {

			Document readDoc = builder.build(new File("./src/armPosition.xml"));
			
			Element element = readDoc.getRootElement();

			int i = 0; // index of element
			for (Element current : element.getChildren("arm")) {
				aModel.setArmAngle(
						Integer.parseInt(current.getChildText("angle")), i);
				rView.setSlider(aModel.getArmAngle(i), i); 
				i++;
			}

			// Reads active joints from xml
			aModel.setActiveJoints(Integer.parseInt(readDoc.getRootElement()
					.getChildText("active_joints")));
			rView.setSpinnerValue(aModel.getActiveJoints());

			// Reads initial x & y possition from xml
			aModel.setxPosition(Integer.parseInt(readDoc.getRootElement()
					.getChildText("x")), 0);
			aModel.setyPosition(Integer.parseInt(readDoc.getRootElement()
					.getChildText("y")), 0);

			aModel.calcXandY(aModel.getxPosition(0), aModel.getyPosition(0), 1);
			//aModel.setxPosition(aModel.getxPosition(1), 1);  ****** Tego nie trzeba znowu
			//aModel.setyPosition(aModel.getyPosition(1), 1);  ****** bo to juz jest w calcXandY

			//aModel.calcXandY(aModel.getxPosition(1), aModel.getyPosition(1), 2); // Znowu calcXnY nie trzeba,
																				   //bo to przebiega przez calosc
			//aModel.setxPosition(aModel.getxPosition(2), 2);
			//aModel.setyPosition(aModel.getyPosition(2), 2);

			setDrawPoints(); // sets points from model and sends them to
								// DrawRobot for drawing arm

		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void saveXml() {

		try {

			Document saveDoc = new Document();
			Element theRoot = new Element("main"); // Creates element main
			saveDoc.setRootElement(theRoot); // main is the root

			Element arm1 = new Element("arm");
			Element arm2 = new Element("arm");
			Element arm3 = new Element("arm");
			Element angle1 = new Element("angle");
			Element angle2 = new Element("angle");
			Element angle3 = new Element("angle");
			angle1.addContent(new Text(Integer.toString(aModel.getArmAngle(0))));
			angle2.addContent(new Text(Integer.toString(aModel.getArmAngle(1))));
			angle3.addContent(new Text(Integer.toString(aModel.getArmAngle(2))));
			Element x = new Element("x");
			x.addContent(new Text(Integer.toString(aModel.getxPosition(0))));
			Element y = new Element("y");
			y.addContent(new Text(Integer.toString(aModel.getyPosition(0))));
			Element activeJoints = new Element("active_joints");
			activeJoints.addContent(new Text(Integer.toString(aModel
					.getActiveJoints())));

			arm1.addContent(angle1);
			arm2.addContent(angle2);
			arm3.addContent(angle3);

			theRoot.addContent(arm1);
			theRoot.addContent(arm2);
			theRoot.addContent(arm3);
			theRoot.addContent(x);
			theRoot.addContent(y);
			theRoot.addContent(activeJoints);

			// <!DOCTYPE main SYSTEM "armposition.dtd" >
			DocType xhtml = new DocType("main", "armPosition.dtd");
			saveDoc.setDocType(xhtml);

			// Uses indenting with pretty format
			XMLOutputter xmlOutput = new XMLOutputter(Format.getPrettyFormat());

			// Create a new file and write XML to it
			xmlOutput.output(saveDoc, new FileOutputStream(new File(
					"./src/armPosition.xml")));
		}

		catch (IOException e) {
			e.printStackTrace();
		}
	}


	private class SpinnerListener implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent event) {

			if (rView.getSpinnerValue() == 0) {

				aModel.setActiveJoints(0);
				rView.getSlider(0).setEnabled(false);
				rView.getSlider(1).setEnabled(false);
				rView.getSlider(2).setEnabled(false);
			}

			if (rView.getSpinnerValue() == 1) {

				aModel.setActiveJoints(1);
				rView.getSlider(0).setEnabled(true);
				rView.getSlider(1).setEnabled(false);
				rView.getSlider(2).setEnabled(false);
			}

			if (rView.getSpinnerValue() == 2) {

				aModel.setActiveJoints(2);
				rView.getSlider(0).setEnabled(true);
				rView.getSlider(1).setEnabled(true);
				rView.getSlider(2).setEnabled(false);
			}

			if (rView.getSpinnerValue() == 3) {

				aModel.setActiveJoints(3);
				rView.getSlider(0).setEnabled(true);
				rView.getSlider(1).setEnabled(true);
				rView.getSlider(2).setEnabled(true);
			}
		}

	} // END of class SpinnerListener

	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {

			if (event.getSource() == rView.getButLoad()) {
				System.out.println("Load");
				readXml();
				assignCalculation();
				dRobot.repaint();
			}

			if (event.getSource() == rView.getButSave()) {
				System.out.println("Save");
				saveXml();
			}
		}

	} // END of class ButtonListener

	private class SliderListener implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent event) {

			if (event.getSource() == rView.getSlider(0)) {
				aModel.setArmAngle(rView.getSlider(0).getValue(), 0);
				assignCalculation();
				//dRobot.setAngle(aModel.getArmAngle(1), 1);
				aModel.setAuxLine(false);
				rView.setChBox(aModel.getAuxLine());
				dRobot.repaint();
				
			}

			if (event.getSource() == rView.getSlider(1)) {
				aModel.setArmAngle(rView.getSlider(1).getValue(), 1);
				assignCalculation();
				//dRobot.setAngle(aModel.getArmAngle(1), 1); // TUTAJ ZMIANA PO COMPARE
				aModel.setAuxLine(false);
				rView.setChBox(aModel.getAuxLine());
				dRobot.repaint();
			}

			if (event.getSource() == rView.getSlider(2)) {
				aModel.setArmAngle(rView.getSlider(2).getValue(), 2);
				assignCalculation();
				//dRobot.setAngle(aModel.getArmAngle(1), 1);
				aModel.setAuxLine(false);
				rView.setChBox(aModel.getAuxLine());
				dRobot.repaint();
			}
		}
	} // END of class SliderListener

	private class MouseMotionListener implements
			java.awt.event.MouseMotionListener {

		@Override
		public void mouseDragged(MouseEvent e) {
/*
			c1 = new Circle((float) aModel.getxPosition(1), (float) aModel.getyPosition(1),  aModel.ARM_LENGTH); // (double centerX, double centerY, double radius)
			c2 = new Circle((float) e.getX(), (float) e.getY(), aModel.ARM_LENGTH);
			
			intersections = Circle.getIntersectionPoints(c1, c2);
	       // for (Point p: intersections) {
	       //     System.out.println(p);
	       // }
			
			if ( rView.getChBox().isSelected() == true ){
				aModel.setAuxLine(true);
				dRobot.setAuxLine(aModel.getAuxLine());
	        } else {
	        	aModel.setAuxLine(false);
	        	dRobot.setAuxLine(aModel.getAuxLine());
	        }
			
	        //aModel.setxPosition( (int)intersections[0].getX(), 2);
	        //aModel.setyPosition( (int)intersections[0].getY(), 2);
			try {
				dRobot.setPoint((int) Math.round(intersections[1].getX()), (int) Math.round(intersections[1].getY()), 2);
				
				dRobot.setPointTEST((int) Math.round(intersections[0].getX()), (int) Math.round(intersections[0].getY()), 2);
				
				// When exception occurs, this part is not executed
				// Arm is not being drawn, when no intersection points are available
				dRobot.setPoint(e.getX(), e.getY(), 3);
				
			} catch (ArrayIndexOutOfBoundsException ex) {
				//ex.printStackTrace();
				System.out.println("Out of bound exception occured mate");
				
				//dRobot.setPoint((int) intersections[1].getX(), (int) intersections[1].getY(), 2);
				
			} catch (Exception ex){
				//ex.printStackTrace();
			}
	        
	        //System.out.println("normalny 1 X: " + aModel.getxPosition(1) + " Y: " + aModel.getyPosition(1));
	        //System.out.println("Intersection X: " + intersections[1].getX() + " Y: " + intersections[1].getY());
	        //System.out.println("e X: " + e.getX() + " Y: " + e.getY());
	        
	        repaint();

	        System.out.println("//============================//");
*/
			
			
/* ************************************************************** */			
			/*
			dRobot.p[3] = new Point(e.getX(), e.getY());
			aModel.setxPosition(e.getX(), 3);
			aModel.setyPosition(e.getY(), 3);
			rView.setSlider(
					(int) Math.toDegrees(Math.atan2(aModel.getxPosition(3)
							- aModel.getxPosition(2), aModel.getyPosition(2)
							- aModel.getyPosition(3))), 2); // atan2(x1-x,y-y1))													
			repaint();
			*/
		}

		@Override
		public void mouseMoved(MouseEvent e) {			
			rView.setCordLab(e.getX(), e.getY());
		}

	} // END of class MouseMotionListener

	private class MouseListener implements java.awt.event.MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {

		}

		@Override
		public void mouseEntered(MouseEvent e) {

		}

		@Override
		public void mouseExited(MouseEvent e) {
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			
			c1 = new Circle((float) aModel.getxPosition(1), (float) aModel.getyPosition(1),  aModel.ARM_LENGTH); // (double centerX, double centerY, double radius)
			c2 = new Circle((float) e.getX(), (float) e.getY(), aModel.ARM_LENGTH);
			
			intersections = Circle.getIntersectionPoints(c1, c2);
	       // for (Point p: intersections) {
	       //     System.out.println(p);
	       // }
			
			if ( rView.getChBox().isSelected() == true ){
				aModel.setAuxLine(true);
				dRobot.setAuxLine(aModel.getAuxLine());
	        } else {
	        	aModel.setAuxLine(false);
	        	dRobot.setAuxLine(aModel.getAuxLine());
	        }
			
	        //aModel.setxPosition( (int)intersections[0].getX(), 2);
	        //aModel.setyPosition( (int)intersections[0].getY(), 2);
			try {
				dRobot.setPoint((int) Math.round(intersections[1].getX()), (int) Math.round(intersections[1].getY()), 2);
				
				dRobot.setPointTEST((int) Math.round(intersections[0].getX()), (int) Math.round(intersections[0].getY()), 2);
				
				// When exception occurs, this part is not executed
				// Arm is not being drawn, when no intersection points are available
				dRobot.setPoint(e.getX(), e.getY(), 3);
				
				
				//(int) Math.toDegrees(Math.atan2(aModel.getxPosition(i)
				//		- aModel.getxPosition(i-1), aModel.getyPosition(i-1)
				//		- aModel.getyPosition(i))), i-1); // atan2(x1-x,y-y1))
				
/************************************
 * TUTAJ MACHNAÆ ze nie getxPosition, tylko ma byæ e.getX(), intersection[0] itp 
 * 				bo nie przekazalem jeszcze do ArmModel tych wartosci X i Y
 */ //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
				
				 /*
				  * TO DO:
				  * - Slider changes value simultaneously with arms changing possition when 
				  * inverse kinematics are used (mouse click/drag)
				  * - 180 degree restriction for arms movement when mouse clicked/dragged
				  */
				
				/*
				aModel.setArmAngle(	(int) Math.toDegrees(Math.atan2(aModel.getxPosition(2)
						- aModel.getxPosition(1), aModel.getyPosition(1)
						- aModel.getyPosition(2))), 1);
				aModel.setArmAngle(	(int) Math.toDegrees(Math.atan2(aModel.getxPosition(3)
						- aModel.getxPosition(2), aModel.getyPosition(2)
						- aModel.getyPosition(3))), 2);

				rView.setSlider(aModel.getArmAngle(1), 1);
				rView.setSlider(aModel.getArmAngle(2), 2);
				*/

				
			} catch (ArrayIndexOutOfBoundsException ex) {
				//ex.printStackTrace();
				//System.out.println("Out of bound exception occured");
				
				//dRobot.setPoint((int) intersections[1].getX(), (int) intersections[1].getY(), 2);
				
			} catch (Exception ex){
				//ex.printStackTrace();
			}
	 
	        
	        repaint();

	        //System.out.println("//============================//");

		}

		@Override
		public void mouseReleased(MouseEvent e) {

		}

	} // END of class MouseListener
	
	private class ItemListener implements java.awt.event.ItemListener {

		@Override
		public void itemStateChanged(ItemEvent e) {

			if (e.getStateChange() == ItemEvent.SELECTED) {

				aModel.setAuxLine(true);
				dRobot.setAuxLine(aModel.getAuxLine());

			} else {
				aModel.setAuxLine(false);
				dRobot.setAuxLine(aModel.getAuxLine());
			}

			repaint();
		}

	}

	public void addMouseListener(MouseListener mouseListener) {
		dRobot.addMouseListener(mouseListener);
	}

	public void addMouseMotionListener(MouseMotionListener mouseListener) {
		dRobot.addMouseMotionListener(mouseListener);
	}

} // End of CLASS
