package company;

/*
 * This panel is used to display two tables, where in one table,
 * There are companies, and the other there are employees. In this
 * panel, the user can click on a company within the company table, 
 * and all the employees with no evaluation data from that specific
 * company will be filtered out. To see the evaluation data, click on
 * an employee in the table of employees, and a JPopupMenu will display.
 * the JPopupMenu will allow the user to choose whether they would like to
 * see the entire report, report without comments, or just the comments.
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;

public class ReportsPanel extends JPanel {

	/*
	 * The JTable is for the companies, where when
	 * one is clicked, the Employee Table will be 
	 * filtered by employer
	 */
	private JTable companyTable;	// Table for Companies to allow sorting in the employee table based on employer
	private CompanyReportModel companyModel;	// Model for the table for companies
	private BufferedImage logo, background;	// Images for background and Logo
	private JScrollPane companyTablePane;	// Allows scrolling for the JTable
	
	private JTextField filterEmployeeText;	// Used to search for a specific person
	private JComboBox chooseEmployeeFilter;	// combo box to allow the user to choose what kind of filtering system
	private String [] filterEmployeeLabels = {"Report ID", "Employee ID", "Name", "Average Score", "Employer"};	// labels for the combo box
	private JTextField filterCompanyText;	// Used to search for a specific Company
	private JComboBox chooseCompanyFilter;	// combo box to allow the user to choose what kind of filtering system
	private String [] filterCompanyLabels = {"Company ID", "Name"};	// labels for the combo box
	private TableRowSorter<EmployeeReportModel> employeeSorter;	// Allows sorting of the table for employee reports
	private TableRowSorter<CompanyReportModel> companySorter;	// Allows sorting of the table for company

	
	private JTable employeeTable;	// Table for employee reports
	private JButton refresh;	// refresh button used to refresh both tables
	private EmployeeReportModel employeeModel;	// model for table for the reports 
	private JScrollPane employeeTablePane;	// Allows scrolling when there are too many reports in JTable
	private JPopupMenu employeeReports;	// Popup menu used to allow user to choose between scores and entire eval report

	// Numbers for the JPopupMenu
	private static final String ENTIRE = "Entire Evaluation Report";
	private static final String PARTIAL = "Score Report";
	private JMenuItem entire, partial;
	private int tableIndexValue;
	private static final int ERROR_CONSTANT = -1;
	
	// Constructor
	public ReportsPanel()
	{
		super(null);
		this.setBackground(new Color(255, 153, 0));
		entire = new JMenuItem(ENTIRE);
		partial = new JMenuItem(PARTIAL);
		tableIndexValue = ERROR_CONSTANT;
		// allows entire to respond by displaying the entire evaluation result
		entire.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String[][] evaluations = ReadFromFile.getEvaluationResults();
				if(tableIndexValue != ERROR_CONSTANT)
				{
					for(int i = 0; i < evaluations.length; i++) {
						if(Integer.toString(tableIndexValue).equals(evaluations[i][0])) {
							JOptionPane.showMessageDialog(employeeReports, 
									employeeEvaluationInfo(i, evaluations), "Employee Evaluation Results", 
									JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
			}
			
		});
		
		// allows partial to respond by displaying the scores
		partial.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String [][] evaluations = ReadFromFile.getEvaluationScores();
				if(tableIndexValue != ERROR_CONSTANT)
				{
					for(int i = 0; i < evaluations.length; i++) {
						if(Integer.toString(tableIndexValue).equals(evaluations[i][0])) {
							JOptionPane.showMessageDialog(employeeReports, 
									noCommentInfo(i, evaluations), "Employee Evaluation Results", 
									JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
			}
			
		});
		employeeReports = new JPopupMenu();
		partial.setEnabled(false);
		entire.setEnabled(false);
		employeeReports.add(entire);
		//Adds a separator
		employeeReports.addSeparator();
		employeeReports.add(partial);
		
		/*
		 * Adds the filters to the tables, to allow easy access to a specific person or company
		 */
		chooseEmployeeFilter = new JComboBox(filterEmployeeLabels);
        filterEmployeeText = new JTextField();
        //Whenever filterText changes, invoke newFilter.
        filterEmployeeText.getDocument().addDocumentListener(
                new DocumentListener() {
                    public void changedUpdate(DocumentEvent e) {
                        newEmployeeFilter();
                    }
					public void insertUpdate(DocumentEvent e) {
                        newEmployeeFilter();
                    }
                    public void removeUpdate(DocumentEvent e) {
                        newEmployeeFilter();
                    }
                });    
        chooseCompanyFilter = new JComboBox(filterCompanyLabels);
        filterCompanyText = new JTextField();
        //Whenever filterText changes, invoke newFilter.
        filterCompanyText.getDocument().addDocumentListener(
                new DocumentListener() {
                    public void changedUpdate(DocumentEvent e) {
                        newCompanyFilter();
                    }
					public void insertUpdate(DocumentEvent e) {
                        newCompanyFilter();
                    }
                    public void removeUpdate(DocumentEvent e) {
                        newCompanyFilter();
                    }
                });    
		
		/*
		 * All company table is declared as well as the filter for the companies
		 */
		companyModel = new CompanyReportModel();
		companyTable = new JTable(companyModel);
		companySorter = new TableRowSorter<CompanyReportModel>(companyModel);
		companyTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
		companyTable.setFillsViewportHeight(true);
        companyTable.setRowSorter(companySorter);
		companyTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		companyTable.addMouseListener(new MouseListener() {
			// Changes the Employee table into one such that
			// only the selected Company is shown
			public void mouseClicked(MouseEvent e) {
				int tableRow = companyTable.rowAtPoint(e.getPoint());
				try
				{
					String indexValue = (String)companyTable.getValueAt(tableRow, 1);
					newEmployeeFilter(indexValue);
				}
				catch(IndexOutOfBoundsException f){}
			}

			public void mouseEntered(MouseEvent e) {}

			public void mouseExited(MouseEvent e) {}

			public void mousePressed(MouseEvent e) {}

			public void mouseReleased(MouseEvent e) {}
			
		});
		
		// Retrieves image of the logo
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
		

		companyTablePane = new JScrollPane(companyTable);
		this.add(companyTablePane);
		this.add(chooseCompanyFilter);
        this.add(filterCompanyText);
        chooseCompanyFilter.setBounds(50, 610, 150, 30);
        filterCompanyText.setBounds(210, 610, 300, 30 );
		companyTablePane.setBounds(50, 100, 500, 500);
		
		/*
		 * All the employee tables data and filter will 
		 * be initialized here. The sorter allows the employee
		 * to be sorted by employer
		 */
		
		employeeModel = new EmployeeReportModel();
		employeeSorter = new TableRowSorter<EmployeeReportModel>(employeeModel);
		employeeTable = new JTable(employeeModel);
		employeeTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
        employeeTable.setFillsViewportHeight(true);
        employeeTable.setRowSorter(employeeSorter);
        employeeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        employeeTable.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {
				int tableRow = employeeTable.rowAtPoint(e.getPoint());
				try
				{
					// allows retrieval of the report
					partial.setEnabled(true);
					entire.setEnabled(true);
					// gives the employeeID number
					tableIndexValue = Integer.parseInt((String)employeeTable.getValueAt(tableRow, 0));
					employeeReports.show(employeeTable, e.getX(), e.getY());
					
				}
				catch(IndexOutOfBoundsException f){
					tableIndexValue = ERROR_CONSTANT;
				}
			}

			public void mouseEntered(MouseEvent e) {}

			public void mouseExited(MouseEvent e) {}

			public void mousePressed(MouseEvent e) {}

			public void mouseReleased(MouseEvent e) {}
        	
        });
;
        
        refresh = new JButton("Refresh");
        refresh.addActionListener(new ActionListener(){
        	// when the refresh button is pressed, the table refreshes
        	public void actionPerformed(ActionEvent a)
        	{
        		companyModel.changeData();
        		companyModel.fireTableDataChanged();
        		employeeModel.changeData();
        		employeeModel.fireTableDataChanged();
        	}
        });
        this.add(refresh);
    	refresh.setBounds(250, 20, 200, 50);
        
		employeeTablePane = new JScrollPane(employeeTable);
        this.add(employeeTablePane);
        this.add(chooseEmployeeFilter);
        this.add(filterEmployeeText);
        chooseEmployeeFilter.setBounds(650, 610, 150, 30);
        filterEmployeeText.setBounds(810, 610, 300, 30 );
        employeeTablePane.setBounds(650, 100, 500, 500);
        
        
	}
	
	
	// adds gradient to background
	public void paintComponent(Graphics g)
	{
		g.drawImage(background, 0, 0, getWidth(), getHeight(), this);	        
        g.drawImage(logo, 5, 5,118, 63, this);

	}
	// receives the entire report
	public String employeeEvaluationInfo(int index, String[][] evaluationData) {
		String employeeInfo = new String("");
		employeeInfo = "This employee's evaluation results are : " + getEvalResults(index, evaluationData);
		return employeeInfo;
	}
	
	// Receives the scores without comments
	public String noCommentInfo(int index, String[][] evaluationScores)
	{
		String employeeInfo = new String("");
		employeeInfo = "This employee's evaluation scores are : " + getEvalScores(index, evaluationScores);
		return employeeInfo;
	}
	
	// Prints the evaluation scores of the employee
	public String getEvalScores(int index, String[][] evaluationScores)
	{
		String output = new String("");
		if(index >= 0) {
			output = String.format( "\nReport ID : " + evaluationScores[index][0] + 
										"\nEmployee ID : " + evaluationScores[index][1] +
										"\nCompany ID : " + evaluationScores[index][2] +
										"\nWork Quality Score : " + evaluationScores[index][3] +
										"\nWork Habits Score : " + evaluationScores[index][4] + 
										"\nJob Knowledge Score : " + evaluationScores[index][5] + 
										"\nBehavior Score : " + evaluationScores[index][6] +
										"\nAverage Score : " + evaluationScores[index][7] +
										"\nOverall Score : " + evaluationScores[index][8] + 
										"\nRecommendation : " + evaluationScores[index][9]);
			return output;
		}
		return "No evaluation data entered yet.";
	}
	
	// Prints the evaluation results in such a format
	public String getEvalResults(int index, String[][] evaluationData) {
		String output = new String("");
		if(index >= 0) {
			output = String.format( "\nReport ID : " + evaluationData[index][0] + 
										"\nEmployee ID : " + evaluationData[index][1] +
										"\nCompany ID : " + evaluationData[index][2] +
										"\nEvaluation Date : " + evaluationData[index][3] +
										"\nNext Evaluation Date : " + evaluationData[index][4] +
										"\nWork Quality Score : " + evaluationData[index][5] +
										"\nWork Quality Comments : " + evaluationData[index][6] +
										"\nWork Habits Score : " + evaluationData[index][7] + 
										"\nWork Habits Comments : " + evaluationData[index][8] + 
										"\nJob Knowledge Score : " + evaluationData[index][9] + 
										"\nJob Knowledge Comments : " + evaluationData[index][10] + 
										"\nBehavior Score : " + evaluationData[index][11] +
										"\nBehavior Comments : " + evaluationData[index][12] + 
										"\nAverage Score : " + evaluationData[index][13] +
										"\nOverall Score : " + evaluationData[index][14] + 
										"\nOverall Comments : " + evaluationData[index][15] + 
										"\nRecommendation : " + evaluationData[index][16]);
			return output;
		}
		return "No evaluation data entered yet.";
	}
	
	private void newEmployeeFilter() {
		RowFilter<EmployeeReportModel, Object> rf = null;
        //If current expression doesn't parse, don't update.
        try {
            rf = RowFilter.regexFilter(filterEmployeeText.getText(), findSelectedEmployeeIndex());
        } catch (Exception e) {}
        employeeSorter.setRowFilter(rf);
	}
	
	private int findSelectedEmployeeIndex() {
		for(int i = 0; i < filterEmployeeLabels.length; i++) {
			if(chooseEmployeeFilter.getItemAt(i).equals((String)chooseEmployeeFilter.getSelectedItem())) {
				return i;
			}
		}
		return 0;
	}
	
	private void newCompanyFilter() {
		RowFilter<CompanyReportModel, Object> rf = null;
        //If current expression doesn't parse, don't update.
        try {
            rf = RowFilter.regexFilter(filterCompanyText.getText(), findSelectedCompanyIndex());
        } catch (Exception e) {
        	e.getStackTrace();
        }
        companySorter.setRowFilter(rf);
	}
	
	private int findSelectedCompanyIndex() {
		for(int i = 0; i < filterCompanyLabels.length; i++) {
			if(chooseCompanyFilter.getItemAt(i).equals((String)chooseCompanyFilter.getSelectedItem())) {
				return i;
			}
		}
		return 0;
	}

	
	// Employee filter which will filter employees based on their employer
	private void newEmployeeFilter(String text) {
		RowFilter<EmployeeReportModel, Object> rf = null;
        //If current expression doesn't parse, don't update.
        try {
            rf = RowFilter.regexFilter(text, 4);
        } catch (Exception e) {}
        employeeSorter.setRowFilter(rf);
	}	
}
