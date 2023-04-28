package com.example.application.data.repository;

import com.example.application.data.entity.D1;
import com.example.application.data.entity.League;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface D_1Repository extends JpaRepository<D1, League.PrimaryInfo> {

    @Query("SELECT DISTINCT homeTeam FROM D1 WHERE date BETWEEN ?1 AND ?2")
    List<String> getTeamBetweenDate( Date begin, Date end);

    @Query("SELECT COUNT(*) FROM D1 WHERE (homeTeam =:name OR awayTeam =:name) AND date BETWEEN :begin AND :end")
    @Cacheable("d1countgames")
    int getCountGames(String name, Date begin, Date end);
}
