package vn.aptech.project4.report;

import java.awt.Color;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

import vn.aptech.project4.entity.Customer;

public class CustomerPDFExporter {
private List<Customer> listCustomer;
private int getMonth = 0;
    public CustomerPDFExporter(List<Customer> listCustomer,int getMonth) {
        this.listCustomer = listCustomer;
        this.getMonth = getMonth;
    }
    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.blue);
        cell.setPadding(6);
         
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);
         
        cell.setPhrase(new Phrase("No.", font));       
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("Date", font));       
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Name", font));
        table.addCell(cell);      
        
        cell.setPhrase(new Phrase("Address", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Phone", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Total Expense", font));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("Membership", font));
        table.addCell(cell);
    }
     
    private void writeTableData(PdfPTable table) {
        for (Customer customer : listCustomer) {
        	
            table.addCell(String.valueOf(customer.getCustomer_id()));
            table.addCell(String.valueOf(customer.getCustomerDate()));
			table.addCell(String.valueOf(customer.getCustomer_name())); 
			table.addCell(String.valueOf(customer.getAddress()));
			table.addCell(String.valueOf(customer.getCustomer_phone()));
			table.addCell(String.valueOf(customer.getTotal_expense()+" VND"));			
            table.addCell(customer.getMembership().getMembership_name());
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
        
        Paragraph p = new Paragraph("Customers List Of Month "+ getMonth, font);
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
         
        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.0f, 2.5f, 2.0f, 2.5f, 2.5f, 3.0f, 3.0f});
        table.setSpacingBefore(10);
         
        writeTableHeader(table);
        writeTableData(table);       
        document.add(table);
        int tong=0;
		for (Customer customer : listCustomer) {
			tong+= customer.getTotal_expense();
		}
		Paragraph e = new Paragraph("Total Expense : "+ tong+" VND" , title1);
        e.setAlignment(Paragraph.ALIGN_RIGHT);
        document.add(e);
        document.close();
         
    }
}

