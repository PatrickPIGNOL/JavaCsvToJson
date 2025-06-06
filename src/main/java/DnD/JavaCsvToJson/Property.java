package DnD.JavaCsvToJson;

public class Property<T> 
{
	private String aName;
	private String aNameJSON;
	private boolean aQuoted; 
	private T aValue;
	
	public Property(String pName, String pNameJSON, boolean pQuoted, T pValue)
	{
		this.aName = pName;
		this.aNameJSON = pNameJSON;
		this.aQuoted = pQuoted;
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
		String vResult = "";
		if(!this.aQuoted)
		{
			vResult += this.aValue.toString(); 
		}
		else
		{
			vResult += "\"" + this.aValue.toString() + "\"";
		}
		return vResult;
	}
}
