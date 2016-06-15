package com.example.remi.ekio;

import android.content.Context;
import org.opencv.core.*;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.imgcodecs.Imgcodecs;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by remi on 09/06/16.
 */
public class ObjectDetection {

   // private static final float inlier_treshold = 2.5f;
   // private static final float nn_match_ratio = 2.5f;

    Mat imgSearch;
    MatOfKeyPoint keyPointsSearch;
    Mat descriptorsSearch;
    HashMap<Integer, Mat> list;
    HashMap<Integer, Integer> resultat;


    public ObjectDetection(){};
    public ObjectDetection(String imgpath, Context context){

        resultat = new HashMap<Integer, Integer>();

     //   imgSearch = new Mat();
      //  Imgproc.resize(Imgcodecs.imread(imgpath, Imgcodecs.IMREAD_COLOR), imgSearch, new Size(), 0.5, 0.5, Imgproc.INTER_CUBIC);

        imgSearch = Imgcodecs.imread(imgpath, Imgcodecs.IMREAD_COLOR);

        // potetentiellement modifier // que veut dire le 15 ?
        FeatureDetector fast = FeatureDetector.create(FeatureDetector.AKAZE);
        // keypoints of image 1
        keyPointsSearch = new MatOfKeyPoint();
        fast.detect(imgSearch, keyPointsSearch);
        // potentielement à modifier
        descriptorsSearch = new Mat();
        DescriptorExtractor descriptorExtractor = DescriptorExtractor.create(DescriptorExtractor.AKAZE); // conseillé par un mec sur yt
        descriptorExtractor.compute(imgSearch, keyPointsSearch, descriptorsSearch);

        CollectionableDAO objectDao = new CollectionableDAO(context);
        objectDao.open();
        list = objectDao.allMat();
        objectDao.close();


    }

    public int match2(Context context) {

        CollectionableDAO objectDao = new CollectionableDAO(context);
        objectDao.open();

        for (Integer mapKey : list.keySet()) {
           // Toast.makeText(context, "Please wait ...", Toast.LENGTH_SHORT).show();
/*            Mat img = Imgcodecs.imread(path, Imgcodecs.IMREAD_COLOR);

            FeatureDetector fast = FeatureDetector.create(FeatureDetector.AKAZE);
            MatOfKeyPoint keyPoints = new MatOfKeyPoint();
            fast.detect(img, keyPoints);

            DescriptorExtractor descriptorExtractor = DescriptorExtractor.create(DescriptorExtractor.AKAZE); // conseillé par un mec sur yt
            Mat descriptors = new Mat();
            descriptorExtractor.compute(img, keyPoints, descriptors);*/

            DescriptorMatcher descriptorMatcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE);
            //MatOfDMatch matchs = new MatOfDMatch();

            ArrayList<MatOfDMatch> matchs = new ArrayList();
            descriptorMatcher.knnMatch(list.get(mapKey), descriptorsSearch, matchs, 2);

            float ratio = 0.8f; // As in Lowe's paper (can be tuned)
            MatOfKeyPoint goodMatches = new MatOfKeyPoint();
            for (int matchIdx = 0; matchIdx < matchs.size(); ++matchIdx)
            {
                if (matchs.get(matchIdx).get(0,0)[3] < ratio * matchs.get(matchIdx).get(1,0)[3])
                {
                    goodMatches.push_back(matchs.get(matchIdx).row(0));
                }
            }


          //  int id = objectDao.getIdFromPath(path);
            resultat.put(mapKey, (int)goodMatches.size().height);


        }
        objectDao.close();

        return 0;
    }




}
