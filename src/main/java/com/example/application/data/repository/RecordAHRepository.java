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

    @Query("SELECT new com.example.application.data.entity.RecordInfo(id, gameDate, betDate, league, homeTeam, hostGoal, " +
            "awayGoal, ah, ahBet, profit, pfGoal, direction, goalLine, betGL) " +
            "FROM RecordAH WHERE ah =:ah AND gameDate BETWEEN :begin AND :end ORDER BY gameDate DESC")
    List<RecordInfo> getRecordByAh(float ah, Date begin, Date end);

    @Query("SELECT new com.example.application.data.entity.RecordInfo(id, gameDate, betDate, league, homeTeam, hostGoal, " +
            "awayGoal, ah, ahBet, profit, pfGoal, direction, goalLine, betGL) " +
            "FROM RecordAH WHERE goalLine =:gl AND gameDate BETWEEN :begin AND :end ORDER BY gameDate DESC")
    List<RecordInfo> getRecordByGL(float gl, Date begin, Date end);

    @Query("SELECT new com.example.application.data.entity.RecordInfo(id, gameDate, betDate, league, homeTeam, hostGoal, " +
            "awayGoal, ah, ahBet, profit, pfGoal, direction, goalLine, betGL) " +
            "FROM RecordAH WHERE ah =:ah AND goalLine =:gl AND gameDate BETWEEN :begin AND :end ORDER BY gameDate DESC")
    List<RecordInfo> getRecordByAhAndGL(float ah, float gl, Date begin, Date end);

    @Query("SELECT new com.example.application.data.entity.RecordInfo(id, gameDate, betDate, league, homeTeam, hostGoal, " +
            "awayGoal, ah, ahBet, profit, pfGoal, direction, goalLine, betGL) " +
            "FROM RecordAH WHERE gameDate BETWEEN :begin AND :end ORDER BY gameDate DESC")
    List<RecordInfo> getRecordByDate(Date begin, Date end);
}
