package DnD.JavaCsvToJson;

import java.util.ArrayList;
import java.util.List;

public class Property<T> 
{
	private String aName;
	private String aNameJSON;
	private boolean aQuoted; 
	private T aValue;
	
	public Property(Enum<? extends IHeader> pHeader, T pValue)
	{
		this.aName = ((IHeader)pHeader).mName();
		this.aNameJSON = ((IHeader)pHeader).mJsonName();
		this.aQuoted = ((IHeader)pHeader).mQuoted();
		this.aValue = pValue;
	}
	
	public String mName()
	{
		return this.aName;
	}
	
	public String mNameJSON()
	{
		return this.aNameJSON;
	}
	
	public boolean mQuoted()
	{
		return this.aQuoted;
	}
	
	public T mValue()
	{
		return this.aValue;
	}
	
	public void mValue(T pValue)
	{
		this.aValue = pValue;
	}
	
	public String mToJSON()
	{
		String vResult = "";
		if(!this.aQuoted)
		{
			vResult += "\"\"" + this.aNameJSON + "\"\": " + this.aValue.toString();
		}
		else
		{
			vResult += "\"\"" + this.aNameJSON + "\"\": \"\"" + this.aValue.toString() + "\"\"";
		}
		return vResult;
	}
	
	public String mToCSV()
	{
		String vResult = null;
		if(this.aValue != null)
		{
			vResult = this.aValue.toString();			
		}
		return vResult;
	}
	
	public List<String> mToBook()
	{
		List<String> vResult = new ArrayList<>();
		if(this.aValue != null)
		{
			vResult.add(this.aNameJSON);
			if (!(this.aValue instanceof List<?> ))
			{
				vResult.add(this.aValue.toString());
			}
		}
		return vResult;
	}
}
