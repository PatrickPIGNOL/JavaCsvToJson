package JavaCsvToJson.JavaCsvToJson;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class JavaCsvToJson extends Application 
{
	private Stage aWindow; 
	private TableView<CSVRow> aTableView = new TableView<>();
    private Button aLoadFileButton = new Button("Charger le fichier CSV");
	@Override
	public void start(Stage pWindow) throws Exception 
	{
		this.aWindow = pWindow;
		
		this.aLoadFileButton.setOnAction
		(
			event -> 
			{
				FileChooser fileChooser = new FileChooser();
				fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers CSV", "*.csv"));
				File selectedFile = fileChooser.showOpenDialog(this.aWindow);
				if (selectedFile != null) 
				{
	                this.aTableView.getColumns().clear();
					List<String[]> vCsvData = this.mReadAllLines(selectedFile);
					if (!vCsvData.isEmpty()) 
					{
					    this.mCreateTableColumns(vCsvData.get(0));
					    this.mPopulateTable(vCsvData);
					}
	            }
	        }
		);
		VBox root = new VBox(10, this.aLoadFileButton, this.aTableView);
	    Scene vScene = new Scene(root, 800, 600);
	    this.aWindow.setScene(vScene);
		this.aWindow.setTitle("Csv To Json");
		this.aWindow.show();
	}
	
	private void mCreateTableColumns(String[] pHeaders) 
	{
		for (int vIndex = 0; vIndex < pHeaders.length; vIndex++) 
		{            
            TableColumn<CSVRow, String> vColumn = new TableColumn<>(pHeaders[vIndex]);
            final int vFinalIndex = vIndex;
            vColumn.setCellValueFactory(pCellData -> pCellData.getValue().getColumn(vFinalIndex));
            this.aTableView.getColumns().add(vColumn);
        }
    }
	
	private void mPopulateTable(List<String[]> pCsvData) {
        ObservableList<CSVRow> vRows = FXCollections.observableArrayList();
        for (int vIndex = 1; vIndex < pCsvData.size(); vIndex++) {
        	vRows.add(new CSVRow(pCsvData.get(vIndex)));
        }
        this.aTableView.setItems(vRows);
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
