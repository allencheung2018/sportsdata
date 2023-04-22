package com.example.application.data.repository;

import com.example.application.data.entity.E0;
import com.example.application.data.entity.League;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface E_0Repository extends JpaRepository<E0, League.PrimaryInfo> {

    @Query(value = "SELECT DISTINCT homeTeam FROM E0 WHERE date BETWEEN :begin AND :end")
    List<String> getTeamBetweenDate(Date begin, Date end);

}
