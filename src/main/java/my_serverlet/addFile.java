// MIT License
//
// Copyright (c) 2020 HowardYun
//
//        Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
//        to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//        copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all
//        copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//        AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.
/*create by YunShaoXuan*/




/*请根据注释修改相关内容*/
package my_serverlet;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;

import DBOP.databaseOperation;
import myConstant.Constant;
import returntype.MyReturn;

//
//import org.apache.hadoop.conf.Configuration;
//import org.apache.hadoop.fs.FileStatus;
//import org.apache.hadoop.fs.FileSystem;
//import org.apache.hadoop.fs.Path;
@WebServlet("/addFile")
public class addFile extends HttpServlet implements Constant {

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws ServletException, IOException {
        //设置返回类型
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        request.setCharacterEncoding("utf-8");
        //获取参数
        String username = request.getParameter("userName");
        String pre_dirctory = "/" + username;


        //创建json对象
        JSONObject jsonObject = new JSONObject();
        jsonObject.clear();
        String errorStr = "unknown error";
        //获取文件
        Part part = request.getPart("file"); // 获取文件
        String filename = part.getSubmittedFileName();//获取文件名
        int index = filename.lastIndexOf('.');
        String type = filename.substring(index + 1, filename.length());

        //数据库操作
        databaseOperation operation = new databaseOperation();
        MyReturn myreturn = operation.addFiles(username, filename, pre_dirctory, type);

        if (myreturn.code == 0) {//add success
            int check = 1;
            //这里的savepath需要改变，写给贤妃
            String savePath = "C:/clouddisk/" + username;

            try {
                //String savePath = "C:/clouddisk/"+username;//要改成username

                //在这里写入本机文件
                File folder = new File(savePath);
                if (!folder.exists() && !folder.isDirectory()) {
                    folder.mkdirs();
                    System.out.println("创建文件夹");
                } else {
                    System.out.println("文件夹已存在");
                }

                if (filename != null && !"".equals(filename)) {
                    // 写入磁盘
                    String localPath = savePath + File.separator + filename;
                    part.write(localPath);
                    // 写入hdfs
//                    if(useHDFS){
//                        Configuration conf = new Configuration();
//                        conf.set("fs.defaultFS", "hdfs://cloud.xianfei.cf:8020");
//                        conf.set("dfs.replication", "3");
//                        FileSystem hdfs = FileSystem.get(new URI("hdfs://cloud.xianfei.cf:8020"), conf, "hadoop");
//                        Path hdfsPath = new Path("/test/" + username + '/' + filename);
//                        hdfs.copyFromLocalFile(new Path(localPath), hdfsPath);
//                    }
                }
//xf--------------------------------------------

            } catch (Exception e) {
                myreturn.errorinfo=e.getMessage();
                e.printStackTrace();
                check = 0;
                System.out.println("Something Wrong");
            }
            if (check == 1) {
                System.out.print("ok");
                jsonObject.put("statuscode", 200);
                jsonObject.put("errorInfo", myreturn.errorinfo);
            }
        } else {//add fail
            jsonObject.put("statuscode", 403);
            jsonObject.put("errorInfo", myreturn.errorinfo);
        }

        //返回状态信息
        response.getWriter().print(jsonObject);
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException {

        doPost(request, response);

    }
}
