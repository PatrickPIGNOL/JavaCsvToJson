package DnD.JavaCsvToJson;

public enum ESpellAttackType
{
	None(""),
	RangedAttack("ranged"),
	MeleeAttack("melee"),
	StrengthSave("str_save"),
	DexteritySave("dex_save"),
	ConstitutionSave("con_save"),
	IntelligenceSave("int_save"),
	WisdomSave("wis_save"),
	CharismaSave("cha_save")
	;
	
	private String aID;
	ESpellAttackType(String pID) 
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
