/** NBody is the class that runs simulations */
public class NBody {

	/* readradius method read the radius of the universe
	 * in the file supplied. */
	public static double readRadius(String fileName) {
		In input = new In(fileName);
		int numOfPlanets = input.readInt();
		double radius = input.readDouble();
		return radius;
	}

	/* Read all the bodies in the given file. */
	public static Body[] readBodies(String fileName) {
		In input = new In(fileName);
		int numOfPlanets = input.readInt();
		double radius = input.readDouble();
		Body[] bArray = new Body[numOfPlanets];
		for (int i = 0; i < numOfPlanets; i += 1) {
			/* Read the body parameters first. */
			double xP = input.readDouble();
			double yP = input.readDouble();
			double xV = input.readDouble();
			double yV = input.readDouble();
			double m = input.readDouble();
			String img = input.readString();
			/* Initialize the ith body */
			bArray[i] = new Body(xP, yP, xV, yV, m, img);
		}
		return bArray;
	}

	/* Main function. Enables drawing the universe 
	 * in its starting position. */
	public static void main(String[] args) {
		/* Time limit */
		double T = Double.parseDouble(args[0]);
		/* Time interval */
		double dt = Double.parseDouble(args[1]);
		/* Universe source file */
		String filename = args[2];
		/* Current time */
		double time = 0;
		/* Universe radius */
		double radius = readRadius(filename);
		/* All the Bodies */
		Body[] bArray = readBodies(filename);
		/* The net x and y forces for each Body */
		double[] xForces = new double[bArray.length];
		double[] yForces = new double[bArray.length];

		StdDraw.enableDoubleBuffering();
		StdDraw.setScale(-radius, radius);
		StdDraw.clear();
		StdDraw.picture(0, 0, "images/starfield.jpg");

		/* Simulation starts. */
		while (time < T) {
			/* Calculate the net x and y forces for each Body. */
			for (int i = 0; i < bArray.length; i += 1) {
				xForces[i] = bArray[i].calcNetForceExertedByX(bArray);
				yForces[i] = bArray[i].calcNetForceExertedByY(bArray);
			}
			/* update each body’s position, velocity, 
			 *and acceleration. */
			for (int j = 0; j < bArray.length; j += 1) {
				bArray[j].update(dt, xForces[j], yForces[j]);
			}
			/* Draw background image. */
			StdDraw.picture(0, 0, "images/starfield.jpg");
			/* Draw all of the Bodies. */
			for (Body b: bArray) {
				b.draw();
			}
			StdDraw.show();
			StdDraw.pause(10);
			time += dt;
		}
		
		StdDraw.show();
	}
}