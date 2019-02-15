package Common.Image;

import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class ImageCompare {

    static {
//        使用opencv必须要加载该内容否则报错
//        并且要调用的主函数要修改VM Option
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    /**
     * @param fileName1
     * @param fileName2
     * @param fileName3
     */
    public boolean compare(String fileName1, String fileName2, String fileName3) {


//        Imgcodecs.IMREAD_UNCHANGED  -1
        Mat img1 = Imgcodecs.imread(fileName1);
        Mat img2 = Imgcodecs.imread(fileName2);

        Mat img = new Mat();
        Mat erodeImg = new Mat();
        Mat dilateImg = new Mat();
        Mat threshImg = new Mat();
        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();

        Mat hierarchy = new Mat();
        //像素做差
        Core.absdiff(img1, img2, img);

        Mat kernel = Imgproc.getStructuringElement(1, new Size(4, 6));
        Mat kernel1 = Imgproc.getStructuringElement(1, new Size(2, 3));
        //腐蚀
        Imgproc.erode(img, erodeImg, kernel, new Point(-1, -1), 1);
        //膨胀
        Imgproc.dilate(erodeImg, dilateImg, kernel1);
        HighGui gui = new HighGui();

        //检测边缘
        Imgproc.threshold(dilateImg, threshImg, 20, 255, Imgproc.THRESH_BINARY);
        //转化成灰度
        Imgproc.cvtColor(threshImg, threshImg, Imgproc.COLOR_RGB2GRAY);
        //找到轮廓(3：CV_RETR_TREE，2：CV_CHAIN_APPROX_SIMPLE)
        Imgproc.findContours(threshImg, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE, new Point(0, 0));

        List<Rect> boundRect = new ArrayList<Rect>(contours.size());
        for (int i = 0; i < contours.size(); i++) {
//        	Mat conMat = (Mat)contours.get(i);
//        	Imgproc.approxPolyDP((MatOfPoint2f)conMat,contours_poly.get(i),3,true);
            //根据轮廓生成外包络矩形
            Rect rect = Imgproc.boundingRect(contours.get(i));
            boundRect.add(rect);
        }

        for (int i = 0; i < contours.size(); i++) {
            Scalar color = new Scalar(0, 0, 255);
            //绘制轮廓
//        	Imgproc.drawContours(img1, contours, i, color, 1, Core.LINE_8, hierarchy, 0, new Point());
            //绘制矩形
            Imgproc.rectangle(img1, boundRect.get(i).tl(), boundRect.get(i).br(), color, 2, Imgproc.LINE_8, 0);
        }
//        gui.imshow("test", img1);
//        gui.waitKey(1);
        return Imgcodecs.imwrite(fileName3, img1);
    }

}

