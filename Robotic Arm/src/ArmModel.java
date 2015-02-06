public class ArmModel {
	// Model

	private int[] armAngle = new int[3];
	private int[] xPosition = new int[4];
	private int[] yPosition = new int[4];
	private int activeJoints;
	private int mouseX, mouseY;

	public ArmModel() {

		armAngle[0] = 90;
		armAngle[1] = 90;
		armAngle[2] = 90;

		activeJoints = 3;

	}

	public int[] getxLength() {
		return xPosition;
	}

	public int getxPosition(int i) {
		return xPosition[i];
	}

	public void setxPosition(int xPosition, int i) {
		this.xPosition[i] = xPosition;
	}

	public int getyPosition(int i) {
		return yPosition[i];
	}

	public void setyPosition(int yPosition, int i) {
		this.yPosition[i] = yPosition;
	}

	public int getArmAngle(int i) {
		
				// atan2(x1-x,y-y1))
		armAngle[i]= (int) Math.toDegrees(Math.atan2(getxPosition(i+1)
				- getxPosition(i), getyPosition(i)
				- getyPosition(i+1)));
		
		return armAngle[i];
	}

	public void setArmAngle(int armAngle, int i) {
		this.armAngle[i] = armAngle;
	}

	public int getActiveJoints() {
		return activeJoints;
	}

	public void setActiveJoints(int activeJoints) {
		this.activeJoints = activeJoints;
	}

	public int getMouseX() {
		return mouseX;
	}

	public void setMouseX(int mouseX) {
		this.mouseX = mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}

	public void setMouseY(int mouseY) {
		this.mouseY = mouseY;
	}

	public void calcXandY(int startX, int startY, int index) {
		// Calculates next x & y point from previous x & y point
		this.xPosition[index] = (int) (startX + 45 * Math.cos(Math.toRadians((armAngle[index - 1] - 90))));
		this.yPosition[index] = (int) (startY + 45 * Math.sin(Math.toRadians((armAngle[index - 1] - 90))));
	}

} // END OF CLASS
