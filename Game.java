import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.*;

@SuppressWarnings("serial")
public class Game extends JComponent {

	BufferedImage image; 
	int mouseX;
	int mouseY;
	double x;
	double y;
	double forceX;
	double forceY;
	int diameter = 20;
	int oldX;
	int oldY;
	boolean inGame;
	boolean stopGame = false;
	Random rand = new Random();
	int timer;
	int score;
	int particleCount;
	boolean newGame = false;
	int highScore;
	int particleNumber;
	File saveFile = new File("p:/High.txt");
	String topPlayer;
	boolean highRead=false;

	Vector<particle> particleVector = new Vector<particle>();


	public Game() {

		this.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();

				if (mouseX > 500){
					mouseX = 500;
				}
				if (mouseX < 0){
					mouseX = 0;
				}
				if (mouseY > 500){
					mouseY = 500;
				}
				if (mouseY < 0){
					mouseY = 0;
				}
				repaint();
			}
			public void mouseDragged(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();

				if (mouseX > 500){
					mouseX = 500;
				}
				if (mouseX < 0){
					mouseX = 0;
				}
				if (mouseY > 500){
					mouseY = 500;
				}
				if (mouseY < 0){
					mouseY = 0;
				}

				repaint();
			}



		});

		this.addMouseListener(new MouseAdapter() {


			public void mouseEntered(MouseEvent e){
				System.out.println("Mouse entered!");
				inGame = true;
			}
			public void mouseExited(MouseEvent e){
				System.out.println("Mouse exited!");
				inGame = false;

			}

		});

		this.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e){
				if (!newGame){
					newGame = true;
					particleVector.add(new particle(rand.nextInt(400), rand.nextInt(400), rand.nextDouble()-.5, rand.nextDouble()-.5));
					particleVector.add(new particle(rand.nextInt(400), rand.nextInt(400), rand.nextDouble()-.5, rand.nextDouble()-.5));
					particleVector.add(new particle(rand.nextInt(400), rand.nextInt(400), rand.nextDouble()-.5, rand.nextDouble()-.5));
				}
			}

		});

	}

	public void endGame(particle p){


		// the center of p
		double centerX = p.x + p.diameter/2;
		double centerY = p.y + p.diameter/2;

		double distance = 
			Math.sqrt(
					Math.pow(mouseX-centerX, 2) +
					Math.pow(mouseY-centerY,2)
			);

		if (distance < diameter/2 + p.diameter/2) {
			if(!stopGame){
				saveHighScore(saveFile);
			}
			stopGame = true;
		}
	}

	public void timer(){
		if (!stopGame){
			timer = timer + 1;
			score = score +1;
			particleCount = particleVector.size();
			if (timer>5000){
				particleVector.add(new particle(rand.nextInt(400), rand.nextInt(400), rand.nextDouble()-.5, rand.nextDouble()-.5));

				timer = 0;
			}
		}
	}

	public void highScore(File f){
		try {
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			Scanner in = new Scanner(br);
			highScore = in.nextInt();
			particleNumber = in.nextInt();
			topPlayer = in.nextLine();


		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void saveHighScore(File f) {
		if(((int)score/100) > highScore){
			try {
				FileWriter fw = new FileWriter(f);
				//write the hight and width
				System.out.println("Enter Name: ");
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

				String userName = null;

				//  read the username from the command-line; need to use try/catch with the
				//  readLine() method
				try {
					userName = br.readLine();
				} catch (IOException ioe) {
					System.out.println("IO error trying to read your name!");
					System.exit(1);
				}



				fw.write(((int)score/100) +" "+ particleCount +" "+ userName);
				fw.write("\n");


				fw.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}








	@Override
	public void paintComponent(Graphics g) {
		if(!highRead){
			highScore(saveFile);
			highRead = true;
		}


		g.setColor(Color.blue);
		g.fillOval(mouseX, mouseY, 10, 10);

		if (image == null) {
			//make image as big as the JComponent
			image = (BufferedImage) createImage(this.getWidth(),this.getHeight());
		}
		Graphics g2 = image.getGraphics();

		if(inGame){
			g2.setColor(Color.WHITE);
		}else{
			g2.setColor(Color.white);
		}
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());
		g2.setColor(Color.blue);
		g2.fillOval(mouseX-10, mouseY-10, 20, 20);


		for (int i = 0; i <particleVector.size(); i++){
			particleVector.elementAt(i).draw(g2);
			particleVector.elementAt(i).wallBounce(image.getWidth(), image.getHeight());
			particleVector.elementAt(i).move(stopGame);
			for (int j= 0; j <particleVector.size(); j++){
				particleVector.elementAt(i).crash(particleVector.elementAt(j));
			}
			endGame(particleVector.elementAt(i));
		}


		g2.drawString("Score: " + ((int)score/100) +" | Paricles: " + particleCount , 0, 25);
		g2.drawString(topPlayer + " | High Score: " + (highScore) +" | Paricles: " + particleNumber , 0, 10);

		if(newGame){
			timer();

		}



		g.drawImage(image,0,0,null);


		repaint();
	}


}
