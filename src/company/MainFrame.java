package company;


/*
 * Contains the instructions for how to use the program
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.jws.soap.SOAPBinding.Style;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.text.StyledDocument;

public class MainFrame extends JPanel {
	
	private JScrollPane instructionScroller;	// Scroller for the instructions
	private JTextPane instructionArea;	// Text area to contain instructions for program
	private BufferedImage logo, background;	// Image for Logo and Background
	private JLabel title;	// label for main title name
	private Font titleFont;	// Font for titles
	private StyledDocument instructionDoc;
	private JLabel underTitle; // title under the title, as a shading
	
	private Style instructionTitle, instructionPar, header;
	
	// Constructor
	public MainFrame() {	
		instructionArea = new JTextPane();
		instructionDoc = instructionArea.getStyledDocument();
		instructionScroller = new JScrollPane(instructionArea);
		// Sets font colors for title and size
		title = new JLabel("MMT EVALUATION SYSTEM");
		titleFont = new Font("Futura", Font.BOLD, 80);
		underTitle = new JLabel("MMT EVALUATION SYSTEM");
		underTitle.setFont(new Font("Futura", Font.BOLD, 81));
		underTitle.setForeground(Color.white);
		title.setFont(titleFont);
			
		
		instructionScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);	// creates a vertical scrollbar
		instructionArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));	// Creates an empty border to allow spacing
		instructionArea.setBackground(Color.black);	//	Sets the background of the TextArea
		instructionArea.setForeground(Color.white);
		instructionScroller.setBorder(new EtchedBorder(Color.white, Color.red));	//Creates a border for the Text area
		
		setText();	//Sets the text of the textArea
		
		this.add(instructionScroller);
		this.add(title);
		this.add(underTitle);
		this.setBackground(Color.black);
		this.setLayout(null);
		title.setBounds(100, 20, 1000, 200);
		underTitle.setBounds(95, 15, 1000, 205);
		instructionScroller.setBounds(100, 200, 1000, 400);
		
		
		// Retrieves images
		try {
			logo = ImageIO.read(new File("MMT_Logo.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try{
			background = ImageIO.read(new File("DAPP_Background2.jpg"));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	// The text for instructions
	public void setText() {
		String instructions = new String("");
		String employee = "1. EMPLOYEE/COMPANY\n" +
				"\tThe first two tabs are the Employee and Company tabs. They are used to register employees and companies into the" +
				" system. There are many text fields that are all REQUIRED to be filled out. If they " +
				"are not filled out, a dialog box will pop up, requesting that you first fill out all " +
				"of the information before submitting the information into the database. Next to the " +
				"registration, you will see a table. The table dynamically adds employees/companies as you submit " +
				"the information into it. Under the table, there is a combo box as well as a field to type " +
				"in. These are used as a filter. The combo box has labels that are based off of the column names of " +
				"the table. As you type into the field, the table will automatically filter out all of the data that " +
				"does not contain the information you have typed.\n\tFor example, if you type in \"H\" in the filtering" +
				" field, and the combo box is set to \"First Name\", then all of the first names that do not contain " +
				"\"H\" will be filtered out of the table. The filtering system IS case-sensitive, thus a \"h\" will" +
				" not be represented as a \"H\".\n\t" +
				"You may have noticed that the table does not display all of the information one may want on a certain" +
				" employee/company. This is because, when you click on the employee of whom you would like the data from," +
				" a message box will appear, displaying all the information on the employee you clicked.\n";
		
		String fieldPlacements = "2. FIELD PLACEMENTS\n" +
				"\tThe third tab is the Field Placements tab. This tab is used to pair up employees with a company." +
				" An employee who already has a field placement cannot be submitted again. The fields only accept " +
				"the ID of the employee and the company it is being placed to. It will not accept first names " +
				"or any other kind of identifier, however, when the ID is typed, the name will display in the box " +
				"above the data fields. Similar to the first two tabs, there is a table at the right side, which " +
				"displays the employee's name, the company he/she is associated with, and the employee's ID.";

		String evaluation = "3. EVALUATION RESULTS\n" +
				"\tThe Evaluation Results tab contains many text fields. The comment fields will only allow you to submit" +
				"256 or less characters. To have an evaluation, the employee must already have a field placement with" +
				" a company. The Employer ID data field will be automatically filled after you enter the ID of the employee." +
				" The Employer ID field CANNOT be edited. \n\tThe scoring is based on a 1-5 scale, where 1 being the lowest " +
				"and 5 being the highest. As you change the scores, the average score will automatically adjust to the average " +
				"of the 4 scores. The overall progress score will be counted separately from the average score.";
		String reports = "4. REPORTS \n" +
				"\tThe Reports tab contains two tables. The first table contains all of the Employers(Companies). " +
				"The second table contains all of the Employee Reports. To receive a score report without comments, " +
				"simply click on the employee's report in the second table, and a pop-up menu will appear. This menu " +
				"allows you to choose between the score report, or the entire evaluation report. If you would like to " +
				"see all of the reports related to a single company, simply click on the company in the first table, " +
				"and the second table will automatically filter based on employer.";
		String howTo = "5. HOW TO DO EXTRA STUFF\n" +
				"\tIf you would like to sort by average score in the Reports tab, you just have to click on the label " +
				"for the column, and it will sort automatically. It will sort by either ascending order or descending " +
				"order, but this can be changed by being clicked again.";
		String end = "\n\nDesktop Application Programming" +
				"\n\n\tBy Harrison Ding";
		instructions = "\n\n INSTRUCTIONS: \n\n ";
		instructions += employee + "\n\n";
		instructions += fieldPlacements + "\n\n";
		instructions += evaluation + "\n\n";
		instructions += reports + "\n\n";
		instructions += howTo + "\n\n";
		instructions += end + "\n\n";
		instructionArea.setText(instructions);
		instructionArea.setEditable(false);
	}
	
	public void paintComponent(Graphics g) {
		g.drawImage(background, 0, 0, getWidth(), getHeight(), this);	        
        g.drawImage(logo, 5, 5,118, 63, this);
	}
}