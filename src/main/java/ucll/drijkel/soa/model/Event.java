package ucll.drijkel.soa.model;

public class Event {

    private String source,status;
    private int limit = -1,days = -1;

    public Event() {}

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        if (source.equals(""))
            this.source = null;
        else
            this.source = source;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (status.equals("")){
            this.status = null;
        }
        else if (status.equals("open") || status.equals("closed")){
            this.status = status;
        }
        else{
            throw new RestException("status must be 'closed' or 'open'");
        }
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }
}
