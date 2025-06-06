package JavaCsvToJson.JavaCsvToJson;

public class SpellAttack 
{
	private Property<String> aSpellTime; 
	private Property<String> aSpellRange;
	private Property<String> aSpellDuration;
	private Property<Boolean> aRitualCast;
	private Property<Boolean> aConcentration;
	private Property<Boolean> aSpellComponentV;
	private Property<Boolean> aSpellComponentS;
	private Property<Boolean> aSpellComponentM;
	private Property<String> aSpellComponentDescription;
	private Property<Boolean> aMaterialCost;
	//private SpellAttackType aSpellAttackType;
	private Property<SpellAttackType> aSaveThrow;
	private Property<String> aSpellDamage;
	//private SpellDamageType aSpellDamageType;
	private Property<String> aSpellDescription;
	
	public SpellAttack
	(
		Property<String> pSpellTime, 
		Property<String> pSpellRange, 
		Property<String> pSpellDuration, 
		Property<Boolean> pRitualCast, 
		Property<Boolean> pConcentration, 
		Property<Boolean> pSpellComponentV, 
		Property<Boolean> pSpellComponentS, 
		Property<Boolean> pSpellComponentM, 
		Property<String> pSpellComponentDescription,
		Property<Boolean> pMaterialCost,
		Property<SpellAttackType> pSaveThrow, 
		Property<String> pSpellDamage, 
		Property<String> pSpellDescription
	)
	{
		this.aSpellTime = pSpellTime;
		this.aSpellRange = pSpellRange;
		this.aSpellDuration = pSpellDuration;
		this.aRitualCast = pRitualCast;
		this.aConcentration = pConcentration;
		this.aSpellComponentV = pSpellComponentV;
		this.aSpellComponentS = pSpellComponentS;
		this.aSpellComponentM = pSpellComponentM;
		this.aSpellComponentDescription = pSpellComponentDescription;
		this.aMaterialCost = pMaterialCost;
		//this.aSpellAttackType = null;
		this.aSaveThrow = pSaveThrow;
		this.aSpellDamage = pSpellDamage;
		//this.aSpellDamageType = null;
		this.aSpellDescription = pSpellDescription;
	}
	
	public Property<String> mSpellTime()
	{
		return this.aSpellTime;		
	}
	
	public Property<String> mSpellRange()
	{
		return this.aSpellRange;	
	}
	
	public Property<String> mSpellDuration()
	{
		return this.aSpellDuration;
	}
	
	public Property<Boolean> mRitualCast()
	{
		return this.aRitualCast;
	}
	
	public Property<Boolean> mConcentration()
	{
		return this.aConcentration;
	}
	
	public Property<Boolean> mSpellComponentV()
	{
		return this.aSpellComponentV;
	}
	
	public Property<Boolean> mSpellComponentS()
	{
		return this.aSpellComponentS;
	}
	
	public Property<Boolean> mSpellComponentM()
	{
		return this.aSpellComponentM;
	}
	
	public Property<String> mSpellComponentDescription()
	{
		return this.aSpellComponentDescription;
	}
	
	public Property<Boolean> mMaterialCost()
	{
		return this.aMaterialCost;
	}
	
	/*
	public SpellAttackType mSpellAttackType()
	{
		return this.aSpellAttackType;
	}
	*/
	
	public Property<SpellAttackType> mSaveThrow()
	{
		return this.aSaveThrow;
	}
	
	public Property<String> mSpeelDamage()
	{
		return this.aSpellDamage;
	}
	
	/*
	public SpellAttackType mSpellDamageType()
	{
		return this.aSpellDammageType;
	}
	*/
	
	public Property<String> mSpellDescription()
	{
		return this.aSpellDescription;
	}
	
	public String mToJSON()
	{
		return  this.aSpellTime.mToJSON() + 
				",\n " + this.aSpellRange.mToJSON() + 
				",\n " + this.aSpellDuration.mToJSON() +
				",\n " + this.aRitualCast.mToJSON() +
				",\n " + this.aConcentration.mToJSON() +
				",\n " + this.aSpellComponentV.mToJSON() + 
				",\n " + this.aSpellComponentS.mToJSON() + 
				",\n " + this.aSpellComponentM.mToJSON() +
				",\n " + this.aSpellComponentDescription.mToJSON() +
				",\n " + this.aMaterialCost.mToJSON() +
				",\n " + this.aSaveThrow.mToJSON() + 
				",\n " + this.aSpellDamage.mToJSON() + 
				",\n " + this.aSpellDescription.mToJSON();
	}
	
	public String mCSVHeaders()
	{
		return this.aSpellTime.mName() +
			", " + this.aSpellRange.mName() + 
			", " + this.aSpellDuration.mName() + 
			", " + this.aRitualCast.mName() + 
			", " + this.aConcentration.mName() + 
			", " + this.aSpellComponentV.mName() + 
			", " + this.aSpellComponentS.mName() + 
			", " + this.aSpellComponentM.mName() + 
			", " + this.aSpellComponentDescription.mName() + 
			", " + this.aSaveThrow.mName() + 
			", " + this.aSpellDamage.mName() + 
			", " + this.aSpellDescription.mName();
	}
	
	public String mToCSV()
	{
		return	this.aSpellTime.mToCSV() + 
				", " + this.aSpellRange.mToCSV() + 
				", " + this.aSpellDuration.mToCSV() + 
				", " + this.aRitualCast.mToCSV() + 
				", " + this.aConcentration.mToCSV() + 
				", " + this.aSpellComponentV.mToCSV() + 
				", " + this.aSpellComponentS.mToCSV() + 
				", " + this.aSpellComponentM.mToCSV() + 
				", " + this.aSpellComponentDescription.mToCSV() + 
				", " + this.aSaveThrow.mToCSV() + 
				", " + this.aSpellDamage.mToCSV() + 
				", " + this.aSpellDescription.mToCSV();
	}
}
