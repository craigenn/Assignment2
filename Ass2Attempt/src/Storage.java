import java.io.PrintWriter;
import java.util.ArrayList;



public class Storage {
	double semiperimeter,side1,side2,side3,area, currenthigh = 0; // various variables used in my math equations
	String memory="", largestShape;
	int i,j,k; //counters for i = shapes , j = sides , k = angles






	//retrieve stored data in file
	public void store(ArrayList<Shape> printout) {
		final String degree = "\u00b0"; 
		memory = memory + "List of shapes:"+"\r\n";
		for (i=0;i<printout.size();i++) {  // for loop to retrieve shape name and length of sides along with angles of shape
			memory = memory +"\r\n";
			memory = memory + printout.get(i).shapename + ": "; 
			if(printout.get(i).shapesides==3) {
				for (j=1;j<=printout.get(i).shapesides;j++) { 
					memory = memory + printout.get(i).getSide(j) + "cm, ";
				}

				memory = memory + "  -- Angles: ";
				for (k=1; k<=3; k++) {
					memory = memory + printout.get(i).getAngle(k) + degree + ", ";
				}// i = shape name j = shape side length k = shape angle
			}else {
				k=4;
				memory = memory + printout.get(i).getSide(k) + "cm, ";
				 
				memory = memory + "  -- Angles: "+printout.get(i).getAngle(k) + degree ;
			}
		}
	}




	//calculates the area of shapes 
	public void area(ArrayList<Shape> printout) {
		String shapeLength, largestLength="";
		for (i=0;i<printout.size();i++) { 
			if(printout.get(i).shapesides == 3) { // uses Herons formula to calculate the area of a triangle given only sides
				side1 = (double) printout.get(i).getSide(1);
				side2 = (double) printout.get(i).getSide(2);
				side3 = (double) printout.get(i).getSide(3);
				semiperimeter= (side1+side2+side3)/2;
				area = Math.sqrt((semiperimeter*(semiperimeter-side1)*(semiperimeter-side2)*(semiperimeter-side3)));
				shapeLength = String.valueOf(printout.get(i).getSide(1)) + ", " + String.valueOf(printout.get(i).getSide(2)) + ", " + String.valueOf(printout.get(i).getSide(3));


			}
			else {
				//area given only length and number of sides=  area = (s * s * n) / 4 tan(360 / (2 * n)  = 180 / n)  
				area = (printout.get(i).getSide(i+1) * printout.get(i).getSide(i+1) * printout.get(i).shapesides) / (4 * Math.tan(Math.PI / printout.get(i).shapesides));
				shapeLength = String.valueOf(printout.get(i).getSide(1));
			}


			if(area > currenthigh) { // if area just calculated is higher than currenthigh then currenthigh gets replaced by the new highest
				currenthigh = area;
				largestShape = printout.get(i).shapename;
				largestLength = shapeLength;
			}
		}
		memory = memory + ("The largest shape was " + largestShape + ": "+ largestLength + "cm with an area of :" + Math.round(currenthigh* 100.0) / 100.0 + "cm^2.");
	}

	// compares frequency counts until the largest is found including equals
	public void freq(int triangleCount, int squareCount, int customCount) { 
		if(squareCount > triangleCount && squareCount > customCount) {
			memory = memory + ("Most frequently drawn was Square drawn: " + squareCount + " times." );
		}else if(triangleCount > squareCount && triangleCount > customCount) {
			memory = memory + ("Most frequently drawn was Triangle drawn: " + triangleCount + " times." );
		}else if(customCount > squareCount && customCount > triangleCount) {
			memory = memory + ("Most frequently drawn was User-defined Polygons drawn: " + customCount + " times." );
		}else if(squareCount == triangleCount && squareCount != customCount) {
			memory = memory + ("Square and Triangle were both drawn " + squareCount + " times." );
		}else if(squareCount == customCount && squareCount != triangleCount) {
			memory = memory + ("Square and User-defined Polygons were both drawn " + squareCount + " times." );
		}else if(triangleCount == customCount && triangleCount != squareCount) {
			memory = memory + ("Triangle and User-defined Polygons were both drawn " + triangleCount + " times." );
		}else {
			memory = memory + ("All shapes were drawn " + triangleCount + " times." );
		}
	}

	public void spacemaker() {
		for(i=0;i<2;i++) { // creates new lines in the string so that it isn't just a wall of text
			memory = memory + "\r\n";
		}
	}
	
	// calculates average time by adding each time and dividing by number of times
	public void time(ArrayList<Shape> printout) { 
		double avgTime=0;
		for(i=0;i<printout.size();i++) {
			avgTime = avgTime + printout.get(i).getTime();
		}
		avgTime = (avgTime/printout.size())/1000;
		memory = memory + ("The average time taken to draw a shape was " + avgTime + " seconds");
	}


	// prints data to ShapeData.txt text file so the program can access it later
	public void print() { 
		try{    
			PrintWriter printer = new PrintWriter("ShapeData.txt");    

			printer.print(memory);    
			printer.close();    
		}catch(Exception e){System.out.println(e);}    
		System.out.println("Success...");   
	}






}
