import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;



public class Yard extends Frame {

	private boolean flag = true;
	private int score=0;
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public static final int ROWS = 30;
	    public static final int COLS= 30;
	   public static final int BLOCK_SIZE=15;
	   
	   Egg egg=new Egg();
	   Snake snake=new Snake(this);
	   
	   
	    Image offScreenImage = null;//双缓冲（控制闪烁严重）
	    public void launch(){
	        this.setLocation(200,200);	 //起始位置       
	        this.setSize(COLS * BLOCK_SIZE,ROWS*BLOCK_SIZE );
	        this.addWindowListener(new WindowAdapter() {// 添加关闭事件  

				@Override
				public void windowClosing(WindowEvent e) {
					// TODO Auto-generated method stub
					System.exit(0);
				}//能关闭窗口
	        	
	        	
	        });
	        this.setVisible(true);//窗口是否显示  
	        this.addKeyListener(new KeyMonitor());// 监听按键  
	        new Thread(new PaintThread()).start();
	    }
	
	    @Override
		public void paint(Graphics g) {//画小格
			Color c = g.getColor();
			g.setColor(Color.GRAY);//灰色底色
			g.fillRect(0, 0,COLS * BLOCK_SIZE,ROWS*BLOCK_SIZE);
			g.setColor(Color.DARK_GRAY);
			for(int i=1;i<ROWS;i++){//画出横线
				g.drawLine(0, BLOCK_SIZE*i,COLS * BLOCK_SIZE , BLOCK_SIZE*i);
			}
			for(int i=1;i<COLS;i++){//画出竖线
				g.drawLine(BLOCK_SIZE*i, 0, BLOCK_SIZE*i, ROWS*BLOCK_SIZE );
			}
			g.setColor(Color.YELLOW);//分数颜色
			g.drawString("score: "+score,10,60);//显示内容 及 位置
			
			g.setColor(c);
			
			snake.eat(egg);
			egg.draw(g);
			snake.draw(g);
			g.setColor(Color.YELLOW);
			if(flag==false){
				g.setFont(new Font("华文彩云",Font.BOLD,50));//字体
				g.drawString("游戏结束！", 10, 150);				
				g.drawString("得分: "+score,10,200);
			}
			
		}
	    
	    @Override
		public void update(Graphics g) {//让屏幕不再闪烁
			if(offScreenImage == null){
				offScreenImage = this.createImage(COLS * BLOCK_SIZE , ROWS*BLOCK_SIZE);
			}
			Graphics gOff = offScreenImage.getGraphics();
			paint(gOff);
			g.drawImage(offScreenImage , 0,0, null);
		}
	    
	    private class  PaintThread implements Runnable {//线程类
			
	    	public void run(){
	    		//固定写法
	    		while(flag){
	    			repaint();
	    			try{
	    				Thread.sleep(100);//睡100毫秒
	    			}catch(InterruptedException	e){
	    				e.printStackTrace();
	    			}
	    		}
	    	}
	    	
		}
	    
	    
	    private class KeyMonitor extends KeyAdapter{//键盘的监听

			@Override
			public void keyPressed(KeyEvent e) {
				snake.keyPressed(e);
	
			}
	    	
	    	
	    }
	    
	    public void stop(){
	    	flag=false;
	    }
	    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Yard().launch();

	}

}
