var SUCCESS = 200;
var ERROR = 404;
var FAILURE = 500;
var selectedfilename=null;
$(function () {	
    $("#checkboxinfo").click(judgeIsChecked);
    if(!cookie('checked')){
        $("#tipsinfo").modal("show");
    }
    //展示用户有的文件
    showDiskInfo();

    // setInterval(function(){
    //     showDiskInfo();
    // },60000);
    //$("#homeFile").click(showDiskInfo);
    $("#downloadButton").click(downloadOption);
    $("#deleteButton").click(deleteOption);
   // $("#createDirInfo").click(createNewDir);
	
	$('#uploadInfo').on('change', function() {
		var files = this.files;
		if (files.length == 0) {
			return;
		}
		uploadFile = files[0];
	});
	//文件上传 
	$("#uploadDirInfo").on('click', function() {
	var filename=document.getElementById('uploadInfo').files[0].name;
	var diractory=document.getElementById('uploadInfo').files[0].value;
	var username=cookie("userName");
	    var formData = new FormData();
		formData.append("userName", username);
		formData.append("fileName", filename);
		formData.append("diractory", diractory);
		formData.append("file", uploadFile); // 后端通过 'file' 获取
    $.ajax({    
         url : "/netdisk/addFile", //这条需要你给我
        type : "POST",
        data:formData,
        processData : false,
         contentType : false,
         success : function(result) {    
          if(result.statuscode==SUCCESS){
              showDiskInfo();
              window.alert("上传成功");
          }else {
              window.alert("上传失败"+result.errorInfo);
          }
         },    
         error : function(result) {    
		 window.alert("上传失败"+result.errorInfo);
         }    
    });
});

		
});

function getcookie(NameOfCookie) {
                if (document.cookie.length > 0) {
                    begin = document.cookie.indexOf(NameOfCookie + "=");
                    if (begin !== -1) {
                        begin += NameOfCookie.length + 1;
                        end = document.cookie.indexOf(";", begin);
                        if (end === -1) end = document.cookie.length;
                        return unescape(document.cookie.substring(begin, end));
                    }
                }
                return null;
            }

function getPath(obj) {
    if (obj) {
        if(window.navigator.userAgent.indexOf("MSIE") >= 1){
            obj.select();
            return document.selection.createRange().text;
    } else if(window.navigator.userAgent.indexOf("Firefox") >= 1){
        if (obj.files) {
            return obj.files.item(0).getAsDataURL();
        }
        return obj.value;
    }
    return obj.value;
}}

function showPath(){
    console.log($("#uploadInfo").val());
}

function judgeIsChecked(){
    if($('#checkboxinfo').is(':checked')){
        addCookie("checked", "true");
        setInterval(function(){
            location.href = "MyCloudDisk.html";
        },1000);
    }
}

function showDiskInfo() {
    $(".diskinfo").empty();
    $("#currentFile").html(" ");
	userName=cookie("userName")
    $.ajax({
        url : "/netdisk/findFiles",
        data : {
			'userName':userName
		},
        type : "Post",
        dataType : "json",
        success : function(result) {
            if (result.statuscode  == SUCCESS) {
				var	info=result;
                showAllDiskInfo(info);
            } else {
                if (result.statuscode  == ERROR)
                {

                    layer.msg(result.msg,{icon:0})
                }
            }
        },
        error : function(e) {
			//试试看能不能画出来 ok 测试是可以的
			var filename1="gogogo.txt";
			var filename2="abc.txt";
			var html="<div class='box template selected' i  data-show='no'filename='"+filename+"' filename='"+filename+"' value='filename'>"+"<img src='images/txt.jpg' class='icon' alt=''filename='"+filename+"' filename='"+filename+"' value='filename' ondblclick='preview("+JSON.stringify(filename1)+")'>"
                    +"<div><p title='"+"filename"+"'>"+filename1+"</p><input type='text' class='none'></div>"
                   +"<img src='images/select.png' class='select' alt='' id='"+filename1+"' onclick='sendfname("+JSON.stringify(filename1)+")'></div>"
				   
				   +"<div class='box template selected i' data-show='no' value='"+"filename"+"'>"+"<img src='images/bmp.png' class='icon' alt=''filename='"+filename+"' filename='"+filename+"' value='filename'  ondblclick='preview("+JSON.stringify(filename2)+")'>"
                    +"<div><p title='"+"info[i].filename"+"'>"+filename2+"</p><input type='text' class='none'></div>"
                   +"<img src='images/select.png' class='select' alt='' id='"+filename2+"'  onclick='sendfname("+JSON.stringify(filename2)+")'></div>"
				   ;
			document.getElementById("loading").innerHTML=html;
		
			
    $(".sousouInput").blur(function(){
         $(".sousuo").css({
            border:"1px solid #ccc"
         });
         $(".ss1").removeClass("focusState");
     });
     $(".sousouInput").focus(function(){
         $(".sousuo").css({
             border:"1px solid #9cf"
         });
         $(".ss1").addClass("focusState");
     });
     $(".zuijinTitle").css({
        width:$(window).width()-237
     });
     $(".content").css({
        width:$(window).width()-237
   });
    
     $("#page_content").css({
         width:$(window).width()-237
    });
    
            layer.msg("showDiskInfo",{icon:2});

        }
    })
}

