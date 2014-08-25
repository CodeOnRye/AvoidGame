import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;


public class particle {
	double x;
	double y;
	double forceX;
	double forceY;
	int diameter = 20;
	int oldX;
	int oldY;
	Random rand = new Random();





	public particle(double x, double y, double forceX, double forceY){

		this.x = x;
		this.y = y;
		this.forceX = forceX;
		this.forceY = forceY;

	}

	public void wallBounce(int width, int height){
		// right wall
		if (this.x + this.diameter >= width) {
			forceX = -forceX;
			this.x = width - diameter;
		}

		// left wall
		if (this.x  <= 0) {
			forceX = -forceX;
			this.x = 0;
		}

		// top wall
		if (this.y <= 0) {
			forceY = -forceY;
			this.y = 0;
		}

		// bottom wall 
		if (this.y + diameter >= height) {
			forceY = -forceY;
			this.y = height - diameter;
		}
	}

	public void draw(Graphics g) {


		g.setColor(Color.red);
		g.fillOval((int)x, (int)y, diameter, diameter);


	}

	public void move(boolean stopGame) {
		if (stopGame == false){
			oldX = (int) x;
			oldY = (int) y;
			x =  (x + forceX);
			y =  (y + forceY);
		}else{

		}


	}

	public void crash(particle p) {

		// the center of this particle
		double cx1 = this.x + diameter/2;
		double cy1 = this.y + diameter/2;

		// the center of p
		double cxp = p.x + p.diameter/2;
		double cyp = p.y + p.diameter/2;

		double distance = 
			Math.sqrt(
					Math.pow(cx1-cxp, 2) +
					Math.pow(cy1-cyp,2)
			);

		if (distance < diameter/2 + p.diameter/2) {
			// have particle swap their x forces
			double temp = forceX; 
			forceX =  p.forceX;
			p.forceX = temp;

			// have particle swap their y forces 
			temp = forceY;
			forceY = p.forceY;
			p.forceY = temp;



		}

	}
}
