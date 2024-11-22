import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {
        int boardWidth = 720;
        int boardHeight = 250;

        JFrame frame = new JFrame("Dinosaur Game");
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DinosaurGame dinosaurGame = new DinosaurGame();
        frame.add(dinosaurGame);
        frame.pack();
        dinosaurGame.requestFocusInWindow();

        frame.setVisible(true);
    }
}
