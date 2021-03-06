package game.models;

import java.awt.Graphics;
import java.awt.Rectangle;

import game.constants.Direction;
import game.map.Map;

public class Shell extends Sprite {
	
	private int direction;
	private boolean isDelete = false;
	private Tank owner;

	public Shell(String path, int direction, double startX, double startY, Tank owner) {
		super(path);
		this.direction = direction;
		this.speed = 20;
		this.setPosition((int) startX - getWidth()/2, (int) startY);
		this.owner = owner;
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(getImage(), getPosX(), getPosY(), null);
		Rectangle rectangle = getRect();

		for (Block block : Map.blocks)
			if (rectangle.intersects(block.getRect()))
				this.isDelete = true;

		for (java.util.Map.Entry<String, Tank> t : Map.tanks.entrySet()) {
			Tank tank = t.getValue();
			if (rectangle.intersects(tank.getRect())) 
				if (!tank.equals(owner)) {
					if (!tank.isImmortal()) {
						owner.setFrags(owner.getFrags() + 1);
						tank.die();
					}
				}
		}
		if (direction == Direction.UP) posy -= speed;
		if (direction == Direction.DOWN) posy += speed;
		if (direction == Direction.LEFT) posx -= speed;
		if (direction == Direction.RIGHT) posx += speed;
	}

	public boolean isDelete() {
		return isDelete;
	}
	
}
