import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;

public class DinosaurGame extends JPanel implements ActionListener, KeyListener {
    int boardWidth = 720;
    int boardHeight = 250;

    Image dinosaurImg;
    Image dinosaurJumpImg;
    Image dinosaurDeadImg;
    Image cactus1Img;
    Image cactus2Img;
    Image cactus3Img;
    Image trackImg;
    Image gameOverImg;
    Image resetImg;
    Image bigCactus1Img;
    Image bigCactus2Img;
    Image bigCactus3Img;
    Image birdImg;
    Image cloudImg;
    Image dinoDuckImg;

    class ImgDesc {
        int x;
        int y;
        int width;
        int height;
        Image img;

        ImgDesc(int x, int y, int width, int height, Image img){
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.img = img;
        }
    }

    int trackWidth = 720;
    int trackHeight = 20;
    int trackX = 0;
    int trackY = boardHeight - trackHeight;
    ImgDesc track;

    int dinosaurWidth = 80;
    int dinosaurHeight = 90;
    int dinosaurX = 50;
    int dinosaurY = boardHeight - dinosaurHeight - trackHeight + 10;

    int duckDinosaurWidth = 100;
    int duckDinosaurHeight = 60;
    int duckDinosaurX = 50;
    int duckDinosaurY = boardHeight - duckDinosaurHeight - trackHeight + 10;

    ImgDesc dinosaur;
    ImgDesc dinosaurDuck;
    ImgDesc dinosaurCurr;

    int cactus1Width = 35;
    int cactus2Width = 70;
    int cactus3Width = 100;

    int cactusHeight = 70;
    int cactusX = 670;
    int cactusY = boardHeight - cactusHeight - trackHeight + 10;

    int bigCactus1Width = 40;
    int bigCactus2Width = 80;
    int bigCactus3Width = 120;

    int bigCactusHeight = 80;
    int bigCactusX = 670;
    int bigCactusY = boardHeight - cactusHeight - trackHeight + 10;

    int birdWidth = 60;
    int birdHeight = 40;
    int birdX = 670;
    int birdY = boardHeight - dinosaurHeight - trackHeight;

    int cloudWidth = 100;
    int cloudHeight = 50;
    int cloudX = 670;
    int cloudY = 30;

    ArrayList<ImgDesc> obstaclesArray;
    
    Timer gameLoop;
    Timer placeCactus;

    int velocityX = -12;
    int velocityY = 0;
    int gravity = 1;

    boolean gameOver = false;
    int score = 0;

    boolean keyDownPressed = false;

    int scoreForDifficulty = 0;
    int[] scoreDifficultyArray = {100, 200, 300, 400, 500};

