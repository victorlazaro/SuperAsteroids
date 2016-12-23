package edu.byu.cs.superasteroids.modelClasses;

/**
 * Created by Victor on 2/3/2016.
 * This class holds images (path, width and height)
 */
public class Image {

    private String imagePath;
    private int imageWidth;
    private int imageHeight;

    public Image(String imagePath, int imageHeight, int imageWidth)
    {
        this.imageHeight = imageHeight;
        this.imagePath = imagePath;
        this.imageWidth = imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }
    public int getImageWidth() {
        return imageWidth;
    }
    public String getImagePath() {
        return imagePath;
    }




}
