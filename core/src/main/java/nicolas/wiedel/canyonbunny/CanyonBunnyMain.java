package nicolas.wiedel.canyonbunny;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.ScreenUtils;
import nicolas.wiedel.canyonbunny.game.Assets;
import nicolas.wiedel.canyonbunny.game.WorldController;
import nicolas.wiedel.canyonbunny.game.WorldRenderer;
import nicolas.wiedel.canyonbunny.util.Constants;

/***
 * Hauptklasse, die von der LWJGLApplication gestartet wird.
 * Hier befindet sich in der render() Methode die GameLoop.
 */
public class CanyonBunnyMain implements ApplicationListener {

    private static final String TAG = CanyonBunnyMain.class.getSimpleName();

    private WorldController worldController;
    private WorldRenderer worldRenderer;

    private boolean paused;

    @Override
    public void create() {
        // Setze LibGDX log level auf debug!
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        // lade Assets
        Assets.INSTANCE.init(new AssetManager());
        // Initialisiere Contrller und Renderer
        worldController = new WorldController();
        worldRenderer = new WorldRenderer(worldController);
        // Die GameWorld ist aktiv beim Start
        paused = false;
    }


    @Override
    public void render() {

        // Kein update(), wenn die GameWorld pausiert!
        if(!paused){
            // Update die GameWorld in Abhängigkeit von der
            // vergangenen Zeit seit des letzten Frames
            worldController.update(Gdx.graphics.getDeltaTime());
        }
        // Die Farbe des ClearScreen auf Cornflowerblue setzen
        ScreenUtils.clear(Constants.CORNFLOWERBLUE);
        // Die GameWorld zeichnen.
        worldRenderer.render();
    }

    @Override
    public void resize(int width, int height) {
        worldRenderer.resize(width, height);
    }

    @Override
    public void pause() {
        paused = true;
    }

    @Override
    public void resume() {
        paused = false;
    }

    @Override
    public void dispose() {
        worldRenderer.dispose();
        Assets.INSTANCE.dispose();
    }
}
