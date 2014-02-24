package company;

// The model for Employee Data 
import javax.swing.table.AbstractTableModel;

public class EmployeeTableModel extends AbstractTableModel{

	// Required variables for abstract class AbstractTableModel
	private String [] columnNames = {"Employee ID", "First Name", "Last Name", "Email"};
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
	
	// Retrieves the data in a 2d array for String
	public String[][] getData() {
		String[][] newData;
		Employee[] employeedata= ReadFromFile.findAllEmployeeData();
		newData = new String[employeedata.length][10];
		for(int i = 0; i < newData.length; i++) {
			for(int j = 0; j < columnNames.length;j++) {
				newData[i][j] = employeedata[i].getEmployeeData(j);
			}
		}
		return newData;
	}
	
	public void changeData() {
		data = getData();
	}
}
