
public class Main {


	public static void main(String[] args) {
		
	
		
		ArmModel aModel = new ArmModel();
		RobotView rView = new RobotView();
		DrawRobot dRobot = new DrawRobot();
		UserControl uControl = new UserControl(aModel, rView, dRobot);

	}

}
