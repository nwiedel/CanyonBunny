package nicolas.wiedel.canyonbunny.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import nicolas.wiedel.canyonbunny.util.CameraHelper;


/***
 * Die Klasse in der die Spiellogic verwaltet wird.
 */
public class WorldController extends InputAdapter {

    private static final String TAG = WorldController.class.getSimpleName();

    // ZU TESTZWECKEN!!!!
    public Sprite[] testSprites;
    public int selectedSprite;

    public CameraHelper cameraHelper;

    public WorldController(){
        init();
    }

    private void init(){
        Gdx.input.setInputProcessor(this);
        cameraHelper = new CameraHelper();
        initTestObjects();
    }

    public void update(float deltaTime){
        // DEBUGCODE
        handleInput(deltaTime);

        // ZU TESTZWECKEN!!!!!
        updateTestObjects(deltaTime);

        cameraHelper.update(deltaTime);
    }

    // ZU TESTZWECKEN 3 Methoden !!!!
    private void initTestObjects(){
        // Array von 5 Sprites
        testSprites = new Sprite[5];
        // ein leeres POT-sized Pixmap mit 8 bit RGBA pixel data erstellen
        int width = 32;
        int height = 32;
        Pixmap pixmap = createProceduralPixmap(width, height);
        // Texture aus Pixmapdata erstellen
        Texture texture = new Texture(pixmap);
        // neue Sprites auf Grundlage der Textur erstellen
        for (int i = 0; i < testSprites.length; i++){
            Sprite spr = new Sprite(texture);
            // SpritGröße definieren: 1m x 1m in der Gameworld
            spr.setSize(1, 1);
            // Origin auf Spritecenter setzen
            spr.setOrigin(spr.getWidth() / 2.0f, spr.getHeight() / 2.0f);
            // Zufällige Position berechnen
            float randomX = MathUtils.random(-2.0f, 2.0f);
            float randomY = MathUtils.random(-2.0f, 2.0f);
            spr.setPosition(randomX, randomY);
            // neuws Spite ins Array einfügen
            testSprites[i] = spr;
        }
        // erstes Sprite als ausgewähltes setzen
        selectedSprite = 0;
    }
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
    private void updateTestObjects(float deltaTime){
        // Rotation des gewälten Sprites ermitteln
        float rotation = testSprites[selectedSprite].getRotation();
        // Sprite um 90 Grad pro Sekunde drehen
        rotation += 90 * deltaTime;
        // Rotation auf 360 Grad begrenzen
        rotation %= 360;
        // Rotation setzen
        testSprites[selectedSprite].setRotation(rotation);
    }

    // DEBUGCODE!!!
    private void handleInput(float deltaTime){
        if(Gdx.app.getType() != Application.ApplicationType.Desktop)
            return;

        //ausgewältes Sprite kontollieren
        float sprMoveSpeed = 5 * deltaTime;
        if(Gdx.input.isKeyPressed(Keys.A))
            moveSelectedSprite(-sprMoveSpeed, 0);
        if(Gdx.input.isKeyPressed(Keys.D))
            moveSelectedSprite(sprMoveSpeed, 0);
        if(Gdx.input.isKeyPressed(Keys.W))
            moveSelectedSprite(0, sprMoveSpeed);
        if(Gdx.input.isKeyPressed(Keys.S))
            moveSelectedSprite(0, -sprMoveSpeed);

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
    private void moveSelectedSprite(float x, float y){
        testSprites[selectedSprite].translate(x, y);
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
        // nächtes Sprite anwählen
        if (keycode == Keys.SPACE){
            selectedSprite = (selectedSprite + 1) % testSprites.length;
            // die Kamera aktualisieren um dem aktuellen Sprite zu folgen
            if(cameraHelper.hasTarget()){
                cameraHelper.setTarget(testSprites[selectedSprite]);
            }
            Gdx.app.debug(TAG, "Sprite # " + selectedSprite + " selected");
        }
        // Kamera umschalten
        else if (keycode == Keys.ENTER){
            cameraHelper.setTarget(cameraHelper.hasTarget() ? null : testSprites[selectedSprite]);
            Gdx.app.debug(TAG, "CamerFollw enabled: " + cameraHelper.hasTarget());
        }
        return false;
    }
}
