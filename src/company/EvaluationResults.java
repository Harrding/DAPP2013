package company;

/*
 * Panel where the evaluation results can be submitted.
 * The data is then sent to evaluation.txt and will be
 * stored there. This evaluation form will also automatically
 * fill out the data for the employer ID and name because
 * the employer should be found from the field placements.
 */
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class EvaluationResults extends JPanel{
	private JLabel [] evaluationLabels;	// JLabels for all evaluation fields
	private JLabel employeeName, employerName;	// JLabels to display the employee/employer names based on the ID
	private JTextArea[] evaluationComments;	//	Text area where only 256 characters can be entered
	private JTextField [] evaluationResults;	// The text fields where the user enters all the results
	private JButton employ, cancel;	// Buttons to submit the information or to delete everything on the page
	private String [] evaluationData;	// Used to retrieve data from all txt fields and sliders, etc.
	private String evaluationtxt;	// evaluation.txt
	private JCheckBox recommendation;	// If checked, the recommendation is true
	private JScrollPane [] commentScrolls;	// Allows scrolling for each text area for comments
	private boolean recommendationValue;	// changes according to the recommendation checkbox
	private JSlider[] evaluationScores;	//	Slider with integers of 1-5 which are the scores, 1 being lowest and 5 being outstanding
	private double averageScore;	//	Average of all evaluation scores
	private JLabel averageScoreLabel;	// Displays the score
	private BufferedImage logo, background;	// Images for background and Logo
	// The color for all foregrounds and backgrounds for the components
	private final Color allColor = Color.white;
	
	public EvaluationResults() {
		//Declaring variables
		this.setLayout(null);
		evaluationData = new String[17];
		evaluationtxt = "evaluation.txt";
		this.setBackground(new Color(255, 153, 0));
		employeeName = new JLabel("");
		employerName = new JLabel("");
		averageScoreLabel = new JLabel("1.0");
		averageScoreLabel.setForeground(Color.white);
		averageScoreLabel.setFont(new Font("Sans Serif", Font.BOLD, 18));
		averageScore = 1.0;
		
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
		
		recommendationValue = false;
		evaluationLabels = new JLabel[15];
		evaluationResults = new JTextField[4];
		evaluationComments = new JTextArea[5];
		commentScrolls = new JScrollPane[5];
		
		// Declaring recommendation value as false
		recommendation = new JCheckBox("Employment Recommendation", false);
		recommendation.setForeground(allColor);
		recommendation.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent i) {
				Object value = i.getItemSelectable();
				if(value == recommendation) {
					recommendationValue = !recommendationValue;
				}
			}
		});
		
		// initializing JLabels
		for(int i = 0; i < evaluationLabels.length; i++) {
			evaluationLabels[i] = new JLabel();
			evaluationLabels[i].setForeground(allColor);
			evaluationLabels[i].setFont(new Font("Sans Serif", Font.BOLD, 15));
			evaluationLabels[i].setBorder(BorderFactory.createEtchedBorder(Color.white, Color.blue));
		}
		
		//JSlider declaration
		evaluationScores = new JSlider[5];
		for(int i = 0; i < evaluationScores.length; i++)
		{
			evaluationScores[i] = new JSlider(1, 5);
			evaluationScores[i].setForeground(allColor);
			evaluationScores[i].setSnapToTicks(true);
			evaluationScores[i].setPaintTicks(true);
			evaluationScores[i].setPaintLabels(true);
			evaluationScores[i].setMajorTickSpacing(1);
			evaluationScores[i].setValue(evaluationScores[i].getMinimum());
		}
		
		// Calculates the average score based on the JSliders (other scores)
		for(int i = 0; i < evaluationScores.length; i++)
		{
			evaluationScores[i].addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent c) {
					calculateAverage();
				} 
			});
		}
		
		// Setting text for each JLabel
		evaluationLabels[0].setText("   Employee ID : ");
		evaluationLabels[1].setText("   Employer ID : ");
		evaluationLabels[2].setText("   Evaluation Date : ");
		evaluationLabels[3].setText("   Next Evaluation Date : ");
		evaluationLabels[4].setText("   Work Quality Score : ");
		evaluationLabels[5].setText("   Work Quality Comments : ");
		evaluationLabels[6].setText("   Work Habits Score : ");
		evaluationLabels[7].setText("   Work Habits Comments : ");
		evaluationLabels[8].setText("   Knowledge Score : ");
		evaluationLabels[9].setText("   Knowledge Comments : ");
		evaluationLabels[10].setText("   Behavior Score : ");
		evaluationLabels[11].setText("   Behavior Comments : ");
		evaluationLabels[12].setText("   Average Score : ");
		evaluationLabels[13].setText("   Overall Progress Score : ");
		evaluationLabels[14].setText("   Overall Comments : ");
		
		// Initializes comment blocks
		for(int i = 0; i < evaluationComments.length; i++) {
			evaluationComments[i] = new JTextArea(2, 30);
			evaluationComments[i].setLineWrap(true);
		}
		
		// Sets each textfields columns
		for(int i = 0; i < evaluationResults.length; i++) {
			evaluationResults[i] = new JTextField();
		}
		
		evaluationResults[0].getDocument().addDocumentListener(
				new DocumentListener() {
					public void changedUpdate(DocumentEvent e) {
						checkEmployeeID();
					}
					public void insertUpdate(DocumentEvent e) {
						checkEmployeeID();
					}
                    public void removeUpdate(DocumentEvent e) {
                    	checkEmployeeID();
                    }
				});
		// Buttons to submit or cancel the current page.
		employ = new JButton("Submit Results");
		employ.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if(checkFields()) {
					getText();
					clearFields();
					enterToFile();
					JOptionPane.showMessageDialog(null, "Your evaluation has been submitted");
				}
			}
		});
		
		cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				clearFields();
			}
		});
		
		// Creates a scroll bar for each comment block
		for(int i = 0; i < commentScrolls.length; i++) {
			commentScrolls[i] = new JScrollPane(evaluationComments[i]);
			commentScrolls[i].setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		}
		
		// Adds each component 
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		JPanel evaluationPanel = new JPanel(new GridLayout(0, 4, 30, 20));
		
		evaluationPanel.setOpaque(false);
		evaluationPanel.setPreferredSize(new Dimension(1000, 300));
		
		// Displays the employee name based on the ID
		evaluationPanel.add(new JLabel("    "));
		evaluationPanel.add(employeeName);
		employeeName.setBackground(allColor);
		employeeName.setForeground(allColor);
		TitledBorder employeeBorder = new TitledBorder("Employee Name");
		employeeBorder.setTitleColor(allColor);
		employeeName.setBorder(employeeBorder);
		
		// Displays the employer name based on the ID
		evaluationPanel.add(new JLabel("      "));
		evaluationPanel.add(employerName);
		employerName.setBackground(allColor);
		employerName.setForeground(allColor);
		TitledBorder employerBorder = new TitledBorder("Employer Name");
		employerBorder.setTitleColor(allColor);
		employerName.setBorder(employerBorder);
		
		evaluationResults[1].setEditable(false);
		
		evaluationPanel.add(evaluationLabels[0]);
		evaluationPanel.add(evaluationResults[0]);
		
		evaluationPanel.add(evaluationLabels[1]);
		evaluationPanel.add(evaluationResults[1]);
		
		evaluationPanel.add(evaluationLabels[2]);
		evaluationPanel.add(evaluationResults[2]);

		evaluationPanel.add(evaluationLabels[3]);
		evaluationPanel.add(evaluationResults[3]);

		evaluationPanel.add(evaluationLabels[4]);
		evaluationPanel.add(evaluationScores[0]);

		evaluationPanel.add(evaluationLabels[5]);
		evaluationPanel.add(commentScrolls[0]);

		evaluationPanel.add(evaluationLabels[6]);
		evaluationPanel.add(evaluationScores[1]);

		evaluationPanel.add(evaluationLabels[7]);
		evaluationPanel.add(commentScrolls[1]);

		evaluationPanel.add(evaluationLabels[8]);
		evaluationPanel.add(evaluationScores[2]);

		evaluationPanel.add(evaluationLabels[9]);
		evaluationPanel.add(commentScrolls[2]);

		evaluationPanel.add(evaluationLabels[10]);
		evaluationPanel.add(evaluationScores[3]);

		evaluationPanel.add(evaluationLabels[11]);
		evaluationPanel.add(commentScrolls[3]);

		evaluationPanel.add(evaluationLabels[12]);
		evaluationPanel.add(averageScoreLabel);

		evaluationPanel.add(evaluationLabels[13]);
		evaluationPanel.add(evaluationScores[4]);

		evaluationPanel.add(evaluationLabels[14]);
		evaluationPanel.add(commentScrolls[4]);
		
		evaluationPanel.add(recommendation);
		
		evaluationPanel.add(new JLabel("    "));
		evaluationPanel.add(new JLabel("    "));
		evaluationPanel.add(new JLabel("    "));
		evaluationPanel.add(cancel);
		evaluationPanel.add(employ);
		
		
		this.add(evaluationPanel);
		evaluationPanel.setBounds(10, 10, 1000, 630);
	}
	
	public void paintComponent(Graphics g)
	{
		g.drawImage(background, 0, 0, getWidth(), getHeight(), this);	        
        g.drawImage(logo, 5, 5,118, 63, this);

	}
	
	public void calculateAverage()
	{
		double sum = 0;
		for(int i = 0; i < evaluationScores.length-1; i++)
		{
			sum += evaluationScores[i].getValue();
		}
		averageScore = ((int)((sum/(evaluationScores.length-1))*10)/10.0);
		averageScoreLabel.setText("" + averageScore);
	}
	
	// Enters all data to the file
	private void enterToFile() {
		PrintToFile.printRelationship(evaluationtxt, evaluationData);
	}
	
	// Resets entire page to initial
	private void clearFields() {
		for(int i = 0; i < evaluationResults.length; i++) {
			evaluationResults[i].setText("");
		}
		for(int i = 0; i < evaluationComments.length; i++) {
			evaluationComments[i].setText("");
		}
		for(int i = 0; i < evaluationScores.length; i++)
		{
			evaluationScores[i].setValue(evaluationScores[i].getMinimum());
		}
		employeeName.setText("");
		employerName.setText("");
		recommendation.setSelected(false);
	}

	// Receives the text from all the data fields
	private void getText() {
		for(int i = 0; i < evaluationComments.length; i++) {
			clearLines(evaluationComments[i]);
		}
		evaluationData[0] = ReadFromFile.getNumberFromFile(evaluationtxt) + "";
		evaluationData[1] = evaluationResults[0].getText();
		evaluationData[2] = evaluationResults[1].getText();
		evaluationData[3] = evaluationResults[2].getText();
		evaluationData[4] = evaluationResults[3].getText();
		evaluationData[5] = "" + evaluationScores[0].getValue();
		evaluationData[6] = evaluationComments[0].getText();
		evaluationData[7] = "" + evaluationScores[1].getValue();
		evaluationData[8] = evaluationComments[1].getText();
		evaluationData[9] = "" + evaluationScores[2].getValue();
		evaluationData[10] = evaluationComments[2].getText();
		evaluationData[11] = "" + evaluationScores[3].getValue();
		evaluationData[12] = evaluationComments[3].getText();
		evaluationData[13] = "" + averageScore;
		evaluationData[14] = "" + evaluationScores[4].getValue();
		evaluationData[15] = evaluationComments[4].getText();
		evaluationData[16] = new Boolean(recommendationValue).toString();
	}

	// checks whether any fields are empty before sending to database
	private boolean checkFields() {
		if(employeeName.getText().equals(""))
		{
			JOptionPane.showMessageDialog(this,
					"There is no such employee with that ID. Please enter a valid ID",
					"Incomplete Form", 
					JOptionPane.WARNING_MESSAGE);
					
				return false;
		}
		if(employerName.getText().equals(""))
		{
			JOptionPane.showMessageDialog(this,
					"This Employee is not associated with any company currently. Please assign a " +
					"Field Placement in the Field Placements tab.",
					"Incomplete Form", 
					JOptionPane.WARNING_MESSAGE);
					
				return false;
		}
		for(int i = 0; i < evaluationResults.length; i++) {
			if(evaluationResults[i].getText().equals("")) {
				
				JOptionPane.showMessageDialog(this,
				"Please fill out all boxes before submitting an evaluation.", "Incomplete Form", 
				JOptionPane.WARNING_MESSAGE);
				
				return false;
			}
		}
		for(int i = 0; i < evaluationComments.length; i++) {
			if(evaluationComments[i].getText().equals("")) {
				JOptionPane.showMessageDialog(this,
				"Please fill out all boxes before submitting an evaluation.", "Incomplete Form", 
				JOptionPane.WARNING_MESSAGE);
				
				return false;
			}
		}
		for(int i = 0; i < evaluationComments.length; i++) {
			if(evaluationComments[i].getText().length() > 256)
			{
				JOptionPane.showMessageDialog(this,
						"Some of your comments exceed 256 characters. Please change it so it is under 256" +
						" characters.", "Incomplete Form", 
						JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}
		
		
		return true;
	}
	
	// Checks the employee's ID and displays the name if there is one
	private void checkEmployeeID() {
		if(evaluationResults[0].getText().length() > 0) {
			String [][]fieldPlacementData = ReadFromFile.getCompanyRelationship();
			try {
				int employeeID = Integer.parseInt(evaluationResults[0].getText());
				if(employeeID > 0) {
					if(employeeID < ReadFromFile.getNumberFromFile("employee.txt")) {
						Employee [] checkEmployees = ReadFromFile.findAllEmployeeData();
						employeeName.setText(checkEmployees[employeeID-1].getFirstName() + " " + 
										checkEmployees[employeeID-1].getLastName());
						for(int i = 0; i < fieldPlacementData.length; i++)
						{
							if(fieldPlacementData[i][0].equals(evaluationResults[0].getText()))
							{
								evaluationResults[1].setText(fieldPlacementData[i][1]);
								checkEmployerID();
							}
						}
					}
					else {
						employeeName.setText("");
						evaluationResults[1].setText("");
						checkEmployerID();
					}
				}
				else {
					employeeName.setText("");
					evaluationResults[1].setText("");
					checkEmployerID();
				}
				
			}
			catch(NumberFormatException n) {
				JOptionPane.showMessageDialog(this,
						"Please enter a number.", "Entry Error", JOptionPane.DEFAULT_OPTION);
				employeeName.setText("");
				evaluationResults[1].setText("");
				checkEmployerID();
			}
			
		}
		else {
			employeeName.setText("");
			evaluationResults[1].setText("");
			checkEmployerID();
		}
	}
	
	// Checks the employer's ID and displays the name if there is one
	private void checkEmployerID() {
		if(evaluationResults[1].getText().length() > 0) {
			try {
				int employerID = Integer.parseInt(evaluationResults[1].getText());
				if(employerID > 0) {
					if(employerID < ReadFromFile.getNumberFromFile("company.txt")) {
						Company [] checkCompany = ReadFromFile.findAllCompanyData();
						employerName.setText(checkCompany[employerID-1].getCompanyData(1));
					}
					else {
						employerName.setText("");
					}
				}
				else {
					employerName.setText("");
				}
			}
			catch(NumberFormatException n) {
				JOptionPane.showMessageDialog(this,
						"Please enter a number.", "Entry Error", JOptionPane.DEFAULT_OPTION);
				employerName.setText("");
			}
		}
		else {
			employerName.setText("");
		}
	}
	private void clearLines(JTextArea field) {
		String fieldData = new String("");
		for(int i = 0; i < field.getText().length(); i++) {
			if(field.getText().charAt(i) != '\n' || field.getText().charAt(i) != '\t') {
				fieldData += field.getText().charAt(i);
			}
		}
		field.setText(fieldData);
	}
}