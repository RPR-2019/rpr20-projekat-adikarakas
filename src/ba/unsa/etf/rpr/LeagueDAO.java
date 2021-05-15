package ba.unsa.etf.rpr;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class LeagueDAO {
    private static LeagueDAO instance;
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
    private PreparedStatement findClubUpit;
    private PreparedStatement findPlayerUpit;
    private PreparedStatement dajClubsUpit;
    private PreparedStatement dajPlayersUpit;
    private PreparedStatement dajPlayerUpit;
    private PreparedStatement dajClubUpit;
    private PreparedStatement captainUpit;
    private PreparedStatement deleteAllStatUpit;
    private Connection conn;

    private String name;
    private int numberOfClubs;
    private boolean scheduleRandom;
    private boolean created=false;
    private boolean started=false;
    private boolean finished=false;
    private PreparedStatement dajGoalsUpit;
    private PreparedStatement addStatUpit;
    private PreparedStatement findStatUpit;

    public static LeagueDAO getInstance() {
        if (instance==null) instance = new LeagueDAO();
        return instance;
    }

    private LeagueDAO () {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:league.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            captainUpit = conn.prepareStatement("SELECT players.id, players.name, players.surname, players.date, players.nationality  FROM players, clubs WHERE players.club=clubs.name AND clubs.name=?");
        } catch (SQLException e) {
            regenerisiBazu();
            try {
                captainUpit = conn.prepareStatement("SELECT players.id, players.name, players.surname, players.date, players.nationality  FROM players, clubs WHERE players.club=clubs.name AND clubs.name=?");
 //               glavniGradUpit = conn.prepareStatement("SELECT grad.id, grad.naziv, grad.broj_stanovnika, grad.drzava, grad.olimpijski FROM grad, drzava WHERE grad.drzava=drzava.id AND drzava.naziv=?");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

        try {
            dajClubUpit = conn.prepareStatement("SELECT * FROM clubs WHERE name=?");
            dajPlayerUpit = conn.prepareStatement("SELECT * FROM players WHERE id=?");

            deletePlayersForClubUpit = conn.prepareStatement("DELETE FROM players WHERE club=?");
            deleteClubUpit = conn.prepareStatement("DELETE FROM clubs WHERE name=?");
            deletePlayerUpit = conn.prepareStatement("DELETE FROM players WHERE id=?");
            deleteFixtureUpit = conn.prepareStatement("DELETE FROM fixtures WHERE home_team=? AND away_team=?");
            deleteAllStatUpit = conn.prepareStatement("DELETE FROM stats");

            findClubUpit = conn.prepareStatement("SELECT * FROM clubs WHERE name=?");
            findPlayersForClubUpit = conn.prepareStatement("SELECT * FROM players WHERE club=?");
            findPlayerUpit = conn.prepareStatement("SELECT * FROM players WHERE name=? AND surname=?");
            findFixtureUpit = conn.prepareStatement("SELECT * FROM fixtures WHERE home_team=? AND away_team=?");
            findStatUpit = conn.prepareStatement("SELECT * FROM stats WHERE id=?");

            dajPlayersUpit = conn.prepareStatement("SELECT * FROM players");
            dajClubsUpit = conn.prepareStatement("SELECT * FROM clubs");
            dajFixturesUpit = conn.prepareStatement("SELECT * FROM fixtures");
            dajResultsUpit = conn.prepareStatement("SELECT * FROM results");
            dajGoalsUpit = conn.prepareStatement("SELECT * FROM goals");
            dajStatsUpit = conn.prepareStatement("SELECT * FROM stats");

            addPlayerUpit = conn.prepareStatement("INSERT INTO players VALUES(?,?,?,?,?,?,?)");
            setIdForPlayerUpit = conn.prepareStatement("SELECT MAX(id)+1 FROM players");
            addClubUpit = conn.prepareStatement("INSERT INTO clubs VALUES(?,?,?,?,?,?)");
            addFixtureUpit = conn.prepareStatement("INSERT INTO fixtures VALUES(?,?)");
            addResultUpit = conn.prepareStatement("INSERT INTO results VALUES(?,?,?,?)");
            addGoalUpit = conn.prepareStatement("INSERT INTO goals VALUES(?,?,?,?,?,?,?)");
            addStatUpit = conn.prepareStatement("INSERT INTO stats VALUES(?,?,?)");

            editPlayerUpit = conn.prepareStatement("UPDATE players SET name=?, surname=?, date=?, nationality=?, club=?, position=? WHERE id=?");
            editClubUpit = conn.prepareStatement("UPDATE clubs SET nickname=?, stadium=?, mascot=?, manager=?, captain=? WHERE name=?");
            editStatUpit = conn.prepareStatement("UPDATE stats SET apperances=?, clean_sheets=? WHERE id=?");
        }
        catch (SQLException e) {
            e.printStackTrace();
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
            e.printStackTrace();
            return null;
        }
    }

    private Player getPlayerFromResultSet(ResultSet rs, Club c) throws SQLException {
        if (rs.getString(7).equals("Goalkeeper")) {
            Player p = new Goalkeeper(rs.getString(2), rs.getString(3), LocalDate.parse(rs.getString(4)), rs.getString(5));
            p.setId(rs.getInt(1));
            p.setClub(c);
            return p;
        }
        else if (rs.getString(7).equals("Defender")) {
            Player p = new Defender(rs.getString(2), rs.getString(3), LocalDate.parse(rs.getString(4)), rs.getString(5));
            p.setId(rs.getInt(1));
            p.setClub(c);
            return p;
        }
        else if (rs.getString(7).equals("Midfielder")) {
            Player p = new Midfielder(rs.getString(2), rs.getString(3), LocalDate.parse(rs.getString(4)), rs.getString(5));
            p.setId(rs.getInt(1));
            p.setClub(c);
            return p;
        }
        else if (rs.getString(7).equals("Attacker")) {
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }

   // lista svih igraca u ligi
    public ArrayList<Player> players() {
        ArrayList<Player> result = new ArrayList();
        try {
            ResultSet rs = dajPlayersUpit.executeQuery();
            while (rs.next()) {
                Club c = getClub(rs.getString(6));
                Player player = getPlayerFromResultSet(rs, c);
                result.add(player);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // lista svih igrača u klubu
    public ArrayList<Player> playersInClub(Club c) {
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
            e.printStackTrace();
        }
        return result;
    }

/*    public ArrayList<Player> playersInClub(Club c) {
        ArrayList<Player> result = new ArrayList<>();
        for (int i=0; i<players().size(); i++) {
            if (players().get(i).getClub().equals(c)) result.add(players().get(i));
        }
        return result;
    }
*/
    public Club clubForPlayer(String name, String surname) {
        ArrayList<Club> clubs = clubs();
        for (int i=0; i< clubs.size(); i++) {
            for (int j=0; j<playersInClub(clubs.get(i)).size(); j++)
            if (playersInClub(clubs.get(i)).get(j).getName().equals(name) && playersInClub(clubs.get(i)).get(j).getSurname().equals(surname)) return clubs.get(i);
        }
        return null;
    }

    // lista svih klubova u ligi
    public ArrayList<Club> clubs() {
        ArrayList<Club> result = new ArrayList();
        try {
            ResultSet rs = dajClubsUpit.executeQuery();
            while (rs.next()) {
                Club club = getClubFromResultSet(rs);
                result.add(club);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<Fixture> fixtures() {
        ArrayList<Fixture> result = new ArrayList();
        try {
            ResultSet rs = dajFixturesUpit.executeQuery();
            while (rs.next()) {
                Fixture fixture = getFixtureFromResultSet(rs);
                result.add(fixture);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private Fixture getFixtureFromResultSet(ResultSet rs) throws SQLException {
        Fixture f = new Fixture(getClub(rs.getString(1)), getClub(rs.getString(2))); // fino poredati
        return f;
    }

    public ArrayList<Result> results() {
        ArrayList<Result> result = new ArrayList<>();
        try {
            ResultSet rs = dajResultsUpit.executeQuery();
            while (rs.next()) {
                Result res = getResultFromResultSet(rs);
                result.add(res);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private Result getResultFromResultSet(ResultSet rs) throws SQLException {
        Result r = new Result(getClub(rs.getString(1)), getClub(rs.getString(2)), rs.getInt(3), rs.getInt(4));
        return r;
    }

    public ArrayList<Goal> goals() {
        ArrayList<Goal> result = new ArrayList<>();
        try {
            ResultSet rs = dajGoalsUpit.executeQuery();
            while (rs.next()) {
                Goal goal = getGoalFromResultSet(rs);
                result.add(goal);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private Goal getGoalFromResultSet(ResultSet rs) throws SQLException {
        return new Goal(getPlayer(rs.getInt(1), getClub(rs.getString(7))), getPlayer(rs.getInt(2), getClub(rs.getString(7))), rs.getInt(3), GoalType.valueOf(rs.getString(4)), GoalSituation.valueOf(rs.getString(5)), GoalDistance.valueOf(rs.getString(6)));
    }

    public ArrayList<Stats> stats() {
        ArrayList<Stats> result = new ArrayList<>();
        try {
            ResultSet rs = dajStatsUpit.executeQuery();
            while (rs.next()) {
                Stats stat = getStatFromResultSet(rs);
                result.add(stat);
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private Stats getStatFromResultSet(ResultSet rs) throws SQLException {
        Stats s = new Stats(rs.getInt(1));
        s.setApperances(rs.getInt(2));
        s.setCleanSheets(rs.getInt(3));
        return s;
    }

    public void createStats() {
        try {
            deleteAllStatUpit.executeUpdate();
            for (int i=0; i<players().size(); i++) addStat(players().get(i).getId());
        }
        catch (SQLException e) {
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }

    public void addFixture(Fixture fixture) {
        try {
            addFixtureUpit.setString(1, fixture.getHomeTeam().getName());
            addFixtureUpit.setString(2, fixture.getAwayTeam().getName());
            addFixtureUpit.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addResult(Result result) {
        try{
            addResultUpit.setString(1, result.getHomeTeam().getName());
            addResultUpit.setString(2, result.getAwayTeam().getName());
            addResultUpit.setInt(3, result.getHomeTeamScore());
            addResultUpit.setInt(4, result.getAwayTeamScore());
            addResultUpit.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
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
            addGoalUpit.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }

    public void editStat(Stats stat) {
        try {
            editStatUpit.setInt(1, stat.getApperances());
            editStatUpit.setInt(2, stat.getCleanSheets());
            editStatUpit.setInt(3, stat.getId());
            editStatUpit.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Club findClub(String clubName) {
        try {
            findClubUpit.setString(1, clubName);
            ResultSet rs = findClubUpit.executeQuery();
            if (!rs.next()) return null;
            return getClubFromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
            return null;
        }
    }

    public Player findPlayer(String playerName, String playerSurname) {
        try {
            findPlayerUpit.setString(1, playerName);
            ResultSet rs = findPlayerUpit.executeQuery();
            if (!rs.next()) return null;
            Club c = clubForPlayer(playerName, playerSurname); // ovu liniju popraviti, nemam taj parametar
            return getPlayerFromResultSet(rs, c);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deletePlayer(Player player) {
        try {
            deletePlayerUpit.setInt(1, player.getId());
            deletePlayerUpit.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }

    private void regenerisiBazu() {
        Scanner input = null;
        try {
            input = new Scanner(new FileInputStream("league.db.sql"));
            String sqlUpit = "";
            while (input.hasNext()) {
                sqlUpit += input.nextLine();
                if ( sqlUpit.length() > 1 && sqlUpit.charAt( sqlUpit.length()-1 ) == ';') {
                    try {
                        Statement stmt = conn.createStatement();
                        stmt.execute(sqlUpit);
                        sqlUpit = "";
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            input.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void vratiBazuNaDefault() throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("DELETE FROM players");
        stmt.executeUpdate("DELETE FROM clubs");
        stmt.executeUpdate("DELETE FROM fixtures");
        stmt.executeUpdate("DELETE FROM matches");
        regenerisiBazu();
    }

    public boolean isScheduleRandom() {
        return scheduleRandom;
    }

    public void setScheduleRandom(boolean scheduleRandom) {
        this.scheduleRandom = scheduleRandom;
    }

    public boolean isCreated() {
        return created;
    }

    public void setCreated(boolean created) {
        this.created = created;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public int getNumberOfClubs() {
        return numberOfClubs;
    }

    public void setNumberOfClubs(int numberOfClubs) {
        this.numberOfClubs = numberOfClubs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
