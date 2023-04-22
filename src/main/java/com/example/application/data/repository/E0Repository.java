package com.example.application.data.repository;

import com.example.application.data.entity.E0;
import com.example.application.data.entity.League;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface E0Repository extends JpaRepository<E0, League.PrimaryInfo> {
}
