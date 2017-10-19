package demo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.fs.FileStatus;

import java.io.IOException;
import java.net.URI;

public class GlobStatus {

    public static void main(String args[]){
        String url = "hdfs://127.0.0.1:9000/user/zzh/input";
        try {
            Configuration conf = new Configuration();
            /*FileSystem fss= new RawLocalFileSystem();
            fss.initialize(null,conf);*/

            FileSystem fs = FileSystem.get(URI.create(url),conf);
            /*FileStatus[] fileStatuses = fs.listStatus(new Path(url));
            Path[] paths = FileUtil.stat2Paths(fileStatuses);
            for(Path p: paths){
                //System.out.println(p);
                fs.create(new Path(p+"/12/30/test.txt")).write((p+"/12/30/test.txt").getBytes());
                fs.create(new Path(p+"/12/31/test.txt")).write((p+"/12/31/test.txt").getBytes());
            }*/
            FileStatus[] fileStatuses = fs.globStatus(new Path(url + "/*/{12/31,1/1}"));
            Path[] paths = FileUtil.stat2Paths(fileStatuses);
            /*for (Path p :paths){
                System.out.println(p);
            }*/
//是否禁用校验和验证
//            fs.setVerifyChecksum();


            FileStatus[] fileStatuses1 = fs.globStatus(new Path(url+"/*/*"), new PathFilter() {
                @Override
                public boolean accept(Path path) {
                    if (path.toString().matches("^.*/*/1"))
                       return false;
                    return true;
                }
            });
            Path[] paths1 = FileUtil.stat2Paths(fileStatuses1);
            for (Path p:paths1){
                System.out.println(p);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
