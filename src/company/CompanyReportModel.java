package company;

/*
 * The table model for a company for retrieving reports
 * Only has the company ID and name
 */

import javax.swing.table.AbstractTableModel;

/*
 * Table Model for Company Report Table
 */
public class CompanyReportModel extends AbstractTableModel{
	
	private String [] columnNames = {"Company ID", "Name"};
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
		newData = new String[companydata.length][2];
		for(int i = 0; i < newData.length; i++) {
			for(int j = 0; j < newData[i].length;j++) {
				newData[i][j] = companydata[i].getCompanyData(j);
			}
		}
		return newData;
	}
	
	public void changeData() {
		data = getData();
	}
}
