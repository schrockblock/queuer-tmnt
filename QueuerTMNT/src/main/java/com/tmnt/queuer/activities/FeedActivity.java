package com.tmnt.queuer.activities;

/**
 * Created by billzito on 1/18/14.
 */
    import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.tmnt.queuer.R;
import com.tmnt.queuer.adapters.FeedAdapter;
import com.tmnt.queuer.models.Project;
import com.tmnt.queuer.views.EnhancedListView;

import java.util.ArrayList;


    public class FeedActivity extends ActionBarActivity {
        private FeedAdapter adapter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_feed);

            ArrayList<Project> projects = new ArrayList<Project>(20);
            for (int i = 0; i < 20; i++){
                projects.add(new Project(i, "Project " + i));
            }

            Intent previousIntent = getIntent();
            String Project_Id = previousIntent.getStringExtra("Project_Id");

            if (Project_Id != null) {
                for(Project proj : projects){
                    if (Project_Id.equals("" + proj.getId())){
                        projects.remove(proj);
                        break;
                    }
                }

            }


            TextView no_projects = (TextView)findViewById(R.id.lv_no_project);
            no_projects.setVisibility(View.INVISIBLE);

            if (projects.isEmpty()) {
                no_projects.setVisibility(View.VISIBLE);
            }

            EnhancedListView listView = (EnhancedListView)findViewById(R.id.lv_projects);
            adapter = new FeedAdapter(this, projects);
            listView.setAdapter(adapter);



            listView.getViewForID(213).setVisibility(View.GONE);


            listView.setDismissCallback(new EnhancedListView.OnDismissCallback() {
                @Override
                public EnhancedListView.Undoable onDismiss(EnhancedListView listView, final int position) {
                    final Project project = adapter.getItem(position);
                    adapter.remove(position);
                    return new EnhancedListView.Undoable() {
                        @Override
                        public void undo() {
                            adapter.insert(project, position);
                        }
                    };
                }
            });

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(FeedActivity.this, ProjectActivity.class);
                    intent.putExtra("project_id", (int)adapter.getItemId(position));
                    startActivity(intent);
                }
            });

            listView.enableSwipeToDismiss();
            listView.enableRearranging();
        }

    }

