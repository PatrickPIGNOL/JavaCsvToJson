package DnD.JavaCsvToJson;

public enum SpellSchool 
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
	SpellSchool(String pID) 
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
