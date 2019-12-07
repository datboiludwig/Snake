package snake.main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import com.gamelib.application.Window;
import com.gamelib.input.Input;
import com.gamelib.util.GameLoop;

public class Main implements Runnable {
	
	Window window;
	Key key;
	GameLoop loop;
	
	private static int w = 800, h = 800;
	
	private float xv = 0, yv = -1;
	private float ax = 15, ay = 15;
	private float px = 10, py = 10;
	private float gs = w/20, tc = 20;
	
	private ArrayList<floatVec> trail = new ArrayList<floatVec>();
	private int tail = 3;
	
	public Main(int width, int height) {
		key = new Key();
		window = new Window(width, height, "title", false, key);
		loop = new GameLoop(5);
		window.setBackGround(new Color(0f, 0f, 0f));
	}
	
	public static void main(String[] args) {
		new Thread(new Main(w, h)).start();
	}

	
	public void run() {
		while(!window.shouldClose()) {
			if(loop.shouldUpdate()) {
				tick();
			}
			Graphics2D g = window.prepare();
			render(g);
			window.swapBuffers();
		}
	}
	
	private void render(Graphics2D g) {
		g.setColor(Color.GREEN);
		g.fillRect((int) (px*gs)+1, (int) (py*gs)+1, (int) gs-2, (int) gs-2);
		for(floatVec v : trail) {
			g.fillRect((int) (v.x*gs)+1, (int) (v.y*gs)+1, (int) gs-2, (int) gs-2);
			if(v.x == px && v.y == py) {
				tail = 3;
			}
		}
		
		g.setColor(Color.RED);
		g.fillRect((int) (ax*gs)+1, (int) (ay*gs)+1, (int) gs-2, (int) gs-2);
	}
	
	private void tick() {
		trail.add(new floatVec(px, py));
		while(trail.size() > tail) {
			trail.remove(0);
		}
		
		px += xv;
		py += yv;
		
		if(px < 0) {
			px = tc-1;
		}
		if(px > tc-1) {
			px = 0;
		}
		if(py < 0) {
			py = tc-1;
		}
		if(py > tc-1) {
			py = 0;
		}
		
		if(px == ax && py == ay) {
			tail++;
			ax = (float) Math.floor(Math.random()*tc);
			ay = (float) Math.floor(Math.random()*tc);
		}
	}
	
	public class Key extends Input {

		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(xv != 1) {
					xv = -1;
					yv = 0;
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(xv != -1) {
					xv = 1;
					yv = 0;
				}
				break;
			case KeyEvent.VK_UP:
				if(yv != 1) {
					xv = 0;
					yv = -1;
				}
				break;
			case KeyEvent.VK_DOWN:
				if(yv != -1) {
					xv = 0;
					yv = 1;
				}
				break;
			}
		}
	}
	
	public class floatVec {
		public float x, y;
		
		public floatVec(float tx, float ty) {
			x = tx;
			y = ty;
		}
	}
}
