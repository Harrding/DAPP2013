package company;

import java.io.*;
import java.util.ArrayList;

public class ReadFromFile 
{	
	/*
	 * returns the 2d array of all company relationships
	 * in the first index, it contains the number of different
	 * relationships. In the second one, it contains the values 
	 * in each index.
	 */
	public static String[][] getCompanyRelationship() {
		String [][] relationship;
		ArrayList<String> companyRelationship = new ArrayList<String>();
		String read;
		try {
			BufferedReader reader = new BufferedReader(new FileReader("fieldPlacement.txt"));
			while((read = reader.readLine()) != null) {
				companyRelationship.add(read);
			}
			reader.close();
		}
		catch(IOException i) {
			try{
				File file = new File("fieldPlacement.txt");
				file.createNewFile();
			}
			catch(IOException j){}
			
		}
		relationship = stringToRelationship(companyRelationship);
		return relationship;
	}
	
	
	/*
	 * Takes in an arrayList of strings, where each value in the arraylist
	 * will be one line from the text file
	 * PRECONDITION: ArrayList a must be from the fieldPlacement.txt file
	 */
	public static String [][] stringToRelationship(ArrayList<String> a) { 
		String[][] relationship = new String[a.size()][2];
		int counter = 0;
		boolean firstQuot = false;
		boolean secondQuot = false;
		int firstIndex = 0;
		int secondIndex = 0;
		for(int i = 0; i < relationship.length; i++)
			for(int j = 0; j < relationship[i].length; j++)
				relationship[i][j] = new String("");
		for(int i = 0; i < a.size(); i++) {
			counter = 0;
			firstQuot = false;
			secondQuot = false;
			for(int j = 0; j < a.get(i).length()-1; j++) {
				if(a.get(i).charAt(j) == '\\'&& firstQuot == false)
				{
					firstQuot = true;
					firstIndex = j+1;
				}
				else if(a.get(i).substring(j, j+2).equals("\\,") && firstQuot == true) {
					secondQuot = true;
					secondIndex = j;
				}
				else if(j == a.get(i).length()-2 && a.get(i).substring(j+1).equals("\\") && firstQuot == true)
				{
					secondQuot = true;
					secondIndex = j+1;
				}
				if(firstQuot && secondQuot) {
					relationship[i][counter] = a.get(i).substring(firstIndex, secondIndex);
					firstQuot = false;
					secondQuot = false;
					counter++;
				}
			}
		}
		return relationship;
	}
	
