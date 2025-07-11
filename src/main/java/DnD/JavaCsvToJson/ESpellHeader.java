package DnD.JavaCsvToJson;

public enum ESpellHeader implements IHeader
{
	Name("Name", "spell", true),
	Level("Level", "spellLevel", false),
	RealLevel("Real Level", "spellRealLevel", false),
	School("School", "spellSchool", true),
	AlwaysPrepared("Always Prepared","alwaysPrepared", false),
	Time("Time", "spellTime", true),
	Range("Range", "spellRange", true),
	Duration("Duration", "spellDuration", true),
	Ritual("Ritual", "ritualCast", false),
	Concentration("Concentration","concentration", false),
	V("V", "spellComponentV", false),
	S("S", "spellComponentS", false),
	M("M", "spellComponentM", false),
	ComponentDescription("Component Description", "spellComponentDescription", true),
	MaterialCost("Material Cost", "materialCost", false),
	Actions("Actions", "actions", true),
	Attack("Attack", "spellAttack", true),
	SaveThrow("Save Throw", "saveThrow", true),
	Damage("Damage", "spellDamage", true),
	Description("Description", "spellDescription", true),
	Legendary("Legendary", "legendary", false)
	;

	private String aName;
	private String aJsonName;
	private boolean aQuoted;
	ESpellHeader(String pName, String pJsonName, boolean pQuoted) 
	{
		this.aName = pName;
		this.aJsonName = pJsonName;
		this.aQuoted = pQuoted;
	}
	
	@Override
	public String mName()
	{
		return this.aName;
	}
	
	@Override
	public String mJsonName()
	{
		return this.aJsonName;
	}
	
	@Override
	public boolean mQuoted()
	{
		return this.aQuoted;
	}
}
