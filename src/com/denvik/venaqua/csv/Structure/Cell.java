package com.denvik.venaqua.csv.Structure;

public class Cell 
{
	private String cellName = null;
	private String cellValue = null;
	
	public Cell(){
		
	}
	public Cell( String name , String value ){
		this.cellName = name;
		this.cellValue = value;
	}
	
	public void setCellName( String name ){
		this.cellName = name;
	}
	public void setCellValue( String value ){
		this.cellValue = value;
	}
	
	public String getCellName(){
		return this.cellName;
	}
	public String getCellValue(){
		return this.cellValue;
	}
	
	public Integer getCellValueAsInteger(){
		if( this.cellValue == null )
			return null;
		
		return Integer.parseInt( this.cellValue );
	}
	
}
