package spaceinvaders;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class SpriteManager {
    private Map<String, Image> spriteCache = new HashMap<>();

    public Image getSprite(String name) {
        // If the image is already in the cache, return it
        if (spriteCache.containsKey(name)) {
            return spriteCache.get(name);
        }

        // Load image from the classpath
        Image img = loadImage(name);
        if (img != null) {
            // Scale the image to 50%
            img = img.getScaledInstance(
                    img.getWidth(null) / 2,
                    img.getHeight(null) / 2,
                    Image.SCALE_SMOOTH
            );
            spriteCache.put(name, img);
        }
        return img;
    }

    private Image loadImage(String name) {
        // Load resource as stream from inside the JAR
        try {
            // Ensure images are placed in src/images/, which becomes /images in the JAR
            // So resource path is "/images/" + name
            var inputStream = getClass().getResourceAsStream("/images/" + name);
            if (inputStream == null) {
                System.err.println("Image resource not found: " + "/images/" + name);
                return null;
            }

            Image img = ImageIO.read(inputStream);
            if (img == null) {
                System.err.println("Failed to load image: " + name);
                return null;
            }
            return img;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
