package com.example.application.data.repository;

import com.example.application.data.entity.GameInfo;
import com.example.application.data.entity.League;
import com.example.application.data.entity.SWE;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SWERepository extends JpaRepository<SWE, League.PrimaryInfo> {

    @Query("SELECT " +
            "SUM(CASE WHEN ahCurrentHome IS NOT NULL THEN 1 ELSE 0 END) AS totalMatch, " +
            "SUM(CASE WHEN ftHomeGoal - ftAwayGoal = 2 THEN 1 ELSE 0 END) AS hostWin2Ball, " +
            "SUM(CASE WHEN ftHomeGoal - ftAwayGoal = 1 THEN 1 ELSE 0 END) AS hostWiSWEBall, " +
            "SUM(CASE WHEN ftHomeGoal - ftAwayGoal > 0 THEN 1 ELSE 0 END) AS hostWin, " +
            "SUM(CASE WHEN ftHomeGoal - ftAwayGoal = 0 THEN 1 ELSE 0 END) AS draw, " +
            "SUM(CASE WHEN ftAwayGoal - ftHomeGoal > 0 THEN 1 ELSE 0 END) AS awayWin, " +
            "SUM(CASE WHEN ftAwayGoal - ftHomeGoal = 1 THEN 1 ELSE 0 END) AS awayWiSWEBall, " +
            "SUM(CASE WHEN ftAwayGoal - ftHomeGoal = 2 THEN 1 ELSE 0 END) AS awayWin2Ball " +
            "FROM SWE WHERE ahCurrentHome =:ahch AND date BETWEEN :begin AND :end")
    @Cacheable("teamGameInfoByAHCh_SWE")
    Object getGameInfoByAHCh(double ahch, Date begin, Date end);
    
    @Query("SELECT DISTINCT homeTeam FROM SWE WHERE date BETWEEN :begin AND :end")
    @Cacheable("teamBetweenDate_SWE")
    List<String> getTeamBetweenDate(Date begin, Date end);

    @Query("SELECT COUNT(*) FROM SWE WHERE (homeTeam =:name OR awayTeam =:name) AND date BETWEEN :begin AND :end")
    @Cacheable("countGames_SWE")
    int getCountGames(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM SWE WHERE homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHost_SWE")
    int getCountHost(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM SWE WHERE (ftHomeGoal - ftAwayGoal)>0 AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHostWin_SWE")
    int getCountHostWin(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM SWE WHERE ftHomeGoal = ftAwayGoal AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHostDraw_SWE")
    int getCountHostDraw(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM SWE WHERE ahCurrentHome < 0 AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHostUp_SWE")
    int getCountHostUp(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM SWE WHERE (ftHomeGoal - ftAwayGoal + ahCurrentHome)>0 AND ahCurrentHome < 0 " +
            "AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHostUpWin_SWE")
    int getCountHostUpWin(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM SWE WHERE (ftHomeGoal - ftAwayGoal + ahCurrentHome)=0 AND ahCurrentHome < 0 " +
            "AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHostUpDraw_SWE")
    int getCountHostUpDraw(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM SWE WHERE ahCurrentHome > 0 AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHostDown_SWE")
    int getCountHostDown(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM SWE WHERE (ftHomeGoal - ftAwayGoal + ahCurrentHome) > 0 AND ahCurrentHome > 0 " +
            "AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHostDownWin_SWE")
    int getCountHostDownWin(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM SWE WHERE (ftHomeGoal - ftAwayGoal + ahCurrentHome) = 0 AND ahCurrentHome > 0 " +
            "AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHostDownDraw_SWE")
    int getCountHostDownDraw(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM SWE WHERE ahCurrentHome = 0 " +
            "AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHostTie_SWE")
    int getCountHostTie(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM SWE WHERE ftHomeGoal > ftAwayGoal AND ahCurrentHome = 0 " +
            "AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHosTieWin_SWE")
    int getCountHostTieWin(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM SWE WHERE awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAway_SWE")
    int getCountAway(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM SWE WHERE ftAwayGoal > ftHomeGoal AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayWin_SWE")
    int getCountAwayWin(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM SWE WHERE ftAwayGoal = ftHomeGoal AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayDraw_SWE")
    int getCountAwayDraw(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM SWE WHERE ahCurrentHome > 0 AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayUp_SWE")
    int getCountAwayUp(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM SWE WHERE (ftAwayGoal - ftHomeGoal - ahCurrentHome) > 0  AND ahCurrentHome > 0 " +
            "AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayUpWin_SWE")
    int getCountAwayUpWin(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM SWE WHERE (ftAwayGoal - ftHomeGoal - ahCurrentHome) = 0  AND ahCurrentHome > 0 " +
            "AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayUpDraw_SWE")
    int getCountAwayUpDraw(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM SWE WHERE ahCurrentHome < 0 AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayDown_SWE")
    int getCountAwayDown(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM SWE WHERE (ftAwayGoal - ftHomeGoal - ahCurrentHome) > 0 AND ahCurrentHome < 0 " +
            "AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayDownWin_SWE")
    int getCountAwayDownWin(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM SWE WHERE (ftAwayGoal - ftHomeGoal - ahCurrentHome) = 0 AND ahCurrentHome < 0 " +
            "AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayDownDraw_SWE")
    int getCountAwayDownDraw(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM SWE WHERE ahCurrentHome = 0 AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayTie_SWE")
    int getCountAwayTie(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM SWE WHERE ftAwayGoal > ftHomeGoal AND ahCurrentHome = 0 " +
            "AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayTieWin_SWE")
    int getCountAwayTieWin(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM SWE WHERE ahCurrentHome = :ah AND date BETWEEN :begin AND :end")
    @Cacheable("AHCount_SWE")
    int getAHCount(double ah, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM SWE WHERE ftHomeGoal > ftAwayGoal AND ahCurrentHome = :ah AND date BETWEEN :begin AND :end")
    @Cacheable("AHCountHostWin_SWE")
    int getAHCountHostWin(double ah, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM SWE WHERE ftHomeGoal - ftAwayGoal = 2 AND ahCurrentHome = :ah " +
            "AND date BETWEEN :begin AND :end")
    @Cacheable("AHCountHostWin2Ball_SWE")
    int getAHCountHostWin2Ball(double ah, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM SWE WHERE ftHomeGoal - ftAwayGoal = 1 AND ahCurrentHome = :ah " +
            "AND date BETWEEN :begin AND :end")
    @Cacheable("AHCountHostWiSWEBall_SWE")
    int getAHCountHostWiSWEBall(double ah, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM SWE WHERE ftAwayGoal - ftHomeGoal = 0 AND ahCurrentHome = :ah " +
            "AND date BETWEEN :begin AND :end")
    @Cacheable("AHCountDraw_SWE")
    int getAHCountDraw(double ah, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM SWE WHERE ftAwayGoal - ftHomeGoal > 0 AND ahCurrentHome = :ah " +
            "AND date BETWEEN :begin AND :end")
    @Cacheable("AHCountAwayWin_SWE")
    int getAHCountAwayWin(double ah, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM SWE WHERE ftAwayGoal - ftHomeGoal = 1 AND ahCurrentHome = :ah " +
            "AND date BETWEEN :begin AND :end")
    @Cacheable("AHCountAwayWiSWEBall_SWE")
    int getAHCountAwayWiSWEBall(double ah, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM SWE WHERE ftAwayGoal - ftHomeGoal = 2 AND ahCurrentHome = :ah " +
            "AND date BETWEEN :begin AND :end")
    @Cacheable("AHCountAwayWin2Ball_SWE")
    int getAHCountAwayWin2Ball(double ah, Date begin, Date end);

    @Query("SELECT new com.example.application.data.entity.GameInfo(date, homeTeam, awayTeam, ftHomeGoal, " +
            "ftAwayGoal, ahCurrentHome, ahHome) FROM SWE WHERE date BETWEEN :begin AND :end ORDER BY date DESC")
    @Cacheable("GamesByDate_SWE")
    List<GameInfo> getGamesByDate(Date begin, Date end);

    @Query("SELECT new com.example.application.data.entity.GameInfo(date, homeTeam, awayTeam, ftHomeGoal, " +
            "ftAwayGoal, ahCurrentHome, ahHome) FROM SWE " +
            "WHERE ahCurrentHome = :ah AND date BETWEEN :begin AND :end ORDER BY date DESC")
    @Cacheable("GamesByAHCh_SWE")
    List<GameInfo> getGamesByAHCh(double ah, Date begin, Date end);

    @Query("SELECT new com.example.application.data.entity.GameInfo(date, homeTeam, awayTeam, ftHomeGoal, " +
            "ftAwayGoal, ahCurrentHome, ahHome) FROM SWE " +
            "WHERE (homeTeam = :team OR awayTeam = :team) AND date BETWEEN :begin AND :end ORDER BY date DESC")
    @Cacheable("GamesByTeam_SWE")
    List<GameInfo> getGamesByTeam(String team, Date begin, Date end);

    @Query("SELECT new com.example.application.data.entity.GameInfo(date, homeTeam, awayTeam, ftHomeGoal, " +
            "ftAwayGoal, ahCurrentHome, ahHome) FROM SWE " +
            "WHERE (homeTeam = :team OR awayTeam = :team) AND ahCurrentHome = :ah AND date BETWEEN :begin AND :end "
            + "ORDER BY date DESC")
    @Cacheable("GamesByTeamAndAHCh_SWE")
    List<GameInfo> getGamesByTeamAndAHCh(String team, double ah, Date begin, Date end);
}
