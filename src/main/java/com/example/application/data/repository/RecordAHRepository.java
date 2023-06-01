package com.example.application.data.repository;

import com.example.application.data.entity.RecordAH;
import com.example.application.data.entity.RecordInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RecordAHRepository extends JpaRepository<RecordAH, Long> {

    @Query("SELECT new com.example.application.data.entity.RecordInfo(gameDate, match, homeTeam, hostGoal, awayGoal, ah, ahBet, profit, direction) " +
            "FROM RecordAH WHERE ah =:ah AND gameDate BETWEEN :begin AND :end ORDER BY gameDate DESC")
    List<RecordInfo> getRecordByAh(float ah, Date begin, Date end);

    @Query("SELECT new com.example.application.data.entity.RecordInfo(gameDate, match, homeTeam, hostGoal, awayGoal, ah, ahBet, profit, direction) " +
            "FROM RecordAH WHERE gameDate BETWEEN :begin AND :end ORDER BY gameDate DESC")
    List<RecordInfo> getRecordByDate(Date begin, Date end);
}
