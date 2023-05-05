package com.example.application.data.repository;

import com.example.application.data.entity.GameInfo;
import com.example.application.data.entity.N1;
import com.example.application.data.entity.League;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface N_1Repository extends JpaRepository<N1, League.PrimaryInfo> {

    @Query("SELECT " +
            "SUM(CASE WHEN ahCurrentHome IS NOT NULL THEN 1 ELSE 0 END) AS totalMatch, " +
            "SUM(CASE WHEN ftHomeGoal - ftAwayGoal = 2 THEN 1 ELSE 0 END) AS hostWin2Ball, " +
            "SUM(CASE WHEN ftHomeGoal - ftAwayGoal = 1 THEN 1 ELSE 0 END) AS hostWin1Ball, " +
            "SUM(CASE WHEN ftHomeGoal - ftAwayGoal > 0 THEN 1 ELSE 0 END) AS hostWin, " +
            "SUM(CASE WHEN ftHomeGoal - ftAwayGoal = 0 THEN 1 ELSE 0 END) AS draw, " +
            "SUM(CASE WHEN ftAwayGoal - ftHomeGoal > 0 THEN 1 ELSE 0 END) AS awayWin, " +
            "SUM(CASE WHEN ftAwayGoal - ftHomeGoal = 1 THEN 1 ELSE 0 END) AS awayWin1Ball, " +
            "SUM(CASE WHEN ftAwayGoal - ftHomeGoal = 2 THEN 1 ELSE 0 END) AS awayWin2Ball " +
            "FROM N1 WHERE ahCurrentHome =:ahch AND date BETWEEN :begin AND :end")
    @Cacheable("teamGameInfoByAHCh_N1")
    Object getGameInfoByAHCh(double ahch, Date begin, Date end);
    
    @Query("SELECT DISTINCT homeTeam FROM N1 WHERE date BETWEEN :begin AND :end")
    @Cacheable("teamBetweenDate_N1")
    List<String> getTeamBetweenDate(Date begin, Date end);

    @Query("SELECT COUNT(*) FROM N1 WHERE (homeTeam =:name OR awayTeam =:name) AND date BETWEEN :begin AND :end")
    @Cacheable("countGames_N1")
    int getCountGames(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM N1 WHERE homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHost_N1")
    int getCountHost(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM N1 WHERE (ftHomeGoal - ftAwayGoal)>0 AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHostWin_N1")
    int getCountHostWin(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM N1 WHERE ftHomeGoal = ftAwayGoal AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHostDraw_N1")
    int getCountHostDraw(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM N1 WHERE ahCurrentHome < 0 AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHostUp_N1")
    int getCountHostUp(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM N1 WHERE (ftHomeGoal - ftAwayGoal + ahCurrentHome)>0 AND ahCurrentHome < 0 " +
            "AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHostUpWin_N1")
    int getCountHostUpWin(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM N1 WHERE (ftHomeGoal - ftAwayGoal + ahCurrentHome)=0 AND ahCurrentHome < 0 " +
            "AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHostUpDraw_N1")
    int getCountHostUpDraw(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM N1 WHERE ahCurrentHome > 0 AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHostDown_N1")
    int getCountHostDown(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM N1 WHERE (ftHomeGoal - ftAwayGoal + ahCurrentHome) > 0 AND ahCurrentHome > 0 " +
            "AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHostDownWin_N1")
    int getCountHostDownWin(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM N1 WHERE (ftHomeGoal - ftAwayGoal + ahCurrentHome) = 0 AND ahCurrentHome > 0 " +
            "AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHostDownDraw_N1")
    int getCountHostDownDraw(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM N1 WHERE ahCurrentHome = 0 " +
            "AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHostTie_N1")
    int getCountHostTie(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM N1 WHERE ftHomeGoal > ftAwayGoal AND ahCurrentHome = 0 " +
            "AND homeTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countHosTieWin_N1")
    int getCountHostTieWin(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM N1 WHERE awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAway_N1")
    int getCountAway(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM N1 WHERE ftAwayGoal > ftHomeGoal AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayWin_N1")
    int getCountAwayWin(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM N1 WHERE ftAwayGoal = ftHomeGoal AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayDraw_N1")
    int getCountAwayDraw(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM N1 WHERE ahCurrentHome > 0 AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayUp_N1")
    int getCountAwayUp(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM N1 WHERE (ftAwayGoal - ftHomeGoal - ahCurrentHome) > 0  AND ahCurrentHome > 0 " +
            "AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayUpWin_N1")
    int getCountAwayUpWin(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM N1 WHERE (ftAwayGoal - ftHomeGoal - ahCurrentHome) = 0  AND ahCurrentHome > 0 " +
            "AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayUpDraw_N1")
    int getCountAwayUpDraw(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM N1 WHERE ahCurrentHome < 0 AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayDown_N1")
    int getCountAwayDown(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM N1 WHERE (ftAwayGoal - ftHomeGoal - ahCurrentHome) > 0 AND ahCurrentHome < 0 " +
            "AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayDownWin_N1")
    int getCountAwayDownWin(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM N1 WHERE (ftAwayGoal - ftHomeGoal - ahCurrentHome) = 0 AND ahCurrentHome < 0 " +
            "AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayDownDraw_N1")
    int getCountAwayDownDraw(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM N1 WHERE ahCurrentHome = 0 AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayTie_N1")
    int getCountAwayTie(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM N1 WHERE ftAwayGoal > ftHomeGoal AND ahCurrentHome = 0 " +
            "AND awayTeam =:name AND date BETWEEN :begin AND :end")
    @Cacheable("countAwayTieWin_N1")
    int getCountAwayTieWin(String name, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM N1 WHERE ahCurrentHome = :ah AND date BETWEEN :begin AND :end")
    @Cacheable("AHCount_N1")
    int getAHCount(double ah, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM N1 WHERE ftHomeGoal > ftAwayGoal AND ahCurrentHome = :ah AND date BETWEEN :begin AND :end")
    @Cacheable("AHCountHostWin_N1")
    int getAHCountHostWin(double ah, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM N1 WHERE ftHomeGoal - ftAwayGoal = 2 AND ahCurrentHome = :ah " +
            "AND date BETWEEN :begin AND :end")
    @Cacheable("AHCountHostWin2Ball_N1")
    int getAHCountHostWin2Ball(double ah, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM N1 WHERE ftHomeGoal - ftAwayGoal = 1 AND ahCurrentHome = :ah " +
            "AND date BETWEEN :begin AND :end")
    @Cacheable("AHCountHostWin1Ball_N1")
    int getAHCountHostWin1Ball(double ah, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM N1 WHERE ftAwayGoal - ftHomeGoal = 0 AND ahCurrentHome = :ah " +
            "AND date BETWEEN :begin AND :end")
    @Cacheable("AHCountDraw_N1")
    int getAHCountDraw(double ah, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM N1 WHERE ftAwayGoal - ftHomeGoal > 0 AND ahCurrentHome = :ah " +
            "AND date BETWEEN :begin AND :end")
    @Cacheable("AHCountAwayWin_N1")
    int getAHCountAwayWin(double ah, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM N1 WHERE ftAwayGoal - ftHomeGoal = 1 AND ahCurrentHome = :ah " +
            "AND date BETWEEN :begin AND :end")
    @Cacheable("AHCountAwayWin1Ball_N1")
    int getAHCountAwayWin1Ball(double ah, Date begin, Date end);

    @Query("SELECT COUNT(*) FROM N1 WHERE ftAwayGoal - ftHomeGoal = 2 AND ahCurrentHome = :ah " +
            "AND date BETWEEN :begin AND :end")
    @Cacheable("AHCountAwayWin2Ball_N1")
    int getAHCountAwayWin2Ball(double ah, Date begin, Date end);

    @Query("SELECT new com.example.application.data.entity.GameInfo(date, homeTeam, awayTeam, ftHomeGoal, " +
            "ftAwayGoal, ahCurrentHome, ahHome) FROM N1 WHERE date BETWEEN :begin AND :end ORDER BY date DESC")
    @Cacheable("GamesByDate_N1")
    List<GameInfo> getGamesByDate(Date begin, Date end);

    @Query("SELECT new com.example.application.data.entity.GameInfo(date, homeTeam, awayTeam, ftHomeGoal, " +
            "ftAwayGoal, ahCurrentHome, ahHome) FROM N1 " +
            "WHERE ahCurrentHome = :ah AND date BETWEEN :begin AND :end ORDER BY date DESC")
    @Cacheable("GamesByAHCh_N1")
    List<GameInfo> getGamesByAHCh(double ah, Date begin, Date end);

    @Query("SELECT new com.example.application.data.entity.GameInfo(date, homeTeam, awayTeam, ftHomeGoal, " +
            "ftAwayGoal, ahCurrentHome, ahHome) FROM N1 " +
            "WHERE (homeTeam = :team OR awayTeam = :team) AND date BETWEEN :begin AND :end ORDER BY date DESC")
    @Cacheable("GamesByTeam_N1")
    List<GameInfo> getGamesByTeam(String team, Date begin, Date end);

    @Query("SELECT new com.example.application.data.entity.GameInfo(date, homeTeam, awayTeam, ftHomeGoal, " +
            "ftAwayGoal, ahCurrentHome, ahHome) FROM N1 " +
            "WHERE (homeTeam = :team OR awayTeam = :team) AND ahCurrentHome = :ah AND date BETWEEN :begin AND :end "
            + "ORDER BY date DESC")
    @Cacheable("GamesByTeamAndAHCh_N1")
    List<GameInfo> getGamesByTeamAndAHCh(String team, double ah, Date begin, Date end);
}
