package DnD.JavaCsvToJson;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class JavaCsvToJson extends Application 
{
	private Stage aWindow; 
    private Button aBookToCsvFileButton = new Button("Book => CSV");
    private Button aCSVToBookFileButton = new Button("CSV => Book");
    private Label aLinkLabel = new Label("Lien : ");
    private TextField aLinkTextInput = new TextField();
    private Button aConvertURLButton = new Button("URL => Book");
    
	@Override
	public void start(Stage pWindow) throws Exception 
	{
		this.aWindow = pWindow;
		
		this.aBookToCsvFileButton.setOnAction
		(
			event -> 
			{
				List<Spell> vSpells = new ArrayList<>();
				List<Monster> vMonsters = new ArrayList<>();
				List<NPC> vNPCs = new ArrayList<>();
				List<Object> vObjects = new ArrayList<>();
				
				FileChooser vFileChooser = new FileChooser();
				vFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers Book", "*.csv"));
				File vOpenFile = vFileChooser.showOpenDialog(this.aWindow);
				if (vOpenFile != null)
				{
					List<String[]> vCsvData = this.mReadAllLines(vOpenFile);
					for(String[] vTable : vCsvData)
					{
						if(vTable[2].trim().equalsIgnoreCase("spell"))
						{
							try
							{
								vSpells.add(Spell.mFromJson(vTable));
							}
							catch(Exception e)
							{
								e.printStackTrace();
							}
						}
						else if(vTable[2].trim().equalsIgnoreCase("monster"))
						{
							try
							{
								vMonsters.add(Monster.mFromJson(vTable));
							}
							catch(Exception e)
							{
								e.printStackTrace();
							}
						}
						else if(vTable[2] == "npc")
						{
							vNPCs.add(null);
						}
						else if(vTable[2] == "object")
						{
							vObjects.add(null);
						}
					}
					vFileChooser.getExtensionFilters().clear();
					vFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers CSV", "*.csv"));
					vFileChooser.setInitialFileName("CSV-" + vOpenFile.getName());
					File vSaveFile = vFileChooser.showSaveDialog(this.aWindow);
					if(vSaveFile != null)
					{
						try(FileWriter vFileWriter = new FileWriter(vSaveFile))
						{
							ICSVWriter vCSVWriter = (ICSVWriter) new CSVWriterBuilder(vFileWriter)
									.withEscapeChar(ICSVParser.DEFAULT_ESCAPE_CHARACTER)
									.withLineEnd(ICSVWriter.DEFAULT_LINE_END)
									.withQuoteChar(ICSVParser.DEFAULT_QUOTE_CHARACTER)
									.withSeparator(ICSVParser.DEFAULT_SEPARATOR)
									.build();
							
							vSpells.sort
							(
								(pSpell1, pSpell2) -> 
								{
									return ((Spell)pSpell1).mSpellName().mName().compareTo(((Spell)pSpell2).mSpellName().mName());
								}
							);
							
							ArrayList<String> vLine = new ArrayList<>();

							vLine.add("Type");
							vLine.addAll(vSpells.get(0).mCSVHeaders());		
							vCSVWriter.writeNext(vLine.toArray(new String[0]));
							
							for(Spell vSpell : vSpells)
							{
								vLine.clear();								
								vLine.add("spell");
								vLine.addAll(vSpell.mToCSV());							
								vCSVWriter.writeNext(vLine.toArray(new String[0]));
							}
							vCSVWriter.flush();
							for(Monster vMonster : vMonsters)
							{
								vLine.clear();
								vLine.add("Type");
								vLine.addAll(vMonster.mCSVHeaders());
								vCSVWriter.writeNext(vLine.toArray(new String[0]));
								vLine.clear();								
								vLine.add("monster");
								vLine.addAll(vMonster.mToCSV());
								vCSVWriter.writeNext(vLine.toArray(new String[0]));
							}
							vCSVWriter.flush();
							
							for(NPC vNPC : vNPCs)
							{
								vFileWriter.write("\"Type\", " + vNPCs.get(0).mCSVHeaders() + "\n");
								vFileWriter.write("\"monster\", " + vNPC.mToCSV() + "\n");
							}
							vCSVWriter.flush();
							vFileWriter.flush();
							vFileWriter.write("\"Type\", " + vObjects.get(0).mCSVHeaders() + "\n");
							for(Object vObject : vObjects)
							{
								vFileWriter.write("\"monster\", " + vObject.mToCSV() + "\n");
							}
							vFileWriter.flush();
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
					}
	            }
				for(Spell vSpell : vSpells)
				{
					vSpell.mSpellAttacks().clear();
				}
				vSpells.clear();
				for(Monster vMonster : vMonsters)
				{
					
				}
				vMonsters.clear();
				for(NPC vNPC : vNPCs)
				{
					
				}
				vNPCs.clear();
				for(Object vObject : vObjects)
				{
					
				}
				vObjects.clear();
	        }
		);
		
		this.aCSVToBookFileButton.setOnAction
		(
			event -> 
			{
				List<Spell> vSpells = new ArrayList<>();
				List<Monster> vMonsters = new ArrayList<>();
				List<NPC> vNPCs = new ArrayList<>();
				List<Object> vObjects = new ArrayList<>();
				
				FileChooser vFileChooser = new FileChooser();
				vFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers CSV", "*.csv"));
				File vOpenFile = vFileChooser.showOpenDialog(this.aWindow);
				if (vOpenFile != null)
				{
					List<String[]> vCsvData = this.mReadAllLines(vOpenFile);
					String[] vHeaders = null;
					for(String[] vTable : vCsvData)
					{
						if(vTable[0].trim().equalsIgnoreCase("Type"))
						{
							vHeaders = vTable;
						}
						else if(vTable[0].trim().equalsIgnoreCase("spell"))
						{
							vSpells.add(Spell.mFromCsv(vHeaders, vTable));
						}
						else if(vTable[0].trim().equalsIgnoreCase("monster"))
						{
							
						}
					}
				}
			}
		);
		HBox vLink = new HBox();
		vLink.getChildren().add(aLinkLabel);
		vLink.getChildren().add(aLinkTextInput);
		vLink.getChildren().add(this.aConvertURLButton);
		
		VBox vRoot = new VBox();
		vRoot.getChildren().add(this.aBookToCsvFileButton);
		vRoot.getChildren().add(this.aCSVToBookFileButton);
		vRoot.getChildren().add(vLink);

		this.aCSVToBookFileButton.setDisable(true);
		
		this.aLinkLabel.setDisable(true);
		this.aLinkTextInput.setDisable(true);
		this.aConvertURLButton.setDisable(true);
		
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
