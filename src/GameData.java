import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GameData {
	
	public static String[] gameLabels = { "Name", "Rating", "Platform", "Date", "Review", "Hidden" };
	public Object[] gameData = new Object[6];
	
	public GameData()
	{
		
	}
	
	public GameData(String n, String r, String p, Date d, String syn, String rev)
	{	
		gameData[0] = n;
		gameData[1] = r;
		gameData[2] = p;
		gameData[3] = d;
		gameData[4] = syn;
		gameData[5] = rev;	
	}


}
