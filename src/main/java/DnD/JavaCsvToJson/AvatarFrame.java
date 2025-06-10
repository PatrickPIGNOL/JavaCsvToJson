package DnD.JavaCsvToJson;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

import org.json.simple.JSONObject;

public class AvatarFrame implements IExportable
{
	public static AvatarFrame mFromJson(JSONObject pJson)
	{
		AvatarFrame vFrame = null;
		String vAvatar = (String) pJson.getOrDefault(EMonsterHeader.Avatar.mJsonName(), null);
		if(vAvatar != null && vAvatar.equalsIgnoreCase("null"))
		{
			vAvatar = null;
		}
		String vToken = (String) pJson.getOrDefault(EMonsterHeader.Token.mJsonName(), null);
		if(vToken != null && vToken.equalsIgnoreCase("null"))
		{
			vToken = null;
		}
		vFrame = new AvatarFrame
		(
			new Property<String>(EMonsterHeader.Avatar, vAvatar),
			new Property<String>(EMonsterHeader.Token, vToken)
		);
		return vFrame;
	}
	
	private Property<String> aAvatar;
	private Property<String> aToken;
	
	AvatarFrame
	(
		Property<String> pAvatar,
		Property<String> pToken
	)
	{
		this.aAvatar = pAvatar;
		this.aToken = pToken;
	}
	
	public Property<String> mAvatar()
	{ 
		return this.aAvatar;
	}
	
	public Property<String> mToken()
	{ 
		return this.aToken;
	}

	@Override
	public String mToJSON() 
	{
		return "";
	}
	
	@Override
	public List<String> mCSVHeaders() 
	{
		List<String> vResult = new ArrayList<>();
		
		vResult.add(this.aAvatar.mName());
		vResult.add(this.aToken.mName());
		
 		return vResult;
	}

	@Override
	public List<String> mToCSV() 
	{
		List<String> vResult = new ArrayList<>();
		
		vResult.add(this.aAvatar.mToCSV());
		vResult.add(this.aToken.mToCSV());
		
		return vResult;
	}

	@Override
	public List<String> mToBook() 
	{
		List<String> vResult = new ArrayList<>();
		
		vResult.addAll(this.aAvatar.mToBook());
		vResult.addAll(this.aToken.mToBook());
		
		return vResult;
	}
}
