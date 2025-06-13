package DnD.JavaCsvToJson;

public enum ESpellAttackType
{
	None("", ""),
	RangedAttack("ranged", "Distance"),
	MeleeAttack("melee","Méllée"),
	StrengthSave("str_save","Force"),
	DexteritySave("dex_save","Dexterity"),
	ConstitutionSave("con_save","Constitution"),
	IntelligenceSave("int_save","Intelligence"),
	WisdomSave("wis_save","Sagesse"),
	CharismaSave("cha_save","Charisme")
	;
	
	private String aID;
	private String aNameFR;
	ESpellAttackType(String pID, String pNameFr) 
	{
		this.aID = pID;
		this.aNameFR = pNameFr;
	}
	
	public String mID()
	{
		return this.aID;
	}
	
	public String mNameFR()
	{
		return this.aNameFR;
	}
	
	public String toString()
	{
		return this.aID;
	}
}
