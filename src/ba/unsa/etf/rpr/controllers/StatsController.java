package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.beans.PlayerStats;
import ba.unsa.etf.rpr.beans.Goalkeeper;
import ba.unsa.etf.rpr.beans.Club;
import ba.unsa.etf.rpr.beans.Goal;
import ba.unsa.etf.rpr.beans.Player;
import ba.unsa.etf.rpr.other.GoalDistance;
import ba.unsa.etf.rpr.other.GoalSituation;
import ba.unsa.etf.rpr.other.GoalType;
import ba.unsa.etf.rpr.other.LeagueDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;

public class StatsController {
    public TableView<PlayerStats> tableViewGoals = new TableView<>();
    public TableView<PlayerStats> tableViewAssists = new TableView<>();
    public TableView<PlayerStats> tableViewCleanSheets = new TableView<>();
    public TableView<PlayerStats> tableViewAppearances = new TableView<>();
    public TableView<PlayerStats> tableViewInside = new TableView<>();
    public TableView<PlayerStats> tableViewOutside = new TableView<>();
    public TableView<PlayerStats> tableViewRight = new TableView<>();
    public TableView<PlayerStats> tableViewLeft = new TableView<>();
    public TableView<PlayerStats> tableViewHead = new TableView<>();
    public TableView<PlayerStats> tableViewPenalties = new TableView<>();
    public TableView<PlayerStats> tableViewFreeKicks = new TableView<>();
    public TableView<PlayerStats> tableViewOpen = new TableView<>();

    public TableColumn<PlayerStats, String> tableColumnGoalsRank;
    public TableColumn<PlayerStats, Player> tableColumnGoalsName;
    public TableColumn<PlayerStats, Club> tableColumnGoalsClub;
    public TableColumn<PlayerStats, Integer> tableColumnGoalsStat;
    public TableColumn<PlayerStats, String> tableColumnAssistsRank;
    public TableColumn<PlayerStats, Player> tableColumnAssistsName;
    public TableColumn<PlayerStats, Club> tableColumnAssistsClub;
    public TableColumn<PlayerStats, Integer> tableColumnAssistsStat;
    public TableColumn<PlayerStats, String> tableColumnCleanSheetsRank;
    public TableColumn<PlayerStats, Player> tableColumnCleanSheetsName;
    public TableColumn<PlayerStats, Club> tableColumnCleanSheetsClub;
    public TableColumn<PlayerStats, Integer> tableColumnCleanSheetsStat;
    public TableColumn<PlayerStats, String> tableColumnAppearancesRank;
    public TableColumn<PlayerStats, Player> tableColumnAppearancesName;
    public TableColumn<PlayerStats, Club> tableColumnAppearancesClub;
    public TableColumn<PlayerStats, Integer> tableColumnAppearancesStat;
    public TableColumn<PlayerStats, String> tableColumnInsideRank;
    public TableColumn<PlayerStats, Player> tableColumnInsideName;
    public TableColumn<PlayerStats, Club> tableColumnInsideClub;
    public TableColumn<PlayerStats, Integer> tableColumnInsideStat;
    public TableColumn<PlayerStats, String> tableColumnOutsideRank;
    public TableColumn<PlayerStats, Player> tableColumnOutsideName;
    public TableColumn<PlayerStats, Club> tableColumnOutsideClub;
    public TableColumn<PlayerStats, Integer> tableColumnOutsideStat;
    public TableColumn<PlayerStats, String> tableColumnRightRank;
    public TableColumn<PlayerStats, Player> tableColumnRightName;
    public TableColumn<PlayerStats, Club> tableColumnRightClub;
    public TableColumn<PlayerStats, Integer> tableColumnRightStat;
    public TableColumn<PlayerStats, String> tableColumnLeftRank;
    public TableColumn<PlayerStats, Player> tableColumnLeftName;
    public TableColumn<PlayerStats, Club> tableColumnLeftClub;
    public TableColumn<PlayerStats, Integer> tableColumnLeftStat;
    public TableColumn<PlayerStats, String> tableColumnHeadRank;
    public TableColumn<PlayerStats, Player> tableColumnHeadName;
    public TableColumn<PlayerStats, Club> tableColumnHeadClub;
    public TableColumn<PlayerStats, Integer> tableColumnHeadStat;
    public TableColumn<PlayerStats, String> tableColumnPenaltiesRank;
    public TableColumn<PlayerStats, Player> tableColumnPenaltiesName;
    public TableColumn<PlayerStats, Club> tableColumnPenaltiesClub;
    public TableColumn<PlayerStats, Integer> tableColumnPenaltiesStat;
    public TableColumn<PlayerStats, String> tableColumnFreeKicksRank;
    public TableColumn<PlayerStats, Player> tableColumnFreeKicksName;
    public TableColumn<PlayerStats, Club> tableColumnFreeKicksClub;
    public TableColumn<PlayerStats, Integer> tableColumnFreeKicksStat;
    public TableColumn<PlayerStats, String> tableColumnOpenRank;
    public TableColumn<PlayerStats, Player> tableColumnOpenName;
    public TableColumn<PlayerStats, Club> tableColumnOpenClub;
    public TableColumn<PlayerStats, Integer> tableColumnOpenStat;
    private final LeagueDAO dao;
    private final List<Goal> allGoals;
    private static final String RANK ="rank";
    private static final String NAME ="name";
    private static final String CLUB ="club";
    private static final String STAT ="stat";

