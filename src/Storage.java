import java.io.PrintWriter;
import java.util.ArrayList;



public class Storage {
	double semiperimeter,side1,side2,side3,area, currenthigh = 0; // various variables used in my math equations
	String memory="", largestShape;
	int i,j,k; //counters for i = shapes , j = sides , k = angles







	public void store(ArrayList<Shape> printout) {

		memory = memory + "List of shapes:"+"\r\n";
		for (i=0;i<printout.size();i++) { 
			memory = memory +"\r\n";
			memory = memory + printout.get(i).shapename + ": "; 
			if(printout.get(i).shapesides==3) {
				for (j=1;j<=printout.get(i).shapesides;j++) { 
					memory = memory + printout.get(i).getSide(j) + "cm, ";
				}
				String deg = "\u00b0"; 

				memory = memory + "  -- Angles: ";
				for (k=1; k<=3; k++) {
					memory = memory + printout.get(i).getAngle(k) + deg + ", ";
				}
			}else {
				k=4;
				memory = memory + printout.get(i).getSide(k) + "cm, ";
				String deg = "\u00b0"; 
				memory = memory + "  -- Angles: "+printout.get(i).getAngle(k) + deg ;
			}
		}
	}





	public void area(ArrayList<Shape> printout) {

		for (i=0;i<printout.size();i++) { 
			if(printout.get(i).shapesides == 3) { // uses Herons formula to calculate the area of a triangle 
				side1 = (double) printout.get(i).getSide(1);
				side2 = (double) printout.get(i).getSide(2);
				side3 = (double) printout.get(i).getSide(3);
				semiperimeter= (side1+side2+side3)/2;
				area = Math.sqrt((semiperimeter*(semiperimeter-side1)*(semiperimeter-side2)*(semiperimeter-side3)));}
			else {
				//area given only length and number of sides=  area = (s * s * n) / 4 tan(360 / (2 * n)  = 180 / n)  
				area = (printout.get(i).getSide(i+1) * printout.get(i).getSide(i+1) * printout.get(i).shapesides) / (4 * Math.tan(Math.PI / printout.get(i).shapesides));
			}


			if(area > currenthigh) {
				currenthigh = area;
				largestShape = printout.get(i).shapename;

			}

		}
		memory = memory + ("The largest shape was " + largestShape + " with an area of :" + Math.round(currenthigh* 100.0) / 100.0 + "cm^2.");
	}

	public void freq(int triangleCount, int squareCount, int customCount) { // compares frequency counts until the largest is found
		if(squareCount > triangleCount) {
			if(squareCount > customCount) {
				memory = memory + ("Most frequently drawn was Square drawn: " + squareCount + " times." );}
			else {
				memory = memory + ("Most frequently drawn was User-defined Polygons drawn: " + customCount + " times." );}
		}

		else if(squareCount < triangleCount){
			if(triangleCount>customCount) {
				memory = memory + ("Most frequently drawn was Triangle drawn: " + triangleCount + " times." );
			}else {
				memory = memory + ("Most frequently drawn was User-defined Polygons drawn: " + customCount + " times." );}
		}
	}

	public void spacemaker() {
		for(i=0;i<2;i++) { // creates new lines in the string so that it isn't just a wall of text
			memory = memory + "\r\n";
		}
	}

	public void time(ArrayList<Shape> printout) { // calculates average time by adding each time and dividing by number of times
		double avgTime=0;
		for(i=0;i<printout.size();i++) {
			avgTime = avgTime + printout.get(i).getTime();
		}
		avgTime = (avgTime/printout.size())/1000;
		memory = memory + ("The average time taken to draw a shape was " + avgTime + " seconds");
	}



	public void print() { // prints to a text file.
		try{    
			PrintWriter pr = new PrintWriter("ttt.txt");    

			pr.print(memory);    
			pr.close();    
		}catch(Exception e){System.out.println(e);}    
		System.out.println("Success...");   
	}






}
