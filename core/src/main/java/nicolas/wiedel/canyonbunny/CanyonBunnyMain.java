package nicolas.wiedel.canyonbunny;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.utils.ScreenUtils;
import nicolas.wiedel.canyonbunny.game.WorldController;
import nicolas.wiedel.canyonbunny.game.WorldRenderer;
import nicolas.wiedel.canyonbunny.util.Constants;

public class CanyonBunnyMain implements ApplicationListener {

    private static final String TAG = CanyonBunnyMain.class.getSimpleName();

    private WorldController worldController;
    private WorldRenderer worldRenderer;

    @Override
    public void create() {
    }


    @Override
    public void render() {
        ScreenUtils.clear(Constants.CORNFLOWERBLUE);
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
    }
}
