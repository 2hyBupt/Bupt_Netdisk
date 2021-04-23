package entity;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
public class user {

    @Id
    @Column(name = "userName")
    private String username = "";

    @Column(name = "password")
    private String password = "";


    @Column(name = "capacity")
    private Integer capacity = 50;

    @Column(name = "phone")
    private String phone = "";


    public List<file> getFileList() {
        return fileList;
    }

    public void setFileList(List<file> fileList) {
        this.fileList = fileList;
    }

    @OneToMany(targetEntity = file.class)
    @JoinColumn(name = "fileUserName", referencedColumnName = "userName")
    private List<file> fileList = new ArrayList<>();


    public user(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public user() {
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
