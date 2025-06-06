package JavaCsvToJson.JavaCsvToJson;

import java.util.Arrays;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CSVRow 
{
    private final String[] aData;

    public CSVRow(String[] pData)
    {
        this.aData = Arrays.copyOf(pData, pData.length);
    }

    public String getColumn(int pIndex) 
    {
        if (pIndex >= 0 && pIndex < aData.length) 
        {
            return this.aData[pIndex];
        }
        return null; // Retourne une chaîne vide si l'index est hors limites
    }
}