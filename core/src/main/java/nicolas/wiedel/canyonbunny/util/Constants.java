package nicolas.wiedel.canyonbunny.util;

import com.badlogic.gdx.graphics.Color;

/***
 * Sammlung aller Konstanten die gesammelt in dieser Klasse gehalten werden
 */
public class Constants {

    // Die Hintergrundfarbe, mit der grundlegend gezeichnet wird.
    public static final Color CORNFLOWERBLUE = new Color(0.388f, 0.584f, 0.933f, 0f);

    // Die sichtbare GameWorld ist 5 Meter breit.
    public static final float VIEWPORT_WIDTH= 5.0f;

    // Die sichtbare GameWorld is 5 Meter hoch.
    public static final float VIEWPORT_HEIGHT = 5.0f;

    // Der Speicherort des TextureAtlas für alle Grafiken
    public static final String TEXTURE_ATLAS_OBJECTS = "images/canyonbunny.pack";

    // Der Speicherort des Bildes für Level 01
    public static final String LEVEL_01 = "levels/level-01.png";
}
