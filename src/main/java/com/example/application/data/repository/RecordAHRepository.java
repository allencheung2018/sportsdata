package com.example.application.data.repository;

import com.example.application.data.entity.GameInfo;
import com.example.application.data.entity.RecordAH;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RecordAHRepository extends JpaRepository<RecordAH, Long> {

    @Query("SELECT new com.example.application.data.entity.GameInfo(gameDate, homeTeam, hostGoal, awayGoal, ah) " +
            "FROM RecordAH WHERE ah =:ah AND gameDate BETWEEN :begin AND :end ORDER BY gameDate DESC")
    List<GameInfo> getRecordByAh(float ah, Date begin, Date end);
}
