package com.tmnt.queuer.activities;

/**
 * Created by billzito on 1/18/14.
 */
    import android.app.AlertDialog;
    import android.app.Dialog;
    import android.content.DialogInterface;
    import android.content.Intent;
    import android.graphics.Color;
    import android.os.Bundle;
    import android.support.v7.app.ActionBarActivity;
    import android.view.Menu;
    import android.view.MenuItem;
    import android.view.View;
    import android.widget.AdapterView;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.TextView;

    import com.tmnt.queuer.R;
    import com.tmnt.queuer.adapters.FeedAdapter;
    import com.tmnt.queuer.models.Project;
    import com.tmnt.queuer.models.Task;
    import com.tmnt.queuer.views.EnhancedListView;

import java.util.ArrayList;


    public class FeedActivity extends ActionBarActivity {
        private FeedAdapter adapter;
        private ArrayList<Project> projects;
        private int lastColor;
        private TextView no_projects;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_feed);

            projects = new ArrayList<Project>(20);
            for (int i = 0; i < 2; i++){
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


            no_projects = (TextView)findViewById(R.id.lv_no_project);
            no_projects.setVisibility(View.INVISIBLE);

            if (projects.isEmpty()) {
                no_projects.setVisibility(View.VISIBLE);
            }

            EnhancedListView listView = (EnhancedListView)findViewById(R.id.lv_projects);
            adapter = new FeedAdapter(this, projects);
            listView.setAdapter(adapter);



            //listView.getViewForID(213).setVisibility(View.GONE);


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

        public void show_empty_project(){
            no_projects.setVisibility(View.VISIBLE);
        }

        public void hide_empty_project(){
            no_projects.setVisibility(View.INVISIBLE);
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {

            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.feed, menu);
            return true;
        }

        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.

            int id = item.getItemId();
            if (id == R.id.action_add_project) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            // set title
            alertDialogBuilder.setTitle("New Project");

            final View layout = getLayoutInflater().inflate(R.layout.new_project, null);

            final EditText projectTitle = (EditText)layout.findViewById(R.id.projectName);

                Button red = (Button)layout.findViewById(R.id.btn_red);
                Button blue = (Button)layout.findViewById(R.id.btn_blue);
                Button green = (Button)layout.findViewById(R.id.btn_green);
                Button yellow = (Button)layout.findViewById(R.id.btn_yellow);
                Button plum = (Button)layout.findViewById(R.id.btn_plum);
                Button orange = (Button)layout.findViewById(R.id.btn_orange);
                Button turquoise = (Button)layout.findViewById(R.id.btn_turquoise);

                red.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lastColor = Color.RED;
                        layout.findViewById(R.id.color_swatch).setBackgroundColor(lastColor);
                    }

                });

                blue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lastColor = Color.BLUE;
                        layout.findViewById(R.id.color_swatch).setBackgroundColor(lastColor);
                    }
                });

                green.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lastColor = Color.rgb(0,128,0 );
                        layout.findViewById(R.id.color_swatch).setBackgroundColor(lastColor);
                    }
                });

                yellow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lastColor = Color.YELLOW;
                        layout.findViewById(R.id.color_swatch).setBackgroundColor(lastColor);
                    }

                });

                plum.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lastColor = Color.rgb(221,160,221);
                        layout.findViewById(R.id.color_swatch).setBackgroundColor(lastColor);
                    }
                });

                orange.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lastColor = Color.rgb(255, 165, 0);
                        layout.findViewById(R.id.color_swatch).setBackgroundColor(lastColor);
                    }
                });

                turquoise.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lastColor = Color.rgb(64, 224, 208);
                        layout.findViewById(R.id.color_swatch).setBackgroundColor(lastColor);
                    }
                });


            // set dialog message
            alertDialogBuilder
                    //.setMessage(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)))
                    .setCancelable(true)
                    .setView(layout)


                    .setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    final Project project = new Project(id, projectTitle.getText().toString());
                                    project.setId(id);
                                    project.setTitle(projectTitle.getText().toString());

                                    project.setColor(lastColor);
                                    projects.add(0, project);
                                    adapter.notifyDataSetChanged();
                                }
                            })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });


            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            return true;
        }
            return super.onOptionsItemSelected(item);
    }
 }