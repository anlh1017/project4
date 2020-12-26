package vn.aptech.project4.report;
import com.lowagie.text.Font;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import vn.aptech.project4.entity.Inventory;

import java.util.Date;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;

import java.awt.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
public class InventoryPDFExporter {
	private List<Inventory> listInventory;
	private int getMonth = 0;
	 public InventoryPDFExporter(List<Inventory> listInventory,int getMonth) {
	        this.listInventory = listInventory;
	        this.getMonth = getMonth;
	    }
	 private void writeTableHeader(PdfPTable table) {
	        PdfPCell cell = new PdfPCell();
	        cell.setBackgroundColor(Color.blue);
	        cell.setPadding(8);
	         
	        Font font = FontFactory.getFont(FontFactory.HELVETICA);
	        font.setColor(Color.WHITE);
	         
	        cell.setPhrase(new Phrase("No.", font));
	         
	        table.addCell(cell);
	        cell.setPhrase(new Phrase("Date", font));
	        table.addCell(cell);
	        cell.setPhrase(new Phrase("Ingredient Name", font));
	        table.addCell(cell);
	         
	        cell.setPhrase(new Phrase("Vendor Name", font));
	        table.addCell(cell);
	         
	        cell.setPhrase(new Phrase("Unit", font));
	        table.addCell(cell);
	        cell.setPhrase(new Phrase("Stock", font));
	        table.addCell(cell);
	        cell.setPhrase(new Phrase("Price", font));
	        table.addCell(cell);
	        cell.setPhrase(new Phrase("Ratio", font));
	        table.addCell(cell);
	     
	    }
	     
	    private void writeTableData(PdfPTable table) {
	        for (Inventory inventory : listInventory) {
	        	
	            table.addCell(String.valueOf(inventory.getId()));
				table.addCell(String.valueOf(inventory.getInventoryDate())); 
	            table.addCell(inventory.getIngredient().getIngredientName());
	            table.addCell(String.valueOf(inventory.getVendorName()));
	            table.addCell(String.valueOf(inventory.getUnit()));
	            table.addCell(String.valueOf(inventory.getQuantity()));
	            table.addCell(String.valueOf(inventory.getPrice()+" VND"));
	            table.addCell(String.valueOf(inventory.getRatio()));
	            
	            
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
	        
	        Paragraph p = new Paragraph("Inventory List Of Month "+ getMonth, font);
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
	         
	        PdfPTable table = new PdfPTable(8);
	        table.setWidthPercentage(100f);
	        table.setWidths(new float[] {1.5f, 3.0f, 3.0f, 3.0f, 1.5f, 1.5f, 2.0f, 1.5f});
	        table.setSpacingBefore(10);
	         
	        writeTableHeader(table);
	        writeTableData(table);       
	        document.add(table);
	        int tong=0;
			for (Inventory inventory : listInventory) {
				tong+= inventory.getPrice();
			}
			Paragraph e = new Paragraph("Total : "+ tong+" VND" , title1);
	        e.setAlignment(Paragraph.ALIGN_RIGHT);
	        document.add(e);
	        document.close();
	         
	    }
	}
