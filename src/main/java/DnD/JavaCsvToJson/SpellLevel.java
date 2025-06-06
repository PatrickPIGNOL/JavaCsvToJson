package DnD.JavaCsvToJson;

public enum SpellLevel 
{
	Cantrip("1"),
	Level1("2"),
	Level2("3"),
	Level3("4"),
	Level4("5"),
	Level5("6"),
	Level6("7"),
	Level7("8"),
	Level8("9"),
	Level9("10")
	;
	
	private String aID;
	SpellLevel(String pID) 
	{
		this.aID = pID;
	}
	
	public String mID()
	{
		return this.aID;
	}
	
	public String toString()
	{
		return this.aID;
	}
}
