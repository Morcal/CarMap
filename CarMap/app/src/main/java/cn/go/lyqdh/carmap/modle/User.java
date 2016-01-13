package cn.go.lyqdh.carmap.modle;

import android.os.Parcel;
import android.os.Parcelable;

import com.baidu.mapapi.model.LatLng;

import cn.bmob.v3.BmobUser;

/**
 * Created by lyqdh on 2016/1/12.
 */
public class User extends BmobUser implements Parcelable {
    private int imgId;
    private int age;
    private String sex;
    private String Extra;
    private LatLng latLng; //经纬度
    private double price; //价格

    private String order1;
    private String order2;
    private String order3;
    private String order4;
    private String order5;

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
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

    public String getExtra() {
        return Extra;
    }

    public void setExtra(String extra) {
        Extra = extra;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getOrder1() {
        return order1;
    }

    public void setOrder1(String order1) {
        this.order1 = order1;
    }

    public String getOrder2() {
        return order2;
    }

    public void setOrder2(String order2) {
        this.order2 = order2;
    }

    public String getOrder3() {
        return order3;
    }

    public void setOrder3(String order3) {
        this.order3 = order3;
    }

    public String getOrder4() {
        return order4;
    }

    public void setOrder4(String order4) {
        this.order4 = order4;
    }

    public String getOrder5() {
        return order5;
    }

    public void setOrder5(String order5) {
        this.order5 = order5;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.imgId);
        dest.writeInt(this.age);
        dest.writeString(this.sex);
        dest.writeString(this.Extra);
        dest.writeParcelable(this.latLng, 0);
        dest.writeDouble(this.price);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.imgId = in.readInt();
        this.age = in.readInt();
        this.sex = in.readString();
        this.Extra = in.readString();
        this.latLng = in.readParcelable(LatLng.class.getClassLoader());
        this.price = in.readDouble();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
