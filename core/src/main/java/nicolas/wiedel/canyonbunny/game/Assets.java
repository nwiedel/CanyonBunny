package nicolas.wiedel.canyonbunny.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;
import nicolas.wiedel.canyonbunny.util.Constants;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class Assets implements Disposable, AssetErrorListener {
    private static final String TAG = Assets.class.getSimpleName();

    public static final Assets INSTANCE = new Assets();

    private AssetManager assetManager;

    public AssetBunny bunny;
    public AssetRock rock;
    public AssetGoldCoin goldCoin;
    public AssetFeather feather;
    public AssetLevelDecoration levelDecoration;

    // Singleton Pattern Konstuktor
    private Assets(){}

    public void init(AssetManager assetManager){
        this. assetManager = assetManager;
        // AssetManager als ErrorHandler setzen
        assetManager.setErrorListener(this);
        // TextureAtlas laden
        assetManager.load(Constants.TEXTURE_ATLAS_OBJECTS, TextureAtlas.class);
        // Warte, bis alles geladen ist
        assetManager.finishLoading();
        Gdx.app.debug(TAG, "# off assets loaded: " + assetManager.getAssetNames().size);
        for(String a : assetManager.getAssetNames()){
            Gdx.app.debug(TAG, "asset: " + a);
        }

        TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS_OBJECTS);

        // filtering aktivieren für wichere Pixel
        for (Texture t : atlas.getTextures()){
            t.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }

        // Spiel Ressourcen erstelle
        bunny = new AssetBunny(atlas);
        rock = new AssetRock(atlas);
        goldCoin = new AssetGoldCoin(atlas);
        feather = new AssetFeather(atlas);
        levelDecoration = new AssetLevelDecoration(atlas);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.debug(TAG, "Could not load asset '" + asset.fileName + "#");
    }

    public class AssetBunny{
        public final AtlasRegion head;

        public AssetBunny(TextureAtlas atlas){
            head = atlas.findRegion("bunny_head");
        }
    }

    public class AssetRock{
        public final AtlasRegion edge;
        public final AtlasRegion middle;

        public AssetRock(TextureAtlas atlas){
            edge = atlas.findRegion("rock_edge");
            middle = atlas.findRegion("rock-middle");
        }
    }

    public class AssetGoldCoin{
        public final AtlasRegion goldCoin;

        public AssetGoldCoin(TextureAtlas atlas){
            goldCoin = atlas.findRegion("item_gold_coin");
        }
    }

    public class AssetFeather{
        public final AtlasRegion feather;

        public AssetFeather(TextureAtlas atlas){
            feather = atlas.findRegion("item_feather");
        }
    }

    public class AssetLevelDecoration{
        public final AtlasRegion cloud1;
        public final AtlasRegion cloud2;
        public final AtlasRegion cloud3;
        public final AtlasRegion mountainLeft;
        public final AtlasRegion mountainRight;
        public final AtlasRegion waterOverlay;

        public AssetLevelDecoration(TextureAtlas atlas){
            cloud1 = atlas.findRegion("cloud1");
            cloud2 = atlas.findRegion("cloud2");
            cloud3 = atlas.findRegion("cloud3");
            mountainLeft = atlas.findRegion("mountain_left");
            mountainRight = atlas.findRegion("mountain_right");
            waterOverlay = atlas.findRegion("water-overlay");
        }
    }
}
