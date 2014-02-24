package company;
/*
 * This panel is used to retrieve data on field placements
 * This panel will also display a table which contains the company,
 * employee ID, and employee name. 
 * This panel will also allow submission of a new field placement,
 * however it will not allow any submission of an employee who 
 * already has a field placement.
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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;

public class CompanyEmployeeRelationship extends JPanel {
	
	private JTable fieldTable;
	private RelationshipTableModel model;	// table's model
	private JTextField filterText;	// Used to search for a specific person
	private TableRowSorter<RelationshipTableModel> sorter;	// allows filtration
	private JScrollPane tablePane;	// Scrollpane for when the table grows too large
	private JComboBox chooseFilter;	// combo box to allow the user to choose what kind of filtering system
	private String [] filterLabels = {"Company", "Employee ID",  "Employee Name"};	// labels for the combo box

	private JLabel employeeLabel, employerLabel,	// Labels for displaying the names based on the ID's
					employeeName, employerName;										
	private JTextField employeeField, employerField;	// Fields to receive the ID's
	private JButton employ, cancel;	// JButtons to either employ (place in field)  
									// or delete all data inside of text fields
	
	private String [] relationshipData;		// the relationship data retrieved from the database
	private String relationshiptxt;		// the txt file for relationship data
	private BufferedImage logo, background;		// image of logo and background
	private final Color allColor = Color.white;	// color for foreground and backgrounds
	private final int fontSize = 15;	// font size for all words
	private final int tableX = 600;	// initial spacing for X table
	private final int tableY = 50;	// initial spacing for Y for table
	
	
	// Constructor
	public CompanyEmployeeRelationship() {
		super(null);
		relationshipData = new String[2];
		relationshiptxt = "fieldPlacement.txt";
		
		model = new RelationshipTableModel();
		fieldTable = new JTable(model);
		fieldTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
		sorter = new TableRowSorter<RelationshipTableModel>(model);
		fieldTable.setRowSorter(sorter);
		fieldTable.setFillsViewportHeight(true);
		fieldTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
	     chooseFilter = new JComboBox(filterLabels);
	     filterText = new JTextField();
	     //Whenever filterText changes, invoke newFilter.
	     filterText.getDocument().addDocumentListener(
	                new DocumentListener() {
	                    public void changedUpdate(DocumentEvent e) {
	                        System.out.println("Works");
	                        newFilter();
	                    }
						public void insertUpdate(DocumentEvent e) {
	                        System.out.println("Works2");
	                        newFilter();
	                    }
	                    public void removeUpdate(DocumentEvent e) {
	                        System.out.println("Works3");

	                        newFilter();
	                    }
	                });       
		
		// Initializes the JLabels and declares the foreground color as allColor
		employeeLabel = new JLabel("Enter the Employee's ID : ");
		employeeLabel.setForeground(allColor);
		employeeLabel.setFont(new Font("Sans Serif",Font.BOLD, fontSize));
		employerLabel = new JLabel("Enter the Company's ID : ");
		employerLabel.setForeground(allColor);
		employerLabel.setFont(new Font("Sans Serif",Font.BOLD, fontSize));
		employeeName = new JLabel("");
		employeeName.setForeground(allColor);
		employeeName.setFont(new Font("Sans Serif",Font.PLAIN, fontSize));
		employerName = new JLabel("");
		employerName.setForeground(allColor);
		employerName.setFont(new Font("Sans Serif",Font.PLAIN, fontSize));
		
		// Looks for images in the database and uses them
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
		// Declares the text fields
		employeeField = new JTextField();
		employeeField.getDocument().addDocumentListener(
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
		employerField = new JTextField();
		employerField.getDocument().addDocumentListener(
				new DocumentListener() {
					public void changedUpdate(DocumentEvent e) {
						checkEmployerID();
					}
					public void insertUpdate(DocumentEvent e) {
						checkEmployerID();
					}
                    public void removeUpdate(DocumentEvent e) {
                    	checkEmployerID();
                    }
				});
		
		// JButtons to employ (place in fields) or to cancel (restarts form)
		employ = new JButton("Employ");
		employ.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if(checkTextFields()) {
					getText();
					clearTextFields();
					enterToFile();				
					model.changeData();
					model.fireTableDataChanged();
					JOptionPane.showMessageDialog(null, "Your field placement has been submitted");
				}
			}
		});
		
		cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				clearTextFields();
			}
		});
		
		
		// Adding all the fields and labels to the panel
		this.add(employeeLabel);
		this.add(employeeField);
		this.add(employeeName);
		this.add(employerLabel);
		this.add(employerField);
		this.add(employerName);
		this.add(cancel);
		this.add(employ);
		
		employeeLabel.setBounds(50, 150, 200, 20);
		employeeField.setBounds(260, 150, 250, 20);
		employeeName.setBounds(260, 100, 200, 40);
		TitledBorder employeeBorder = new TitledBorder("Employee Name");
		employeeBorder.setTitleColor(allColor);
		employeeName.setBorder(employeeBorder);
		employerName.setBounds(260, 240, 200, 40);
		TitledBorder employerBorder = new TitledBorder("Employer Name");
		employerBorder.setTitleColor(allColor);
		employerName.setBorder(employerBorder);
		employerLabel.setBounds(50, 300, 200, 20);
		employerField.setBounds(260, 300, 250, 20);
		cancel.setBounds(50, 400, 70, 20);
		employ.setBounds(150, 400, 70, 20);
		this.add(chooseFilter);
	    chooseFilter.setBounds(tableX, 590, 150, 20);
	        
	    this.add(filterText);
	    filterText.setBounds(tableX + 170, 590, 300, 20);
		tablePane = new JScrollPane(fieldTable);
		this.add(tablePane);
        tablePane.setBounds(tableX, tableY, 500, 500);
	}
	
	private void enterToFile() {
		PrintToFile.printRelationship(relationshiptxt, relationshipData);
	}
	
	public void paintComponent(Graphics g) {
		g.drawImage(background, 0, 0, getWidth(), getHeight(), this);	        
        g.drawImage(logo, 5, 5, 118, 63, this);

    }

	private void clearTextFields() {
		employeeField.setText("");
		employerField.setText("");
	}

	// Retrieves the text in the data field
	private void getText() {
		relationshipData[0] = employeeField.getText();
		relationshipData[1] = employerField.getText();
	}
	
	private boolean checkTextFields() {
		String emptyText = new String("");
		if(employeeName.getText().equals(emptyText))
		{
			JOptionPane.showMessageDialog(null, "Please submit a valid Employee");
			return false;
		}
		if(employerName.getText().equals(emptyText))
		{
			JOptionPane.showMessageDialog(null, "Please submit a valid Company.");
			return false;
		}
		if(ReadFromFile.hasRelationship(employeeField.getText()))
		{
			JOptionPane.showMessageDialog(null, "This employee already has a field placement, and so he/she " +
					"cannot have anymore. Please select another employee.");
			return false;
		}
		return true;
	}
	
	// Checks whether there is such an employee ID and 
	// prints the name if there is one.
	private void checkEmployeeID() {
		if(employeeField.getText().length() > 0) {
			try {
				int employeeID = Integer.parseInt(employeeField.getText());
				if(employeeID > 0) {
					if(employeeID < ReadFromFile.getNumberFromFile("employee.txt")) {
						Employee [] checkEmployees = ReadFromFile.findAllEmployeeData();
						employeeName.setText(checkEmployees[employeeID-1].getFirstName() + " " + 
										checkEmployees[employeeID-1].getLastName());
					}
					else {
						employeeName.setText("");
					}
				}
				else {
					employeeName.setText("");
				}
			}
			catch(NumberFormatException n) {
				JOptionPane.showMessageDialog(this,
						"Please enter a number.", "Entry Error", JOptionPane.DEFAULT_OPTION);
				employeeName.setText("");
			}
		}
		else {
			employeeName.setText("");
		}
	}
	/*
	 * Checks for whether there is such an employer ID, and will
	 * display the name if there is.
	 */
	private void checkEmployerID() {
		if(employerField.getText().length() > 0) {
			try {
				int employerID = Integer.parseInt(employerField.getText());
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
	
	private void newFilter() {
		RowFilter<RelationshipTableModel, Object> rf = null;
        //If current expression doesn't parse, don't update.
        try {
            rf = RowFilter.regexFilter(filterText.getText(), findSelectedIndex());
        } catch (Exception e) {
        	e.getStackTrace();
        }
        sorter.setRowFilter(rf);
	}
	
	private int findSelectedIndex() {
		for(int i = 0; i < filterLabels.length; i++) {
			if(chooseFilter.getItemAt(i).equals((String)chooseFilter.getSelectedItem())) {
				return i;
			}
		}
		return 0;
	}

}
