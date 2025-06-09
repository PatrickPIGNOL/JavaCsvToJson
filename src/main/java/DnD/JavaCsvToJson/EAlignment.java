package DnD.JavaCsvToJson;

public enum EAlignment 
{
	Default("default", "Select an alignment"),
	ChaoticEvil("chaoticEvil", "ChaoticEvil"),	
	ChaoticGood("chaoticGood", "ChaoticGood"),
	ChaoticNeutral("chaoticNeutral", "ChaoticNeutral"),
	LawfulEvil("lawfulEvil", "LawfulEvil"),
	LawfulGood("lawfulGood", "LawfulGood"),
	LawfulNeutral("lawfulNeutral", "LawfulNeutral"),
	Neutral("neutral", "Neutral"),
	NeutralEvil("neutralEvil", "NeutralEvil"),
	NeutralGood("neutralGood", "NeutralGood"),
	Unaligned("unaligned", "Unaligned")
	;

	private String aID;
	private String aAlignment;
	
	EAlignment(String pID, String pAlignment) 
	{
		this.aID = pID;
		this.aAlignment = pAlignment;
	}
	
	public String mID()
	{ 
		return this.aID;
	}
	
	public String mAlignment()
	{ 
		return this.aAlignment;
	}
}
