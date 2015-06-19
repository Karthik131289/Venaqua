package net.report;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.text.StyleConstants.ParagraphConstants;

import net.utills.IconCollections;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.itextpdf.text.pdf.PdfWriter;

public class waterBill 
{
	public static final String _outFilePath = System.getProperty("user.home")+ File.separator + "Desktop";
	public static final String _fileExtension = ".pdf";

	private static final Rectangle _pageSize = PageSize.A4;
	private static final Float _marginLeft = 50.0f;
	private static final Float _marginRight = 50.0f;
	private static final Float _marginTop = 50.0f;
	private static final Float _marginBottom = 50.0f;
	
	private static final int billHeadTableWidth = 225;
	private static final int billHeadTableHeight = 60;
	private static final int consumptionTableWidth = 500;
	private static final int consumptionTableHeight = 50;
	private static final int consSummaryTableWidth = 500;
	private static final int consSummaryTableHeight = 200;
	private static final int returnSlipSummaryWidth = 500;
	private static final int returnSlipSummaryHeight = 60;
	
	
	private File	 outFile;
	private Document doc;
	private PdfWriter writer;
	private PdfContentByte contentBytes;
	
	private BaseFont fontTitle;
	private BaseFont fontHead;
	private BaseFont fontText;
	
	private Location locLogo;
	private Location locTitle;
	private Location locCustomerDetail;
	private Location locBillHeader;
	private Location locConsumption;
	private Location locConsumpSummaryHeader;
	private Location locConsumpSummaryTable;
	private Location locReturnSlipSummary;
	
