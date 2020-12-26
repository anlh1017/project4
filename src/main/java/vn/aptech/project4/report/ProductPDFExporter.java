package vn.aptech.project4.report;

import java.awt.Color;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import vn.aptech.project4.entity.ChartInterface;
import vn.aptech.project4.entity.Customer;

public class ProductPDFExporter {
	private List<ChartInterface> listChart;
	public ProductPDFExporter(List<ChartInterface> listChart) {
        this.listChart = listChart;
    }
	 private void writeTableHeader(PdfPTable table) {
	        PdfPCell cell = new PdfPCell();
	        cell.setBackgroundColor(Color.blue);
	        cell.setPadding(2);
	         
	        Font font = FontFactory.getFont(FontFactory.HELVETICA);
	        font.setColor(Color.WHITE);
         
	        cell.setPhrase(new Phrase("Name", font));
	        table.addCell(cell);  
	        cell.setPhrase(new Phrase("Quantity", font));
	        table.addCell(cell);   

	    }
	     
	    private void writeTableData(PdfPTable table) {
			
			  for (ChartInterface chartInterface : listChart) {
			  table.addCell(chartInterface.getProductName());
			  table.addCell(chartInterface.getCount()); 
			  
			  }
			 
	      
	    }
	     
	    public void export(HttpServletResponse response) throws DocumentException, IOException {
	        Document document = new Document(PageSize.A4);
	        PdfWriter.getInstance(document, response.getOutputStream());
	         
	        document.open();
	        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
	        Font title = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
	        Font title1 = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
	        Font title2 = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
	        font.setSize(18);
	        font.setColor(Color.DARK_GRAY);
	        
	        title.setSize(24);
	        title.setColor(Color.DARK_GRAY);
	        
	        title1.setSize(12);
	        title1.setColor(Color.DARK_GRAY);
	        
	        Paragraph a = new Paragraph("Coffee Shop", title);
	        a.setAlignment(Paragraph.ALIGN_LEFT);
	        
	        Paragraph b = new Paragraph("Address: 590 Cach Mang Thang 8, District 3, Ho Chi Minh City", title1);
	        b.setAlignment(Paragraph.ALIGN_LEFT);
	        Paragraph c = new Paragraph("Tel: 09123456789", title1);
	        b.setAlignment(Paragraph.ALIGN_LEFT);
	        
	        Paragraph p = new Paragraph("Top Ten Product In sell", font);
	        p.setAlignment(Paragraph.ALIGN_CENTER);
	        document.add(a);
	        document.add(b);
	        document.add(c); 
	        DateFormat dateFormatter = new
	                SimpleDateFormat("dd-MM-yyyy"); String currentDateTime =
	                dateFormatter.format(new Date());
	        Paragraph d = new Paragraph("Date: " + currentDateTime, title1);
	        d.setAlignment(Paragraph.ALIGN_LEFT);
	        document.add(d);
	        document.add(p);
	         
	        PdfPTable table = new PdfPTable(2);
	        table.setWidthPercentage(100f);
	        table.setWidths(new float[] {3.0f,3.0f});
	        table.setSpacingBefore(10);
	         
	        writeTableHeader(table);
	        writeTableData(table);       
	        document.add(table);
	  
	        document.close();
	         
	    }
	}

