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

    @Query("SELECT DISTINCT homeTeam FROM D1 WHERE date BETWEEN :begin AND :end")
    @Cacheable("teamBetweenDate_D1")
    List<String> getTeamBetweenDate(Date begin, Date end);

    @Query("SELECT COUNT(*) FROM D1 WHERE (homeTeam =:name OR awayTeam =:name) AND date BETWEEN :begin AND :end")
    @Cacheable("countGames_D1")
    int getCountGames(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM D1 WHERE homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHost_D1")
    int getCountHost(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM D1 WHERE (ftHomeGoal - ftAwayGoal)>0 AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHostWin_D1")
    int getCountHostWin(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM D1 WHERE ftHomeGoal = ftAwayGoal AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHostDraw_D1")
    int getCountHostDraw(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM D1 WHERE ahCurrentHome < 0 AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHostUp_D1")
    int getCountHostUp(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM D1 WHERE (ftHomeGoal - ftAwayGoal + ahCurrentHome)>0 AND ahCurrentHome < 0 " +
            "AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHostUpWin_D1")
    int getCountHostUpWin(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM D1 WHERE (ftHomeGoal - ftAwayGoal + ahCurrentHome)=0 AND ahCurrentHome < 0 " +
            "AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHostUpDraw_D1")
    int getCountHostUpDraw(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM D1 WHERE ahCurrentHome > 0 AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHostDown_D1")
    int getCountHostDown(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM D1 WHERE (ftHomeGoal - ftAwayGoal + ahCurrentHome) > 0 AND ahCurrentHome > 0 " +
            "AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHostDownWin_D1")
    int getCountHostDownWin(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM D1 WHERE (ftHomeGoal - ftAwayGoal + ahCurrentHome) = 0 AND ahCurrentHome > 0 " +
            "AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHostDownDraw_D1")
    int getCountHostDownDraw(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM D1 WHERE ahCurrentHome = 0 " +
            "AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHostTie_D1")
    int getCountHostTie(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM D1 WHERE ftHomeGoal > ftAwayGoal AND ahCurrentHome = 0 " +
            "AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHosTieWin_D1")
    int getCountHostTieWin(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM D1 WHERE awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAway_D1")
    int getCountAway(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM D1 WHERE ftAwayGoal > ftHomeGoal AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayWin_D1")
    int getCountAwayWin(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM D1 WHERE ftAwayGoal = ftHomeGoal AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayDraw_D1")
    int getCountAwayDraw(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM D1 WHERE ahCurrentHome > 0 AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayUp_D1")
    int getCountAwayUp(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM D1 WHERE (ftAwayGoal - ftHomeGoal - ahCurrentHome) > 0  AND ahCurrentHome > 0 " +
            "AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayUpWin_D1")
    int getCountAwayUpWin(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM D1 WHERE (ftAwayGoal - ftHomeGoal - ahCurrentHome) = 0  AND ahCurrentHome > 0 " +
            "AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayUpDraw_D1")
    int getCountAwayUpDraw(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM D1 WHERE ahCurrentHome < 0 AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayDown_D1")
    int getCountAwayDown(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM D1 WHERE (ftAwayGoal - ftHomeGoal - ahCurrentHome) > 0 AND ahCurrentHome < 0 " +
            "AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayDownWin_D1")
    int getCountAwayDownWin(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM D1 WHERE (ftAwayGoal - ftHomeGoal - ahCurrentHome) = 0 AND ahCurrentHome < 0 " +
            "AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayDownDraw_D1")
    int getCountAwayDownDraw(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM D1 WHERE ahCurrentHome = 0 AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayTie_D1")
    int getCountAwayTie(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM D1 WHERE ftAwayGoal > ftHomeGoal AND ahCurrentHome = 0 " +
            "AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayTieWin_D1")
    int getCountAwayTieWin(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM D1 WHERE ahCurrentHome = :ah AND date BETWEEN :begin AND :end")
    @Cacheable("AHCount_D1")
    int getAHCount(double ah, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM D1 WHERE ftHomeGoal > ftAwayGoal AND ahCurrentHome = :ah AND date BETWEEN :begin AND :end")
    @Cacheable("AHCountHostWin_D1")
    int getAHCountHostWin(double ah, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM D1 WHERE ftHomeGoal - ftAwayGoal = 2 AND ahCurrentHome = :ah " +
            "AND date BETWEEN :begin AND :end")
    @Cacheable("AHCountHostWin2Ball_D1")
    int getAHCountHostWin2Ball(double ah, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM D1 WHERE ftHomeGoal - ftAwayGoal = 1 AND ahCurrentHome = :ah " +
            "AND date BETWEEN :begin AND :end")
    @Cacheable("AHCountHostWin1Ball_D1")
    int getAHCountHostWin1Ball(double ah, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM D1 WHERE ftAwayGoal - ftHomeGoal = 0 AND ahCurrentHome = :ah " +
            "AND date BETWEEN :begin AND :end")
    @Cacheable("AHCountDraw_D1")
    int getAHCountDraw(double ah, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM D1 WHERE ftAwayGoal - ftHomeGoal > 0 AND ahCurrentHome = :ah " +
            "AND date BETWEEN :begin AND :end")
    @Cacheable("AHCountAwayWin_D1")
    int getAHCountAwayWin(double ah, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM D1 WHERE ftAwayGoal - ftHomeGoal = 1 AND ahCurrentHome = :ah " +
            "AND date BETWEEN :begin AND :end")
    @Cacheable("AHCountAwayWin1Ball_D1")
    int getAHCountAwayWin1Ball(double ah, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM D1 WHERE ftAwayGoal - ftHomeGoal = 2 AND ahCurrentHome = :ah " +
            "AND date BETWEEN :begin AND :end")
    @Cacheable("AHCountAwayWin2Ball_D1")
    int getAHCountAwayWin2Ball(double ah, Date begin, Date end);

    @Query("SELECT date, homeTeam, awayTeam, ftHomeGoal, ftAwayGoal, ahCurrentHome, ahHome FROM D1 " +
            "WHERE date BETWEEN :begin AND :end")
    @Cacheable("GamesByDate_D1")
    List<D1> getGamesByDate(Date begin, Date end);

    @Query("SELECT date, homeTeam, awayTeam, ftHomeGoal, ftAwayGoal, ahCurrentHome, ahHome FROM D1 " +
            "WHERE ahCurrentHome = :ah AND date BETWEEN :begin AND :end")
    @Cacheable("GamesByAHCh_D1")
    List<D1> getGamesByAHCh(double ah, Date begin, Date end);

    @Query("SELECT  date, homeTeam, awayTeam, ftHomeGoal, ftAwayGoal, ahCurrentHome, ahHome FROM D1 " +
            "WHERE (homeTeam = :team OR awayTeam = :team) AND date BETWEEN :begin AND :end")
    @Cacheable("GamesByTeam_D1")
    List<D1> getGamesByTeam(String team, Date begin, Date end);

    @Query("SELECT  date, homeTeam, awayTeam, ftHomeGoal, ftAwayGoal, ahCurrentHome, ahHome  FROM D1 " +
            "WHERE (homeTeam = :team OR awayTeam = :team) AND ahCurrentHome = :ah " +
            "AND date BETWEEN :begin AND :end")
    @Cacheable("GamesByTeamAndAHCh_D1")
    List<D1> getGamesByTeamAndAHCh(String team, double ah, Date begin, Date end);
}
