package net.report;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import net.utills.IconCollections;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.util.StringTokenizer;
import java.util.Vector;

public class tUtill
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
	private static final int summaryTableWidth = 500;
	private static final int summaryTableHeight = 200;
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
	private Location locSummaryTableHeader;
	private Location locSummaryTable;
	private Location locReturnSlipSummary;

	public static void main(String[] args)
	{
		/*tUtill bill = new tUtill();
        bill.createSampleBill();*/
	}

    public tUtill( Document doc , PdfContentByte contentBytes ) {
        this.doc = doc ;
        this.contentBytes = contentBytes;
    }

	public void createSampleBill()
	{
		/*isFileExists( _outFilePath , "WaterBill-Nov" , "House-2" );
		
		initPDF();
		initBillSettings();
		addBillHeaderLayout();		
		
		addCustomerDetails( "Karthik", "dfsfdsjfjfks\nuiewhejkf");
		addBillHeaderDetails("2214443", "08/12/2014", "20/12/2014");
		
		Vector<String> description = new Vector<String>();
		Vector<String> Amt = new Vector<String>();
        String totAmt = "";
        description.add("Inlet - 1 ");
		Amt.add("4000");
        description.add("Inlet - 2 ");
		Amt.add("2000");
        totAmt = "6000";
		addSummaryTableDetails(description, Amt , totAmt );
		
		addReturnSlipSummary("www.denvik.in","2214443","08/12/2014","20/12/2014", totAmt+" Rs.");

        doc.newPage();*/

        initBillSettings();
        addBillHeaderLayout();

        addCustomerDetails( "Karthik", "dfsfdsjfjfks\nuiewhejkf");
        addBillHeaderDetails("2214443", "08/12/2014", "20/12/2014");

        Vector<String> description = new Vector<String>();
        Vector<String> Amt = new Vector<String>();
        String totAmt = "";
        description.add("Inlet - 1 ");
        Amt.add("4000");
        description.add("Inlet - 2 ");
        Amt.add("2000");
        totAmt = "6000";
        addSummaryTableDetails(description, Amt , totAmt );

        addReturnSlipSummary("www.denvik.in","2214443","08/12/2014","20/12/2014", totAmt+" Rs.");

		//closePDF();
		//viewPdf();
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
			doc.addTitle("Maintenance Bill");
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

            // Summary Table
            locSummaryTableHeader = new Location( 50, 630 );
            locSummaryTable = new Location(50, 620);

            // Return Slip Table
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
			createTitle( contentBytes , locTitle.getPosX() , locTitle.getPosY() , "Your Maintenance Bill");
			
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

			// Consumption Summary
			createHeadings(contentBytes, locSummaryTableHeader.getPosX(), locSummaryTableHeader.getPosY(), "Summary - Maintenance Details");

			contentBytes.rectangle( locSummaryTable.getPosX() , locSummaryTable.getPosY()-(summaryTableHeight) , summaryTableWidth, summaryTableHeight);
			contentBytes.stroke();
			contentBytes.moveTo( locSummaryTable.getPosX() , locSummaryTable.getPosY()-30 );
			contentBytes.lineTo( locSummaryTable.getPosX()+ summaryTableWidth, locSummaryTable.getPosY()-30 );
			contentBytes.stroke();

            contentBytes.moveTo( locSummaryTable.getPosX()+(summaryTableWidth *0.25f) , locSummaryTable.getPosY() );
            contentBytes.lineTo( locSummaryTable.getPosX()+(summaryTableWidth *0.25f) , locSummaryTable.getPosY()- summaryTableHeight);
            contentBytes.moveTo( locSummaryTable.getPosX()+(summaryTableWidth *0.75f) , locSummaryTable.getPosY() );
            contentBytes.lineTo( locSummaryTable.getPosX()+(summaryTableWidth *0.75f) , locSummaryTable.getPosY()- summaryTableHeight);
            contentBytes.stroke();

            // Add total field
            contentBytes.rectangle( locSummaryTable.getPosX() , locSummaryTable.getPosY()-summaryTableHeight-30 , summaryTableWidth, 30);
            contentBytes.stroke();
            contentBytes.moveTo( locSummaryTable.getPosX()+(summaryTableWidth *0.75f) , locSummaryTable.getPosY()-summaryTableHeight );
            contentBytes.lineTo( locSummaryTable.getPosX()+(summaryTableWidth *0.75f) , locSummaryTable.getPosY()- summaryTableHeight-30);
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

	public void addSummaryTableDetails(Vector<String> description , Vector<String> amt , String totAmt )
	{
		try 
		{
			final float x1 = locSummaryTable.getPosX()+(summaryTableWidth *0.01f)+10;
			final float x2 = locSummaryTable.getPosX()+(summaryTableWidth *0.25f)+10;
			final float x3 = locSummaryTable.getPosX()+(summaryTableWidth *0.75f)+10;
            final float x4 = locSummaryTable.getPosX()+(summaryTableWidth *0.75f)-30;
            final float x5 = locSummaryTable.getPosX()+(summaryTableWidth *0.75f)+10;
            float y  = locSummaryTable.getPosY()-20;
			 	  
			createHeadings( contentBytes , x1 , y , "SNo");
			createHeadings( contentBytes , x2 , y , "Description");
			createHeadings( contentBytes , x3 , y , "Amount");

            createHeadings( contentBytes , x4 , y-summaryTableHeight , "Total" );
            createText( contentBytes , x5 , y-summaryTableHeight , totAmt );

			y = y - 30;
			for (int i = 0; i < description.size(); i++)
			{
				createText( contentBytes , x1 , y-(i*20) , (i+1)+"" );
				createText( contentBytes , x2 , y-(i*20) , description.get(i) );
				createText(contentBytes, x3, y - (i * 20), amt.get(i));
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
