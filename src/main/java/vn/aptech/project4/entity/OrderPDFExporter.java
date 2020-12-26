package vn.aptech.project4.entity;

import com.lowagie.text.Font;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.util.Date;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;

import java.awt.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
@Controller
public class OrderPDFExporter {
	private List<Order> listOrders;
    
    public OrderPDFExporter(List<Order> listOrders) {
        this.listOrders = listOrders;
    }
    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.blue);
        cell.setPadding(4);
         
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);
         
        cell.setPhrase(new Phrase("Order ID", font));
         
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Date Order", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Customer Name", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Total", font));
        table.addCell(cell);
     
    }
     
    private void writeTableData(PdfPTable table) {
        for (Order order : listOrders) {
        	
            table.addCell(String.valueOf(order.getId()));
			table.addCell(String.valueOf(order.getOrderDate())); 
            table.addCell(order.getCustomer().getCustomer_name());
            table.addCell(String.valueOf(order.getTotal())+" VND");
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
        
        Paragraph p = new Paragraph("Orders List For The Month"	+ "", font);
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
         
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.5f, 3.5f, 3.0f, 3.0f});
        table.setSpacingBefore(10);
         
        writeTableHeader(table);
        writeTableData(table);       
        document.add(table);
        int tong = 0;
		for (Order order : listOrders) {
			tong += (int)order.getTotal();
		}
        Paragraph e = new Paragraph("Total: " + tong + " VND", title1);
        e.setAlignment(Paragraph.ALIGN_RIGHT);
        document.add(e);
        document.close();
         
    }
}

