package company;

// Employee class which contains the data for all employees
// Reads in an array of strings as its data for simplicity in 
// resending it out in String form and to send it to PrintToFile

public class Employee {	
	// employeeData indexes : 
	// 0 = employee ID
	// 1 = first name
	// 2 = last name
	// 3 = email
	// 4 = home phone
	// 5 = cell phone
	// 6 = address
	// 7 = city
	// 8 = state
	// 9 = zip
	
	private String employeeID, zip, firstName, lastName, email, homePhone, cellPhone, address, city, state;
	private String [] employeeData;
	
	public Employee(String [] employeedata) {
		employeeData = employeedata;
		
		employeeID = employeeData[0];
		firstName = employeeData[1];
		lastName = employeeData[2];
		email = employeeData[3];
		homePhone = employeeData[4];
		cellPhone = employeeData[5];
		address = employeeData[6];
		city = employeeData[7];
		state = employeeData[8];
		zip = employeeData[9];
		
	}
	
	public void setEmployeeData(int i, String newData) {
		employeeData[i] = newData;
	}
	
	public String getEmployeeData(int i) {
		return employeeData[i];
	}
	
	public String getEmployeeID()
	{
		return employeeID;
	}
	
	public String getFirstName()
	{
		return firstName;
	}
	
	public String getLastName()
	{
		return lastName;
	}
	
	public String getEmail()
	{
		return email;
	}
	
	public String getHomePhone()
	{
		return homePhone;
	}
	
	public String getCellPhone()
	{
		return cellPhone;
	}
	
	public String getAddress()
	{
		return address;
	}
	
	public String getState()
	{
		return state;
	}
	
	public String getCity()
	{
		return city;
	}
	
	public String getZip()
	{
		return zip;
	}
	
	public String[] allEmployeeData()
	{
		return employeeData;
	}
}
