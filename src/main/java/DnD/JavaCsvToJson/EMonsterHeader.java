package DnD.JavaCsvToJson;

public enum EMonsterHeader implements IHeader
{
	Name("Name", "monsterName", true),
	Alignment("Alignment", "alignment", true),
	MonsterType("Monster Type", "monsterType", true),
	Size("Size", "size", true),
	ArmorClassInput("Armor Class Input", "acInput", true),
	HPMaxRoll("HPMax Roll", "HPMaxRoll", true),
	HP("HP", "hp", false),
	HPMax("HP Max", "hpmax", false),
	Strength("Strength", "strength", false),
	Dexterity("Dexterity", "dexterity", false),
	Constitution("Constitution", "constitution", false),
	Intelligence("Intelligence", "intelligence", false),
	Wisdom("Wisdom", "wisdom", false),
	Charisma("Charisma", "charisma", false),
	ChalengeRating("Chalenge Rating", "chalangeRating", true),
	ProeficiencyInput("Proeficiency Input", "proeficiencyInput", false),
	MonsterSubtype("Monster Subtype", "monsterSubtype", true),
	Properties("Properties", "properties", false),
	Property("Property", "property", true),
	PropertyName("Property Name", "propertyName", true),
	PropertyValue("Property Value", "propertyValue", true),
	SpecialAbilities("Special Abilities", "specialAbilities", true),
	MonsterAvatar("Monster Avatar", "monsterAvatar", false),
	Avatar("Avatar", "avatar", true),
	Token("Token", "token", true),
	Frame("Frame", "frame", false),
	Actions("Actions", "actions", false),
	AttackSaveThrow("Attack Save Throw", "attackSt", true),
	AttackSaveThrowDC("Attack Save Throw Difficulty Class", "attackStDC", false),
	AttackDescription("Attack Description", "attackDescription", true),
	AttackName("Attack Name", "attackName", true),
	AttackHit("Attack Hit", "attackHit", false),
	AttackDamage("Attack Damage", "attackDamage", true),
	AttackType("Attack Type", "attackType", true),
	Skills("Skills","skills", false),
	ActionsReadCommon("Action Read Common", "actionRead_comon", false),
	ArmorClass("Armor Class", "ac", false),
	;

	private String aName;
	private String aJsonName;
	private boolean aQuoted;

	EMonsterHeader(String pName, String pJsonName, boolean pQuoted) 
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
