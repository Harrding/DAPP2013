package company;

/*
 * Company Class that is used to represent each company
 */
public class Company {
	
	// 0 = employer number
	// 1 = company name
	// 2 = company address
	// 3 = company city
	// 4 = company state
	// 5 = company zip
	// 6 = company phone
	// 7 = company email
	// 8 = company contact person
	private String[] companyData;
	
	public Company(String [] data) {
		companyData = data;
	}
	
	public void setCompanyData(int index, String change) {
		companyData[index] = change;
	}
	
	public String getCompanyData(int index) {
		return companyData[index];
	}
	
	public String getID()
	{
		return companyData[0];
	}
	public String getName()
	{
		return companyData[1];
	}
	
	public String getAddress()
	{
		return companyData[2];
	}
	
	public String getCity()
	{
		return companyData[3];
	}
	public String getState()
	{
		return companyData[4];
	}
	public String getZip()
	{
		return companyData[5];
	}
	public String getPhone()
	{
		return companyData[6];
	}
	public String getEmail()
	{
		return companyData[7];
	}
	public String getContact()
	{
		return companyData[8];
	}
}