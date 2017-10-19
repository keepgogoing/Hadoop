package demo;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionOutputStream;
import org.apache.hadoop.util.ReflectionUtils;

import java.io.IOException;
import java.net.URI;

public class ZipHdfsFile {
    public static void main(String args[]){
        try {
            String baseUrl = "hdfs://127.0.0.1:9000/user/zzh/input";
            String testUrl = "hdfs://127.0.0.1:9000/user/zzh/input/test.gz";
            String readUrl = "hdfs://127.0.0.1:9000/user/zzh/input/README";
            String codecClassname = "org.apache.hadoop.io.compress.GzipCodec";
            Class<?> codecClass = Class.forName(codecClassname);
            Configuration conf = new Configuration();
            CompressionCodec codec = (CompressionCodec) ReflectionUtils.newInstance(codecClass,conf);
            FileSystem fs = FileSystem.get(URI.create(baseUrl),conf);
            fs.setVerifyChecksum(false);
            FSDataInputStream in = fs.open(new Path(readUrl));
            FSDataOutputStream outputStream = fs.create(new Path(testUrl));
            CompressionOutputStream out = codec.createOutputStream(outputStream);
            IOUtils.copyBytes(in,out,4096,false);

            out.finish();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
