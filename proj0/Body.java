/** Body class
 * @author Dawei Gu */

public class Body {
	
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;
	public static final double g = 6.67408e-11;

	/* Body constructor 1 */
	public Body(double xP, double yP, double xV,
              double yV, double m, String img) {
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}

	/* Body constructor 2, make a copy. */
	public Body(Body b) {
		this.xxPos = b.xxPos;
		this.yyPos = b.yyPos;
		this.xxVel = b.xxVel;
		this.yyVel = b.yyVel;
		this.mass = b.mass;
		this.imgFileName = b.imgFileName;
	}

	/* Calculate the distance between the body that's doing the calculation
	 * and the sipplied body. */
	public double calcDistance(Body b) {
		double dx = this.xxPos - b.xxPos;
		double dy = this.yyPos - b.yyPos;
		double distance = Math.sqrt(dx * dx + dy * dy);
		return distance;	
	}

	/* Calculate the force excerted on this body by the body supplied */
	public double calcForceExertedBy(Body b) {
		double force = g * this.mass * b.mass /
			(calcDistance(b) * calcDistance(b));
		return force;
	}

	/* Calculate the force excerted in the X direction
	 * on this body by the body supplied */
	public double calcForceExertedByX(Body b) {
		double xForce = this.calcForceExertedBy(b) * (b.xxPos - this.xxPos) /
			this.calcDistance(b);
		return xForce;
	}

	/* Calculate the force excerted in the Y direction
	 * on this body by the body supplied */
	public double calcForceExertedByY(Body b) {
		double yForce = this.calcForceExertedBy(b) * (b.yyPos - this.yyPos) /
			this.calcDistance(b);
		return yForce;
	}

	/* Calculate the force excerted in the X direction
	 * on this body by all the body in the system */
	public double calcNetForceExertedByX(Body[] bArray) {
		double xNetForce = 0;
		for (Body b: bArray) {
			if (this.equals(b)) {
				continue;
			} else {
				xNetForce += this.calcForceExertedByX(b);
			}
		}
		return xNetForce;
	}

	/* Calculate the force excerted in the Y direction
	 * on this body by all the body in the system */
	public double calcNetForceExertedByY(Body[] bArray) {
		double yNetForce = 0;
		for (Body b: bArray) {
			if (this.equals(b)) {
				continue;
			} else {
				yNetForce += this.calcForceExertedByY(b);
			}
		}
		return yNetForce;
	}

	/* determines how much the forces exerted on the body 
	 * will cause that body to accelerate, and the
	 * resulting change in the body’s velocity and position
	 * in a small period of time dt */
	public void update(double dt, double xNetForce, 
		double yNetForce) {
		double xAcc = xNetForce / this.mass;
		double yAcc = yNetForce / this.mass;
		this.xxVel += dt * xAcc;
		this.yyVel += dt * yAcc;
		this.xxPos += dt * xxVel;
		this.yyPos += dt *yyVel;
	}

	/* Uses StdDraw API to draw the body's image at
	 * the body's position. */
	public void draw() {
		StdDraw.picture(xxPos, yyPos, String.format(
			"images/%s", imgFileName));
	}
}