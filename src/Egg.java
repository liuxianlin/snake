import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Egg {
	
	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getRow() {
		return row;
	}
	
	public void setRow(int row) {
		this.row = row;
	}

	int w=Yard.BLOCK_SIZE;
	int h=Yard.BLOCK_SIZE;
	int col,row;
	private static Random r = new Random();
	private static Color color=Color.GREEN;
	
	public Egg(int row, int col) {
		this.col = col;
		this.row = row;
	}
	
	public Egg(){
		this(r.nextInt(Yard.ROWS-3)+3, r.nextInt(Yard.COLS-3)+3);//随机找一行  一列  
	}
	
	//重新出现egg
		public void reAppear(){
			this.row=r.nextInt(Yard.ROWS-2)+2;//防止出现在 蓝色标题框里
			this.col=r.nextInt(Yard.COLS-2)+2;
		}
	
	public Rectangle getRect(){
		return new Rectangle(Yard.BLOCK_SIZE*col,Yard.BLOCK_SIZE*row,w,h);//蛋Rectangle

	}
	
	public void draw(Graphics g){
		Color c=g.getColor();
		g.setColor(color);
		g.fillOval(Yard.BLOCK_SIZE*col,Yard.BLOCK_SIZE*row, w, h);//fillOval圆的     位于第几行几列，宽高
		g.setColor(c);
		//控制egg的闪烁
		if(color==Color.GREEN) color=Color.RED;//如果是绿色  再画红色
		else color=Color.GREEN;//如果是红色  再画绿色
	}
}
