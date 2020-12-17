import geometry.Point;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.*;

public class SettingsPanel extends JPanel {

    private final Color backgroundColor = new Color(50, 50, 50);
    private final Font font = new Font("Times New Roman", Font.PLAIN, 18);
    private final Border border = new BasicBorders.FieldBorder(backgroundColor, backgroundColor, Color.BLACK, Color.BLACK);
    private static final int SIGNS = 9;

    private void setSettings(JComponent component) {
        component.setFont(font);
        component.setBackground(new Color(30, 30, 30));
        component.setForeground(Color.WHITE);
        component.setBorder(border);
        component.setBorder(BorderFactory.createCompoundBorder(
                component.getBorder(),
                BorderFactory.createEmptyBorder(5, 16, 5, 0)));
        component.setOpaque(true);
        if (component instanceof JTextField) {
            ((JTextField) component).setCaretColor(Color.WHITE);
        }
    }

    private static String formatNumber(int i) {
        return Integer.toString(i);
    }

    private static String formatNumber(double d) {
        int digits = Math.max(1, (int) Math.log10(d) + 1);
        String s = String.format("%." + Math.max(0, SIGNS - digits) + "f", d);
        return (s.contains(".") ? s.replaceAll("0*$", "").replaceAll("\\.$", ".0") : s);
    }

    public SettingsPanel(Model model) {

        this.setPreferredSize(new Dimension(300, 0));
        this.setBackground(backgroundColor);

        GridBagLayout gridBag = new GridBagLayout();
        this.setLayout(gridBag);
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 0, 0, 0);
        int row = 0;



        c.gridy = row++;
        JLabel controlText = new JLabel("<html>WASD - rotate<br>" +
                "Arrows - move<br>" +
                "+- - zoom</html>");
        setSettings(controlText);
        controlText.setBorder(new BasicBorders.FieldBorder(Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK));
        controlText.setBorder(BorderFactory.createCompoundBorder(
                controlText.getBorder(),
                BorderFactory.createEmptyBorder(8, 16, 8, 0)));
        c.gridwidth = 2;
        c.insets = new Insets(0, 0, 14, 0);
        gridBag.setConstraints(controlText, c);
        this.add(controlText);
        c.insets = new Insets(0, 0, 0, 0);
        c.gridwidth = 1;



        c.gridy = row++;
        JLabel systemText = new JLabel("<html>x' = \u03C3(y - x)<br>" +
                "y' = x(\u03F1 - z) - y<br>" +
                "z' = xy - \u03D0z</html>");
        setSettings(systemText);
        systemText.setBorder(new BasicBorders.FieldBorder(Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK));
        systemText.setBorder(BorderFactory.createCompoundBorder(
                systemText.getBorder(),
                BorderFactory.createEmptyBorder(8, 16, 8, 0)));
        c.gridwidth = 2;
        c.insets = new Insets(0, 0, 14, 0);
        gridBag.setConstraints(systemText, c);
        this.add(systemText);
        c.insets = new Insets(0, 0, 0, 0);
        c.gridwidth = 1;



        c.gridy = row++;
        JLabel speedLabel = new JLabel("Speed");
        JTextField speedText = new JTextField(10);
        setSettings(speedLabel);
        setSettings(speedText);
        gridBag.setConstraints(speedLabel, c);
        gridBag.setConstraints(speedText, c);
        this.add(speedLabel);
        this.add(speedText);

        c.gridy = row++;
        JButton speedButton = new JButton("Change speed");
        setSettings(speedButton);
        speedButton.addActionListener(actionEvent -> {
            try {
                int speed = Integer.parseInt(speedText.getText());
                model.setPointsByIteration(speed);
            } catch (NumberFormatException ignored) {

            } finally {
                speedText.setText(formatNumber(model.getPointsByIteration()));
            }
        });
        c.gridwidth = 2;
        c.insets = new Insets(0, 0, 14, 0);
        gridBag.setConstraints(speedButton, c);
        this.add(speedButton);
        c.insets = new Insets(0, 0, 0, 0);
        c.gridwidth = 1;
        speedButton.doClick();



        c.gridy = row++;
        JLabel xLabel = new JLabel("Start x");
        JTextField xText = new JTextField(10);
        setSettings(xLabel);
        setSettings(xText);
        gridBag.setConstraints(xLabel, c);
        this.add(xLabel);
        c.ipadx = 150;
        gridBag.setConstraints(xText, c);
        this.add(xText);
        c.ipadx = 0;

        c.gridy = row++;
        JLabel yLabel = new JLabel("Start y");
        JTextField yText = new JTextField(10);
        setSettings(yLabel);
        setSettings(yText);
        gridBag.setConstraints(yLabel, c);
        gridBag.setConstraints(yText, c);
        this.add(yLabel);
        this.add(yText);

