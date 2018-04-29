import java.awt.Color;
import java.awt.Graphics;
import java.awt.List;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Snake {
	private class Node{		
		int w = Yard.BLOCK_SIZE;
		int h = Yard.BLOCK_SIZE;
		int row,col;
		Dir dir = Dir.L;
		Node next=null;
		Node prev=null;
		
		Node(int row, int col,Dir dir) {
			this.row = row;
			this.col = col;
			this.dir=dir;
		}
		void draw(Graphics g){
			Color c=g.getColor();
			g.setColor(Color.blue);//蓝蛇
			g.fillRect(Yard.BLOCK_SIZE*col,Yard.BLOCK_SIZE*row, w, h);//位于第几行几列，宽高
			g.setColor(c);
		}
	}
	//初始蛇
	private Node head=null;
	private Node tail=null;
	private int size=0;
	private Node n=new Node(20,20,Dir.L);
	private Yard y;
	
	public Snake(Yard y){
		head=n;
		tail=n;
		size=1;
		this.y=y;
	}
	
	
	
	public void addToTail(){
		Node node=null;
		switch (tail.dir) {
		case L:
			node=new Node(tail.row,tail.col+1,tail.dir);
			break;
		case U:
			node=new Node(tail.row+1,tail.col,tail.dir);
			break;
		case R:
			node=new Node(tail.row,tail.col-1,tail.dir);
			break;
		case D:
			node=new Node(tail.row-1,tail.col,tail.dir);
			break;

		}
		tail.next=node;
		node.prev=tail;
		tail=node;
		size++;
	}
	
	public void addToHead(){
		Node node=null;
		switch (head.dir) {
		case L:
			node=new Node(head.row,head.col-1,head.dir);
			break;
		case U:
			node=new Node(head.row-1,head.col,head.dir);
			break;
		case R:
			node=new Node(head.row,head.col+1,head.dir);
			break;
		case D:
			node=new Node(head.row+1,head.col,head.dir);
			break;

		}
		node.next=head;
		head.prev=node;
		head=node;
		size++;
	}
	
	public void draw(Graphics g){//画蛇
		if(size<=0){
			return;
		}
		move();
		for(Node n=head;n!=null;n=n.next){
			n.draw(g);
		}
		
	}



	private void move() {//根据不同方向 move   蛇要move  移动:尾部加到头部，删除尾部
		
		addToHead();
		deleteFromTail();
		checkDead();
		
	}



	private void checkDead() {
		
		if(head.row<2 || head.col<0 || head.row>Yard.ROWS-1 || head.col>Yard.COLS-1){//检验是否撞墙
			y.stop();
		}
		for(Node n=head.next;n!=null;n=n.next){//检验是否撞到自己的身子
			if(head.row==n.row&&head.col==n.col){
				y.stop();
			}
		}
		
	}



	private void deleteFromTail() {
		if(size==0) return;
		tail=tail.prev;
		tail.next=null;
		
		
	}
	
	public void eat(Egg egg){//碰撞检测
		if(this.getRect().intersects(egg.getRect())){
			egg.reAppear();
			this.addToHead();
			y.setScore(y.getScore()+5);
		}
	}
	private Rectangle getRect(){
		return new Rectangle(Yard.BLOCK_SIZE*head.col,Yard.BLOCK_SIZE*head.row,head.w,head.h);//蛇头Rectangle
	}
	

	public void keyPressed(KeyEvent e) {//蛇头方向， 键盘控制
		int key=e.getKeyCode();
		System.out.println(key);
		switch(key){
		case KeyEvent.VK_LEFT:
			if(head.dir!=Dir.R){
				head.dir=Dir.L;
			}
			
			break;
		case KeyEvent.VK_UP:
			if(head.dir!=Dir.D){
			head.dir=Dir.U;
			}
			break;
		case KeyEvent.VK_RIGHT:
			if(head.dir!=Dir.L){
			head.dir=Dir.R;
			}
			break;
		case KeyEvent.VK_DOWN:
			if(head.dir!=Dir.U){
			head.dir=Dir.D;
			}
			break;
		}
		
	}
	
	
}
