package company;

import javax.swing.table.AbstractTableModel;

/*
 * Table Model for Company Table
 */

public class CompanyTableModel extends AbstractTableModel{

	private String [] columnNames = {"Employer ID", "Name", "Contact Person", "Contact Email"};
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

	public String[][] getData() {
		String[][] newData;
		Company[] companydata= ReadFromFile.findAllCompanyData();
		newData = new String[companydata.length][4];
		for(int i = 0; i < newData.length; i++) {
				newData[i][0] = companydata[i].getID();
				newData[i][1] = companydata[i].getName();
				newData[i][2] = companydata[i].getContact();
				newData[i][3] = companydata[i].getEmail();
		}
		return newData;
	}
	
	public void changeData() {
		data = getData();
	}
}
