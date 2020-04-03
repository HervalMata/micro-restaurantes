package com.herval.booking.common;

public class DuplicateBookingException extends Exception {

    private static final long serialVerionUID = -8890080495441147845L;

    private String message;
    private Object[] args;

    public DuplicateBookingException(String arg) {
        this.message = String.format("There is already a booking with the name - %s", arg);
    }

    public DuplicateBookingException(Object[] args) {
        this.args = args;
    }

    public DuplicateBookingException(String message, Object[] args) {
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
