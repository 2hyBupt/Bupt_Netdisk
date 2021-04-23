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

/*根据注释进行修改*/
package my_serverlet;

import DBOP.databaseOperation;
import com.alibaba.fastjson.JSONObject;
import returntype.MyReturn;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@WebServlet("/modifyFile")
public class modifyFile extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        //设置返回环境
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");

        //获取参数
        String username = new String(request.getParameter("userName").getBytes("ISO8859-1"), "UTF-8");
        String old_filename = request.getParameter("old_fileName");
        String new_filename = new String(request.getParameter("new_fileName").getBytes("ISO8859-1"), "UTF-8");

        //保留接口
        String type = "";

        //保留接口，暂时不考虑使用更改文件夹功能
        String old_pre_dirctory = "/" + username;
        String new_pre_dirctory = "/" + username;

        //数据库操作
        databaseOperation operation = new databaseOperation();
        MyReturn myreturn = operation.modifyFileName(username, old_filename, old_pre_dirctory, new_filename, new_pre_dirctory, type);

        //创建json对象
        JSONObject jsonObject = new JSONObject();
        jsonObject.clear();


        if (myreturn.code == 0) {

            String oldpath = "C:/clouddisk/" + username + "/" + old_filename;
            File ofile = new File(oldpath);
            if (!ofile.exists()) {
                jsonObject.put("statuscode", 403);
                jsonObject.put("errorInfo", "数据库找到，但是系统中不存在");

            } else {
                String newpath = "C:/clouddisk/" + username + "/" + new_filename;
                File newfile = new File(newpath);
                if (ofile.renameTo(newfile)) {
                    jsonObject.put("statuscode", 200);
                    jsonObject.put("errorInfo", "成功修改");

                    //这时本机修改完成，在此操作hadoop




                } else {
                    jsonObject.put("statuscode", 403);
                    jsonObject.put("errorInfo", "修改失败");
                }

            }

        } else {
            //修改失败
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
