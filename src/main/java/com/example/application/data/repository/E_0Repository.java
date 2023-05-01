package com.example.application.data.repository;

import com.example.application.data.entity.E0;
import com.example.application.data.entity.GameInfo;
import com.example.application.data.entity.League;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface E_0Repository extends JpaRepository<E0, League.PrimaryInfo> {

    @Query("SELECT DISTINCT homeTeam FROM E0 WHERE date BETWEEN :begin AND :end")
    @Cacheable("teamBetweenDate_E0")
    List<String> getTeamBetweenDate(Date begin, Date end);

    @Query("SELECT COUNT(*) FROM E0 WHERE (homeTeam =:name OR awayTeam =:name) AND date BETWEEN :begin AND :end")
    @Cacheable("countGames_E0")
    int getCountGames(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM E0 WHERE homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHost_E0")
    int getCountHost(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM E0 WHERE (ftHomeGoal - ftAwayGoal)>0 AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHostWin_E0")
    int getCountHostWin(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM E0 WHERE ftHomeGoal = ftAwayGoal AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHostDraw_E0")
    int getCountHostDraw(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM E0 WHERE ahCurrentHome < 0 AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHostUp_E0")
    int getCountHostUp(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM E0 WHERE (ftHomeGoal - ftAwayGoal + ahCurrentHome)>0 AND ahCurrentHome < 0 " +
            "AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHostUpWin_E0")
    int getCountHostUpWin(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM E0 WHERE (ftHomeGoal - ftAwayGoal + ahCurrentHome)=0 AND ahCurrentHome < 0 " +
            "AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHostUpDraw_E0")
    int getCountHostUpDraw(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM E0 WHERE ahCurrentHome > 0 AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHostDown_E0")
    int getCountHostDown(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM E0 WHERE (ftHomeGoal - ftAwayGoal + ahCurrentHome) > 0 AND ahCurrentHome > 0 " +
            "AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHostDownWin_E0")
    int getCountHostDownWin(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM E0 WHERE (ftHomeGoal - ftAwayGoal + ahCurrentHome) = 0 AND ahCurrentHome > 0 " +
            "AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHostDownDraw_E0")
    int getCountHostDownDraw(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM E0 WHERE ahCurrentHome = 0 " +
            "AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHostTie_E0")
    int getCountHostTie(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM E0 WHERE ftHomeGoal > ftAwayGoal AND ahCurrentHome = 0 " +
            "AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHosTieWin_E0")
    int getCountHostTieWin(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM E0 WHERE awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAway_E0")
    int getCountAway(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM E0 WHERE ftAwayGoal > ftHomeGoal AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayWin_E0")
    int getCountAwayWin(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM E0 WHERE ftAwayGoal = ftHomeGoal AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayDraw_E0")
    int getCountAwayDraw(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM E0 WHERE ahCurrentHome > 0 AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayUp_E0")
    int getCountAwayUp(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM E0 WHERE (ftAwayGoal - ftHomeGoal - ahCurrentHome) > 0  AND ahCurrentHome > 0 " +
            "AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayUpWin_E0")
    int getCountAwayUpWin(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM E0 WHERE (ftAwayGoal - ftHomeGoal - ahCurrentHome) = 0  AND ahCurrentHome > 0 " +
            "AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayUpDraw_E0")
    int getCountAwayUpDraw(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM E0 WHERE ahCurrentHome < 0 AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayDown_E0")
    int getCountAwayDown(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM E0 WHERE (ftAwayGoal - ftHomeGoal - ahCurrentHome) > 0 AND ahCurrentHome < 0 " +
            "AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayDownWin_E0")
    int getCountAwayDownWin(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM E0 WHERE (ftAwayGoal - ftHomeGoal - ahCurrentHome) = 0 AND ahCurrentHome < 0 " +
            "AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayDownDraw_E0")
    int getCountAwayDownDraw(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM E0 WHERE ahCurrentHome = 0 AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayTie_E0")
    int getCountAwayTie(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM E0 WHERE ftAwayGoal > ftHomeGoal AND ahCurrentHome = 0 " +
            "AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayTieWin_E0")
    int getCountAwayTieWin(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM E0 WHERE ahCurrentHome = :ah AND date BETWEEN :begin AND :end")
    @Cacheable("AHCount_E0")
    int getAHCount(double ah, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM E0 WHERE ftHomeGoal > ftAwayGoal AND ahCurrentHome = :ah AND date BETWEEN :begin AND :end")
    @Cacheable("AHCountHostWin_E0")
    int getAHCountHostWin(double ah, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM E0 WHERE ftHomeGoal - ftAwayGoal = 2 AND ahCurrentHome = :ah " +
            "AND date BETWEEN :begin AND :end")
    @Cacheable("AHCountHostWin2Ball_E0")
    int getAHCountHostWin2Ball(double ah, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM E0 WHERE ftHomeGoal - ftAwayGoal = 1 AND ahCurrentHome = :ah " +
            "AND date BETWEEN :begin AND :end")
    @Cacheable("AHCountHostWin1Ball_E0")
    int getAHCountHostWin1Ball(double ah, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM E0 WHERE ftAwayGoal - ftHomeGoal = 0 AND ahCurrentHome = :ah " +
            "AND date BETWEEN :begin AND :end")
    @Cacheable("AHCountDraw_E0")
    int getAHCountDraw(double ah, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM E0 WHERE ftAwayGoal - ftHomeGoal > 0 AND ahCurrentHome = :ah " +
            "AND date BETWEEN :begin AND :end")
    @Cacheable("AHCountAwayWin_E0")
    int getAHCountAwayWin(double ah, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM E0 WHERE ftAwayGoal - ftHomeGoal = 1 AND ahCurrentHome = :ah " +
            "AND date BETWEEN :begin AND :end")
    @Cacheable("AHCountAwayWin1Ball_E0")
    int getAHCountAwayWin1Ball(double ah, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM E0 WHERE ftAwayGoal - ftHomeGoal = 2 AND ahCurrentHome = :ah " +
            "AND date BETWEEN :begin AND :end")
    @Cacheable("AHCountAwayWin2Ball_E0")
    int getAHCountAwayWin2Ball(double ah, Date begin, Date end);

    @Query("SELECT new com.example.application.data.entity.GameInfo(date, homeTeam, awayTeam, ftHomeGoal, " +
            "ftAwayGoal, ahCurrentHome, ahHome) FROM E0 WHERE date BETWEEN :begin AND :end ORDER BY date DESC")
    @Cacheable("GamesByDate_E0")
    List<GameInfo> getGamesByDate(Date begin, Date end);

    @Query("SELECT new com.example.application.data.entity.GameInfo(date, homeTeam, awayTeam, ftHomeGoal, " +
            "ftAwayGoal, ahCurrentHome, ahHome) FROM E0 " +
            "WHERE ahCurrentHome = :ah AND date BETWEEN :begin AND :end ORDER BY date DESC")
    @Cacheable("GamesByAHCh_E0")
    List<GameInfo> getGamesByAHCh(double ah, Date begin, Date end);

    @Query("SELECT new com.example.application.data.entity.GameInfo(date, homeTeam, awayTeam, ftHomeGoal, " +
            "ftAwayGoal, ahCurrentHome, ahHome) FROM E0 " +
            "WHERE (homeTeam = :team OR awayTeam = :team) AND date BETWEEN :begin AND :end ORDER BY date DESC")
    @Cacheable("GamesByTeam_E0")
    List<GameInfo> getGamesByTeam(String team, Date begin, Date end);

    @Query("SELECT new com.example.application.data.entity.GameInfo(date, homeTeam, awayTeam, ftHomeGoal, " +
            "ftAwayGoal, ahCurrentHome, ahHome) FROM E0 " +
            "WHERE (homeTeam = :team OR awayTeam = :team) AND ahCurrentHome = :ah AND date BETWEEN :begin AND :end "
            + "ORDER BY date DESC")
    @Cacheable("GamesByTeamAndAHCh_E0")
    List<GameInfo> getGamesByTeamAndAHCh(String team, double ah, Date begin, Date end);
}