    public DinosaurGame(){
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setBackground(Color.lightGray);
        setFocusable(true);
        addKeyListener(this);

        dinosaurImg = new ImageIcon(getClass().getResource("./img/dino-run.gif")).getImage();
        dinosaurJumpImg = new ImageIcon(getClass().getResource("./img/dino-jump.png")).getImage();
        dinosaurDeadImg = new ImageIcon(getClass().getResource("./img/dino-dead.png")).getImage();
        cactus1Img = new ImageIcon(getClass().getResource("./img/cactus1.png")).getImage();
        cactus2Img = new ImageIcon(getClass().getResource("./img/cactus2.png")).getImage();
        cactus3Img = new ImageIcon(getClass().getResource("./img/cactus3.png")).getImage();
        trackImg = new ImageIcon(getClass().getResource("./img/track.png")).getImage();
        gameOverImg = new ImageIcon(getClass().getResource("./img/game-over.png")).getImage();
        resetImg = new ImageIcon(getClass().getResource("./img/reset.png")).getImage();
        bigCactus1Img = new ImageIcon(getClass().getResource("./img/big-cactus1.png")).getImage();
        bigCactus2Img = new ImageIcon(getClass().getResource("./img/big-cactus2.png")).getImage();
        bigCactus3Img = new ImageIcon(getClass().getResource("./img/big-cactus3.png")).getImage();
        birdImg = new ImageIcon(getClass().getResource("./img/bird.gif")).getImage();
        cloudImg = new ImageIcon(getClass().getResource("./img/cloud.png")).getImage();
        dinoDuckImg = new ImageIcon(getClass().getResource("./img/dino-duck.gif")).getImage();

        dinosaur = new ImgDesc(dinosaurX, dinosaurY, dinosaurWidth, dinosaurHeight, dinosaurImg);
        dinosaurDuck = new ImgDesc(duckDinosaurX, duckDinosaurY, duckDinosaurHeight, duckDinosaurHeight, dinoDuckImg);
        dinosaurCurr = dinosaur;

        Random random = new Random();
        int randomIndex = random.nextInt(scoreDifficultyArray.length);
        scoreForDifficulty = scoreDifficultyArray[randomIndex];

        obstaclesArray = new ArrayList<ImgDesc>();

        track = new ImgDesc(trackX, trackY, trackWidth, trackHeight, trackImg);

        gameLoop = new Timer(1000/60, this);
        gameLoop.start();

        placeCactus = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if(score < scoreForDifficulty){
                    placeCactusInGame();
                } else{
                    placeObstaclesInGame();
                }
                placeObstaclesInGame();
            }
        });

        placeCactus.start();
    }

    public void placeCactusInGame(){
        double placeCactusChance = 3*Math.random();

        if(placeCactusChance > 2.5){
            ImgDesc cactus = new ImgDesc(cactusX, cactusY, cactus3Width, cactusHeight, cactus3Img);
            obstaclesArray.add(cactus);
        } else if(placeCactusChance > 2){
            ImgDesc cactus = new ImgDesc(cactusX, cactusY, cactus2Width, cactusHeight, cactus2Img);
            obstaclesArray.add(cactus);
        } else if(placeCactusChance > 1){
            ImgDesc cactus = new ImgDesc(cactusX, cactusY, cactus1Width, cactusHeight, cactus1Img);
            obstaclesArray.add(cactus);
        }

        if(obstaclesArray.size() > 10){
            obstaclesArray.remove(0);
        }
    }

    public void placeObstaclesInGame(){
        double placeObstaclesChance = 20*Math.random();

        if(placeObstaclesChance > 19){
            ImgDesc bigCactus = new ImgDesc(bigCactusX, bigCactusY, bigCactus3Width, bigCactusHeight, bigCactus3Img);
            obstaclesArray.add(bigCactus);
        } else if(placeObstaclesChance > 17){
            ImgDesc bigCactus = new ImgDesc(bigCactusX, bigCactusY, bigCactus2Width, bigCactusHeight, bigCactus2Img);
            obstaclesArray.add(bigCactus);
        } else if(placeObstaclesChance > 14){
            ImgDesc bigCactus = new ImgDesc(bigCactusX, bigCactusY, bigCactus1Width, bigCactusHeight, bigCactus1Img);
            obstaclesArray.add(bigCactus);
        } else if(placeObstaclesChance > 13){
            ImgDesc cactus = new ImgDesc(cactusX, cactusY, cactus3Width, cactusHeight, cactus3Img);
            obstaclesArray.add(cactus);
        } else if(placeObstaclesChance > 12){
            ImgDesc cactus = new ImgDesc(cactusX, cactusY, cactus2Width, cactusHeight, cactus2Img);
            obstaclesArray.add(cactus);
        } else if(placeObstaclesChance > 11){
            ImgDesc cactus = new ImgDesc(cactusX, cactusY, cactus1Width, cactusHeight, cactus1Img);
            obstaclesArray.add(cactus);
        } else if(placeObstaclesChance > 8){
            ImgDesc bird = new ImgDesc(birdX, birdY, birdWidth, birdHeight, birdImg);
            obstaclesArray.add(bird);
        } else if(placeObstaclesChance > 6){
            ImgDesc cloud = new ImgDesc(cloudX, cloudY, cloudWidth, cloudHeight, cloudImg);
            obstaclesArray.add(cloud);
        }

        if(obstaclesArray.size() > 10){
            obstaclesArray.remove(0);
        }
        
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        g.drawImage(dinosaurCurr.img, dinosaurCurr.x, dinosaurCurr.y, dinosaurCurr.width, dinosaurCurr.height, null);
        g.drawImage(track.img, track.x, track.y, track.width, track.height, null);

        for(int i = 0; i < obstaclesArray.size(); ++i){
            ImgDesc obstacle = obstaclesArray.get(i);
            g.drawImage(obstacle.img, obstacle.x, obstacle.y, obstacle.width, obstacle.height, null);
        }

        if(gameOver){
            g.setColor(Color.red);
            g.setFont(new Font("DialogInput", Font.BOLD, 25));
            g.drawString("Your Score: " + String.valueOf(score), 250, 35);
            g.drawImage(gameOverImg, 160, 50, 400, 50, null);
            g.drawImage(resetImg, 165, 110, 30, 30, null);
            g.setColor(Color.black);
            g.setFont(new Font("DialogInput", Font.PLAIN, 25));
            g.drawString("Press Any Key to Restart", 200, 132);
        } else{
            g.setColor(Color.black);
            g.setFont(new Font("DialogInput", Font.PLAIN, 25));
            g.drawString("Score: " + String.valueOf(score), 10, 35);
        }
    }

    public void move(){
        velocityY += gravity;
        dinosaurCurr.y += velocityY;

        if(!keyDownPressed && dinosaurCurr.y > dinosaurY){
            velocityY = 0;
            dinosaurCurr.y = dinosaurY;
            dinosaurCurr.img = dinosaurImg;
        } else if(keyDownPressed && dinosaurCurr.y > duckDinosaurY){
            velocityY = 0;
            dinosaurCurr.y = duckDinosaurY;
            dinosaurCurr.img = dinoDuckImg;
        }

        for(int i = 0; i < obstaclesArray.size(); ++i){
            ImgDesc obstacle = obstaclesArray.get(i);
            obstacle.x += velocityX;

            if(collision(dinosaurCurr, obstacle)){
                gameOver = true;
                dinosaurCurr = dinosaur;
                dinosaurCurr.img = dinosaurDeadImg;
            }
        }

        ++score;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(keyDownPressed){
            dinosaurCurr = dinosaurDuck;
        } else{
            dinosaurCurr = dinosaur;
        }
        move();
        repaint();

        if(gameOver){
            placeCactus.stop();
            gameLoop.stop();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            if(!keyDownPressed && dinosaurCurr.y == dinosaurY){
                velocityY = -17;
                dinosaurCurr.img = dinosaurJumpImg;
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN){
            if(!gameOver && velocityY == 0){
                keyDownPressed = true;
            }
        }

        if(!keyDownPressed && gameOver){
            dinosaurCurr.y = dinosaurY;
            dinosaurCurr.img = dinosaurImg;
            velocityY = 0;
            obstaclesArray.clear();
            gameOver = false;
            score = 0;
            gameLoop.start();
            placeCactus.start();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_DOWN){
            keyDownPressed = false;
        }
    }

    public boolean collision(ImgDesc dinosaur, ImgDesc obstacle){
        return dinosaur.x < obstacle.x + obstacle.width && dinosaur.x + dinosaur.width > obstacle.x
                && dinosaur.y < obstacle.y + obstacle.height && dinosaur.y + dinosaur.height > obstacle.y;
    }
}
