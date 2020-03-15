package exp.zhen.zayta.main.sokoban;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import exp.zhen.zayta.main.SizeManager;
import exp.zhen.zayta.util.GdxUtils;

import static exp.zhen.zayta.main.SizeManager.SCALE;
import static exp.zhen.zayta.main.SizeManager.VIRTUAL_HEIGHT;
import static exp.zhen.zayta.main.SizeManager.VIRTUAL_WIDTH;

public class PuzzleRenderer {


    private final OrthographicCamera camera;
    private final Viewport viewport;
    private boolean cameraShouldMove;

    public PuzzleRenderer(Viewport viewport, OrthographicCamera camera) {
        this.camera = camera;
//        viewport = new ExtendViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        this.viewport = viewport;
    }


    public void render(Puzzle puzzle, SpriteBatch batch) {
        GdxUtils.clearScreen();

        if (puzzle.getWidth() > VIRTUAL_HEIGHT || puzzle.getHeight() > VIRTUAL_HEIGHT) {
            cameraShouldMove = true;
        } else {
            cameraShouldMove = false;
        }

        viewport.apply();

        updateCamera(puzzle);

        batch.enableBlending();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        puzzle.render(batch, SCALE);
        batch.end();
    }

    private void updateCamera(Puzzle puzzle) {
        if (cameraShouldMove) {
            Vector2 playerPosition = puzzle.getPlayer().getPosition();
            Vector3 newCameraPosition = new Vector3(playerPosition.x * SCALE,
                    playerPosition.y * SCALE, 0);
            camera.position.interpolate(newCameraPosition, 0.45f, Interpolation.exp10In);
        } else {
            camera.position.set(puzzle.getWidth() * 0.5f, puzzle.getHeight() * 0.5f, 0);
        }
    }
}