package com.example.demo.jpa.repository;

import com.example.demo.jpa.DeliveryResultJpa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeliveryResultJpaRepository extends JpaRepository<DeliveryResultJpa, Long> {
    List<DeliveryResultJpa> findAllByDeliveryDateOrderByIdAsc(String date);
    List<DeliveryResultJpa> findAllByDeliveryDateBetweenOrderByIdDesc(String start, String end);
    List<DeliveryResultJpa> findAllByDeliveryDateBetweenAndIsAcceptOrderByIdDesc(String start, String end, Boolean isAccept);
}
