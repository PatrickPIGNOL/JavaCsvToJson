package DnD.JavaCsvToJson;

public enum EObjectHeader implements IHeader 
{
	Name("Name", "objectName", true),
	Type("Type", "objectType", true),
	Rarity("Rarity", "objectRarity", true),
	Link("Link", "objectLink", true),
	Description("Description", "objectDescription", true)
	;

	private String aName;
	private String aJsonName;
	private boolean aQuoted;
	private EObjectHeader(String pName, String pJsonName, boolean pQuoted) 
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
		return aJsonName;
	}

	@Override
	public boolean mQuoted() 
	{		
		return aQuoted;
	}

}
