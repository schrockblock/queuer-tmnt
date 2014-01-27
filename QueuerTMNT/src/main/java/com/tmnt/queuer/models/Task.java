package com.tmnt.queuer.models;

import android.content.Context;

import com.tmnt.queuer.databases.TaskDataSource;

import java.sql.Date;

/**
 * Created by billzito on 1/19/14.
 */
public class Task {
    private int id;
    private int localId;
    private String name;
    private int project_id;
    private int order;
    private boolean finished;
    private Date created_at;
    private Date updated_at;

    public Task(){
    }

    public Task(Context context, int project_id, int id, String name) {
        this.project_id = project_id;
        this.id = id;
        this.name = name;

        TaskDataSource taskDataSource = new TaskDataSource(context);
        taskDataSource.open();
        this.localId = taskDataSource.createTask(name, project_id, id, order, finished).localId;
        taskDataSource.close();
    }

    public int getLocalId() {
        return localId;
    }

    public void setLocalId(int localId) {
        this.localId = localId;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public void updateTask(Context context){
        TaskDataSource taskDataSource = new TaskDataSource(context);
        taskDataSource.open();
        taskDataSource.updateTask(this);
        taskDataSource.close();
    }

    public void deleteTask(Context context){
        TaskDataSource taskDataSource = new TaskDataSource(context);
        taskDataSource.open();
        taskDataSource.deleteTask(this);
        taskDataSource.close();
    }
}
