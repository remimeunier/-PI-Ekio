package com.example.remi.ekio;

import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.FeatureDetector;
import org.opencv.imgcodecs.Imgcodecs;

import java.util.ArrayList;

/**
 * Created by remi on 01/06/16.
 */
public class Collectionable {

    private long id;
    private String title;
    private String date;
    private String location;
    private String comment;
    private String keyWords;
    private String photoPath;
    private String descriptor;

    public Collectionable(long id, String title, String date, String location, String comment, String keyWords, String photoPath) {
        super();
        this.id = id;
        this.title = title;
        this.date = date;
        this.location = location;
        this.comment = comment;
        this.keyWords = keyWords;
        this.photoPath = photoPath;
    }
    public Collectionable(String title, String date, String location, String comment, String keyWords, String photoPath) {
        super();
        this.title = title;
        this.date = date;
        this.location = location;
        this.comment = comment;
        this.keyWords = keyWords;
        this.photoPath = photoPath;
    }
    public Collectionable(String title, String date, String location, String comment, String keyWords, String photoPath, Boolean createMat) {
        super();
        this.title = title;
        this.date = date;
        this.location = location;
        this.comment = comment;
        this.keyWords = keyWords;
        this.photoPath = photoPath;
        if (createMat == true) {

            Mat img = Imgcodecs.imread(photoPath, Imgcodecs.IMREAD_COLOR);
            FeatureDetector fast = FeatureDetector.create(FeatureDetector.AKAZE);
            MatOfKeyPoint keyPoints = new MatOfKeyPoint();
            fast.detect(img, keyPoints);

            DescriptorExtractor descriptorExtractor = DescriptorExtractor.create(DescriptorExtractor.AKAZE); // conseillé par un mec sur yt
            Mat descriptors = new Mat();
            descriptorExtractor.compute(img, keyPoints, descriptors);

            descriptor = this.matToJson(descriptors);
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public String getcomment() {
        return comment;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public String getDecriptor() {
        return descriptor;
    }


        public static String matToJson(Mat mat){
            JsonObject obj = new JsonObject();

            if(mat.isContinuous()){
                int cols = mat.cols();
                int rows = mat.rows();
                int elemSize = (int) mat.elemSize();

                byte[] data = new byte[cols * rows * elemSize];

                mat.get(0, 0, data);

                obj.addProperty("rows", mat.rows());
                obj.addProperty("cols", mat.cols());
                obj.addProperty("type", mat.type());

                // We cannot set binary data to a json object, so:
                // Encoding data byte array to Base64.
                String dataString = new String(Base64.encode(data, Base64.DEFAULT));

                obj.addProperty("data", dataString);

                Gson gson = new Gson();
                String json = gson.toJson(obj);

                return json;
            } else {
                // Log.e(TAG, "Mat not continuous.");
            }
            return "{}";
        }

        public static  Mat matFromJson(String json){
            JsonParser parser = new JsonParser();
            JsonObject JsonObject = parser.parse(json).getAsJsonObject();

            int rows = JsonObject.get("rows").getAsInt();
            int cols = JsonObject.get("cols").getAsInt();
            int type = JsonObject.get("type").getAsInt();

            String dataString = JsonObject.get("data").getAsString();
            byte[] data = Base64.decode(dataString.getBytes(), Base64.DEFAULT);

            Mat mat = new Mat(rows, cols, type);
            mat.put(0, 0, data);

            return mat;
        }
}
