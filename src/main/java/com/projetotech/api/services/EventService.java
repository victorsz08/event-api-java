package com.projetotech.api.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.projetotech.api.domain.event.Event;
import com.projetotech.api.domain.event.EventRequestDto;
import com.projetotech.api.domain.event.EventResponseDto;
import com.projetotech.api.repositories.EventRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class EventService {

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3Client;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private AddressService addressService;
    
    public Event createEvent(EventRequestDto data) {
        String imgUrl = null;

        if(data.image() != null) {
            imgUrl = this.uploadImage(data.image());
        }

        Event newEvent = new Event();
        newEvent.setTitle(data.title());
        newEvent.setDescription(data.description());
        newEvent.setDate(new Date(data.date()));
        newEvent.setEventUrl(data.eventUrl());
        newEvent.setRemote(data.remote());
        newEvent.setImageUrl(imgUrl);

        eventRepository.save(newEvent);

        if(!data.remote()) {
            this.addressService.create(data, newEvent);
        }

        return newEvent;
    }

    public List<EventResponseDto> getUpcomingEvents(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Event> eventsPage = this.eventRepository.findUpComingEvents(new Date(), pageable);

        return eventsPage.map(event -> new EventResponseDto(
            event.getId(),
            event.getTitle(),
            event.getDescription(),
            event.getDate(),
            event.getEventUrl(),
            event.getRemote(),
            "",
            "",
            event.getImageUrl()
        )).stream().toList();
    }

    @SuppressWarnings("UseSpecificCatch")
    private String uploadImage(MultipartFile multipartFile) {
        String fileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();

        try {
            File file = this.convertMultiPartToFile(multipartFile);
            s3Client.putObject(bucketName, fileName, file);
            file.delete();
            return s3Client.getUrl(bucketName, fileName).toString();
        } catch (Exception e) {
            System.out.println("Error uploading file" + e.getMessage());
            return null;
        }
    };

    @SuppressWarnings("ConvertToTryWithResources")
    private File convertMultiPartToFile(MultipartFile multipartFile) throws IOException {
        File convFile = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(multipartFile.getBytes());
        fos.close();
        return convFile;
    }
}
