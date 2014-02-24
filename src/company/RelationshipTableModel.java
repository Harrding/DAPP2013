package company;

import javax.swing.table.AbstractTableModel;

/*
 * Table Model for Field Placements
 */

public class RelationshipTableModel extends AbstractTableModel{

	private String [] columnNames = { "Company", "Employee ID", "Employee Name"};
	private String [][] data = getData();
	
	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		return data.length;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		return data[rowIndex][columnIndex];
	}
	public String getColumnName(int col) {
        return columnNames[col];
    }

	// Retrieves data based on the field placements
	public String[][] getData() {
		String[][] newData;
		String[][] relationshipData = ReadFromFile.getCompanyRelationship();
		Employee[] employeeData = ReadFromFile.findAllEmployeeData();
		Company[] companyData = ReadFromFile.findAllCompanyData();
		newData = new String[relationshipData.length][3];
		for(int i = 0; i < newData.length; i++) {
			Employee employee = employeeData[Integer.parseInt(relationshipData[i][0])-1];
			Company company = companyData[Integer.parseInt(relationshipData[i][1])-1];
			newData[i][0] = company.getName();
			newData[i][1] = relationshipData[i][0];
			newData[i][2] = employee.getFirstName() + " " + employee.getLastName();
		}
		return newData;
	}
	
	public void changeData() {
		data = getData();
	}
}
