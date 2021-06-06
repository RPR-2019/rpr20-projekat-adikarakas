package ba.unsa.etf.rpr.other;

import ba.unsa.etf.rpr.beans.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class LeagueDAO {
    private static LeagueDAO instance;
    private PreparedStatement findResultQuery;
    private PreparedStatement setIdForResultQuery;
    private PreparedStatement getStatsQuery;
    private PreparedStatement deleteFixtureQuery;
    private PreparedStatement addGoalQuery;
    private PreparedStatement getResultsQuery;
    private PreparedStatement addResultQuery;
    private PreparedStatement findFixtureQuery;
    private PreparedStatement getFixturesQuery;
    private PreparedStatement addFixtureQuery;
    private PreparedStatement findPlayersForClubQuery;
    private PreparedStatement editClubQuery;
    private PreparedStatement editPlayerQuery;
    private PreparedStatement editStatQuery;
    private PreparedStatement addPlayerQuery;
    private PreparedStatement setIdForPlayerQuery;
    private PreparedStatement addClubQuery;
    private PreparedStatement setIdForClubQuery;
    private PreparedStatement deletePlayersForClubQuery;
    private PreparedStatement deleteClubQuery;
    private PreparedStatement deletePlayerQuery;
    private PreparedStatement deleteAllGoalsQuery;
    private PreparedStatement deleteAllResultsQuery;
    private PreparedStatement deleteAllPlayersQuery;
    private PreparedStatement deleteAllClubsQuery;
    private PreparedStatement findClubQuery;
    private PreparedStatement findPlayerQuery;
    private PreparedStatement getClubsQuery;
    private PreparedStatement getPlayersQuery;
    private PreparedStatement getPlayerQuery;
    private PreparedStatement getClubQuery;
    private PreparedStatement captainQuery;
    private PreparedStatement deleteAllStatQuery;
    private PreparedStatement getResultQuery;
    private PreparedStatement forReportQuery;
    private PreparedStatement getGoalsQuery;
    private PreparedStatement addStatQuery;
    private PreparedStatement findStatQuery;
    private Connection conn;

    public static LeagueDAO getInstance() {
        if (instance==null) instance = new LeagueDAO();
        return instance;
    }

    public Connection getConn() {
        return conn;
    }

    private LeagueDAO ()  {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:league.db");
        } catch (SQLException e) {
            e.getMessage();
        }
        try {
            getClubQuery = conn.prepareStatement("SELECT * FROM clubs WHERE name=?");
        } catch (SQLException e) {
            regenerisiBazu();
            try {
                getClubQuery = conn.prepareStatement("SELECT * FROM clubs WHERE name=?");
            } catch (SQLException e1) {
                e1.getMessage();
            }
        }
        try {
            forReportQuery = conn.prepareStatement("SELECT * FROM goals where result=? AND club=?");

            deletePlayersForClubQuery = conn.prepareStatement("DELETE FROM players WHERE club=?");
            deleteClubQuery = conn.prepareStatement("DELETE FROM clubs WHERE name=?");
            deletePlayerQuery = conn.prepareStatement("DELETE FROM players WHERE id=?");
            deleteFixtureQuery = conn.prepareStatement("DELETE FROM fixtures WHERE home_team=? AND away_team=?");
            deleteAllStatQuery = conn.prepareStatement("DELETE FROM stats");
            deleteAllGoalsQuery = conn.prepareStatement("DELETE FROM goals");
            deleteAllResultsQuery = conn.prepareStatement("DELETE FROM results");
            deleteAllPlayersQuery = conn.prepareStatement("DELETE FROM players");
            deleteAllClubsQuery = conn.prepareStatement("DELETE FROM clubs");

            findClubQuery = conn.prepareStatement("SELECT * FROM clubs WHERE name=?");
            findPlayersForClubQuery = conn.prepareStatement("SELECT * FROM players WHERE club=?");
            findPlayerQuery = conn.prepareStatement("SELECT * FROM players WHERE id=?");
            findFixtureQuery = conn.prepareStatement("SELECT * FROM fixtures WHERE home_team=? AND away_team=?");
            findStatQuery = conn.prepareStatement("SELECT * FROM stats WHERE id=?");
            findResultQuery = conn.prepareStatement("SELECT * FROM results WHERE home_team=? AND away_team=?");

            getPlayersQuery = conn.prepareStatement("SELECT * FROM players");
            getClubsQuery = conn.prepareStatement("SELECT * FROM clubs");
            getFixturesQuery = conn.prepareStatement("SELECT * FROM fixtures");
            getResultsQuery = conn.prepareStatement("SELECT * FROM results");
            getGoalsQuery = conn.prepareStatement("SELECT * FROM goals");
            getStatsQuery = conn.prepareStatement("SELECT * FROM stats");

            getPlayerQuery = conn.prepareStatement("SELECT * FROM players WHERE id=?");
            getResultQuery = conn.prepareStatement("SELECT * FROM results WHERE id=?");

            addPlayerQuery = conn.prepareStatement("INSERT INTO players VALUES(?,?,?,?,?,?,?)");
            addClubQuery = conn.prepareStatement("INSERT INTO clubs VALUES(?,?,?,?,?,?)");
            addFixtureQuery = conn.prepareStatement("INSERT INTO fixtures VALUES(?,?)");
            addResultQuery = conn.prepareStatement("INSERT INTO results VALUES(?,?,?,?,?)");
            addGoalQuery = conn.prepareStatement("INSERT INTO goals VALUES(?,?,?,?,?,?,?,?)");
            addStatQuery = conn.prepareStatement("INSERT INTO stats VALUES(?,?,?)");

            setIdForPlayerQuery = conn.prepareStatement("SELECT MAX(id)+1 FROM players");
            setIdForResultQuery = conn.prepareStatement("SELECT MAX(id)+1 FROM results");

            editPlayerQuery = conn.prepareStatement("UPDATE players SET name=?, surname=?, date=?, nationality=?, club=?, position=? WHERE id=?");
            editClubQuery = conn.prepareStatement("UPDATE clubs SET nickname=?, stadium=?, mascot=?, manager=?, captain=? WHERE name=?");
            editStatQuery = conn.prepareStatement("UPDATE stats SET appearances=?, clean_sheets=? WHERE id=?");
        }
        catch (SQLException e) {
            e.getMessage();
        }
    }

    public ObservableList<Goal> homeTeamGoalsCollecting(Result r) {
        ObservableList<Goal> goals = FXCollections.observableArrayList();
        try {
            forReportQuery.setInt(1, r.getId());
            forReportQuery.setString(2, r.getHomeTeam().getName());
            ResultSet rs = forReportQuery.executeQuery();
            while (rs.next()) {
                goals.add(getGoalFromResultSet(rs));
            }
            return goals;
        }
        catch (SQLException e) {
            e.getMessage();
            return goals;
        }
    }

    public ObservableList<Goal> awayTeamGoalsCollecting(Result r) {
        ObservableList<Goal> goals = FXCollections.observableArrayList();
        try {
            forReportQuery.setInt(1, r.getId());
            forReportQuery.setString(2, r.getAwayTeam().getName());
            ResultSet rs = forReportQuery.executeQuery();
            while (rs.next()) {
                goals.add(getGoalFromResultSet(rs));
            }
            return goals;
        }
        catch(SQLException e) {
            e.getMessage();
            return goals;
        }
    }

    private Player getPlayerFromResultSet(ResultSet rs, Club c) throws SQLException {
        if (("Goalkeeper").equals(rs.getString(7))) {
            Player p = new Goalkeeper(rs.getString(2), rs.getString(3), LocalDate.parse(rs.getString(4)), rs.getString(5));
            p.setId(rs.getInt(1));
            p.setClub(c);
            return p;
        }
        else if (("Defender").equals(rs.getString(7))) {
            Player p = new Defender(rs.getString(2), rs.getString(3), LocalDate.parse(rs.getString(4)), rs.getString(5));
            p.setId(rs.getInt(1));
            p.setClub(c);
            return p;
        }
        else if (("Midfielder").equals(rs.getString(7))) {
            Player p = new Midfielder(rs.getString(2), rs.getString(3), LocalDate.parse(rs.getString(4)), rs.getString(5));
            p.setId(rs.getInt(1));
            p.setClub(c);
            return p;
        }
        else if (("Attacker").equals(rs.getString(7))) {
            Player p = new Attacker(rs.getString(2), rs.getString(3), LocalDate.parse(rs.getString(4)), rs.getString(5));
            p.setId(rs.getInt(1));
            p.setClub(c);
            return p;
        }
        return null;
    }

    private Club getClub(String name) {
        try {
            getClubQuery.setString(1, name);
            ResultSet rs = getClubQuery.executeQuery();
            if (!rs.next()) return null;
            return getClubFromResultSet(rs);
        } catch (SQLException e) {
            e.getMessage();
            return null;
        }
    }

    private Player getPlayer(int id, Club c) {
        try {
            getPlayerQuery.setInt(1, id);
            ResultSet rs = getPlayerQuery.executeQuery();
            if (!rs.next()) return null;
            return getPlayerFromResultSet(rs, c);
        } catch (SQLException e) {
            e.getMessage();
            return null;
        }
    }

    private Result getResult(int id) {
        try {
            getResultQuery.setInt(1, id);
            ResultSet rs = getResultQuery.executeQuery();
            if (!rs.next()) return null;
            return getResultFromResultSet(rs);
        }
        catch (SQLException e) {
            e.getMessage();
            return null;
        }
    }

    public Result findResult(Club c1, Club c2) {
        try{
            findResultQuery.setString(1, c1.getName());
            findResultQuery.setString(2, c2.getName());
            ResultSet rs = findResultQuery.executeQuery();
            if (!rs.next()) return null;
            return getResultFromResultSet(rs);
        }
        catch(SQLException e) {
            e.getMessage();
            return null;
        }
    }

    private Club getClubFromResultSet(ResultSet rs) throws SQLException {
        Club c = new Club(rs.getString(1)); // fino poredati
        c.setNickname(rs.getString(2));
        c.setStadium(rs.getString(3));
        c.setMascot(rs.getString(4));
        c.setManager(rs.getString(5));
        c.setCaptain(getPlayer(rs.getInt(6), c ));
        return c;
    }

    public void deleteClub(String clubName) {
        try {
            findClubQuery.setString(1, clubName);
            ResultSet rs = findClubQuery.executeQuery();
            if (!rs.next()) return;
            Club club = getClubFromResultSet(rs);

            deletePlayersForClubQuery.setInt(1, club.getId());
            deletePlayersForClubQuery.executeUpdate();

            deleteClubQuery.setString(1, club.getName());
            deleteClubQuery.executeUpdate();
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    public void deleteFixture(String homeTeamName, String awayTeamName) {
        try {
            findFixtureQuery.setString(1, homeTeamName);
            findFixtureQuery.setString(2, awayTeamName);
            ResultSet rs = findFixtureQuery.executeQuery();
            if (!rs.next()) return;
            Fixture fixture = getFixtureFromResultSet(rs);

            deleteFixtureQuery.setString(1, fixture.getHomeTeam().getName());
            deleteFixtureQuery.setString(2, fixture.getAwayTeam().getName());
            deleteFixtureQuery.executeUpdate();
        }
        catch (SQLException e) {
            e.getMessage();
        }
    }

    public List<Player> players() {
        ArrayList<Player> result = new ArrayList<>();
        try {
            ResultSet rs = getPlayersQuery.executeQuery();
            while (rs.next()) {
                Club c = getClub(rs.getString(6));
                Player player = getPlayerFromResultSet(rs, c);
                result.add(player);
            }
        } catch (SQLException e) {
            e.getMessage();
        }
        return result;
    }

    public List<Player> playersInClub(Club c) {
        ArrayList<Player> result = new ArrayList<>();
        try {
            findPlayersForClubQuery.setString(1, c.getName());
            ResultSet rs = findPlayersForClubQuery.executeQuery();
            while (rs.next()) {
                Player player = getPlayerFromResultSet(rs, c);
                result.add(player);
            }
        }
        catch (SQLException e) {
            e.getMessage();
        }
        return result;
    }

    public Club clubForPlayer(int id) {
        List<Club> clubs = clubs();
        for (Club club : clubs) {
            for (int j = 0; j < playersInClub(club).size(); j++) {
                if (playersInClub(club).get(j).getId()==id) {
                    return club;
                }
            }
        }
        return null;
    }

    public List<Club> clubs() {
        ArrayList<Club> result = new ArrayList<>();
        try {
            ResultSet rs = getClubsQuery.executeQuery();
            while (rs.next()) {
                Club club = getClubFromResultSet(rs);
                result.add(club);
            }
        } catch (SQLException e) {
            e.getMessage();
        }
        return result;
    }

    public List<Fixture> fixtures() {
        ArrayList<Fixture> result = new ArrayList<>();
        try {
            ResultSet rs = getFixturesQuery.executeQuery();
            while (rs.next()) {
                Fixture fixture = getFixtureFromResultSet(rs);
                result.add(fixture);
            }
        } catch (SQLException e) {
            e.getMessage();
        }
        return result;
    }

    private Fixture getFixtureFromResultSet(ResultSet rs) throws SQLException {
        return new Fixture(getClub(rs.getString(1)), getClub(rs.getString(2)));
    }

    public List<Result> results() {
        ArrayList<Result> result = new ArrayList<>();
        try {
            ResultSet rs = getResultsQuery.executeQuery();
            while (rs.next()) {
                Result res = getResultFromResultSet(rs);
                result.add(res);
            }
        }
        catch (SQLException e) {
            e.getMessage();
        }
        return result;
    }

    private Result getResultFromResultSet(ResultSet rs) throws SQLException {
        Result r = new Result(getClub(rs.getString(2)), getClub(rs.getString(3)), rs.getInt(4), rs.getInt(5));
        r.setId(rs.getInt(1));
        return r;
    }

    public List<Goal> goals() {
        ArrayList<Goal> result = new ArrayList<>();
        try {
            ResultSet rs = getGoalsQuery.executeQuery();
            while (rs.next()) {
                Goal goal = getGoalFromResultSet(rs);
                result.add(goal);
            }
        }
        catch (SQLException e) {
            e.getMessage();
        }
        return result;
    }

    private Goal getGoalFromResultSet(ResultSet rs) throws SQLException {
        Goal g = new Goal(getPlayer(rs.getInt(1), getClub(rs.getString(7))), getPlayer(rs.getInt(2), getClub(rs.getString(7))), rs.getInt(3), GoalType.valueOf(rs.getString(4)), GoalSituation.valueOf(rs.getString(5)), GoalDistance.valueOf(rs.getString(6)));
        g.setResult(getResult(rs.getInt(8)));
        return g;
    }

    public List<Stats> stats() {
        ArrayList<Stats> result = new ArrayList<>();
        try {
            ResultSet rs = getStatsQuery.executeQuery();
            while (rs.next()) {
                Stats stat = getStatFromResultSet(rs);
                result.add(stat);
            }
        }
        catch(SQLException e) {
            e.getMessage();
        }
        return result;
    }

    private Stats getStatFromResultSet(ResultSet rs) throws SQLException {
        Stats s = new Stats(rs.getInt(1));
        s.setAppearances(rs.getInt(2));
        s.setCleanSheets(rs.getInt(3));
        return s;
    }

    public void createStats() {
        try {
            deleteAllStatQuery.executeUpdate();
            for (int i=0; i<players().size(); i++) addStat(players().get(i).getId());
        }
        catch (SQLException e) {
            e.getMessage();
        }
    }

    public void addStat(int id) {
        try {
            addStatQuery.setInt(1, id);
            addStatQuery.setInt(2, 0);
            addStatQuery.setInt(3, 0);
            addStatQuery.executeUpdate();
        }
        catch (SQLException e) {
            e.getMessage();
        }
    }

    public void addPlayer(Player player) {
        try {
            ResultSet rs = setIdForPlayerQuery.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }
            addPlayerQuery.setInt(1, id);
            addPlayerQuery.setString(2, player.getName());
            addPlayerQuery.setString(3, player.getSurname());
            addPlayerQuery.setObject(4, player.getBirth());
            addPlayerQuery.setString(5, player.getNationality());
            addPlayerQuery.setString(6, player.getClub().getName());
            addPlayerQuery.setString(7, player.getClass().getSimpleName());
            addPlayerQuery.executeUpdate();
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    public void addClub(Club club) {
        try {
            addClubQuery.setString(1, club.getName());
            addClubQuery.setString(2, club.getNickname());
            addClubQuery.setString(3, club.getStadium());
            addClubQuery.setString(4, club.getMascot());
            addClubQuery.setString(5, club.getManager());
            if (club.getCaptain()!=null) addClubQuery.setInt(6, club.getCaptain().getId());
            addClubQuery.executeUpdate();

        } catch (SQLException e) {
            e.getMessage();
        }
    }

    public void addFixture(Fixture fixture) {
        try {
            addFixtureQuery.setString(1, fixture.getHomeTeam().getName());
            addFixtureQuery.setString(2, fixture.getAwayTeam().getName());
            addFixtureQuery.executeUpdate();
        }
        catch (SQLException e) {
            e.getMessage();
        }
    }

    public void addResult(Result result) {
        try{
            ResultSet rs = setIdForResultQuery.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }
            addResultQuery.setInt(1, id);
            addResultQuery.setString(2, result.getHomeTeam().getName());
            addResultQuery.setString(3, result.getAwayTeam().getName());
            addResultQuery.setInt(4, result.getHomeTeamScore());
            addResultQuery.setInt(5, result.getAwayTeamScore());
            addResultQuery.executeUpdate();
        }
        catch(SQLException e) {
            e.getMessage();
        }
    }

    public void addGoal (Goal goal) {
        try {
            addGoalQuery.setInt(1, goal.getScorer().getId());
             if (goal.getAssistent()!=null) addGoalQuery.setInt(2, goal.getAssistent().getId());
            addGoalQuery.setInt(3, goal.getMinute());
            addGoalQuery.setString(4, goal.getGoalType().name());
            addGoalQuery.setString(5, goal.getGoalSituation().name());
            addGoalQuery.setString(6, goal.getGoalDistance().name());
            addGoalQuery.setString(7, goal.getScorer().getClub().getName());
            addGoalQuery.setInt(8, goal.getResult().getId());
            addGoalQuery.executeUpdate();
        }
        catch (SQLException e) {
            e.getMessage();
        }
    }

    public void editClub(Club club) {
        try {
            editClubQuery.setString(1, club.getNickname());
            editClubQuery.setString(2, club.getStadium());
            editClubQuery.setString(3, club.getMascot());
            editClubQuery.setString(4, club.getManager());
            if (club.getCaptain()!=null) editClubQuery.setInt(5, club.getCaptain().getId());
            editClubQuery.executeUpdate();
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    public void editStat(Stats stat) {
        try {
            editStatQuery.setInt(1, stat.getAppearances());
            editStatQuery.setInt(2, stat.getCleanSheets());
            editStatQuery.setInt(3, stat.getId());
            editStatQuery.executeUpdate();
        }
        catch (SQLException e) {
            e.getMessage();
        }
    }

    public Club findClub(String clubName) {
        try {
            findClubQuery.setString(1, clubName);
            ResultSet rs = findClubQuery.executeQuery();
            if (!rs.next()) return null;
            return getClubFromResultSet(rs);
        } catch (SQLException e) {
            e.getMessage();
            return null;
        }
    }

    public Stats findStat(int id) {
        try {
            findStatQuery.setInt(1, id);
            ResultSet rs = findStatQuery.executeQuery();
            if (!rs.next()) return null;
            return getStatFromResultSet(rs);
        }
        catch (SQLException e) {
            e.getMessage();
            return null;
        }
    }

    public Fixture findFixture(String homeTeam, String awayTeam) {
        try {
            findFixtureQuery.setString(1, homeTeam);
            findFixtureQuery.setString(2, awayTeam);
            ResultSet rs = findFixtureQuery.executeQuery();
            if (!rs.next()) return null;
            return getFixtureFromResultSet(rs);
        } catch (SQLException e) {
            e.getMessage();
            return null;
        }
    }

    public Player findPlayer(int id) {
        try {
            findPlayerQuery.setInt(1, id);
            ResultSet rs = findPlayerQuery.executeQuery();
            if (!rs.next()) return null;
            Club c = clubForPlayer(id);
            return getPlayerFromResultSet(rs, c);
        } catch (SQLException e) {
            e.getMessage();
            return null;
        }
    }

    public void deletePlayer(Player player) {
        try {
            deletePlayerQuery.setInt(1, player.getId());
            deletePlayerQuery.executeUpdate();
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    public void deleteAllResults() {
        try {
            deleteAllResultsQuery.executeUpdate();
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    public void deleteAllGoals() {
        try {
            deleteAllGoalsQuery.executeUpdate();
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    public void deleteAllStats() {
        try {
            deleteAllStatQuery.executeUpdate();
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    public void deleteAllPlayers() {
        try {
            deleteAllPlayersQuery.executeUpdate();
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    public void deleteAllClubs() {
        try {
            deleteAllClubsQuery.executeUpdate();
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    public static void removeInstance() {
        if (instance == null) return;
        instance.close();
        instance = null;
    }

    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    private void regenerisiBazu() {
        try {
            Scanner input = new Scanner(new FileInputStream("league.db.sql"));
            String sqlQuery = "";
            while (input.hasNext()) {
                sqlQuery += input.nextLine();
                if ( sqlQuery.length() > 1 && sqlQuery.charAt( sqlQuery.length()-1 ) == ';') {
                    try {
                        Statement stmt = conn.createStatement();
                        stmt.execute(sqlQuery);
                        sqlQuery = "";
                    } catch (SQLException e) {
                        e.getMessage();
                    }
                }
            }
            input.close();
        } catch (FileNotFoundException e) {
            e.getMessage();
        }
    }

    public void vratiBazuNaDefault() throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("DELETE FROM players");
        stmt.executeUpdate("DELETE FROM clubs");
        stmt.executeUpdate("DELETE FROM fixtures");
        stmt.executeUpdate("DELETE FROM goals");
        stmt.executeUpdate("DELETE FROM results");
        stmt.executeUpdate("DELETE FROM stats");
        regenerisiBazu();
    }

}