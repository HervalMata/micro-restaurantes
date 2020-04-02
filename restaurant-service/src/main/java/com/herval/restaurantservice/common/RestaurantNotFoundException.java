package com.herval.restaurantservice.common;

public class RestaurantNotFoundException extends Exception {

    private static final long serialVerionUID = -8890080495441147845L;

    private String message;
    private Object[] args;

    public RestaurantNotFoundException(String message) {
        this.message = String.format("User %s is not found.", message);
    }

    public RestaurantNotFoundException(Object[] args) {
        this.args = args;
    }

    public RestaurantNotFoundException(String message, Object[] args) {
        this.message = message;
        this.args = args;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}
