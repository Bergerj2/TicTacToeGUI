import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeFrame extends JFrame {

    private TicTacToeGame game;
    private TicTacToeTile[][] boardButtons;
    private JPanel mainPanel, boardPanel, controlPanel;
    private JLabel statusLabel;

    private static final int FRAME_WIDTH = 500;
    private static final int FRAME_HEIGHT = 600;

    public TicTacToeFrame() {
        game = new TicTacToeGame();
        boardButtons = new TicTacToeTile[3][3];

        setTitle("Tic-Tac-Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(FRAME_WIDTH, FRAME_HEIGHT);

        createGUI();
        updateStatus("Current Player: X");

        setResizable(false);
        setVisible(true);
    }

    private void createGUI() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        createBoardPanel();
        createControlPanel();

        mainPanel.add(boardPanel, BorderLayout.CENTER);
        mainPanel.add(controlPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }

    private void createBoardPanel() {
        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(3, 3));
        boardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                TicTacToeTile tile = new TicTacToeTile(r, c);
                tile.addActionListener(new TileClickListener());
                boardButtons[r][c] = tile;
                boardPanel.add(tile);
            }
        }
    }

    private void createControlPanel() {
        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        statusLabel = new JLabel("Welcome! Current Player: X");
        statusLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton quitButton = new JButton("Quit Game");
        quitButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        quitButton.addActionListener((ActionEvent e) -> {
            int response = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to quit?",
                    "Confirm Quit",
                    JOptionPane.YES_NO_OPTION);

            if (response == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        controlPanel.add(statusLabel);
        controlPanel.add(quitButton);
    }

    private void updateStatus(String msg) {
        statusLabel.setText(msg);
    }

    private void setBoardEnabled(boolean enabled) {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                boardButtons[r][c].setEnabled(enabled);
            }
        }
    }

    private void resetBoard() {
        game.clearBoard();
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                boardButtons[r][c].setText(" ");
                boardButtons[r][c].setEnabled(true);
            }
        }
        updateStatus("Current Player: X");
    }

    private class TileClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            TicTacToeTile tile = (TicTacToeTile) e.getSource();
            int row = tile.getRow();
            int col = tile.getCol();
            String currentPlayer = game.getCurrentPlayer();

            if (!game.isValidMove(row, col)) {
                JOptionPane.showMessageDialog(TicTacToeFrame.this,
                        "Illegal move. Choose an empty square.",
                        "Invalid Move",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            tile.setText(currentPlayer);
            tile.setEnabled(false);

            String status = game.makeMove(row, col);

            if (status.equals("WIN")) {
                String winner = game.getWinner();
                updateStatus("Game Over! Player " + winner + " Wins!");
                setBoardEnabled(false);
                JOptionPane.showMessageDialog(TicTacToeFrame.this,
                        "Player " + winner + " has won the game!",
                        "Game Over",
                        JOptionPane.INFORMATION_MESSAGE);

                askToPlayAgain();

            } else if (status.equals("TIE")) {
                updateStatus("Game Over! It's a Tie!");
                setBoardEnabled(false);
                JOptionPane.showMessageDialog(TicTacToeFrame.this,
                        "The game is a Tie!",
                        "Game Over",
                        JOptionPane.INFORMATION_MESSAGE);

                askToPlayAgain();

            } else {
                updateStatus("Current Player: " + game.getCurrentPlayer());
            }
        }

        private void askToPlayAgain() {
            int response = JOptionPane.showConfirmDialog(
                    TicTacToeFrame.this,
                    "Do you want to play again?",
                    "New Game?",
                    JOptionPane.YES_NO_OPTION);

            if (response == JOptionPane.YES_OPTION) {
                resetBoard();
            } else {
                System.exit(0);
            }
        }
    }
}