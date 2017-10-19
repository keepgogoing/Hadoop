package demo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.net.URI;

public class Mkdirs {
    public static void main(String args[]){
        String url = "hdfs://127.0.0.1:9000/user/zzh/input/test";
        String noExist = "hdfs://127.0.0.1:9000/user/zzh/input/test";
        Configuration conf = new Configuration();
        try {
            FileSystem fs = FileSystem.get(URI.create(url),conf);
            //列出文件目录中的内容状态
            //fs.listStatus();
            //fs.getFileStatus();
            System.out.println(fs.mkdirs(new Path(noExist)) );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
