package availty_csv_filter;

import java.io.*;
import java.util.*;

public class availty_csv_filter {
	
	private static availty_csv_filter acf1 = new availty_csv_filter();
	private Client currentClient;
	private FileReader reader;
	private Scanner scanner = new Scanner(System.in);
	private Scanner csvFile;
	private ArrayList<Client> csvLinesUnSorted = new ArrayList<Client>();
	private ArrayList<Client> csvLinesSorted;
	private ArrayList<String> csvNames;
	private String userID; 
	private String fullName;
	private int version;
	private String insuranceCompany;
	private String csvName;
	private String currentName;
	private int arraySize = 0;
	private int startIndex = 0;
	
	//gets a csv file name from the user and extracts its contents into a usable format.
	public void parseCsvData() 
	{
		//prompts user for file name to be parsed through.
		System.out.print("Please enter a csv file name: ");
		String csvName = scanner.next();
		
		//imports the file to be used within the program
		System.out.println("processing csv file.");
		try {
			reader = new FileReader(csvName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		csvFile = new Scanner (reader);
		
		//skips over the line containing data headers
		csvFile.nextLine();

		//stores all lines in the csv file in an ArrayList as "Client objects".
		int a = 0;
		while(csvFile.hasNextLine())
		{
            String s = csvFile.nextLine();
            String[] split = s.split(",");
            //System.out.println(split[0] + split[2]);
            csvLinesUnSorted.add(new Client(split[0], split[1], Integer.parseInt(split[2]), split[3]));
            a++;
        }
		
		System.out.println("file read!");
		csvFile.close();
	}
	
	//prints contents of the csv file
	public void outputCsvData()
	{
		System.out.println("file contents:");
		System.out.print(String.format("%-20s","User ID"));
		System.out.print(String.format("%-20s","Full Name"));
		System.out.print(String.format("%-20s","Version"));
		System.out.print(String.format("%-20s","Insurance Company"));
		System.out.println("");
		
		for (int a = 0; a < csvLinesUnSorted.size(); a++)
		{
			userID = csvLinesUnSorted.get(a).getUserID();
			fullName = csvLinesUnSorted.get(a).getFullName();
			version = csvLinesUnSorted.get(a).getVersion();
			insuranceCompany = csvLinesUnSorted.get(a).getInsuranceCompany();
			
			System.out.print(String.format("%-20s",userID));
			System.out.print(String.format("%-20s",fullName));
			System.out.print(String.format("%-20s",version));
			System.out.print(String.format("%-20s",insuranceCompany));
		}
	}
	//removes all entries which have the same name, but a lower version number than another entry.
	private void removeDuplicates() 
	{
		for (int a = 0; a < csvLinesUnSorted.size(); a++)
		{
			for (int b = 1; a < csvLinesUnSorted.size(); a++)
			{
				if (csvLinesUnSorted.get(a).getUserID().equals(csvLinesUnSorted.get(b).getUserID()))
				{
					if (csvLinesUnSorted.get(a).getVersion() > (csvLinesUnSorted.get(b).getVersion()))
					{
						csvLinesUnSorted.remove(b);
					}
					else
					{
						csvLinesUnSorted.remove(a);
					}
				}
			}
		}
	}
	
	//sorts the unsorted arraylist into a sorted arraylist via the full name
	private void sortByName() 
	{
		arraySize = csvLinesUnSorted.size();
		csvNames = new ArrayList<String>(arraySize);
		
		for (int a = 0; a < arraySize; a++)
		{
			fullName = csvLinesUnSorted.get(a).getFullName();
			csvNames.add(fullName);
		}
		//sorted this way because I was having trouble figuring on how to use the sort method
		//on an instance of the arraylist value, rather than the value itself.
		Collections.sort(csvNames);
		
		csvLinesSorted = new ArrayList<Client>(arraySize);
		
		//Using the csvNames AL as a template as to what the sorted AL should look like,
		//the for loop carries out the sorting.
		for (int a = 0; a < arraySize; a++)
		{
			for (int b = 0; b < arraySize; b++)
			{
				currentName = csvLinesUnSorted.get(b).getFullName();
				
				if (csvNames.get(a).equals(currentName))
				{
					currentClient = csvLinesUnSorted.get(b);
					csvLinesSorted.add(currentClient);
				}
			}
		}
	}
	
	//Stores each line according to its insurance policy 
	private void storeByInsuranceCompany()
	{
		File file;
		FileWriter fw;
		String sortedInsuranceCompany = csvLinesSorted.get(0).getInsuranceCompany();

		try {	
				file = new File(sortedInsuranceCompany);
				fw = new FileWriter(file);
				
				for (int a = 0; a < arraySize; a++)
				{
					startIndex++;
					insuranceCompany = csvLinesSorted.get(a).getInsuranceCompany();
					
					//determines if the current entry belongs in the file that is currently being
					//written to.
					if (insuranceCompany.equals(sortedInsuranceCompany))
					{
						fw.write(
									csvLinesSorted.get(a).getUserID() + "," +
									csvLinesSorted.get(a).getFullName() + "," +
									csvLinesSorted.get(a).getVersion() + "," +
									csvLinesSorted.get(a).getInsuranceCompany()
								);
						System.lineSeparator();
						fw.flush();
						fw.close();
					}
					
					else
						break;	
				}
				
			} catch (IOException e) {
			e.printStackTrace();
		}
		//if there are still more values in the AL to be stored into a file, a recursive call is
		//made.
		if (startIndex <= arraySize)
		{
			storeByInsuranceCompany();
		}
	}
	
	public static void main (String[] args)
	{
		acf1.parseCsvData();
		acf1.outputCsvData();
		acf1.removeDuplicates();
		acf1.sortByName();
		acf1.storeByInsuranceCompany();
	}
}
