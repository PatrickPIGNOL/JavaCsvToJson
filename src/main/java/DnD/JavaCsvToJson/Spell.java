package DnD.JavaCsvToJson;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Spell implements IExportable
{
	private Property<String> aSpellName;	
	private Property<ESpellLevel> aSpellLevel;
	private Property<ESpellSchool> aSpellSchool;
	private Property<Boolean> aAlwaysPrepared;	
	private List<SpellAttack> aSpellAttacks;
	
	public static Spell mFromCsv(String[] pHeaders, String[] pCsv)
	{
		Spell vSpell = null;
		return vSpell;		
	}
	
	public static Spell mFromJson(String[] vTable) throws ParseException
	{
		Spell vResult = null;
		
		JSONObject vJSONObject = (JSONObject) new JSONParser().parse(vTable[1]);
		
		String vSpellName = (String) vJSONObject.getOrDefault(ESpellHeader.Name.mJsonName(), null);
		if(vSpellName == null)
		{
			vSpellName = vTable[0];
		}
		
		String vSpellLevelValue = (String) vJSONObject.getOrDefault(ESpellHeader.Level.mJsonName(), "1");
		ESpellLevel vSpellLevel = ESpellLevel.Cantrip;
		for(ESpellLevel vSpellLvl : ESpellLevel.values())
		{
			if (vSpellLevelValue.trim().equalsIgnoreCase(vSpellLvl.mID()))
			{
				vSpellLevel = vSpellLvl;
				break;
			}
		}
		
		String vSpellSchoolValue = (String) vJSONObject.getOrDefault(ESpellHeader.School.mJsonName(), ESpellSchool.Abjuration.mID());
		ESpellSchool vSpellSchool = ESpellSchool.Abjuration;
		for(ESpellSchool vSpellScl : ESpellSchool.values())
		{
			if (vSpellSchoolValue.trim().equalsIgnoreCase(vSpellScl.mID()))
			{
				vSpellSchool = vSpellScl;
				break;
			}
		}
		
		boolean vAlwaysPrepared = (boolean) vJSONObject.getOrDefault("alwaysPrepared", false);
		
		vResult = new Spell
		(
			new Property<String>(ESpellHeader.Name, vSpellName), 
			new Property<ESpellLevel>(ESpellHeader.Level, vSpellLevel),
			new Property<ESpellSchool>(ESpellHeader.School, vSpellSchool),
			new Property<Boolean>(ESpellHeader.AlwaysPrepared, vAlwaysPrepared)
		);
		
		String vSpellTime = (String) vJSONObject.getOrDefault(ESpellHeader.Time.mJsonName(), "");
		String vSpellRange = (String) vJSONObject.getOrDefault(ESpellHeader.Range.mJsonName(), "");
		String vSpellDuration = (String) vJSONObject.getOrDefault(ESpellHeader.Duration.mJsonName(), "");
		boolean vRitualCast = (boolean) vJSONObject.getOrDefault(ESpellHeader.Ritual.mJsonName(), false);
		boolean vConcentration = (boolean) vJSONObject.getOrDefault(ESpellHeader.Concentration.mJsonName(), false);
		boolean vSpellComponentV = (boolean) vJSONObject.getOrDefault(ESpellHeader.V.mJsonName(), false);
		boolean vSpellComponentS = (boolean) vJSONObject.getOrDefault(ESpellHeader.S.mJsonName(), false);
		boolean vSpellComponentM = (boolean) vJSONObject.getOrDefault(ESpellHeader.M.mJsonName(), false);
		String vSpellComponenDescription = (String) vJSONObject.getOrDefault(ESpellHeader.ComponentDescription.mJsonName(), "");
		boolean vMaterialCost = (boolean) vJSONObject.getOrDefault(ESpellHeader.MaterialCost.mJsonName(), false);
		String vSpellAttackType = (String) vJSONObject.getOrDefault(ESpellHeader.Attack.mJsonName(), "");
		ESpellAttackType vSpellAttack = ESpellAttackType.None;
		for(ESpellAttackType vType : ESpellAttackType.values())
		{
			if(vSpellAttackType.trim().equalsIgnoreCase(vType.mID()))
			{
				vSpellAttack = vType;
				break;
			}
		}
		String vSpellDamage = (String) vJSONObject.getOrDefault(ESpellHeader.Damage.mJsonName(), "");
		String vSpellDescription = (String) vJSONObject.getOrDefault(ESpellHeader.Description.mJsonName(), "");
		
		vResult.mSpellAttacks().add
		(
			new SpellAttack
			(
				new Property<String>(ESpellHeader.Time, vSpellTime),
				new Property<String>(ESpellHeader.Range, vSpellRange),
				new Property<String>(ESpellHeader.Duration, vSpellDuration),
				new Property<Boolean>(ESpellHeader.Ritual, vRitualCast),
				new Property<Boolean>(ESpellHeader.Concentration, vConcentration),
				new Property<Boolean>(ESpellHeader.V, vSpellComponentV),
				new Property<Boolean>(ESpellHeader.S, vSpellComponentS),
				new Property<Boolean>(ESpellHeader.M, vSpellComponentM),
				new Property<String>(ESpellHeader.ComponentDescription, vSpellComponenDescription),
				new Property<Boolean>(ESpellHeader.MaterialCost, vMaterialCost),
				new Property<ESpellAttackType>(ESpellHeader.Attack, vSpellAttack),
				new Property<String>(ESpellHeader.Damage, vSpellDamage),
				new Property<String>(ESpellHeader.Description, vSpellDescription)
			)
		);
		return vResult;
	}
	
	public Spell
	(
		Property<String> pSpellName, 
		Property<ESpellLevel> pSpellLevel,
		Property<ESpellSchool> pSpellSchool,
		Property<Boolean> pAlwaysPrepared
	)
	{
		this.aSpellName = pSpellName;
		this.aSpellLevel = pSpellLevel;
		this.aSpellSchool = pSpellSchool;
		this.aAlwaysPrepared = pAlwaysPrepared;
		this.aSpellAttacks = new ArrayList<SpellAttack>();
	}
	
	public Property<String> mSpellName()
	{
		return this.aSpellName;
	}
	
	public Property<ESpellLevel> mSpellLevel()
	{
		return this.aSpellLevel;
	}
	
	public Property<ESpellSchool> mSpellSchool()
	{
		return this.aSpellSchool;
	}
	
	public List<SpellAttack> mSpellAttacks()
	{
		return this.aSpellAttacks;
	}	
	
	public String mToJSON()
	{
		String vResult = "\"{\n " + this.aSpellName.mToJSON() + 
			",\n " + this.aSpellLevel.mToJSON() + 
			",\n " + this.aSpellSchool.mToJSON() +
			",\n " + this.aAlwaysPrepared.mToJSON();
		for(SpellAttack vSpellAttack : this.aSpellAttacks)
		{
			vResult +=  ",\n " + vSpellAttack.mToJSON();
		}
		vResult += "}";
		return vResult;
	}
	
	public List<String> mCSVHeaders()
	{
		List<String> vResult = new ArrayList<>();
		vResult.add(this.aSpellName.mName());
		vResult.add(this.aSpellLevel.mName());
		vResult.add(this.aSpellSchool.mName());
		vResult.add(this.aAlwaysPrepared.mName());
		for(SpellAttack vSpellAttack : this.aSpellAttacks)
		{
			vResult.addAll(vSpellAttack.mCSVHeaders());
		}
		return vResult;
	}
	
	public List<String> mToCSV()
	{
		List<String> vResult = new ArrayList<>();
		vResult.add(this.aSpellName.mToCSV());
		vResult.add(this.aSpellLevel.mToCSV());
		vResult.add(this.aSpellSchool.mToCSV());
		vResult.add(this.aAlwaysPrepared.mToCSV());
		for(SpellAttack vSpellAttack : this.aSpellAttacks)
		{
			vResult.addAll(vSpellAttack.mToCSV());
		}
		return vResult;
	}
}