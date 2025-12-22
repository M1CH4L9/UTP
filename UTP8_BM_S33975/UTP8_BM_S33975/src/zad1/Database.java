package zad1;

import java.sql.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.*;
import java.util.List;

public class Database {

    private String url;
    private TravelData travelData;
    private JFrame frame;
    private JTable table;
    private DefaultTableModel model;

    public Database(String url, TravelData travelData) {
        this.url = url;
        this.travelData = travelData;
    }

    public void create() {
        try {
            Connection connection = DriverManager.getConnection(url);
            Statement stmt = connection.createStatement();

            try {
                stmt.execute("DROP TABLE Offers");
            } catch (Exception e) {

            }

            String sql = "CREATE TABLE Offers (" +
                    "id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " +
                    "country VARCHAR(50), " +
                    "dateStart DATE, " +
                    "dateEnd DATE, " +
                    "place VARCHAR(50), " +
                    "price DOUBLE, " +
                    "currency VARCHAR(10), " +
                    "origLocale VARCHAR(20))";
            stmt.execute(sql);

            String insert = "INSERT INTO Offers (country, dateStart, dateEnd, place, price, currency, origLocale) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(insert);

            List<Offer> list = travelData.getOffersList();
            for (int i = 0; i < list.size(); i++) {
                Offer o = list.get(i);
                pstmt.setString(1, o.country);
                pstmt.setDate(2, new java.sql.Date(o.dateStart.getTime()));
                pstmt.setDate(3, new java.sql.Date(o.dateEnd.getTime()));
                pstmt.setString(4, o.place);
                pstmt.setDouble(5, o.price);
                pstmt.setString(6, o.currency);
                pstmt.setString(7, o.locale.toString());
                pstmt.executeUpdate();
            }

            pstmt.close();
            stmt.close();
            connection.close();

        } catch (Exception e) {
            // Główny catch dla błędów połączenia lub SQL
            e.printStackTrace();
        }
    } // Tutaj kończy się metoda create(). Wcześniej brakowało tego nawiasu.

    public void showGui() {
        frame = new JFrame("Oceń ofertę / Travel Offers");
        frame.setSize(800, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        model = new DefaultTableModel();
        model.addColumn("Kraj");
        model.addColumn("Wyjazd");
        model.addColumn("Powrót");
        model.addColumn("Miejsce");
        model.addColumn("Cena");
        model.addColumn("Waluta");

        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        frame.add(scroll, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        String[] jezyki = {"pl_PL", "en_GB"};
        JComboBox<String> combo = new JComboBox<>(jezyki);
        panel.add(new JLabel("Wybierz język / Select language:"));
        panel.add(combo);
        frame.add(panel, BorderLayout.NORTH);

        combo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String chosen = (String) combo.getSelectedItem();
                refreshTable(chosen);
            }
        });

        refreshTable("pl_PL");

        frame.setVisible(true);
    }

    private void refreshTable(String locStr) {
        model.setRowCount(0);

        String[] locArr = locStr.split("_");
        Locale locale = new Locale(locArr[0], locArr[1]);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        List<Offer> lista = travelData.getOffersList();

        for (int i = 0; i < lista.size(); i++) {
            Offer o = lista.get(i);

            String kraj = o.country;
            Locale[] dostepne = Locale.getAvailableLocales();
            for (int j = 0; j < dostepne.length; j++) {
                if (dostepne[j].getDisplayCountry(o.locale).equals(o.country)) {
                    kraj = dostepne[j].getDisplayCountry(locale);
                    break;
                }
            }

            String miejsce = o.place; // prymitywna logika powtórzona
            if (miejsce.equals("jezioro") || miejsce.equals("See")) miejsce = "lake";
            if (miejsce.equals("morze") || miejsce.equals("Meer")) miejsce = "sea";
            if (miejsce.equals("góry") || miejsce.equals("Gebirge")) miejsce = "mountains";

            if (locale.getLanguage().equals("pl")) {
                if (miejsce.equals("lake")) miejsce = "jezioro";
                if (miejsce.equals("sea")) miejsce = "morze";
                if (miejsce.equals("mountains")) miejsce = "góry";
            } else if (locale.getLanguage().equals("en")) {
                if (miejsce.equals("jezioro")) miejsce = "lake";
                if (miejsce.equals("morze")) miejsce = "sea";
                if (miejsce.equals("góry")) miejsce = "mountains";
            }

            NumberFormat nf = NumberFormat.getInstance(locale);
            if (locale.getLanguage().equals("pl")) {
                nf.setMinimumFractionDigits(0);
                nf.setMaximumFractionDigits(2);
            } else {
                nf.setMinimumFractionDigits(1);
                nf.setMaximumFractionDigits(2);
            }
            String cena = nf.format(o.price);

            model.addRow(new Object[]{
                    kraj,
                    sdf.format(o.dateStart),
                    sdf.format(o.dateEnd),
                    miejsce,
                    cena,
                    o.currency
            });
        }
    }
}