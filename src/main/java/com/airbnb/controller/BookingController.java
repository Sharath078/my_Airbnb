package com.airbnb.controller;

import com.airbnb.dto.BookingDto;
import com.airbnb.entity.Bookings;
import com.airbnb.entity.Property;
import com.airbnb.entity.PropertyUser;
import com.airbnb.repository.BookingsRepository;
import com.airbnb.repository.PropertyRepository;
import com.airbnb.service.PdfGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/booking")
public class BookingController {

    private BookingsRepository bookingRepository;
    private PropertyRepository  propertyRepository;
    private PdfGenerationService pdfGenerationService;


    public BookingController(BookingsRepository bookingRepository, PropertyRepository propertyRepository, PdfGenerationService pdfGenerationService) {
        this.bookingRepository = bookingRepository;
        this.propertyRepository = propertyRepository;
        this.pdfGenerationService = pdfGenerationService;
    }

    @PostMapping("/createBooking/{propertyId}")
    public ResponseEntity<Bookings> createBooking(@RequestBody Bookings booking,
                                                 @AuthenticationPrincipal PropertyUser user
            , @PathVariable Long propertyId   ) {
        booking.setPropertyUser(user);
        Property property = propertyRepository.findById(propertyId).get();
        int propertyPrice=property.getNightlyPrice();
        int totalNights = booking.getTotal_nights();
        int totalPrice=propertyPrice * totalNights;
        booking.setProperty(property);
        booking.setTotalPrice(totalPrice);

        Bookings createdBooking = bookingRepository.save(booking);
        BookingDto dto= new BookingDto();
        dto.setBookingId(createdBooking.getId());
        dto.setGuestName(createdBooking.getGuestName());
        dto.setTotalPrice(createdBooking.getTotalPrice());
        dto.setTotalPrice(propertyPrice);
        pdfGenerationService.generatePDF("E://PDF//"+"bookings-confirmation-id"+createdBooking.getId()+".pdf",dto);

        return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);

    }

}