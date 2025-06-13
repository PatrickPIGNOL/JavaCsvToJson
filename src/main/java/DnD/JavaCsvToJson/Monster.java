package DnD.JavaCsvToJson; 

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Monster implements IExportable
{
	public static Monster mFromBook()
	{
		return null;		
	}
	
	public static Monster mFromURL(String vURL)
	{
		Monster vResult = null;
		try 
		{
			Document vDocument = Jsoup.connect(vURL.trim()).get();
			String vMonsterName = vDocument.selectXpath("//div[@class='jaune']/h1").text(); // name
		    String vStrType = vDocument.selectXpath("//div[@class='jaune']//div[@class='type']").text().trim();
			Pattern vPattern = Pattern.compile("de taille [a-zA-Z]{1,3}");
		    Matcher vMatcher = vPattern.matcher(vStrType);
		    String vMonsterType = null;
		    String vMonsterSize = null;
		    if(vMatcher.find())
		    {
		    	String vTemp = vMatcher.group();
		    	vMonsterType = vStrType.substring(0, vMatcher.start());
		    	vMonsterSize = vTemp.replaceAll("de taille", "").trim();
		    }
		    String vMonsterAlignment = "";
		    vPattern = Pattern.compile("((loyal|neutre|chaotique) ?(bon|neutre|mauvais)?)|tout alignement|sans alignement");
		    vMatcher = vPattern.matcher(vStrType);
		    if(vMatcher.find())
		    {
		    	vMonsterAlignment = vMatcher.group();
		    }
		    String vRed = vDocument.selectXpath("//div[@class='jaune']//div[@class='red']").text();
		    int vIndexCA = vRed.indexOf("Classe d'armure");
		    int vIndexPV = vRed.indexOf("Points de vie");
		    int vIndexSpeed = vRed.indexOf("Vitesse");
		    String vCharacteristics = vDocument.selectXpath("//div[@class='jaune']//div[@class='red']//div[@class='carac']").text();
		    int vIndexFORRed = vRed.indexOf("FOR");
		    int vIndexFOR = vCharacteristics.indexOf("FOR");
		    int vIndexDEX = vCharacteristics.indexOf("DEX");
		    int vIndexCON = vCharacteristics.indexOf("CON");
		    int vIndexINT = vCharacteristics.indexOf("INT");
		    int vIndexSAG = vCharacteristics.indexOf("SAG");
		    int vIndexCHA = vCharacteristics.indexOf("CHA");
		    
		    String vCA = vRed.substring(vIndexCA + 15, vIndexPV).trim().split(" ")[0].trim(); // CA
		    Integer vMonsterCA = Integer.valueOf(vCA);  
		    String vMonsterCADescription = vRed.substring(vIndexCA + 16 + vCA.length(), vIndexPV).trim(); // Size
		    
		    String vHP = vRed.substring(vIndexPV + 13 , vIndexSpeed).trim().split(" ")[0].trim();
			Integer vMonsterHP = Integer.valueOf(vHP); // HP
			Integer vMonsterHPMax = vMonsterHP;
			String vMonsterHPFormula = vRed.substring(vIndexPV + 14 + vHP.length(), vIndexSpeed).trim().replace('(', ' ').replace(')', ' ').trim();
			String vSpeed = vRed.substring(vIndexSpeed + 7, vIndexFORRed); // Speed
			List<MonsterProperty> vMonsterProperties = new ArrayList<>();
			for(String vSpd : vSpeed.split(","))
			{
				if(vSpd.contains("nage"))
				{
					vMonsterProperties.add
					(
						new MonsterProperty
						(
							new Property<String>(EMonsterHeader.Property, "speed"),
							new Property<String>(EMonsterHeader.PropertyName, "nage"),
							new Property<String>(EMonsterHeader.PropertyValue, vSpd.trim().substring(4).trim())
						)
					);
				}
				else if (vSpd.contains("vol"))
				{
					vMonsterProperties.add
					(
						new MonsterProperty
						(
							new Property<String>(EMonsterHeader.Property, "speed"),
							new Property<String>(EMonsterHeader.PropertyName, "vol"),
							new Property<String>(EMonsterHeader.PropertyValue, vSpd.trim().substring(3).trim())
						)
					);
				}
				else if (vSpd.contains("escalade"))
				{
					vMonsterProperties.add
					(
						new MonsterProperty
						(
							new Property<String>(EMonsterHeader.Property, "speed"),
							new Property<String>(EMonsterHeader.PropertyName, "escalade"),
							new Property<String>(EMonsterHeader.PropertyValue, vSpd.trim().substring(8).trim())
						)
					);
				}
				else
				{
					vMonsterProperties.add
					(
							new MonsterProperty
							(
								new Property<String>(EMonsterHeader.Property, "speed"),
								new Property<String>(EMonsterHeader.PropertyName, "marche"),
								new Property<String>(EMonsterHeader.PropertyValue, vSpd.trim())
							)
						);
				}
			}
			
			
			
			
			Integer vMonsterStrength = Integer.valueOf(vCharacteristics.substring(vIndexFOR + 3, vIndexDEX).trim().split(" ")[0]);
			Integer vMonsterDexterity = Integer.valueOf(vCharacteristics.substring(vIndexDEX + 3, vIndexCON).trim().split(" ")[0]); // Dexterity
			Integer vMonsterConstitution = Integer.valueOf(vCharacteristics.substring(vIndexCON + 3, vIndexINT).trim().split(" ")[0]); // Constitution
			Integer vMonsterInteligence = Integer.valueOf(vCharacteristics.substring(vIndexINT + 3, vIndexSAG).trim().split(" ")[0]); // Intelligence
			Integer vMonsterWisdom = Integer.valueOf(vCharacteristics.substring(vIndexSAG + 3, vIndexCHA).trim().split(" ")[0]); // Sagesse
			Integer vMonsterCharisma = Integer.valueOf(vCharacteristics.substring(vIndexCHA + 3).trim().split(" ")[0]); // Charisma
			
			String vChalengeRating = null;
			String vChalenge = null;
			Integer vXP = 0;
			
			List<PropertyIndex> vPropertiesIndex = new ArrayList<>();
						
			for(Element vElement : vDocument.selectXpath("//div[@class='jaune']//div[@class='red']/strong"))
			{
				if
				(
					!(
						vElement.text().trim().equalsIgnoreCase("Classe d'armure") 
						|| 
						vElement.text().trim().equalsIgnoreCase("Points de vie") 
						||
						vElement.text().trim().equalsIgnoreCase("Vitesse")
					)
				)
				{
					vPropertiesIndex.add(new PropertyIndex(vElement.text().trim(), vRed.indexOf(vElement.text().trim())));
				}
			}
			
			for(int vIndex = 0; vIndex < vPropertiesIndex.size(); vIndex++)
			{
				if(vIndex < vPropertiesIndex.size() - 1)
				{
					PropertyIndex vPropertyIndex = vPropertiesIndex.get(vIndex);
					PropertyIndex vNextIndex = vPropertiesIndex.get(vIndex + 1);
					if (vPropertyIndex.mProperty().trim().equalsIgnoreCase("Jet de sauvegarde"))
					{
						String vJDS = vRed.substring(vPropertyIndex.mIndex() + vPropertyIndex.mProperty().length(), vNextIndex.mIndex()).trim().split(",")[0];
						for(String vSJDS : vJDS.split(","))
						{
							if(vSJDS.contains("For"))
							{
								vMonsterProperties.add
								(
									new MonsterProperty
									(
										new Property<String>(EMonsterHeader.Property, "Jet de sauvegarde"),
										new Property<String>(EMonsterHeader.PropertyName, "Force"),
										new Property<String>(EMonsterHeader.PropertyValue, vSJDS.trim().substring(3).trim())
									)
								);
								continue;
							}
							if (vSJDS.contains("Dex"))
							{
								vMonsterProperties.add
								(
									new MonsterProperty
									(
										new Property<String>(EMonsterHeader.Property, "Jet de sauvegarde"),
										new Property<String>(EMonsterHeader.PropertyName, "Dexterity"),
										new Property<String>(EMonsterHeader.PropertyValue, vSJDS.trim().substring(3).trim())
									)
								);
								continue;
							}
							if (vSJDS.contains("Con"))
							{
								vMonsterProperties.add
								(
									new MonsterProperty
									(
										new Property<String>(EMonsterHeader.Property, "Jet de sauvegarde"),
										new Property<String>(EMonsterHeader.PropertyName, "Constitution"),
										new Property<String>(EMonsterHeader.PropertyValue, vSJDS.trim().substring(3).trim())
									)
								);
								continue;
							}
							if (vSJDS.contains("Int"))
							{
								vMonsterProperties.add
								(
									new MonsterProperty
									(
										new Property<String>(EMonsterHeader.Property, "Jet de sauvegarde"),
										new Property<String>(EMonsterHeader.PropertyName, "Intelligence"),
										new Property<String>(EMonsterHeader.PropertyValue, vSJDS.trim().substring(3).trim())
									)
								);
								continue;
							}
							if (vSJDS.contains("Sag"))
							{
								vMonsterProperties.add
								(
									new MonsterProperty
									(
										new Property<String>(EMonsterHeader.Property, "Jet de sauvegarde"),
										new Property<String>(EMonsterHeader.PropertyName, "Sagesse"),
										new Property<String>(EMonsterHeader.PropertyValue, vSJDS.trim().substring(3).trim())
									)
								);
								continue;
							}
							if (vSJDS.contains("Cha"))
							{
								vMonsterProperties.add
								(
									new MonsterProperty
									(
										new Property<String>(EMonsterHeader.Property, "Jet de sauvegarde"),
										new Property<String>(EMonsterHeader.PropertyName, "Charisme"),
										new Property<String>(EMonsterHeader.PropertyValue, vSJDS.trim().substring(3).trim())
									)
								);
								continue;
							}
						}
					}
					if (vPropertyIndex.mProperty().trim().equalsIgnoreCase("Compétences"))
					{
						String vCMPTS = vRed.substring(vPropertyIndex.mIndex() + vPropertyIndex.mProperty().length(), vNextIndex.mIndex()).trim();
						for(String vSkll : vCMPTS.split(","))
						{
							String vSkill = "Acrobaties";
							if(vSkll.contains(vSkill))
							{
								vMonsterProperties.add
								(
									new MonsterProperty
									(
										new Property<String>(EMonsterHeader.Property, "skills"),
										new Property<String>(EMonsterHeader.PropertyName, vSkill),
										new Property<String>(EMonsterHeader.PropertyValue, vSkll.replace('+', ' ').replace('-', ' ').trim().substring(vSkill.length()).trim())
									)
								);
								continue;
							}
							vSkill = "Arcanes";
							if(vSkll.contains(vSkill))
							{
								vMonsterProperties.add
								(
									new MonsterProperty
									(
										new Property<String>(EMonsterHeader.Property, "skills"),
										new Property<String>(EMonsterHeader.PropertyName, vSkill),
										new Property<String>(EMonsterHeader.PropertyValue, vSkll.replace('+', ' ').replace('-', ' ').trim().substring(vSkill.length()).trim())
									)
								);
								continue;
							}
							vSkill = "Athletisme";
							if(vSkll.contains(vSkill))
							{
								vMonsterProperties.add
								(
									new MonsterProperty
									(
										new Property<String>(EMonsterHeader.Property, "skills"),
										new Property<String>(EMonsterHeader.PropertyName, vSkill),
										new Property<String>(EMonsterHeader.PropertyValue, vSkll.replace('+', ' ').replace('-', ' ').trim().substring(vSkill.length()).trim())
									)
								);
								continue;
							}
							vSkill = "Discrétion";
							if(vSkll.contains(vSkill))
							{
								vMonsterProperties.add
								(
									new MonsterProperty
									(
										new Property<String>(EMonsterHeader.Property, "skills"),
										new Property<String>(EMonsterHeader.PropertyName, vSkill),
										new Property<String>(EMonsterHeader.PropertyValue, vSkll.replace('+', ' ').replace('-', ' ').trim().substring(vSkill.length()).trim())
									)
								);
								continue;
							}
							vSkill = "Dressage";
							if(vSkll.contains(vSkill))
							{
								vMonsterProperties.add
								(
									new MonsterProperty
									(
										new Property<String>(EMonsterHeader.Property, "skills"),
										new Property<String>(EMonsterHeader.PropertyName, vSkill),
										new Property<String>(EMonsterHeader.PropertyValue, vSkll.replace('+', ' ').replace('-', ' ').trim().substring(vSkill.length()).trim())
									)
								);
								continue;
							}
							vSkill = "Escamotage";
							if(vSkll.contains(vSkill))
							{
								vMonsterProperties.add
								(
									new MonsterProperty
									(
										new Property<String>(EMonsterHeader.Property, "skills"),
										new Property<String>(EMonsterHeader.PropertyName, vSkill),
										new Property<String>(EMonsterHeader.PropertyValue, vSkll.replace('+', ' ').replace('-', ' ').trim().substring(vSkill.length()).trim())
									)
								);
								continue;
							}
							vSkill = "Histoire";
							if(vSkll.contains(vSkill))
							{
								vMonsterProperties.add
								(
									new MonsterProperty
									(
										new Property<String>(EMonsterHeader.Property, "skills"),
										new Property<String>(EMonsterHeader.PropertyName, vSkill),
										new Property<String>(EMonsterHeader.PropertyValue, vSkll.replace('+', ' ').replace('-', ' ').trim().substring(vSkill.length()).trim())
									)
								);
								continue;
							}
							vSkill = "Intimidation";
							if(vSkll.contains(vSkill))
							{
								vMonsterProperties.add
								(
									new MonsterProperty
									(
										new Property<String>(EMonsterHeader.Property, "skills"),
										new Property<String>(EMonsterHeader.PropertyName, vSkill),
										new Property<String>(EMonsterHeader.PropertyValue, vSkll.replace('+', ' ').replace('-', ' ').trim().substring(vSkill.length()).trim())
									)
								);
								continue;
							}
							vSkill = "Intuition";
							if(vSkll.contains(vSkill))
							{
								vMonsterProperties.add
								(
									new MonsterProperty
									(
										new Property<String>(EMonsterHeader.Property, "skills"),
										new Property<String>(EMonsterHeader.PropertyName, vSkill),
										new Property<String>(EMonsterHeader.PropertyValue, vSkll.replace('+', ' ').replace('-', ' ').trim().substring(vSkill.length()).trim())
									)
								);
								continue;
							}
							vSkill = "Investigation";
							if(vSkll.contains(vSkill))
							{
								vMonsterProperties.add
								(
									new MonsterProperty
									(
										new Property<String>(EMonsterHeader.Property, "skills"),
										new Property<String>(EMonsterHeader.PropertyName, vSkill),
										new Property<String>(EMonsterHeader.PropertyValue, vSkll.replace('+', ' ').replace('-', ' ').trim().substring(vSkill.length()).trim())
									)
								);
								continue;
							}
							vSkill = "Médecine";
							if(vSkll.contains(vSkill))
							{
								vMonsterProperties.add
								(
									new MonsterProperty
									(
										new Property<String>(EMonsterHeader.Property, "skills"),
										new Property<String>(EMonsterHeader.PropertyName, vSkill),
										new Property<String>(EMonsterHeader.PropertyValue, vSkll.replace('+', ' ').replace('-', ' ').trim().substring(vSkill.length()).trim())
									)
								);
								continue;
							}
							vSkill = "Nature";
							if(vSkll.contains(vSkill))
							{
								vMonsterProperties.add
								(
									new MonsterProperty
									(
										new Property<String>(EMonsterHeader.Property, "skills"),
										new Property<String>(EMonsterHeader.PropertyName, vSkill),
										new Property<String>(EMonsterHeader.PropertyValue, vSkll.replace('+', ' ').replace('-', ' ').trim().substring(vSkill.length()).trim())
									)
								);
								continue;
							}
							vSkill = "Perception";
							if (vSkll.contains(vSkill))
							{
								vMonsterProperties.add
								(
									new MonsterProperty
									(
										new Property<String>(EMonsterHeader.Property, "skills"),
										new Property<String>(EMonsterHeader.PropertyName, vSkill),
										new Property<String>(EMonsterHeader.PropertyValue, vSkll.replace('+', ' ').replace('-', ' ').trim().substring(vSkill.length()).trim())
									)
								);
								continue;
							}			
							vSkill = "Persuasion";
							if(vSkll.contains(vSkill))
							{
								vMonsterProperties.add
								(
									new MonsterProperty
									(
										new Property<String>(EMonsterHeader.Property, "skills"),
										new Property<String>(EMonsterHeader.PropertyName, vSkill),
										new Property<String>(EMonsterHeader.PropertyValue, vSkll.replace('+', ' ').replace('-', ' ').trim().substring(vSkill.length()).trim())
									)
								);
								continue;
							}		
							vSkill = "Religion";
							if(vSkll.contains(vSkill))
							{
								vMonsterProperties.add
								(
									new MonsterProperty
									(
										new Property<String>(EMonsterHeader.Property, "skills"),
										new Property<String>(EMonsterHeader.PropertyName, vSkill),
										new Property<String>(EMonsterHeader.PropertyValue, vSkll.replace('+', ' ').replace('-', ' ').trim().substring(vSkill.length()).trim())
									)
								);
								continue;
							}	
							vSkill = "Representation";
							if(vSkll.contains(vSkill))
							{
								vMonsterProperties.add
								(
									new MonsterProperty
									(
										new Property<String>(EMonsterHeader.Property, "skills"),
										new Property<String>(EMonsterHeader.PropertyName, vSkill),
										new Property<String>(EMonsterHeader.PropertyValue, vSkll.replace('+', ' ').replace('-', ' ').trim().substring(vSkill.length()).trim())
									)
								);
								continue;
							}	
							vSkill = "Survie";
							if(vSkll.contains(vSkill))
							{
								vMonsterProperties.add
								(
									new MonsterProperty
									(
										new Property<String>(EMonsterHeader.Property, "skills"),
										new Property<String>(EMonsterHeader.PropertyName, vSkill),
										new Property<String>(EMonsterHeader.PropertyValue, vSkll.replace('+', ' ').replace('-', ' ').trim().substring(vSkill.length()).trim())
									)
								);
								continue;
							}	
							vSkill = "Tromperie";
							if(vSkll.contains(vSkill))
							{
								vMonsterProperties.add
								(
									new MonsterProperty
									(
										new Property<String>(EMonsterHeader.Property, "skills"),
										new Property<String>(EMonsterHeader.PropertyName, vSkill),
										new Property<String>(EMonsterHeader.PropertyValue, vSkll.replace('+', ' ').replace('-', ' ').trim().substring(vSkill.length()).trim())
									)
								);
								continue;
							}
						}
					}
					if (vPropertyIndex.mProperty().trim().equalsIgnoreCase("Sens"))
					{
						String vSenses = vRed.substring(vPropertyIndex.mIndex() + vPropertyIndex.mProperty().length(), vNextIndex.mIndex()).trim();
						for(String vSens : vSenses.split(","))
						{
							vPattern = Pattern.compile("[0-9]*");
							vMatcher = vPattern.matcher(vSens);														
							if(vMatcher.find())
							{
								String vTemp = vMatcher.group();
								int vIndexMatch = vMatcher.start();
								vMonsterProperties.add
								(
									new MonsterProperty
									(
										new Property<String>(EMonsterHeader.Property, "skills"),
										new Property<String>(EMonsterHeader.PropertyName, vSens.substring(0, vIndexMatch).trim()),
										new Property<String>(EMonsterHeader.PropertyValue, vTemp.trim())
									)
								);
							}
							else								
							{
								vMonsterProperties.add
								(
									new MonsterProperty
									(
										new Property<String>(EMonsterHeader.Property, "skills"),
										new Property<String>(EMonsterHeader.PropertyName, vSens),
										new Property<String>(EMonsterHeader.PropertyValue, "")
									)
								);
							}
						}
					}
					if (vPropertyIndex.mProperty().trim().equalsIgnoreCase("Langues"))
					{
						String vLangues = vRed.substring(vPropertyIndex.mIndex() + vPropertyIndex.mProperty().length(), vNextIndex.mIndex());
						for (String vLangue : vLangues.split(","))
						{
							vPattern = Pattern.compile("[0-9]*");
							vMatcher = vPattern.matcher(vLangue);							
							if(vMatcher.find())
							{
								String vTemp = vMatcher.group();
								int vIndexMatch = vMatcher.start();
								vMonsterProperties.add
								(
									new MonsterProperty
									(
										new Property<String>(EMonsterHeader.Property, "language"),
										new Property<String>(EMonsterHeader.PropertyName, vLangue.substring(0, vMatcher.start())),
										new Property<String>(EMonsterHeader.PropertyValue, vLangue.substring(vMatcher.start()))
									)
								);
							}							
							else
							{
								vMonsterProperties.add
								(
									new MonsterProperty
									(
										new Property<String>(EMonsterHeader.Property, "language"),
										new Property<String>(EMonsterHeader.PropertyName, vLangue),
										new Property<String>(EMonsterHeader.PropertyValue, null)
									)
								);
							}
						}
					}
				}
				else
				{
					PropertyIndex vPropertyIndex = vPropertiesIndex.get(vIndex);
					if (vPropertyIndex.mProperty().trim().equalsIgnoreCase("Puissance"))
					{
						vChalengeRating = vRed.substring(vPropertyIndex.mIndex() + vPropertyIndex.mProperty().length()).trim();
						vChalenge = vChalengeRating.split("\\(")[0].trim();
						vXP = Integer.valueOf(vChalengeRating.split("\\(")[1].split(" ")[0].split(" ")[0].replace(',', ' ').replaceAll("[\\r\\n\\t\\f ]", ""));						
					}
				}
			}	
			
			String vText = vDocument.selectXpath("//div[@class='jaune']").text();
			int vActionsIndex = vText.indexOf("Actions");
			
			List<MonsterAction> vMonsterActions = new ArrayList<>();
			
			for(Element vElement : vDocument.selectXpath("//div[@class='jaune']/div/p"))
			{
				String vTitre = vElement.getElementsByTag("strong").text();
				String vDescription = vElement.text().substring(vTitre.length() + 1).trim();
				if(vText.indexOf(vTitre) < vActionsIndex)
				{
					vMonsterProperties.add
					(
						new MonsterProperty
						(
							new Property<String>(EMonsterHeader.Property, "special_ability"),
							new Property<String>(EMonsterHeader.PropertyName, vTitre),
							new Property<String>(EMonsterHeader.PropertyValue, vDescription)
						)
					);
				}
				else
				{
					Integer vHit = null;
					vPattern = Pattern.compile("\\+\\d+ au toucher");
					vMatcher = vPattern.matcher(vDescription);
					if(vMatcher.find())
					{
						String vToHit = vMatcher.group();
						vHit = Integer.valueOf(vToHit.replace("+", "").replace("au toucher", "").trim());
					}
										
					String vDamage = null;
					String vType = null;
					
					
					vPattern = Pattern.compile("\\. Touché : \\d+ \\(\\d+d\\d+(\\s+\\+\\s+\\d+)?\\) dégâts [a-zA-Z]+");
					vMatcher = vPattern.matcher(vDescription);
					
					if(vMatcher.find())
					{
						String vTemp = vMatcher.group();
						int vIndexDegats = vTemp.indexOf("dégâts");
						
						vType = vTemp.substring(vIndexDegats + 6).trim();
						int vStart = vTemp.indexOf("(");
						int vEnd = vTemp.indexOf(")");
						vDamage = vTemp.substring(vStart, vEnd).replaceAll(" ", "") + "," + vType;
					}
					
					String vSavingThrow = null;
					
					vPattern = Pattern.compile("jet de sauvegarde de [a-zA-Z]+");
					vMatcher = vPattern.matcher(vDescription);
					if(vMatcher.find())
					{
						String vTemp = vMatcher.group();
						vSavingThrow = vTemp.substring(21);
					}

					vMonsterActions.add
					(
						new MonsterAction
						(
							new Property<String>(EMonsterHeader.AttackName, vTitre),
							new Property<String>(EMonsterHeader.AttackDescription, vDescription),
							new Property<Integer>(EMonsterHeader.AttackHit, vHit),
							new Property<String>(EMonsterHeader.AttackDamage, vDamage),
							new Property<String>(EMonsterHeader.AttackType, vType),
							new Property<String>(EMonsterHeader.AttackSaveThrow, vSavingThrow)
						)
					);
				}				
			}
			
			
			EAlignment vAlignmentEnum = EAlignment.Default;
			for(EAlignment vAlignment : EAlignment.values())
			{
				if(vMonsterAlignment.trim().equalsIgnoreCase(vAlignment.mAlignmentFR()))
				{
					vAlignmentEnum = vAlignment;
					break;	
				}
			}
			ESize vSizeEnum = null;
			for(ESize vSize : ESize.values())
			{
				if(vMonsterSize.trim().equalsIgnoreCase(vSize.mAideDD()))
				{
					vSizeEnum = vSize;
					break;
				}
			}
			vResult = new Monster					
			(
				new Property<String>(EMonsterHeader.Name, vMonsterName),
				new Property<EAlignment>(EMonsterHeader.Alignment, vAlignmentEnum),
				new Property<Integer>(EMonsterHeader.ArmorClass, vMonsterCA),
				new Property<Integer>(EMonsterHeader.ArmorClassInput, vMonsterCA),
				new Property<Integer>(EMonsterHeader.Charisma, vMonsterCharisma),
				new Property<String>(EMonsterHeader.ChalengeRating, vChalengeRating),            
				new Property<Integer>(EMonsterHeader.Constitution, vMonsterConstitution),             
				new Property<Integer>(EMonsterHeader.Dexterity, vMonsterDexterity),                
				new Property<Integer>(EMonsterHeader.HP, vMonsterHP),                       
				new Property<Integer>(EMonsterHeader.HPMax, vMonsterHPMax),                    
				new Property<String>(EMonsterHeader.HPMaxRoll, vMonsterHPFormula),                 
				new Property<Integer>(EMonsterHeader.Intelligence, vMonsterInteligence),             
				new Property<MonsterAvatar>(EMonsterHeader.MonsterAvatar, null),      
				new Property<ESize>(EMonsterHeader.Size, vSizeEnum),
				new Property<String>(EMonsterHeader.MonsterSubtype, null),            
				new Property<String>(EMonsterHeader.MonsterType, vMonsterType),               
				new Property<Integer>(EMonsterHeader.ProeficiencyInput, null),        
				new Property<Integer>(EMonsterHeader.Strength, vMonsterStrength),                 
				new Property<Integer>(EMonsterHeader.Wisdom, vMonsterWisdom),                   
				new Property<List<MonsterProperty>>(EMonsterHeader.Properties, vMonsterProperties), 
				new Property<List<MonsterAction>>(EMonsterHeader.Actions, vMonsterActions)
			);	
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}		
		return vResult;
	}
	
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
			new Property<List<MonsterProperty>>(EMonsterHeader.Properties, new ArrayList<>()),             
			new Property<List<MonsterAction>>(EMonsterHeader.Actions, new ArrayList<>())                 
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
		Property<List<MonsterProperty>> pProperties,  
		Property<List<MonsterAction>> pMonsterActions
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
		return vResult;
	}

	@Override
	public List<String> mToBook() 
	{
		List<String> vResult = new ArrayList<>();
		vResult.addAll(this.aName.mToBook());
		vResult.addAll(this.aAlignment.mToBook()); 
		vResult.addAll(this.aArmorClass.mToBook());
		vResult.addAll(this.aArmorClassInput.mToBook());
		vResult.addAll(this.aChalengeRating.mToBook());
		vResult.addAll(this.aCharisma.mToBook());
		vResult.addAll(this.aConstitution.mToBook());
		vResult.addAll(this.aDexterity.mToBook());
		vResult.addAll(this.aHP.mToBook());
		vResult.addAll(this.aHPMax.mToBook());
		vResult.addAll(this.aHPMaxRoll.mToBook());
		vResult.addAll(this.aIntelligence.mToBook());
		vResult.addAll(this.aMonsterSize.mToBook());
		vResult.addAll(this.aMonsterSubtype.mToBook());
		vResult.addAll(this.aMonsterType.mToBook());
		vResult.addAll(this.aProeficiencyInput.mToBook());
		vResult.addAll(this.aStrength.mToBook());
		vResult.addAll(this.aWisdom.mToBook());
		//vResult.addAll(this.aMonsterAvatar.mValue().mToBook());
		this.aProperties.mToBook();
		for(MonsterProperty vProperty : this.aProperties.mValue())
		{
			vResult.addAll(vProperty.mToBook());
		}
		this.aMonsterActions.mToBook();
		for(MonsterAction vAction : this.aMonsterActions.mValue())
		{
			vResult.addAll(vAction.mToBook());
		}
		return vResult;
	}
}
