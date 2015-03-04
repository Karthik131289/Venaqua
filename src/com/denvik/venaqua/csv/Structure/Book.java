package com.denvik.venaqua.csv.Structure;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import com.denvik.venaqua.csv.utils.FileUtils;

public class Book 
{
	private static final String EXT = ".csv";
	private static final Boolean debug = false;
	private static final Boolean LOG_ONLY_LAST_RECORD = false;
	
	private int recordCount;
	private int rowCount;
	private int colCount;
	
	private String fileName;
	
	private File book;
	private Vector<String> inletName;
	private Vector<String> waterType;
	private Map< String , String > header;
	private Vector<Record> records;
	
	public Book( String filePath ) 
	{
		this( FileUtils.getFile( filePath, EXT ) );
	}
	
	public Book( File f )
	{
		this.book = f;
		this.fileName = this.book.getName();
		this.recordCount = 0;
		this.rowCount = 0;
		this.colCount = 0;
		this.header = new HashMap<String, String>();
		this.inletName = new Vector<String>();
		this.waterType = new Vector<String>();
		this.records = new Vector<Record>();
		
		this.processBookDetails();
		this.storeHeaderDetails();
	}
	
	public File getBook(){
		return this.book;
	}
	public String getBookPath(){
		return this.book.getPath();
	}
	public String getBookName(){
		return this.fileName;
	}
	public File getParentFile(){
		return this.book.getParentFile();
	}
	public String getParentPath(){
		return this.book.getParent();
	}
	
	public void processBookDetails(){
		StringBuffer fileData = FileUtils.readFile( this.book ); 
		StringTokenizer row = new StringTokenizer( fileData.toString() , "\n" );
		
		final int headRow = 0;
		final int waterTypeRow = 1;
		int rowIndex = 0;
		while( row.hasMoreTokens() )
		{
			String rowData = row.nextToken();
			if( debug )
				System.out.println( rowData );
			
			StringTokenizer cell = new StringTokenizer( rowData , "," );
			if( rowIndex == headRow )
			{
				this.inletName.removeAllElements();
				while( cell.hasMoreTokens() )
				{
					this.inletName.add( cell.nextToken() );
				}
			}
			else if( rowIndex == waterTypeRow )
			{
				this.waterType.removeAllElements();
				while( cell.hasMoreTokens() )
				{
					this.waterType.add( cell.nextToken() );
				}
			}
			else
			{
				Vector<String> temp = new Vector<String>();
				temp.removeAllElements();
				while( cell.hasMoreTokens() )
				{
					temp.add( cell.nextToken() );
				}
				
				if( LOG_ONLY_LAST_RECORD ){
					if( !row.hasMoreTokens() )
						this.records.add( new Record( temp , this.inletName ) );
				} else 
					this.records.add( new Record( temp , this.inletName ) );
				
				this.recordCount++;
			}
			this.rowCount++;
			rowIndex++;
		}
		
		this.colCount = this.inletName.size();
	}
	
	private void storeHeaderDetails(){
		for( int i=0; i<this.inletName.size(); i++ )
			this.header.put( this.inletName.elementAt(i) , this.waterType.elementAt(i) );
	}
	
	public Vector<String> getInletName(){
		return this.inletName;
	}
	public Vector<String> getWaterTypes(){
		return this.waterType;
	}
	public Map<String, String> getHeader(){
		return this.header;
	}
	public Vector<Record> getRecords(){
		return this.records;
	}
	public Record getRecord( int index ){
		return this.records.elementAt( index );
	}
	public Record getLastRecord() {
		return this.records.lastElement();
	}
	
	public int getTotalRecord(){
		return this.recordCount;
	}
	public int getRowCount(){
		return this.rowCount;
	}
	public int getColCount(){
		return this.colCount;
	}
	
	public Cell getCell( int rowIndex , int colIndex ){
		Cell cell = null ;
		Record rec = this.records.elementAt( rowIndex );
		cell = rec.getCell( colIndex );
		return cell;
	}
	
	public static void main( String args[] ){
		Book b = new Book( "G:/Denvik Projects/Venaqua Web/Sample csv files/VENAQUA_01-JAN-15_Master1.csv" );
		
		System.out.println( b.getBookName() );
		System.out.println( b.getBookPath() );
		System.out.println( b.getParentPath() );
		System.out.println( b.getRowCount() );
		System.out.println( b.getTotalRecord() );
		System.out.println( b.getInletName().size() + " -- "+ b.getInletName() );
		System.out.println( b.getWaterTypes() );
		int i=0;
		while( i<b.getTotalRecord() )
		{
			Record r = b.getRecord( i );
			//System.out.println( (i+1)+ " : "+ r.getTime() + " " + r.getFlowValuesAsString() );
			i++;
		}
		Vector<Cell> celldata = b.getLastRecord().getFlowValues();
		System.out.println( "Last Record : "  );
		Iterator<Cell> it = celldata.iterator();
		while ( it.hasNext() ) {
			Cell cell = it.next();
			System.out.print( cell.getCellValue() + "  " );
		}
	}
}
