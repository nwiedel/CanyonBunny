package nicolas.wiedel.canyonbunny.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import nicolas.wiedel.canyonbunny.game.Assets;

public class Mountains extends AbstractGameObject {

    private TextureRegion regMountainLeft;
    private TextureRegion regMountainRight;

    private int length;

    public Mountains(int length){
        this.length = length;
        init();
    }

    private void init(){
        dimension.set(10, 2);

        regMountainLeft = Assets.INSTANCE.levelDecoration.mountainLeft;;
        regMountainRight = Assets.INSTANCE.levelDecoration.mountainRight;

        // wechsele die Berge und verlängere sie
        origin.x = -dimension.x * 2;
        length += dimension.x * 2;
    }

    private void drawMountain(SpriteBatch batch, float offsetX,
                              float offsetY, float tintColor){
        TextureRegion reg = null;
        batch.setColor(tintColor, tintColor, tintColor, 1);
        float xRel = dimension.x *offsetX;
        float yRel = dimension.y * offsetY;

        // Berge bedecken den ganzen Level
        int mountainLength = 0;
        mountainLength += MathUtils.ceil(2 * dimension.x);
        mountainLength += MathUtils.ceil(0.5f + offsetX);
        for (int i = 0; i <mountainLength; i++){

            // rechter Berg
            reg = regMountainLeft;
            batch.draw(reg.getTexture(),
                origin.x + xRel, position.y + origin.y + yRel,
                origin.x, origin.y,
                dimension.x, dimension.y,
                scale.x, scale.y,
                rotation,
                reg.getRegionX(), reg.getRegionY(),
                reg.getRegionWidth(), reg.getRegionHeight(),
                false, false);
            xRel += dimension.x;

            // linker Berg
            reg = regMountainRight;
            batch.draw(reg.getTexture(),
                origin.x + xRel, position.y + origin.y + yRel,
                origin.x, origin.y,
                dimension.x, dimension.y,
                scale.x, scale.y, rotation,
                reg.getRegionX(), reg.getRegionY(),
                reg.getRegionWidth(), reg.getRegionHeight(),
                false, false);
            xRel += dimension.x;
        }

        // Farbe wieder auf weiß setzen
        batch.setColor(1, 1, 1, 1);
    }

    @Override
    public void render(SpriteBatch batch) {
        // Berge dunkelgrau
        drawMountain(batch, 0.5f, 0.5f, 0.5f);
        // Berge grau
        drawMountain(batch, 0.25f, 0.25f, 0.7f);
        // Berge hellgrau
        drawMountain(batch, 0.0f, 0.0f, 0.9f);
    }
}
