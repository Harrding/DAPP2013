package company;

import java.io.*;

/*
 * This file is used to print data with given information
 */

public class PrintToFile 
{
	
	// Prints employee and company field placements
	public static void printAssignedEmployee(String employeeNumber, String employerNumber) {
		BufferedWriter output;
		FileWriter streamFiles;
		try {
			streamFiles = new FileWriter("fieldPlacement.txt");
			output = new BufferedWriter(streamFiles);
			if(ReadFromFile.getNumberFromFile("fieldPlacement.txt") < 2) {
				output.write("\\" + employeeNumber + "\\,\\" + employerNumber + "\\");
			}
			else
				output.write("\n\\" + employeeNumber + "\\,\\" + employerNumber + "\\");
			output.close();
		}
		catch (IOException i) {}
		
	}

	// Prints all data of given employee to the file
	public static void printEmployeeDataToFile(String file, Employee employee) {
		BufferedWriter output; 
		FileWriter streamFiles;
		try {
			streamFiles = new FileWriter(file, true);
			output = new BufferedWriter(streamFiles);
			if(ReadFromFile.getNumberFromFile(file) < 2) {
				for(int i = 0; i < 10; i++) {
					if(i != 9)
						output.write("\\" + employee.getEmployeeData(i) + "\\,");
					else
						output.write("\\" + employee.getEmployeeData(i) + "\\");
				}
				output.close();
			}
			else {
				for(int i = 0; i < 10; i++) {
					if(i == 0)
						output.write("\n\\" + employee.getEmployeeData(i) + "\\,\\");
					else if(i != 9)
						output.write(employee.getEmployeeData(i) + "\\,\\");
					else
						output.write(employee.getEmployeeData(i) + "\\");
				}
				output.close();
			}
		}
		catch(IOException i) {}
	}
	
	// Prints companys data to the file by appending
	public static void printCompanyDataToFile(String file, Company company) {
		BufferedWriter output;
		FileWriter streamFiles;
		try {
			streamFiles = new FileWriter(file, true);
			output = new BufferedWriter(streamFiles);
			if(ReadFromFile.getNumberFromFile(file) < 2) {
				for(int i = 0; i < 9; i++) {
					if(i!= 8)
						output.write("\\" + company.getCompanyData(i) + "\\,");
					else
						output.write("\\" + company.getCompanyData(i) + "\\");
				}
				output.close();
			}
			else {
				for(int i = 0; i < 9; i++) {
					if(i == 0)
						output.write("\n\\" + company.getCompanyData(i) + "\\,");
					else if(i != 8)
						output.write("\\" + company.getCompanyData(i) + "\\,");
					else
						output.write("\\" + company.getCompanyData(i) + "\\");
				}
				output.close();
			}
		}
		catch(IOException i) {}
	}
	
	// Prints relationship data to the file
	public static void printRelationship(String file, String [] relations) {
		BufferedWriter output;
		FileWriter streamFiles;
		try {
			streamFiles = new FileWriter(file, true);
			output = new BufferedWriter(streamFiles);
			if(ReadFromFile.getNumberFromFile(file) < 2) {
				for(int i = 0; i < relations.length; i++) {
					if(i!= relations.length-1)
						output.write("\\" + relations[i] + "\\,");
					else
						output.write("\\" + relations[i] + "\\");
				}
				output.close();
			}
			else {
				for(int i = 0; i < relations.length; i++) {
					if(i == 0)
						output.write("\n\\" + relations[i] + "\\,");
					else if(i != relations.length - 1)
						output.write("\\" + relations[i] + "\\,");
					else
						output.write("\\" + relations[i] + "\\");
				}
				output.close();
			}
		}
		catch(IOException i) {
			System.out.println("ERROR: File Not Found");
		}
	}
	/*
	 * Prints all evaluation result data
	 */
	public static void printEvaluations(String file, String[] results) {
		BufferedWriter output;
		FileWriter streamFiles;
		try {
			streamFiles = new FileWriter(file, true);
			output = new BufferedWriter(streamFiles);
			if(ReadFromFile.getNumberFromFile(file) < 2) {
				for(int i = 0; i < results.length; i++) {
					if(i!= results.length-1)
						output.write("\\" + results[i] + "\\,\\");
					else
						output.write(results[i] + "\\");
				}
				output.close();
			}
			else {
				for(int i = 0; i < results.length; i++) {
					if(i == 0)
						output.write("\n\\" + results[i] + "\\,\\");
					else if(i != results.length - 1)
						output.write(results[i] + "\\,\\");
					else
						output.write(results[i] + "\\");
				}
				output.close();
			}
		}
		catch(IOException i) {}
	}
}
