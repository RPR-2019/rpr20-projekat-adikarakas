package ba.unsa.etf.rpr;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.Comparator;

public class StatsController {
    public TableView tableViewGoals = new TableView();
    public TableView tableViewAssists = new TableView();
    public TableView tableViewCleanSheets = new TableView();
    public TableView tableViewApperances = new TableView();
    public TableView tableViewInside = new TableView();
    public TableView tableViewOutside = new TableView();
    public TableView tableViewRight = new TableView();
    public TableView tableViewLeft = new TableView();
    public TableView tableViewHead = new TableView();
    public TableView tableViewPenalties = new TableView();
    public TableView tableViewFreeKicks = new TableView();
    public TableView tableViewOpen = new TableView();

    public TableColumn tableColumnGoalsRank, tableColumnGoalsName, tableColumnGoalsClub, tableColumnGoalsStat;
    public TableColumn tableColumnAssistsRank, tableColumnAssistsName, tableColumnAssistsClub, tableColumnAssistsStat;
    public TableColumn tableColumnCleanSheetsRank, tableColumnCleanSheetsName, tableColumnCleanSheetsClub, tableColumnCleanSheetsStat;
    public TableColumn tableColumnApperancesRank, tableColumnApperancesName, tableColumnApperancesClub, tableColumnApperancesStat;
    public TableColumn tableColumnInsideRank, tableColumnInsideName, tableColumnInsideClub, tableColumnInsideStat;
    public TableColumn tableColumnOutsideRank, tableColumnOutsideName, tableColumnOutsideClub, tableColumnOutsideStat;
    public TableColumn tableColumnRightRank, tableColumnRightName, tableColumnRightClub, tableColumnRightStat;
    public TableColumn tableColumnLeftRank, tableColumnLeftName, tableColumnLeftClub, tableColumnLeftStat;
    public TableColumn tableColumnHeadRank, tableColumnHeadName, tableColumnHeadClub, tableColumnHeadStat;
    public TableColumn tableColumnPenaltiesRank, tableColumnPenaltiesName, tableColumnPenaltiesClub, tableColumnPenaltiesStat;
    public TableColumn tableColumnFreeKicksRank, tableColumnFreeKicksName, tableColumnFreeKicksClub, tableColumnFreeKicksStat;
    public TableColumn tableColumnOpenRank, tableColumnOpenName, tableColumnOpenClub, tableColumnOpenStat;

    private LeagueDAO dao;

    StatsController() {
    }

