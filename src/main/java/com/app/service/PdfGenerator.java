package com.app.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.util.List;

public class PdfGenerator {

    private Document document = new Document();

    public PdfGenerator(String fileName) {
        initGenerator(fileName);
    }

    public void addTable(String mainHeader, List<String> headers, List<List<String>> tableContent){

        try{
            PdfPTable table = new PdfPTable(headers.size());

            addHeaderToTable(table, mainHeader, headers.size());
            addRowToTable(table, headers);
            tableContent.forEach(row -> addRowToTable(table, row));

            document.add(table);

        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    public void generatePdf(){
        document.close();
    }

    private PdfPTable addHeaderToTable(PdfPTable table, String mainHeader, int span){

        if(mainHeader != null){

            Paragraph paragraph = new Paragraph(mainHeader);
            paragraph.setAlignment(Element.ALIGN_CENTER);

            PdfPCell pcell = new PdfPCell(paragraph);
            pcell.setColspan(span);
            pcell.setVerticalAlignment(Element.ALIGN_CENTER);
            pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(pcell);

        }
        return table;
    }

    private PdfPTable addRowToTable(PdfPTable table, List<String> row){

        Paragraph paragraph;
        PdfPCell pcell;

        for(int i = 0; i < row.size(); i++){

            paragraph = new Paragraph(row.get(i));
            paragraph.setAlignment(Element.ALIGN_CENTER);

            pcell = new PdfPCell();
            pcell.addElement(paragraph);
            pcell.setVerticalAlignment(Element.ALIGN_CENTER);
            pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(pcell);

        }

        return table;
    }

    private void initGenerator(String fileName){

        String desktopPath = System.getProperty("user.home") + "\\desktop\\" + fileName;
        try {
            PdfWriter.getInstance(document, new FileOutputStream(desktopPath));
            document.open();
        } catch (Exception e) {
            System.err.println("There was a problem with generating a pdf file!");
        }

    }
}
