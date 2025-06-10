package DnD.JavaCsvToJson;

public enum ESize 
{
	Default("default", "Select a size", ""),
	Tiny("tiny", "Tiny", "TP"),
	Small("small", "Small", "P"),
	Medium("medium", "Medium", "M"),
	Large("large", "Large", "G"),
	Huge("huge", "Huge", "TG"),
	Gargantuan("gargantuan", "Gargantuan", "Gig")
	;

	private String aID;
	private String aSize;
	private String aAideDD;
	
	ESize(String pID, String pSize, String pAideDD) 
	{
		this.aID = pID;
		this.aSize = pSize;
		this.aAideDD = pAideDD;
	}
	
	public String mID()
	{
		return this.aID ;
	}
	
	public String mSize()
	{
		return this.aSize ;
	}
	
	public String mAideDD()
	{
		return this.aAideDD;
	}
	
	@Override
	public String toString()
	{
		return this.aID;
	}
}
