public class ArmModel {
	// Model

	private int[] armAngle = new int[3];
	private int[] xPosition = new int[4];
	private int[] yPosition = new int[4];
	private int activeJoints;
	private int mouseX, mouseY;
	private boolean auxLine;
	public final int ARM_LENGTH = 45;

	public ArmModel() {

		armAngle[0] = 90;
		armAngle[1] = 90;
		armAngle[2] = 90;

		activeJoints = 3;
		
		setAuxLine(false);

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
		
		//(int) Math.toDegrees(Math.atan2(aModel.getxPosition(i)
		//		- aModel.getxPosition(i-1), aModel.getyPosition(i-1)
		//		- aModel.getyPosition(i))), i-1); // atan2(x1-x,y-y1))
		
			// **** Ten fragment psuje LOAD z XML 
			// ***  i jeszcze sie okazuje ze z Save, wiec wypierdalam
		/*armAngle[i]= (int) Math.toDegrees(Math.atan2(getxPosition(i+1)
				- getxPosition(i), getyPosition(i)
				- getyPosition(i+1)));
		*/
		
		
		// ****** TUTUTAJ TEZ OGARNAC CO INDEX OUT OF BOUND
		// Najwyzej zamienic i na i-1, a i+1 na i.
		
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
		this.xPosition[index] = (int) (startX + 45 * Math.cos(Math.toRadians((armAngle[index - 1])))); // Math.cos(Math.toRadians((armAngle[index - 1] - 90))));
		this.yPosition[index] = (int) (startY + 45 * Math.sin(Math.toRadians((armAngle[index - 1]))));
		
		
		// PRÓBA ********************//
		
		//System.out.println("Index wynosi: " + index + ", wchodzimy do IFow");
		

		
		//if (index == 1){
					// X -> 45 * cos(alfa1) + 45 * cos(alfa1 - alfa2)
					// Y -> 45 * sin(alfa1) + 45 * sin(alfa1 - alfa2)
			this.xPosition[2] = (int) (xPosition[0] + ( (45 * Math.cos(Math.toRadians(armAngle[0]))) + (45 * Math.cos(Math.toRadians(armAngle[0] - armAngle[1]))) ));
			this.yPosition[2] = (int) (yPosition[0] + ( (45 * Math.sin(Math.toRadians(armAngle[0]))) + (45 * Math.sin(Math.toRadians(armAngle[0] - armAngle[1]))) ));
			
			this.xPosition[3] = (int) (xPosition[0] + ( (45 * Math.cos(Math.toRadians(armAngle[0]))) + (45 * Math.cos(Math.toRadians(armAngle[0] - armAngle[1])) + (45 * Math.cos(Math.toRadians(armAngle[0] - armAngle[1] - armAngle[2])))) ));
			this.yPosition[3] = (int) (yPosition[0] + ( (45 * Math.sin(Math.toRadians(armAngle[0]))) + (45 * Math.sin(Math.toRadians(armAngle[0] - armAngle[1])) + (45 * Math.sin(Math.toRadians(armAngle[0] - armAngle[1] - armAngle[2])))) ));
			/*
			this.xPosition[2] = (int) (startX + ((45 * Math.cos(Math.toRadians((armAngle[1] - 90) + (armAngle[0])))) + (45 * Math.cos(Math.toRadians((armAngle[1] - 90) - (armAngle[0]))))) );
			this.yPosition[2] = (int) (startY + ((45 * Math.sin(Math.toRadians((armAngle[1] - 90) + (armAngle[0])))) + (45 * Math.sin(Math.toRadians((armAngle[1] - 90) - (armAngle[0]))))) );
			
			this.xPosition[3] = (int) (startX + ((45 * Math.cos(Math.toRadians((armAngle[2] - 90) + (armAngle[1])))) + (45 * Math.cos(Math.toRadians((armAngle[2] - 90) - (armAngle[1]))))) );
			this.yPosition[3] = (int) (startY + ((45 * Math.sin(Math.toRadians((armAngle[2] - 90) + (armAngle[1])))) + (45 * Math.sin(Math.toRadians((armAngle[2] - 90) - (armAngle[1]))))) );
			
			System.out.println("IF 1, INDEX: " + index);
			System.out.println("Pozycja 1 x: " + xPosition[1] + " y: " + yPosition[1]);
			System.out.println("Pozycja 2 x: " + xPosition[2] + " y: " + yPosition[2]);
			System.out.println("Pozycja 3 x: " + xPosition[3] + " y: " + yPosition[3]); */
		//}
		/*
		if (index == 2){

			this.xPosition[3] = (int) (startX + ((45 * Math.cos(Math.toRadians((armAngle[2] - 90) + (armAngle[1])))) + (45 * Math.cos(Math.toRadians((armAngle[2] - 90) - (armAngle[1]))))) );
			this.yPosition[3] = (int) (startY + ((45 * Math.sin(Math.toRadians((armAngle[2] - 90) + (armAngle[1])))) + (45 * Math.sin(Math.toRadians((armAngle[2] - 90) - (armAngle[1]))))) );
			
			System.out.println("IF 2, INDEX: " + index);
			System.out.println("Pozycja 3 x: " + xPosition[3] + " y: " + yPosition[3]);			
		}
		
/*		if (index == 3){

			this.xPosition[3] = (int) (startX + ((45 * Math.cos(Math.toRadians((armAngle[2] - 90) + (armAngle[1])))) + (45 * Math.cos(Math.toRadians((armAngle[2] - 90) - (armAngle[1]))))) );
			this.yPosition[3] = (int) (startY + ((45 * Math.sin(Math.toRadians((armAngle[2] - 90) + (armAngle[1])))) + (45 * Math.sin(Math.toRadians((armAngle[2] - 90) - (armAngle[1]))))) );
			
		
		}*/
		//********            *******//  
		
	}

	public boolean getAuxLine() {
		return auxLine;
	}

	public void setAuxLine(boolean auxLine) {
		this.auxLine = auxLine;
	}

} // END OF CLASS
