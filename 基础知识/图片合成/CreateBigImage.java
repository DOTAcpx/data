package com.demo;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

/**
 * 图片拼接
 * 把多张宽度一样的图片拼接成一张大图片
 * @author Administrator
 *
 */
@SuppressWarnings("restriction")
public class CreateBigImage {
    
    public static void main(String[] args) throws Exception {
//        System.out.println("123");
//        
//        //设置图片宽度相同
//        changeImage("D:/imgs/", "1.jpg", "1.jpg", 300,200);
//        changeImage("D:/imgs/", "2.jpg", "2.jpg", 300,200);
//        changeImage("D:/imgs/", "3.jpg", "3.jpg", 300,200);
//        //获取宽度相同的图片
//        String img1 = "D:/imgs/1.jpg";
//        String img2 = "D:/imgs/2.jpg";
//        String img3 = "D:/imgs/3.jpg";
//        String[] imgs = new String[]{img1,img2,img3};
//        //图片拼接
//        merge(imgs,"jpg","D:/imgs/big.jpg");
        
//        String folderPath = "F:\\画画\\临摹\\test";
//        changeFolderImages(folderPath,600,400);
//        
//        String imgs = mergeFolderImgs(folderPath,"jpg","F:\\画画\\临摹\\test\\123.jpg");
//        System.out.println(imgs);
    	
    	File file = new File("F:\\画画\\临摹\\test\\16111701.jpg");
        Image img = ImageIO.read(file);
        // 构造Image对象
//        int wideth = img.getWidth(null); // 得到源图宽
//        int height = img.getHeight(null); // 得到源图长
        //修改图片宽高
        BufferedImage tag = new BufferedImage(150, 150,
               BufferedImage.TYPE_INT_RGB);
        //前两个为0,需要跟宽高一致
        tag.getGraphics()
               .drawImage(img, 0, 0, 150, 150, null); 
    	InputStream inputStream = changeMerchantSeatQrcodeImage(tag, "F:\\画画\\临摹\\test\\123.jpg");
    	getFile(inputStream, "F:\\画画\\临摹\\test\\1233.jpg");
    	
    }
    
    public static void getFile(InputStream is,String fileName) throws IOException{
        BufferedInputStream in=null;
        BufferedOutputStream out=null;
        in=new BufferedInputStream(is);
        out=new BufferedOutputStream(new FileOutputStream(fileName));
        int len=-1;
        byte[] b=new byte[1024];
        while((len=in.read(b))!=-1){
            out.write(b,0,len);
        }
        in.close();
        out.close();
    }
    