    StatsController() {
        dao=LeagueDAO.getInstance();
        allGoals= new ArrayList<>(dao.goals());
    }

    @FXML
    public void initialize() {
        tableColumnGoalsRank.setCellValueFactory(new PropertyValueFactory<>(RANK));
        tableColumnGoalsName.setCellValueFactory(new PropertyValueFactory<>(NAME));
        tableColumnGoalsClub.setCellValueFactory(new PropertyValueFactory<>(CLUB));
        tableColumnGoalsStat.setCellValueFactory(new PropertyValueFactory<>(STAT));
        tableViewGoals.setItems(initializeGoals());

        tableColumnAssistsRank.setCellValueFactory(new PropertyValueFactory<>(RANK));
        tableColumnAssistsName.setCellValueFactory(new PropertyValueFactory<>(NAME));
        tableColumnAssistsClub.setCellValueFactory(new PropertyValueFactory<>(CLUB));
        tableColumnAssistsStat.setCellValueFactory(new PropertyValueFactory<>(STAT));
        tableViewAssists.setItems(initializeAssists());

        tableColumnInsideRank.setCellValueFactory(new PropertyValueFactory<>(RANK));
        tableColumnInsideName.setCellValueFactory(new PropertyValueFactory<>(NAME));
        tableColumnInsideClub.setCellValueFactory(new PropertyValueFactory<>(CLUB));
        tableColumnInsideStat.setCellValueFactory(new PropertyValueFactory<>(STAT));
        tableViewInside.setItems(initializeGoalData(GoalDistance.INSIDEBOX, Goal::getGoalDistance));

        tableColumnOutsideRank.setCellValueFactory(new PropertyValueFactory<>(RANK));
        tableColumnOutsideName.setCellValueFactory(new PropertyValueFactory<>(NAME));
        tableColumnOutsideClub.setCellValueFactory(new PropertyValueFactory<>(CLUB));
        tableColumnOutsideStat.setCellValueFactory(new PropertyValueFactory<>(STAT));
        tableViewOutside.setItems(initializeGoalData(GoalDistance.OUTSIDEBOX, Goal::getGoalDistance));

        tableColumnRightRank.setCellValueFactory(new PropertyValueFactory<>(RANK));
        tableColumnRightName.setCellValueFactory(new PropertyValueFactory<>(NAME));
        tableColumnRightClub.setCellValueFactory(new PropertyValueFactory<>(CLUB));
        tableColumnRightStat.setCellValueFactory(new PropertyValueFactory<>(STAT));
        tableViewRight.setItems(initializeGoalData(GoalType.RIGHTFOOT, Goal::getGoalType));

        tableColumnLeftRank.setCellValueFactory(new PropertyValueFactory<>(RANK));
        tableColumnLeftName.setCellValueFactory(new PropertyValueFactory<>(NAME));
        tableColumnLeftClub.setCellValueFactory(new PropertyValueFactory<>(CLUB));
        tableColumnLeftStat.setCellValueFactory(new PropertyValueFactory<>(STAT));
        tableViewLeft.setItems(initializeGoalData(GoalType.LEFTFOOT, Goal::getGoalType));

        tableColumnHeadRank.setCellValueFactory(new PropertyValueFactory<>(RANK));
        tableColumnHeadName.setCellValueFactory(new PropertyValueFactory<>(NAME));
        tableColumnHeadClub.setCellValueFactory(new PropertyValueFactory<>(CLUB));
        tableColumnHeadStat.setCellValueFactory(new PropertyValueFactory<>(STAT));
        tableViewHead.setItems(initializeGoalData(GoalType.HEADER, Goal::getGoalType));

        tableColumnPenaltiesRank.setCellValueFactory(new PropertyValueFactory<>(RANK));
        tableColumnPenaltiesName.setCellValueFactory(new PropertyValueFactory<>(NAME));
        tableColumnPenaltiesClub.setCellValueFactory(new PropertyValueFactory<>(CLUB));
        tableColumnPenaltiesStat.setCellValueFactory(new PropertyValueFactory<>(STAT));
        tableViewPenalties.setItems(initializeGoalData(GoalSituation.PENALTY, Goal::getGoalSituation));

        tableColumnFreeKicksRank.setCellValueFactory(new PropertyValueFactory<>(RANK));
        tableColumnFreeKicksName.setCellValueFactory(new PropertyValueFactory<>(NAME));
        tableColumnFreeKicksClub.setCellValueFactory(new PropertyValueFactory<>(CLUB));
        tableColumnFreeKicksStat.setCellValueFactory(new PropertyValueFactory<>(STAT));
        tableViewFreeKicks.setItems(initializeGoalData(GoalSituation.FREEKICK, Goal::getGoalSituation));

        tableColumnOpenRank.setCellValueFactory(new PropertyValueFactory<>(RANK));
        tableColumnOpenName.setCellValueFactory(new PropertyValueFactory<>(NAME));
        tableColumnOpenClub.setCellValueFactory(new PropertyValueFactory<>(CLUB));
        tableColumnOpenStat.setCellValueFactory(new PropertyValueFactory<>(STAT));
        tableViewOpen.setItems(initializeGoalData(GoalSituation.OPENPLAY, Goal::getGoalSituation));

        tableColumnAppearancesRank.setCellValueFactory(new PropertyValueFactory<>(RANK));
        tableColumnAppearancesName.setCellValueFactory(new PropertyValueFactory<>(NAME));
        tableColumnAppearancesClub.setCellValueFactory(new PropertyValueFactory<>(CLUB));
        tableColumnAppearancesStat.setCellValueFactory(new PropertyValueFactory<>(STAT));
        tableViewAppearances.setItems(initializeAppearances());

        tableColumnCleanSheetsRank.setCellValueFactory(new PropertyValueFactory<>(RANK));
        tableColumnCleanSheetsName.setCellValueFactory(new PropertyValueFactory<>(NAME));
        tableColumnCleanSheetsClub.setCellValueFactory(new PropertyValueFactory<>(CLUB));
        tableColumnCleanSheetsStat.setCellValueFactory(new PropertyValueFactory<>(STAT));
        tableViewCleanSheets.setItems(initializeCleanSheets());

        if (Locale.getDefault().equals(new Locale ("en", "EN"))) tableViewCleanSheets.setTooltip(new Tooltip("Goalkeepers with most games without conceding a goal"));
        else if (Locale.getDefault().equals(new Locale("bs", "BA"))) tableViewCleanSheets.setTooltip(new Tooltip("Golmani sa najviše utakmica bez primljenog gola"));
    }


