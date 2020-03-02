import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;

//import edu.cmu.ri.createlab.terk.robot.finch.Finch;
import com.birdbraintechnologies.Finch;

public class programstart extends JFrame implements KeyListener {
	//gui components
	GridBagConstraints c;
	JFrame frame;
	JPanel Panel;
	JLabel side1Label, side2Label, helpLabel = new JLabel("");
	JTextField side1Text = new JTextField(10),side2Text = new JTextField(10);
	JButton mainButton, backButton = new JButton("Back");
	JLabel ranges = new JLabel("Numbers between 15 and 85");



	static ArrayList<Shape> Storage = new ArrayList<Shape>();
	static int triangleCount=0, squareCount=0, customCount=0, maxLength = 85, minLength = 15;


	static Finch finch = new Finch();	

	public static void main(String[] args) {

		if(!finch.isFinchLevel()) {
			JLabel starter = new JLabel("To begin place finch flat.");
			JPanel startPanel = new JPanel();
			JFrame strtMsgFrame = new JFrame("Program start");
			strtMsgFrame.setLayout(new FlowLayout());
			strtMsgFrame.add(startPanel);
			startPanel.add(starter);
			strtMsgFrame.setSize(200,100);
			strtMsgFrame.show();
			while(!finch.isFinchLevel()) {
				int wait = 300;
				try {
					Thread.sleep(wait); //sleep thread
				} 
				catch(InterruptedException ex) {
					Thread.currentThread().interrupt();
				}
			}
			strtMsgFrame.setVisible(false);
		}
		programstart mystuff = new programstart();
		mystuff.mainmenu();

	}