    /**
     * 合并图片
     * @param folderPath 图片所在文件夹的绝对路径
     * @param imgType 合并后的图片类型（jpg、png...）
     * @param outAbsolutePath（输出合并后文件的绝对路径）
     * @return
     */
    public static String mergeFolderImgs(String folderPath,String imgType,String outAbsolutePath){
        File folder = new File(folderPath);
        File[] imgList = folder.listFiles();
        String[] imgPaths = new String[imgList.length];
        
        for (int i = 0; i < imgList.length; i++) {
            //System.out.println("文件个数："+imgList[i].length());
            imgPaths[i] = imgList[i].getAbsolutePath();
            System.out.println("第"+i+"张图片途径："+imgPaths[i]);
        }
        merge(imgPaths,imgType,outAbsolutePath);
        
        System.out.println("---------------------");
        File newImg = new File(outAbsolutePath);
        System.out.println(newImg.getName());
        return newImg.getName();
    }
    
    
    /**
     * 设置图片大小（单张图片）
     * @param path 路径
     * @param oldimg 旧图片名称
     * @param newimg 新图片名称
     * @param newWidth 新图片宽度
     * @param newHeight 新图片高度
     */
    public static void changeImage(String path, String oldimg, String newimg, int newWidth,int newHeight) {
           try {
               File file = new File(path + oldimg);
               Image img = ImageIO.read(file);
               // 构造Image对象
//               int wideth = img.getWidth(null); // 得到源图宽
//               int height = img.getHeight(null); // 得到源图长
               BufferedImage tag = new BufferedImage(newWidth, newHeight,
                      BufferedImage.TYPE_INT_RGB);
               tag.getGraphics()
                      .drawImage(img, 0, 0, newWidth, newHeight, null); // 绘制后的图
               FileOutputStream out = new FileOutputStream(path + newimg);
               JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
               encoder.encode(tag); // 近JPEG编码
               out.close();
           } catch (IOException e) {
               System.out.println("处理文件出现异常");
               e.printStackTrace();
           }
        }
    
    
    /**
     * 设置图片大小（批量处理整个文件夹中的图片）
     * @param folderPath 文件夹路径
     * @param newWidth 新图片宽度
     * @param newHeight 新图片高度
     */
    public static void changeFolderImages(String folderPath, int newWidth,int newHeight) {
           try {
               File folder = new File(folderPath);//得到文件夹
               File[] imgList = folder.listFiles();//得到文件夹中的所有图片
               Image image = null;//定义一张图片
               
               BufferedImage bfImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
               FileOutputStream outputStream = null;
               JPEGImageEncoder encoder = null;
               for (int i = 0; i < imgList.length; i++) {
                   image = ImageIO.read(imgList[i]);//将得到的图片放入新定义的图片中
                   bfImg.getGraphics().drawImage(image, 0, 0, newWidth, newHeight, null);//绘制后的图
                   outputStream = new FileOutputStream(imgList[i]);
                   encoder = JPEGCodec.createJPEGEncoder(outputStream);
                   encoder.encode(bfImg);
               }
               outputStream.close();
           } catch (IOException e) {
               System.out.println("处理文件出现异常");
               e.printStackTrace();
           }
        }
    
    
    /** 
     * Java拼接多张图片 
     *  
     * @param pics:图片源文件 （必须要宽度一样），如：
     *                         String img1 = "D:/imgs/3.jpg";
     *                        String img2 = "D:/imgs/3.jpg";
     *                        String img3 = "D:/imgs/big.jpg";
     *                        String[] pics = new String[]{img1,img2,img3};
     * @param type ：图片输出类型（jpg，png，jpeg...）
     * @param dst_pic ：图片输出绝对路径，如 String dst_pic="D:/imgs/big2.jpg";
     * @return 
     */  
    public static boolean merge(String[] pics, String type, String dst_pic) {  
  
        int len = pics.length;  //图片文件个数
        if (len < 1) {  
            System.out.println("pics len < 1");  
            return false;  
        }  
        File[] src = new File[len];  
        BufferedImage[] images = new BufferedImage[len];  
        int[][] ImageArrays = new int[len][];  
        for (int i = 0; i < len; i++) {  
            try {  
                src[i] = new File(pics[i]);  
                images[i] = ImageIO.read(src[i]);  
            } catch (Exception e) {  
                e.printStackTrace();  
                return false;  
            }  
            int width = images[i].getWidth();  
            int height = images[i].getHeight();  
            ImageArrays[i] = new int[width * height];// 从图片中读取RGB   
            ImageArrays[i] = images[i].getRGB(0, 0, width, height,  
                    ImageArrays[i], 0, width);  
        }  
  
        int dst_height = 0;  
        int dst_width = images[0].getWidth();  
        for (int i = 0; i < images.length; i++) {  
            dst_width = dst_width > images[i].getWidth() ? dst_width  
                    : images[i].getWidth();  
  
            dst_height += images[i].getHeight();  
        }  
        System.out.println(dst_width);  
        System.out.println(dst_height);  
        if (dst_height < 1) {  
            System.out.println("dst_height < 1");  
            return false;  
        }  
  
        // 生成新图片   
        try {  
            // dst_width = images[0].getWidth();   
            BufferedImage ImageNew = new BufferedImage(dst_width, dst_height,  
                    BufferedImage.TYPE_INT_RGB);  
            int height_i = 0;  
            for (int i = 0; i < images.length; i++) {  
                ImageNew.setRGB(0, height_i, dst_width, images[i].getHeight(),  
                        ImageArrays[i], 0, dst_width);  
                height_i += images[i].getHeight();  
            }  
  
            File outFile = new File(dst_pic);  
            ImageIO.write(ImageNew, type, outFile);// 写图片   
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }  
        return true;  
    }  
    public static InputStream changeMerchantSeatQrcodeImage(BufferedImage zxingImage, String backgroundPath) {
        InputStream imagein = null;
        ImageOutputStream imOut = null;
        try {
            imagein = new FileInputStream(backgroundPath);
            BufferedImage image = ImageIO.read(imagein);
            BufferedImage image2 = zxingImage;
            Graphics g = image.getGraphics();
            // 生成的二维码设置的较小，这里等比放大了二维码。也可在zxing中设置二维码生成的大小
            BufferedImage squreImage = resizeImage(image2, 2);
            //右移动数,下移动数
            g.drawImage(squreImage, 200, 300,
                    squreImage.getWidth(), squreImage.getHeight(), null);
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            imOut = ImageIO.createImageOutputStream(bs);
            ImageIO.write(image, "jpg", imOut);
            InputStream is = new ByteArrayInputStream(bs.toByteArray());
            return is;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                imagein.close();
                imOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    public static BufferedImage resizeImage(BufferedImage  originalImage, double times){
        int width = (int)(originalImage.getWidth()*times);
        int height = (int)(originalImage.getHeight()*times);

        int tType = originalImage.getType();
        if(0 == tType){
            tType = 5;
        }
        BufferedImage newImage = new BufferedImage(width,height, tType);
        Graphics g = newImage.getGraphics();
        g.drawImage(originalImage, 0,0,width,height,null);
        g.dispose();

        return newImage;
    }
}
