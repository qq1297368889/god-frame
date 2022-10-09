package gzb.tools.img;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;


public class PicUtils {


    /**
     * 强制压缩/放大图片到固定的大小
     *
     * @param w int 新宽度
     * @param h int 新高度
     * @throws IOException
     */
    public static final byte[] resize(File file, int w, int h) throws IOException {
        BufferedImage _image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Image img = ImageIO.read(file);
        _image.getGraphics().drawImage(img, 0, 0, w, h, null);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(_image, "jpg", os);
        return os.toByteArray();
    }

    public static final byte[] resize(Image img, int w, int h) throws IOException {
        BufferedImage _image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        _image.getGraphics().drawImage(img, 0, 0, w, h, null);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(_image, "jpg", os);
        return os.toByteArray();
    }

    /**
     * 按照固定的比例缩放图片
     *
     * @param t double 比例  0.1 0.2 ..... 1  1.1  1.2
     * @throws IOException
     */
    public static final byte[] resize(File file, double t) throws IOException {
        Image img = ImageIO.read(file);
        int width = img.getWidth(null); // 得到源图宽
        int height = img.getHeight(null); // 得到源图高
        int w = (int) (width * t);
        int h = (int) (height * t);
        return resize(img, w, h);
    }


    /**
     * 以宽度为基准，等比例放缩图片
     *
     * @param w int 新宽度
     * @throws IOException
     */
    public static final byte[] resizeByWidth(File file, int w) throws IOException {
        Image img = ImageIO.read(file);
        int width = img.getWidth(null); // 得到源图宽
        int height = img.getHeight(null); // 得到源图高
        int h = (int) (height * w / width);
        return resize(img, w, h);
    }

    /**
     * 以高度为基准，等比例缩放图片
     *
     * @param h int 新高度
     * @throws IOException
     */
    public static final byte[] resizeByHeight(File file, int h) throws IOException {
        Image img = ImageIO.read(file);
        int width = img.getWidth(null); // 得到源图宽
        int height = img.getHeight(null); // 得到源图高
        int w = (int) (width * h / height);
        return resize(img, w, h);
    }


}