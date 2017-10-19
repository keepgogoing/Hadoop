package demo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.net.URI;

public class FileStatus {


    public static void main(String args[]){
        String url = "hdfs://127.0.0.1:9000/user/zzh/input";
        Configuration conf = new Configuration();
        try {
            FileSystem fs = FileSystem.get(URI.create(url), conf);
            System.out.println(fs.getFileStatus(new Path(url)));
            org.apache.hadoop.fs.FileStatus[] fileStatuses = fs.listStatus(new Path(url));
            Path[] paths = FileUtil.stat2Paths(fileStatuses);
            for (Path p:paths){
                System.out.println(p);
            }

            for (int i=0;i<fileStatuses.length;i++){
                System.out.println(fileStatuses[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
