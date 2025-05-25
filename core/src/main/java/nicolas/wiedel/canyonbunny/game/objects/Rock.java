package nicolas.wiedel.canyonbunny.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import nicolas.wiedel.canyonbunny.game.Assets;

/**
 * Klasse für die Felsen, die als Untergrud dienen
 */
public class Rock extends AbstractGameObject {

    public TextureRegion regEdge;
    public TextureRegion regMiddle;

    private int length;

    public Rock(){
        init();
    }

    private void init(){
        dimension.set(1f, 1.5f);

        regEdge = Assets.INSTANCE.rock.edge;
        regMiddle = Assets.INSTANCE.rock.middle;

        // Grundlegend ist die länge des Rocks 1
        setLength(1);
    }

    public void setLength(int lenght){
        this.length = lenght;
    }

    public void increaseLength(int amount){
        setLength(length + amount);
    }

    @Override
    public void render(SpriteBatch batch) {
        TextureRegion reg = null;

        float relX = 0;
        float relY = 0;

        // linke Seite zeichnen
        reg = regEdge;
        relX -= dimension.x / 4;
        batch.draw(reg.getTexture(),
            position.x + relX, position.y + relY,
            origin.x, origin.y,
            dimension.x / 4, dimension.y,
            scale.x, scale.y,
            rotation,
            reg.getRegionX(), reg.getRegionY(),
            reg.getRegionWidth(), reg.getRegionHeight(),
            false, false);

        // Mitte zeichnen
        reg = regMiddle;
        relX = 0;
        for (int i = 0; i < length; i++){
            batch.draw(reg.getTexture(),
                position.x + relX, position.y + relY,
                origin.x, origin.y,
                dimension.x , dimension.y,
                scale.x, scale.y,
                rotation,
                reg.getRegionX(), reg.getRegionY(),
                reg.getRegionWidth(), reg.getRegionHeight(),
                false, false);
            relX += dimension.x;
        }

        // rechte Seite zeichnen
        reg = regEdge;
        batch.draw(reg.getTexture(),
            position.x + relX, position.y + relY,
            origin.x + dimension.x / 8, origin.y,
            dimension.x / 4, dimension.y,
            scale.x, scale.y,
            rotation,
            reg.getRegionX(), reg.getRegionY(),
            reg.getRegionWidth(), reg.getRegionHeight(),
            true, false);
    }
}
