package JavaCsvToJson.JavaCsvToJson;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.simple.JSONObject;


public class JavaCsvToJson extends Application 
{
	private Stage aWindow; 
    private Button aBookToCsvFileButton = new Button("Book => CSV");
    private Button aCSVToBookFileButton = new Button("CSV => Book");
    private Label aLinkLabel = new Label("Lien : ");
    private TextField aLinkTextInput = new TextField();
    private Button aConvertURLButton = new Button("Convertir une URL en Fichier de Book");
    
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
						if(vTable[2].equalsIgnoreCase("spell"))
						{
							vSpells.add(Spell.mFromJson(vTable[1]));
						}
						else if(vTable[2] == "monster")
						{
							vMonsters.add(null);
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
	            }
				vFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers CSV", "*.csv"));
				vFileChooser.setInitialFileName("CSV-" + vOpenFile.getName());
				File vSaveFile = vFileChooser.showSaveDialog(this.aWindow);
				if(vSaveFile != null)
				{
					try(FileWriter vFileWriter = new FileWriter(vSaveFile))
					{
						vSpells.sort
						(
							(pSpell1, pSpell2) -> 
							{
								return ((Spell)pSpell1).mSpellName().mName().compareTo(((Spell)pSpell2).mSpellName().mName());
							}
						);
						vFileWriter.write("Type, " + vSpells.get(0).mCSVHeaders() + "\n");
						for(Spell vSpell : vSpells)
						{
							vFileWriter.write("spell, " + vSpell.mToCSV() + "\n");
						}
						vFileWriter.flush();
					}
					catch(Exception e)
					{
						e.printStackTrace();
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
