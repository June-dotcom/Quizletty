package edu.ucucite.quizlettyn.shareQuiztoUser;

public class UserQueryInfo {
    private int index;
    private String uid;
    private String name;
    private String email;
    private String profile_uri;
    private String provider_data;

    public UserQueryInfo(int index, String uid, String name, String email, String profile_uri, String provider_data) {
        this.index = index;
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.profile_uri = profile_uri;
        this.provider_data = provider_data;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfile_uri() {
        return profile_uri;
    }

    public void setProfile_uri(String profile_uri) {
        this.profile_uri = profile_uri;
    }

    public String getProvider_data() {
        return provider_data;
    }

    public void setProvider_data(String provider_data) {
        this.provider_data = provider_data;
    }
}