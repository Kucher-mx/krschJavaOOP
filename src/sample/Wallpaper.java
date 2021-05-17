package sample;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Wallpaper {
    Image image;
    public  static ImageView imageView;
    private static Group subi;
    public static Rectangle border;

    static Group wallpaperGroup;
    Wallpaper() throws FileNotFoundException {
        image = new Image(new FileInputStream("src/source/wallpaper.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(3700);
        imageView.setFitHeight(3000);
        this.imageView = imageView;

        border = new Rectangle(0,0,this.imageView.getFitWidth(), this.imageView.getFitHeight());
        border.setFill(Color.TRANSPARENT);
        border.setStroke(Color.BLUEVIOLET);

        Group sub = new Group( new Group(this.imageView,border));
        this.wallpaperGroup = sub;
    }

    public static Group getWallGroup() {
        return wallpaperGroup;
    }
}
