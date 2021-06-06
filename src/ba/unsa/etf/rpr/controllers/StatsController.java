package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.beans.Club;
import ba.unsa.etf.rpr.beans.Goal;
import ba.unsa.etf.rpr.beans.Player;
import ba.unsa.etf.rpr.beans.PlayerStats;
import ba.unsa.etf.rpr.other.GoalDistance;
import ba.unsa.etf.rpr.other.GoalSituation;
import ba.unsa.etf.rpr.other.GoalType;
import ba.unsa.etf.rpr.other.LeagueDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

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
    private LeagueDAO dao;
    private ArrayList<Goal> allGoals;

    StatsController() {
        dao=LeagueDAO.getInstance();
        allGoals= new ArrayList<>(dao.goals());
    }

    @FXML
    public void initialize() {
        tableColumnGoalsRank.setCellValueFactory(new PropertyValueFactory<>("rank"));
        tableColumnGoalsName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnGoalsClub.setCellValueFactory(new PropertyValueFactory<>("club"));
        tableColumnGoalsStat.setCellValueFactory(new PropertyValueFactory<>("stat"));
        tableViewGoals.setItems(initializeGoals());

        tableColumnAssistsRank.setCellValueFactory(new PropertyValueFactory<>("rank"));
        tableColumnAssistsName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnAssistsClub.setCellValueFactory(new PropertyValueFactory<>("club"));
        tableColumnAssistsStat.setCellValueFactory(new PropertyValueFactory<>("stat"));
        tableViewAssists.setItems(initializeAssists());

        tableColumnInsideRank.setCellValueFactory(new PropertyValueFactory<>("rank"));
        tableColumnInsideName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnInsideClub.setCellValueFactory(new PropertyValueFactory<>("club"));
        tableColumnInsideStat.setCellValueFactory(new PropertyValueFactory<>("stat"));
        tableViewInside.setItems(initializeDistance(GoalDistance.INSIDEBOX));

        tableColumnOutsideRank.setCellValueFactory(new PropertyValueFactory<>("rank"));
        tableColumnOutsideName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnOutsideClub.setCellValueFactory(new PropertyValueFactory<>("club"));
        tableColumnOutsideStat.setCellValueFactory(new PropertyValueFactory<>("stat"));
        tableViewOutside.setItems(initializeDistance(GoalDistance.OUTSIDEBOX));

        tableColumnRightRank.setCellValueFactory(new PropertyValueFactory<>("rank"));
        tableColumnRightName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnRightClub.setCellValueFactory(new PropertyValueFactory<>("club"));
        tableColumnRightStat.setCellValueFactory(new PropertyValueFactory<>("stat"));
        tableViewRight.setItems(initializeType(GoalType.RIGHTFOOT));

        tableColumnLeftRank.setCellValueFactory(new PropertyValueFactory<>("rank"));
        tableColumnLeftName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnLeftClub.setCellValueFactory(new PropertyValueFactory<>("club"));
        tableColumnLeftStat.setCellValueFactory(new PropertyValueFactory<>("stat"));
        tableViewLeft.setItems(initializeType(GoalType.LEFTFOOT));

        tableColumnHeadRank.setCellValueFactory(new PropertyValueFactory<>("rank"));
        tableColumnHeadName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnHeadClub.setCellValueFactory(new PropertyValueFactory<>("club"));
        tableColumnHeadStat.setCellValueFactory(new PropertyValueFactory<>("stat"));
        tableViewHead.setItems(initializeType(GoalType.HEADER));

        tableColumnPenaltiesRank.setCellValueFactory(new PropertyValueFactory<>("rank"));
        tableColumnPenaltiesName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnPenaltiesClub.setCellValueFactory(new PropertyValueFactory<>("club"));
        tableColumnPenaltiesStat.setCellValueFactory(new PropertyValueFactory<>("stat"));
        tableViewPenalties.setItems(initializeSituation(GoalSituation.PENALTY));

        tableColumnFreeKicksRank.setCellValueFactory(new PropertyValueFactory<>("rank"));
        tableColumnFreeKicksName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnFreeKicksClub.setCellValueFactory(new PropertyValueFactory<>("club"));
        tableColumnFreeKicksStat.setCellValueFactory(new PropertyValueFactory<>("stat"));
        tableViewFreeKicks.setItems(initializeSituation(GoalSituation.FREEKICK));

        tableColumnOpenRank.setCellValueFactory(new PropertyValueFactory<>("rank"));
        tableColumnOpenName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnOpenClub.setCellValueFactory(new PropertyValueFactory<>("club"));
        tableColumnOpenStat.setCellValueFactory(new PropertyValueFactory<>("stat"));
        tableViewOpen.setItems(initializeSituation(GoalSituation.OPENPLAY));

        tableColumnAppearancesRank.setCellValueFactory(new PropertyValueFactory<>("rank"));
        tableColumnAppearancesName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnAppearancesClub.setCellValueFactory(new PropertyValueFactory<>("club"));
        tableColumnAppearancesStat.setCellValueFactory(new PropertyValueFactory<>("stat"));
        tableViewAppearances.setItems(initializeAppearances());

        tableColumnCleanSheetsRank.setCellValueFactory(new PropertyValueFactory<>("rank"));
        tableColumnCleanSheetsName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnCleanSheetsClub.setCellValueFactory(new PropertyValueFactory<>("club"));
        tableColumnCleanSheetsStat.setCellValueFactory(new PropertyValueFactory<>("stat"));
        tableViewCleanSheets.setItems(initializeCleanSheets());
    }


    public ObservableList<PlayerStats> initializeGoals() {
        int broj;
        ArrayList<PlayerStats> goals = new ArrayList<>();
        ArrayList<Club> clubs = new ArrayList<>(dao.clubs());
        for (int i=0; i<clubs.size(); i++) {
            Club temp = clubs.get(i);
            ArrayList<Player> players = new ArrayList<>(dao.playersInClub(temp));
            for (int j=0; j<players.size(); j++) {
                broj = 0;
                for (int k=0; k<allGoals.size(); k++) {
                    if (allGoals.get(k).getScorer().equals(players.get(j))) broj++;
                }
                goals.add(new PlayerStats(players.get(j), temp, broj));
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
        for (int i=0; i<clubs.size(); i++) {
            Club temp = clubs.get(i);
            ArrayList<Player> players = new ArrayList<>(dao.playersInClub(temp));
            for (int j=0; j<players.size(); j++) {
                broj = 0;
                for (int k=0; k<allGoals.size(); k++) {
                    if (allGoals.get(k).getAssistent()!=null && allGoals.get(k).getAssistent().equals(players.get(j))) broj++;
                }
                assists.add(new PlayerStats(players.get(j), temp, broj));
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

    private ObservableList<PlayerStats> initializeDistance(GoalDistance distance) {
        int broj;
        ArrayList<PlayerStats> list = new ArrayList<>();
        ArrayList<Club> clubs = new ArrayList<>(dao.clubs());
        for (int i=0; i<clubs.size(); i++) {
            Club temp = clubs.get(i);
            ArrayList<Player> players = new ArrayList<>(dao.playersInClub(temp));
            for (int j=0; j<players.size(); j++) {
                broj = 0;
                for (int k=0; k<allGoals.size(); k++) {
                    if (allGoals.get(k).getScorer().equals(players.get(j)) && allGoals.get(k).getGoalDistance().equals(distance)) broj++;
                }
                list.add(new PlayerStats(players.get(j), temp, broj));
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

    private ObservableList<PlayerStats> initializeType(GoalType type) {
        int broj;
        ArrayList<PlayerStats> list = new ArrayList<>();
        ArrayList<Club> clubs = new ArrayList<>(dao.clubs());
        for (int i = 0; i < clubs.size(); i++) {
            Club temp = clubs.get(i);
            ArrayList<Player> players = new ArrayList<>(dao.playersInClub(temp));
            for (int j = 0; j < players.size(); j++) {
                broj = 0;
                for (int k = 0; k < allGoals.size(); k++) {
                    if (allGoals.get(k).getScorer().equals(players.get(j)) && allGoals.get(k).getGoalType().equals(type))
                        broj++;
                }
                list.add(new PlayerStats(players.get(j), temp, broj));
            }
        }
        list.sort((o1, o2) -> {
            if (o2.getStat() - o1.getStat() != 0) return o2.getStat() - o1.getStat();
            else return o1.getName().getSurname().compareTo(o2.getName().getSurname());
        });
        ObservableList<PlayerStats> stat = FXCollections.observableArrayList();
        for (int i = 0; i < 5; i++) {
            list.get(i).setRank((i + 1) + ".");
            stat.add(list.get(i));
        }
        return stat;
    }

    private ObservableList<PlayerStats> initializeSituation(GoalSituation situation) {
        int broj;
        ArrayList<PlayerStats> list = new ArrayList<>();
        ArrayList<Club> clubs = new ArrayList<>(dao.clubs());
        for (int i=0; i<clubs.size(); i++) {
            Club temp = clubs.get(i);
            ArrayList<Player> players = new ArrayList<>(dao.playersInClub(temp));
            for (int j=0; j<players.size(); j++) {
                broj = 0;
                for (int k=0; k<allGoals.size(); k++) {
                    if (allGoals.get(k).getScorer().equals(players.get(j)) && allGoals.get(k).getGoalSituation().equals(situation)) broj++;
                }
                list.add(new PlayerStats(players.get(j), temp, broj));
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
        for (int i=0; i<clubs.size(); i++) {
            Club temp = clubs.get(i);
            ArrayList<Player> players = new ArrayList<>(dao.playersInClub(temp));
            for (int j=0; j<players.size(); j++) {
                appearances.add(new PlayerStats(players.get(j), temp, dao.findStat(players.get(j).getId()).getAppearances()));
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
        for (int i=0; i<clubs.size(); i++) {
            Club temp = clubs.get(i);
            ArrayList<Player> players = new ArrayList<>(dao.playersInClub(temp));
            for (int j=0; j<players.size(); j++) {
                cleanSheets.add(new PlayerStats(players.get(j), temp, dao.findStat(players.get(j).getId()).getCleanSheets()));
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
        }
        return cleanSheetsStat;
    }
}