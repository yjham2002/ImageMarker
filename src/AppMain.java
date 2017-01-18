import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class AppMain {
    public static ArrayList<BufferedImage> list = new ArrayList<>();
    public static ArrayList<Thread> tList = new ArrayList<>();
    public static int i = 0;

    public static void main(String[] args) throws IOException{
        BufferedImage originalImage = ImageIO.read(new File("C:\\Users\\a\\IdeaProjects\\ImageMarker\\or.png"));
        BufferedImage waterImage = ImageIO.read(new File("C:\\Users\\a\\IdeaProjects\\ImageMarker\\water.png"));

        /**
         * Type : Singleton Pattern instance
         * Set Attributes with Decorator Pattern Style
         * */
        ImageMarker imageMarker = ImageMarker.getInstance();
        imageMarker
                .of(originalImage)
                .alpha(0.5f)
                .font(new Font("malgun gothic", Font.BOLD, 100))
                .caption("Caption Text")
                .subFont(new Font("malgun gothic", Font.ITALIC, 30))
                .subCaption("SubScript Text")
                .waterMark(waterImage)
                .setAsIntrinsicWaterMark()
                .outputFormat("png")
                .alphaOfCaption(0.5f)
                .subTopMargin(10)
                .position(ImageMarker.CENTER)
                .captionColor(Color.WHITE);

        Runnable r = () -> {
            System.out.println("Thread Started");
            for(int k = 0; k < 1; k++) list.add(imageMarker.toBufferedImage());

        };

        for(i = 0; i < 10; i++){
            tList.add(new Thread(r));
        }

        for(Thread t : tList){
            t.start();
        }

        for(Thread t : tList){
            try {
                t.join();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        int count = 0;
        for(BufferedImage b : list){
            ImageIO.write(b, "png", new File("testImage/result" + count++ + ".png"));
        }


    }
}
