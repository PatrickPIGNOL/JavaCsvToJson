package DnD.JavaCsvToJson;

public enum ESpellSchool 
{
	Abjuration("abjuration", "abjuration"),
	Conjuration("conjuration", "conjuration"),
	Divination("divination", "divination"),
	Enchantment("enchantment", "enchantement"),
	Evocation("evocation", "évocation"),
	Illusion("illusion", "illusion"),
	Necromancy("necromancy", "nécromancie"),
	Transmutation("transmutation", "transmutation")
	;
	
	private String aID;
	private String aSchoolFR;
	ESpellSchool(String pID, String pSchoolFR) 
	{
		this.aID = pID;
		this.aSchoolFR = pSchoolFR;
	}
	
	public String mID()
	{
		return this.aID;
	}
	
	public String mSchoolFr()
	{
		return this.aSchoolFR;
	}
	
	public String toString()
	{
		return this.aID;
	}
}
