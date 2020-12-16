    private List<AndroidOS> getData(){
        InputStream is = getResources().openRawResource(R.raw.android_versions);
        InputStreamReader isr = new InputStreamReader(is);
        Gson gson = new Gson();
        Type listType = new TypeToken<List<AndroidOS>>(){}.getType();
        List<AndroidOS> oss = gson.fromJson(isr, listType);
        return oss;
    }
    
    public class AndroidOS implements Parcelable{
    public String name;
    public String version;
    public int sdk_int;
    public String description;
    public int year;
    public String image_url;
    public String icon_url;

    public AndroidOS(){}

    private AndroidOS(Parcel in){
        name = in.readString();
        version = in.readString();
        sdk_int = in.readInt();
        description = in.readString();
        year = in.readInt();
        image_url = in.readString();
        icon_url = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(version);
        dest.writeInt(sdk_int);
        dest.writeString(description);
        dest.writeInt(year);
        dest.writeString(image_url);
        dest.writeString(icon_url);
    }

    public static final Creator<AndroidOS> CREATOR = new Creator<AndroidOS>() {
        @Override
        public AndroidOS createFromParcel(Parcel source) {
            return new AndroidOS(source);
        }

        @Override
        public AndroidOS[] newArray(int size) {
            return new AndroidOS[size];
        }
    };

}
