package services;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Helper {

    public static final int WINDOW_WIDTH = 1000;
    public static final int WINDOW_HEIGHT = 600;

    public static Boolean deleteRecord(Connection conn, String table, String pkName, int pk) {
        try {
            String query = "DELETE FROM `" + table + "` WHERE `" + pkName + "` = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, pk);

            final int rowsAffected = stmt.executeUpdate();
            if(rowsAffected > 0) {
                stmt.close();
                return true;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void addRow(JPanel parent, Component c1, Component c2, String rowId, int vgap, int hgap) {
        final int parentWidth = (int) parent.getPreferredSize().getWidth();
        final int col1 = parentWidth / 3;
        final int col2 = 2 * parentWidth / 3;

        c1.setPreferredSize(new Dimension(col1, 30));
        c2.setPreferredSize(new Dimension(col2, 30));
        c1.setMaximumSize(c1.getPreferredSize());
        c2.setMaximumSize(c2.getPreferredSize());

        if(c1 instanceof JLabel)
            c1.setForeground(new Color(85, 85, 85));

        JPanel row = new JPanel();
        row.setBackground(new Color(255, 255, 255));
        row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));

        if(!rowId.equals(""))
            row.setName(rowId);

        row.add(c1);
        if(hgap > 0)
            addHGap(row, hgap);

        row.add(c2);
        parent.add(row);
        if(vgap > 0)
            addVGap(parent, vgap);

        refreshUI(parent);
    }

    public static void refreshUI(Component container) {
        container.revalidate();
        container.repaint();
    }

    public static Boolean isFieldEmpty(JTextField textField) {
        final String text = textField.getText().trim();
        return text.equals("");
    }

    public static void addVGap(JPanel parent, int vgap) {
        parent.add(Box.createRigidArea(new Dimension(0, vgap)));
    }
    public static void addHGap(JPanel parent, int hgap) {
        parent.add(Box.createRigidArea(new Dimension(hgap, 0)));
    }

    // src::stackOverflow
    public static void setTimeout(Runnable runnable, int delay){
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }).start();
    }

}
