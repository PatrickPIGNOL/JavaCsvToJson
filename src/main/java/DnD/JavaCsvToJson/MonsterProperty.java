package DnD.JavaCsvToJson;

import java.util.ArrayList;
import java.util.List;

public class MonsterProperty implements IExportable
{
	Property<String> aType;
	Property<String> aName;
	Property<String> aValue;
	
	MonsterProperty
	(
		Property<String> pType,
		Property<String> pName,
		Property<String> pValue
	)
	{
		this.aType = pType;
		this.aName = pName;
		this.aValue = pValue;
	}
	
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
		vResult.add("Property");
		vResult.add(this.aType.mValue());
		vResult.add("PropertyName");
		vResult.add(this.aName.mValue());
		vResult.add("PropertyValue");
		vResult.add(this.aValue.mValue());
		
		return vResult;
	}


	@Override
	public List<String> mToBook() 
	{
		List<String> vResult = new ArrayList<>();
		vResult.add("Property");
		vResult.add(this.aType.mValue());
		vResult.add("PropertyName");
		vResult.add(this.aName.mValue());
		vResult.add("PropertyValue");
		vResult.add(this.aValue.mValue());
		
		return vResult;
	}
}
