package company;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/*
 * Main class where the JFrame is created and where the JTabbedPane
 * is also created to hold the different panels
 */

public class Main {	
	public static void main(String [] args) {
		 SwingUtilities.invokeLater(new Runnable(){
	            public void run() {
	                createAndShowGUI(); 
	            }
	        });
	}

	/*
	 * Creates the JPanels which will be displayed in the JFrame
	 */
	private static void createAndShowGUI() {
		JFrame f = new JFrame("MMT Temp Services Program");	// Creates the JFrame where the JPanel is to be shown on
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		f.setSize(1200,800);								// Sets size of the frame to be 1200 and 700
		f.setLayout(new BorderLayout(10, 10));
		
		JTabbedPane layout = new JTabbedPane(SwingConstants.TOP);
		f.setBackground(Color.black);

		
		//Declaration for each panel	
		MainFrame main = new MainFrame();
		ShowEmployeePanel displayEmployee = new ShowEmployeePanel();
		ShowCompanyPanel displayCompany = new ShowCompanyPanel();
		CompanyEmployeeRelationship relationships = new CompanyEmployeeRelationship();
		EvaluationResults evalResults = new EvaluationResults();
		ReportsPanel reports = new ReportsPanel();
		
		layout.setBorder(new LineBorder(Color.black));
		layout.add(main, "Main Page");
		layout.add(displayEmployee, "Employee");
		layout.add(displayCompany, "Company");
		layout.add(relationships, "Field Placements");
		layout.add(evalResults, "Evaluation Results");
		layout.add(reports, "Reports");
		
		f.add(layout);
		f.setVisible(true);
	} 
}