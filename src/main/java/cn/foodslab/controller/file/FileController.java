package cn.foodslab.controller.file;

import com.alibaba.fastjson.JSON;
import com.jfinal.core.Controller;
import com.jfinal.upload.UploadFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.LinkedHashMap;

/**
 * Created by Pengwei Ding on 2016-11-01 15:40.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class FileController extends Controller implements IFileController {
    @Override
    public void index() {

    }

    @Override
    //@Before({SessionInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    public void mImage() {
        UploadFile file = this.getFile("file");
//        fileChannelCopy(file.getFile(), new File("/Users/dingpengwei/Desktop/" + file.getFileName()));
        String p = this.getPara("p");
        System.out.println(p);
        System.out.println("------------ok-------------");
        LinkedHashMap<String,String> map = new LinkedHashMap<>();
        map.put("code","200");
        map.put("path","http://localhost:8080/foodslab/upload/" + file.getFileName());
        renderJson(JSON.toJSONString(map));
    }


    public void fileChannelCopy(File s, File t) {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        FileChannel fileInChannel = null;
        FileChannel fileOutChannel = null;
        try {
            fileInputStream = new FileInputStream(s);
            fileOutputStream = new FileOutputStream(t);
            fileInChannel = fileInputStream.getChannel();// 得到对应的文件通道
            fileOutChannel = fileOutputStream.getChannel();// 得到对应的文件通道
            fileInChannel.transferTo(0, fileInChannel.size(), fileOutChannel);// 连接两个通道，并且从in通道读取，然后写入out通道
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileInputStream.close();
                fileInChannel.close();
                fileOutputStream.close();
                fileOutChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("=========ok=========");
        }
    }
}
