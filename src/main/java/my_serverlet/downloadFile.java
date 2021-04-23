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
import entity.file;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import DBOP.databaseOperation;
import myConstant.Constant;

@WebServlet("/downloadFile")
public class downloadFile extends HttpServlet implements Constant {
    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws ServletException, IOException{
        if(useHDFS) {
            // xianfei 备注：Download 直接交给Hadoop解决吧，咱不手写了
            //设置返回类型
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json");
            request.setCharacterEncoding("utf-8");
            JSONObject jsonObject = new JSONObject();
            //获取前端传回的参数
            String username = request.getParameter("userName");
            String filename = request.getParameter("fileName");

            /*测试使用*/
            System.out.println(username);
            System.out.println(filename);

            //获取环境
            ServletContext servletContext = this.getServletContext();

            //设置本地地点路径，在使用hadoop时需要修改！！！！！！！！！(写给贤妃)
            String realpath = "C:/clouddisk/" + username + "/" + filename;

            //数据库判断有没有以上文件
            databaseOperation operation = new databaseOperation();
            List<file> fl = operation.findFile(username, filename, "/" + username, "");
            if (fl == null) {
                /*do nothing */
            } else {//判断数据库中有该文件再进行查找
                //http://cloud.xianfei.cf:50070/webhdfs/v1/test/xianfei/CefSharp.BrowserSubprocess.exe?op=OPEN
                jsonObject.put("dlurl", "http://cloud.xianfei.cf:50070/webhdfs/v1/test/" + username + "/" + filename + "?op=OPEN");
            }
            response.getWriter().print(jsonObject);
        }else {

            //千万不要删除一下注释！！！！！！！！！！！！！！！！！！


            /*测试是否调用*/
            System.out.println(" calldownload");

            //返回的编码设置
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/pdf");

            //获取前端传回的参数
            String username = request.getParameter("userName");
            String filename = request.getParameter("fileName");

            /*测试使用*/
            System.out.println(username);
            System.out.println(filename);

            //获取环境
            ServletContext servletContext = this.getServletContext();

            //设置文件名
            response.setHeader("Content-Disposition", "attachment;filename=" + filename);

            //设置本地地点路径，在使用hadoop时需要修改！！！！！！！！！(写给贤妃)
            String realpath = "C:/clouddisk/" + username + "/" + filename;

            //数据库判断有没有以上文件
            databaseOperation operation = new databaseOperation();
            List<file> fl = operation.findFile(username, filename, "/" + username, "");
            if (fl == null) {
                /*do nothing */
            } else {//判断数据库中有该文件再进行查找
                //打开文件流
                try {
                    //考虑到hadoop使用需要先存入本地，对于本读文件存储结束，可以开始进行hadoop操作存入本地，路径放到变量@varible(realpath)中，


                    //请在以上添加hadoop的操作


                    //获取文件输入流
                    InputStream in = new FileInputStream(new File(realpath));
                    //获取输出流
                    OutputStream out = response.getOutputStream();
                    int read = 0;
                    byte[] bytes = new byte[10240000];
                    while ((read = in.read(bytes)) != -1) {
                        out.write(bytes, 0, read);
                    }
                    out.flush();
                    out.close();
                    in.close();


                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }


            }
        }

    }
    @Override
    public void doGet(HttpServletRequest request,
                       HttpServletResponse response)
            throws ServletException, IOException{
            doPost(request,response);
    }
}
