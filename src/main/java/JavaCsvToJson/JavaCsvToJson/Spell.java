package JavaCsvToJson.JavaCsvToJson;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Spell 
{
	private Property<String> aSpellName;	
	private Property<SpellLevel> aSpellLevel;
	private Property<SpellSchool> aSpellSchool;
	private Property<Boolean> aAlwaysPrepared;	
	private List<SpellAttack> aSpellAttacks;
	

	public static Spell mFromJson(String pJSON)
	{
		Spell vResult = null;
		try
		{
			JSONObject vJSONObject = (JSONObject) new JSONParser().parse(pJSON);
			String vSpellName = (String) vJSONObject.getOrDefault("spellName", "(Unamed Spell)");
	
			String vSpellLevelValue = (String) vJSONObject.getOrDefault("spellLevel", "1");
			SpellLevel vSpellLevel = SpellLevel.Cantrip;
			for(SpellLevel vSpellLvl : SpellLevel.values())
			{
				if (vSpellLvl.mID().equalsIgnoreCase(vSpellLevelValue))
				{
					vSpellLevel = vSpellLvl;
				}
			}
			
			String vSpellSchoolValue = (String) vJSONObject.getOrDefault("spellSchool", "abjuration");
			SpellSchool vSpellSchool = SpellSchool.Abjuration;
			for(SpellSchool vSpellScl : SpellSchool.values())
			{
				if (vSpellScl.mID().equalsIgnoreCase(vSpellSchoolValue))
				{
					vSpellSchool = vSpellScl;
				}
			}
			
			boolean vAlwaysPrepared = (boolean) vJSONObject.getOrDefault("alwaysPrepared", false);
			
			vResult = new Spell
			(
				new Property<String>("Name", "spellName", true, vSpellName), 
				new Property<SpellLevel>("Level", "spellLevel", true, vSpellLevel),
				new Property<SpellSchool>("School", "spellSchool", true, vSpellSchool),
				new Property<Boolean>("Always Prepared", "alwaysPrepared", false, vAlwaysPrepared)
			);
			
			String vSpellTime = (String) vJSONObject.getOrDefault("spellTime", "");
			String vSpellRange = (String) vJSONObject.getOrDefault("spellRange", "");
			String vSpellDuration = (String) vJSONObject.getOrDefault("spellDuration", "");
			boolean vRitualCast = (boolean) vJSONObject.getOrDefault("ritualCast", false);
			boolean vConcentration = (boolean) vJSONObject.getOrDefault("concentration", false);
			boolean vSpellComponentV = (boolean) vJSONObject.getOrDefault("spellComponentV", false);
			boolean vSpellComponentS = (boolean) vJSONObject.getOrDefault("spellComponentS", false);
			boolean vSpellComponentM = (boolean) vJSONObject.getOrDefault("spellComponentM", false);
			String vSpellComponenDescription = (String) vJSONObject.getOrDefault("spellComponentDescription", "");
			boolean vMaterialCost = (boolean) vJSONObject.getOrDefault("materialCost", false);
			String vSpellAttackType = (String) vJSONObject.getOrDefault("spellAttack", "");
			SpellAttackType vSpellAttack = SpellAttackType.None;
			for(SpellAttackType vType : SpellAttackType.values())
			{
				if(vType.mID() == vSpellAttackType)
				{
					vSpellAttack = vType;
				}
			}
			String vSpellDamage = (String) vJSONObject.getOrDefault("spellDamage", "");
			String vSpellDescription = (String) vJSONObject.getOrDefault("spellDescription", "");
			
			vResult.mSpellAttacks().add
			(
				new SpellAttack
				(
					new Property<String>("Time", "spellTime", true, vSpellTime),
					new Property<String>("Range", "spellRange", true, vSpellRange),
					new Property<String>("Duration", "spellDuration", true, vSpellDuration),
					new Property<Boolean>("Ritual", "ritualCast", false, vRitualCast),
					new Property<Boolean>("Concentration","concentration",false,vConcentration),
					new Property<Boolean>("V","spellComponentV",false, vSpellComponentV),
					new Property<Boolean>("S","spellComponentS",false, vSpellComponentS),
					new Property<Boolean>("M","spellComponentM",false, vSpellComponentM),
					new Property<String>("Component Description", "spellComponentDescription", true,vSpellComponenDescription),
					new Property<Boolean>("Material Cost", "materialCost", true, vMaterialCost),
					new Property<SpellAttackType>("Attack","spellAttack", true, vSpellAttack),
					new Property<String>("Damage", "spellDamage", true, vSpellDamage),
					new Property<String>("Description","spellDescription",true, vSpellDescription)
				)
			);
		} 
		catch (ParseException e) 
		{
			e.printStackTrace();
		}
		return vResult;
	}
	
	public Spell
	(
		Property<String> pSpellName, 
		Property<SpellLevel> pSpellLevel,
		Property<SpellSchool> pSpellSchool,
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
	
	public Property<SpellLevel> mSpellLevel()
	{
		return this.aSpellLevel;
	}
	
	public Property<SpellSchool> mSpellSchool()
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
	
	public String mCSVHeaders()
	{
		String vResult = this.aSpellName.mName() +	", " +
			this.aSpellLevel.mName() + ", " +
			this.aSpellSchool.mName() + ", " +
			this.aAlwaysPrepared.mName() + ", ";
		for(SpellAttack vSpellAttack : this.aSpellAttacks)
		{
			vResult += vSpellAttack.mCSVHeaders();
		}
		return vResult;
	}
	
	public String mToCSV()
	{
		String vResult = this.aSpellName.mToCSV() +	", " +
			this.aSpellLevel.mToCSV() + ", " +
			this.aSpellSchool.mToCSV() + ", " +
			this.aAlwaysPrepared.mToCSV() + ", ";
		for(SpellAttack vSpellAttack : this.aSpellAttacks)
		{
			vResult += vSpellAttack.mToCSV();
		}
		return vResult;
	}
}