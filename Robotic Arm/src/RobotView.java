import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultFormatter;


@SuppressWarnings("serial")
public class RobotView extends JPanel {

	// View
	private JPanel panelLeft, panel1, panel2;
	private JButton butSave, butLoad;
	private JSlider slider[] = new JSlider[3];
	private JLabel  lab, labCordX, labCordY;
	private JCheckBox chBox;



	private JSpinner spinner;
	private SpinnerNumberModel spinModel;

	public RobotView() {

		setLayout(new BorderLayout());
		panelLeft = new JPanel();

		Box panelRight = Box.createVerticalBox();

		panel1 = new JPanel();
		GridBagConstraints gConstraints = new GridBagConstraints();
		panel1.setLayout(new GridBagLayout());

		panel2 = new JPanel();
		panel2.setLayout(new GridBagLayout());

		spinModel = new SpinnerNumberModel(3, 0, 3, 1); // (value, min, max,step)
		spinner = new JSpinner(spinModel);
		// http://stackoverflow.com/questions/3949382/jspinner-value-change-events
		// immediate update when spinner changes, even manually
		JComponent comp = spinner.getEditor();
		JFormattedTextField field = (JFormattedTextField) comp.getComponent(0);
		DefaultFormatter formatter = (DefaultFormatter) field.getFormatter();
		formatter.setCommitsOnValidEdit(true);

		butSave = new JButton("Zapisz");
		butLoad = new JButton("Wczytaj");
		lab = new JLabel("X and Y : ");
		labCordX = new JLabel("...");
		labCordY = new JLabel("...");
		chBox = new JCheckBox("Auxiliary lines", false);

		slider[0] = new JSlider(0, 180, 90); // (min, max, initial)
		slider[0].setMajorTickSpacing(90);
		slider[0].setMinorTickSpacing(15);
		slider[0].setPaintTicks(true);
		slider[0].setPaintLabels(true);
		slider[1] = new JSlider(0, 180, 90);
		slider[1].setMajorTickSpacing(90);
		slider[1].setMinorTickSpacing(15);
		slider[1].setPaintTicks(true);
		slider[1].setPaintLabels(true);
		slider[2] = new JSlider(0, 180, 90);
		slider[2].setMajorTickSpacing(90);
		slider[2].setMinorTickSpacing(15);
		slider[2].setPaintTicks(true);
		slider[2].setPaintLabels(true);

		gConstraints.gridx = 0;
		gConstraints.gridy = 0;
		gConstraints.gridheight = 5;
		gConstraints.gridwidth = 4;
		gConstraints.insets = new Insets(5, 0, 5, 0); // top, left, bottom, right
		panel1.add(new JLabel("Liczba stopni swobody"), gConstraints);
		gConstraints.gridx = 4;
		gConstraints.gridy = 0;
		gConstraints.gridwidth = 1;
		panel1.add(spinner, gConstraints);
		gConstraints.gridx = 0;
		gConstraints.gridy = 6;
		gConstraints.gridwidth = 2;
		panel1.add(butSave, gConstraints);
		gConstraints.gridx = 3;
		gConstraints.gridy = 6;
		gConstraints.gridwidth = 2;
		panel1.add(butLoad, gConstraints);
		
		gConstraints.gridx = 0;
		gConstraints.gridy = 12;
		panel1.add(lab, gConstraints);
		gConstraints.gridx = 2;
		//gConstraints.gridy = 12;
		panel1.add(labCordX, gConstraints);
		gConstraints.gridx = 4;
		panel1.add(labCordY, gConstraints);
		
		gConstraints.gridy = 18;
		gConstraints.gridx = 1;
		panel1.add(chBox, gConstraints);
		
		panel1.setBorder(BorderFactory.createLineBorder(Color.GRAY));

		gConstraints.gridx = 0;
		gConstraints.gridy = 0;
		gConstraints.gridheight = 5;
		gConstraints.gridwidth = 4;
		gConstraints.insets = new Insets(2, 5, 2, 5);
		panel2.add(new JLabel("Wêze³ 1"), gConstraints);
		gConstraints.gridx = 5;
		gConstraints.gridy = 0;
		panel2.add(slider[0], gConstraints);
		gConstraints.gridx = 0;
		gConstraints.gridy = 5;
		panel2.add(new JLabel("Wêze³ 2"), gConstraints);
		gConstraints.gridx = 5;
		gConstraints.gridy = 5;
		panel2.add(slider[1], gConstraints);
		gConstraints.gridx = 0;
		gConstraints.gridy = 11;
		panel2.add(new JLabel("Wêze³ 3"), gConstraints);
		gConstraints.gridx = 5;
		gConstraints.gridy = 11;
		panel2.add(slider[2], gConstraints);
		panel2.setBorder(BorderFactory.createLineBorder(Color.GRAY));

		panelRight.add(panel1);
		panelRight.add(panel2);
		add(panelRight, BorderLayout.EAST);

		setVisible(true);

	} // END OF CONSTRUCTOR

	public JCheckBox getChBox() {
		return chBox;
	}
	
	public void setChBox(boolean b) {
		chBox.setSelected(b);
	}
	
	
	public JPanel getPanelLeft() {
		return panelLeft;
	}

	public void setSlider(int angle, int i) {
		this.slider[i].setValue(angle);
	}

	public JSlider getSlider(int i) {
		return slider[i];
	}

	public int getSpinnerValue() {
		return (int) spinner.getValue();
	}

	public void setSpinnerValue(int i) {
		spinner.setValue(i);
	}

	public void setCordLab(int x, int y){
		labCordX.setText(Integer.toString(x));
		labCordY.setText(Integer.toString(y));
	}
	

	public void addSpinnerListener(ChangeListener spinnerListener) {
		// TODO Auto-generated method stub
		spinner.addChangeListener(spinnerListener);

	}

	public void addButtonListener(ActionListener listener) {
		butLoad.addActionListener(listener);
		butSave.addActionListener(listener);
	}

	public void addSliderListener(ChangeListener sliderListener) {
		slider[0].addChangeListener(sliderListener);
		slider[1].addChangeListener(sliderListener);
		slider[2].addChangeListener(sliderListener);
	}
	
	public void addItemListener(ItemListener itemListener) {  ///////////
		chBox.addItemListener(itemListener);
	}

	public JButton getButSave() {
		return butSave;
	}

	public JButton getButLoad() {
		return butLoad;
	}

} // END OF CLASS RobotView

