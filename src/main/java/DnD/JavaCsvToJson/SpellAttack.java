package DnD.JavaCsvToJson;

import java.util.ArrayList;
import java.util.List;

public class SpellAttack implements IExportable 
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
	private Property<ESpellAttackType> aSaveThrow;
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
		Property<ESpellAttackType> pSaveThrow, 
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
	
	public Property<ESpellAttackType> mSaveThrow()
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
	
	@Override
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
	
	@Override
	public List<String> mCSVHeaders()
	{
		List<String> vResult = new ArrayList<>();

		vResult.add(this.aSpellTime.mName());
		vResult.add(this.aSpellRange.mName());
		vResult.add(this.aSpellDuration.mName());
		vResult.add(this.aRitualCast.mName());
		vResult.add(this.aConcentration.mName());
		vResult.add(this.aSpellComponentV.mName());
		vResult.add(this.aSpellComponentS.mName());
		vResult.add(this.aSpellComponentM.mName());
		vResult.add(this.aSpellComponentDescription.mName());
		vResult.add(this.aSaveThrow.mName());
		vResult.add(this.aSpellDamage.mName());
		vResult.add(this.aSpellDescription.mName());
		
		return vResult;
	}
	
	@Override
	public List<String> mToCSV()
	{
		List<String> vResult = new ArrayList<>();
		vResult.add(this.aSpellTime.mToCSV());
		vResult.add(this.aSpellRange.mToCSV());
		vResult.add(this.aSpellDuration.mToCSV()); 
		vResult.add(this.aRitualCast.mToCSV());
		vResult.add(this.aConcentration.mToCSV());
		vResult.add(this.aSpellComponentV.mToCSV());
		vResult.add(this.aSpellComponentS.mToCSV());
		vResult.add(this.aSpellComponentM.mToCSV());
		vResult.add(this.aSpellComponentDescription.mToCSV());
		vResult.add(this.aSaveThrow.mToCSV());
		vResult.add(this.aSpellDamage.mToCSV());
		vResult.add(this.aSpellDescription.mToCSV());		
		return vResult;
	}
}
