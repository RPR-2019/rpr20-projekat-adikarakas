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
    private PreparedStatement findResultUpit;
    private PreparedStatement setIdForResultUpit;
    private PreparedStatement dajStatsUpit;
    private PreparedStatement deleteFixtureUpit;
    private PreparedStatement addGoalUpit;
    private PreparedStatement dajResultsUpit;
    private PreparedStatement addResultUpit;
    private PreparedStatement findFixtureUpit;
    private PreparedStatement dajFixturesUpit;
    private PreparedStatement addFixtureUpit;
    private PreparedStatement findPlayersForClubUpit;
    private PreparedStatement editClubUpit;
    private PreparedStatement editPlayerUpit;
    private PreparedStatement editStatUpit;
    private PreparedStatement addPlayerUpit;
    private PreparedStatement setIdForPlayerUpit;
    private PreparedStatement addClubUpit;
    private PreparedStatement setIdForClubUpit;
    private PreparedStatement deletePlayersForClubUpit;
    private PreparedStatement deleteClubUpit;
    private PreparedStatement deletePlayerUpit;
    private PreparedStatement deleteAllGoalsUpit;
    private PreparedStatement deleteAllResultsUpit;
    private PreparedStatement deleteAllPlayersUpit;
    private PreparedStatement deleteAllClubsUpit;
    private PreparedStatement findClubUpit;
    private PreparedStatement findPlayerUpit;
    private PreparedStatement dajClubsUpit;
    private PreparedStatement dajPlayersUpit;
    private PreparedStatement dajPlayerUpit;
    private PreparedStatement dajClubUpit;
    private PreparedStatement captainUpit;
    private PreparedStatement deleteAllStatUpit;
    private PreparedStatement dajResultUpit;
    private PreparedStatement zaIzvjestajUpit;
    private Connection conn;

    private PreparedStatement dajGoalsUpit;
    private PreparedStatement addStatUpit;
    private PreparedStatement findStatUpit;

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
            dajClubUpit = conn.prepareStatement("SELECT * FROM clubs WHERE name=?");
        } catch (SQLException e) {
            regenerisiBazu();
            try {
                dajClubUpit = conn.prepareStatement("SELECT * FROM clubs WHERE name=?");
            } catch (SQLException e1) {
                e1.getMessage();
            }
        }

        try {

            zaIzvjestajUpit = conn.prepareStatement("SELECT * FROM goals where result=? AND club=?");

            dajPlayerUpit = conn.prepareStatement("SELECT * FROM players WHERE id=?");
            dajResultUpit = conn.prepareStatement("SELECT * FROM results WHERE id=?");

            deletePlayersForClubUpit = conn.prepareStatement("DELETE FROM players WHERE club=?");
            deleteClubUpit = conn.prepareStatement("DELETE FROM clubs WHERE name=?");
            deletePlayerUpit = conn.prepareStatement("DELETE FROM players WHERE id=?");
            deleteFixtureUpit = conn.prepareStatement("DELETE FROM fixtures WHERE home_team=? AND away_team=?");
            deleteAllStatUpit = conn.prepareStatement("DELETE FROM stats");
            deleteAllGoalsUpit = conn.prepareStatement("DELETE FROM goals");
            deleteAllResultsUpit = conn.prepareStatement("DELETE FROM results");
            deleteAllPlayersUpit = conn.prepareStatement("DELETE FROM players");
            deleteAllClubsUpit = conn.prepareStatement("DELETE FROM clubs");

            findClubUpit = conn.prepareStatement("SELECT * FROM clubs WHERE name=?");
            findPlayersForClubUpit = conn.prepareStatement("SELECT * FROM players WHERE club=?");
            findPlayerUpit = conn.prepareStatement("SELECT * FROM players WHERE id=?");
            findFixtureUpit = conn.prepareStatement("SELECT * FROM fixtures WHERE home_team=? AND away_team=?");
            findStatUpit = conn.prepareStatement("SELECT * FROM stats WHERE id=?");
            findResultUpit = conn.prepareStatement("SELECT * FROM results WHERE home_team=? AND away_team=?");

            dajPlayersUpit = conn.prepareStatement("SELECT * FROM players");
            dajClubsUpit = conn.prepareStatement("SELECT * FROM clubs");
            dajFixturesUpit = conn.prepareStatement("SELECT * FROM fixtures");
            dajResultsUpit = conn.prepareStatement("SELECT * FROM results");
            dajGoalsUpit = conn.prepareStatement("SELECT * FROM goals");
            dajStatsUpit = conn.prepareStatement("SELECT * FROM stats");

            addPlayerUpit = conn.prepareStatement("INSERT INTO players VALUES(?,?,?,?,?,?,?)");
            setIdForPlayerUpit = conn.prepareStatement("SELECT MAX(id)+1 FROM players");
            setIdForResultUpit = conn.prepareStatement("SELECT MAX(id)+1 FROM results");
            addClubUpit = conn.prepareStatement("INSERT INTO clubs VALUES(?,?,?,?,?,?)");
            addFixtureUpit = conn.prepareStatement("INSERT INTO fixtures VALUES(?,?)");
            addResultUpit = conn.prepareStatement("INSERT INTO results VALUES(?,?,?,?,?)");
            addGoalUpit = conn.prepareStatement("INSERT INTO goals VALUES(?,?,?,?,?,?,?,?)");
            addStatUpit = conn.prepareStatement("INSERT INTO stats VALUES(?,?,?)");

            editPlayerUpit = conn.prepareStatement("UPDATE players SET name=?, surname=?, date=?, nationality=?, club=?, position=? WHERE id=?");
            editClubUpit = conn.prepareStatement("UPDATE clubs SET nickname=?, stadium=?, mascot=?, manager=?, captain=? WHERE name=?");
            editStatUpit = conn.prepareStatement("UPDATE stats SET appearances=?, clean_sheets=? WHERE id=?");
        }
        catch (SQLException e) {
            e.getMessage();
        }
    }

    public Player captain(String club) {
        try {
            Club c = findClub(club);
            captainUpit.setString(1, club);
            ResultSet rs = captainUpit.executeQuery();
            if (!rs.next()) return null;
            return getPlayerFromResultSet(rs, c);
        } catch (SQLException e) {
            e.getMessage();
            return null;
        }
    }

    public ObservableList<Goal> metodaDomacin(Result r) {
        ObservableList<Goal> goals = FXCollections.observableArrayList();
        try {
            zaIzvjestajUpit.setInt(1, r.getId());
            zaIzvjestajUpit.setString(2, r.getHomeTeam().getName());
            ResultSet rs = zaIzvjestajUpit.executeQuery();
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

    public ObservableList<Goal> metodaGost(Result r) {
        ObservableList<Goal> goals = FXCollections.observableArrayList();
        try {
            zaIzvjestajUpit.setInt(1, r.getId());
            zaIzvjestajUpit.setString(2, r.getAwayTeam().getName());
            ResultSet rs = zaIzvjestajUpit.executeQuery();
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
            dajClubUpit.setString(1, name);
            ResultSet rs = dajClubUpit.executeQuery();
            if (!rs.next()) return null;
            return getClubFromResultSet(rs);
        } catch (SQLException e) {
            e.getMessage();
            return null;
        }
    }

    private Player getPlayer(int id, Club c) {
        try {
            dajPlayerUpit.setInt(1, id);
            ResultSet rs = dajPlayerUpit.executeQuery();
            if (!rs.next()) return null;
            return getPlayerFromResultSet(rs, c);
        } catch (SQLException e) {
            e.getMessage();
            return null;
        }
    }

    private Result getResult(int id) {
        try {
            dajResultUpit.setInt(1, id);
            ResultSet rs = dajResultUpit.executeQuery();
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
            findResultUpit.setString(1, c1.getName());
            findResultUpit.setString(2, c2.getName());
            ResultSet rs = findResultUpit.executeQuery();
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
            findClubUpit.setString(1, clubName);
            ResultSet rs = findClubUpit.executeQuery();
            if (!rs.next()) return;
            Club club = getClubFromResultSet(rs);

            deletePlayersForClubUpit.setInt(1, club.getId());
            deletePlayersForClubUpit.executeUpdate();

            deleteClubUpit.setString(1, club.getName());
            deleteClubUpit.executeUpdate();
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    public void deleteFixture(String homeTeamName, String awayTeamName) {
        try {
            findFixtureUpit.setString(1, homeTeamName);
            findFixtureUpit.setString(2, awayTeamName);
            ResultSet rs = findFixtureUpit.executeQuery();
            if (!rs.next()) return;
            Fixture fixture = getFixtureFromResultSet(rs);

            deleteFixtureUpit.setString(1, fixture.getHomeTeam().getName());
            deleteFixtureUpit.setString(2, fixture.getAwayTeam().getName());
            deleteFixtureUpit.executeUpdate();
        }
        catch (SQLException e) {
            e.getMessage();
        }
    }

   // lista svih igraca u ligi
    public List<Player> players() {
        ArrayList<Player> result = new ArrayList<>();
        try {
            ResultSet rs = dajPlayersUpit.executeQuery();
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

    // lista svih igrača u klubu
    public List<Player> playersInClub(Club c) {
        ArrayList<Player> result = new ArrayList<>();
        try {
            findPlayersForClubUpit.setString(1, c.getName());
            ResultSet rs = findPlayersForClubUpit.executeQuery();
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

    public Club clubForPlayer(String name, String surname) {
        List<Club> clubs = clubs();
        for (Club club : clubs) {
            for (int j = 0; j < playersInClub(club).size(); j++) {
                if (playersInClub(club).get(j).getName().equals(name) && playersInClub(club).get(j).getSurname().equals(surname)) {
                    return club;
                }
            }
        }
        return null;
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

    // lista svih klubova u ligi
    public List<Club> clubs() {
        ArrayList<Club> result = new ArrayList<>();
        try {
            ResultSet rs = dajClubsUpit.executeQuery();
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
            ResultSet rs = dajFixturesUpit.executeQuery();
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
            ResultSet rs = dajResultsUpit.executeQuery();
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
            ResultSet rs = dajGoalsUpit.executeQuery();
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
            ResultSet rs = dajStatsUpit.executeQuery();
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
            deleteAllStatUpit.executeUpdate();
            for (int i=0; i<players().size(); i++) addStat(players().get(i).getId());
        }
        catch (SQLException e) {
            e.getMessage();
        }
    }

    public void addStat(int id) {
        try {
            addStatUpit.setInt(1, id);
            addStatUpit.setInt(2, 0);
            addStatUpit.setInt(3, 0);
            addStatUpit.executeUpdate();
        }
        catch (SQLException e) {
            e.getMessage();
        }
    }

    public void addPlayer(Player player) {
        try {
            ResultSet rs = setIdForPlayerUpit.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }

            // poredati fino parametre
            addPlayerUpit.setInt(1, id);
            addPlayerUpit.setString(2, player.getName());
            addPlayerUpit.setString(3, player.getSurname());
            addPlayerUpit.setObject(4, player.getBirth());
            addPlayerUpit.setString(5, player.getNationality());
            addPlayerUpit.setString(6, player.getClub().getName());
            addPlayerUpit.setString(7, player.getClass().getSimpleName());
            addPlayerUpit.executeUpdate();
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    public int setIdForClub () {
        try {
            ResultSet rs = setIdForClubUpit.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }
            return id;
        }
        catch (SQLException e) {
            e.getMessage();
            return 0;
        }
    }

    public void addClub(Club club) {
        try {
            // poredati fino parametre
            addClubUpit.setString(1, club.getName());
            addClubUpit.setString(2, club.getNickname());
            addClubUpit.setString(3, club.getStadium());
            addClubUpit.setString(4, club.getMascot());
            addClubUpit.setString(5, club.getManager());
            if (club.getCaptain()!=null) addClubUpit.setInt(6, club.getCaptain().getId());
            addClubUpit.executeUpdate();

        } catch (SQLException e) {
            e.getMessage();
        }
    }

    public void addFixture(Fixture fixture) {
        try {
            addFixtureUpit.setString(1, fixture.getHomeTeam().getName());
            addFixtureUpit.setString(2, fixture.getAwayTeam().getName());
            addFixtureUpit.executeUpdate();
        }
        catch (SQLException e) {
            e.getMessage();
        }
    }

    public void addResult(Result result) {
        try{
            ResultSet rs = setIdForResultUpit.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }
            addResultUpit.setInt(1, id);
            addResultUpit.setString(2, result.getHomeTeam().getName());
            addResultUpit.setString(3, result.getAwayTeam().getName());
            addResultUpit.setInt(4, result.getHomeTeamScore());
            addResultUpit.setInt(5, result.getAwayTeamScore());
            addResultUpit.executeUpdate();
        }
        catch(SQLException e) {
            e.getMessage();
        }
    }

    public void addGoal (Goal goal) {
        try {
            addGoalUpit.setInt(1, goal.getScorer().getId());
             if (goal.getAssistent()!=null) addGoalUpit.setInt(2, goal.getAssistent().getId());
            addGoalUpit.setInt(3, goal.getMinute());
            addGoalUpit.setString(4, goal.getGoalType().name());
            addGoalUpit.setString(5, goal.getGoalSituation().name());
            addGoalUpit.setString(6, goal.getGoalDistance().name());
            addGoalUpit.setString(7, goal.getScorer().getClub().getName());
            addGoalUpit.setInt(8, goal.getResult().getId());
            addGoalUpit.executeUpdate();
        }
        catch (SQLException e) {
            e.getMessage();
        }
    }

    public void editPlayer(Player player) {
        try {
            // poredati fino parametre
            editPlayerUpit.setString(1, player.getName());
            editPlayerUpit.setString(2, player.getSurname());
            editPlayerUpit.setString(3, player.getBirth().toString());
            editPlayerUpit.setString(4, player.getNationality());
            editPlayerUpit.setString(5, player.getClub().getName());
            editPlayerUpit.executeUpdate();
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    public void editClub(Club club) {
        try {
            // poredati fino parametre
            editClubUpit.setString(1, club.getNickname());
            editClubUpit.setString(2, club.getStadium());
            editClubUpit.setString(3, club.getMascot());
            editClubUpit.setString(4, club.getManager());
            if (club.getCaptain()!=null) editClubUpit.setInt(5, club.getCaptain().getId());
            editClubUpit.executeUpdate();
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    public void editStat(Stats stat) {
        try {
            editStatUpit.setInt(1, stat.getAppearances());
            editStatUpit.setInt(2, stat.getCleanSheets());
            editStatUpit.setInt(3, stat.getId());
            editStatUpit.executeUpdate();
        }
        catch (SQLException e) {
            e.getMessage();
        }
    }

    public Club findClub(String clubName) {
        try {
            findClubUpit.setString(1, clubName);
            ResultSet rs = findClubUpit.executeQuery();
            if (!rs.next()) return null;
            return getClubFromResultSet(rs);
        } catch (SQLException e) {
            e.getMessage();
            return null;
        }
    }

    public Stats findStat(int id) {
        try {
            findStatUpit.setInt(1, id);
            ResultSet rs = findStatUpit.executeQuery();
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
            findFixtureUpit.setString(1, homeTeam);
            findFixtureUpit.setString(2, awayTeam);
            ResultSet rs = findFixtureUpit.executeQuery();
            if (!rs.next()) return null;
            return getFixtureFromResultSet(rs);
        } catch (SQLException e) {
            e.getMessage();
            return null;
        }
    }

    public Player findPlayer(int id) {
        try {
            findPlayerUpit.setInt(1, id);
            ResultSet rs = findPlayerUpit.executeQuery();
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
            deletePlayerUpit.setInt(1, player.getId());
            deletePlayerUpit.executeUpdate();
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    public void deleteAllResults() {
        try {
            deleteAllResultsUpit.executeUpdate();
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    public void deleteAllGoals() {
        try {
            deleteAllGoalsUpit.executeUpdate();
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    public void deleteAllStats() {
        try {
            deleteAllStatUpit.executeUpdate();
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    public void deleteAllPlayers() {
        try {
            deleteAllPlayersUpit.executeUpdate();
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    public void deleteAllClubs() {
        try {
            deleteAllClubsUpit.executeUpdate();
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
            String sqlUpit = "";
            while (input.hasNext()) {
                sqlUpit += input.nextLine();
                if ( sqlUpit.length() > 1 && sqlUpit.charAt( sqlUpit.length()-1 ) == ';') {
                    try {
                        Statement stmt = conn.createStatement();
                        stmt.execute(sqlUpit);
                        sqlUpit = "";
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
