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
import entity.file;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.StreamSupport;

@WebServlet("/findFiles")
public class findFiles extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws ServletException, IOException {
        /*测试使用*/
        System.out.println("Find files Call");

        //设置返回编码
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");

        //获取参数
        String username = new String(request.getParameter("userName").getBytes("ISO8859-1"), "UTF-8");

        /*测试使用*/
        System.out.println("findFiles call");

        //设置文件夹，暂时就是用户名，受限于时间问题
        String pre_dirctory = "/" + username;

        //保留接口
        String[] type = new String[1];

        //调用数据库
        databaseOperation operation = new databaseOperation();
        List<file> flist = operation.findFiles(username, pre_dirctory, type[0]);

        //创建json对象
        JSONObject jsonObject = new JSONObject();
        jsonObject.clear();


        if (flist != null) {

            //成功找到
            jsonObject.put("statuscode", 200);
            jsonObject.put("errorInfo", "Success");

            int i = 0;
            /*本循环用于便利数据库穿出的所有file信息*/
            for (; i < flist.size(); i++) {
                /*测试使用*/
                System.out.println(flist.get(i).getFileName());
                System.out.println(flist.get(i).getType());

                //在json中再次创建json，命名就为file+该file的位置数
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("fileName", flist.get(i).getFileName());
                jsonObject1.put("type", flist.get(i).getType());
                jsonObject.put("file" + i, jsonObject1);
            }

            //一个字段用于记录总数

            jsonObject.put("Num", i);

        } else {

            //啥也没到
            jsonObject.put("statuscode", 403);
            jsonObject.put("errorInfo", "Fail");
        }
        //发送返回信息
        response.getWriter().print(jsonObject);
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException {

        doPost(request, response);


    }


}
