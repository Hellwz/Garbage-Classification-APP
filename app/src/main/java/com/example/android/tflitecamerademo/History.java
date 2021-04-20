package com.example.android.tflitecamerademo;

class History {
    private String address;
    private String result;
    private long time;
    private int user_id;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "History{" +
                "address='" + address + '\'' +
                ", result='" + result + '\'' +
                ", time=" + time +
                ", user_id=" + user_id +
                '}';
    }
}
