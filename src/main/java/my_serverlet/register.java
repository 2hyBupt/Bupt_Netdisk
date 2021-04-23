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

/*请不要修改该文件中任何内容*/
package my_serverlet;

import DBOP.databaseOperation;
import com.alibaba.fastjson.JSONObject;
import returntype.MyReturn;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class register extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws ServletException, IOException {
        /*测试使用*/
        System.out.println("register call");
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        // 获取需要使用的变量
        String username = request.getParameter("userName");
        String pwd = request.getParameter("password");
        String phone = request.getParameter("phone");
        /*测试使用*/
        System.out.println(username + pwd + phone);

        //创建json对象
        JSONObject jsonObject = new JSONObject();

        //数据库操作
        databaseOperation operation = new databaseOperation();
        MyReturn myReturn = operation.signIn(username, pwd, phone);


        if (myReturn.code == 0) {
            //成功返回
            jsonObject.clear();
            jsonObject.put("statuscode", 200);
            jsonObject.put("errorInfo", "Register success");
        } else {
            //注册失败，错误信息已经在errorInfo中
            jsonObject.clear();
            jsonObject.put("statuscode", 403);
            jsonObject.put("errorInfo", myReturn.errorinfo);
        }
        //写回数据
        response.getWriter().print(jsonObject);
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

}
