package DnD.JavaCsvToJson;

public enum EAlignment 
{
	Default("default", "Select an alignment", "tout alignement"),
	ChaoticEvil("chaoticEvil", "ChaoticEvil", "Chaotique Mauvais"),	
	ChaoticGood("chaoticGood", "ChaoticGood", "Chaotique Bon"),
	ChaoticNeutral("chaoticNeutral", "ChaoticNeutral", "Chaotique Neutre"),
	LawfulEvil("lawfulEvil", "LawfulEvil", "Loyal Mauvais"),
	LawfulGood("lawfulGood", "LawfulGood", "Loyal Bon"),
	LawfulNeutral("lawfulNeutral", "LawfulNeutral", "Loyal Neutre"),
	Neutral("neutral", "Neutral", "Neutre"),
	NeutralEvil("neutralEvil", "NeutralEvil", "Neutre Mauvais"),
	NeutralGood("neutralGood", "NeutralGood", "Neutre Bon"),
	Unaligned("unaligned", "Unaligned", "sans alignement")
	;

	private String aID;
	private String aAlignment;
	private String aAlignmentFR;
	
	EAlignment(String pID, String pAlignment, String pAlignmentFR) 
	{
		this.aID = pID;
		this.aAlignment = pAlignment;
		this.aAlignmentFR = pAlignmentFR;
	}
	
	public String mID()
	{ 
		return this.aID;
	}
	
	public String mAlignment()
	{ 
		return this.aAlignment;
	}
	
	public String mAlignmentFR()
	{
		return this.aAlignmentFR;
	}
	
	@Override
	public String toString()
	{
		return this.aID;
	}
}
