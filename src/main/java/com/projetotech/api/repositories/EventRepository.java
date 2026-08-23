package com.projetotech.api.repositories;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.projetotech.api.domain.event.Event;

public interface EventRepository extends JpaRepository<Event, UUID> {

    @Query("SELECT e FROM Event e WHERE e.date >= :currentDate")
    public Page<Event> findUpComingEvents(@Param("currentDate") Date currentDate, Pageable pageable);

    @Query(
        "SELECT e FROM Event e " +
        "LEFT JOIN e.address a " +
        "WHERE e.date >= :currentDate AND " +
        "(:title IS NULL OR e.title LIKE %:title%) AND " +
        "(:city IS NULL OR a.city LIKE %:city%) AND " +
        "(:uf IS NULL OR a.uf LIKE %:uf%) AND " +
        "(:startDate IS NULL OR e.date >= :startDate) AND " +
        "(:endDate IS NULL OR e.date <= :endDate)"
    )
    public Page<Event> findFilteredEvents(
        @Param("currentDate") Date currentDate,
        @Param("title") String title,
        @Param("city") String city,
        @Param("uf") String uf,
        @Param("startDate") Date startDate,
        @Param("endDate") Date endDate,
        Pageable pageable
    );
}
