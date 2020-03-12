import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;

import edu.cmu.ri.createlab.terk.robot.finch.Finch;


public class programstart extends JFrame implements KeyListener {
	//shared GUI components

	GridBagConstraints cell;
	JFrame frame;
	JPanel Panel;
	JLabel side1Label, side2Label, helpLabel = new JLabel("");
	JTextField side1Text = new JTextField(10),side2Text = new JTextField(10);
	JButton mainButton, backButton = new JButton("Back");
	JLabel ranges = new JLabel("Numbers between 15 and 85");

	//arraylist of the Shape class for each instance
	static ArrayList<Shape> Storage = new ArrayList<Shape>();
	//Counters of shapes drawn and min/max lengths allowed
	static int triangleCount=0, squareCount=0, customCount=0;
	final static int maxLength = 85, minLength = 15; 

	static Finch finch = new Finch();	

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		
		if(!finch.isFinchLevel()) { // waits until finch is level
			JLabel starter = new JLabel("To begin place finch flat."); //GUI prompt to lay finch flat to start the program
			JPanel startPanel = new JPanel();
			JFrame strtMsgFrame = new JFrame("Program start");
			strtMsgFrame.setLayout(new FlowLayout());
			strtMsgFrame.add(startPanel);
			startPanel.add(starter);
			strtMsgFrame.setSize(300,100);
			strtMsgFrame.show();
			while(!finch.isFinchLevel()) {
				int wait = 300;
				finch.sleep(wait);
				}
			
			strtMsgFrame.setVisible(false);
		}
		programstart mystuff = new programstart(); // makes object of class

