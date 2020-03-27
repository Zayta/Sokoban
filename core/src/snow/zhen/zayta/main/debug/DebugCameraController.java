package snow.zhen.zayta.main.debug;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;


public class DebugCameraController {

    // == constants ==

    // == attributes ==
    private Vector2 position = new Vector2();
    private Vector2 startPosition = new Vector2();
    private float zoom = 1.0f;
    private DebugCameraConfig config;

    // == constructor ==
    public DebugCameraController() {
        config = new DebugCameraConfig();
    }

    // == public methods ==
    public void setStartPosition(float x, float y) {
        startPosition.set(x, y);
        position.set(x, y);
    }

    public void applyTo(OrthographicCamera orthographicCamera) {
        orthographicCamera.position.set(position, 0);
        orthographicCamera.zoom = zoom;
        orthographicCamera.update();
    }

    public void handleDebugInput(float delta) {
        // check if we are not on desktop then dont handle input just return
        if (Gdx.app.getType() != Application.ApplicationType.Desktop) {
            return;
        }

        float moveSpeed = config.getMoveSpeed() * delta;
        float zoomSpeed = config.getZoomSpeed() * delta;

        // move controls
        if (config.isLeftPressed()) {
            moveLeft(moveSpeed);
        } else if (config.isRightPressed()) {
            moveRight(moveSpeed);
        } else if (config.isUpPressed()) {
            moveUp(moveSpeed);
        } else if (config.isDownPressed()) {
            moveDown(moveSpeed);
        }

        // zoom controls
        if (config.isZoomInPressed()) {
            zoomIn(zoomSpeed);
        } else if (config.isZoomOutPressed()) {
            zoomOut(zoomSpeed);
        }

        // reset controls
        if (config.isResetPressed()) {
            reset();
        }

        // log controls
        if (config.isLogPressed()) {
            logDebug();
        }
    }

    // == private methods ==
    private void setPosition(float x, float y) {
        position.set(x, y);
    }

    private void setZoom(float value) {
        zoom = MathUtils.clamp(value, config.getMaxZoomIn(), config.getMaxZoomOut());
    }

    private void moveCamera(float velX, float velY) {
        setPosition(position.x + velX, position.y + velY);
    }

    private void moveLeft(float speed) {
        moveCamera(-speed, 0);
    }

    private void moveRight(float speed) {
        moveCamera(speed, 0);
    }

    private void moveUp(float speed) {
        moveCamera(0, speed);
    }

    private void moveDown(float speed) {
        moveCamera(0, -speed);
    }

    private void zoomIn(float zoomSpeed) {
        setZoom(zoom + zoomSpeed);
    }

    private void zoomOut(float zoomSpeed) {
        setZoom(zoom - zoomSpeed);
    }

    private void reset() {
        position.set(startPosition);
        setZoom(1.0f);
    }

    private void logDebug() {
    }
}
