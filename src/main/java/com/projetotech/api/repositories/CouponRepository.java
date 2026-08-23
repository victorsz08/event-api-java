package com.projetotech.api.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projetotech.api.domain.coupon.Coupon;

public interface CouponRepository extends  JpaRepository<Coupon, UUID> {

}
