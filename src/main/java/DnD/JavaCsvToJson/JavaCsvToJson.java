package DnD.JavaCsvToJson;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class JavaCsvToJson extends Application 
{
	private Stage aWindow; 
	private Button aLoadSpell = new Button("Charger les URLs de sorts");
	private Button aLoadMonster = new Button("Charger les URLs de Monstres");
    private Label aLinkLabel = new Label("Liens : ");
    private TextArea aLinkText = new TextArea();
    private ProgressBar aProgressBar = new ProgressBar();
    private Button aConvertURLButton = new Button("URL => Book");
    
	@Override
	public void start(Stage pWindow) throws Exception 
	{
		this.aWindow = pWindow;
		
		this.aLoadSpell.setOnAction
		(
			event ->
			{
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
					}
				};
				new Thread(vEventWorker).start();
			}
		);
		
		this.aLoadMonster.setOnAction
		(
			event ->
			{
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
					}
				};
				new Thread(vEventWorker).start();
			}
		);
		
		this.aConvertURLButton.setOnAction
		(
			event -> 
			{
				aProgressBar.setProgress(-1);
				List<Monster> vMonsters = new ArrayList<>();
				List<Spell> vSpells = new ArrayList<>();	
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
								vMonsters.add(Monster.mFromURL(vStringURL));
							}
							else if(vStringURL.trim().contains("https://www.aidedd.org/dnd/sorts.php?vf"))
							{
								vSpells.add(Spell.mFromURL(vStringURL));
							}
							else
							{
								System.out.println("L'URL : \"" + vStringURL + "\" à été ignorée parce qu'elle ne fait pas partie du site \"aidedd.org/\".");
							}
							vCount++;
						}
					}
				};
				Thread vThread = new Thread(vEventWorker);
				vThread.start();
				try 
				{
					vThread.join();
				}
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
				FileChooser vFileChooser = new FileChooser();
				vFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers Book", "*.csv"));
				final File vSaveFile = vFileChooser.showSaveDialog(aWindow);
				vEventWorker = new Runnable() 
				{	
					@Override
					public void run() 
					{
						
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
									int vSize = vMonsters.size() + vSpells.size();
									int vCount = 0;	
									for(Monster vMonster : vMonsters)
									{
										final double vProgress = ((double)vCount)/((double)vSize);
										Platform.runLater(() -> aProgressBar.setProgress(vProgress));														
										String[] vLine = vMonster.mToBook().toArray(new String[0]);
										vCSVWriter.writeNext(vLine);	
										vCount++;
									}
									for(Spell vSpell : vSpells)
									{
										final double vProgress = ((double)vCount)/((double)vSize);
										Platform.runLater(() -> aProgressBar.setProgress(vProgress));														
										String[] vLine = vSpell.mToBook().toArray(new String[0]);
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
						}
					}
				};
				new Thread(vEventWorker).start();
	        }
		);

		VBox vTop = new VBox();
		vTop.getChildren().addAll(this.aLoadSpell, this.aLoadMonster, this.aLinkLabel);
		VBox vBottom = new VBox();
		vBottom.getChildren().addAll(this.aConvertURLButton, this.aProgressBar);
		
		BorderPane vRoot = new BorderPane();
	    Scene vScene = new Scene(vRoot, 800, 600);
	    vRoot.setPadding(new Insets(0, 0, 0, 0));  
		vRoot.setTop(vTop);
		vRoot.setCenter(aLinkText);
		vRoot.setBottom(vBottom);
		this.aProgressBar.setMaxWidth(Double.MAX_VALUE);
		this.aProgressBar.setProgress(0);
		this.aLinkLabel.setMaxWidth(Double.MAX_VALUE);
		this.aLinkLabel.setAlignment(Pos.CENTER);
		this.aLoadSpell.setMaxWidth(Double.MAX_VALUE);
		this.aLoadMonster.setMaxWidth(Double.MAX_VALUE);
		this.aConvertURLButton.setMaxWidth(Double.MAX_VALUE);
	    this.aWindow.setScene(vScene);
		this.aWindow.setTitle("URLs To Book");
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
