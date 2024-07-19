package com.airbnb.service;

import com.airbnb.dto.BookingDto;
import com.airbnb.entity.Bookings;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

@Service
public class PdfGenerationService {

    public void generatePDF(String fileName, BookingDto dto) {

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(fileName));

            document.open();
            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
            Chunk bookingConfirmation = new Chunk("BookingConfirmation", font);
            Chunk guestName = new Chunk("Guest Name"+dto.getGuestName(), font);
            Chunk price = new Chunk("price per night"+dto.getPrice(), font);
            Chunk totalPrice = new Chunk("Total Price"+dto.getTotalPrice(), font);

            document.add(bookingConfirmation);
            document.add(new Paragraph(" "));
            document.add(guestName);
            document.add(new Paragraph(" "));
            document.add(price);
            document.add(new Paragraph(" "));
            document.add(totalPrice);
            document.add(new Paragraph(" "));

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

