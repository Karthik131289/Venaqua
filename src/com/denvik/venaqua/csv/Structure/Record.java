package com.denvik.venaqua.csv.Structure;

import java.util.Iterator;
import java.util.Vector;

public class Record 
{
	private String timeStamp;
	private Vector<String> stringData;
	private Vector<Cell> cellData;
	private Vector<String> headerData;
	
	public Record() 
	{
		this.timeStamp = null;
		this.stringData = new Vector<String>();
		this.cellData = new Vector<Cell>();
		this.headerData = new Vector<String>();
	}
	public Record( Vector<String> colData , Vector<String> header )
	{
		this.headerData = new Vector<String>( header );
		this.stringData = new Vector<String>( colData );		
		this.timeStamp = colData.elementAt(0);					// Time - First column in each record.
		this.stringData.remove(0);								// Removing Time from stringData bcoz stringData only for flow values.
		this.generateCellData();
	}	
	public Record( String time , Vector<String> colData , Vector<String> header )
	{
		this.timeStamp = time;
		this.stringData = new Vector<String>( colData );
		this.headerData = new Vector<String>( header );
		this.generateCellData();
	}
	
	private void generateCellData()
	{
		if( this.cellData !=null )
			this.cellData.removeAllElements();
		else
			this.cellData = new Vector<Cell>();
		
		Iterator<String> header = this.headerData.iterator();
		Iterator<String> value  = this.stringData.iterator();
		while( header.hasNext() )
		{
			Cell cell = new Cell( header.next() , value.next() );
			this.cellData.add( cell );
		}
	}
	
	public Vector<Cell> getFlowValues()
	{
		Vector<Cell> temp = new Vector<Cell>( this.cellData );
		return temp;
	}
	public Vector<String> getFlowValuesAsString()
	{
		Vector<String> temp = new Vector<String>( this.stringData );
		return temp;
	}
	public String getTime()
	{
		return this.timeStamp;
	}
	public Cell getCell( int index ){
		return this.cellData.elementAt( index );
	}
	
	public void reInitialize( String time , Vector<String>colData , Vector<String>header )
	{
		this.timeStamp = time;
		this.stringData.removeAllElements();
		this.stringData.addAll( colData );
		this.headerData.removeAllElements();
		this.headerData.addAll( header );
		generateCellData();
	}
}