        c.gridy = row++;
        JLabel zLabel = new JLabel("Start z");
        JTextField zText = new JTextField(10);
        setSettings(zLabel);
        setSettings(zText);
        gridBag.setConstraints(zLabel, c);
        gridBag.setConstraints(zText, c);
        this.add(zLabel);
        this.add(zText);

        c.gridy = row++;
        JButton startButton = new JButton("Restart model");
        setSettings(startButton);
        startButton.addActionListener(actionEvent -> {
            try {
                double x = Double.parseDouble(xText.getText());
                double y = Double.parseDouble(yText.getText());
                double z = Double.parseDouble(zText.getText());
                model.restart(new Point(x, y, z));
            } catch (NumberFormatException ignored) {

            } finally {
                Point p = model.getStart();
                xText.setText(formatNumber(p.x));
                yText.setText(formatNumber(p.y));
                zText.setText(formatNumber(p.z));
            }
        });
        c.gridwidth = 2;
        c.insets = new Insets(0, 0, 14, 0);
        gridBag.setConstraints(startButton, c);
        this.add(startButton);
        c.insets = new Insets(0, 0, 0, 0);
        c.gridwidth = 1;
        startButton.doClick();



        c.gridwidth = 2;

        c.gridy = row++;
        JCheckBox checkBoxEuler = new JCheckBox("Euler");
        checkBoxEuler.setSelected(true);
        setSettings(checkBoxEuler);
        gridBag.setConstraints(checkBoxEuler, c);
        this.add(checkBoxEuler);

        c.gridy = row++;
        JCheckBox checkBoxRunge = new JCheckBox("Runge–Kutta");
        setSettings(checkBoxRunge);
        gridBag.setConstraints(checkBoxRunge, c);
        this.add(checkBoxRunge);

        c.gridy = row++;
        JCheckBox checkBoxAdams = new JCheckBox("Adams");
        setSettings(checkBoxAdams);
        gridBag.setConstraints(checkBoxAdams, c);
        this.add(checkBoxAdams);

        c.gridwidth = 1;



        c.gridy = row++;
        JLabel rhoLabel = new JLabel("Rho \u03F1");
        JTextField rhoText = new JTextField(10);
        setSettings(rhoLabel);
        setSettings(rhoText);
        gridBag.setConstraints(rhoLabel, c);
        gridBag.setConstraints(rhoText, c);
        this.add(rhoLabel);
        this.add(rhoText);

        c.gridy = row++;
        JLabel sigmaLabel = new JLabel("Sigma \u03C3");
        JTextField sigmaText = new JTextField(10);
        setSettings(sigmaLabel);
        setSettings(sigmaText);
        gridBag.setConstraints(sigmaLabel, c);
        gridBag.setConstraints(sigmaText, c);
        this.add(sigmaLabel);
        this.add(sigmaText);

        c.gridy = row++;
        JLabel betaLabel = new JLabel("Beta \u03D0");
        JTextField betaText = new JTextField(10);
        setSettings(betaLabel);
        setSettings(betaText);
        gridBag.setConstraints(betaLabel, c);
        gridBag.setConstraints(betaText, c);
        this.add(betaLabel);
        this.add(betaText);

        c.gridy = row++;
        JLabel dtLabel = new JLabel("dt");
        JTextField dtText = new JTextField(10);
        setSettings(dtLabel);
        setSettings(dtText);
        gridBag.setConstraints(dtLabel, c);
        gridBag.setConstraints(dtText, c);
        this.add(dtLabel);
        this.add(dtText);

        c.gridy = row++;
        JButton changesButton = new JButton("Apply");
        setSettings(changesButton);
        changesButton.addActionListener(actionEvent -> {
                try {
                    model.setVisible(EvaluateType.EULER, checkBoxEuler.isSelected());
                    model.setVisible(EvaluateType.RUNGE, checkBoxRunge.isSelected());
                    model.setVisible(EvaluateType.ADAMS, checkBoxAdams.isSelected());
                    double rho = Double.parseDouble(rhoText.getText());
                    double sigma = Double.parseDouble(sigmaText.getText());
                    double beta = Double.parseDouble(betaText.getText());
                    double dt = Double.parseDouble(dtText.getText());
                    model.setConstants(rho, sigma, beta, dt);
                } catch (NumberFormatException ignored) {

                } finally {
                    checkBoxEuler.setSelected(model.getVisible(EvaluateType.EULER));
                    checkBoxRunge.setSelected(model.getVisible(EvaluateType.RUNGE));
                    checkBoxAdams.setSelected(model.getVisible(EvaluateType.ADAMS));
                    rhoText.setText(formatNumber(model.getRho()));
                    sigmaText.setText(formatNumber(model.getSigma()));
                    betaText.setText(formatNumber(model.getBeta()));
                    dtText.setText(formatNumber(model.getDt()));
                }
        });
        c.gridwidth = 2;
        gridBag.setConstraints(changesButton, c);
        this.add(changesButton);
        c.gridwidth = 1;
        changesButton.doClick();

        this.setOpaque(true);
    }
}
