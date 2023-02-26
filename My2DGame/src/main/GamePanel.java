package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import tiles.TileManager;

public class GamePanel extends JPanel implements Runnable{

		//SCREEN SETTINGS
		final int originalTileSize = 16; //16x16 tile
		final int scale = 3;
		
		public final int tileSize = originalTileSize * scale; // 48x48 tile
		public final int maxScreenCol = 16;
		public final int maxScreenRow = 12;
		public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
		public final int screenHeight = tileSize * maxScreenRow; // 576 pixels
		
		// FPS
		int FPS = 60;
		
		TileManager tileM = new TileManager(this);
		KeyHandler keyH = new KeyHandler();
		Thread gameThread;
		Player player = new Player(this,keyH);
		
		
		public GamePanel () {
			
			this.setPreferredSize(new Dimension(screenWidth, screenHeight));
			this.setBackground(Color.black);
			this.setDoubleBuffered(true);
			this.addKeyListener(keyH);
			this.setFocusable(true);
		}
		
		public void startGameThread() {
			
			gameThread = new Thread(this);
			gameThread.start();
		}
		
		@Override
		public void run() {
			
			while(gameThread !=null) {
				
				double drawInterval = 1000000000/FPS; //0.01666 seconds
				double nextDrawTime = System.nanoTime() + drawInterval;
				
				while(gameThread != null) {
				
				long currentTime = System.nanoTime();
				System.out.println("current Time: "+currentTime);
				
//				System.out.println("The game loop is running");
				
				// 1 UPDATE: update information such as character positions
				update();
				
				// 2 DRAW: draw the screen with the updated information
				repaint();
				
			try {
				
				double remainingTime = nextDrawTime - System.nanoTime();
				remainingTime = remainingTime/1000000;
				
				if(remainingTime < 0) {
					remainingTime = 0;
					
				}
				
				
				Thread.sleep((long) remainingTime);
				
				nextDrawTime += drawInterval;
		
		}	catch (InterruptedException e) {
				//TODO Auto-generated catch block
				e.printStackTrace();
		
				}	
				}
			}
		}
		public void update() {
			
			player.update();
			
		}
		public void paintComponent (Graphics g) {
			
			super.paintComponent(g);
			
			Graphics2D g2 = (Graphics2D)g;
			
			tileM.draw(g2);
			
			player.draw(g2);
			
			g2.dispose();
		}
}
