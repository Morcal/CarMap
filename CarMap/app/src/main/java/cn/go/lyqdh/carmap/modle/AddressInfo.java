package cn.go.lyqdh.carmap.modle;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import cn.go.lyqdh.carmap.R;

/**
 * Created by lyqdh on 2016/1/11.
 */
public class AddressInfo implements Parcelable {

    private double latitude;
    private double longitude;
    private Driver driver;

    public static List<AddressInfo> infos = new ArrayList<AddressInfo>();

    static {
        // 模拟坐标数据
        // String name, int age, String sex, String phone, String QQ, String weiXin, int zan, int comment
        infos.add(new AddressInfo(24.627944, 118.267592, new Driver("xyz", 21, "man", "12345678", "123455678", "12345678", 23, 34, R.drawable.avator)));
        infos.add(new AddressInfo(24.626893, 118.235397, new Driver("qwe", 21, "man", "12345678", "123455678", "12345678", 23, 34, R.drawable.avator)));
        infos.add(new AddressInfo(24.610074, 118.235972, new Driver("tyr", 21, "man", "12345678", "123455678", "12345678", 23, 34, R.drawable.avator)));
    }

    public AddressInfo(double latitude, double longitude, Driver driver) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.driver = driver;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public static List<AddressInfo> getInfos() {
        return infos;
    }

    public static void setInfos(List<AddressInfo> infos) {
        AddressInfo.infos = infos;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
        dest.writeParcelable(this.driver, flags);
    }

    protected AddressInfo(Parcel in) {
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.driver = in.readParcelable(Driver.class.getClassLoader());
    }

    public static final Creator<AddressInfo> CREATOR = new Creator<AddressInfo>() {
        public AddressInfo createFromParcel(Parcel source) {
            return new AddressInfo(source);
        }

        public AddressInfo[] newArray(int size) {
            return new AddressInfo[size];
        }
    };
}
