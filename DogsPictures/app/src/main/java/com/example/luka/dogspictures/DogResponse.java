package com.example.luka.dogspictures;

import java.util.List;

public class DogResponse  {

    private boolean success;

    private List<String> message;


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success=success;
    }

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }
}
