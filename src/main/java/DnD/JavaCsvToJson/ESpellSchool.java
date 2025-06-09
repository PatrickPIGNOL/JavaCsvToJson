package DnD.JavaCsvToJson;

public enum ESpellSchool 
{
	Abjuration("abjuration"),
	Conjuration("conjuration"),
	Divination("divination"),
	Enchantment("enchantment"),
	Evocation("evocation"),
	Illusion("illusion"),
	Necromancy("necromancy"),
	Transmutation("transmutation")
	;
	
	private String aID;
	ESpellSchool(String pID) 
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
