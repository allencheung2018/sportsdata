package com.example.application.data.repository;

import com.example.application.data.entity.F2;
import com.example.application.data.entity.GameInfo;
import com.example.application.data.entity.League;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface F_2Repository extends JpaRepository<F2, League.PrimaryInfo> {

    @Query("SELECT " +
            "SUM(CASE WHEN ahCurrentHome IS NOT NULL THEN 1 ELSE 0 END) AS totalMatch, " +
            "SUM(CASE WHEN ftHomeGoal - ftAwayGoal = 2 THEN 1 ELSE 0 END) AS hostWin2Ball, " +
            "SUM(CASE WHEN ftHomeGoal - ftAwayGoal = 1 THEN 1 ELSE 0 END) AS hostWin1Ball, " +
            "SUM(CASE WHEN ftHomeGoal - ftAwayGoal > 0 THEN 1 ELSE 0 END) AS hostWin, " +
            "SUM(CASE WHEN ftHomeGoal - ftAwayGoal = 0 THEN 1 ELSE 0 END) AS draw, " +
            "SUM(CASE WHEN ftAwayGoal - ftHomeGoal > 0 THEN 1 ELSE 0 END) AS awayWin, " +
            "SUM(CASE WHEN ftAwayGoal - ftHomeGoal = 1 THEN 1 ELSE 0 END) AS awayWin1Ball, " +
            "SUM(CASE WHEN ftAwayGoal - ftHomeGoal = 2 THEN 1 ELSE 0 END) AS awayWin2Ball " +
            "FROM F2 WHERE ahCurrentHome =:ahch AND date BETWEEN :begin AND :end")
    @Cacheable("teamGameInfoByAHCh_F2")
    Object getGameInfoByAHCh(double ahch, Date begin, Date end);
    
    @Query("SELECT DISTINCT homeTeam FROM F2 WHERE date BETWEEN :begin AND :end")
    @Cacheable("teamBetweenDate_F2")
    List<String> getTeamBetweenDate(Date begin, Date end);

    @Query("SELECT COUNT(*) FROM F2 WHERE (homeTeam =:name OR awayTeam =:name) AND date BETWEEN :begin AND :end")
    @Cacheable("countGames_F2")
    int getCountGames(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM F2 WHERE homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHost_F2")
    int getCountHost(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM F2 WHERE (ftHomeGoal - ftAwayGoal)>0 AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHostWin_F2")
    int getCountHostWin(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM F2 WHERE ftHomeGoal = ftAwayGoal AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHostDraw_F2")
    int getCountHostDraw(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM F2 WHERE ahCurrentHome < 0 AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHostUp_F2")
    int getCountHostUp(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM F2 WHERE (ftHomeGoal - ftAwayGoal + ahCurrentHome)>0 AND ahCurrentHome < 0 " +
            "AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHostUpWin_F2")
    int getCountHostUpWin(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM F2 WHERE (ftHomeGoal - ftAwayGoal + ahCurrentHome)=0 AND ahCurrentHome < 0 " +
            "AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHostUpDraw_F2")
    int getCountHostUpDraw(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM F2 WHERE ahCurrentHome > 0 AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHostDown_F2")
    int getCountHostDown(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM F2 WHERE (ftHomeGoal - ftAwayGoal + ahCurrentHome) > 0 AND ahCurrentHome > 0 " +
            "AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHostDownWin_F2")
    int getCountHostDownWin(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM F2 WHERE (ftHomeGoal - ftAwayGoal + ahCurrentHome) = 0 AND ahCurrentHome > 0 " +
            "AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHostDownDraw_F2")
    int getCountHostDownDraw(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM F2 WHERE ahCurrentHome = 0 " +
            "AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHostTie_F2")
    int getCountHostTie(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM F2 WHERE ftHomeGoal > ftAwayGoal AND ahCurrentHome = 0 " +
            "AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHosTieWin_F2")
    int getCountHostTieWin(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM F2 WHERE awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAway_F2")
    int getCountAway(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM F2 WHERE ftAwayGoal > ftHomeGoal AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayWin_F2")
    int getCountAwayWin(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM F2 WHERE ftAwayGoal = ftHomeGoal AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayDraw_F2")
    int getCountAwayDraw(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM F2 WHERE ahCurrentHome > 0 AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayUp_F2")
    int getCountAwayUp(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM F2 WHERE (ftAwayGoal - ftHomeGoal - ahCurrentHome) > 0  AND ahCurrentHome > 0 " +
            "AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayUpWin_F2")
    int getCountAwayUpWin(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM F2 WHERE (ftAwayGoal - ftHomeGoal - ahCurrentHome) = 0  AND ahCurrentHome > 0 " +
            "AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayUpDraw_F2")
    int getCountAwayUpDraw(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM F2 WHERE ahCurrentHome < 0 AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayDown_F2")
    int getCountAwayDown(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM F2 WHERE (ftAwayGoal - ftHomeGoal - ahCurrentHome) > 0 AND ahCurrentHome < 0 " +
            "AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayDownWin_F2")
    int getCountAwayDownWin(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM F2 WHERE (ftAwayGoal - ftHomeGoal - ahCurrentHome) = 0 AND ahCurrentHome < 0 " +
            "AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayDownDraw_F2")
    int getCountAwayDownDraw(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM F2 WHERE ahCurrentHome = 0 AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayTie_F2")
    int getCountAwayTie(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM F2 WHERE ftAwayGoal > ftHomeGoal AND ahCurrentHome = 0 " +
            "AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayTieWin_F2")
    int getCountAwayTieWin(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM F2 WHERE ahCurrentHome = :ah AND date BETWEEN :begin AND :end")
    @Cacheable("AHCount_F2")
    int getAHCount(double ah, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM F2 WHERE ftHomeGoal > ftAwayGoal AND ahCurrentHome = :ah AND date BETWEEN :begin AND :end")
    @Cacheable("AHCountHostWin_F2")
    int getAHCountHostWin(double ah, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM F2 WHERE ftHomeGoal - ftAwayGoal = 2 AND ahCurrentHome = :ah " +
            "AND date BETWEEN :begin AND :end")
    @Cacheable("AHCountHostWin2Ball_F2")
    int getAHCountHostWin2Ball(double ah, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM F2 WHERE ftHomeGoal - ftAwayGoal = 1 AND ahCurrentHome = :ah " +
            "AND date BETWEEN :begin AND :end")
    @Cacheable("AHCountHostWin1Ball_F2")
    int getAHCountHostWin1Ball(double ah, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM F2 WHERE ftAwayGoal - ftHomeGoal = 0 AND ahCurrentHome = :ah " +
            "AND date BETWEEN :begin AND :end")
    @Cacheable("AHCountDraw_F2")
    int getAHCountDraw(double ah, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM F2 WHERE ftAwayGoal - ftHomeGoal > 0 AND ahCurrentHome = :ah " +
            "AND date BETWEEN :begin AND :end")
    @Cacheable("AHCountAwayWin_F2")
    int getAHCountAwayWin(double ah, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM F2 WHERE ftAwayGoal - ftHomeGoal = 1 AND ahCurrentHome = :ah " +
            "AND date BETWEEN :begin AND :end")
    @Cacheable("AHCountAwayWin1Ball_F2")
    int getAHCountAwayWin1Ball(double ah, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM F2 WHERE ftAwayGoal - ftHomeGoal = 2 AND ahCurrentHome = :ah " +
            "AND date BETWEEN :begin AND :end")
    @Cacheable("AHCountAwayWin2Ball_F2")
    int getAHCountAwayWin2Ball(double ah, Date begin, Date end);

    @Query("SELECT new com.example.application.data.entity.GameInfo(date, homeTeam, awayTeam, ftHomeGoal, " +
            "ftAwayGoal, ahCurrentHome, ahHome) FROM F2 WHERE date BETWEEN :begin AND :end ORDER BY date DESC")
    @Cacheable("GamesByDate_F2")
    List<GameInfo> getGamesByDate(Date begin, Date end);

    @Query("SELECT new com.example.application.data.entity.GameInfo(date, homeTeam, awayTeam, ftHomeGoal, " +
            "ftAwayGoal, ahCurrentHome, ahHome) FROM F2 " +
            "WHERE ahCurrentHome = :ah AND date BETWEEN :begin AND :end ORDER BY date DESC")
    @Cacheable("GamesByAHCh_F2")
    List<GameInfo> getGamesByAHCh(double ah, Date begin, Date end);

    @Query("SELECT new com.example.application.data.entity.GameInfo(date, homeTeam, awayTeam, ftHomeGoal, " +
            "ftAwayGoal, ahCurrentHome, ahHome) FROM F2 " +
            "WHERE (homeTeam = :team OR awayTeam = :team) AND date BETWEEN :begin AND :end ORDER BY date DESC")
    @Cacheable("GamesByTeam_F2")
    List<GameInfo> getGamesByTeam(String team, Date begin, Date end);

    @Query("SELECT new com.example.application.data.entity.GameInfo(date, homeTeam, awayTeam, ftHomeGoal, " +
            "ftAwayGoal, ahCurrentHome, ahHome) FROM F2 " +
            "WHERE (homeTeam = :team OR awayTeam = :team) AND ahCurrentHome = :ah AND date BETWEEN :begin AND :end "
            + "ORDER BY date DESC")
    @Cacheable("GamesByTeamAndAHCh_F2")
    List<GameInfo> getGamesByTeamAndAHCh(String team, double ah, Date begin, Date end);
}