	public void mainmenu() {

		frame = new JFrame("Draw a Shape");
		frame.setLayout(new FlowLayout());

		Panel = new JPanel(new GridBagLayout());
		c = new GridBagConstraints();
		frame.add(Panel);
		JLabel selectionLabel = new JLabel("<HTML>Enter Selection: <br>S - Square</br><br>T - Triangle</br><br>C - Custom</br><br>Q - Quit</br></HTML>");
		JTextField selectionText = new JTextField(10);
		selectionText.addKeyListener(this);
		selectionText.setText("");
		side1Text.addKeyListener(this);
		side2Text.addKeyListener(this);
		mainButton = new JButton("Select Shape");
		mainButton.addActionListener(new ActionListener() {
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
					helpLabel.setText("Enter correct stuff");
					finch.setLED(Color.RED, 500);


				}
			}
		});
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 0;
		c.insets = new Insets(3,3,3,3);
		Panel.add(selectionLabel,c);
		c.gridx = 3;
		Panel.add(selectionText,c);
		c.gridx = 3;
		c.gridy = 2;
		Panel.add(mainButton,c);
		c.gridy = 1;
		Panel.add(helpLabel,c);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.setSize(700,200);
		frame.show();
	}

	public void squareGUI() {
		frame = new JFrame("Square");
		Panel = new JPanel(new GridBagLayout());
		side1Text = new JTextField(10);
		mainButton = new JButton("Draw Square");
		mainButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int sideCheck = Integer.parseInt(side1Text.getText());
					if(sideCheck<=maxLength && sideCheck>=minLength) {
						Storage.add(new Shape(sideCheck));
						reset();
						frame.dispose();
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
		backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				reset();
				frame.dispose();
				mainmenu();

			}

		});
		JLabel sideLabel = new JLabel("Length of sides in CM:");
		c = new GridBagConstraints();

		frame.add(Panel);

		c.fill = GridBagConstraints.HORIZONTAL;

		c.insets = new Insets(3,3,3,3);
		c.gridx = 1;
		c.gridy = 0;
		Panel.add(ranges,c);
		c.gridy = 1;
		Panel.add(sideLabel, c);
		c.gridx = 2;
		Panel.add(side1Text,c);
		c.gridy = 2;
		Panel.add(helpLabel , c);
		c.gridy = 3;
		Panel.add(mainButton,c);
		c.gridx = 3;
		Panel.add(backButton,c);








		frame.setSize(700,200);
		frame.show();
	}

	void triangleGUI() {
		JLabel side3Label = new JLabel("Side C (CM): ");
		JTextField side3Text = new JTextField(10);
		side3Text.setText("");
		side3Text.addKeyListener(this);
		frame = new JFrame("Triangle");
		Panel = new JPanel(new GridBagLayout());
		backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				reset();
				frame.dispose();
				mainmenu();

			}

		});
		mainButton = new JButton("Draw Triangle");
		mainButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					double AngleA, AngleB, AngleC;
					int tricheck, aTriangle = 180;
					int side1 = Integer.parseInt(side1Text.getText());
					int side2 = Integer.parseInt(side2Text.getText());
					int side3 = Integer.parseInt(side3Text.getText());
					if(side1 <= maxLength && side1 >= minLength && side2 <= maxLength && side2 >= minLength && side3 <= maxLength && side3 >= minLength) {
						double SideA = (double) side1;
						double SideB = (double) side2;
						double SideC = (double) side3;
						if(SideA ==SideB && SideA == SideC) {
							//rounds for equilateral but not any others as java .todgrees screws up equilateral triangles
							AngleA = Math.round((Math.toDegrees(Math.acos(((SideB*SideB)+(SideC*SideC)-(SideA*SideA))/(2*SideB*SideC)))));
							AngleB = Math.round((Math.toDegrees(Math.acos(((SideA*SideA)+(SideC*SideC)-(SideB*SideB))/(2*SideA*SideC)))));
							AngleC = Math.round((Math.toDegrees(Math.acos(((SideB*SideB)+(SideA*SideA)-(SideC*SideC))/(2*SideB*SideA)))));
						}
						else {
							AngleA = (Math.round(Math.toDegrees(Math.acos(((SideB*SideB)+(SideC*SideC)-(SideA*SideA))/(2*SideB*SideC)))* 100.0) / 100.0);
							AngleB = (Math.round(Math.toDegrees(Math.acos(((SideA*SideA)+(SideC*SideC)-(SideB*SideB))/(2*SideA*SideC)))* 100.0) / 100.0);
							AngleC =  (Math.round(Math.toDegrees(Math.acos(((SideB*SideB)+(SideA*SideA)-(SideC*SideC))/(2*SideB*SideA)))* 100.0) / 100.0);
						}
						tricheck = (int) (AngleA+AngleB+AngleC);
						if(tricheck == aTriangle) {	
							Storage.add(new Shape(SideA, SideB, SideC, AngleA, AngleB, AngleC));
							reset();
							frame.dispose();
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

		c = new GridBagConstraints();
		frame.add(Panel);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(3,3,3,3);
		c.gridy = 1;
		c.gridx = 1;
		Panel.add(ranges,c);
		c.gridx = 1;
		c.gridy = 2;
		Panel.add(side1Label,c);
		c.gridy = 3;
		Panel.add(side2Label,c);
		c.gridy = 4;
		Panel.add(side3Label,c);
		c.gridx = 2;
		c.gridy = 2;
		Panel.add(side1Text,c);
		c.gridy = 3;
		Panel.add(side2Text,c);
		c.gridy = 4;
		Panel.add(side3Text,c);
		c.gridx = 2;
		c.gridy = 5;
		Panel.add(helpLabel,c);
		c.gridy = 6;
		Panel.add(mainButton,c);
		c.gridx = 3;
		Panel.add(backButton,c);




		frame.setSize(700,200);
		frame.show();
	}

	void customGUI() {
		frame = new JFrame("Custom Shape");
		Panel = new JPanel(new GridBagLayout());
		mainButton = new JButton("Draw Shape");
		mainButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int sideCheck = Integer.parseInt(side1Text.getText());
					int numberOfSides = Integer.parseInt(side2Text.getText());
					if(sideCheck<=maxLength && sideCheck>=minLength) {
						Storage.add(new Shape(sideCheck, numberOfSides));
						reset();
						frame.dispose();
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

		backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				reset();
				frame.dispose();
				mainmenu();

			}

		});
		side1Label = new JLabel("Length of sides in CM (15-85): ");
		side2Label = new JLabel("Number of sides: ");
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(3,3,3,3);
		c.gridx = 1;
		c.gridy = 0;
		frame.add(Panel);
		c.gridy = 1;
		Panel.add(side1Label,c);
		c.gridy = 2;
		Panel.add(side2Label,c);
		c.gridx = 2;
		c.gridy = 1;
		Panel.add(side1Text,c);
		c.gridy = 2;
		Panel.add(side2Text,c);
		c.gridy = 3;
		Panel.add(helpLabel,c);
		c.gridy = 4;
		Panel.add(mainButton,c);
		c.gridx = 3;
		Panel.add(backButton,c);


		frame.setSize(700,200);
		frame.show();

	}

	void quit() {
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



	void reset() {
		finch.setLED(Color.green,500);
		side1Text.setText("");
		side2Text.setText("");
		helpLabel.setText("");


	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode()==KeyEvent.VK_M) {
			try {
				Desktop.getDesktop().open(new File("help.docx"));
			}catch (IOException ioe) {
				ioe.printStackTrace();
			}

		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}



}
