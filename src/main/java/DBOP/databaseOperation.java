package DBOP;

import entity.file;
import entity.user;
import returntype.MyReturn;

import java.util.ArrayList;
import java.util.List;
import JpaUtils.JpaUtils;
import javax.persistence.EntityManager;

public class databaseOperation {
    protected EntityManager em = JpaUtils.getEntityManager();
    // 添加文件
    public MyReturn addFiles(String userName, String fileName, String pre_directory, String fileType){
        try{
            String fileId = pre_directory + fileName;
            file f = em.find(file.class, fileId);
            //如果文件不重复
            if(f == null){
                //保存文件
                em.getTransaction().begin();
                file file_ = new file(fileId, fileName, pre_directory, fileType, userName);
                em.persist(file_);
                em.getTransaction().commit();
            }else{
                return new MyReturn(303, "上传失败, 文件重命名");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new MyReturn(404, "上传失败, 后端有bug");
        }
        return new MyReturn(0, "上传成功");
    }

    //修改文件
    public MyReturn modifyFileName(String userName, String old_fileName, String old_pre_directory,
                                   String new_fileName, String new_pre_directory, String fileType){
        try{
            //先找新的名字是否重名
            String newFileId = new_pre_directory+ new_fileName;
            file newFile = em.find(file.class, newFileId);
            if(newFile == null){
                //更新文件名字
                em.getTransaction().begin();
                String oldFileId = old_pre_directory + old_fileName;

               //云绍轩 modify

                file oldFile = em.find(file.class, oldFileId);
                em.remove(oldFile);

                newFile = new file(newFileId, new_fileName, new_pre_directory, oldFile.getType(), userName);

                em.persist(newFile);

                em.getTransaction().commit();
                return new MyReturn(0, "修改成功");
            }else {
                return new MyReturn(303, "修改失败,文件重名");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new MyReturn(404, "修改失败,后端有bug");
        }
    }


    // 删除文件
    public MyReturn deleteFile(String userName, String fileName, String pre_directory, String fileType){
        try{
            em.getTransaction().begin();
            String fileId = pre_directory + fileName;
            file file_ = em.find(file.class, fileId);

            //yunshaoxuan modify
            if(file_==null){
                return new MyReturn(404, "没有该文件");
            }
            em.remove(file_);
            em.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            return new MyReturn(404, "删除失败, 后端有bug");
        }
        return new MyReturn(0, "删除成功");
    }


    //查找文件
    public List<file> findFiles(String userName, String pre,String fileType){
        try{
            user u = em.find(user.class, userName);
            List<file> fileList = new ArrayList<>(u.getFileList());
            return fileList;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public List<file> findFile(String userName, String fileName, String pre,String fileType){
        try{
            user u = em.find(user.class, userName);
            List<file> fileList = new ArrayList<>(u.getFileList());
            for (int i = 0; i < fileList.size(); i++) {
                if(!fileList.get(i).getFileName().contains(fileName)){
                    fileList.remove(i);
                    i--;
                }
            }
            if(fileList.size()==0)
                return null;
            else
            return fileList;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public MyReturn logIn(String username, String pwd, String[] phone, Integer[] capacity){
        try{
            user u=em.find(user.class, username);
            if(u!=null){
                if(u.getPassword().equals(pwd)){
                    phone[0] = u.getPhone();
                    capacity[0] = u.getCapacity();
                    return new MyReturn(0, "登录成功");
                }
                else
                    return new MyReturn(302, "登录失败,密码错误");
            }else{
                return new MyReturn(303, "该账户不存在");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new MyReturn(404, "登录失败,系统后端有bug");
        }
    }

    public MyReturn signIn(String username,String pwd, String phone){
        user u=em.find(user.class,username);
        if(u==null){
            u=new user(username, pwd);
            u.setPhone(phone);
            try {
                em.getTransaction().begin();
                em.persist(u);
                em.getTransaction().commit();
            } catch (Exception e) {
                return new MyReturn(404, "注册失败,系统后端有bug");
            }
            return new MyReturn(0, "注册成功");
        }else
            return new MyReturn(303, "该用户名已被注册,请重新输入用户名");
    }

}