function sendfname(filenameselect)
{
	// alert(filenameselect);
	var img = document.getElementById(filenameselect);
	console.log(img.src);
	if(img.src.indexOf("images/selectC.png")!=-1){
        img.src = "images/select.png";
        selectedfilename=null;
    }else {
        img.src = "images/selectC.png";
        selectedfilename = filenameselect;
    }
	
}

 function preview(detailInfo){//双击下载可以试试了  	
	selectedfilename=detailInfo;
	 alert(detailInfo);
	 downloadOption(); 
 }

 function searchFile(){
     var searchText = $('#searchInput').val();
     console.log(searchText)
     var items = document.getElementsByClassName('box')
     for (const item of items) {
         if(searchText==''){item.style.background = '';continue;}
         if(item.getAttribute('filename').indexOf(searchText)!=-1)item.style.background = 'yellow';
         else item.style.background = '';
     }
 }

function showAllDiskInfo(info) {
    var Num = info.Num;
    // alert(Num);
    var outhtml="";
    for (var i = 0; i < Num; i++) {
        var filename = info["file" + i].fileName;
        var type = info["file" + i].type;
        if (type == "1") {
        } else {
            //这里需要重新画图，变量只使用filename，type，其他字段要保证空串以及空白串
			if(type=="txt")	{				
					outhtml=outhtml+"<div class='box template selected' i  data-show='no'filename='"+filename+"' filename='"+filename+"' value='filename'>"+"<img src='images/txt.jpg' class='icon' alt=''filename='"+filename+"' filename='"+filename+"' value='filename' ondblclick='preview("+JSON.stringify(filename)+")'>"
                    +"<div><p title='"+"filename"+"'>"+filename+"</p><input type='text' class='none'></div>"
                   +"<img src='images/select.png' class='select' alt='' id='"+filename+"' onclick='sendfname("+JSON.stringify(filename)+")'></div>";
			}
			
		else if(type=="pdf"){
			  outhtml=outhtml
                    +"<div class='box template selected' i  data-show='no'filename='"+filename+"' filename='"+filename+"' value='filename'>"+"<img src='images/pdf.png' class='icon' alt=''filename='"+filename+"' filename='"+filename+"' value='filename' ondblclick='preview("+JSON.stringify(filename)+")'>"
                    +"<div><p title='"+"filename"+"'>"+filename+"</p><input type='text' class='none'></div>"
                   +"<img src='images/select.png' class='select' alt='' id='"+filename+"' onclick='sendfname("+JSON.stringify(filename)+")'></div>";
			}
		else if(type=="jpg")	{

			   outhtml=outhtml+
                   "<div class='box template selected' i  data-show='no'filename='"+filename+"' filename='"+filename+"' value='filename'>"+"<img src='images/jpg.gif' class='icon' alt=''filename='"+filename+"' filename='"+filename+"' value='filename' ondblclick='preview("+JSON.stringify(filename)+")'>"
                    +"<div><p title='"+"filename"+"'>"+filename+"</p><input type='text' class='none'></div>"
                   +"<img src='images/select.png' class='select' alt='' id='"+filename+"' onclick='sendfname("+JSON.stringify(filename)+")'></div>";
		
			}
			else if(type=="bmp")	{	
			 outhtml=outhtml+
                  "<div class='box template selected' i  data-show='no'filename='"+filename+"' filename='"+filename+"' value='filename'>"+"<img src='images/bmp.png' class='icon' alt=''filename='"+filename+"' filename='"+filename+"' value='filename' ondblclick='preview("+JSON.stringify(filename)+")'>"
                    +"<div><p title='"+"filename"+"'>"+filename+"</p><input type='text' class='none'></div>"
                   +"<img src='images/select.png' class='select' alt='' id='"+filename+"' onclick='sendfname("+JSON.stringify(filename)+")'></div>";	
			}
			else	if(type=="avi")	{	
			 outhtml=outhtml+
                    "<div class='box template selected' i  data-show='no'filename='"+filename+"' filename='"+filename+"' value='filename'>"+"<img src='images/avi.png' class='icon' alt=''filename='"+filename+"' filename='"+filename+"' value='filename' ondblclick='preview("+JSON.stringify(filename)+")'>"
                    +"<div><p title='"+"filename"+"'>"+filename+"</p><input type='text' class='none'></div>"
                  +"<img src='images/select.png' class='select' alt='' id='"+filename+"' onclick='sendfname("+JSON.stringify(filename)+")'></div>";
			}
            else	if(type=="doc")	{
                outhtml=outhtml+
                    "<div class='box template selected' i  data-show='no'filename='"+filename+"' filename='"+filename+"' value='filename'>"+"<img src='images/doc.png' class='icon' alt=''filename='"+filename+"' filename='"+filename+"' value='filename' ondblclick='preview("+JSON.stringify(filename)+")'>"
                    +"<div><p title='"+"filename"+"'>"+filename+"</p><input type='text' class='none'></div>"
                    +"<img src='images/select.png' class='select' alt='' id='"+filename+"' onclick='sendfname("+JSON.stringify(filename)+")'></div>";
            }
			else{
					 outhtml=outhtml+
                   "<div class='box template selected' i  data-show='no'filename='"+filename+"' filename='"+filename+"' value='filename'>"+"<img src='images/settings.png' class='icon' alt=''filename='"+filename+"' filename='"+filename+"' value='filename' ondblclick='preview("+JSON.stringify(filename)+")'>"
                    +"<div><p title='"+"filename"+"'>"+filename+"</p><input type='text' class='none'></div>"
                    +"<img src='images/select.png' class='select' alt='' id='"+filename+"' onclick='sendfname("+JSON.stringify(filename)+")'></div>";
			}

        }
    }
	document.getElementById("loading").innerHTML=outhtml;//绘制

    $(".sousouInput").blur(function(){
         $(".sousuo").css({
            border:"1px solid #ccc"
         });
         $(".ss1").removeClass("focusState");
     });
     $(".sousouInput").focus(function(){
         $(".sousuo").css({
             border:"1px solid #9cf"
         });
         $(".ss1").addClass("focusState");
     });
     $(".zuijinTitle").css({
        width:$(window).width()-237
     });
     $(".content").css({
        width:$(window).width()-237
   });   
     $("#page_content").css({
         width:$(window).width()-237
    });
}

