package cn.zh.zhbackend.canseeguan.Utils;

import com.spire.presentation.Presentation;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class FileUtil {

    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        System.out.println("filePath="+filePath);
        //获取父级文件夹
        File fileParent = targetFile.getParentFile();
        //判断该文件夹或者该文件是否存在，不存在则创建
        if(!fileParent.exists()){

            System.out.println("该路径不存在,接下来创建");
            //先创建父级目录，然后再创建子目录
            fileParent.mkdirs();
            targetFile.mkdir();

            System.out.println("创建成功");
        }
        String savePath = filePath+"/SVG";
        File outFile = new File(targetFile,fileName);
        FileOutputStream out = new FileOutputStream(outFile);
        out.write(file);
        out.flush();
        pptToSvg(filePath,fileName,savePath);
        out.close();
    }

    public static void pptToSvg(String filePath,String fileName,String savePath) throws Exception {

        //创建Presentation对象
        Presentation ppt = new Presentation();
        File file = new File(savePath);

        if(!file.exists()){
            System.out.println("该路径不存在，创建中");
            file.mkdir();
            System.out.println("创建完成");
        }
        //加载示例文档
        ppt.loadFromFile("/Users/mac/Desktop/uploads/company1/PPT/333.pptx");

        //将PowerPoint文档转换为SVG格式，并以byte数组的形式保存于ArrayList
        ArrayList<byte[]> svgBytes =(ArrayList<byte[]>) ppt.saveToSVG();

        //遍历ArrayList中的byte数组
        for (int i = 0; i < svgBytes.size(); i++)
        {
            File saveFile = new File(file,String.format("333_SVG_%d.svg",i));
            //将byte数组保存为SVG格式文件
            byte[] bytes = svgBytes.get(i);
            FileOutputStream stream = new FileOutputStream(saveFile);
            stream.write(bytes);
        }
        ppt.dispose();
    }

}