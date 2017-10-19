package demo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;

import java.io.*;
import java.net.URI;

public class FileCopyWilhProgress {

    public static void main(String args[]){
        String  localSrc = "/opt/test.txt";
        String  dst= "hdfs://127.0.0.1:9000/user/zzh/input/README";
        InputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(localSrc));
            Configuration conf=new Configuration();
            FileSystem fs = FileSystem.get(URI.create(dst),conf);

            OutputStream out = fs.create(new Path(dst), new Progressable() {
                @Override
                public void progress() {
                    System.out.println(".");
                }
            });

            //IOUtils.copyBytes(fs.open(new Path(dst)),System.out,4096,true);

            System.out.println(fs.open(new Path(dst)).getPos());
            IOUtils.copyBytes(in,out,4096,true);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            IOUtils.closeStream(in);
        }
    }
}
