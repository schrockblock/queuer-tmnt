package com.tmnt.queuer.adapters;

/**
 * Created by billzito on 1/18/14.
 */


        import android.content.Context;
        import android.util.Log;
        import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tmnt.queuer.R;
        import com.tmnt.queuer.activities.FeedActivity;
        import com.tmnt.queuer.databases.ProjectDataSource;
        import com.tmnt.queuer.interfaces.RearrangementListener;
import com.tmnt.queuer.models.Project;

import java.util.ArrayList;


public class FeedAdapter extends BaseAdapter implements RearrangementListener{
    private Context context;
    private ArrayList<Project> projects = new ArrayList<Project>();

    public FeedAdapter(Context context, ArrayList<Project> projects) {
        this.context = context;
        this.projects = projects;
    }

    public void remove(int position) {
        Project oldProject = projects.remove(position);
        if (projects.isEmpty()) {
            ((FeedActivity)context).show_empty_project();
        }
        oldProject.deleteProject(context);
        notifyDataSetChanged();
    }

    public void insert(Project project, int position){
        projects.add(position, project);
        notifyDataSetChanged();
        if (!projects.isEmpty()) {
            ((FeedActivity)context).hide_empty_project();
        }
    }

    @Override
    public int getCount() {
        return projects.size();
    }

    @Override
    public Project getItem(int position) {

        return projects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    public long getColor(int position){
        return getItem(position).getColor();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.list_project, null);
        }

        ((TextView)convertView.findViewById(R.id.tv_title)).setText(getItem(position).getFeedTitle());
        convertView.findViewById(R.id.ll_project).setBackgroundColor(getItem(position).getColor());

        return convertView;
    }

    @Override
    public boolean isEmpty() {
        return projects.isEmpty();
    }

    @Override
    public void onStartedRearranging() {

    }

    @Override
    public void swapElements(int indexOne, int indexTwo) {
        Project temp1 = projects.get(indexOne);
        Project temp2 = projects.get(indexTwo);

        projects.remove(indexOne);
        projects.add(indexOne, temp2);

        projects.remove(indexTwo);
        projects.add(indexTwo, temp1);
    }

    @Override
    public void onFinishedRearranging() {

    }
}