package nicolas.wiedel.canyonbunny.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import nicolas.wiedel.canyonbunny.util.CameraHelper;
import nicolas.wiedel.canyonbunny.util.Constants;


/***
 * Die Klasse in der die Spiellogic verwaltet wird.
 */
public class WorldController extends InputAdapter {

    private static final String TAG = WorldController.class.getSimpleName();

    public CameraHelper cameraHelper;

    public Level level;
    public int lives;
    public int score;

    public WorldController(){
        init();
    }

    private void init(){
        Gdx.input.setInputProcessor(this);
        cameraHelper = new CameraHelper();
        lives = Constants.LIVES_START;
        initLevel();
    }

    private void initLevel(){
        score = 0;
        level = new Level(Constants.LEVEL_01);
    }

    public void update(float deltaTime){
        // DEBUGCODE
        handleInput(deltaTime);

        cameraHelper.update(deltaTime);
    }

    // ZU TESTZWECKEN 3 Methoden !!!!

    private Pixmap createProceduralPixmap(int width, int height){
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        //Quadrat mit roter Farbe füllenmit 50% alpha Wert
        pixmap.setColor(1, 0, 0, 0.5f);
        pixmap.fill();
        // gelbes X auf das Quadrat zeichen
        pixmap.setColor(1, 1, 0, 1);
        pixmap.drawLine(0, 0, width, height);
        pixmap.drawLine(width, 0, 0,  height);
        // Cyan Rahmen zeichnen
        pixmap.setColor(0, 1, 1, 1);
        pixmap.drawRectangle(0, 0, width, height);

        return pixmap;
    }


    // DEBUGCODE!!!
    private void handleInput(float deltaTime){
        if(Gdx.app.getType() != Application.ApplicationType.Desktop)
            return;

        // Kamera Kontrolle - bewegen;
        float camMoveSpeed = 5 * deltaTime;
        float camMoveSpeedAccelerationFaktor = 5f;
        if(Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)){
            camMoveSpeed *= camMoveSpeedAccelerationFaktor;
        }
        if (Gdx.input.isKeyPressed(Keys.LEFT)) moveCamera(-camMoveSpeed, 0);
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) moveCamera(camMoveSpeed, 0);
        if (Gdx.input.isKeyPressed(Keys.UP)) moveCamera(0, camMoveSpeed);
        if (Gdx.input.isKeyPressed(Keys.DOWN)) moveCamera(0, -camMoveSpeed);
        if (Gdx.input.isKeyPressed(Keys.BACKSPACE)) cameraHelper.setPosition(0, 0);

        // Kamera Kontrolle - zoom
        float camZoomSpeed = 1 * deltaTime;
        float camZoomSpeedAccelerationFactor = 5f;
        if(Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)){
            camZoomSpeed *= camZoomSpeedAccelerationFactor;
        }
        if (Gdx.input.isKeyPressed(Keys.COMMA)) cameraHelper.addZoom(camZoomSpeed);
        if (Gdx.input.isKeyPressed(Keys.PERIOD)) cameraHelper.addZoom(-camZoomSpeed);
        if (Gdx.input.isKeyPressed(Keys.SLASH)) cameraHelper.setZoom(1);
    }

    private void moveCamera(float x, float y){
        x += cameraHelper.getPosition().x;
        y += cameraHelper.getPosition().y;
        cameraHelper.setPosition(x, y);
    }

    @Override
    public boolean keyUp(int keycode){
        // die Spilewelt zurücksetzen
        if (keycode == Keys.R){
            init();
            Gdx.app.debug(TAG, "Game World resetted");
        }
        return false;
    }
}
