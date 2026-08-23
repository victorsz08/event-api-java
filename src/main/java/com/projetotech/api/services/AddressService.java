package com.projetotech.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projetotech.api.domain.address.Address;
import com.projetotech.api.domain.event.Event;
import com.projetotech.api.domain.event.EventRequestDto;
import com.projetotech.api.repositories.AddressRepository;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;
    
    
    public Address create(EventRequestDto data, Event event) {
        Address newAddress = new Address();
        newAddress.setCity(data.city());
        newAddress.setUf(data.state());
        newAddress.setEvent(event);

        return addressRepository.save(newAddress);
    }
}
