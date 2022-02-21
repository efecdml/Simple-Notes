package org.efecdml.simplenotes;

public class NotesModel {

    private int id;
    private String title;
    private String content;
    private String createdOrModifiedDate;

    public NotesModel(int id, String title, String content, String createdOrModifiedDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdOrModifiedDate = createdOrModifiedDate;
    }

    @Override
    public String toString() {
        return "NotesModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createdOrModifiedDate='" + createdOrModifiedDate + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedOrModifiedDate() {
        return createdOrModifiedDate;
    }

    public void setCreatedOrModifiedDate(String createdOrModifiedDate) {
        this.createdOrModifiedDate = createdOrModifiedDate;
    }
}
