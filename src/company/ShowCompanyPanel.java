package company;

import java.awt.BorderLayout;
import java.awt.CardLayout;
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

public class ShowCompanyPanel extends JPanel {
	

	private JTable companyTable;
	private JTextField filterText;	// Used to search for a specific person
	private TableRowSorter<CompanyTableModel> sorter;	// allows filtration
	private CompanyTableModel model;	// table's model
	private JScrollPane tablePane;	// Scrollpane for when the table grows too large
	private JComboBox chooseFilter;	// combo box to allow the user to choose what kind of filtering system
	private String [] filterLabels = {"Employer ID", "Name",  "Contact Person", "Contact Email"};	// labels for the combo box
	private BufferedImage logo, background;	// images for logo and background
	
	/*
	 * Registration variables on the left part of the panel
	 */
	
	private JLabel companynameLabel, contactLabel, phoneLabel,	// JLabel variables for each text field
	emailLabel, addressLabel, cityLabel, stateLabel, zipLabel;
	private JTextField companynameField, contactField, emailField, addressField, 	// TextField variables that receive the user's input
		cityField, stateField, zipField, phoneField;
	private String [] companyTableData;	// Strings to get answers from the text fields to send into the employee class
	private JButton cancel, submit;	// Buttons to submit the info or cancel
	private String companytxt;	// String for the txt file where the employee data is stored

	private final Color allColor = Color.white;	// Changes all the colors for foregrounds and backgrounds
	private final int fontSize = 15;	// The font size for all JLabels
	private final int registerX = 30;	// initial spacing for the X register part
	private final int registerY = 100;	// initial spacing for the Y register part
	private final int addition = 50;	// increment for the labels and fields
	private final int tableX = 600;	// initial spacing for X table
	private final int tableY = 50;	// initial spacing for Y for table
	private int currentY;	// value of current space
	private final int fieldSize = 250;
	// Constructor
	public ShowCompanyPanel() {
		super(null);
		this.setBackground(Color.black);
		model = new CompanyTableModel();
		sorter = new TableRowSorter<CompanyTableModel>(model);
		companyTable = new JTable(model);
		companyTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
		companyTable.setFillsViewportHeight(true);
		companyTable.setRowSorter(sorter);
		companyTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		companyTable.addMouseListener(new MouseListener() {


			public void mouseClicked(MouseEvent e) {
				int tableRow = companyTable.rowAtPoint(e.getPoint());
				Company[] companyData = ReadFromFile.findAllCompanyData();
				try
				{
					String indexValue =(String)companyTable.getValueAt(tableRow, 0);
					for(int i = 0; i < companyData.length; i++) {
						if(indexValue.equals(companyData[i].getID())) {
							JOptionPane.showMessageDialog(null, 
									companyDataInfo(Integer.parseInt(indexValue)-1, companyData), "Company Information", 
									JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
				catch(IndexOutOfBoundsException f){}
				
			}

			public void mouseEntered(MouseEvent arg0) {}

			public void mouseExited(MouseEvent arg0) {}

			public void mousePressed(MouseEvent arg0) {}

			public void mouseReleased(MouseEvent arg0) {}
			
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
                        newFilter();
                    }
					public void insertUpdate(DocumentEvent e) {
                        newFilter();
                    }
                    public void removeUpdate(DocumentEvent e) {
                        newFilter();
                    }
                });        
        
        /*
         * The registration part of the panel initialized here.
         * includes the text fields, buttons, and JLabels
         */
        companytxt = "company.txt";
		companyTableData = new String[9];
		
		logo = null;
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
		
		
		// Labels declared and initialized
		companynameLabel = new JLabel("Company Name : ");
		companynameLabel.setForeground(allColor);
		companynameLabel.setFont(new Font("Sans Serif",Font.BOLD, fontSize));
		contactLabel = new JLabel("Contact Person : ");
		contactLabel.setForeground(allColor);
		contactLabel.setFont(new Font("Sans Serif",Font.BOLD, fontSize));
		emailLabel = new JLabel("Email : ");
		emailLabel.setForeground(allColor);
		emailLabel.setFont(new Font("Sans Serif",Font.BOLD, fontSize));
		phoneLabel = new JLabel("Phone Number : ");
		phoneLabel.setForeground(allColor);
		phoneLabel.setFont(new Font("Sans Serif",Font.BOLD, fontSize));
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
		companynameField = new JTextField();
		contactField = new JTextField();
		emailField = new JTextField();
		phoneField = new JTextField();
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
					JOptionPane.showMessageDialog(null, "Your information on this company has been submitted");
				}
			}
		});

