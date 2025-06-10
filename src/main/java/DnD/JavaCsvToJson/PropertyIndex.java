package DnD.JavaCsvToJson;

public class PropertyIndex 
{
	public String aProperty;
	public int aIndex;
	public PropertyIndex(String pProperty, int pIndex)
	{
		this.aProperty = pProperty;
		this.aIndex = pIndex;
	}
	
	public String mProperty()
	{
		return this.aProperty;
	}
	
	public int mIndex()
	{
		return this.aIndex;
	}
}
