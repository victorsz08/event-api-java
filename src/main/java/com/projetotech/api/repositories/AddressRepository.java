package com.projetotech.api.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projetotech.api.domain.address.Address;

public interface AddressRepository extends  JpaRepository<Address, UUID> {

}
