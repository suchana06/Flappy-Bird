
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {

    int boardwidth = 360;
    int boardheight = 640;

    Image backgroundImg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;

    //bird
    int birdX = boardwidth / 8;
    int birdY = boardheight / 2;
    int birdWidth = 34;
    int birdHeight = 24;

    //pipe
    int pipeX = boardwidth;
    int pipeY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;

    class Bird {
        int x = birdX;
        int y = birdY;
        int width = birdWidth;
        int height = birdHeight;
        Image img;

        Bird(Image img) {
            this.img = img;
        }
    }

    class Pipe {
        int x = pipeX;
        int y = pipeY;
        int width = pipeWidth;
        int height = pipeHeight;
        Image img;
        boolean passed = false;
        Pipe(Image img) {
            this.img = img;
        }
    }


    Bird bird;
    Timer gameloop;
    Timer placePipeTimer;
    int velocityX = -4;
    int velocityY = 0;
    int gravity = 1;
    ArrayList<Pipe> pipes;
    Random random = new Random();
    boolean gameOver = false;
    double score=0;
    FlappyBird() {
        setPreferredSize(new Dimension(boardwidth, boardheight));
        addKeyListener(this);
        setFocusable(true);
        backgroundImg = new ImageIcon(getClass().getResource("/resources/flappybirdbg.png")).getImage();
        birdImg = new ImageIcon(getClass().getResource("/resources/flappybird.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("/resources/toppipe.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("/resources/bottompipe.png")).getImage();
        bird = new Bird(birdImg);
        pipes = new ArrayList<>();
        placePipeTimer = new Timer(1500, new ActionListener() {
           public void actionPerformed(ActionEvent e) {
             placePipes();
           }
        });
        placePipeTimer.start();
        gameloop = new Timer(1000 / 60, this);
        gameloop.start();
    }


    public void placePipes(){
        int randompipeY = (int)(pipeY - pipeHeight/4 - Math.random()*(pipeHeight/2));
        int openingSpace = boardheight/4;
        Pipe topPipe = new Pipe(topPipeImg);
        topPipe.y = randompipeY;
        pipes.add(topPipe);
        Pipe bottomPipe = new Pipe(bottomPipeImg);
        bottomPipe.y = randompipeY + topPipe.height + openingSpace;
        pipes.add(bottomPipe);

    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
       
    }

    public void draw(Graphics g) {
        g.drawImage(backgroundImg, 0, 0, boardwidth, boardheight, null);

        //bird
        g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null);
        for(int i=0;i<pipes.size();i++){
            Pipe pipe  = pipes.get(i);
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height,null);
        }
        g.setColor(Color.black);
        g.setFont(new Font("Arial",Font.PLAIN,32));
        if(gameOver){
            g.drawString("GAME OVER: "+ String.valueOf((int)score) , 10, 35);
        }else{
            g.drawString("Score: "+ String.valueOf((int)score), 10, 35);
        }
        
    }

    public void move() {
        velocityY += gravity;
        bird.y += velocityY;
        for(int i=0;i<pipes.size();i++){
            Pipe pipe  = pipes.get(i);
            pipe.x +=velocityX;
            if(collision(bird, pipe)){
                gameOver = true;
            }
            if(!pipe.passed && bird.x > pipe.x + pipe.width){
                score+=0.5;
                pipe.passed = true;
            }
    
        }

        
        if(bird.y> boardheight){
            gameOver = true;
            gameloop.stop();
            placePipeTimer.stop();
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
        move();
        if(gameOver){
            gameloop.stop();
            placePipeTimer.stop();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
    boolean collision(Bird a, Pipe b) {
        return a.x < b.x + b.width &&   //a's top left corner doesn't reach b's top right corner
               a.x + a.width > b.x &&   //a's top right corner passes b's top left corner
               a.y < b.y + b.height &&  //a's top left corner doesn't reach b's bottom left corner
               a.y + a.height > b.y;    //a's bottom left corner passes b's top left corner
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            velocityY = -9;
            if(gameOver){
                bird.y = birdY;
                gameloop.start();
                placePipeTimer.start();
                gameOver = false;
                velocityY = 0;
                pipes.clear();
                score = 0;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
