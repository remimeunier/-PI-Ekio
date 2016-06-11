package com.example.remi.ekio;

import org.opencv.calib3d.Calib3d;
import org.opencv.core.*;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.imgcodecs.Imgcodecs;
//import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;
//import org.opencv.;

/**
 * Created by remi on 09/06/16.
 */
public class ObjectDetection {

   // private static final float inlier_treshold = 2.5f;
   // private static final float nn_match_ratio = 2.5f;

    public ObjectDetection(){

    }

    public int match(String img1_filename, String img2_filename) {

        Mat img1 = Imgcodecs.imread(img1_filename, Imgcodecs.IMREAD_COLOR);
        Mat img2 = Imgcodecs.imread(img2_filename, Imgcodecs.IMREAD_COLOR);


        // potetentiellement modifier // que veut dire le 15 ?
        FeatureDetector fast = FeatureDetector.create(FeatureDetector.ORB);

        // keypoints of image 1
        MatOfKeyPoint keyPoints1 = new MatOfKeyPoint();
        fast.detect(img1, keyPoints1);

        // pour image2
        MatOfKeyPoint keyPoints2 = new MatOfKeyPoint();
        fast.detect(img2, keyPoints2);

        // potentielement à modifier
        DescriptorExtractor descriptorExtractor = DescriptorExtractor.create(DescriptorExtractor.ORB); // conseillé par un mec sur yt
        Mat descriptors1 = new Mat();
        descriptorExtractor.compute(img1, keyPoints1, descriptors1);

        Mat descriptors2 = new Mat();
        descriptorExtractor.compute(img2, keyPoints2, descriptors2);

        //matcher que veut dire brutforce
        DescriptorMatcher descriptorMatcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE); // regarder flann matcher
        ArrayList<MatOfDMatch> matchs = new ArrayList<>();
        descriptorMatcher.knnMatch(descriptors1, descriptors2, matchs, 2);

        float ratio = 0.9f; // As in Lowe's paper (can be tuned)
        MatOfKeyPoint goodMatches = new MatOfKeyPoint();
        for (int matchIdx = 0; matchIdx < matchs.size(); ++matchIdx)
        {
            if (matchs.get(matchIdx).get(0,0)[3] < ratio * matchs.get(matchIdx).get(1,0)[3])
            {
                goodMatches.push_back(matchs.get(matchIdx).row(0));
            }
        }
        /*MatOfKeyPoint matched1 = new MatOfKeyPoint();
        MatOfKeyPoint matched2 = new MatOfKeyPoint();
        MatOfKeyPoint inliers1 = new MatOfKeyPoint();
        MatOfKeyPoint inliers2 = new MatOfKeyPoint();
        MatOfDMatch goodMatchs = new MatOfDMatch();

        DMatch first;
        float dist1;
        float dist2;
        for(int i = 0; i < matchs.get(0).size().height; i++) {

            first = new DMatch((int)matchs.get(0).get(i, 0)[0], (int)matchs.get(0).get(i, 0)[1], (int)matchs.get(0).get(i, 0)[2], (float)matchs.get(0).get(i, 0)[3]);
            dist1 = (float)matchs.get(0).get(i, 0)[3];
            dist2 = (float)matchs.get(1).get(i, 0)[3];

            if(dist1 < 0.8*dist2) {
                matched1.push_back((keyPoints1.row(first.queryIdx)));
                matched2.push_back((keyPoints2.row(first.trainIdx)));
            }
        }

        Mat col = Mat.ones(3,1,CvType.CV_64F);
        for (int i = 0; i < matched1.size().height; i++) {
            col.
        }*/


        matchs.toArray();
        return (int)goodMatches.size().height;
    }


}
