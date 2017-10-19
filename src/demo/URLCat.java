package demo;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.io.InputStream;
import java.net.URI;

public class URLCat {

    public static final String BASE_URL="hdfs://127.0.0.1:9000/user/zzh/input/README";

    public static Configuration conf;

    public static FileSystem fs;

    static {
        conf = new Configuration();
        try{
            fs = FileSystem.get(URI.create(BASE_URL),conf);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String args[]){
        InputStream in = null;
        try{
            in = fs.open(new Path(BASE_URL));
            IOUtils.copyBytes(in,System.out,4096,false);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            IOUtils.closeStream(in);
        }

    }
}
