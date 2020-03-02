import java.text.DecimalFormat;

public class Shape {

	public int[] sides;
	public double[] angles;
	long time, startTime,endTime;
	public String shapename;
	public int shapesides;
	public String formatting = "###.###";


	public Shape(double SideA, double SideB, double SideC, double AngleA, double AngleB, double AngleC) {
		sides=new int[3];
		angles=new double[3];
		programstart.triangleCount++;
		this.shapesides = 3;
		this.shapename = "Triangle";
		this.sides[0]=(int) SideA;
		this.sides[1]=(int) SideB;
		this.sides[2]=(int) SideC;
		this.angles[0] = AngleC;
		this.angles[1] = AngleA;
		this.angles[2] = AngleB;
		draw();

	}

	public Shape(int length) {
		sides=new int[4];
		angles=new double[4];
		programstart.squareCount++;
		this.shapesides = 4;
		this.shapename = "Square";
		int i;
		for(i=0; i<4;i++) {
			this.sides[i]=length; 
			this.angles[i]=90;
		}
		draw();

	}

	public Shape(int length, int sidenumber) {
		this.shapename = sidenumber + "-sided polygon";
		this.shapesides = sidenumber;
		programstart.customCount++;
		angles = new double[sidenumber];
		sides = new int[sidenumber];
		for(int i=0;i<this.shapesides;i++) {
			this.sides[i]=length;
			this.angles[i]= (sidenumber-2)* 180 / sidenumber;
		}
		draw();
	}




	void draw() {
		startTime = System.currentTimeMillis();
		for(int i=0;i<this.shapesides;i++) {
			forward(this.sides[i]);
			rotate(this.angles[i]);
		}
		endTime = System.currentTimeMillis();
		time = endTime-startTime;

	}

	void forward(int length) {
		int time = length*85, finchSpeed = 100, finchHold=0;
		programstart.finch.setWheelVelocities(finchSpeed-3,finchSpeed,time); // my finch left wheel is slightly faster than right
		programstart.finch.setWheelVelocities(finchHold,finchHold,500);
	}

	void rotate(double angle) {

		double exangle = 180-angle;
		int time = (int) exangle*14, finchSpeed = 97, finchHold = 0;
		programstart.finch.setWheelVelocities(finchHold,finchSpeed,time);
		programstart.finch.setWheelVelocities(finchHold,finchHold,500);
	}


public double getTime() {
	return this.time;
	
}


	public double getSide(int side) {
		return this.sides[side-1];
	}

	public String getAngle(int angle) {
		DecimalFormat decimalFormat = new DecimalFormat(formatting);
		return decimalFormat.format(this.angles[angle-1]);

	}
}