function downloadOption(){
    // var filename=selectedfilename;
    // var username=cookie("userName");
    // $.ajax({
    //     url : "/netdisk/downloadFile", //这条需要你给我
    //     data:{
    //         "fileName":filename,
    //         "userName":username
    //     },
    //     type : "POST",
    //     dataType: "json"
    //     ,
    //     success : function(result) {
    //         window.open(result.dlurl);
    //
    //     },
    //     error : function(result) {
    //         window.alert("网络异常失败");
    //     }
    // });
	// alert(selectedfilename);
    if(selectedfilename==null){
        alert('你没选择文件');
        return;
    }
	var $form = $("<form id='download' class='hidden' method='post' action='/netdisk/downloadFile' ></form>");
    var input1=$("<input>");
    input1.attr("type","hidden");
    input1.attr("name","userName");
    input1.attr("value",getcookie("userName"));
    var input2=$("<input>");
    input2.attr("type","hidden");
    input2.attr("name","fileName");
    input2.attr("value",selectedfilename);
    $form.append(input1);
    $form.append(input2);
    $(".diskinfo").append($form);
    $form.submit();
}

function deleteOption(){
	
	var filename=selectedfilename;
	var username=cookie("userName");
    $.ajax({    
         url : "/netdisk/deleteFile", //这条需要你给我
        data:{
            "fileName":filename,
			"userName":username
		},
        type : "POST",
        dataType: "json"
        ,
         success : function(result) {
            if(result.statuscode==SUCCESS){
                showDiskInfo();
                window.alert("删除成功");

            }else {

                window.alert("删除失败");
            }

         },    
         error : function(result) {    
		 window.alert("网络异常失败");
         }    
    });
}
