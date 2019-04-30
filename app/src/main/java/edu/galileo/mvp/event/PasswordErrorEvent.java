package edu.galileo.mvp.event;

public class PasswordErrorEvent {

    private int messageResId;

    //delete this
    public PasswordErrorEvent() {

    }
    //

    public PasswordErrorEvent(int messageResId) {
        this.messageResId = messageResId;
    }

    public int getMessageResId() {
        return messageResId;
    }
}
