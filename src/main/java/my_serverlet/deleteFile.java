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

import DBOP.databaseOperation;
import com.alibaba.fastjson.JSONObject;
import myConstant.Constant;
import returntype.MyReturn;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


//import org.apache.hadoop.conf.Configuration;
//import org.apache.hadoop.fs.FileStatus;
//import org.apache.hadoop.fs.FileSystem;
//import org.apache.hadoop.fs.Path;

@WebServlet("/deleteFile")
public class deleteFile extends HttpServlet implements Constant {


    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws ServletException, IOException {

        // 设置接受，响应内容类型
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");

        //获取得到的参数
        String username = request.getParameter("userName");
        String filename = request.getParameter("fileName");
        String pre_dirctory = "/" + username;

        System.out.println(username);
        System.out.println(filename);

        String type = "";//保留接口

        //数据库操作
        databaseOperation operation = new databaseOperation();
        MyReturn myreturn = operation.deleteFile(username, filename, pre_dirctory, type);
        //创建json对象
        JSONObject jsonObject = new JSONObject();
        jsonObject.clear();

        if (myreturn.code == 0) {
            //这里在部署的时候需要修改

            String path = "C:/clouddisk/";
            File file = new File(path + username + "/" + filename);
//            if(useHDFS) {
//                Configuration conf = new Configuration();
//                conf.set("fs.defaultFS", "hdfs://cloud.xianfei.cf:8020");
//                conf.set("dfs.replication", "3");
//                try {
//                    FileSystem hdfs = FileSystem.get(new URI("hdfs://cloud.xianfei.cf:8020"), conf, "hadoop");
//                    Path hdfsPath = new Path("/test/" + username + '/' + filename);
//                    hdfs.delete(hdfsPath, true);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (URISyntaxException e) {
//                    e.printStackTrace();
//                }
//            }

            //删除文件
            if (file != null) {
                if (file.delete()) {
                    jsonObject.put("statuscode", 200);
                    jsonObject.put("errorInfo", "成功删除");

                } else {
                    jsonObject.put("statuscode", 403);
                    jsonObject.put("errorInfo", "数据库已经删除，在本机文件删除失败");

                }

            } else {
                jsonObject.put("statuscode", 403);
                jsonObject.put("errorInfo", "数据库已经删除，在本机文件没有找到");
            }


        } else {
            //删除失败
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
