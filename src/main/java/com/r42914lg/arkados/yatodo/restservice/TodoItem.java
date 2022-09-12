package com.r42914lg.arkados.yatodo.restservice;

public class TodoItem {

    private int localid;
    private String text;
    private int importance;
    private boolean done;
    private long created;
    private String deadline;
    private long changed;
    private boolean deletepending;

    public TodoItem(
        int localid,
        String text,
        int importance,
        boolean done,
        long created,
        String deadline,
        long changed,
        boolean deletepending
    ) {
        this.localid = localid;
        this.text = text;
        this.importance = importance;
        this.done = done;
        this.created = created;
        this.deadline = deadline;
        this.changed = changed;
        this.deletepending = deletepending;
    }

    public int getLocalid() { return localid; }
    public String getText() { return text; }
    public int getImportance() { return importance; }
    public boolean getDone() { return done; }
    public long getCreated() { return created; }
    public String getDeadline() { return deadline; }
    public long getChanged() { return changed; }
    public boolean getDeletepending() { return deletepending; }

    public void setLocalid(int localid) { this.localid = localid; }
    public void setText(String text) { this.text = text; }
    public void setImportance(int importance) { this.importance = importance; }
    public void setDone(boolean done) { this.done = done; }
    public void setCreated(long created) { this.created = created; }
    public void setDeadline(String deadline) { this.deadline = deadline; }
    public void setChanged(long changed) { this.changed = changed; }
    public void setDeletepending(boolean deletepending) { this.deletepending = deletepending; }

    public void updateFrom(TodoItem newItem) {
        changed = newItem.changed;
        text = newItem.text;
        importance = newItem.importance;
        done = newItem.done;
        deadline = newItem.deadline;
        deletepending = newItem.deletepending;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof TodoItem))
            return false;

        return ((TodoItem) other).localid == localid;    
    }

    @Override
    public String toString() {
        return "{ " + localid + ", " +  importance + ", " + done +  ", " + created + ", " + deadline
            + ", " +  changed + ", " + deletepending +  ", " + text + " }";
    } 
    
}
