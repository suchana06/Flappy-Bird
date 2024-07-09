
import java.awt.*;
import javax.swing.*;

public class FlappyBird extends JPanel {

    int boardwidth = 360;
    int boardheight = 640;

    Image backgroundImg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;

    FlappyBird() {
        setPreferredSize(new Dimension(boardwidth, boardheight));
        //setBackground(Color.blue);
        backgroundImg = new ImageIcon(getClass().getResource("/resources/flappybirdbg.png")).getImage();
        birdImg = new ImageIcon(getClass().getResource("/resources/flappybird.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("/resources/toppipe.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("/resources/bottompipe.png")).getImage();
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        g.drawImage(backgroundImg,0,0,boardwidth,boardheight,null);
    }
}
