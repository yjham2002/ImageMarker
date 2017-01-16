# ImageMarker(이미지마커)

- A library for marking watermark with image or text on source image and returning as BufferedImage.

- 소스 이미지에 대해 이미지 기반의 워터마크 혹은 텍스트를 삽입할 수 있는 라이브러리.

#### Usage

- Add ImageMarker.jar to your project. (ImageMarker.jar 파일을 다운로드하여 프로젝트에 삽입합니다.)

- Set option simply with inner method. (아래 코드 스니펫과 같은 방법으로 옵션을 설정합니다.)

- Get final result with simply using toBufferedImage() method. (toBufferedImage() 메소드로 결과 인스턴스를 받습니다.)

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