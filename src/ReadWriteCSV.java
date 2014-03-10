import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

 
public class ReadWriteCSV {
	
	public ReadWriteCSV()
	{
	}

	public String[] ReadPlatformData(String csvFile) 
	{
		
		CSVReader reader = null;
		String [] nextLine = null;
		try {
			reader = new CSVReader(new FileReader(csvFile));
			nextLine = reader.readNext();
			reader.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return nextLine;
	}
	
	public Vector<GameData> ReadGameData(String csvFile) 
	{
		
		CSVReader reader = null;
		String [] nextLine = null;
		Vector<GameData> gameDataList = new Vector<GameData>();
		
		try {
			reader = new CSVReader(new FileReader(csvFile));
			while ((nextLine = reader.readNext()) != null) {
				try {
					gameDataList.addElement(new GameData(nextLine[0], nextLine[1], nextLine[2], new SimpleDateFormat("MM/dd/yy").parse(nextLine[3]), nextLine[4], nextLine[5]));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			reader.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return gameDataList;
	}
	
	public void WriteGameData(String csvFile)
	{
		 try {
			CSVWriter writer = new CSVWriter(new FileWriter(csvFile));
			Vector<Vector> entries = GameRater.tblmodel.getDataVector();
			DateFormat df = new SimpleDateFormat("MM/dd/yy");
			
			for (int i = 0; i < entries.size(); i++)
			{				
				Vector<Object> s = entries.elementAt(i);
				String[] dataRow = new String[s.size()];
				for (int j = 0; j < s.size(); j++)					
				{
					if (j == 3)
						dataRow[j] = df.format(s.elementAt(j));
					else
						dataRow[j] = s.elementAt(j).toString();
				}
				writer.writeNext(dataRow);
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void WritePlatform(String csvFile, Vector<String> platforms)
	{
		 try {
			CSVWriter writer = new CSVWriter(new FileWriter(csvFile));
						
			platforms.remove("-- Add Platform --");
			platforms.remove("None");
			writer.writeNext(platforms.toArray(new String[platforms.size()]));
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Vector<String> loadPlatforms(String filename)
	{
		Vector<String> platforms;
		
		platforms = new Vector<String>(Arrays.asList(ReadPlatformData(filename)));
		if (platforms != null)
		{
			platforms.add(0, "None");
			platforms.add("-- Add Platform --");
		}
		
		return platforms;
	}
 
}
