// ****************************//
// 2D robotic arm simulator, simple inverse kinematics applied 
// for 2 joints (2 degrees of freedom)
// ****************************//
// JDOM library was used for XML files 
// here -> http://www.jdom.org/dist/binary/jdom-2.0.5.zip
// ****************************//
public class Main {


	public static void main(String[] args) {
		
	
		
		ArmModel aModel = new ArmModel();
		RobotView rView = new RobotView();
		DrawRobot dRobot = new DrawRobot();
		UserControl uControl = new UserControl(aModel, rView, dRobot);

	}

}