		mystuff.mainmenu();

	}





	@SuppressWarnings("deprecation")
	//creating GUI for main menu for users to enter the shape they want drawn
	public void mainmenu() {

		frame = new JFrame("Draw a Shape");
		frame.setLayout(new FlowLayout());

		Panel = new JPanel(new GridBagLayout());
		cell = new GridBagConstraints();
		frame.add(Panel);
		JLabel selectionLabel = new JLabel("<HTML>Enter Selection: <br>S - Square</br><br>T - Triangle</br><br>C - Custom</br><br>Q - Quit</br></HTML>");
		JTextField selectionText = new JTextField(10);
		selectionText.addKeyListener(this);
		selectionText.setText("");
		side1Text.addKeyListener(this); // adds keylisteners for the F1 help file
		side2Text.addKeyListener(this);
		backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {


			public void actionPerformed(ActionEvent e) {
				frame.dispose();// disposes of old frames before new frames are drawn	
				reset(); // resets form components for next form
				mainmenu();

			}

		});
		mainButton = new JButton("Select Shape");
		mainButton.addActionListener(new ActionListener() {
			//checks user input in text field and compares to cases to allow upper and lower cases 
			public void actionPerformed(ActionEvent e) {
				
				try {

					switch(selectionText.getText()) {
					case "s":
					case "S":

						frame.dispose();				
						reset();
						squareGUI();

						break;
					case "t":
					case "T":
						frame.dispose();
						reset();
						triangleGUI();
						break;
					case "C":
					case "c":
						frame.dispose();
						reset();
						customGUI();
						break;

					case "q":
					case "Q":
						System.out.println("quit");
						quit();
						break;
					default: 

						helpLabel.setText("<HTML>Error: Please use only <br>S, T, C or Q.</br></html>");
						finch.setLED(Color.RED, 500);
					}

				}
				catch(Exception y) {
					helpLabel.setText("Enter correct values.");
					finch.setLED(Color.RED, 500);


				}
			}
		}); //format for GUI
		cell.fill = GridBagConstraints.HORIZONTAL;
		cell.gridx = 2;
		cell.gridy = 0; // more form components layout manager
		cell.insets = new Insets(3,3,3,3);
		Panel.add(selectionLabel,cell);
		cell.gridx = 3;
		Panel.add(selectionText,cell);
		cell.gridx = 3;
		cell.gridy = 2;
		Panel.add(mainButton,cell);
		cell.gridy = 1;
		Panel.add(helpLabel,cell);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.setSize(700,250);
		frame.show();
	}

	@SuppressWarnings("deprecation")
	// Square GUI created when selected in main menu
	public void squareGUI() { 
		frame = new JFrame("Square");
		Panel = new JPanel(new GridBagLayout());
		side1Text = new JTextField(10);
		mainButton = new JButton("Draw Square");
		mainButton.addActionListener(new ActionListener() { // validation on button click
			public void actionPerformed(ActionEvent e) {
				try {
					int sideCheck = Integer.parseInt(side1Text.getText());
					if(sideCheck<=maxLength && sideCheck>=minLength) {
						Storage.add(new Shape(sideCheck));
						frame.dispose();
						reset();
						mainmenu();
					}else {

						helpLabel.setText("Error: Please use numbers between 15 and 85");
						finch.setLED(Color.RED, 500);
					}
				}catch(Exception y) {
					helpLabel.setText("Please use numbers only.");
					finch.setLED(Color.RED, 500);

				}
			}
		});

		JLabel sideLabel = new JLabel("Length of sides in CM:");
		cell = new GridBagConstraints();
		//format for GUI
		frame.add(Panel);
		
		cell.fill = GridBagConstraints.HORIZONTAL;

		cell.insets = new Insets(3,3,3,3);
		cell.gridx = 1;
		cell.gridy = 0;
		Panel.add(ranges,cell);
		cell.gridy = 1;
		Panel.add(sideLabel, cell);
		cell.gridx = 2;
		Panel.add(side1Text,cell);
		cell.gridy = 2;
		Panel.add(helpLabel , cell);
		cell.gridy = 3;
		Panel.add(mainButton,cell);
		cell.gridx = 3;
		Panel.add(backButton,cell);








		frame.setSize(700,250);
		frame.show();
	}

	@SuppressWarnings("deprecation")
	//create GUI for triangle when selected in main menu
	void triangleGUI() {
		JLabel side3Label = new JLabel("Side C (CM): ");
		JTextField side3Text = new JTextField(10);
		side3Text.setText("");
		side3Text.addKeyListener(this);
		frame = new JFrame("Triangle");
		Panel = new JPanel(new GridBagLayout());
		mainButton = new JButton("Draw Triangle");
		mainButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					double AngleA, AngleB, AngleC;
					int tricheck;
					final int aTriangle = 180;
					int side1 = Integer.parseInt(side1Text.getText());
					int side2 = Integer.parseInt(side2Text.getText());
					int side3 = Integer.parseInt(side3Text.getText());
					if(side1 <= maxLength && side1 >= minLength && side2 <= maxLength && side2 >= minLength && side3 <= maxLength && side3 >= minLength) {
						double SideA = (double) side1;
						double SideB = (double) side2;
						double SideC = (double) side3;
						if(SideA ==SideB && SideA == SideC) {
							//rounds for equilateral but not any others as java todgrees screws up equilateral triangles
							AngleA = Math.round((Math.toDegrees(Math.acos((Math.pow(SideB,2)+Math.pow(SideC,2)-Math.pow(SideA,2))/(2*SideB*SideC)))));
							AngleB = Math.round((Math.toDegrees(Math.acos((Math.pow(SideA,2)+Math.pow(SideC,2)-Math.pow(SideB,2))/(2*SideA*SideC)))));
							AngleC = Math.round((Math.toDegrees(Math.acos((Math.pow(SideB,2)+Math.pow(SideA,2)-Math.pow(SideC,2))/(2*SideB*SideA)))));
						}
						else {//had to split equation as certain triangles were rejected due to rounding errors in the todegrees function when two angles would be rounded down
							AngleA = (Math.round(Math.toDegrees(Math.acos((Math.pow(SideB,2)+Math.pow(SideC,2)-Math.pow(SideA,2))/(2*SideB*SideC)))* 100.0) / 100.0);
							AngleB = (Math.round(Math.toDegrees(Math.acos((Math.pow(SideA,2)+Math.pow(SideC,2)-Math.pow(SideB,2))/(2*SideA*SideC)))* 100.0) / 100.0);
							AngleC =  (Math.round(Math.toDegrees(Math.acos((Math.pow(SideB,2)+Math.pow(SideA,2)-Math.pow(SideC,2))/(2*SideB*SideA)))* 100.0) / 100.0);
						}
						tricheck = (int) (AngleA+AngleB+AngleC);
						if(tricheck == aTriangle) {	// if angles make a triangle then create new instance of the object.
							Storage.add(new Shape(SideA, SideB, SideC, AngleA, AngleB, AngleC));
							frame.dispose();						
							reset();
							mainmenu();
						}else {
							helpLabel.setText("<HTML>Chosen sides cannot make a triangle <br>Please try again.</br></HTML>");
							finch.setLED(Color.RED, 500);
						}

					}else {
						helpLabel.setText("Error: Please use numbers between 15 and 85");
						finch.setLED(Color.RED, 500);
					}

				}catch(Exception y) {
					helpLabel.setText("Please use numbers only.");
					finch.setLED(Color.RED, 500);
				}

			}
		});

		side1Label = new JLabel("Side A (CM): ");
		side2Label = new JLabel("Side B (CM): ");
		
		cell = new GridBagConstraints();
		frame.add(Panel);
		//format for GUI
		cell.fill = GridBagConstraints.HORIZONTAL;
		cell.insets = new Insets(3,3,3,3);
		cell.gridy = 1;
		cell.gridx = 1;
		Panel.add(ranges,cell);
		cell.gridx = 1;
		cell.gridy = 2;
		Panel.add(side1Label,cell);
		cell.gridy = 3;
		Panel.add(side2Label,cell);
		cell.gridy = 4;
		Panel.add(side3Label,cell);
		cell.gridx = 2;
		cell.gridy = 2;
		Panel.add(side1Text,cell);
		cell.gridy = 3;
		Panel.add(side2Text,cell);
		cell.gridy = 4;
		Panel.add(side3Text,cell);
		cell.gridx = 2;
		cell.gridy = 5;
		Panel.add(helpLabel,cell);
		cell.gridy = 6;
		Panel.add(mainButton,cell);
		cell.gridx = 3;
		Panel.add(backButton,cell);




		frame.setSize(700,250);
		frame.show();
	}

	@SuppressWarnings("deprecation")
	// Creates GUI for when user selects custom option in main menu 
	void customGUI() { 
		
		frame = new JFrame("Custom Shape");
		Panel = new JPanel(new GridBagLayout());
		mainButton = new JButton("Draw Shape");
		mainButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try { // checks side lengths and number of sides
					int minSides = 5;
					int sideCheck = Integer.parseInt(side1Text.getText());
					int numberOfSides = Integer.parseInt(side2Text.getText());
					if(sideCheck<=maxLength && sideCheck>=minLength && numberOfSides >=minSides) {
						Storage.add(new Shape(sideCheck, numberOfSides));
						frame.dispose();						
						reset();
						mainmenu();
					}else if(sideCheck>=maxLength && sideCheck<=minLength) {
						helpLabel.setText("<html>Error: <br>Please use numbers between 15 and 85</br></html>");
						finch.setLED(Color.RED, 500);
					}else {
						helpLabel.setText("<html>Error: <br>Number of sides should be greater than 4</br></html>");
						finch.setLED(Color.RED, 500);
					}
				}catch(Exception y) {
					helpLabel.setText("Please use numbers only.");
					finch.setLED(Color.RED, 500);

				}
			}
		});
		//format for GUI
		side1Label = new JLabel("Length of sides in CM (15-85): ");
		side2Label = new JLabel("Number of sides: ");
		cell = new GridBagConstraints();
		cell.fill = GridBagConstraints.HORIZONTAL;
		cell.insets = new Insets(3,3,3,3);
		cell.gridx = 1;
		cell.gridy = 0;
		frame.add(Panel);
		cell.gridy = 1;
		Panel.add(side1Label,cell);
		cell.gridy = 2;
		Panel.add(side2Label,cell);
		cell.gridx = 2;
		cell.gridy = 1;
		Panel.add(side1Text,cell);
		cell.gridy = 2;
		Panel.add(side2Text,cell);
		cell.gridy = 3;
		Panel.add(helpLabel,cell);
		cell.gridy = 4;
		Panel.add(mainButton,cell);
		cell.gridx = 3;
		Panel.add(backButton,cell);


		frame.setSize(700,250);
		frame.show();

	}

	void quit() { // writes to text file by creating Storage instance and passing the ArrayList of shapes
		Storage Print = new Storage();
		Print.store(Storage);
		Print.spacemaker();
		Print.area(Storage);
		Print.spacemaker();
		Print.freq(triangleCount, squareCount, customCount);
		Print.spacemaker();
		Print.time(Storage);
		Print.print();
		System.exit(0);

	}



	void reset() { // resets form components and flashes finch light green as this is only called for correct inputs
		finch.setLED(Color.green,500);
		side1Text.setText("");
		side2Text.setText("");
		helpLabel.setText("");


	}


	@Override
	public void keyTyped(KeyEvent e) {
		// Doesn't need to do anything

	}

	@Override
	//this method waits for key event to open helpfile.pdf
	public void keyPressed(KeyEvent e) { // opens user manual on F1 press
		if (e.getKeyCode()==KeyEvent.VK_F1) {
			try {
				Desktop.getDesktop().open(new File("helpfile.pdf"));
			}catch (IOException ioe) {
				ioe.printStackTrace();
			}

		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// Doesn't need to do anything

	}



}
