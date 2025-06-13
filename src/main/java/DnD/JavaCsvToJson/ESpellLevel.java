package DnD.JavaCsvToJson;

public enum ESpellLevel 
{
	Cantrip(1, "Cantrip", "niveau 0", "level 0" ),
	Level1(2,  "Level 1", "niveau 1", "level 1" ),
	Level2(3,  "Level 2", "niveau 2", "level 2" ),
	Level3(4,  "Level 3", "niveau 3", "level 3" ),
	Level4(5,  "Level 4", "niveau 4", "level 4" ),
	Level5(6,  "Level 5", "niveau 5", "level 5" ),
	Level6(7,  "Level 6", "niveau 6", "level 6" ),
	Level7(8,  "Level 7", "niveau 7", "level 7" ),
	Level8(9,  "Level 8", "niveau 8", "level 8" ),
	Level9(10, "Level 9", "niveau 9", "level 9" )
	;
	
	private int aID;
	private String aLevel;
	private String aLevelFR;
	private String aLevelEN;
	ESpellLevel(int pID, String pLevel, String pLevelFR, String pLevelEN) 
	{
		this.aID = pID;
		this.aLevel = pLevel;
		this.aLevelFR = pLevelFR;
		this.aLevelEN = pLevelEN;
	}
	
	public int mID()
	{
		return this.aID;
	}
	
	public String mLevel()
	{
		return this.aLevel;
	}
	
	public String mLevelFR()
	{
		return this.aLevelFR;
	}
	
	public String mLevelEN()
	{
		return this.aLevelEN;
	}
	
	public String toString()
	{
		return Integer.toString(this.aID);
	}
}
