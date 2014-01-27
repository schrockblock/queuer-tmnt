package com.tmnt.queuer.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SyncAdapterType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tmnt.queuer.R;
import com.tmnt.queuer.activities.FeedActivity;
import com.tmnt.queuer.activities.ProjectActivity;
import com.tmnt.queuer.interfaces.RearrangementListener;
import com.tmnt.queuer.models.Task;

import java.util.ArrayList;

/**
 * Created by billzito on 1/19/14.
 */

public class ProjectAdapter extends BaseAdapter implements RearrangementListener {
    private Context context;
    private ArrayList<Task> tasks = new ArrayList<Task>();
    private ArrayList<Task> finishedTasks = new ArrayList<Task>();

    public ProjectAdapter(Context context, ArrayList<Task> tasks) {
        this.context = context;
        this.tasks = tasks;
    }

    public void remove(int position) {
        Task tempTask = tasks.remove(position);
        tempTask.setFinished(true);
        finishedTasks.add(0, tempTask);
        tempTask.deleteTask(context);

        notifyDataSetChanged();
        if (tasks.isEmpty()){
            Intent goToFeed = new Intent(context, FeedActivity.class);
            goToFeed.putExtra("Project_Id", tempTask.getProject_id() + "");
            context.startActivity(goToFeed);
        }
    }

    public void insert(Task task, int position){
        tasks.add(position, task);
        if (!tasks.isEmpty()){
            ((ProjectActivity)context).hide_empty_tasks();
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public Task getItem(int position) {
        Log.e("positionId: ", tasks.get(position).getId()+"");
        Log.e("positionName: ", tasks.get(position).getName()+"");
        Log.e("positionPosition: ", position + "");

        return tasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.list_task, null);
        }

        ((TextView)convertView.findViewById(R.id.tv_title)).setText(getItem(position).getName());

        return convertView;
    }

    @Override
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    @Override
    public void onStartedRearranging() {

    }

    @Override
    public void swapElements(int indexOne, int indexTwo) {
        Task temp1 = tasks.get(indexOne);
        Task temp2 = tasks.get(indexTwo);

        tasks.remove(indexOne);
        tasks.add(indexOne, temp2);

        tasks.remove(indexTwo);
        tasks.add(indexTwo, temp1);
    }

    @Override
    public void onFinishedRearranging() {

    }
}
