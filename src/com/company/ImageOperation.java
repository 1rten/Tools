package Common.Image;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import static org.opencv.imgproc.Imgproc.ADAPTIVE_THRESH_MEAN_C;
import static org.opencv.imgproc.Imgproc.adaptiveThreshold;


public class ImageOperation {

    static {
//        使用opencv必须要加载该内容否则报错
//        并且要调用的主函数要修改VM Option
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        String file = "./DataResource/kaptcha-1.jpeg";
        HighGui gui = new HighGui();
        Mat srcImg = Imgcodecs.imread(file);
        Mat grayImg = new Mat();
        Mat threshImg = new Mat();
        Mat binaryImg = new Mat();
        Mat erodeImg = new Mat();
        Mat dilateImg = new Mat();
        Mat destImg = new Mat();

        //        转化成灰度
        Imgproc.cvtColor(srcImg, grayImg, Imgproc.COLOR_RGB2GRAY);

//        gui.imshow("gray", grayImg);
//        二值化
//        Imgproc.threshold(grayImg, threshImg, 100, 255, Imgproc.THRESH_OTSU);
        adaptiveThreshold(grayImg, threshImg, 255, ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, 15, -2);

//        gui.imshow("binary", threshImg);

        int verticalSize = srcImg.rows() / 30;

//        size 越小，腐蚀的单位越小，图片越接近原图
//        Mat structImg = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(1, verticalSize));
        Mat structImg = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(1, 1), new Point(-1, -1));


        /**
         * 图像腐蚀
         * 腐蚀说明： 图像的一部分区域与指定的核进行卷积，
         * 求核的最`小`值并赋值给指定区域。
         * 腐蚀可以理解为图像中`高亮区域`的'领域缩小'。
         * 意思是高亮部分会被不是高亮部分的像素侵蚀掉，使高亮部分越来越少。
         */
        Imgproc.erode(threshImg, erodeImg, structImg, new Point(-1, -1), 1);

        /**
         * 膨胀
         * 膨胀说明： 图像的一部分区域与指定的核进行卷积，
         * 求核的最`大`值并赋值给指定区域。
         * 膨胀可以理解为图像中`高亮区域`的'领域扩大'。
         * 意思是高亮部分会侵蚀不是高亮的部分，使高亮部分越来越多。
         */
        Imgproc.dilate(erodeImg, dilateImg, structImg, new Point(-1, -1), 1);


        gui.imshow("mat", dilateImg);
        gui.waitKey(1);
        Imgcodecs.imwrite("ForTest.png", dilateImg);
    }


}
