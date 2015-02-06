import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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


public class UserControl extends JFrame {
	// Controller

	private ArmModel aModel;
	private RobotView rView;
	private DrawRobot dRobot;

	private String[] y = new String[3];

	public UserControl(ArmModel a, RobotView r, DrawRobot d) {

		aModel = a;
		rView = r;
		dRobot = d;

		aModel.setxPosition(50, 0);
		aModel.setyPosition(150, 0);

		assignCalculation();

		rView.addSpinnerListener(new SpinnerListener());
		rView.addButtonListener(new ButtonListener());
		rView.addSliderListener(new SliderListener());
		dRobot.addMouseMotionListener(new MouseMotionListener());
		dRobot.addMouseListener(new MouseListener());

		initWindowPanel();

	} // End of constructor

	private void initWindowPanel() {

		setLayout(new BorderLayout());
		add(rView, BorderLayout.EAST);
		add(rView.getPanelLeft().add(dRobot), BorderLayout.CENTER); 														
		setTitle("Robotic Arm");
		setSize(500, 325);
		setLocationRelativeTo(null);
		setResizable(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setVisible(true);

	} // End of init method

	private void assignCalculation() {

			// Calculates following points and stores in model
		for (int idx = 1; idx < aModel.getxLength().length; idx++) { 
			aModel.calcXandY(aModel.getxPosition(idx - 1),
					aModel.getyPosition(idx - 1), idx);
		}
		setDrawPoints();

	}

	private void setDrawPoints() {
		for (int i = 0; i < aModel.getxLength().length; i++) {
			dRobot.setPoint(aModel.getxPosition(i), aModel.getyPosition(i), i);
		}
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
			aModel.setxPosition(aModel.getxPosition(1), 1);
			aModel.setyPosition(aModel.getyPosition(1), 1);

			aModel.calcXandY(aModel.getxPosition(1), aModel.getyPosition(1), 2);
			aModel.setxPosition(aModel.getxPosition(2), 2);
			aModel.setyPosition(aModel.getyPosition(2), 2);

			setDrawPoints(); // sets points from model and sends them to
								// DrawRobot for drawing arm

		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
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

	// ///////////////////////////////////////////////////////////////////////////////////////////

	private class SpinnerListener implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent event) {
			// TODO Auto-generated method stub

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
			// TODO Auto-generated method stub

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

	}

	private class SliderListener implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent event) {
			// TODO Auto-generated method stub

			if (event.getSource() == rView.getSlider(0)) {
				aModel.setArmAngle(rView.getSlider(0).getValue(), 0);
				assignCalculation();
				
				dRobot.repaint();
			}

			if (event.getSource() == rView.getSlider(1)) {
				aModel.setArmAngle(rView.getSlider(1).getValue(), 1);
				assignCalculation();
				dRobot.setAngle(aModel.getArmAngle(1), 1); /////***/////////******///
				dRobot.repaint();
			}

			if (event.getSource() == rView.getSlider(2)) {
				aModel.setArmAngle(rView.getSlider(2).getValue(), 2);
				assignCalculation();
				dRobot.repaint();
			}
		}
	}

	private class MouseMotionListener implements
			java.awt.event.MouseMotionListener {

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			dRobot.p[3] = new Point(e.getX(), e.getY());
			aModel.setxPosition(e.getX(), 3);
			aModel.setyPosition(e.getY(), 3);
			rView.setSlider(
					(int) Math.toDegrees(Math.atan2(aModel.getxPosition(3)
							- aModel.getxPosition(2), aModel.getyPosition(2)
							- aModel.getyPosition(3))), 2); // atan2(x1-x,y-y1))													
			repaint();
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

	private class MouseListener implements java.awt.event.MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			dRobot.p[3] = new Point(e.getX(), e.getY());
			aModel.setxPosition(e.getX(), 3);
			aModel.setyPosition(e.getY(), 3);
			rView.setSlider(
					(int) Math.toDegrees(Math.atan2(aModel.getxPosition(3)
							- aModel.getxPosition(2), aModel.getyPosition(2)
							- aModel.getyPosition(3))), 2); // atan2(x1-x,y-y1))	
			
			repaint();

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
		}

	}

	public void addMouseListener(MouseListener mouseListener) {
		// TODO Auto-generated method stub
		dRobot.addMouseListener(mouseListener);
	}

	public void addMouseMotionListener(MouseMotionListener mouseListener) {
		// TODO Auto-generated method stub
		dRobot.addMouseMotionListener(mouseListener);
	}

} // End of CLASS
