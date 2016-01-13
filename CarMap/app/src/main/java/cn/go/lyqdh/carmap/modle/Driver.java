package cn.go.lyqdh.carmap.modle;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lyqdh on 2016/1/11.
 */
public class Driver implements Parcelable {
    private String name;
    private int age;
    private String sex;
    private String phone;
    private String QQ;
    private String weiXin;
    private int zan;
    private int comment;
    private int avator;

    public Driver(String name, int age, String sex, String phone, String QQ, String weiXin, int zan, int comment, int avator) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.phone = phone;
        this.QQ = QQ;
        this.weiXin = weiXin;
        this.zan = zan;
        this.comment = comment;
        this.avator = avator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQQ() {
        return QQ;
    }

    public void setQQ(String QQ) {
        this.QQ = QQ;
    }

    public String getWeiXin() {
        return weiXin;
    }

    public void setWeiXin(String weiXin) {
        this.weiXin = weiXin;
    }

    public int getZan() {
        return zan;
    }

    public void setZan(int zan) {
        this.zan = zan;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public int getAvator() {
        return avator;
    }

    public void setAvator(int avator) {
        this.avator = avator;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.age);
        dest.writeString(this.sex);
        dest.writeString(this.phone);
        dest.writeString(this.QQ);
        dest.writeString(this.weiXin);
        dest.writeInt(this.zan);
        dest.writeInt(this.comment);
        dest.writeInt(this.avator);
    }

    protected Driver(Parcel in) {
        this.name = in.readString();
        this.age = in.readInt();
        this.sex = in.readString();
        this.phone = in.readString();
        this.QQ = in.readString();
        this.weiXin = in.readString();
        this.zan = in.readInt();
        this.comment = in.readInt();
        this.avator = in.readInt();
    }

    public static final Creator<Driver> CREATOR = new Creator<Driver>() {
        public Driver createFromParcel(Parcel source) {
            return new Driver(source);
        }

        public Driver[] newArray(int size) {
            return new Driver[size];
        }
    };
}