	// Gets number of lines (aka number of employees or number of companies) from a given text file
	public static int getNumberFromFile(String filename) {
		int employeeNum = 1;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			while(reader.readLine() != null) {
				employeeNum++;
			}
			reader.close();
		}
		catch(IOException i) {
			return 1;
		}
		return employeeNum;
	}
	
	// Gets all employee data to be put into a single array, and then returns the array
	public static Employee [] findAllEmployeeData() {
		Employee [] employeedata;
		String read;
		ArrayList<String> employeeData = new ArrayList<String>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader("employee.txt"));
			while((read = reader.readLine()) != null) {
				employeeData.add(read);
			}
			reader.close();
		}
		catch(IOException i) {
			try{
				File file = new File("employee.txt");
				file.createNewFile();
			}
			catch(IOException j){}
		}
		
		employeedata = stringToEmployee(employeeData);
		return employeedata;
	}
	
	// Changes Employees data into an array of strings to convert to the employee class
	/*
	 * PRECONDITION: ArrayList a must be an entire line from the employee.txt file
	 */
	public static Employee [] stringToEmployee(ArrayList<String> a) {
		Employee[] employees = new Employee[a.size()];
		String [][] data = new String[a.size()][10];
		int counter = 0;
		boolean firstQuot = false;
		boolean secondQuot = false;
		int firstIndex = 0;
		int secondIndex = 0;
		for(int i = 0; i < data.length; i++)
			for(int j = 0; j < data[i].length; j++)
				data[i][j] = "";
		for(int i = 0; i < a.size(); i++) {
			counter = 0;
			for(int j = 0; j < a.get(i).length()-1; j++) {
				if(a.get(i).charAt(j) == '\\'&& firstQuot == false)
				{
					firstQuot = true;
					firstIndex = j+1;
				}
				else if(a.get(i).substring(j, j+2).equals("\\,") && firstQuot == true) {
					secondQuot = true;
					secondIndex = j;
				}
				else if(j == a.get(i).length()-2 && a.get(i).substring(j+1).equals("\\") && firstQuot == true)
				{
					secondQuot = true;
					secondIndex = j+1;
				}
				if(firstQuot && secondQuot) {
					data[i][counter] = a.get(i).substring(firstIndex, secondIndex);
					firstQuot = false;
					secondQuot = false;
					counter++;
				}
			}
			employees[i] = new Employee(data[i]);
		}
		return employees;
	}
	
	// Gets all employee data to be put into a single array, and then returns the array
	public static Company [] findAllCompanyData() {
			Company [] companydata;
			String read;
			ArrayList<String> companyData = new ArrayList<String>();
			try {
				BufferedReader reader = new BufferedReader(new FileReader("company.txt"));
				while((read = reader.readLine()) != null) {
					companyData.add(read);
				}
				reader.close();
			}
			catch(IOException i) {
				try{
					File file = new File("company.txt");
					file.createNewFile();
				}
				catch(IOException j){}
			}
			
			companydata = stringToCompany(companyData);
			return companydata;
		}
		
	/*
	 * Retrieves the data from an arrayList of strings which contains
	 * each line of company.txt and will convert all of it into an array
	 * of companies.
	 * 
	 * PRECONDITION: ArrayList a must be a line from the company.txt file
	 */
	public static Company [] stringToCompany(ArrayList<String> a) {
		Company[] companys = new Company[a.size()];
		String [][] data = new String[a.size()][9];
		int counter = 0;
		boolean firstQuot = false;
		boolean secondQuot = false;
		int firstIndex = 0;
		int secondIndex = 0;
		for(int i = 0; i < data.length; i++)
			for(int j = 0; j < data[i].length; j++)
				data[i][j] = "";
		for(int i = 0; i < a.size(); i++) {
			counter = 0;
			firstQuot = false;
			secondQuot = false;
			for(int j = 0; j < a.get(i).length()-1; j++) {
				if(a.get(i).charAt(j) == '\\'&& firstQuot == false)
				{
					firstQuot = true;
					firstIndex = j+1;
				}
				else if(a.get(i).substring(j, j+2).equals("\\,") && firstQuot == true) {
					secondQuot = true;
					secondIndex = j;
				}
				else if(j == a.get(i).length()-2 && a.get(i).substring(j+1).equals("\\") && firstQuot == true)
				{
					secondQuot = true;
					secondIndex = j+1;
				}
				if(firstQuot && secondQuot) {
					data[i][counter] = a.get(i).substring(firstIndex, secondIndex);
					firstQuot = false;
					secondQuot = false;
					counter++;
				}
			}
			companys[i] = new Company(data[i]);
		}
		return companys;
	}
	
	/*
	 * retrieves the evaluation results from evaluation.txt and will 
	 * use the stringToEvaluation method to convert from an arraylist 
	 * into a 2d array.
	 */
	public static String[][] getEvaluationResults() {
		String [][] evaluationdata;
		String read;
		ArrayList<String> evaluationData = new ArrayList<String>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader("evaluation.txt"));
			while((read = reader.readLine()) != null) {
				evaluationData.add(read);
			}
			reader.close();
		}
		catch(IOException i) {
			try{
				File file = new File("evaluation.txt");
				file.createNewFile();
			}
			catch(IOException j){}
		}
		evaluationdata = stringToEvaluation(evaluationData);
		return evaluationdata;
	}
	
	/*
	 *  Gets the evaluation scores where 
	 *  0 = evaluation ID
	 *  1 = employee ID
	 *  2 = employer ID
	 *  3 = work quality
	 *  4 = work habits
	 *  5 = job knowledge
	 *  6 = behavior/relations w/ others
	 *  7 = average score
	 *  8 = overall score
	 *  9 = recommendation
	 */
	public static String[][] getEvaluationScores(){
		String[][] evaluationResults = getEvaluationResults();
		String [][] evaluationScores = new String[evaluationResults.length][10];
		for(int i = 0; i < evaluationResults.length; i++)
		{
			evaluationScores[i][0] = evaluationResults[i][0];
			evaluationScores[i][1] = evaluationResults[i][1];
			evaluationScores[i][2] = evaluationResults[i][2];
			evaluationScores[i][3] = evaluationResults[i][5];
			evaluationScores[i][4] = evaluationResults[i][7];
			evaluationScores[i][5] = evaluationResults[i][9];
			evaluationScores[i][6] = evaluationResults[i][11];
			evaluationScores[i][7] = evaluationResults[i][13];
			evaluationScores[i][8] = evaluationResults[i][14];
			evaluationScores[i][9] = evaluationResults[i][16];
			
		}
		return evaluationScores;
	}
	
	
	/*
	 * returns a boolean based on whether the employeeID already has
	 * an evaluation report
	 */
	public static boolean hasEvaluationReport(String employeeID)
	{
		String [][] evalResults = getEvaluationResults();
		for(int i = 0; i < evalResults.length; i++)
		{
			if(evalResults[i][1].equals(employeeID))
				return true;
		}
		return false;
	}
	
	/*
	 * Returns a boolean based on whether there is such and employee ID 
	 * inside of fieldPlacement.txt
	 */
	public static boolean hasRelationship(String employeeID)
	{
		String[][] relationshipData = getCompanyRelationship();
		for(int i = 0; i < relationshipData.length; i++)
			if(relationshipData[i][0].equals(employeeID))
				return true;
		return false;
	}
	/*
	 * method called from getEvaluationResults method, and converts
	 * the given arrayList into a 2d array based on the given data.
	 * 
	 * PRECONDITION: ArrayList a must be a line from the evaluation.txt
	 */
	public static String[][] stringToEvaluation(ArrayList<String> a) {
		String [][] data = new String[a.size()][17];
		int counter = 0;
		boolean firstQuot = false;
		boolean secondQuot = false;
		int firstIndex = 0;
		int secondIndex = 0;
		for(int i = 0; i < data.length; i++)
			for(int j = 0; j < data[i].length; j++)
				data[i][j] = new String("");
		for(int i = 0; i < a.size(); i++) {
			counter = 0;
			firstQuot = false;
			secondQuot = false;
			for(int j = 0; j < a.get(i).length()-1; j++) {
				if(a.get(i).charAt(j) == '\\'&& firstQuot == false)
				{
					firstQuot = true;
					firstIndex = j+1;
				}
				else if(a.get(i).substring(j, j+2).equals("\\,") && firstQuot == true) {
					secondQuot = true;
					secondIndex = j;
				}
				else if(j == a.get(i).length()-2 && a.get(i).substring(j+1).equals("\\") && firstQuot == true)
				{
					secondQuot = true;
					secondIndex = j+1;
				}
				if(firstQuot && secondQuot) {
					data[i][counter] = a.get(i).substring(firstIndex, secondIndex);
					firstQuot = false;
					secondQuot = false;
					counter++;
				}
			}
		}
		return data;
	}
}
