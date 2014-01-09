package org.gem.utils.csv;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Date;
import java.util.List;

import org.gem.business.CapitalOneCreditCardTransaction;

import com.googlecode.jcsv.*;
import com.googlecode.jcsv.annotations.internal.ValueProcessorProvider;
import com.googlecode.jcsv.reader.CSVEntryParser;
import com.googlecode.jcsv.reader.CSVReader;
import com.googlecode.jcsv.reader.internal.AnnotationEntryParser;
import com.googlecode.jcsv.reader.internal.CSVReaderBuilder;

public class CSVUtil {
	public static final String CSV_DATA_FILE = "C:\\Users\\--\\Documents\\Expenses2013.csv";
	
	public static List<CapitalOneCreditCardTransaction> getAllRecordsFromCSV(String csvFilePath)
	{
		try {
			Reader reader = new FileReader(csvFilePath);
			
			ValueProcessorProvider provider = getValueProcessorProvider();
			
			CSVEntryParser<CapitalOneCreditCardTransaction> entryParser = new AnnotationEntryParser<CapitalOneCreditCardTransaction>(CapitalOneCreditCardTransaction.class, provider);
			CSVReader<CapitalOneCreditCardTransaction> csvCreditCardTransactionReader = new CSVReaderBuilder<CapitalOneCreditCardTransaction>(reader).entryParser(entryParser).strategy(CSVStrategy.UK_DEFAULT).build();

			List<CapitalOneCreditCardTransaction> transactions = csvCreditCardTransactionReader.readAll();
		
			return transactions;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		
	}

	private static ValueProcessorProvider getValueProcessorProvider() {
		
		ValueProcessorProvider provider = new ValueProcessorProvider();
		provider.removeValueProcessor(Date.class);
		provider.registerValueProcessor(Date.class, new DateProcessor());
		//TODO: determine if the date is being parsed and captured correctly
		provider.removeValueProcessor(Double.class);
		provider.registerValueProcessor(Double.class, new DoubleProcessor());
		return provider;
	}
	
	
}
