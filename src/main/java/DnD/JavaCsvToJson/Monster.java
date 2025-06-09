package DnD.JavaCsvToJson; 

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Monster implements IExportable
{	
	public static Monster mFromJson(String[] vTable) throws ParseException
	{
		Monster vResult = null;
		
		JSONObject vJSONObject = (JSONObject) new JSONParser().parse(vTable[1]);
		
		String vMonsterName = vTable[0];

		String vMA = (String) vJSONObject.getOrDefault(EMonsterHeader.Alignment.mJsonName(), EAlignment.Unaligned.mID());		
		EAlignment vMonsterAlignment = EAlignment.Default;
		for(EAlignment vAlignment : EAlignment.values())
		{
			if(vMA.trim().equalsIgnoreCase(vAlignment.mID()))
			{
				vMonsterAlignment = vAlignment;
				break;
			}
		}
		
		Integer vArmorClass = ((Number)vJSONObject.getOrDefault(EMonsterHeader.ArmorClass.mJsonName(), 0)).intValue();
		Integer vArmorClassInput = ((Number)vJSONObject.getOrDefault(EMonsterHeader.ArmorClassInput.mJsonName(), 0)).intValue();
		Integer vAttackDamage = ((Number)vJSONObject.getOrDefault(EMonsterHeader.AttackDamage.mJsonName(), 0)).intValue();
		String vChalengeRating = (String) vJSONObject.getOrDefault(EMonsterHeader.ChalengeRating.mJsonName(), "");
		Integer vCharisma = ((Number)vJSONObject.getOrDefault(EMonsterHeader.Charisma.mJsonName(), 0)).intValue();		
		Integer vConstitution = ((Number)vJSONObject.getOrDefault(EMonsterHeader.Constitution.mJsonName(), 0)).intValue();
		Integer vDexterity = ((Number)vJSONObject.getOrDefault(EMonsterHeader.Dexterity.mJsonName(), 0)).intValue();
		Integer vHP = ((Number)vJSONObject.getOrDefault(EMonsterHeader.HP.mJsonName(), 0)).intValue();
		Integer vHPMax = ((Number)vJSONObject.getOrDefault(EMonsterHeader.HPMax.mJsonName(), 0)).intValue();
		String vHPMaxRoll = (String)vJSONObject.getOrDefault(EMonsterHeader.HPMaxRoll.mJsonName(), "");
		Integer vIntelligence = ((Number)vJSONObject.getOrDefault(EMonsterHeader.Intelligence.mJsonName(), 0)).intValue();
		
		JSONObject vJSONMonsterAvatar = (JSONObject) vJSONObject.getOrDefault(EMonsterHeader.MonsterAvatar.mJsonName(), null);
		MonsterAvatar vMonsterAvatar = null;
		if(vJSONMonsterAvatar != null)
		{
			vMonsterAvatar = MonsterAvatar.mFromJson(vJSONMonsterAvatar);
		}
		else
		{
			AvatarFrame vAvatarFrame = new AvatarFrame
			(
				new Property<String>(EMonsterHeader.Avatar, null),
				new Property<String>(EMonsterHeader.Token, null)
			);
			vMonsterAvatar = new MonsterAvatar
			(
				new Property<String>(EMonsterHeader.Avatar, null),
				new Property<String>(EMonsterHeader.Token, null),
				new Property<AvatarFrame>(EMonsterHeader.Frame, vAvatarFrame)
			);
		}
		
		String vSize = (String)vJSONObject.getOrDefault(EMonsterHeader.Size.mJsonName(), ESize.Default.mID());
		ESize vMonsterSize = ESize.Default;
		for(ESize vMS : ESize.values())
		{
			if(vSize.trim().equalsIgnoreCase(vMS.mID()))
			{
				vMonsterSize = vMS;
				break;
			}
		}
		String vMonsterSubType = (String)vJSONObject.getOrDefault(EMonsterHeader.MonsterSubtype.mJsonName(), "");
		String vMonsterType = (String)vJSONObject.getOrDefault(EMonsterHeader.MonsterType.mJsonName(), "");
		Integer vProeficiencyInput = ((Number)vJSONObject.getOrDefault(EMonsterHeader.ProeficiencyInput.mJsonName(), 0)).intValue();
		Integer vStrength = ((Number)vJSONObject.getOrDefault(EMonsterHeader.Strength.mJsonName(), 0)).intValue();
		Integer vWisdom = ((Number)vJSONObject.getOrDefault(EMonsterHeader.Wisdom.mJsonName(), 0)).intValue();
		
		vResult = new Monster 
		
		(
			new Property<String>(EMonsterHeader.Name, vMonsterName),				
			new Property<EAlignment>(EMonsterHeader.Alignment, vMonsterAlignment),
			new Property<Integer>(EMonsterHeader.ArmorClass, vArmorClass),
			new Property<Integer>(EMonsterHeader.ArmorClassInput, vArmorClassInput),
			new Property<Integer>(EMonsterHeader.Charisma, vCharisma),
			new Property<String>(EMonsterHeader.ChalengeRating, vChalengeRating),
			new Property<Integer>(EMonsterHeader.Constitution, vConstitution),
			new Property<Integer>(EMonsterHeader.Dexterity, vDexterity),
			new Property<Integer>(EMonsterHeader.HP, vHP),
			new Property<Integer>(EMonsterHeader.HPMax, vHPMax),
			new Property<String>(EMonsterHeader.HPMaxRoll, vHPMaxRoll),
			new Property<Integer>(EMonsterHeader.Intelligence, vIntelligence),
			new Property<MonsterAvatar>(EMonsterHeader.MonsterAvatar, vMonsterAvatar),
			new Property<ESize>(EMonsterHeader.Size, vMonsterSize),
			new Property<String>(EMonsterHeader.MonsterSubtype, vMonsterSubType),
			new Property<String>(EMonsterHeader.MonsterType, vMonsterType),
			new Property<Integer>(EMonsterHeader.ProeficiencyInput, vProeficiencyInput),
			new Property<Integer>(EMonsterHeader.Strength, vStrength),
			new Property<Integer>(EMonsterHeader.Wisdom, vWisdom),
			new Property<List<MonsterAction>>(EMonsterHeader.Actions, new ArrayList<>()),
			new Property<List<MonsterProperty>>(EMonsterHeader.Properties, new ArrayList<>()),
			new Property<List<SpecialAbility>>(EMonsterHeader.SpecialAbilities, new ArrayList<>())				
		);
		return vResult;
	}

	
	Property<EAlignment> aAlignment;
	Property<Integer> aArmorClass;
	Property<Integer> aArmorClassInput;
	Property<Integer> aCharisma;
	Property<String> aChalengeRating;
	Property<Integer> aConstitution;
	Property<Integer> aDexterity;
	Property<Integer> aHP;
	Property<Integer> aHPMax;
	Property<String> aHPMaxRoll;
	Property<Integer> aIntelligence;
	Property<MonsterAvatar> aMonsterAvatar;
	Property<ESize> aMonsterSize;
	Property<String> aMonsterSubtype;
	Property<String> aMonsterType;
	Property<String> aName;
	Property<Integer> aProeficiencyInput;
	Property<Integer> aStrength;
	Property<Integer> aWisdom;
	Property<List<MonsterAction>> aMonsterActions;
	Property<List<MonsterProperty>> aProperties;
	Property<List<SpecialAbility>> aSpecialAbilities;
	
	public Monster
	(
		Property<String> pName,
		Property<EAlignment> pAlignment,
		Property<Integer> pArmorClass,
		Property<Integer> pArmorClassInput,
		Property<Integer> pCharisma,
		Property<String> pChalengeRating,
		Property<Integer> pConstitution,
		Property<Integer> pDexterity,
		Property<Integer> pHP,
		Property<Integer> pHPMax,
		Property<String> pHPMaxRoll,
		Property<Integer> pIntelligence,
		Property<MonsterAvatar> pMonsterAvatar,
		Property<ESize> pMonsterSize,
		Property<String> pMonsterSubtype,
		Property<String> pMonsterType,
		Property<Integer> pProeficiencyInput,
		Property<Integer> pStrength,
		Property<Integer> pWisdom,
		Property<List<MonsterAction>> pMonsterActions,
		Property<List<MonsterProperty>> pProperties,              
		Property<List<SpecialAbility>> pSpecialAbilities           
	)
	{
		this.aName = pName;
		this.aAlignment = pAlignment;
		this.aArmorClass = pArmorClass;
		this.aArmorClassInput = pArmorClassInput;
		this.aChalengeRating = pChalengeRating;
		this.aCharisma = pCharisma;
		this.aConstitution = pConstitution;
		this.aDexterity = pDexterity;
		this.aHP = pHP;
		this.aHPMax = pHPMax;
		this.aHPMaxRoll = pHPMaxRoll;
		this.aIntelligence = pIntelligence;
		this.aMonsterAvatar = pMonsterAvatar;
		this.aMonsterSize = pMonsterSize;
		this.aMonsterSubtype = pMonsterSubtype;
		this.aMonsterType = pMonsterType;
		this.aProeficiencyInput = pProeficiencyInput;
		this.aStrength = pStrength;
		this.aWisdom = pWisdom;
		this.aMonsterActions = pMonsterActions;
		this.aProperties = pProperties;
		this.aSpecialAbilities = pSpecialAbilities;
	}
	
	public Property<String> mName()
	{
		return this.aName;
	}
	
	public Property<EAlignment> mAlignment()
	{
		return this.aAlignment;
	}
	
	public Property<Integer> mArmorClass()
	{
		return this.aArmorClass;
	}
	
	public Property<Integer> mArmorClassInput()
	{
		return this.aArmorClassInput;
	}
	
	public Property<String> mChalengeRating()
	{
		return this.aChalengeRating;
	}
	
	public Property<Integer> mCharisma()
	{
		return this.aCharisma;
	}
	
	public Property<Integer> mConstitution()
	{
		return this.aConstitution;
	}
	
	public Property<Integer> mDexterity()
	{
		return this.aDexterity;
	}
	
	public Property<Integer> mHP()
	{
		return this.aHP;
	}
	
	public Property<Integer> mHPMax()
	{
		return this.aHPMax;
	}
	
	public Property<String> mHPMaxRoll()
	{
		return this.aHPMaxRoll;
	}
	
	public Property<Integer> mIntelligence()
	{
		return this.aIntelligence;
	}
	
	public Property<MonsterAvatar> mMonsterAvatar()
	{
		return this.aMonsterAvatar;
	}
	
	public Property<ESize> mMonsterSize()
	{
		return this.aMonsterSize;
	}
	
	public Property<String> mMonsterSubtype()
	{
		return this.aMonsterSubtype;
	}
	
	public Property<String> mMonsterType()
	{
		return this.aMonsterType;
	}
	
	public Property<Integer> mProeficiencyInput()
	{
		return this.aProeficiencyInput;
	}
	
	public Property<Integer> mStrength()
	{
		return this.aStrength;
	}
	
	public Property<Integer> mWisdom()
	{
		return this.aWisdom;
	}
	
	public Property<List<MonsterAction>> mMonsterActions()
	{
		return this.aMonsterActions;
	}
	
	public Property<List<MonsterProperty>> mProperties()
	{
		return this.aProperties;
	}
	
	public Property<List<SpecialAbility>> mSpecialAbilities()
	{
		return this.aSpecialAbilities;
	}
	
	@Override	
	public String mToJSON()
	{
		String vResult = "";
		
		return vResult;
	}
		
	@Override
	public List<String> mCSVHeaders()
	{
		List<String> vResult = new ArrayList<>();
		vResult.add(this.aName.mName());
		vResult.add(this.aAlignment.mName()); 
		vResult.add(this.aArmorClass.mName());
		vResult.add(this.aArmorClassInput.mName());
		vResult.add(this.aChalengeRating.mName());
		vResult.add(this.aCharisma.mName());
		vResult.add(this.aConstitution.mName());
		vResult.add(this.aDexterity.mName());
		vResult.add(this.aHP.mName());
		vResult.add(this.aHPMax.mName());
		vResult.add(this.aHPMaxRoll.mName());
		vResult.add(this.aIntelligence.mName());
		vResult.add(this.aMonsterSize.mName());
		vResult.add(this.aMonsterSubtype.mName());
		vResult.add(this.aMonsterType.mName());
		vResult.add(this.aProeficiencyInput.mName());
		vResult.add(this.aStrength.mName());
		vResult.add(this.aWisdom.mName());
		vResult.add(this.aMonsterAvatar.mName());
		vResult.addAll(this.aMonsterAvatar.mValue().mCSVHeaders());
		vResult.add(this.aMonsterActions.mName());	
		for(MonsterAction vAction : this.aMonsterActions.mValue())
		{
			vResult.addAll(vAction.mCSVHeaders());
		}
		vResult.add(this.aProperties.mName());
		for(MonsterProperty vProperty : this.aProperties.mValue())
		{
			vResult.addAll(vProperty.mCSVHeaders());
		}
		vResult.add(this.aSpecialAbilities.mName());
		for(SpecialAbility vSpecialAbility : this.aSpecialAbilities.mValue())
		{
			vResult.addAll(vSpecialAbility.mCSVHeaders());
		}				
		return vResult;
	}
	
	@Override
	public List<String> mToCSV()
	{
		List<String> vResult = new ArrayList<>();
		vResult.add(this.aName.mToCSV());
		vResult.add(this.aAlignment.mToCSV()); 
		vResult.add(this.aArmorClass.mToCSV());
		vResult.add(this.aArmorClassInput.mToCSV());
		vResult.add(this.aChalengeRating.mToCSV());
		vResult.add(this.aCharisma.mToCSV());
		vResult.add(this.aConstitution.mToCSV());
		vResult.add(this.aDexterity.mToCSV());
		vResult.add(this.aHP.mToCSV());
		vResult.add(this.aHPMax.mToCSV());
		vResult.add(this.aHPMaxRoll.mToCSV());
		vResult.add(this.aIntelligence.mToCSV());
		vResult.add(this.aMonsterSize.mToCSV());
		vResult.add(this.aMonsterSubtype.mToCSV());
		vResult.add(this.aMonsterType.mToCSV());
		vResult.add(this.aProeficiencyInput.mToCSV());
		vResult.add(this.aStrength.mToCSV());
		vResult.add(this.aWisdom.mToCSV());
		vResult.add("");
		vResult.addAll(this.aMonsterAvatar.mValue().mToCSV());
		vResult.add("");	
		for(MonsterAction vAction : this.aMonsterActions.mValue())
		{
			vResult.addAll(vAction.mToCSV());
		}
		vResult.add("");
		for(MonsterProperty vProperty : this.aProperties.mValue())
		{
			vResult.addAll(vProperty.mToCSV());
		}
		vResult.add("");
		for(SpecialAbility vSpecialAbility : this.aSpecialAbilities.mValue())
		{
			vResult.addAll(vSpecialAbility.mToCSV());
		}				
		return vResult;
	}
}
