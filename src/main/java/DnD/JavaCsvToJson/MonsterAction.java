package DnD.JavaCsvToJson;

import java.util.ArrayList;
import java.util.List;

public class MonsterAction implements IExportable 
{
	Property<String> aAttackName;
	Property<String> aAttackDescription;
	Property<Integer> aAttackHit;
	Property<String> aAttackDamage;
	Property<String> aAttackType;
	Property<String> aAttackSavingThrow;

	public MonsterAction
	(
		Property<String> pAttackName,
		Property<String> pAttackDescription,
		Property<Integer> pAttackHit,
		Property<String> pAttackDamage,
		Property<String> pAttackType,
		Property<String> pAttackSavingThrow
	)
	{
		this.aAttackName = pAttackName;
		this.aAttackDescription = pAttackDescription;
		this.aAttackHit = pAttackHit;
		this.aAttackDamage = pAttackDamage;
		this.aAttackType = pAttackType;
		this.aAttackSavingThrow = pAttackSavingThrow;
	}

	@Override
	public String mToJSON() 
	{
		return null;
	}

	@Override
	public List<String> mCSVHeaders() 
	{
		List<String> vResult = new ArrayList<>();
		return vResult;
	}

	@Override
	public List<String> mToCSV() 
	{		
		List<String> vResult = new ArrayList<>();
		return vResult;
	}

	@Override
	public List<String> mToBook() 
	{
		List<String> vResult = new ArrayList<>();
		vResult.addAll(this.aAttackName.mToBook());
		if(this.aAttackDescription.mValue() != null)
		{
			vResult.addAll(this.aAttackDescription.mToBook());
		}
		if(this.aAttackHit.mValue() != null)
		{
			vResult.addAll(this.aAttackHit.mToBook());	
		}
		if(this.aAttackDamage.mValue() != null)
		{
			vResult.addAll(this.aAttackDamage.mToBook());
		}
		if(this.aAttackType.mValue() != null)
		{
			vResult.addAll(this.aAttackType.mToBook());
		}
		if(this.aAttackSavingThrow.mValue() != null)
		{
			vResult.addAll(this.aAttackSavingThrow.mToBook());
		}
		return vResult;
	}
}