    public ObservableList<PlayerStats> initializeGoals() {
        int broj;
        ArrayList<PlayerStats> goals = new ArrayList<>();
        ArrayList<Club> clubs = new ArrayList<>(dao.clubs());
        for (Club temp : clubs) {
            ArrayList<Player> players = new ArrayList<>(dao.playersInClub(temp));
            for (Player player : players) {
                broj = 0;
                for (Goal allGoal : allGoals) {
                    if (allGoal.getScorer().equals(player)) broj++;
                }
                goals.add(new PlayerStats(player, temp, broj));
            }
        }
        goals.sort((o1, o2) -> {
            if (o2.getStat()-o1.getStat()!=0) return o2.getStat() - o1.getStat();
            else return o1.getName().getSurname().compareTo(o2.getName().getSurname());
        });
        ObservableList<PlayerStats> goalsStat = FXCollections.observableArrayList();
        for (int i=0; i<5; i++) {
            goals.get(i).setRank((i+1) + ".");
            goalsStat.add(goals.get(i));
        }
        return goalsStat;
    }

    public ObservableList<PlayerStats> initializeAssists() {
        int broj;
        ArrayList<PlayerStats> assists = new ArrayList<>();
        ArrayList<Club> clubs = new ArrayList<>(dao.clubs());
        for (Club temp : clubs) {
            ArrayList<Player> players = new ArrayList<>(dao.playersInClub(temp));
            for (Player player : players) {
                broj = 0;
                for (Goal allGoal : allGoals) {
                    if (allGoal.getAssistent() != null && allGoal.getAssistent().equals(player))
                        broj++;
                }
                assists.add(new PlayerStats(player, temp, broj));
            }
        }
        assists.sort((o1, o2) -> {
            if (o2.getStat()-o1.getStat()!=0) return o2.getStat() - o1.getStat();
            else return o1.getName().getSurname().compareTo(o2.getName().getSurname());
        });
        ObservableList<PlayerStats> assistsStat = FXCollections.observableArrayList();
        for (int i=0; i<5; i++) {
            assists.get(i).setRank((i+1) + ".");
            assistsStat.add(assists.get(i));
        }
        return assistsStat;
    }

