package nicolas.wiedel.canyonbunny.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import nicolas.wiedel.canyonbunny.util.Constants;

/***
 * Zentrale Klassein der das zeichnen passiert
 */
public class WorldRenderer implements Disposable {

    public static final String TAG = WorldRenderer.class.getSimpleName();

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private WorldController worldController;

    public WorldRenderer(WorldController worldController){
        this.worldController = worldController;
        init();
    }

    private void init(){
        batch = new SpriteBatch();
        camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        camera.position.set(0, 0, 0);
        camera.update();
    }

    public void render(){
        // ZU TESTZWECKEN!!!!!!
        renderTestObjects();
    }

    public void resize(int width, int height){
        camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) * width;
        camera.update();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    // ZU TESTZWECKEN!!!!!!
    public void renderTestObjects(){
        worldController.cameraHelper.applyTo(camera);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (Sprite sprite : worldController.testSprites){
            sprite.draw(batch);
        }
        batch.end();
    }
}
