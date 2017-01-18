import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.filters.Caption;
import net.coobird.thumbnailator.geometry.Position;
import net.coobird.thumbnailator.geometry.Positions;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

/**
 * @author EuiJin.Ham
 * @version 0.1
 * Library for marking watermark image or text on source image
 * */

public class ImageMarker {
    /**
     * CONST LIST FOR POSITION
     * */
    public static final int CENTER = 0;
    public static final int BOTTOM_CENTER = 1;
    public static final int BOTTOM_LEFT = 2;
    public static final int BOTTOM_RIGHT = 3;
    public static final int CENTER_LEFT = 4;
    public static final int CENTER_RIGHT = 5;
    public static final int TOP_CENTER = 6;
    public static final int TOP_LEFT = 7;
    public static final int TOP_RIGHT = 8;

    private static int positionCode = 0;

    private static int subTopMargin = 0;

    private static float alpha = 0.5f;
    private static float captionAlpha = 1.0f;

    private static String outputFormat = "png";
    private static String captionText = "";
    private static String subCaptionText = "";

    private static BufferedImage originalImage = null;
    private static BufferedImage waterImage = null;
    private static BufferedImage toApply = null;

    private int intrinsicWidth = 0;
    private int intrinsicHeight = 0;

    private static Font font = new Font("Gulim", Font.PLAIN, 50);
    private static Font subFont = new Font("Gulim", Font.PLAIN, 20);
    private static Color captionColor = Color.black;
    private static Position position = Positions.CENTER;
    private static int insetPixels = 0;

    /**
     * Singleton Instance
     * */
    private static ImageMarker instance;

    /**
     * Internal Default Constructor
     * */
    protected ImageMarker(){}

    private static void setPos(){
        switch (positionCode){
            case CENTER:
                position = Positions.CENTER;
                break;
            case BOTTOM_CENTER:
                position = Positions.BOTTOM_CENTER;
                break;
            case BOTTOM_LEFT:
                position = Positions.BOTTOM_LEFT;
                break;
            case BOTTOM_RIGHT:
                position = Positions.BOTTOM_RIGHT;
                break;
            case CENTER_LEFT:
                position = Positions.CENTER_LEFT;
                break;
            case CENTER_RIGHT:
                position = Positions.CENTER_RIGHT;
                break;
            case TOP_CENTER:
                position = Positions.TOP_CENTER;
                break;
            case TOP_LEFT:
                position = Positions.TOP_LEFT;
                break;
            case TOP_RIGHT:
                position = Positions.TOP_RIGHT;
                break;
            default:
                position = Positions.CENTER;
                break;
        }
    }

    /**
     * Instanciating function
     * */
    public static ImageMarker getInstance(){
        if(instance == null) instance = new ImageMarker();
        setPos();
        return instance;
    }

    /**
     * Function for setting position of Watermark image
     * */
    public ImageMarker position(int constCode){
        this.positionCode = constCode;
        this.setPos();
        return instance;
    }

    /**
     * Function for setting a top margin of sub text
     * @param m : Margin value as the pixel unit
     * */
    public ImageMarker subTopMargin(int m){
        this.subTopMargin = m;
        this.setPos();
        return instance;
    }

    /**
     * Function for setting color of caption
     * @param c : Color type value
     * */
    public ImageMarker captionColor(Color c){
        this.captionColor = c;
        return instance;
    }

    /**
     * Function for setting caption String
     * @param caption : String type contents for caption
     * */
    public ImageMarker caption(String caption){
        this.captionText = caption;
        return instance;
    }

    /**
     * Function for setting sub caption String
     * @param caption : String type contents for caption
     * */
    public ImageMarker subCaption(String caption){
        this.subCaptionText = caption;
        return instance;
    }

    /**
     * Function for setting target Image
     * @param image : Target image as BufferedImage
     * */
    public ImageMarker of(BufferedImage image){
        this.originalImage = image;
        return instance;
    }

    private BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    /**
     * Function for setting watermark image
     * @param image : Image to be displayed as watermark
     * */
    public ImageMarker waterMark(BufferedImage image){
        this.waterImage = image;
        this.toApply = deepCopy(image);
        this.intrinsicHeight = image.getHeight();
        this.intrinsicWidth = image.getWidth();
        return instance;
    }

    /**
     * Function for setting scale of watermark to intrinsic size
     * */
    public ImageMarker setAsIntrinsicWaterMark(){
        this.toApply = deepCopy(waterImage);
        return instance;
    }

    /**
     * Function for setting scale of watermark with arbitrary sizes
     * @param width : width size
     * @param height : height size
     * */
    public ImageMarker setWaterMarkSize(int width, int height) throws IOException{
        this.toApply = deepCopy(waterImage);
        this.toApply = Thumbnails.of(toApply).size(width, height).asBufferedImage();
        return instance;
    }

    /**
     * Function for setting font of caption
     * @param f : font type
     * */
    public ImageMarker font(Font f){
        this.font = f;
        return instance;
    }

    /**
     * Function for setting sub font of caption
     * @param f : font type
     * */
    public ImageMarker subFont(Font f){
        this.subFont = f;
        return instance;
    }

    /**
     * Function for setting alpha of watermark image
     * @param f : float type 0.0f ~ 1.0f alpha value
     * */
    public ImageMarker alpha(float f){
        this.alpha = f;
        return instance;
    }

    /**
     * Function for setting alpha of caption string
     * @param f : float type 0.0f ~ 1.0f alpha value
     * */
    public ImageMarker alphaOfCaption(float f){
        this.captionAlpha = f;
        return instance;
    }

    /**
     * Function for setting output format
     * @param o : output format
     * */
    public ImageMarker outputFormat(String o){
        this.outputFormat = o;
        return instance;
    }

    /**
     * Function for getting bufferedImage as a result
     * */
    public BufferedImage toBufferedImage(){
        BufferedImage ret = null;
        try {
            BufferedImage toMark = Thumbnails
                    .of(originalImage)
                    .watermark(toApply, alpha)
                    .outputQuality(1.0f)
                    .size(originalImage.getWidth(), originalImage.getHeight())
                    .asBufferedImage();

            Caption filter = new Caption(captionText, font, captionColor, captionAlpha, position, insetPixels);
            Caption subFilter = new Caption(" \n" + subCaptionText, subFont, captionColor, captionAlpha, Positions.BOTTOM_CENTER, insetPixels);
            ret = filter.apply(toMark);
            BufferedImage tempCaption = subFilter.apply(new BufferedImage(ret.getWidth(), font.getSize() * 2 + subTopMargin, BufferedImage.TYPE_INT_ARGB));
            ret = Thumbnails
                    .of(ret)
                    .watermark(tempCaption)
                    .size(originalImage.getWidth(), originalImage.getHeight())
                    .outputQuality(1.0f)
                    .outputFormat(outputFormat)
                    .asBufferedImage();

        }catch(IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

}
