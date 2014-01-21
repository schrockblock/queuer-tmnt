package com.tmnt.queuer.models;

/**
 * Created by billzito on 1/18/14.
 */
public class Project {
        private int id;
        private String title;
        private int color;

        public Project(int id, String title) {
            this.id = id;
            this.title = title;
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

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

}
