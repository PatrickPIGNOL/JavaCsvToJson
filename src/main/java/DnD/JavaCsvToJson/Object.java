package DnD.JavaCsvToJson;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Object implements IExportable 
{
	public static Object mFromURL(String vURL)
	{
		Object vResult = null;
		try 
		{
			Document vDocument = Jsoup.connect(vURL.trim()).get();
			
			String vObjectName = vDocument.selectXpath("//div[@class='col1']/h1").text();
			String vTemp = vDocument.selectXpath("//div[@class='col1']//div[@class='type']").text();
			String vObjectType = null;
			boolean vLink = false;
			Pattern vPattern = Pattern.compile("(Anneau|Arme|Armure|Baguette|Bâton|Objet merveilleux|Parchemin|Potion|Sceptre)");
			Matcher vMatcher = vPattern.matcher(vTemp);
			if(vMatcher.find())
			{
				vObjectType = vMatcher.group();
			}
			String vRarity = null;
			vPattern = Pattern.compile("(commun|variable|peu commun|peu commun \\(\\+1\\) rare \\(\\+2\\) ou très rare \\(\\+3\\)|rareté selon le type de statuette|rare|rare \\(\\+1\\) très rare \\(\\+2\\) ou légendaire \\(\\+3\\)|très rare|très rare ou légendaire|légendaire|artéfact)");
			vMatcher = vPattern.matcher(vTemp);
			if (vMatcher.find())
			{
				vRarity = vMatcher.group();
			}
			if(vTemp.contains("nécessite un lien"))
			{
				vLink = true;
			}
			
			String vDescription = vDocument.selectXpath("//div[@class='col1']//div[@class='resume']").text();
			if(!vDescription.isEmpty())
			{
				vDescription += "\n";
			}
			vDescription += vDocument.selectXpath("//div[@class='col1']//div[@class='description']").text();
			
			vResult = new Object
			(
				new Property<String>(EObjectHeader.Name, vObjectName),
				new Property<String>(EObjectHeader.Type, vObjectType),
				new Property<String>(EObjectHeader.Rarity, vRarity),
				new Property<Boolean>(EObjectHeader.Link, vLink),
				new Property<String>(EObjectHeader.Description, vDescription)
			);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return vResult;
	}
	
	Property<String> aName;
	Property<String> aType; 
	Property<String> aRarity;
	Property<Boolean> aLink;
	Property<String> aDescription;
	
	public Object
	(
		Property<String> pName,
		Property<String> pType, 
		Property<String> pRarity, 
		Property<Boolean> pLink, 
		Property<String> pDescription
	)
	{
		this.aName = pName;
		this.aType = pType;
		this.aRarity = pRarity;
		this.aLink = pLink;
		this.aDescription = pDescription;
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
	public String mToJSON() 
	{		
		return "";
	}

	@Override
	public List<String> mToBook()
	{
		List<String> vResult = new ArrayList<>();
		vResult.addAll(this.aName.mToBook());
		vResult.addAll(this.aType.mToBook());
		vResult.addAll(this.aRarity.mToBook());
		vResult.addAll(this.aLink.mToBook());
		vResult.addAll(this.aDescription.mToBook());		
		return vResult;
	}
	
}
