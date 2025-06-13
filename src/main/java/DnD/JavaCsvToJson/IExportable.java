package DnD.JavaCsvToJson;

import java.util.List;

public interface IExportable 
{
	public String mToJSON();
	public List<String> mCSVHeaders();
	public List<String> mToCSV();
	public List<String> mToBook();
}
