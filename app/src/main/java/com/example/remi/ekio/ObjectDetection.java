package com.example.remi.ekio;

import org.opencv.calib3d.Calib3d;
import org.opencv.core.*;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.imgcodecs.Imgcodecs;
//import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
//import org.opencv.;

/**
 * Created by remi on 09/06/16.
 */
public class ObjectDetection {

   // private static final float inlier_treshold = 2.5f;
   // private static final float nn_match_ratio = 2.5f;

    public ObjectDetection(){}

    public int match(String img1_filename, String img2_filename) {

        Mat img1 = Imgcodecs.imread(img1_filename, Imgcodecs.IMREAD_GRAYSCALE);
        Mat img2 = Imgcodecs.imread(img2_filename, Imgcodecs.IMREAD_GRAYSCALE);


        // potetentiellement modifier // que veut dire le 15 ?
        FeatureDetector fast = FeatureDetector.create(FeatureDetector.AKAZE);

        // keypoints of image 1
        MatOfKeyPoint keyPoints1 = new MatOfKeyPoint();
        fast.detect(img1, keyPoints1);

        // pour image2
        MatOfKeyPoint keyPoints2 = new MatOfKeyPoint();
        fast.detect(img2, keyPoints2);

        // potentielement à modifier
        DescriptorExtractor descriptorExtractor = DescriptorExtractor.create(DescriptorExtractor.AKAZE);
        Mat descriptors1 = new Mat();
        descriptorExtractor.compute(img1, keyPoints1, descriptors1);

        Mat descriptors2 = new Mat();
        descriptorExtractor.compute(img2, keyPoints2, descriptors2);

        //matcher que veut dire brutforce
        DescriptorMatcher descriptorMatcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE);
        MatOfDMatch matchs = new MatOfDMatch();
        descriptorMatcher.match(descriptors1, descriptors2, matchs);

        matchs.total();



        return 1;
    }


}
