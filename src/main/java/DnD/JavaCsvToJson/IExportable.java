package DnD.JavaCsvToJson;

import java.util.List;

import org.json.simple.JSONObject;

public interface IExportable 
{
	public String mToJSON();
	public List<String> mCSVHeaders();
	public List<String> mToCSV();
	public List<String> mToBook();
}
