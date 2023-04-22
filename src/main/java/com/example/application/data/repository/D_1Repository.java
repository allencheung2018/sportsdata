package com.example.application.data.repository;

import com.example.application.data.entity.E0;
import com.example.application.data.entity.League;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface D_1Repository extends JpaRepository<E0, League.PrimaryInfo> {

    @Query(value = "SELECT DISTINCT homeTeam FROM D1 WHERE date BETWEEN ?1 AND ?2")
    List<String> getTeamBetweenDate( Date begin, Date end);
}
