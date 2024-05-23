package View;

import Controller.LoginController;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.Cursor;
import java.awt.FlowLayout;

public class LoginForm extends JFrame {

    private static final long serialVersionUID = 1L;
    private Dimension FRAME_SIZE;
    private Color themeColor;
    private JPanel contentPane;
    private JPanel panel_top;
    private JPanel panel_center;
    private JPanel panel_bottom;
    private JTextField textField_input;
    private JLabel lbl_logo;
    private JButton btn_play;
    private JLabel lbl_input;

    private LoginController controller;

    public JButton getBtn_play() {
        return btn_play;
    }

    public JTextField getTextField_input() {
        return textField_input;
    }

    public LoginForm() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(475, 300);
        setResizable(false);
        setLocationRelativeTo(null);
        contentPane = new JPanel();

        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        initVariables();
        controller = new LoginController(this);
        btn_play.addActionListener(controller);
        textField_input.addKeyListener(controller);

        pack();
        setVisible(true);
    }

    private void initVariables() {
        FRAME_SIZE = this.getSize();
        int frameWidth = (int)FRAME_SIZE.getWidth();
        themeColor = new Color(184, 218, 239);

        panel_center = new JPanel();
        panel_center.setPreferredSize(new Dimension(frameWidth,100));
        panel_center.setBackground(themeColor);
        contentPane.add(panel_center, BorderLayout.CENTER);
        panel_center.setLayout(null);

        lbl_input = new JLabel("Player's name: ");
        lbl_input.setFont(new Font("Tahoma", Font.BOLD, 15));
        lbl_input.setBounds(31, 53, 120, 14);
        panel_center.add(lbl_input);

        textField_input = new JTextField();
        textField_input.setFont(new Font("Tahoma", Font.PLAIN, 16));
        textField_input.setBounds(148, 47, 261, 26);
        panel_center.add(textField_input);
        textField_input.setColumns(10);

        panel_top = new JPanel();
        FlowLayout flowLayout = (FlowLayout) panel_top.getLayout();
        flowLayout.setVgap(20);
        panel_top.setBackground(themeColor);
        panel_top.setPreferredSize(new Dimension(frameWidth, 100));
        contentPane.add(panel_top, BorderLayout.NORTH);

        lbl_logo = new JLabel();
        lbl_logo.setIcon(new ImageIcon("src/Sprites/Flappy_Logo.png"));
        panel_top.add(lbl_logo);

        panel_bottom = new JPanel();
        panel_bottom.setPreferredSize(new Dimension(frameWidth,70));
        panel_bottom.setBackground(themeColor);
        contentPane.add(panel_bottom, BorderLayout.SOUTH);
        panel_bottom.setLayout(null);

        btn_play = new JButton("PLAY GAME");
        btn_play.setBounds(181, 11, 119, 29);
        btn_play.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btn_play.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panel_bottom.add(btn_play);
    }

}
