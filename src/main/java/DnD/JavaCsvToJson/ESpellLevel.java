package DnD.JavaCsvToJson;

public enum ESpellLevel 
{
	Cantrip("1", ""),
	Level1("2", "Level 1"),
	Level2("3", "Level 2"),
	Level3("4", "Level 3"),
	Level4("5", "Level 4"),
	Level5("6", "Level 5"),
	Level6("7", "Level 6"),
	Level7("8", "Level 7"),
	Level8("9", "Level 8"),
	Level9("10", "Level 9")
	;
	
	private String aID;
	private String aLevel;
	ESpellLevel(String pID, String pLevel) 
	{
		this.aID = pID;
		this.aLevel = pLevel;
	}
	
	public String mID()
	{
		return this.aID;
	}
	
	public String mLevel()
	{
		return this.aLevel;
	}
	
	public String toString()
	{
		return this.aID;
	}
}
