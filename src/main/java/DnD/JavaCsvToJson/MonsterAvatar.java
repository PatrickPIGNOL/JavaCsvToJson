package DnD.JavaCsvToJson;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;

public class MonsterAvatar implements IExportable
{
	public static MonsterAvatar mFromJson(JSONObject vJson)
	{
		MonsterAvatar vResult = null;
		String vAvatar = (String) vJson.getOrDefault(EMonsterHeader.Avatar.mJsonName(), null);
		if(vAvatar != null && vAvatar.equalsIgnoreCase("null"))
		{
			vAvatar = null;
		}
		String vToken = (String) vJson.getOrDefault(EMonsterHeader.Token.mJsonName(), null);
		if(vToken != null && vToken.equalsIgnoreCase("null"))
		{
			vToken = null;
		}
		JSONObject vJsonFrame = (JSONObject) vJson.getOrDefault(EMonsterHeader.Frame.mJsonName(), null);
		AvatarFrame vAvatarFrame;
		if(vJsonFrame != null)
		{
			vAvatarFrame = AvatarFrame.mFromJson(vJsonFrame);
		}
		else
		{
			vAvatarFrame = new AvatarFrame
			(
				new Property<String>(EMonsterHeader.Avatar, null),
				new Property<String>(EMonsterHeader.Token, null)
			);
		}
		vResult = new MonsterAvatar
		(
			new Property<String>(EMonsterHeader.Avatar, vAvatar),
			new Property<String>(EMonsterHeader.Token, vToken),
			new Property<AvatarFrame>(EMonsterHeader.Frame, vAvatarFrame)
		);
		return vResult;
	}
	
	private Property<String> aAvatar;
	private Property<String> aToken;
	private Property<AvatarFrame> aFrame;
	
	MonsterAvatar
	(
		Property<String> pAvatar,
		Property<String> pToken,
		Property<AvatarFrame> pFrame
	)
	{
		this.aAvatar = pAvatar;
		this.aToken = pToken;
		this.aFrame = pFrame;
	}
	
	public Property<String> mAvatar()
	{
		return this.aAvatar	;
	}
	
	public Property<String> mToken()
	{
		return this.aToken;
	}	
	
	public Property<AvatarFrame> mFrame()
	{
		return this.aFrame;
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
		vResult.add(this.aFrame.mName());
		vResult.addAll(this.aFrame.mValue().mCSVHeaders());
		return vResult;
	}
	
	@Override
	public List<String> mToCSV()
	{
		List<String> vResult = new ArrayList<>();
		vResult.add(this.aAvatar.mToCSV());
		vResult.add(this.aToken.mToCSV());
		vResult.add("");
		vResult.addAll(this.aFrame.mValue().mToCSV());
		return vResult;
	}

	@Override
	public List<String> mToBook()
	{
		List<String> vResult = new ArrayList<>();
		vResult.add(this.aAvatar.mNameJSON());
		vResult.add(this.aAvatar.mToCSV());
		vResult.add(this.aToken.mNameJSON());
		vResult.add(this.aToken.mToCSV());
		vResult.add("");
		vResult.add(this.aFrame.mNameJSON());
		vResult.addAll(this.aFrame.mValue().mToBook());
		return vResult;
	}
}
