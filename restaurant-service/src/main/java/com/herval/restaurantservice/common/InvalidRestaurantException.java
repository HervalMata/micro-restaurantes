package com.herval.restaurantservice.common;

public class InvalidRestaurantException extends Exception {

    private static final long serialVerionUID = -8890080495441147845L;

    private String message;
    private Object[] args;

    public InvalidRestaurantException(String arg) {
        this.message = String.format("%s is an invalid restaurant.", arg);
    }

    public InvalidRestaurantException(Object[] args) {
        this.args = args;
    }

    public InvalidRestaurantException(String message, Object[] args) {
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
