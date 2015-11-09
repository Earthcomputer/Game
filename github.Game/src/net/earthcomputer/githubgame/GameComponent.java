package net.earthcomputer.githubgame;

import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

import net.earthcomputer.githubgame.geom.Pos;

public abstract class GameComponent extends JComponent {

	private static final long serialVersionUID = 4039393389062942365L;

	private Pos pos;
	private Shape collisionMask;
	private boolean canDoFastCollision;
	private boolean fastCollision = true;

	public GameComponent(float x, float y) {
		this.pos = new Pos(x, y);
		setLocation((int) x, (int) y);
	}

	public Pos getPos() {
		return pos;
	}

	public float getXPos() {
		return pos.x;
	}

	public float getYPos() {
		return pos.y;
	}

	public void setPos(Pos pos) {
		this.pos = pos;
		setLocation((int) pos.x, (int) pos.y);
	}

	public void setXPos(float xpos) {
		pos.x = xpos;
		setLocation((int) xpos, getY());
	}

	public void setYPos(float ypos) {
		pos.y = ypos;
		setLocation(getX(), (int) ypos);
	}

	public boolean hasCollisionMask() {
		return collisionMask != null;
	}

	public Shape getCollisionMask() {
		return collisionMask;
	}

	public void setCollisionMask(Shape mask) {
		canDoFastCollision = mask instanceof Rectangle2D;
		collisionMask = mask;
	}

	protected void setDoFastCollision(boolean doFastCollision) {
		fastCollision = doFastCollision;
	}

	protected boolean doesFastCollision() {
		return fastCollision;
	}

	public boolean requiresUpdate() {
		return false;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}

	public void draw(Graphics g) {

	}

	public boolean isCollidedWith(Shape other) {
		if (collisionMask == null || other == null)
			return false;
		if (canDoFastCollision && fastCollision)
			return other.intersects((Rectangle2D) collisionMask);
		else if (other instanceof Rectangle2D && fastCollision)
			return collisionMask.intersects((Rectangle2D) other);
		else {
			Area area = new Area(collisionMask);
			area.intersect(new Area(other));
			return !area.isEmpty();
		}
	}

	public boolean isCollidedWith(GameComponent other) {
		if (collisionMask == null || other.collisionMask == null)
			return false;
		boolean doFastCollision = fastCollision && other.fastCollision;
		if (canDoFastCollision && doFastCollision) {
			return other.collisionMask.intersects((Rectangle2D) collisionMask);
		} else if (other.canDoFastCollision && doFastCollision) {
			return collisionMask.intersects((Rectangle2D) other.collisionMask);
		} else {
			Area area = new Area(collisionMask);
			area.intersect(new Area(other.collisionMask));
			return !area.isEmpty();
		}
	}

}
