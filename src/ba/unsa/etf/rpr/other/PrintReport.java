package ba.unsa.etf.rpr.other;

import ba.unsa.etf.rpr.beans.Club;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import javax.swing.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

public class PrintReport extends JFrame {

    Club club;

    public PrintReport(Club c) {
        this.club=c;
    }

    public void showReport(Connection conn) throws JRException {
        String reportSrcFile = getClass().getResource("/reports/team.jrxml").getFile();
        String reportsDir = getClass().getResource("/reports/").getFile();

        JasperReport jasperReport = JasperCompileManager.compileReport(reportSrcFile);
        // Fields for resources path
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("reportsDirPath", reportsDir);
        parameters.put("team", this.club);
        ArrayList<HashMap<String, Object>> list = new ArrayList<>();
        list.add(parameters);
        JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, conn);
        JasperViewer viewer = new JasperViewer(print, false);
        viewer.setVisible(true);
    }
}
