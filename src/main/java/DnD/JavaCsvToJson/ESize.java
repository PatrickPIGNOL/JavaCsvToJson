package DnD.JavaCsvToJson;

public enum ESize 
{
	Default("default", "Select a size"),
	Tiny("tiny", "Tiny"),
	Small("small", "Small"),
	Medium("medium", "Medium"),
	Large("large", "Large"),
	Huge("huge", "Huge"),
	Gargantuan("gargantuan", "Gargantuan")
	;

	private String aID;
	private String aSize;
	ESize(String pID, String pSize) 
	{
		this.aID = pID;
		this.aSize = pSize;
	}
	
	public String mID()
	{
		return this.aID ;
	}
	
	public String mSize()
	{
		return this.aSize ;
	}
}
