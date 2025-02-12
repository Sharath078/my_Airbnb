package com.airbnb.controller;

import com.airbnb.dto.ReviewDto;
import com.airbnb.entity.Property;
import com.airbnb.entity.PropertyUser;
import com.airbnb.entity.Review;
import com.airbnb.repository.PropertyRepository;
import com.airbnb.repository.ReviewRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {
    private final PropertyRepository propertyRepository;
    private final ReviewRepository reviewRepository;

    public ReviewController(PropertyRepository propertyRepository, ReviewRepository reviewRepository) {
        this.propertyRepository = propertyRepository;
        this.reviewRepository = reviewRepository;
    }

    @PostMapping("/addReview/{propertyId}")
    public ResponseEntity<String> addReview(
            @PathVariable Long propertyId,
            @RequestBody Review review,
            @AuthenticationPrincipal PropertyUser user) {

        Optional<Property> opProperty = propertyRepository.findById(propertyId);
        if (opProperty.isEmpty()) {
            return new ResponseEntity<>("Property not found", HttpStatus.NOT_FOUND);
        }
        Property property = opProperty.get();

        Review existingReview = reviewRepository.findReviewByUser(property, user);
        if (existingReview != null) {
            return new ResponseEntity<>("You have already added a review for this property", HttpStatus.BAD_REQUEST);
        }

        review.setProperty(property);
        review.setPropertyUser(user);
        reviewRepository.save(review);

        return new ResponseEntity<>("Review added successfully", HttpStatus.OK);


    }
    @GetMapping("/userReviews")
    public ResponseEntity<List<Review>> getUserReview (@AuthenticationPrincipal PropertyUser user){
        List<Review> reviews = reviewRepository.findBypropertyUser(user);
        return new ResponseEntity<>(reviews,HttpStatus.OK);

    }

}
