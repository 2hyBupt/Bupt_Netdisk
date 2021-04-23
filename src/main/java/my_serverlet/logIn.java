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


/*请不要修改该文件的任何内容*/

package my_serverlet;

import DBOP.databaseOperation;
import com.alibaba.fastjson.JSONObject;
import returntype.MyReturn;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/logIn")
public class logIn extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws ServletException, IOException {
        //千万不要删除一下注释！！！！！！！！！！！！！！！！！！

        //设置返回编码方式
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        /*测试使用*/
        System.out.println("call");

        //获取变量
        String username = request.getParameter("userName");
        String pwd = request.getParameter("password");
        /*测试使用*/
        System.out.println(username);


        //定义json对象
        JSONObject jsonObject = new JSONObject();

        //创建本地接受变量
        int id = 0;
        String[] phone = new String[1];
        Integer[] capacity = new Integer[1];

        //数据库操作
        databaseOperation operation = new databaseOperation();
        MyReturn myReturn = operation.logIn(username, pwd, phone, capacity);

        if (myReturn.code == 0) {

            //成功注册
            //以防万一进行清空
            jsonObject.clear();
            //添加状态码
            jsonObject.put("statuscode", (int) 200);
            //添加错误信息
            jsonObject.put("errorInfo", "Log in success");
            //返回个人信息
            jsonObject.put("id", id);
            jsonObject.put("phone", phone[0]);
            jsonObject.put("capacity", capacity[0]);
            //返回状态信息
            response.getWriter().print(jsonObject);
        } else {//注册失败
            //以防万一进行清空
            jsonObject.clear();
            //添加状态码
            jsonObject.put("statuscode", 403);
            //添加错误信息
            jsonObject.put("errorInfo", myReturn.errorinfo);
            //返回个人信息
            jsonObject.put("id", 0);
            jsonObject.put("phone", "");
            jsonObject.put("capacity", 0);

            //返回状态信息
            response.getWriter().print(jsonObject);
        }


    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException {

        doPost(request, response);

    }
}
