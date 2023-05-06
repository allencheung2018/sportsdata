package com.example.application.data.repository;

import com.example.application.data.entity.E1;
import com.example.application.data.entity.GameInfo;
import com.example.application.data.entity.League;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface E_1Repository extends JpaRepository<E1, League.PrimaryInfo> {

    @Query("SELECT " +
            "SUM(CASE WHEN ahCurrentHome IS NOT NULL THEN 1 ELSE 0 END) AS totalMatch, " +
            "SUM(CASE WHEN ftHomeGoal - ftAwayGoal = 2 THEN 1 ELSE 0 END) AS hostWin2Ball, " +
            "SUM(CASE WHEN ftHomeGoal - ftAwayGoal = 1 THEN 1 ELSE 0 END) AS hostWin1Ball, " +
            "SUM(CASE WHEN ftHomeGoal - ftAwayGoal > 0 THEN 1 ELSE 0 END) AS hostWin, " +
            "SUM(CASE WHEN ftHomeGoal - ftAwayGoal = 0 THEN 1 ELSE 0 END) AS draw, " +
            "SUM(CASE WHEN ftAwayGoal - ftHomeGoal > 0 THEN 1 ELSE 0 END) AS awayWin, " +
            "SUM(CASE WHEN ftAwayGoal - ftHomeGoal = 1 THEN 1 ELSE 0 END) AS awayWin1Ball, " +
            "SUM(CASE WHEN ftAwayGoal - ftHomeGoal = 2 THEN 1 ELSE 0 END) AS awayWin2Ball " +
            "FROM E1 WHERE ahCurrentHome =:ahch AND date BETWEEN :begin AND :end")
    @Cacheable("teamGameInfoByAHCh_E1")
    Object getGameInfoByAHCh(double ahch, Date begin, Date end);
    
    @Query("SELECT DISTINCT homeTeam FROM E1 WHERE date BETWEEN :begin AND :end")
    @Cacheable("teamBetweenDate_E1")
    List<String> getTeamBetweenDate(Date begin, Date end);

    @Query("SELECT COUNT(*) FROM E1 WHERE (homeTeam =:name OR awayTeam =:name) AND date BETWEEN :begin AND :end")
    @Cacheable("countGames_E1")
    int getCountGames(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM E1 WHERE homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHost_E1")
    int getCountHost(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM E1 WHERE (ftHomeGoal - ftAwayGoal)>0 AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHostWin_E1")
    int getCountHostWin(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM E1 WHERE ftHomeGoal = ftAwayGoal AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHostDraw_E1")
    int getCountHostDraw(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM E1 WHERE ahCurrentHome < 0 AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHostUp_E1")
    int getCountHostUp(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM E1 WHERE (ftHomeGoal - ftAwayGoal + ahCurrentHome)>0 AND ahCurrentHome < 0 " +
            "AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHostUpWin_E1")
    int getCountHostUpWin(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM E1 WHERE (ftHomeGoal - ftAwayGoal + ahCurrentHome)=0 AND ahCurrentHome < 0 " +
            "AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHostUpDraw_E1")
    int getCountHostUpDraw(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM E1 WHERE ahCurrentHome > 0 AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHostDown_E1")
    int getCountHostDown(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM E1 WHERE (ftHomeGoal - ftAwayGoal + ahCurrentHome) > 0 AND ahCurrentHome > 0 " +
            "AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHostDownWin_E1")
    int getCountHostDownWin(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM E1 WHERE (ftHomeGoal - ftAwayGoal + ahCurrentHome) = 0 AND ahCurrentHome > 0 " +
            "AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHostDownDraw_E1")
    int getCountHostDownDraw(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM E1 WHERE ahCurrentHome = 0 " +
            "AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHostTie_E1")
    int getCountHostTie(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM E1 WHERE ftHomeGoal > ftAwayGoal AND ahCurrentHome = 0 " +
            "AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHosTieWin_E1")
    int getCountHostTieWin(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM E1 WHERE awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAway_E1")
    int getCountAway(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM E1 WHERE ftAwayGoal > ftHomeGoal AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayWin_E1")
    int getCountAwayWin(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM E1 WHERE ftAwayGoal = ftHomeGoal AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayDraw_E1")
    int getCountAwayDraw(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM E1 WHERE ahCurrentHome > 0 AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayUp_E1")
    int getCountAwayUp(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM E1 WHERE (ftAwayGoal - ftHomeGoal - ahCurrentHome) > 0  AND ahCurrentHome > 0 " +
            "AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayUpWin_E1")
    int getCountAwayUpWin(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM E1 WHERE (ftAwayGoal - ftHomeGoal - ahCurrentHome) = 0  AND ahCurrentHome > 0 " +
            "AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayUpDraw_E1")
    int getCountAwayUpDraw(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM E1 WHERE ahCurrentHome < 0 AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayDown_E1")
    int getCountAwayDown(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM E1 WHERE (ftAwayGoal - ftHomeGoal - ahCurrentHome) > 0 AND ahCurrentHome < 0 " +
            "AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayDownWin_E1")
    int getCountAwayDownWin(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM E1 WHERE (ftAwayGoal - ftHomeGoal - ahCurrentHome) = 0 AND ahCurrentHome < 0 " +
            "AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayDownDraw_E1")
    int getCountAwayDownDraw(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM E1 WHERE ahCurrentHome = 0 AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayTie_E1")
    int getCountAwayTie(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM E1 WHERE ftAwayGoal > ftHomeGoal AND ahCurrentHome = 0 " +
            "AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayTieWin_E1")
    int getCountAwayTieWin(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM E1 WHERE ahCurrentHome = :ah AND date BETWEEN :begin AND :end")
    @Cacheable("AHCount_E1")
    int getAHCount(double ah, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM E1 WHERE ftHomeGoal > ftAwayGoal AND ahCurrentHome = :ah AND date BETWEEN :begin AND :end")
    @Cacheable("AHCountHostWin_E1")
    int getAHCountHostWin(double ah, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM E1 WHERE ftHomeGoal - ftAwayGoal = 2 AND ahCurrentHome = :ah " +
            "AND date BETWEEN :begin AND :end")
    @Cacheable("AHCountHostWin2Ball_E1")
    int getAHCountHostWin2Ball(double ah, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM E1 WHERE ftHomeGoal - ftAwayGoal = 1 AND ahCurrentHome = :ah " +
            "AND date BETWEEN :begin AND :end")
    @Cacheable("AHCountHostWin1Ball_E1")
    int getAHCountHostWin1Ball(double ah, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM E1 WHERE ftAwayGoal - ftHomeGoal = 0 AND ahCurrentHome = :ah " +
            "AND date BETWEEN :begin AND :end")
    @Cacheable("AHCountDraw_E1")
    int getAHCountDraw(double ah, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM E1 WHERE ftAwayGoal - ftHomeGoal > 0 AND ahCurrentHome = :ah " +
            "AND date BETWEEN :begin AND :end")
    @Cacheable("AHCountAwayWin_E1")
    int getAHCountAwayWin(double ah, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM E1 WHERE ftAwayGoal - ftHomeGoal = 1 AND ahCurrentHome = :ah " +
            "AND date BETWEEN :begin AND :end")
    @Cacheable("AHCountAwayWin1Ball_E1")
    int getAHCountAwayWin1Ball(double ah, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM E1 WHERE ftAwayGoal - ftHomeGoal = 2 AND ahCurrentHome = :ah " +
            "AND date BETWEEN :begin AND :end")
    @Cacheable("AHCountAwayWin2Ball_E1")
    int getAHCountAwayWin2Ball(double ah, Date begin, Date end);

    @Query("SELECT new com.example.application.data.entity.GameInfo(date, homeTeam, awayTeam, ftHomeGoal, " +
            "ftAwayGoal, ahCurrentHome, ahHome) FROM E1 WHERE date BETWEEN :begin AND :end ORDER BY date DESC")
    @Cacheable("GamesByDate_E1")
    List<GameInfo> getGamesByDate(Date begin, Date end);

    @Query("SELECT new com.example.application.data.entity.GameInfo(date, homeTeam, awayTeam, ftHomeGoal, " +
            "ftAwayGoal, ahCurrentHome, ahHome) FROM E1 " +
            "WHERE ahCurrentHome = :ah AND date BETWEEN :begin AND :end ORDER BY date DESC")
    @Cacheable("GamesByAHCh_E1")
    List<GameInfo> getGamesByAHCh(double ah, Date begin, Date end);

    @Query("SELECT new com.example.application.data.entity.GameInfo(date, homeTeam, awayTeam, ftHomeGoal, " +
            "ftAwayGoal, ahCurrentHome, ahHome) FROM E1 " +
            "WHERE (homeTeam = :team OR awayTeam = :team) AND date BETWEEN :begin AND :end ORDER BY date DESC")
    @Cacheable("GamesByTeam_E1")
    List<GameInfo> getGamesByTeam(String team, Date begin, Date end);

    @Query("SELECT new com.example.application.data.entity.GameInfo(date, homeTeam, awayTeam, ftHomeGoal, " +
            "ftAwayGoal, ahCurrentHome, ahHome) FROM E1 " +
            "WHERE (homeTeam = :team OR awayTeam = :team) AND ahCurrentHome = :ah AND date BETWEEN :begin AND :end "
            + "ORDER BY date DESC")
    @Cacheable("GamesByTeamAndAHCh_E1")
    List<GameInfo> getGamesByTeamAndAHCh(String team, double ah, Date begin, Date end);
}
