# ImageMarker

- A library for marking watermark with image or text on source image and returning as BufferedImage.

#### Usage

- Add ImageMarker.jar to your project.

- Set option simply with inner method.

- Get final result with simply using toBufferedImage() method.

```java
public static void main(String[] args) throws IOException{
        BufferedImage originalImage = ImageIO.read(new File("C:\\Users\\a\\IdeaProjects\\ImageMarker\\or.png"));
        BufferedImage waterImage = ImageIO.read(new File("C:\\Users\\a\\IdeaProjects\\ImageMarker\\water.png"));

        /**
         * Type : Singleton Pattern instance
         * Set Attributes with Decorator Pattern Style
         * */
        ImageMarker imageMarker = ImageMarker.getInstance();
        BufferedImage result = imageMarker
                .of(originalImage)
                .alpha(0.5f)
                .font(new Font("malgun gothic", Font.BOLD, 100))
                .waterMark(waterImage)
                .setAsIntrinsicWaterMark()
                .caption("Caption Text")
                .alphaOfCaption(0.5f)
                .position(ImageMarker.CENTER)
                .captionColor(Color.WHITE)
                .toBufferedImage();

        ImageIO.write(result, "png", new File("result.png"));
    }
```

#### License

- Thumbnailator [on Github](https://github.com/coobird/thumbnailator)