import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.util.Date;
import java.text.SimpleDateFormat;

public class MyGame extends JFrame implements ActionListener {

    JLabel heading, clockLabel;
    Font font = new Font("", Font.BOLD, 40);
    JPanel mainPanel;

    JButton[] btns = new JButton[9];

    // Game instance variable

    int gameChances[] = { 2, 2, 2, 2, 2, 2, 2, 2, 2 };
    int activePlayer = 0;

    int wps[][] = {
            { 0, 1, 2 },
            { 3, 4, 5 },
            { 6, 7, 8 },
            { 0, 3, 6 },
            { 1, 4, 7 },
            { 2, 5, 8 },
            { 0, 4, 8 },
            { 2, 4, 6 }
    };

    int winner = 2;

    boolean gameOver = false;

    MyGame() {
        System.out.println("Creating instance of game");
        setTitle("Tic-Tac-Toe");
        setSize(800, 800);
        ImageIcon img = new ImageIcon("img/icon.png");
        setIconImage(img.getImage());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createGUI();
        setVisible(true);
    }

    private void createGUI() {

        this.getContentPane().setBackground(Color.decode("#2196f3"));
        this.setLayout(new BorderLayout());
        heading = new JLabel("Tic-Tac-Toe");
        heading.setFont(font);
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setForeground(Color.white);
        heading.setHorizontalTextPosition(SwingConstants.CENTER);
        heading.setVerticalTextPosition(SwingConstants.BOTTOM);

        this.add(heading, BorderLayout.NORTH);

        clockLabel = new JLabel("Clock");
        clockLabel.setForeground(Color.white);
        clockLabel.setFont(font);
        clockLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(clockLabel, BorderLayout.SOUTH);

        Thread t = new Thread() {
            public void run() {
                try {
                    while (true) {
                        Date date = new Date();
                        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                        String datetime = timeFormat.format(date);
                        clockLabel.setText(datetime);

                        Thread.sleep(1000);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();

        // Panel Section

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 3));
        for (int i = 1; i <= 9; i++) {
            JButton btn = new JButton();
            btn.setBackground(Color.decode("#90caf9"));
            btn.setFont(font);
            mainPanel.add(btn);
            btns[i - 1] = btn;
            btn.addActionListener(this);
            btn.setName(String.valueOf(i - 1));
        }
        this.add(mainPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton currentButton = (JButton) e.getSource();

        String nameStr = currentButton.getName();
        System.out.println(nameStr);

        int name = Integer.parseInt(nameStr.trim());

        if(gameOver) {
            JOptionPane.showMessageDialog(null, "Game over already");
        }

        if (gameChances[name] == 2) {
            if (activePlayer == 0) {
                currentButton.setIcon(new ImageIcon("img/0.png"));
                gameChances[name] = activePlayer;
                activePlayer = 1;
            } else {
                currentButton.setIcon(new ImageIcon("img/x.png"));
                gameChances[name] = activePlayer;
                activePlayer = 0;
            }

            // Find Winner

            for (int[] temp : wps) {
                if ((gameChances[temp[0]] == gameChances[temp[1]]) && (gameChances[temp[1]] == gameChances[temp[2]])
                        && gameChances[temp[2]] != 2) {
                    winner = gameChances[temp[0]];
                    gameOver = true;
                    JOptionPane.showMessageDialog(null, "Player " + (int)(winner+1) + " has won the game!");
                    int i = JOptionPane.showConfirmDialog(this, "Do you want to play again?");
                    if (i == 0) {
                        this.setVisible(false);
                        new MyGame();
                    } else if (i == 1) {
                        System.exit(0);
                    } else {

                    }
                    break;
                }
            }
            // Draw Logic
            int c = 0;

            for(int x : gameChances) {
                if(x == 2) {
                    c++;
                    break;
                }

            }
            if(c==0 && gameOver==false) {
                JOptionPane.showMessageDialog(null, "It's a Draw!");
                int i = JOptionPane.showConfirmDialog(this, "Play more?");
                if(i == 0) {
                    this.setVisible(false);
                    new MyGame();
                }
                else if(i == 1) {
                    System.exit(0);
                }
                else {

                }
                gameOver = true;
            }


        } else {
            JOptionPane.showMessageDialog(this, "Position already occupied!");
        }
    }

}
