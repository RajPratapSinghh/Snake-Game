import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameBoard extends JPanel implements ActionListener {
int height =400;
int width=400;
int x[]=new int[height*width];
int y[]=new int[height*width];
int dots;
int apple_x=100;
int apple_y=100;
int dot_size=10;
Image apple;
Image body;
Image head;
boolean leftD=true;
boolean rightD=false;
boolean upD=false;
boolean downD=false;
Timer timer;
int DELAY=250;
int RAND_POS=39;
boolean inGame=true;
public GameBoard(){
    setPreferredSize(new Dimension(width,height));
    addKeyListener(new TAdapter());
    setFocusable(true);
    setBackground(Color.BLACK);
    loadImages();
    initGame();
}
public void initGame(){
    dots=3;
    for(int i=0;i<dots;i++){
        x[i]=150+i*dot_size;
        y[i]=150;
    }
    timer = new Timer(DELAY,this);
    timer.start();;
    }
    private void loadImages(){
    ImageIcon image_apple =new ImageIcon("src/resources/apple.png");
    apple= image_apple.getImage();
    ImageIcon image_body=new ImageIcon("src/resources/dot.png");
    body=image_body.getImage();
    ImageIcon image_head=new ImageIcon("src/resources/head.png");
    head=image_head.getImage();
    }
    @Override
    public void paintComponent(Graphics graphics){
    super.paintComponent(graphics);
    graphics.drawImage(apple,apple_x,apple_y,this);
   if(inGame){ for(int i=0;i<dots;i++){
        if(i==0){
            graphics.drawImage(head,x[0],y[0],this);
        }
        else{
            graphics.drawImage(body,x[i],y[i],this);
        }
    }
    Toolkit.getDefaultToolkit().sync();
    }
   else{
       gameOver(graphics);
   }
}
    private void move(){
    for(int i=dots-1;i>0;i--){
        x[i]=x[i-1];
        y[i]=y[i-1];
    }
    if(leftD){
        x[0]-=dot_size;
            }
    if(rightD){
        x[0]+=dot_size;
    }
    if(upD) {
        y[0] -= dot_size;
    }
    if(downD){
        y[0]+=dot_size;
    }

    }
private void locateApple(){
    int r=(int)(Math.random()*RAND_POS);
    apple_x=r*dot_size;
    r=(int)(Math.random()*RAND_POS);

}
private void checkApple(){
    if(x[0]==apple_x && y[0]==apple_y){
        dots++;
        locateApple();
    }
}
private void checkCollision(){
    if(x[0]<0){
        inGame=false;
    }
    if(x[0]>=width){
        inGame=false;
    }
    if(y[0]<0){
        inGame=false;
    }
    if(y[0]>=height){
        inGame=false;
    }
    for(int i=dots-1;i>3;i--){
        if(x[0]==x[i]&&y[0]==y[i]){
            inGame=false;
            break;
        }
    }
    }
    private void gameOver(Graphics graphics){
    String msg="GAME OVER!";
    Font  small= new Font("Helvetica",Font.BOLD,16);
   FontMetrics metrics=getFontMetrics(small);
   graphics.setColor(Color.cyan);
   graphics.setFont(small);
   graphics.drawString(msg, (width-metrics.stringWidth(msg))/2, height/2);
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
     if(inGame){
       checkApple();
       checkCollision();
       move();
     }
       repaint();
    }


    public class TAdapter extends KeyAdapter{
@Override
public void keyPressed(KeyEvent keyEvent){
    int key=keyEvent.getKeyCode();
    if((key==keyEvent.VK_LEFT)&&(!rightD)){
        leftD=true;
        upD=false;
        downD=false;
    }
    if((key==keyEvent.VK_RIGHT)&&(!leftD)){
        rightD=true;
        upD=false;
        downD=false;
    }
    if((key==keyEvent.VK_UP)&&(!downD)){
        leftD=false;
        upD=true;
        rightD=false;
    }
    if((key==keyEvent.VK_DOWN)&&(!upD)){
        leftD=false;
        rightD=false;
        downD=true;
    }
}
    }
}

