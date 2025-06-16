package DnD.JavaCsvToJson;

import java.awt.GridLayout;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.functors.IfClosure;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVParser;
import com.opencsv.ICSVWriter;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class JavaCsvToJson extends Application 
{
	private Stage aWindow; 
	private Button aLoadObjects = new Button("Charger les URLs d'objets");
	private Button aLoadSpell = new Button("Charger les URLs de sorts");
	private Button aLoadMonster = new Button("Charger les URLs de Monstres");
    private Label aLinkLabel = new Label("Liens : ");
    private TextArea aLinkText = new TextArea();
    private ProgressBar aProgressBar = new ProgressBar();
    private Button aConvertURLButton = new Button("Télécharger les données des URLs");
    private Button aModifyURLButton = new Button("Modifier les URLs (éfface les données)");
    private Button aSaveBook = new Button("Enregistrer en fichier Book (csv)");

	private Map<String, Monster> aMonsters = new HashMap<>();
	private Map<String, Spell> aSpells = new HashMap<>();
	private Map<String, DnDObject> aObjects = new HashMap<>();
	
	private EApplicationStatus aStatus = EApplicationStatus.Setup;
	
	@Override
	public void start(Stage pWindow) throws Exception 
	{
		this.aWindow = pWindow;
		
		this.aLoadObjects.setOnAction
		(
			event ->
			{
				aStatus = EApplicationStatus.Computing;
				mEnable();
				aProgressBar.setProgress(-1);
				Runnable vEventWorker = new Runnable() 
				{	
					@Override
					public void run() 
					{
						try 
						{
							Document vDocument = Jsoup.connect("https://www.aidedd.org/dnd-filters/objets-magiques.php").get();
							String vList = "";
							int vSize = vDocument.selectXpath("//table[@id='liste']//td/a").size();
							int vCount = 0;
							for(Element vElement : vDocument.selectXpath("//table[@id='liste']//td/a"))		
							{
								final double vProgress = ((double)vCount)/((double)vSize);
								Platform.runLater(() -> aProgressBar.setProgress(vProgress));
								vList += vElement.attr("href") + "\n";
								vCount++;
							}
							final String vResult = vList;
							Platform.runLater(() -> aLinkText.setText(aLinkText.getText() + vResult));
							Platform.runLater(() -> aProgressBar.setProgress(0));
						}
						catch (IOException e)
						{
							e.printStackTrace();
						}
						Platform.runLater
						(
							() -> 
							{
								aStatus = EApplicationStatus.Idle;
								mEnable();
							}
						);
					}
				};
				new Thread(vEventWorker).start();
			}
		);
		
		this.aLoadSpell.setOnAction
		(
			event ->
			{
				aStatus = EApplicationStatus.Computing;
				mEnable();
				aProgressBar.setProgress(-1);
				Runnable vEventWorker = new Runnable() 
				{	
					@Override
					public void run() 
					{
						try 
						{
							Document vDocument = Jsoup.connect("https://www.aidedd.org/dnd-filters/sorts.php").get();
							String vList = "";
							int vSize = vDocument.selectXpath("//table[@id='liste']//td/a").size();
							int vCount = 0;
							for(Element vElement : vDocument.selectXpath("//table[@id='liste']//td/a"))		
							{
								final double vProgress = ((double)vCount)/((double)vSize);
								Platform.runLater(() -> aProgressBar.setProgress(vProgress));
								vList += vElement.attr("href") + "\n";
								vCount++;
							}
							final String vResult = vList;
							Platform.runLater(() -> aLinkText.setText(aLinkText.getText() + vResult));
							Platform.runLater(() -> aProgressBar.setProgress(0));
						}
						catch (IOException e)
						{
							e.printStackTrace();
						}
						Platform.runLater
						(
							() -> 
							{
								aStatus = EApplicationStatus.Idle;
								mEnable();
							}
						);
					}
				};
				new Thread(vEventWorker).start();
			}
		);
		
		this.aLoadMonster.setOnAction
		(
			event ->
			{
				aStatus = EApplicationStatus.Computing;
				mEnable();
				aProgressBar.setProgress(-1);
				Runnable vEventWorker = new Runnable() 
				{	
					@Override
					public void run() 
					{
						try 
						{
							String vList = "";
							Document vDocument = Jsoup.connect("https://www.aidedd.org/dnd-filters/monstres.php").get();	
							int vSize = vDocument.selectXpath("//table[@id='liste']//td/a").size();
							int vCount = 0;
							for(Element vElement : vDocument.selectXpath("//table[@id='liste']//td/a"))		
							{	
								final double vProgress = ((double)vCount)/((double)vSize);
								Platform.runLater(() -> aProgressBar.setProgress(vProgress));
								vList += vElement.attr("href") + "\n";
								vCount++;
							}
							final String vResult = vList;
							Platform.runLater(() -> aLinkText.setText(aLinkText.getText() + vResult));
							Platform.runLater(() -> aProgressBar.setProgress(0));
						}
						catch (IOException e)
						{
							e.printStackTrace();
						}
						Platform.runLater
						(
							() -> 
							{
								aStatus = EApplicationStatus.Idle;
								mEnable();
							}
						);
					}
				};
				new Thread(vEventWorker).start();
			}
		);
		
		this.aConvertURLButton.setOnAction
		(
			event -> 
			{
				aStatus = EApplicationStatus.Computing;
				mEnable();
				aProgressBar.setProgress(-1);
				Runnable vEventWorker = new Runnable() 
				{	
					@Override
					public void run() 
					{
						int vSize = aLinkText.getText().split("\n").length;
						int vCount = 0;						
						for(String vStringURL : aLinkText.getText().split("\n"))
						{
							final double vProgress = ((double)vCount)/((double)vSize);
							Platform.runLater(() -> aProgressBar.setProgress(vProgress));
							if(vStringURL.trim().contains("https://www.aidedd.org/dnd/monstres.php?vf"))
							{
								if(!aMonsters.containsKey(vStringURL))
								{
									aMonsters.put(vStringURL, Monster.mFromURL(vStringURL));
								}
							}
							else if(vStringURL.trim().contains("https://www.aidedd.org/dnd/sorts.php?vf"))
							{
								if(!aSpells.containsKey(vStringURL))
								{
									aSpells.put(vStringURL, Spell.mFromURL(vStringURL));
								}
							}
							else if(vStringURL.trim().contains("https://www.aidedd.org/dnd/om.php?vf"))
							{
								if(!aObjects.containsKey(vStringURL))
								{
									aObjects.put(vStringURL, DnDObject.mFromURL(vStringURL));
								}								
							}
							else
							{
								System.out.println("L'URL : \"" + vStringURL + "\" à été ignorée parce qu'elle ne fait pas partie du site \"aidedd.org/\".");
							}
							vCount++;
						}
						Platform.runLater
						(
							() -> 
							{
								aStatus = EApplicationStatus.Data;
								mEnable();
							}
						);
					}
				};
				new Thread(vEventWorker).start();
	        }
		);
		
		this.aModifyURLButton.setOnAction
		(
			event -> 
			{
				Platform.runLater
				(
					() -> 
					{
						aStatus = EApplicationStatus.Computing;
						mEnable();
						aProgressBar.setProgress(-1);
						aMonsters.clear();
						aSpells.clear();
						aObjects.clear();
						aProgressBar.setProgress(0);
						aStatus = EApplicationStatus.Idle;
						mEnable();
					}
				);
			}
		);

		this.aSaveBook.setOnAction
		(
			event -> 
			{
				aStatus = EApplicationStatus.Computing;
				mEnable();
				FileChooser vFileChooser = new FileChooser();
				vFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers Book", "*.csv"));
				final File vSaveFile = vFileChooser.showSaveDialog(aWindow);
				if(vSaveFile != null)
				{
					aProgressBar.setProgress(-1);
					Runnable vEventWorker = new Runnable() 
					{	
						@Override
						public void run() 
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
									int vSize = aMonsters.size() + aSpells.size();
									int vCount = 0;	
									for(Entry<String, Monster> vMonster : aMonsters.entrySet())
									{
										final double vProgress = ((double)vCount)/((double)vSize);
										Platform.runLater(() -> aProgressBar.setProgress(vProgress));
										List<String> vTemp = new ArrayList<>();
										vTemp.add(vMonster.getKey());
										vTemp.addAll(vMonster.getValue().mToBook());
										String[] vLine = vMonster.getValue().mToBook().toArray(new String[0]);
										vCSVWriter.writeNext(vLine);	
										vCount++;
									}
									for(Entry<String, Spell> vSpell : aSpells.entrySet())
									{
										final double vProgress = ((double)vCount)/((double)vSize);
										Platform.runLater(() -> aProgressBar.setProgress(vProgress));	
										List<String> vTemp = new ArrayList<>();
										vTemp.add(vSpell.getKey());
										vTemp.addAll(vSpell.getValue().mToBook());
										String[] vLine = vSpell.getValue().mToBook().toArray(new String[0]);
										vCSVWriter.writeNext(vLine);
										vCount++;
									}
									for(Entry<String, DnDObject> vObject : aObjects.entrySet())
									{
										final double vProgress = ((double)vCount)/((double)vSize);
										Platform.runLater(() -> aProgressBar.setProgress(vProgress));
										List<String> vTemp = new ArrayList<>();
										vTemp.add(vObject.getKey());
										vTemp.addAll(vObject.getValue().mToBook());
										String[] vLine = vTemp.toArray(new String[0]);
										vCSVWriter.writeNext(vLine);
										vCount++;
									}
									vCSVWriter.flush();
									Platform.runLater(() -> aProgressBar.setProgress(0));
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
							Platform.runLater
							(
								() -> 
								{
									aStatus = EApplicationStatus.Data;
									mEnable();
								}
							);
						}
					};
					new Thread(vEventWorker).start();
				}
			}
		);
		
		this.aLinkText.textProperty().addListener
		(
			(observable, oldValue, newValue)-> 
			{
				if(aStatus == EApplicationStatus.Idle)
				{
					mEnable();
				}
			}
		);
		
		GridPane vRoot = new GridPane();
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPercentWidth(50);
		ColumnConstraints column2 = new ColumnConstraints();
		column2.setPercentWidth(50);
		vRoot.getColumnConstraints().addAll(column1, column2); // each get 50% of width
		RowConstraints rowConstraint1 = new RowConstraints();
		RowConstraints rowConstraint2 = new RowConstraints();
		rowConstraint2.prefHeightProperty().bind(this.aWindow.heightProperty());
		vRoot.getRowConstraints().addAll(rowConstraint1,rowConstraint1,rowConstraint1,rowConstraint1,rowConstraint2,rowConstraint1,rowConstraint1,rowConstraint1,rowConstraint1);
		
		vRoot.add(this.aLoadMonster, 0, 0, 2, 1);
		vRoot.add(this.aLoadSpell, 0, 1, 2, 1);
		vRoot.add(this.aLoadObjects, 0, 2, 2, 1);
		vRoot.add(this.aLinkLabel, 0, 3, 2, 1);
		vRoot.add(this.aLinkText, 0, 4, 2, 1);
		vRoot.add(this.aConvertURLButton, 0, 5, 1, 1);
		vRoot.add(this.aModifyURLButton, 1, 5, 1, 1);
		vRoot.add(this.aSaveBook, 0, 6, 2, 1);
		vRoot.add(this.aProgressBar, 0, 7, 2, 1);	
		
	    Scene vScene = new Scene(vRoot, 800, 600);  
	    vRoot.prefWidthProperty().bind(this.aWindow.widthProperty());
	    vRoot.prefHeightProperty().bind(this.aWindow.widthProperty());
	    this.aProgressBar.setMinHeight(20);
		this.aProgressBar.setMaxWidth(Double.MAX_VALUE);
		this.aProgressBar.setProgress(0);
		this.aLinkLabel.setMaxWidth(Double.MAX_VALUE);
		this.aLinkText.setMaxHeight(Double.MAX_VALUE);
		this.aLinkLabel.setAlignment(Pos.CENTER);
		this.aLoadObjects.setMaxWidth(Double.MAX_VALUE);
		this.aLoadSpell.setMaxWidth(Double.MAX_VALUE);
		this.aLoadMonster.setMaxWidth(Double.MAX_VALUE);
		this.aConvertURLButton.setMaxWidth(Double.MAX_VALUE);
		this.aModifyURLButton.setMaxWidth(Double.MAX_VALUE);
		this.aSaveBook.setMaxWidth(Double.MAX_VALUE);
	    this.aWindow.setScene(vScene);
	    
	    this.aConvertURLButton.setDisable(true);
	    this.aModifyURLButton.setDisable(true);
	    this.aSaveBook.setDisable(true);
	    
		this.aWindow.setTitle("URLs To Book");
		this.aWindow.show();
		this.aStatus = EApplicationStatus.Idle;
		this.mEnable();
	}
	
	public void mEnable()
	{
		switch(this.aStatus)
		{
			case Idle:
			{

				this.aLoadMonster.setDisable(false);
				this.aLoadSpell.setDisable(false);
				this.aLoadObjects.setDisable(false);
				this.aLinkText.setDisable(false);
				if(this.aLinkText.getText().isEmpty())
				{
					this.aConvertURLButton.setDisable(true);
				}
				else
				{
					this.aConvertURLButton.setDisable(false);
				}
				this.aModifyURLButton.setDisable(true);
				this.aSaveBook.setDisable(true);
			}break;
			case Data:
			{
				this.aLoadMonster.setDisable(true);
				this.aLoadSpell.setDisable(true);
				this.aLoadObjects.setDisable(true);
				this.aLinkText.setDisable(true);
				this.aConvertURLButton.setDisable(true);
				this.aModifyURLButton.setDisable(false);
				this.aSaveBook.setDisable(false);
			}break;
			default:
			{
				this.aLoadMonster.setDisable(true);
				this.aLoadSpell.setDisable(true);
				this.aLoadObjects.setDisable(true);
				this.aLinkText.setDisable(true);
				this.aConvertURLButton.setDisable(true);
				this.aModifyURLButton.setDisable(true);
				this.aSaveBook.setDisable(true);
			}break;
		};		
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
