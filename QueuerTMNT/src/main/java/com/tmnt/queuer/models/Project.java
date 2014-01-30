package com.tmnt.queuer.models;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.tmnt.queuer.Constants;
import com.tmnt.queuer.databases.ProjectDataSource;
import com.tmnt.queuer.databases.TaskDataSource;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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



    public void setTasks(Context context) {
        tasks = new ArrayList<Task>();
        TaskDataSource taskDataSource = new TaskDataSource(context);
        taskDataSource.open();
        ArrayList<Task> allTasks = taskDataSource.getAllTasks();
        for(Task currentTask : allTasks){
            if (currentTask.getProject_id() == id){
                tasks.add(currentTask);
            }
        }


        taskDataSource.close();

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
        setTasks(context);
        // Puts projects in server. Works, but we haven't done the rest so we comment it out
        /**
        JSONObject jsonObject = null;
        final Context context1 = context;
        try{
            jsonObject = new JSONObject(new Gson().toJson(Project.this));
        } catch (Exception e){
            Log.e("ErrorPostingNewProject", e.toString());
        }

        String url = Constants.QUEUER_CREATE_ACCOUNT_URL;
        SharedPreferences sharedPreferences = context.getSharedPreferences("login", Activity.MODE_PRIVATE);
        url += "/";
        url += sharedPreferences.getString("id", "Default");
        url += "/projects";
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonObject,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = context1.getSharedPreferences("login", Activity.MODE_PRIVATE);
                String api_key = sharedPreferences.getString("api_key", "Default");

                Map headers = new HashMap();
                headers.put("X-Qer-Authorization", api_key);
                return headers;
            }
        };
        Volley.newRequestQueue(context.getApplicationContext()).add(request);

*/
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

    public String getFeedTitle(){
        //todo make not ugly if necessary
        if (tasks.isEmpty()){
            return name;
        } else{
            return name + " :\n\t" + tasks.get(0).getName();
        }
    }

    public void setFeedTitle(){
    }

    public Task dismissFirstTask(Context context){
        if (!tasks.isEmpty()){
            TaskDataSource taskDataSource = new TaskDataSource(context);
            taskDataSource.open();
            Task tempTask = tasks.remove(0);
            taskDataSource.deleteTask(tempTask);
            taskDataSource.close();
            getFeedTitle();
            return tempTask;
        }
        return null;
    }

    public void undoDismissTask(Context context, Task tempTask){
        TaskDataSource taskDataSource = new TaskDataSource(context);
        taskDataSource.open();
        tasks.add(0, tempTask);

        tempTask.setLocalId(taskDataSource.createTask(tempTask.getName(), tempTask.getProject_id(), tempTask.getId(), tempTask.getOrder(), tempTask.isFinished()).getLocalId());

        taskDataSource.updateTask(tempTask);
        taskDataSource.close();
        getFeedTitle();
    }
}

