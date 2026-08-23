package com.projetotech.api.domain.event;

import java.util.Date;
import java.util.UUID;

public record EventResponseDto(UUID id, String title, String description, Date date, String eventUrl, Boolean remote, String city, String state, String imageUrl) {}
