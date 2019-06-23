package com.example.desk.been;

/**
 * Created by Credit on 2017/2/28.
 */

public class UserBean {
    /**
     * id : 2
     * userid : 2016021051
     * college : 计算机与控制工程学院
     * classs : 软件161
     * password : 25f9e794323b453885f5181f1b624d0b
     * birthday : 1998-10-23
     * email : 13608428279@163.com
     * gender : 男
     * userlogo : http://172.16.63.128:8080/photo/2019_04_12_12_04_954.png
     */

    private int id;
    private String userid;
    private String college;
    private String classs;
    private String password;
    private String birthday;
    private String email;
    private String gender;
    private String userlogo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getClasss() {
        return classs;
    }

    public void setClasss(String classs) {
        this.classs = classs;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUserlogo() {
        return userlogo;
    }

    public void setUserlogo(String userlogo) {
        this.userlogo = userlogo;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "id=" + id +
                ", userid='" + userid + '\'' +
                ", college='" + college + '\'' +
                ", classs='" + classs + '\'' +
                ", password='" + password + '\'' +
                ", birthday='" + birthday + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", userlogo='" + userlogo + '\'' +
                '}';
    }

    ////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////
    /**
     * Id : 1
     * Name : 1
     * Mobile : 1
     * Sex : false
     * Age : 0
     * Birth : 2017-02-28 13:36:15
     * Address : 广州市天河区
     * Pwd : 123456
     * Pic : /static/img/default.jpg
     */
/*
    private int Id;
    private String Name;
    private String Mobile;
    private boolean Sex;
    private int Age;
    private String Birth;
    private String Address;
    private String Pwd;
    private String Pic;

    public UserBean() {
    }

    public UserBean(int id, String name, String mobile, boolean sex, int age, String birth, String address, String pwd, String pic) {
        Id = id;
        Name = name;
        Mobile = mobile;
        Sex = sex;
        Age = age;
        Birth = birth;
        Address = address;
        Pwd = pwd;
        Pic = pic;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String Mobile) {
        this.Mobile = Mobile;
    }

    public boolean isSex() {
        return Sex;
    }

    public void setSex(boolean Sex) {
        this.Sex = Sex;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int Age) {
        this.Age = Age;
    }

    public String getBirth() {
        return Birth;
    }

    public void setBirth(String Birth) {
        this.Birth = Birth;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getPwd() {
        return Pwd;
    }

    public void setPwd(String Pwd) {
        this.Pwd = Pwd;
    }

    public String getPic() {
        return Pic;
    }

    public void setPic(String Pic) {
        this.Pic = Pic;
    }

    @Override
    public String toString() {
        return "User{" +
                "Id=" + Id +
                ", Name='" + Name + '\'' +
                ", Mobile='" + Mobile + '\'' +
                ", Sex=" + Sex +
                ", Age=" + Age +
                ", Birth='" + Birth + '\'' +
                ", Address='" + Address + '\'' +
                ", Pwd='" + Pwd + '\'' +
                ", Pic='" + Pic + '\'' +
                '}';
    }

*/
}