	public static void main(String[] args) 
	{
		waterBill bill = new waterBill();
        bill.createSampleBill();
	}
	public waterBill( )
	{
		//createSampleBill();
	}
	public void createSampleBill()
	{

		isFileExists( _outFilePath , "WaterBill-Nov" , "House-2" );
		
		initPDF();
		initBillSettings();
		addBillHeaderLayout();		
		
		addCustomerDetails( "Karthik", "dfsfdsjfjfks\nuiewhejkf");
		addBillHeaderDetails("2214443", "08/12/2014", "20/12/2014");
		addConsumptionDetails( "2000 KL", "3000 KL", "1000 KL", "4000 Rs." );
		
		Vector<String> inletName = new Vector<String>();
		Vector<String> totConsump = new Vector<String>();
		Vector<String> costPerLtr = new Vector<String>();
		Vector<String> totAmt = new Vector<String>();

		inletName.add("Inlet - 1 ");
		totConsump.add("2000");
		costPerLtr.add("2");
		totAmt.add("4000");
		
		inletName.add("Inlet - 2 ");
		totConsump.add("1000");
		costPerLtr.add("2");
		totAmt.add("2000");
		addConsumptionSummaryDetails( inletName , totConsump , costPerLtr , totAmt );
		
		
		addReturnSlipSummary("www.denvik.in","2214443","08/12/2014","20/12/2014","4000 Rs.");

        doc.newPage();

        tUtill newPage = new tUtill( doc , contentBytes );
        newPage.initBillSettings();
        newPage.addBillHeaderLayout();
        newPage.addCustomerDetails( "Karthik", "dfsfdsjfjfks\nuiewhejkf");
        newPage.addBillHeaderDetails("2214443", "08/12/2014", "20/12/2014");
        Vector<String> description = new Vector<String>();
        Vector<String> Amt = new Vector<String>();
        String newPage_totAmt = "";
        description.add("Inlet - 1 ");
        Amt.add("4000");
        description.add("Inlet - 2 ");
        Amt.add("2000");
        newPage_totAmt = "6000";
        newPage.addSummaryTableDetails(description, Amt , newPage_totAmt );
        newPage.addReturnSlipSummary("www.denvik.in","2214443","08/12/2014","20/12/2014", totAmt+" Rs.");

		closePDF();
		viewPdf();


	}
	public void initPDF()
	{
		try
		{			
			doc = new Document( _pageSize , _marginLeft , _marginRight , _marginTop , _marginBottom );
			writer = PdfWriter.getInstance( doc , new FileOutputStream( outFile ));		
			addPDFDetails();
			doc.open();
			contentBytes = writer.getDirectContent();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public void addPDFDetails()
	{
		try 
		{
			doc.addAuthor("Venaqua Water Metering System");
			doc.addCreationDate();
			doc.addProducer();
			doc.addCreator("www.denvik.in");
			doc.addTitle("Water Bill");
			doc.setPageSize(PageSize.LETTER);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public void initBillSettings()
	{
		try 
		{
			// Setting vertical gap between lines
			contentBytes.setLineWidth(1f);
			
			// Initializing Font Settings
			fontTitle= BaseFont.createFont(BaseFont.HELVETICA_BOLDOBLIQUE, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			fontHead = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			fontText = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			
			// Initializing locations
			locLogo = new Location( 25 , 700 );
			locTitle = new Location( 420 , 770 );
			locCustomerDetail = new Location( 180 , 745 );
			locBillHeader = new Location( 350, 700 );
			locConsumption = new Location( 50 , 630 );
			locConsumpSummaryHeader = new Location( 50, 600 );
			locConsumpSummaryTable = new Location(50, 590);
			locReturnSlipSummary = new Location( 50 , 340 );
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public void addBillHeaderLayout()
	{
		try 
		{
			// Add Logo
			Image logoVenaqua = Image.getInstance( IconCollections.class.getResource( "icons/appLogo_128.jpg" ) );
			logoVenaqua.setAbsolutePosition( locLogo.getPosX() , locLogo.getPosY() );
			logoVenaqua.scalePercent(100);
			doc.add(logoVenaqua);
			
			// Add Title
			createTitle( contentBytes , locTitle.getPosX() , locTitle.getPosY() , "Your Water Bill");
			
			// Add Customer Details Table
			createHeadings( contentBytes , locCustomerDetail.getPosX() , locCustomerDetail.getPosY()    , " Name        : " );
			createHeadings( contentBytes , locCustomerDetail.getPosX() , locCustomerDetail.getPosY()-20 , " Address   : " );
			
			
			// Bill Header Table
			contentBytes.rectangle( locBillHeader.getPosX() , locBillHeader.getPosY() ,billHeadTableWidth ,billHeadTableHeight );
			contentBytes.moveTo( locBillHeader.getPosX() , locBillHeader.getPosY()+20 );
			contentBytes.lineTo( locBillHeader.getPosX()+billHeadTableWidth , locBillHeader.getPosY()+20 );
			contentBytes.moveTo( locBillHeader.getPosX() , locBillHeader.getPosY()+40 );
			contentBytes.lineTo( locBillHeader.getPosX()+billHeadTableWidth , locBillHeader.getPosY()+40 );
			contentBytes.stroke();
			
			contentBytes.moveTo( locBillHeader.getPosX()+(billHeadTableWidth*0.45f), locBillHeader.getPosY() ); 
			contentBytes.lineTo( locBillHeader.getPosX()+(billHeadTableWidth*0.45f), locBillHeader.getPosY()+billHeadTableHeight );
			contentBytes.stroke();
			
			// Consumption Detail Table
			contentBytes.rectangle( locConsumption.getPosX() , locConsumption.getPosY() ,consumptionTableWidth ,consumptionTableHeight );
			contentBytes.moveTo( locConsumption.getPosX() , locConsumption.getPosY()+(consumptionTableHeight*0.5f) );
			contentBytes.lineTo( locConsumption.getPosX()+consumptionTableWidth , locConsumption.getPosY()+(consumptionTableHeight*0.5f) );
			contentBytes.stroke();
			
			contentBytes.moveTo( locConsumption.getPosX()+(consumptionTableWidth*0.25f) , locConsumption.getPosY() );
			contentBytes.lineTo( locConsumption.getPosX()+(consumptionTableWidth*0.25f) , locConsumption.getPosY()+consumptionTableHeight );
			contentBytes.moveTo( locConsumption.getPosX()+(consumptionTableWidth*0.50f) , locConsumption.getPosY() );
			contentBytes.lineTo( locConsumption.getPosX()+(consumptionTableWidth*0.50f) , locConsumption.getPosY()+consumptionTableHeight );
			contentBytes.moveTo( locConsumption.getPosX()+(consumptionTableWidth*0.75f) , locConsumption.getPosY() );
			contentBytes.lineTo( locConsumption.getPosX()+(consumptionTableWidth*0.75f) , locConsumption.getPosY()+consumptionTableHeight );
			contentBytes.stroke();
			
			// Consumption Summary
			createHeadings( contentBytes , locConsumpSummaryHeader.getPosX() , locConsumpSummaryHeader.getPosY() , "Summary - Consumption Details");

			contentBytes.rectangle( locConsumpSummaryTable.getPosX() , locConsumpSummaryTable.getPosY()-(consSummaryTableHeight) ,consSummaryTableWidth ,consSummaryTableHeight );
			contentBytes.stroke();
			contentBytes.moveTo( locConsumpSummaryTable.getPosX() , locConsumpSummaryTable.getPosY()-30 );
			contentBytes.lineTo( locConsumpSummaryTable.getPosX()+consSummaryTableWidth , locConsumpSummaryTable.getPosY()-30 );
			contentBytes.stroke();
			
			contentBytes.moveTo( locConsumpSummaryTable.getPosX()+(consSummaryTableWidth*0.35f) , locConsumpSummaryTable.getPosY() );
			contentBytes.lineTo( locConsumpSummaryTable.getPosX()+(consSummaryTableWidth*0.35f) , locConsumpSummaryTable.getPosY()-consSummaryTableHeight );
			contentBytes.moveTo( locConsumpSummaryTable.getPosX()+(consSummaryTableWidth*0.60f) , locConsumpSummaryTable.getPosY() );
			contentBytes.lineTo( locConsumpSummaryTable.getPosX()+(consSummaryTableWidth*0.60f) , locConsumpSummaryTable.getPosY()-consSummaryTableHeight );
			contentBytes.moveTo( locConsumpSummaryTable.getPosX()+(consSummaryTableWidth*0.80f) , locConsumpSummaryTable.getPosY() );
			contentBytes.lineTo( locConsumpSummaryTable.getPosX()+(consSummaryTableWidth*0.80f) , locConsumpSummaryTable.getPosY()-consSummaryTableHeight );
			contentBytes.stroke();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public void addCustomerDetails( String name , String addr )
	{
		try 
		{
			// Add Customer Name
			createHeadings( contentBytes , locCustomerDetail.getPosX()+50 , locCustomerDetail.getPosY() , name );
			
			// Add Customer Address
			StringTokenizer tokens = new StringTokenizer( addr , "\n" );
			int i=1;
			while( tokens.hasMoreTokens() )
			{
				createHeadings( contentBytes , locCustomerDetail.getPosX()+50 , locCustomerDetail.getPosY()-(i*20) , tokens.nextToken() );
				i++;
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	public void addBillHeaderDetails( String AcNo , String BillDate , String DueDate )
	{
		try 
		{
			createHeadings( contentBytes , locBillHeader.getPosX()+5 , locBillHeader.getPosY()+45 , " Flat No " );
			createHeadings( contentBytes , locBillHeader.getPosX()+5 , locBillHeader.getPosY()+25 , " Bill Date" );
			createHeadings( contentBytes , locBillHeader.getPosX()+5 , locBillHeader.getPosY()+5 ,  " Last Date for Payment" );
			
			createHeadings( contentBytes , locBillHeader.getPosX()+(billHeadTableWidth*0.45f)+10 , locBillHeader.getPosY()+45 , AcNo );
			createHeadings( contentBytes , locBillHeader.getPosX()+(billHeadTableWidth*0.45f)+10 , locBillHeader.getPosY()+25 , BillDate );
			createHeadings( contentBytes , locBillHeader.getPosX()+(billHeadTableWidth*0.45f)+10 , locBillHeader.getPosY()+5 , DueDate );
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public void addConsumptionDetails( String preMonthCons , String currMonthCons , String totalCons , String dueAmount )
	{
		try 
		{
			createHeadings( contentBytes , locConsumption.getPosX()+(billHeadTableWidth*0.01f)+2 , locConsumption.getPosY()+(consumptionTableHeight*0.5f)+10 , " Previous Month Consumption" );
			createHeadings( contentBytes , locConsumption.getPosX()+(billHeadTableWidth*0.57f)+2 , locConsumption.getPosY()+(consumptionTableHeight*0.5f)+10 , " Current Month Consumption" );
			createHeadings( contentBytes , locConsumption.getPosX()+(billHeadTableWidth*1.15f)+10 , locConsumption.getPosY()+(consumptionTableHeight*0.5f)+10 ,  " Overall Consumption " );
			createHeadings( contentBytes , locConsumption.getPosX()+(billHeadTableWidth*1.75f)+10 , locConsumption.getPosY()+(consumptionTableHeight*0.5f)+10 ,  " Amount Payable " );
			
			createHeadings( contentBytes , locConsumption.getPosX()+(billHeadTableWidth*0.01f)+40 , locConsumption.getPosY()+10 , preMonthCons );
			createHeadings( contentBytes , locConsumption.getPosX()+(billHeadTableWidth*0.57f)+40 , locConsumption.getPosY()+10 , currMonthCons );
			createHeadings( contentBytes , locConsumption.getPosX()+(billHeadTableWidth*1.15f)+30 , locConsumption.getPosY()+10 , totalCons );
			createHeadings( contentBytes , locConsumption.getPosX()+(billHeadTableWidth*1.75f)+20 , locConsumption.getPosY()+10 , dueAmount );
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void addConsumptionSummaryDetails( Vector<String> inletName , Vector<String> totConsump , Vector<String> costPerLtr , Vector<String> totAmt )
	{
		try 
		{
			final float x1 = locConsumpSummaryTable.getPosX()+(consSummaryTableWidth*0.01f)+10;
			final float x2 = locConsumpSummaryTable.getPosX()+(consSummaryTableWidth*0.35f)+10;
			final float x3 = locConsumpSummaryTable.getPosX()+(consSummaryTableWidth*0.60f)+10;
			final float x4 = locConsumpSummaryTable.getPosX()+(consSummaryTableWidth*0.80f)+10;
			 	  float y  = locConsumpSummaryTable.getPosY()-20;
			 	  
			createHeadings( contentBytes , x1 , y , "Inlet Name");
			createHeadings( contentBytes , x2 , y , "Total Consumption( KL )");
			createHeadings( contentBytes , x3 , y , "Cost per Liter");
			createHeadings( contentBytes , x4 , y , "Amount");
			
			y = y - 30;
			for (int i = 0; i < inletName.size(); i++) 
			{
				createText( contentBytes , x1 , y-(i*20) , inletName.get(i) );
				createText( contentBytes , x2 , y-(i*20) , totConsump.get(i) );
				createText( contentBytes , x3 , y-(i*20) , costPerLtr.get(i) );
				createText( contentBytes , x4 , y-(i*20) , totAmt.get(i) );
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	public void addReturnSlipSummary( String weblink , String acNo , String billDate , String dueDate , String amt )
	{
		try
		{
			createText( contentBytes , locReturnSlipSummary.getPosX() , locReturnSlipSummary.getPosY() , "You may pay your bill online @ "+weblink);
			
			contentBytes.setLineWidth(1.0f);
			contentBytes.setLineDash(3f,3f);
			contentBytes.moveTo( locReturnSlipSummary.getPosX()-49, locReturnSlipSummary.getPosY()-20 );
			contentBytes.lineTo( locReturnSlipSummary.getPosX()+550, locReturnSlipSummary.getPosY()-20 );
			contentBytes.stroke();
			
			contentBytes.setLineDash(0.5f,0.5f);
			contentBytes.rectangle( locReturnSlipSummary.getPosX() , locReturnSlipSummary.getPosY()-40-returnSlipSummaryHeight , returnSlipSummaryWidth , returnSlipSummaryHeight );
			contentBytes.moveTo( locReturnSlipSummary.getPosX() , locReturnSlipSummary.getPosY()-40-(returnSlipSummaryHeight*0.50f) );
			contentBytes.lineTo( locReturnSlipSummary.getPosX()+returnSlipSummaryWidth , locReturnSlipSummary.getPosY()-40-(returnSlipSummaryHeight*0.50f) );
			contentBytes.moveTo( locReturnSlipSummary.getPosX()+(returnSlipSummaryWidth*0.25f) , locReturnSlipSummary.getPosY()-40 );
			contentBytes.lineTo( locReturnSlipSummary.getPosX()+(returnSlipSummaryWidth*0.25f) , locReturnSlipSummary.getPosY()-40-returnSlipSummaryHeight );
			contentBytes.moveTo( locReturnSlipSummary.getPosX()+(returnSlipSummaryWidth*0.50f) , locReturnSlipSummary.getPosY()-40 );
			contentBytes.lineTo( locReturnSlipSummary.getPosX()+(returnSlipSummaryWidth*0.50f) , locReturnSlipSummary.getPosY()-40-returnSlipSummaryHeight );
			contentBytes.moveTo( locReturnSlipSummary.getPosX()+(returnSlipSummaryWidth*0.75f) , locReturnSlipSummary.getPosY()-40 );
			contentBytes.lineTo( locReturnSlipSummary.getPosX()+(returnSlipSummaryWidth*0.75f) , locReturnSlipSummary.getPosY()-40-returnSlipSummaryHeight );
			contentBytes.stroke();
			
			createText( contentBytes , locReturnSlipSummary.getPosX()+10+(returnSlipSummaryWidth*0.01f), locReturnSlipSummary.getPosY()-40-20, "Account Number");
			createText( contentBytes , locReturnSlipSummary.getPosX()+10+(returnSlipSummaryWidth*0.25f), locReturnSlipSummary.getPosY()-40-20, "Billing Date");
			createText( contentBytes , locReturnSlipSummary.getPosX()+10+(returnSlipSummaryWidth*0.50f), locReturnSlipSummary.getPosY()-40-20, "Last Date for Payment");
			createText( contentBytes , locReturnSlipSummary.getPosX()+10+(returnSlipSummaryWidth*0.75f), locReturnSlipSummary.getPosY()-40-20, "Amount Payable");
			
			createText( contentBytes , locReturnSlipSummary.getPosX()+10+(returnSlipSummaryWidth*0.01f), locReturnSlipSummary.getPosY()-40-20-(returnSlipSummaryHeight*0.50f), acNo);
			createText( contentBytes , locReturnSlipSummary.getPosX()+10+(returnSlipSummaryWidth*0.25f), locReturnSlipSummary.getPosY()-40-20-(returnSlipSummaryHeight*0.50f), billDate);
			createText( contentBytes , locReturnSlipSummary.getPosX()+10+(returnSlipSummaryWidth*0.50f), locReturnSlipSummary.getPosY()-40-20-(returnSlipSummaryHeight*0.50f), dueDate);
			createText( contentBytes , locReturnSlipSummary.getPosX()+10+(returnSlipSummaryWidth*0.75f), locReturnSlipSummary.getPosY()-40-20-(returnSlipSummaryHeight*0.50f), amt);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void createHeadings(PdfContentByte cb, float x, float y, String text)
	{
		cb.beginText();
		cb.setFontAndSize(fontHead, 8);
		cb.setTextMatrix(x,y);
		cb.showText(text);
		cb.endText(); 
	}
	private void createTitle(PdfContentByte cb, float x, float y, String text)
	{
		cb.beginText();
		cb.setFontAndSize(fontTitle, 12);
		cb.setTextMatrix(x,y);
		cb.showText(text);
		cb.endText(); 
	}
	private void createText(PdfContentByte cb , float x , float y, String text )
	{
		cb.beginText();
		cb.setFontAndSize(fontText, 10);
		cb.setTextMatrix(x,y);
		cb.showText(text);
		cb.endText(); 
	}
	private void generateBillContents()
	{
		try 
		{
			Phrase head;
			PdfPTable tableBillInfo;
			PdfPCell colName;
			
			/*** Adding Heading ***/
			Font fontHead = FontFactory.getFont( FontFactory.HELVETICA , 12 , Font.BOLDITALIC, new CMYKColor( 0, 255, 255,17 ) );	 
			head = new Phrase( _marginTop , "VenAqua Water Metering System \n\n" , fontHead );
			
			/*** General Bill Info ***/
			float colWidth[]= {1.0f,1.0f,1.0f};
			tableBillInfo = new PdfPTable(3);
			tableBillInfo.setWidthPercentage(100.0f);
			tableBillInfo.setWidths( colWidth );
			
			/*** Add Column Names ***/
			Font fontColName = FontFactory.getFont( FontFactory.HELVETICA , 12 , Font.BOLDITALIC, new CMYKColor( 0, 0 , 0 ,17 ) );
			
			colName = new PdfPCell( new Paragraph("Bill Date") );
			colName.setHorizontalAlignment( Element.ALIGN_CENTER);
			tableBillInfo.addCell( colName );
			
			colName = new PdfPCell( new Paragraph("Due Date") );
			tableBillInfo.addCell( colName );
			
			colName = new PdfPCell( new Paragraph("Account Number") );			
			tableBillInfo.addCell( colName );
			
			/*** Add Row Info ***/
			tableBillInfo.addCell( "30-Nov-2014" );
			tableBillInfo.addCell( "15-Dec-2014" );
			tableBillInfo.addCell( "1245893225" );

			doc.add( head );
			doc.add(tableBillInfo);
		} 
		catch (Exception e) 
		{
			
		}
	}
	
	public void closePDF()
	{
		try 
		{
			doc.close();
		} 
		catch (Exception e) 
		{
			
		}
	}
	public void isFileExists( String filePath , String parentFolder , String pdfName )
	{
		try 
		{
			File f = new File( filePath + File.separator + parentFolder );
			if( !f.exists() )
				f.mkdir();
			
			outFile = new File( f.getPath() + File.separator + pdfName + _fileExtension );
			if( !outFile.exists() )
			{
				outFile.delete();
				
				File temp = new File( f.getPath() + File.separator + pdfName + _fileExtension );
				temp.createNewFile();
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public void viewPdf()
	{
		try 
		{
			Desktop.getDesktop().open( outFile );
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	
	class Location
	{
		int posX,posY;
		
		public Location( int x , int y )
		{
			this.setLocation(x, y);
		}
		
		protected int getPosX()
		{
			return this.posX;
		}
		protected void setPosX( int x )
		{
			this.posX = x;
		}
		protected int getPosY()
		{
			return this.posY;
		}
		protected void setPosY( int y )
		{
			this.posX = y;
		}
		protected void setLocation( int x , int y )
		{
			this.posX = x;
			this.posY = y;
		}
	}
}
