package company;

/*
 * Used to display the table to display all employee data.
 * Can dynamically search for employees with a filter system
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
import javax.swing.BorderFactory;
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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;

public class ShowEmployeePanel extends JPanel {
	
	private JTable employeeTable;	// Table used to display all current employees
	private JTextField filterText;	// Used to search for a specific person
	private EmployeeTableModel model;	// Model of the table
	private JScrollPane tablePane;	// Scrollpane for table when there are many employees
	private TableRowSorter<EmployeeTableModel> sorter;	// Sorter allows filtration
	private JComboBox chooseFilter;	// Choose which variable the user wants to filter by
	private String[] filterLabels = {"Employee ID", "First Name", "Last Name", "Email"};
	private BufferedImage logo, background;
	
	/*
	 * Used for registration of an employee
	 */
	private JLabel firstnameLabel, lastnameLabel, cellphoneLabel, homephoneLabel,// JLabel variables for each text field
					emailLabel, addressLabel, cityLabel, stateLabel, zipLabel;
	private JTextField firstnameField, lastnameField, emailField, addressField, // TextField variables that receive the user's input
			cityField, stateField, zipField, cellphoneField, homephoneField;
	private String [] employeeTextData;	// Strings to get answers from the text fields to send into the employee class
	private JButton cancel, submit;	// Buttons to submit the info or cancel
	private String employeetxt;	// String for the txt file where the employee data is stored
	
	private final Color allColor = Color.white;	// Changes all the colors for foregrounds and backgrounds
	private final int fontSize = 15;	// The font size for all JLabels
	private final int registerX = 30;	// initial spacing for the X register part
	private final int registerY = 100;	// initial spacing for the Y register part
	private final int addition = 50;	// increment for the labels and fields
	private final int tableX = 600;	// initial spacing for X table
	private final int tableY = 50;	// initial spacing for Y for table
	private int currentY;	// value of current space
	
	// Constructor
	public ShowEmployeePanel() {
		
		super(null);	// Declares as a null layout
		model = new EmployeeTableModel();
		sorter = new TableRowSorter<EmployeeTableModel>(model);
		employeeTable = new JTable(model);
		employeeTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
        employeeTable.setFillsViewportHeight(true);
        employeeTable.setRowSorter(sorter);
        employeeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        employeeTable.addMouseListener(new MouseListener(){

			public void mouseClicked(MouseEvent e) {
				int tableRow = employeeTable.rowAtPoint(e.getPoint());
				Employee[] employeeData = ReadFromFile.findAllEmployeeData();
				try
				{
					String indexValue =(String)employeeTable.getValueAt(tableRow, 0);
					for(int i = 0; i < employeeData.length; i++) {
						if(indexValue.equals(employeeData[i].getEmployeeID())) {
							JOptionPane.showMessageDialog(null, 
									employeeDataInfo(Integer.parseInt(indexValue)-1, employeeData), "Employee Information", 
									JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
				catch(IndexOutOfBoundsException f){}
			}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
        });
        
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
		chooseFilter = new JComboBox(filterLabels);
	    filterText = new JTextField();
        //Whenever filterText changes, invoke newFilter.
        filterText.getDocument().addDocumentListener(
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
		// Labels declared and initialized
		firstnameLabel = new JLabel("First Name : ");
		firstnameLabel.setForeground(allColor);
		firstnameLabel.setFont(new Font("Sans Serif",Font.BOLD, fontSize));
		lastnameLabel = new JLabel("Last Name : ");
		lastnameLabel.setForeground(allColor);
		lastnameLabel.setFont(new Font("Sans Serif",Font.BOLD, fontSize));
		emailLabel = new JLabel("Email : ");
		emailLabel.setForeground(allColor);
		emailLabel.setFont(new Font("Sans Serif",Font.BOLD, fontSize));
		cellphoneLabel = new JLabel("Cell Phone :");
		cellphoneLabel.setForeground(allColor);
		cellphoneLabel.setFont(new Font("Sans Serif",Font.BOLD, fontSize));
		homephoneLabel = new JLabel("Home Phone: ");
		homephoneLabel.setForeground(allColor);
		homephoneLabel.setFont(new Font("Sans Serif",Font.BOLD, fontSize));
		addressLabel = new JLabel("Address : ");
		addressLabel.setForeground(allColor);
		addressLabel.setFont(new Font("Sans Serif",Font.BOLD, fontSize));
		cityLabel = new JLabel("City : ");
		cityLabel.setForeground(allColor);
		cityLabel.setFont(new Font("Sans Serif",Font.BOLD, fontSize));
		stateLabel = new JLabel("State : ");
		stateLabel.setForeground(allColor);
		stateLabel.setFont(new Font("Sans Serif",Font.BOLD, fontSize));
		zipLabel = new JLabel("Zip Code : ");
		zipLabel.setForeground(allColor);
		zipLabel.setFont(new Font("Sans Serif",Font.BOLD, fontSize));
		
		// JTextFields declared with their lengths declared as well
		firstnameField = new JTextField();
		lastnameField = new JTextField();
		emailField = new JTextField();
		cellphoneField = new JTextField();
		homephoneField = new JTextField();
		addressField = new JTextField();
		cityField = new JTextField();
		stateField = new JTextField();
		zipField = new JTextField();
		
		// Button which cancels the registration, going back to the Main Frame
		cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				clearTextFields();
			}
		});
		
		// Button that enters all the data to an Employee file and also prints to txt file
		submit = new JButton("Submit");
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if(checkTextFields()) {
					getText();
					clearTextFields();
					enterToFile();
					model.changeData();
					model.fireTableDataChanged();
					JOptionPane.showMessageDialog(null, "Your information on this employee has been submitted");
				}
			}
		});
		
		// Adds JPanels showing the label and the fields together as well as the buttons at the end
		employeetxt = "employee.txt";
		employeeTextData = new String[10];
		this.setBorder(BorderFactory.createEmptyBorder(50, 200, 50, 190));
		currentY = registerY;
		this.add(firstnameLabel);
		firstnameLabel.setBounds(registerX, currentY, 150, 20);
		this.add(firstnameField);
		firstnameField.setBounds(registerX + 170, currentY, 250, 20);
		currentY+= addition;
		this.add(lastnameLabel);
		lastnameLabel.setBounds(registerX, currentY, 150, 20);
		this.add(lastnameField);
		lastnameField.setBounds(registerX + 170, currentY, 250, 20);
		currentY+= addition;
		this.add(emailLabel);
		emailLabel.setBounds(registerX, currentY, 150, 20);
		this.add(emailField);
		emailField.setBounds(registerX + 170, currentY, 250, 20);
		currentY+= addition;
		this.add(cellphoneLabel);
		cellphoneLabel.setBounds(registerX, currentY, 150, 20);
		this.add(cellphoneField);
		cellphoneField.setBounds(registerX + 170, currentY, 250, 20);
		currentY+= addition;
		this.add(homephoneLabel);
		homephoneLabel.setBounds(registerX, currentY, 150, 20);
		this.add(homephoneField);
		homephoneField.setBounds(registerX + 170, currentY, 250, 20);
		currentY+= addition;
		this.add(addressLabel);
		addressLabel.setBounds(registerX, currentY, 150, 20);
		this.add(addressField);
		addressField.setBounds(registerX + 170, currentY, 250, 20);
		currentY+= addition;
		this.add(cityLabel);
		cityLabel.setBounds(registerX, currentY, 150, 20);
		this.add(cityField);
		cityField.setBounds(registerX + 170, currentY, 250, 20);
		currentY+= addition;
		this.add(stateLabel);
		stateLabel.setBounds(registerX, currentY, 150, 20);
		this.add(stateField);
		stateField.setBounds(registerX + 170, currentY, 250, 20);
		currentY+= addition;
		this.add(zipLabel);
		zipLabel.setBounds(registerX, currentY, 150, 20);
		this.add(zipField);
		zipField.setBounds(registerX + 170, currentY, 250, 20);
		currentY+= addition;
		this.add(cancel);
		cancel.setBounds(registerX, currentY, 70, 20);
		this.add(submit);
		submit.setBounds(registerX + 90, currentY, 70, 20);
		
		this.add(chooseFilter);
        chooseFilter.setBounds(tableX, 590, 150, 20);
        this.add(filterText);
        filterText.setBounds(tableX + 170, 590, 300, 20);
		tablePane = new JScrollPane(employeeTable);
        this.add(tablePane);
        tablePane.setBounds(tableX, tableY, 500, 500);
	}
	
	public void paintComponent(Graphics g) {
		g.drawImage(background, 0, 0, getWidth(), getHeight(), this);	        
        g.drawImage(logo, 5, 5, 118, 63, this);

    }
	private void newEmployeeFilter() {
		RowFilter<EmployeeTableModel, Object> rf = null;
        //If current expression doesn't parse, don't update.
        try {
            rf = RowFilter.regexFilter(filterText.getText(), findSelectedIndex());
        } catch (Exception e) {}
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
	// returns the information of relationships
	private String employeeDataInfo(int index, Employee[] employeeData) {
		String companyInfo = new String("");
		companyInfo = "" + getEmployeeData(index, employeeData) ;
		return companyInfo;
	}
	
	// Prints the employee data in such a format
	public String getEmployeeData(int index, Employee[] employeeData) {
		String output = new String("");
		if(index >= 0) {
			output = String.format( "\nID : " + employeeData[index].getEmployeeID() + 
										"\nFirst Name : " + employeeData[index].getFirstName()  +
										"\nLast Name : " + employeeData[index].getLastName()  +
										"\nAddress : " + employeeData[index].getAddress()  +
										"\nCity : " + employeeData[index].getCity() +
										"\nState : " + employeeData[index].getState()  +
										"\nZip Code : " + employeeData[index].getZip()  +
										"\nHome Phone Number : " + employeeData[index].getHomePhone()  +
										"\nCell Phone Number : " + employeeData[index].getCellPhone()  +
										"\nEmail : " + employeeData[index].getEmail());
			return output;
		}
		return "";
	}
	
	// Gets the text from all the text fields
	private void getText() {
		employeeTextData[1] = firstnameField.getText();
		employeeTextData[2] = lastnameField.getText();
		employeeTextData[3] = emailField.getText();
		employeeTextData[4] = cellphoneField.getText();
		employeeTextData[5] = homephoneField.getText();
		employeeTextData[6] = addressField.getText();
		employeeTextData[7] = cityField.getText();
		employeeTextData[8] = stateField.getText();
		employeeTextData[9] = zipField.getText();
	}
	
	// Clears all text fields for next use
	private void clearTextFields() {
		String emptyText = new String("");
		firstnameField.setText(emptyText);
		lastnameField.setText(emptyText);
		emailField.setText(emptyText);
		cellphoneField.setText(emptyText);
		homephoneField.setText(emptyText);
		addressField.setText(emptyText);
		cityField.setText(emptyText);
		stateField.setText(emptyText);
		zipField.setText(emptyText);
	}
	
	// Enters all the data into an employee and prints it to the employee txt file
	private void enterToFile() {
		employeeTextData[0] = ReadFromFile.getNumberFromFile(employeetxt) + "";
		PrintToFile.printEmployeeDataToFile(employeetxt, new Employee(employeeTextData));
	}
	
	// Checks whether there are anything inside of the text fields
	private boolean checkTextFields() {
		if(firstnameField.getText().length() == 0 || lastnameField.getText().length() == 0 || 
			emailField.getText().length() == 0 || addressField.getText().length() == 0 || 
			cityField.getText().length() == 0 || stateField.getText().length() == 0 || 
			zipField.getText().length() == 0 || homephoneField.getText().equals("(   )   -    ") || 
			cellphoneField.getText().equals("(   )   -    ")) {
			
			JOptionPane.showMessageDialog(this,
					"Please fill out all boxes before submitting the data.", "Incomplete Form", 
					JOptionPane.WARNING_MESSAGE);
					
			return false;
		}
		return true;
	}
}