		/*
		 * Adds all the Components into the Panel
		 * Has same method as ShowEmployeePanel for labeling and 
		 * ordering as well as spacing.
		 */
		this.setBorder(BorderFactory.createEmptyBorder(50, 200, 50, 190));
		currentY = registerY;
		this.add(companynameLabel);
		companynameLabel.setBounds(registerX, currentY, 150, 20);
		this.add(companynameField);
		companynameField.setBounds(registerX + 170, currentY, fieldSize, 20);
		currentY+= addition;
		this.add(contactLabel);
		contactLabel.setBounds(registerX, currentY, 150, 20);
		this.add(contactField);
		contactField.setBounds(registerX + 170, currentY, fieldSize, 20);
		currentY+= addition;
		this.add(emailLabel);
		emailLabel.setBounds(registerX, currentY, 150, 20);
		this.add(emailField);
		emailField.setBounds(registerX + 170, currentY, fieldSize, 20);
		currentY+= addition;
		this.add(phoneLabel);
		phoneLabel.setBounds(registerX, currentY, 150, 20);
		this.add(phoneField);
		phoneField.setBounds(registerX + 170, currentY, fieldSize, 20);
		currentY+= addition;
		this.add(addressLabel);
		addressLabel.setBounds(registerX, currentY, 150, 20);
		this.add(addressField);
		addressField.setBounds(registerX + 170, currentY, fieldSize, 20);
		currentY+= addition;
		this.add(cityLabel);
		cityLabel.setBounds(registerX, currentY, 150, 20);
		this.add(cityField);
		cityField.setBounds(registerX + 170, currentY, fieldSize, 20);
		currentY+= addition;
		this.add(stateLabel);
		stateLabel.setBounds(registerX, currentY, 150, 20);
		this.add(stateField);
		stateField.setBounds(registerX + 170, currentY, fieldSize, 20);
		currentY+= addition;
		this.add(zipLabel);
		zipLabel.setBounds(registerX, currentY, 150, 20);
		this.add(zipField);
		zipField.setBounds(registerX + 170, currentY, fieldSize, 20);
		currentY+= addition;
		this.add(cancel);
		cancel.setBounds(registerX, currentY, 70, 20);
		this.add(submit);
		submit.setBounds(registerX + 90, currentY, 70, 20);
        
        this.add(chooseFilter);
        chooseFilter.setBounds(tableX, 590, 150, 20);
        
        this.add(filterText);
        filterText.setBounds(tableX + 170, 590, 300, 20);
        
		tablePane = new JScrollPane(companyTable);
        this.add(tablePane);
        tablePane.setBounds(tableX, tableY, 500, 500);
        
	}
	
	// adds gradient to background
	public void paintComponent(Graphics g)
	{
		g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        g.drawImage(logo, 5, 5,118, 63, this);

	}
	
	private void newFilter() {
		RowFilter<CompanyTableModel, Object> rf = null;
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
	private String companyDataInfo(int index, Company[] companyData) {
		String companyInfo = new String("");
		companyInfo = "" + getCompanyData(index, companyData) ;
		return companyInfo;
	}
	
	// Prints the Company data in such a format
	// 0 = employer number
		// 1 = company name
		// 2 = company address
		// 3 = company city
		// 4 = company state
		// 5 = company zip
		// 6 = company phone
		// 7 = company email
		// 8 = company contact person
	public String getCompanyData(int index, Company[] companyData) {
		String output = new String("");
		if(index >= 0) {
			output = String.format( "\nID : " + companyData[index].getID() + 
										"\nName : " + companyData[index].getName()  +
										"\nAddress : " + companyData[index].getAddress()  +
										"\nCity : " + companyData[index].getCity() +
										"\nState : " + companyData[index].getState()  +
										"\nZip Code : " + companyData[index].getZip()  +
										"\nContact Number : " + companyData[index].getPhone()  +
										"\nCompany Email : " + companyData[index].getEmail() + 
										"\nContact Person : " + companyData[index].getContact());
			return output;
		}
		return "";
	}
	// Gets the text from all the text fields
	private void getText() {
		companyTableData[1] = companynameField.getText();
		companyTableData[2] = addressField.getText();
		companyTableData[3] = cityField.getText();
		companyTableData[4] = stateField.getText();
		companyTableData[5] = zipField.getText();
		companyTableData[6] = phoneField.getText();
		companyTableData[7] = emailField.getText();
		companyTableData[8] = contactField.getText();
	}
	
	// Clears all text fields for next use
	private void clearTextFields() {
		companynameField.setText("");
		contactField.setText("");
		emailField.setText("");
		phoneField.setText("");
		addressField.setText("");
		cityField.setText("");
		stateField.setText("");
		zipField.setText("");
	}
	
	// Enters all the data into an employee and prints it to the employee txt file
	private void enterToFile() {
		companyTableData[0] = ReadFromFile.getNumberFromFile(companytxt) + "";
		PrintToFile.printCompanyDataToFile(companytxt, new Company(companyTableData));
	}
	
	// Checks whether there are anything inside of the text fields
	private boolean checkTextFields() {
		if(companynameField.getText().length() == 0 || contactField.getText().length() == 0 || 
			emailField.getText().length() == 0 || addressField.getText().length() == 0 || 
			cityField.getText().length() == 0 || stateField.getText().length() == 0 || 
			zipField.getText().length() == 0 || phoneField.getText().equals("(   )   -    ")) {
			JOptionPane.showMessageDialog(this,
					"Please fill out all boxes before submitting the data.", "Incomplete Form", 
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}
}