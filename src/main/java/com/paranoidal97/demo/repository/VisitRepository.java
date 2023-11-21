package com.paranoidal97.demo.repository;

import com.paranoidal97.demo.model.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface VisitRepository extends JpaRepository<Visit, Long> {

    @Query("SELECT v FROM Visit v WHERE v.patient IS NULL AND v.startTime < :now")
    List<Visit> findAllOutdated(@Param("now") LocalDateTime now);

    @Query("SELECT COUNT(v) > 0 FROM Visit v WHERE v.doctor.id = :doctorId AND ((v.startTime <= :endTime AND v.endTime >= :startTime) OR (v.startTime >= :startTime AND v.startTime < :endTime))")
    List<Visit> findOverlapping(LocalDateTime startTime, LocalDateTime endTime, Long doctorId);
}
