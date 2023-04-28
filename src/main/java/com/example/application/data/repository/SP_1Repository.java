package com.example.application.data.repository;

import com.example.application.data.entity.League;
import com.example.application.data.entity.SP1;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SP_1Repository extends JpaRepository<SP1, League.PrimaryInfo> {

    @Query("SELECT DISTINCT homeTeam FROM SP1 WHERE date BETWEEN :begin AND :end")
    @Cacheable("teamBetweenDate_SP1")
    List<String> getTeamBetweenDate(Date begin, Date end);

    @Query("SELECT COUNT(*) FROM SP1 WHERE (homeTeam =:name OR awayTeam =:name) AND date BETWEEN :begin AND :end")
    @Cacheable("countGames_SP1")
    int getCountGames(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM SP1 WHERE homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHost_SP1")
    int getCountHost(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM SP1 WHERE (ftHomeGoal - ftAwayGoal)>0 AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHostWin_SP1")
    int getCountHostWin(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM SP1 WHERE ftHomeGoal = ftAwayGoal AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHostDraw_SP1")
    int getCountHostDraw(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM SP1 WHERE ahCurrentHome < 0 AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHostUp_SP1")
    int getCountHostUp(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM SP1 WHERE (ftHomeGoal - ftAwayGoal + ahCurrentHome)>0 AND ahCurrentHome < 0 " +
            "AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHostUpWin_SP1")
    int getCountHostUpWin(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM SP1 WHERE (ftHomeGoal - ftAwayGoal + ahCurrentHome)=0 AND ahCurrentHome < 0 " +
            "AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHostUpDraw_SP1")
    int getCountHostUpDraw(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM SP1 WHERE ahCurrentHome > 0 AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHostDown_SP1")
    int getCountHostDown(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM SP1 WHERE (ftHomeGoal - ftAwayGoal + ahCurrentHome) > 0 AND ahCurrentHome > 0 " +
            "AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHostDownWin_SP1")
    int getCountHostDownWin(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM SP1 WHERE (ftHomeGoal - ftAwayGoal + ahCurrentHome) > 0 AND ahCurrentHome > 0 " +
            "AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHostDownDraw_SP1")
    int getCountHostDownDraw(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM SP1 WHERE ahCurrentHome = 0 " +
            "AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHostTie_SP1")
    int getCountHostTie(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM SP1 WHERE ftHomeGoal > ftAwayGoal AND ahCurrentHome = 0 " +
            "AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHosTieWin_SP1")
    int getCountHostTieWin(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM SP1 WHERE awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAway_SP1")
    int getCountAway(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM SP1 WHERE ftAwayGoal > ftHomeGoal AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayWin_SP1")
    int getCountAwayWin(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM SP1 WHERE ftAwayGoal = ftHomeGoal AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayDraw_SP1")
    int getCountAwayDraw(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM SP1 WHERE ahCurrentHome > 0 AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayUp_SP1")
    int getCountAwayUp(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM SP1 WHERE (ftAwayGoal - ftHomeGoal - ahCurrentHome) > 0  AND ahCurrentHome > 0 " +
            "AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayUpWin_SP1")
    int getCountAwayUpWin(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM SP1 WHERE (ftAwayGoal - ftHomeGoal - ahCurrentHome) = 0  AND ahCurrentHome > 0 " +
            "AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayUpDraw_SP1")
    int getCountAwayUpDraw(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM SP1 WHERE ahCurrentHome < 0 AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayDown_SP1")
    int getCountAwayDown(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM SP1 WHERE (ftAwayGoal - ftHomeGoal - ahCurrentHome) > 0 AND ahCurrentHome < 0 " +
            "AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayDownWin_SP1")
    int getCountAwayDownWin(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM SP1 WHERE (ftAwayGoal - ftHomeGoal - ahCurrentHome) = 0 AND ahCurrentHome < 0 " +
            "AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayDownDraw_SP1")
    int getCountAwayDownDraw(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM SP1 WHERE ahCurrentHome = 0 AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayTie_SP1")
    int getCountAwayTie(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM SP1 WHERE ftAwayGoal > ftHomeGoal AND ahCurrentHome = 0 " +
            "AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayTieWin_SP1")
    int getCountAwayTieWin(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM SP1 WHERE ahCurrentHome = :ah AND date BETWEEN :begin AND :end")
    @Cacheable("AHCount_SP1")
    int getAHCount(double ah, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM SP1 WHERE ftHomeGoal > ftAwayGoal AND ahCurrentHome = :ah AND date BETWEEN :begin AND :end")
    @Cacheable("AHCountHostWin_SP1")
    int getAHCountHostWin(double ah, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM SP1 WHERE ftHomeGoal - ftAwayGoal = 2 AND ahCurrentHome = :ah " +
            "AND date BETWEEN :begin AND :end")
    @Cacheable("AHCountHostWin2Ball_SP1")
    int getAHCountHostWin2Ball(double ah, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM SP1 WHERE ftHomeGoal - ftAwayGoal = 1 AND ahCurrentHome = :ah " +
            "AND date BETWEEN :begin AND :end")
    @Cacheable("AHCountHostWin1Ball_SP1")
    int getAHCountHostWin1Ball(double ah, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM SP1 WHERE ftAwayGoal - ftHomeGoal = 0 AND ahCurrentHome = :ah " +
            "AND date BETWEEN :begin AND :end")
    @Cacheable("AHCountDraw_SP1")
    int getAHCountDraw(double ah, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM SP1 WHERE ftAwayGoal - ftHomeGoal > 0 AND ahCurrentHome = :ah " +
            "AND date BETWEEN :begin AND :end")
    @Cacheable("AHCountAwayWin_SP1")
    int getAHCountAwayWin(double ah, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM SP1 WHERE ftAwayGoal - ftHomeGoal = 1 AND ahCurrentHome = :ah " +
            "AND date BETWEEN :begin AND :end")
    @Cacheable("AHCountAwayWin1Ball_SP1")
    int getAHCountAwayWin1Ball(double ah, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM SP1 WHERE ftAwayGoal - ftHomeGoal = 2 AND ahCurrentHome = :ah " +
            "AND date BETWEEN :begin AND :end")
    @Cacheable("AHCountAwayWin2Ball_SP1")
    int getAHCountAwayWin2Ball(double ah, Date begin, Date end);
}
