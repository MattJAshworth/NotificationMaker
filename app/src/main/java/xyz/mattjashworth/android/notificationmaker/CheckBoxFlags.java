package xyz.mattjashworth.android.notificationmaker;

public class CheckBoxFlags {

    private Boolean okFlag = false;
    private Boolean cancelFlag = false;
    private Boolean replyFlag = false;
    private Boolean noFlag = false;
    private Boolean yesFlag = false;


    public Boolean getOkFlag() {
        return okFlag;
    }

    public Boolean getCancelFlag() {
        return cancelFlag;
    }

    public Boolean getReplyFlag() {
        return replyFlag;
    }

    public Boolean getNoFlag() {
        return noFlag;
    }

    public Boolean getYesFlag() {
        return yesFlag;
    }

    public void setOkFlag(Boolean okFlag) {
        this.okFlag = okFlag;
    }

    public void setCancelFlag(Boolean cancelFlag) {
        this.cancelFlag = cancelFlag;
    }

    public void setNoFlag(Boolean noFlag) {
        this.noFlag = noFlag;
    }

    public void setReplyFlag(Boolean replyFlag) {
        this.replyFlag = replyFlag;
    }

    public void setYesFlag(Boolean yesFlag) {
        this.yesFlag = yesFlag;
    }
}
