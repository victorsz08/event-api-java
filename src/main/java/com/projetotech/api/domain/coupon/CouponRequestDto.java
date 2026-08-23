package com.projetotech.api.domain.coupon;

public record CouponRequestDto(String code, Integer discount, Long valid) {}
