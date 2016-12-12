package cn.xmrk.cookbook.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 作者：请叫我百米冲刺 on 2016/10/24 15:35
 * 邮箱：mail@hezhilin.cc
 */

public class CookBookInfo implements Parcelable {


    /**
     * count : 300
     * description : 5、用余油炒花椒粒（也可以放花椒粉），炒出香味后捞出不用，锅内放入姜末、鸡丁翻炒几下，放入辣椒段胡萝卜丁翻炒
     * fcount : 0
     * food : 鸡脯,胡萝卜,花生米,干红辣椒,葱姜蒜,花椒粒,料酒,老抽,生抽,红油,淀粉
     * id : 106981
     * images :
     * img : /cook/080421/8c3be5b5a46ca73d9594cf3e333740d1.jpg
     * keywords : 花生米 淀粉 胡萝卜 放入 翻炒
     * message : <h2>材料 </h2><hr>
     * <p>鸡脯300克，胡萝卜1根，花生米100克，干红辣椒、葱姜蒜、花椒粒、料酒、盐、老抽、生抽、醋、白糖、红油、淀粉、植物油</p>   <h2>做法 </h2><hr>
     * <p>1、鸡脯洗净切丁，用盐、料酒、干淀粉抓匀腌制10分钟。用干淀粉可以使肉质更加滑嫩。 </p>
     * <p>2、胡萝卜、葱蒜（蒜苗）切丁，姜切末，干红辣椒切小段。 </p>
     * <p>3、用料酒、生抽、醋、干淀粉、红油各1小勺，白糖、老抽各半小勺，加适量水调汁。红油是用来提色提味的。 </p>
     * <p>4、热锅凉油，炸花生米，盛出。用熟花生米更方便些。 </p>
     * <p>5、用余油炒花椒粒（也可以放花椒粉），炒出香味后捞出不用，锅内放入姜末、鸡丁翻炒几下，放入辣椒段胡萝卜丁翻炒。我用的油较少，这一步加了半杯水，因为腌制鸡丁时加了淀粉，容易糊锅。油多的话就不存在这个问题。 </p>
     * <p>6、放入葱、蒜或蒜苗丁翻炒几下，烹入兑好的汁，翻炒，关火，最后放入炸好的花生米，炒匀出锅。</p>
     * name : 宫保鸡丁
     * rcount : 0
     */

    private int count;
    private String description;
    private int fcount;
    private String food;
    private int id;
    private String images;
    private String img;
    private String keywords;
    private String message;
    private String name;
    private int rcount;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFcount() {
        return fcount;
    }

    public void setFcount(int fcount) {
        this.fcount = fcount;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRcount() {
        return rcount;
    }

    public void setRcount(int rcount) {
        this.rcount = rcount;
    }

    public static Type getListType() {
        return new TypeToken<List<CookBookInfo>>() {
        }.getType();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.count);
        dest.writeString(this.description);
        dest.writeInt(this.fcount);
        dest.writeString(this.food);
        dest.writeInt(this.id);
        dest.writeString(this.images);
        dest.writeString(this.img);
        dest.writeString(this.keywords);
        dest.writeString(this.message);
        dest.writeString(this.name);
        dest.writeInt(this.rcount);
    }

    public CookBookInfo() {
    }

    protected CookBookInfo(Parcel in) {
        this.count = in.readInt();
        this.description = in.readString();
        this.fcount = in.readInt();
        this.food = in.readString();
        this.id = in.readInt();
        this.images = in.readString();
        this.img = in.readString();
        this.keywords = in.readString();
        this.message = in.readString();
        this.name = in.readString();
        this.rcount = in.readInt();
    }

    public static final Parcelable.Creator<CookBookInfo> CREATOR = new Parcelable.Creator<CookBookInfo>() {
        @Override
        public CookBookInfo createFromParcel(Parcel source) {
            return new CookBookInfo(source);
        }

        @Override
        public CookBookInfo[] newArray(int size) {
            return new CookBookInfo[size];
        }
    };
}
