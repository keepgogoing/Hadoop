package demo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.ByteBuffer;

public class FileSystemDoubleCat {

    public static void main(String args[]){
//        InputStream in = null;
        FSDataInputStream in = null;
        FSDataOutputStream out = null;
        try {
            String url = "hdfs://127.0.0.1:9000/user/zzh/input/README";
            Configuration conf = new Configuration();
            FileSystem fs = FileSystem.get(URI.create(url),conf);
           // in.seek(0);
           // in.skip(0); 跳转到开头的位置
           // IOUtils.copyBytes(in,System.out,4096,false);
            String appendString = "我是小白";
            fs.append(new Path(url)).write(appendString.getBytes());
            String noExist = "hdfs://127.0.0.1:9000/user/zzh/input/test.txt";

            out = fs.create(new Path(noExist));
            out.write(appendString.getBytes());
            //out.hflush();
            //同步，让下面能显示写入的东西
            out.hsync();
            in = fs.open(new Path(noExist));
            IOUtils.copyBytes(in,System.out,4096,false);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            IOUtils.closeStream(in);
            IOUtils.closeStream(out);
        }

    }
}
