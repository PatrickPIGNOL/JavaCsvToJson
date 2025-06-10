package DnD.JavaCsvToJson;

import java.util.ArrayList;
import java.util.List;

public class SpecialAbility implements IExportable 
{

	@Override
	public String mToJSON() 
	{
		return "";
	}

	@Override
	public List<String> mCSVHeaders() 
	{
		List<String> vResult = new ArrayList<>();
		return vResult;
	}

	@Override
	public List<String> mToCSV() 
	{		
		List<String> vResult = new ArrayList<>();
		return vResult;
	}

	@Override
	public List<String> mToBook() 
	{
		return null;
	}

}
