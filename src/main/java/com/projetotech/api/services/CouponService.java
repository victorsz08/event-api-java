package com.projetotech.api.services;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projetotech.api.domain.coupon.Coupon;
import com.projetotech.api.domain.coupon.CouponRequestDto;
import com.projetotech.api.domain.event.Event;
import com.projetotech.api.repositories.CouponRepository;
import com.projetotech.api.repositories.EventRepository;

@Service
public class CouponService {

    @Autowired
    private EventRepository eventRepository;
    
    @Autowired
    private CouponRepository couponRepository;

    public Coupon create(UUID eventId, CouponRequestDto data) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"));

        Coupon newCoupon = new Coupon();
        newCoupon.setCode(data.code());
        newCoupon.setDiscount(data.discount());
        newCoupon.setValid(new Date(data.valid()));
        newCoupon.setEvent(event);

        return couponRepository.save(newCoupon);
    }
}
