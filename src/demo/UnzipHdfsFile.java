package demo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionCodecFactory;
import org.apache.hadoop.io.compress.CompressionInputStream;

import java.io.IOException;
import java.net.URI;

public class UnzipHdfsFile {

    public static void main(String args[]){
        String baseUrl = "hdfs://127.0.0.1:9000/user/zzh/input";
        String testUrl = "hdfs://127.0.0.1:9000/user/zzh/input/test.gz";
        String outUrl = "hdfs://127.0.0.1:9000/user/zzh/input/test.txt";
        try {
            Configuration conf = new Configuration();
            FileSystem fs = FileSystem.get(URI.create(baseUrl),conf);

            CompressionCodecFactory factory = new CompressionCodecFactory(conf);
            CompressionCodec codec = factory.getCodec(new Path(testUrl));

            //这个用来得到原来文件的名字
            //String outputUrl = CompressionCodecFactory.removeSuffix(testUrl,codec.getDefaultExtension()
//            );
            CompressionInputStream in = codec.createInputStream(fs.open(new Path(testUrl)));
            FSDataOutputStream out = fs.create(new Path(outUrl));

            IOUtils.copyBytes(in,out,conf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
