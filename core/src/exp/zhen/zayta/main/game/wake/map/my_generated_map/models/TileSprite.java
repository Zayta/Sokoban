package exp.zhen.zayta.main.game.wake.map.my_generated_map.models;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import exp.zhen.zayta.main.game.wake.map.my_generated_map.World;

public class TileSprite {
	public static final Color landColor = new Color(77/255f,219/255f,114/255f,1);
	public static final Color waterColor = new Color(77/255f,219/255f,255/255f,1);
	public static final Color lineColor = Color.valueOf("AEE239");

	private Integer level;
	private Sprite linePart, solidPart;

	public TileSprite(Integer level) {
		this.level = level;
	}

	public void draw(World world, SpriteBatch batch, GeneratedCell generatedCell) {
		if (solidPart!=null) {
			solidPart.setBounds(generatedCell.x, generatedCell.y, 1, 1);
			solidPart.setColor(world.getColorFromId(level));
			solidPart.draw(batch);
		}

		if (linePart!=null) {
			linePart.setBounds(generatedCell.x, generatedCell.y, 1, 1);
			linePart.setColor(lineColor);
			linePart.draw(batch);
		}
	}

	public void setLinePart(Sprite sprite) {
		this.linePart = sprite;
	}

	public void setSolidPart(Sprite sprite) {
		this.solidPart = sprite;
	}

}
