package DnD.JavaCsvToJson;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVParser;
import com.opencsv.ICSVWriter;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class JavaCsvToJson extends Application 
{
	private Stage aWindow; 
    private Label aLinkLabel = new Label("Lien : ");
    private TextArea aLinkText = new TextArea();
    private Button aConvertURLButton = new Button("URL => Book");
    
	@Override
	public void start(Stage pWindow) throws Exception 
	{
		this.aWindow = pWindow;
		
		this.aConvertURLButton.setOnAction
		(
			event -> 
			{
				List<Monster> vMonsters = new ArrayList<>();
				List<Spell> vSpells = new ArrayList<>();				
				
				for(String vStringURL : this.aLinkText.getText().split("\n"))
				{
					if(vStringURL.trim().contains("https://www.aidedd.org/dnd/monstres.php"))
					{
						vMonsters.add(Monster.mFromURL(vStringURL));
					}
					else
					{
						System.out.println("L'URL : \"" + vStringURL + "\" à été ignorée parce qu'elle ne fait pas partie du site \"aidedd.org/\".");
					}
				}
				FileChooser vFileChooser = new FileChooser();
				vFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers Book", "*.csv"));
				File vSaveFile = vFileChooser.showSaveDialog(this.aWindow);
				if(vSaveFile != null)
				{
					try(Writer vWriter = new FileWriter(vSaveFile))
					{
						try(ICSVWriter vCSVWriter = (ICSVWriter) new CSVWriterBuilder(vWriter)
								.withEscapeChar(ICSVParser.DEFAULT_ESCAPE_CHARACTER)
								.withLineEnd(ICSVWriter.DEFAULT_LINE_END)
								.withQuoteChar(ICSVParser.DEFAULT_QUOTE_CHARACTER)
								.withSeparator(ICSVParser.DEFAULT_SEPARATOR)
								.build())
						{
							for(Monster vMonster : vMonsters)
							{
								String[] vLine = vMonster.mToBook().toArray(new String[0]);
								vCSVWriter.writeNext(vLine);						
							}
							vCSVWriter.flush();
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
	        }
		);
		
		
		HBox vLink = new HBox();
		vLink.getChildren().add(aLinkLabel);
		vLink.getChildren().add(aLinkText);
		vLink.getChildren().add(this.aConvertURLButton);
		
		VBox vRoot = new VBox();
		vRoot.getChildren().add(vLink);
		
	    Scene vScene = new Scene(vRoot, 800, 600);
	    this.aWindow.setScene(vScene);
		this.aWindow.setTitle("Csv To Json");
		this.aWindow.show();
	}
	
	public List<String[]> mReadAllLines(File pFile)
	{
		CSVParser vParser = new CSVParserBuilder()				
				.withSeparator(',')
				.withIgnoreQuotations(false)
				.build();			
		
		try(Reader vReader = new FileReader(pFile))
		{
			try (CSVReader vCsvReader = new CSVReaderBuilder(vReader).withSkipLines(0).withCSVParser(vParser).build())
			{				
				return vCsvReader.readAll();
			}
			catch(Exception e)
			{
				System.out.println(e.getStackTrace());
				return null;
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getStackTrace());
			return null;
		}
	}
}
