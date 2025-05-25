package nicolas.wiedel.canyonbunny.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import nicolas.wiedel.canyonbunny.game.objects.*;

public class Level {

    public static final String TAG = Level.class.getSimpleName();

    public enum BLOCK_TYPE{
        EMPTY(0, 0, 0), // schwarz
        ROCK(0, 255, 0), // grün
        PLAYER_SPAWNPOINT(255, 255, 255), // weiß
        ITEM_FEATHER(255, 0, 255), // rosa
        ITEM_GOLD_COIN(25, 255, 0); // gelb

        private int color;
        private BLOCK_TYPE(int r, int g, int b){
            color = r << 24 | g << 16 | b << 8 | 0xff;
        }

        public boolean sameColor( int color){
            return this.color == color;
        }

        public int getColor(){
            return color;
        }
    }

    // Objekte
    public Array<Rock> rocks;

    // Dekoration
    public Clouds clouds;
    public Mountains mountains;
    public WaterOverlay waterOverlay;

    public Level(String fileName){
        init(fileName);
    }

    private void init(String fileName){
        // Objekte
        rocks = new Array<>();

        // lade die Datei, die den Level repräsentiert
        Pixmap pixmap = new Pixmap(Gdx.files.internal(fileName));
        // Scanne die Pixels von oben links nach unten rechts
        int lastPixel = -1;
        for (int pixelY = 0; pixelY < pixmap.getHeight(); pixelY++){
            for (int pixelX = 0; pixelX < pixmap.getWidth(); pixelX++){
                AbstractGameObject obj = null;
                float offsetHeight = 0;
                // Die Höhe wächst von unten nach oben.
                float baseHeight = pixmap.getHeight() - pixelY;
                // hole die Farbe als 32.Bit RGBA Wert
                int currentPixel = pixmap.getPixel(pixelX, pixelY);

                // erstelle BlockType entsprechend der Farbe
                // empty Block
                if(BLOCK_TYPE.EMPTY.sameColor(currentPixel)){
                    // nichts tun
                }

                //Rock
                else if (BLOCK_TYPE.ROCK.sameColor(currentPixel)){
                    if(lastPixel != currentPixel){
                        obj = new Rock();
                        float heightIncreaseFactor = 0.25f;
                        offsetHeight = -2.5f;
                        obj.position.set(pixelX, baseHeight * obj.dimension.y * heightIncreaseFactor + offsetHeight);
                        rocks.add((Rock)obj);
                    } else {
                        rocks.get(rocks.size - 1).increaseLength(1);
                    }
                }

                // Spieler Spawn Punkt
                else if (BLOCK_TYPE.PLAYER_SPAWNPOINT.sameColor(currentPixel)){

                }

                // Feder
                else if (BLOCK_TYPE.ITEM_FEATHER.sameColor(currentPixel)){

                }

                // Münze
                else if(BLOCK_TYPE.ITEM_GOLD_COIN.sameColor(currentPixel)){

                }

                // unbekanntes Objekt
                else {
                    int r = 0xff & (currentPixel >>> 24); //red color channel
                    int g = 0xff & (currentPixel >>> 16); //green color channel
                    int b = 0xff & (currentPixel >>> 8);  //blue color channel
                    int a = 0xff & currentPixel;  //alpha channel
                    Gdx.app.error(TAG, "Unknown object at x<" + pixelX + "> y<" + pixelY + ">: r<" + r+ "> g<" + g + "> b<" + b + "> a<" + a + ">");
                }
                lastPixel = currentPixel;
            }
        }

        // Dekoration
        clouds = new Clouds(pixmap.getWidth());
        clouds.position.set(0, 2);
        mountains = new Mountains(pixmap.getWidth());
        mountains.position.set(-1, -1);
        waterOverlay = new WaterOverlay(pixmap.getWidth());
        waterOverlay.position.set(0, 3.75f);

        // Speicher frei geben
        pixmap.dispose();
        Gdx.app.debug(TAG, "Level: " + fileName + " geladen");
    }

    public void render(SpriteBatch batch){
        // Berge zeichnen
        mountains.render(batch);

        // Rocks zeichnen
        for (Rock rock : rocks){
            rock.render(batch);
        }

        // Wateroverlay zeichnen
        waterOverlay.render(batch);

        // Wolken zeichnen
        clouds.render(batch);
    }

}
