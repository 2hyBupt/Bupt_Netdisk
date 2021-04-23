package entity;
import javax.persistence.*;

@Entity
@Table(name = "file")
public class file {

    public String getF_id() {
        return f_id;
    }

    public void setF_id(String f_id) {
        this.f_id = f_id;
    }

    @Id
    @Column(name = "fileId")
    private String f_id = "";

    @Column(name = "fileName")
    private String fileName = "";

    @Column(name = "pre_directory")
    private String pre_directory = "";

    @Column(name = "type")
    private String type = "";

    @Column(name = "md5")
    private String md5 = "";

    @Column(name = "fileUserName")
    private String fileUserName = "";




    public file(){}

    public file(String fileId, String fileName, String pre_directory, String type, String userName){
        this.f_id = fileId;
        this.fileName = fileName;
        this.pre_directory = pre_directory;
        this.type = type;
        this.fileUserName = userName;
    }




    public String getFileUserName() {
        return fileUserName;
    }

    public void setFileUserName(String userName) {
        this.fileUserName = userName;
    }


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPre_directory() {
        return pre_directory;
    }

    public void setPre_directory(String pre_directory) {
        this.pre_directory = pre_directory;
    }

    public String getType() {
        return type;
    }

    public void setType(String tyep) {
        this.type = tyep;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }
}