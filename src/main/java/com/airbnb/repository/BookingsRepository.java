package com.airbnb.repository;

import com.airbnb.entity.Bookings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingsRepository extends JpaRepository<Bookings, Long> {


}