    private <Type> ObservableList<PlayerStats> initializeGoalData(Type type, Function<Goal, Type> fun) {
        int broj;
        ArrayList<PlayerStats> list = new ArrayList<>();
        ArrayList<Club> clubs = new ArrayList<>(dao.clubs());
        for (Club temp : clubs) {
            ArrayList<Player> players = new ArrayList<>(dao.playersInClub(temp));
            for (Player player : players) {
                broj = 0;
                for (Goal allGoal : allGoals) {
                    if (allGoal.getScorer().equals(player) && fun.apply(allGoal).equals(type))
                        broj++;
                }
                list.add(new PlayerStats(player, temp, broj));
            }
        }
        list.sort((o1, o2) -> {
            if (o2.getStat()-o1.getStat()!=0) return o2.getStat() - o1.getStat();
            else return o1.getName().getSurname().compareTo(o2.getName().getSurname());
        });
        ObservableList<PlayerStats> stat = FXCollections.observableArrayList();
        for (int i=0; i<5; i++) {
            list.get(i).setRank((i+1) + ".");
            stat.add(list.get(i));
        }
        return stat;
    }

    private ObservableList<PlayerStats> initializeAppearances() {
        ArrayList<PlayerStats> appearances = new ArrayList<>();
        ArrayList<Club> clubs = new ArrayList<>(dao.clubs());
        for (Club temp : clubs) {
            ArrayList<Player> players = new ArrayList<>(dao.playersInClub(temp));
            for (Player player : players) {
                appearances.add(new PlayerStats(player, temp, dao.findStat(player.getId()).getAppearances()));
            }
        }
        appearances.sort((o1, o2) -> {
            if (o2.getStat()-o1.getStat()!=0) return o2.getStat() - o1.getStat();
            else return o1.getName().getSurname().compareTo(o2.getName().getSurname());
        });
        ObservableList<PlayerStats> appearancesStat = FXCollections.observableArrayList();
        for (int i=0; i<5; i++) {
            appearances.get(i).setRank((i+1) + ".");
            appearancesStat.add(appearances.get(i));
        }
        return appearancesStat;
    }

    private ObservableList<PlayerStats> initializeCleanSheets() {
        ArrayList<PlayerStats> cleanSheets = new ArrayList<>();
        ArrayList<Club> clubs = new ArrayList<>(dao.clubs());
        for (Club temp : clubs) {
            ArrayList<Player> players = new ArrayList<>(dao.playersInClub(temp));
            for (Player player : players) {
                if (!(player instanceof Goalkeeper)) continue;
                cleanSheets.add(new PlayerStats(player, temp, dao.findStat(player.getId()).getCleanSheets()));
            }
        }
        cleanSheets.sort((o1, o2) -> {
            if (o2.getStat()-o1.getStat()!=0) return o2.getStat() - o1.getStat();
            else return o1.getName().getSurname().compareTo(o2.getName().getSurname());
        });
        ObservableList<PlayerStats> cleanSheetsStat = FXCollections.observableArrayList();
        for (int i=0; i<5; i++) {
            cleanSheets.get(i).setRank((i+1) + ".");
            cleanSheetsStat.add(cleanSheets.get(i));
            if (cleanSheets.size()==i+1) break;
        }
        return cleanSheetsStat;
    }
}