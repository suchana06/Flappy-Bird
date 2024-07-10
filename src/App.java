import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {
        int boardwidth = 360;
        int boardheight = 640;
        JFrame frame = new JFrame();
        frame.setSize(boardwidth,boardheight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Flappy Bird");
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        FlappyBird game = new FlappyBird();
        frame.add(game);
        frame.pack();
        frame.requestFocus();
        frame.setVisible(true);
    }
}
