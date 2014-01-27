package com.tmnt.queuer.models;

import android.content.Context;

import com.tmnt.queuer.databases.ProjectDataSource;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by billzito on 1/18/14.
 */
public class Project {
        private int id;
        private String name;
        private int color;
        private int localId;
        private ArrayList<Task> tasks;
        private Date created_at;
        private Date updated_at;


    public int getLocalId() {
        return localId;
    }

    public void setLocalId(int localId) {
        this.localId = localId;
        //new ProjectDataSource(context).updateProject(this);
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
       // new ProjectDataSource(context).updateProject(this);
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
      //  new ProjectDataSource(context).updateProject(this);
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
      //  new ProjectDataSource(context).updateProject(this);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        //new ProjectDataSource(context).updateProject(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
       // new ProjectDataSource(context).updateProject(this);
    }
        public Project(Context context, int id, String name) {
            this.id = id;
            this.name = name;
            ProjectDataSource projectDataSource = new ProjectDataSource(context);
            projectDataSource.open();
            localId = projectDataSource.createProject(name, color, id, new Date(), new Date()).localId;
            projectDataSource.close();
        }

        public Project(){

        }

        public static Project syncProject(Project serverProject, Project localProject){
            //TODO make this not naiive
            if (serverProject.getUpdated_at().after(localProject.getUpdated_at())){
                serverProject.setLocalId(localProject.localId);
                return serverProject;
            }

            return localProject;
        }



        public void deleteProject(Context context){
            ProjectDataSource projectDataSource = new ProjectDataSource(context);
            projectDataSource.open();
            projectDataSource.deleteProject(this);
            projectDataSource.close();
        }

    public void updateProject(Context context){
        ProjectDataSource projectDataSource = new ProjectDataSource(context);
        projectDataSource.open();
        projectDataSource.updateProject(this);
        projectDataSource.close();
    }
}
