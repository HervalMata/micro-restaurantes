package com.herval.userservice.common;

public class DuplicateUserException extends Exception {

    private static final long serialVerionUID = -8890080495441147845L;

    private String message;
    private Object[] args;

    public DuplicateUserException(String arg) {
        this.message = String.format("%s is an invalid user.", arg);
    }

    public DuplicateUserException(Object[] args) {
        this.args = args;
    }

    public DuplicateUserException(String message, Object[] args) {
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
