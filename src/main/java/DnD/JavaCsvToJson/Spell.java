package DnD.JavaCsvToJson;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Spell implements IExportable
{	
	public static Spell mFromURL(String vURL)
	{
		Spell vSpell = null;
		try 
		{
			Document vDocument = Jsoup.connect(vURL.trim()).get();
			String vSpellName = vDocument.selectXpath("//div[@class='col1']/h1").text();
			String vStringLevel = vDocument.selectXpath("//div[@class='col1']//div[@class='ecole']").text().split("-")[0].trim();
			ESpellLevel vSpellLevel = ESpellLevel.Cantrip;
			for(ESpellLevel vLevel : ESpellLevel.values())
			{
				if(vStringLevel.trim().equalsIgnoreCase(vLevel.mLevelFR().trim()))
				{
					vSpellLevel = vLevel;
					break;
				}
			}
			int vSpellRealLevel = vSpellLevel.mID() - 1;
			boolean vAlwaysPrepared = false;
			if(vSpellRealLevel == 0)
			{
				vAlwaysPrepared = true;
			}
			String vStringSchool = vDocument.selectXpath("//div[@class='col1']//div[@class='ecole']").text().split("-")[1].trim();
			ESpellSchool vSpellSchool = ESpellSchool.Abjuration;
			for(ESpellSchool vSchool : ESpellSchool.values())
			{
				if(vStringSchool.trim().equalsIgnoreCase(vSchool.mSchoolFr()))
				{
					vSpellSchool = vSchool;
					break;
				}
			}
			vSpell = new Spell
			(
				new Property<String>(ESpellHeader.Name, vSpellName), 
				new Property<ESpellLevel>(ESpellHeader.Level, vSpellLevel),
				new Property<Integer>(ESpellHeader.RealLevel, vSpellRealLevel),
				new Property<ESpellSchool>(ESpellHeader.School, vSpellSchool),
				new Property<Boolean>(ESpellHeader.AlwaysPrepared, vAlwaysPrepared)
			);
			String vTitle = vDocument.selectXpath("//div[@class='col1']//div[@class='t']/strong").text();
			String vTempsIncantation = vDocument.selectXpath("//div[@class='col1']//div[@class='t']").text().substring(vTitle.length()).replaceAll(":","").trim();
			
			vTitle = vDocument.selectXpath("//div[@class='col1']//div[@class='r']/strong").text();
			String vRange = vDocument.selectXpath("//div[@class='col1']//div[@class='r']").text().substring(vTitle.length()).replaceAll(":","").trim();
			
			vTitle = vDocument.selectXpath("//div[@class='col1']//div[@class='c']/strong").text().trim();
			boolean vComposantV = false;
			boolean vComposantS = false;
			boolean vComposantM = false;
			boolean vMaterialCost = false;
			String vComposantMDescription = null;
			vTitle = vDocument.selectXpath("//div[@class='col1']//div[@class='c']/strong").text();
			String[] vComposants = vDocument.selectXpath("//div[@class='col1']//div[@class='c']").text().substring(vTitle.length()).trim().replaceAll(":", "").trim().split(",");
			for(String vComposant : vComposants)
			{
				if(vComposant.trim().contains("V"))
				{
					vComposantV = true;
					continue;
				}
				if(vComposant.trim().contains("S"))
				{
					vComposantS = true;
					continue;
				}
				if(vComposant.trim().contains("M"))
				{
					vComposantM = true;
					if(vComposant.trim().contains("("))
					{
						vComposantMDescription = vComposant.trim().split("\\(")[1].replaceAll("\\)","").trim();
					}
					if(vComposantMDescription.contains("d'une valeur d'au moins"))
					{
						vMaterialCost = true;
					}
					continue;
				}
			}
			vTitle = vDocument.selectXpath("//div[@class='col1']//div[@class='d']/strong").text();
			String vAttackDuration = vDocument.selectXpath("//div[@class='col1']//div[@class='d']").text().substring(vTitle.length()).trim().replaceAll(":", "").trim();
			boolean vConcentration = false;
			boolean vRitual = false;
			vTitle = vDocument.selectXpath("//div[@class='col1']//div[@class='d']/strong").text();
			if(vAttackDuration.contains("concentration"))
			{
				vConcentration = true;
			}
			if(vAttackDuration.contains("rituel"))
			{
				vRitual = true;
			}
			String vAttackDescription = null;
			vAttackDescription = vDocument.selectXpath("//div[@class='col1']//p[@class='resume']").text();
			if(! vAttackDescription.isEmpty())
			{
				vAttackDescription += "\n";
			}
			vAttackDescription += vDocument.selectXpath("//div[@class='col1']//div[@class='description']").text();
			String vSpellDamage = null;
			Pattern vPattern = Pattern.compile("\\d+d\\d+(\\s*\\+\\s*\\d+)? dégâts (de [a-zA-Z]+|[a-zA-Z]{3,})");
			Matcher vMatcher = vPattern.matcher(vAttackDescription);
			while(vMatcher.find())
			{
				if(!vMatcher.group().isEmpty())
				{
					String vGroup = vMatcher.group();
					vSpellDamage = vGroup.replaceAll("dégâts", ",").replaceAll("de", "").replaceAll(" ", "").trim();
					break;
				}
			}
			String vSaveThrow = null;
			vPattern = Pattern.compile("jet de sauvegarde de [a-zA-Z]+");
			vMatcher = vPattern.matcher(vAttackDescription);
			while(vMatcher.find())
			{
				if(!vMatcher.group().isEmpty())
				{
					String vGroup = vMatcher.group();
					vSaveThrow = vGroup.substring(21);
					break;
				}
			}
			ESpellAttackType vSpellSaveThrow = null;
			for(ESpellAttackType vType : ESpellAttackType.values())
			{
				if(vSaveThrow.trim().equalsIgnoreCase(vType.mNameFR()))
				{
					vSpellSaveThrow = vType;
				}
			}
			boolean vLegendaire = vAttackDescription.contains("légendaire");
			vSpell.aSpellAttacks.mValue().add
			(
				new SpellAttack
				(
					new Property<String>(ESpellHeader.Time, vTempsIncantation),
					new Property<String>(ESpellHeader.Range, vRange),
					new Property<String>(ESpellHeader.Duration, vAttackDuration),
					new Property<Boolean>(ESpellHeader.Ritual, vRitual),
					new Property<Boolean>(ESpellHeader.Concentration, vConcentration),
					new Property<Boolean>(ESpellHeader.V, vComposantV),
					new Property<Boolean>(ESpellHeader.S, vComposantS),
					new Property<Boolean>(ESpellHeader.M, vComposantM),
					new Property<String>(ESpellHeader.ComponentDescription, vComposantMDescription),
					new Property<Boolean>(ESpellHeader.MaterialCost, vMaterialCost),
					new Property<ESpellAttackType>(ESpellHeader.SaveThrow, vSpellSaveThrow),
					new Property<String>(ESpellHeader.Damage, vSpellDamage),
					new Property<String>(ESpellHeader.Description, vAttackDescription),
					new Property<Boolean>(ESpellHeader.Legendary, vLegendaire)
				)
			);
		}
		catch(Exception e)
		{
			e.fillInStackTrace();
		}
		return vSpell;
	}
	
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
		
		Integer vSpellLevelValue = ((Number)vJSONObject.getOrDefault(ESpellHeader.RealLevel.mJsonName(), 1)).intValue();
		
		ESpellLevel vSpellLevel = ESpellLevel.Cantrip;
		for(ESpellLevel vSpellLvl : ESpellLevel.values())
		{
			if (vSpellLevelValue == vSpellLvl.mID())
			{
				vSpellLevel = vSpellLvl;
				break;
			}
		}
		Integer vSpellRealLevel = ((Number)vJSONObject.getOrDefault(ESpellHeader.Level.mJsonName(), 1)).intValue();
		
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
			new Property<Integer>(ESpellHeader.RealLevel, vSpellRealLevel),
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
		boolean vLegendary = (Boolean) vJSONObject.getOrDefault(ESpellHeader.Legendary, false);
		vResult.mSpellAttacks().mValue().add
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
				new Property<String>(ESpellHeader.Description, vSpellDescription),
				new Property<Boolean>(ESpellHeader.Legendary, vLegendary)
			)
		);
		return vResult;
	}

	private Property<String> aSpellName;	
	private Property<ESpellLevel> aSpellLevel;
	private Property<Integer> aSpellRealLevel;
	private Property<ESpellSchool> aSpellSchool;
	private Property<Boolean> aAlwaysPrepared;	
	private Property<List<SpellAttack>> aSpellAttacks;
	
	public Spell
	(
		Property<String> pSpellName, 
		Property<ESpellLevel> pSpellLevel,
		Property<Integer> pSpellRealLevel,
		Property<ESpellSchool> pSpellSchool,
		Property<Boolean> pAlwaysPrepared
	)
	{
		this.aSpellName = pSpellName;
		this.aSpellLevel = pSpellLevel;
		this.aSpellSchool = pSpellSchool;
		this.aSpellRealLevel = pSpellRealLevel;
		this.aAlwaysPrepared = pAlwaysPrepared;
		this.aSpellAttacks = new Property(ESpellHeader.Actions, new ArrayList<SpellAttack>());
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
	
	public Property<List<SpellAttack>> mSpellAttacks()
	{
		return this.aSpellAttacks;
	}	
	
	public String mToJSON()
	{
		String vResult = "\"{\n " + this.aSpellName.mToJSON() + 
			",\n " + this.aSpellLevel.mToJSON() + 
			",\n " + this.aSpellRealLevel.mToJSON() +
			",\n " + this.aSpellSchool.mToJSON() +
			",\n " + this.aAlwaysPrepared.mToJSON();
		for(SpellAttack vSpellAttack : this.aSpellAttacks.mValue())
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
		vResult.add(this.aSpellRealLevel.mName());
		vResult.add(this.aSpellSchool.mName());
		vResult.add(this.aAlwaysPrepared.mName());
		for(SpellAttack vSpellAttack : this.aSpellAttacks.mValue())
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
		vResult.add(this.aSpellRealLevel.mToCSV());
		vResult.add(this.aSpellSchool.mToCSV());
		vResult.add(this.aAlwaysPrepared.mToCSV());
		for(SpellAttack vSpellAttack : this.aSpellAttacks.mValue())
		{
			vResult.addAll(vSpellAttack.mToCSV());
		}
		return vResult;
	}

	@Override
	public List<String> mToBook() 
	{		
		List<String> vResult = new ArrayList<>();
		vResult.addAll(this.aSpellName.mToBook());
		vResult.addAll(this.aSpellLevel.mToBook());
		vResult.addAll(this.aSpellRealLevel.mToBook());
		vResult.addAll(this.aSpellSchool.mToBook());
		vResult.addAll(this.aAlwaysPrepared.mToBook());
		for(SpellAttack vSpellAttack : this.aSpellAttacks.mValue())
		{
			vResult.addAll(vSpellAttack.mToBook());
		}
		
		return vResult;
	}
}