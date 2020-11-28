import geometry.Line;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.*;

public class SettingsPanel extends JPanel {

    private final Color backgroundColor = new Color(50, 50, 50);
    private final Font font = new Font("Times New Roman", Font.PLAIN, 24);
    private final Border border = new BasicBorders.FieldBorder(backgroundColor, backgroundColor, Color.BLACK, Color.BLACK);
    private final Model model;
    private Line line;

    private void setSettings(JComponent component) {
        component.setFont(font);
        component.setBackground(backgroundColor);
        component.setForeground(Color.WHITE);
        component.setBorder(border);
    }

    public SettingsPanel(Model model, Line line) {
        this.model = model;
        this.line = line;

        this.setPreferredSize(new Dimension(300, 800));
        this.setBackground(backgroundColor);

        GridBagLayout gridBag = new GridBagLayout();
        this.setLayout(gridBag);
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipadx = 40;
        c.ipady = 40;

        c.gridy = 0;
        JLabel systemText = new JLabel("<html>&nbsp;&nbsp;x' = \u03C3(y - x)<br>" +
                "&nbsp;&nbsp;y' = x(\u03F1 - z) - y<br>" +
                "&nbsp;&nbsp;z' = xy - \u03D0z</html>");
        setSettings(systemText);
        systemText.setBorder(new BasicBorders.FieldBorder(Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK));
        c.gridwidth = 2;
        gridBag.setConstraints(systemText, c);
        this.add(systemText);
        c.gridwidth = 1;

        c.gridy = 1;
        JLabel rhoLabel = new JLabel("  Rho \u03F1");
        JTextField rhoText = new JTextField(20);
        setSettings(rhoLabel);
        setSettings(rhoText);
        gridBag.setConstraints(rhoLabel, c);
        gridBag.setConstraints(rhoText, c);
        this.add(rhoLabel);
        this.add(rhoText);

        c.gridy = 2;
        JLabel sigmaLabel = new JLabel("  Sigma \u03C3");
        JTextField sigmaText = new JTextField(20);
        setSettings(sigmaLabel);
        setSettings(sigmaText);
        gridBag.setConstraints(sigmaLabel, c);
        gridBag.setConstraints(sigmaText, c);
        this.add(sigmaLabel);
        this.add(sigmaText);

        c.gridy = 3;
        JLabel betaLabel = new JLabel("  Beta \u03D0");
        JTextField betaText = new JTextField(20);
        setSettings(betaLabel);
        setSettings(betaText);
        gridBag.setConstraints(betaLabel, c);
        gridBag.setConstraints(betaText, c);
        this.add(betaLabel);
        this.add(betaText);

        c.gridy = 4;
        JLabel dtLabel = new JLabel("  dt");
        JTextField dtText = new JTextField(20);
        setSettings(dtLabel);
        setSettings(dtText);
        gridBag.setConstraints(dtLabel, c);
        gridBag.setConstraints(dtText, c);
        this.add(dtLabel);
        this.add(dtText);

        c.gridy = 5;
        JButton button = new JButton("Apply values");
        setSettings(button);
        button.addActionListener(actionEvent -> {
            synchronized (line) {
                try {
                    double rho = Double.parseDouble(rhoText.getText());
                    double sigma = Double.parseDouble(sigmaText.getText());
                    double beta = Double.parseDouble(betaText.getText());
                    double dt = Double.parseDouble(dtText.getText());
                    model.setRho(rho);
                    model.setSigma(sigma);
                    model.setBeta(beta);
                    model.setDt(dt);
                } catch (NumberFormatException ignored) {

                } finally {
                    rhoText.setText(Double.toString(model.getRho()));
                    sigmaText.setText(Double.toString(model.getSigma()));
                    betaText.setText(Double.toString(model.getBeta()));
                    dtText.setText(Double.toString(model.getDt()));
                }
            }
        });
        c.gridwidth = 2;
        gridBag.setConstraints(button, c);
        this.add(button);

        button.doClick();

        this.setOpaque(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        this.setPreferredSize(new Dimension(this.getWidth(), this.getParent().getHeight()));
        super.paintComponent(g);
    }
}