    @FXML
    public void initialize() {

        // golovi
        tableColumnGoalsRank.setCellValueFactory(new PropertyValueFactory<PlayerStats, String>("rank"));
        tableColumnGoalsName.setCellValueFactory(new PropertyValueFactory<PlayerStats, Player>("name"));
        tableColumnGoalsClub.setCellValueFactory(new PropertyValueFactory<PlayerStats, Club>("club"));
        tableColumnGoalsStat.setCellValueFactory(new PropertyValueFactory<PlayerStats, Integer>("stat"));

        int broj=0;
        ArrayList<PlayerStats> goals = new ArrayList<>();
        for (int i=0; i<dao.getInstance().clubs().size(); i++) {
            for (int j=0; j<dao.getInstance().playersInClub(dao.getInstance().clubs().get(i)).size(); j++) {
                broj = 0;
                for (int k=0; k<dao.getInstance().goals().size(); k++) {
                    if (dao.getInstance().goals().get(k).getScorer().equals(dao.getInstance().playersInClub(dao.getInstance().clubs().get(i)).get(j))) broj++;
                }
                goals.add(new PlayerStats(dao.getInstance().playersInClub(dao.getInstance().clubs().get(i)).get(j), dao.getInstance().clubs().get(i), broj));
            }
        }
        goals.sort(new Comparator<PlayerStats>() {
            @Override
            public int compare(PlayerStats o1, PlayerStats o2) {
                if (o2.getStat()-o1.getStat()!=0) return o2.getStat() - o1.getStat();
                else return o1.getName().getSurname().compareTo(o2.getName().getSurname());
            }
        });
        ObservableList goalsStat = FXCollections.observableArrayList();
        for (int i=0; i<5; i++) {
            goals.get(i).setRank((i+1) + ".");
            goalsStat.add(goals.get(i));
        }
        tableViewGoals.setItems(goalsStat);


        // asistencije

        tableColumnAssistsRank.setCellValueFactory(new PropertyValueFactory<PlayerStats, String>("rank"));
        tableColumnAssistsName.setCellValueFactory(new PropertyValueFactory<PlayerStats, Player>("name"));
        tableColumnAssistsClub.setCellValueFactory(new PropertyValueFactory<PlayerStats, Club>("club"));
        tableColumnAssistsStat.setCellValueFactory(new PropertyValueFactory<PlayerStats, Integer>("stat"));

        ArrayList<PlayerStats> assists = new ArrayList<>();
        for (int i=0; i<dao.getInstance().clubs().size(); i++) {
            for (int j=0; j<dao.getInstance().playersInClub(dao.getInstance().clubs().get(i)).size(); j++) {
                broj = 0;
                for (int k=0; k<dao.getInstance().goals().size(); k++) {
                    if (dao.getInstance().goals().get(k).getAssistent()!=null && dao.getInstance().goals().get(k).getAssistent().equals(dao.getInstance().playersInClub(dao.getInstance().clubs().get(i)).get(j))) broj++;
                }
                assists.add(new PlayerStats(dao.getInstance().playersInClub(dao.getInstance().clubs().get(i)).get(j), dao.getInstance().clubs().get(i), broj));
            }
        }
        assists.sort(new Comparator<PlayerStats>() {
            @Override
            public int compare(PlayerStats o1, PlayerStats o2) {
                if (o2.getStat()-o1.getStat()!=0) return o2.getStat() - o1.getStat();
                else return o1.getName().getSurname().compareTo(o2.getName().getSurname());
            }
        });
        ObservableList assistsStat = FXCollections.observableArrayList();
        for (int i=0; i<5; i++) {
            assists.get(i).setRank((i+1) + ".");
            assistsStat.add(assists.get(i));
        }
        tableViewAssists.setItems(assistsStat);

        // golovi iz sesnaesterca

        tableColumnInsideRank.setCellValueFactory(new PropertyValueFactory<PlayerStats, String>("rank"));
        tableColumnInsideName.setCellValueFactory(new PropertyValueFactory<PlayerStats, Player>("name"));
        tableColumnInsideClub.setCellValueFactory(new PropertyValueFactory<PlayerStats, Club>("club"));
        tableColumnInsideStat.setCellValueFactory(new PropertyValueFactory<PlayerStats, Integer>("stat"));

        broj=0;
        ArrayList<PlayerStats> inside = new ArrayList<>();
        for (int i=0; i<dao.getInstance().clubs().size(); i++) {
            for (int j=0; j<dao.getInstance().playersInClub(dao.getInstance().clubs().get(i)).size(); j++) {
                broj = 0;
                for (int k=0; k<dao.getInstance().goals().size(); k++) {
                    if (dao.getInstance().goals().get(k).getScorer().equals(dao.getInstance().playersInClub(dao.getInstance().clubs().get(i)).get(j)) && dao.getInstance().goals().get(k).getGoalDistance().equals(GoalDistance.INSIDEBOX)) broj++;
                }
                inside.add(new PlayerStats(dao.getInstance().playersInClub(dao.getInstance().clubs().get(i)).get(j), dao.getInstance().clubs().get(i), broj));
            }
        }
        inside.sort(new Comparator<PlayerStats>() {
            @Override
            public int compare(PlayerStats o1, PlayerStats o2) {
                if (o2.getStat()-o1.getStat()!=0) return o2.getStat() - o1.getStat();
                else return o1.getName().getSurname().compareTo(o2.getName().getSurname());
            }
        });
        ObservableList insideStat = FXCollections.observableArrayList();
        for (int i=0; i<5; i++) {
            inside.get(i).setRank((i+1) + ".");
            insideStat.add(inside.get(i));
        }
        tableViewInside.setItems(insideStat);



        tableColumnOutsideRank.setCellValueFactory(new PropertyValueFactory<PlayerStats, String>("rank"));
        tableColumnOutsideName.setCellValueFactory(new PropertyValueFactory<PlayerStats, Player>("name"));
        tableColumnOutsideClub.setCellValueFactory(new PropertyValueFactory<PlayerStats, Club>("club"));
        tableColumnOutsideStat.setCellValueFactory(new PropertyValueFactory<PlayerStats, Integer>("stat"));

        broj=0;
        ArrayList<PlayerStats> outside = new ArrayList<>();
        for (int i=0; i<dao.getInstance().clubs().size(); i++) {
            for (int j=0; j<dao.getInstance().playersInClub(dao.getInstance().clubs().get(i)).size(); j++) {
                broj = 0;
                for (int k=0; k<dao.getInstance().goals().size(); k++) {
                    if (dao.getInstance().goals().get(k).getScorer().equals(dao.getInstance().playersInClub(dao.getInstance().clubs().get(i)).get(j)) && dao.getInstance().goals().get(k).getGoalDistance().equals(GoalDistance.OUTSIDEBOX)) broj++;
                }
                outside.add(new PlayerStats(dao.getInstance().playersInClub(dao.getInstance().clubs().get(i)).get(j), dao.getInstance().clubs().get(i), broj));
            }
        }
        outside.sort(new Comparator<PlayerStats>() {
            @Override
            public int compare(PlayerStats o1, PlayerStats o2) {
                if (o2.getStat()-o1.getStat()!=0) return o2.getStat() - o1.getStat();
                else return o1.getName().getSurname().compareTo(o2.getName().getSurname());
            }
        });
        ObservableList outsideStat = FXCollections.observableArrayList();
        for (int i=0; i<5; i++) {
            outside.get(i).setRank((i+1) + ".");
            outsideStat.add(outside.get(i));
        }
        tableViewOutside.setItems(outsideStat);



        tableColumnRightRank.setCellValueFactory(new PropertyValueFactory<PlayerStats, String>("rank"));
        tableColumnRightName.setCellValueFactory(new PropertyValueFactory<PlayerStats, Player>("name"));
        tableColumnRightClub.setCellValueFactory(new PropertyValueFactory<PlayerStats, Club>("club"));
        tableColumnRightStat.setCellValueFactory(new PropertyValueFactory<PlayerStats, Integer>("stat"));

        broj=0;
        ArrayList<PlayerStats> right = new ArrayList<>();
        for (int i=0; i<dao.getInstance().clubs().size(); i++) {
            for (int j=0; j<this.dao.getInstance().playersInClub(dao.getInstance().clubs().get(i)).size(); j++) {
                broj = 0;
                for (int k=0; k<dao.getInstance().goals().size(); k++) {
                    if (dao.getInstance().goals().get(k).getScorer().equals(dao.getInstance().playersInClub(dao.getInstance().clubs().get(i)).get(j)) && dao.getInstance().goals().get(k).getGoalType().equals(GoalType.RIGHTFOOT)) broj++;
                }
                right.add(new PlayerStats(dao.getInstance().playersInClub(dao.getInstance().clubs().get(i)).get(j), dao.getInstance().clubs().get(i), broj));
            }
        }
        right.sort(new Comparator<PlayerStats>() {
            @Override
            public int compare(PlayerStats o1, PlayerStats o2) {
                if (o2.getStat()-o1.getStat()!=0) return o2.getStat() - o1.getStat();
                else return o1.getName().getSurname().compareTo(o2.getName().getSurname());
            }
        });
        ObservableList rightStat = FXCollections.observableArrayList();
        for (int i=0; i<5; i++) {
            right.get(i).setRank((i+1) + ".");
            rightStat.add(right.get(i));
        }
        tableViewRight.setItems(rightStat);



        tableColumnLeftRank.setCellValueFactory(new PropertyValueFactory<PlayerStats, String>("rank"));
        tableColumnLeftName.setCellValueFactory(new PropertyValueFactory<PlayerStats, Player>("name"));
        tableColumnLeftClub.setCellValueFactory(new PropertyValueFactory<PlayerStats, Club>("club"));
        tableColumnLeftStat.setCellValueFactory(new PropertyValueFactory<PlayerStats, Integer>("stat"));

        broj=0;
        ArrayList<PlayerStats> left = new ArrayList<>();
        for (int i=0; i<dao.getInstance().clubs().size(); i++) {
            for (int j=0; j<dao.getInstance().playersInClub(dao.getInstance().clubs().get(i)).size(); j++) {
                broj = 0;
                for (int k=0; k<dao.getInstance().goals().size(); k++) {
                    if (dao.getInstance().goals().get(k).getScorer().equals(dao.getInstance().playersInClub(dao.getInstance().clubs().get(i)).get(j)) && dao.getInstance().goals().get(k).getGoalType().equals(GoalType.LEFTFOOT)) broj++;
                }
                left.add(new PlayerStats(dao.getInstance().playersInClub(dao.getInstance().clubs().get(i)).get(j), dao.getInstance().clubs().get(i), broj));
            }
        }
        left.sort(new Comparator<PlayerStats>() {
            @Override
            public int compare(PlayerStats o1, PlayerStats o2) {
                if (o2.getStat()-o1.getStat()!=0) return o2.getStat() - o1.getStat();
                else return o1.getName().getSurname().compareTo(o2.getName().getSurname());
            }
        });
        ObservableList leftStat = FXCollections.observableArrayList();
        for (int i=0; i<5; i++) {
            left.get(i).setRank((i+1) + ".");
            leftStat.add(left.get(i));
        }
        tableViewLeft.setItems(leftStat);


        tableColumnHeadRank.setCellValueFactory(new PropertyValueFactory<PlayerStats, String>("rank"));
        tableColumnHeadName.setCellValueFactory(new PropertyValueFactory<PlayerStats, Player>("name"));
        tableColumnHeadClub.setCellValueFactory(new PropertyValueFactory<PlayerStats, Club>("club"));
        tableColumnHeadStat.setCellValueFactory(new PropertyValueFactory<PlayerStats, Integer>("stat"));

        broj=0;
        ArrayList<PlayerStats> head = new ArrayList<>();
        for (int i=0; i<dao.getInstance().clubs().size(); i++) {
            for (int j=0; j<dao.getInstance().playersInClub(dao.getInstance().clubs().get(i)).size(); j++) {
                broj = 0;
                for (int k=0; k<dao.getInstance().goals().size(); k++) {
                    if (dao.getInstance().goals().get(k).getScorer().equals(dao.getInstance().playersInClub(dao.getInstance().clubs().get(i)).get(j)) && dao.getInstance().goals().get(k).getGoalType().equals(GoalType.HEADER)) broj++;
                }
                head.add(new PlayerStats(dao.getInstance().playersInClub(dao.getInstance().clubs().get(i)).get(j), dao.getInstance().clubs().get(i), broj));
            }
        }
        head.sort(new Comparator<PlayerStats>() {
            @Override
            public int compare(PlayerStats o1, PlayerStats o2) {
                if (o2.getStat()-o1.getStat()!=0) return o2.getStat() - o1.getStat();
                else return o1.getName().getSurname().compareTo(o2.getName().getSurname());
            }
        });
        ObservableList headStat = FXCollections.observableArrayList();
        for (int i=0; i<5; i++) {
            head.get(i).setRank((i+1) + ".");
            headStat.add(head.get(i));
        }
        tableViewHead.setItems(headStat);



        tableColumnPenaltiesRank.setCellValueFactory(new PropertyValueFactory<PlayerStats, String>("rank"));
        tableColumnPenaltiesName.setCellValueFactory(new PropertyValueFactory<PlayerStats, Player>("name"));
        tableColumnPenaltiesClub.setCellValueFactory(new PropertyValueFactory<PlayerStats, Club>("club"));
        tableColumnPenaltiesStat.setCellValueFactory(new PropertyValueFactory<PlayerStats, Integer>("stat"));

        broj=0;
        ArrayList<PlayerStats> penalties = new ArrayList<>();
        for (int i=0; i<dao.getInstance().clubs().size(); i++) {
            for (int j=0; j<dao.getInstance().playersInClub(dao.getInstance().clubs().get(i)).size(); j++) {
                broj = 0;
                for (int k=0; k<dao.getInstance().goals().size(); k++) {
                    if (dao.getInstance().goals().get(k).getScorer().equals(dao.getInstance().playersInClub(dao.getInstance().clubs().get(i)).get(j)) && dao.getInstance().goals().get(k).getGoalSituation().equals(GoalSituation.PENALTY)) broj++;
                }
                penalties.add(new PlayerStats(dao.getInstance().playersInClub(dao.getInstance().clubs().get(i)).get(j), dao.getInstance().clubs().get(i), broj));
            }
        }
        penalties.sort(new Comparator<PlayerStats>() {
            @Override
            public int compare(PlayerStats o1, PlayerStats o2) {
                if (o2.getStat()-o1.getStat()!=0) return o2.getStat() - o1.getStat();
                else return o1.getName().getSurname().compareTo(o2.getName().getSurname());
            }
        });
        ObservableList penaltiesStat = FXCollections.observableArrayList();
        for (int i=0; i<5; i++) {
            penalties.get(i).setRank((i+1) + ".");
            penaltiesStat.add(penalties.get(i));
        }
        tableViewPenalties.setItems(penaltiesStat);


        tableColumnFreeKicksRank.setCellValueFactory(new PropertyValueFactory<PlayerStats, String>("rank"));
        tableColumnFreeKicksName.setCellValueFactory(new PropertyValueFactory<PlayerStats, Player>("name"));
        tableColumnFreeKicksClub.setCellValueFactory(new PropertyValueFactory<PlayerStats, Club>("club"));
        tableColumnFreeKicksStat.setCellValueFactory(new PropertyValueFactory<PlayerStats, Integer>("stat"));

        broj=0;
        ArrayList<PlayerStats> freeKicks = new ArrayList<>();
        for (int i=0; i<dao.getInstance().clubs().size(); i++) {
            for (int j=0; j<dao.getInstance().playersInClub(dao.getInstance().clubs().get(i)).size(); j++) {
                broj = 0;
                for (int k=0; k<dao.getInstance().goals().size(); k++) {
                    if (dao.getInstance().goals().get(k).getScorer().equals(dao.getInstance().playersInClub(dao.getInstance().clubs().get(i)).get(j)) && dao.getInstance().goals().get(k).getGoalSituation().equals(GoalSituation.FREEKICK)) broj++;
                }
                freeKicks.add(new PlayerStats(dao.getInstance().playersInClub(dao.getInstance().clubs().get(i)).get(j), dao.getInstance().clubs().get(i), broj));
            }
        }
        freeKicks.sort(new Comparator<PlayerStats>() {
            @Override
            public int compare(PlayerStats o1, PlayerStats o2) {
                if (o2.getStat()-o1.getStat()!=0) return o2.getStat() - o1.getStat();
                else return o1.getName().getSurname().compareTo(o2.getName().getSurname());
            }
        });
        ObservableList freeKicksStats = FXCollections.observableArrayList();
        for (int i=0; i<5; i++) {
            freeKicks.get(i).setRank((i+1) + ".");
            freeKicksStats.add(freeKicks.get(i));
        }
        tableViewFreeKicks.setItems(freeKicksStats);


        tableColumnOpenRank.setCellValueFactory(new PropertyValueFactory<PlayerStats, String>("rank"));
        tableColumnOpenName.setCellValueFactory(new PropertyValueFactory<PlayerStats, Player>("name"));
        tableColumnOpenClub.setCellValueFactory(new PropertyValueFactory<PlayerStats, Club>("club"));
        tableColumnOpenStat.setCellValueFactory(new PropertyValueFactory<PlayerStats, Integer>("stat"));

        broj=0;
        ArrayList<PlayerStats> openPlays = new ArrayList<>();
        for (int i=0; i<dao.getInstance().clubs().size(); i++) {
            for (int j=0; j<dao.getInstance().playersInClub(dao.getInstance().clubs().get(i)).size(); j++) {
                broj = 0;
                for (int k=0; k<dao.getInstance().goals().size(); k++) {
                    if (dao.getInstance().goals().get(k).getScorer().equals(dao.getInstance().playersInClub(dao.getInstance().clubs().get(i)).get(j)) && dao.getInstance().goals().get(k).getGoalSituation().equals(GoalSituation.OPENPLAY)) broj++;
                }
                openPlays.add(new PlayerStats(dao.getInstance().playersInClub(dao.getInstance().clubs().get(i)).get(j), dao.getInstance().clubs().get(i), broj));
            }
        }
        openPlays.sort(new Comparator<PlayerStats>() {
            @Override
            public int compare(PlayerStats o1, PlayerStats o2) {
                if (o2.getStat()-o1.getStat()!=0) return o2.getStat() - o1.getStat();
                else return o1.getName().getSurname().compareTo(o2.getName().getSurname());
            }
        });
        ObservableList openPlaysStat = FXCollections.observableArrayList();
        for (int i=0; i<5; i++) {
            openPlays.get(i).setRank((i+1) + ".");
            openPlaysStat.add(openPlays.get(i));
        }
        tableViewOpen.setItems(openPlaysStat);



        tableColumnApperancesRank.setCellValueFactory(new PropertyValueFactory<PlayerStats, String>("rank"));
        tableColumnApperancesName.setCellValueFactory(new PropertyValueFactory<PlayerStats, Player>("name"));
        tableColumnApperancesClub.setCellValueFactory(new PropertyValueFactory<PlayerStats, Club>("club"));
        tableColumnApperancesStat.setCellValueFactory(new PropertyValueFactory<PlayerStats, Integer>("stat"));

        broj=0;
        ArrayList<PlayerStats> apperances = new ArrayList<>();
        for (int i=0; i<dao.getInstance().clubs().size(); i++) {
            for (int j=0; j<dao.getInstance().playersInClub(dao.getInstance().clubs().get(i)).size(); j++) {
                apperances.add(new PlayerStats(dao.getInstance().playersInClub(dao.getInstance().clubs().get(i)).get(j), dao.getInstance().clubs().get(i), dao.getInstance().findStat(dao.getInstance().playersInClub(dao.getInstance().clubs().get(i)).get(j).getId()).getApperances()));
            }
        }
        apperances.sort(new Comparator<PlayerStats>() {
            @Override
            public int compare(PlayerStats o1, PlayerStats o2) {
                if (o2.getStat()-o1.getStat()!=0) return o2.getStat() - o1.getStat();
                else return o1.getName().getSurname().compareTo(o2.getName().getSurname());
            }
        });
        ObservableList apperancesStat = FXCollections.observableArrayList();
        for (int i=0; i<5; i++) {
            apperances.get(i).setRank((i+1) + ".");
            apperancesStat.add(apperances.get(i));
        }
        tableViewApperances.setItems(apperancesStat);


        tableColumnCleanSheetsRank.setCellValueFactory(new PropertyValueFactory<PlayerStats, String>("rank"));
        tableColumnCleanSheetsName.setCellValueFactory(new PropertyValueFactory<PlayerStats, Player>("name"));
        tableColumnCleanSheetsClub.setCellValueFactory(new PropertyValueFactory<PlayerStats, Club>("club"));
        tableColumnCleanSheetsStat.setCellValueFactory(new PropertyValueFactory<PlayerStats, Integer>("stat"));

        broj=0;
        ArrayList<PlayerStats> cleanSheets = new ArrayList<>();
        for (int i=0; i<dao.getInstance().clubs().size(); i++) {
            for (int j=0; j<dao.getInstance().playersInClub(dao.getInstance().clubs().get(i)).size(); j++) {
                cleanSheets.add(new PlayerStats(dao.getInstance().playersInClub(dao.getInstance().clubs().get(i)).get(j), dao.getInstance().clubs().get(i), dao.getInstance().findStat(dao.getInstance().playersInClub(dao.getInstance().clubs().get(i)).get(j).getId()).getCleanSheets()));
            }
        }
        cleanSheets.sort(new Comparator<PlayerStats>() {
            @Override
            public int compare(PlayerStats o1, PlayerStats o2) {
                if (o2.getStat()-o1.getStat()!=0) return o2.getStat() - o1.getStat();
                else return o1.getName().getSurname().compareTo(o2.getName().getSurname());
            }
        });
        ObservableList cleanSheetsStat = FXCollections.observableArrayList();
        for (int i=0; i<5; i++) {
            cleanSheets.get(i).setRank((i+1) + ".");
            cleanSheetsStat.add(cleanSheets.get(i));
        }
        tableViewCleanSheets.setItems(cleanSheetsStat);
    }
}
