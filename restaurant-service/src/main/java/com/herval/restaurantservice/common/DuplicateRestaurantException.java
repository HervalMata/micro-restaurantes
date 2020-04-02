package com.herval.restaurantservice.common;

public class DuplicateRestaurantException extends Exception {

    private static final long serialVerionUID = -8890080495441147845L;

    private String message;
    private Object[] args;

    public DuplicateRestaurantException(String arg) {
        this.message = String.format("There is already a restaurant with the name - %s", arg);
    }

    public DuplicateRestaurantException(Object[] args) {
        this.args = args;
    }

    public DuplicateRestaurantException(String message, Object[] args) {
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
