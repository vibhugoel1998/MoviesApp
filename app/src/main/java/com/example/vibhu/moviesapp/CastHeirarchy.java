package com.example.vibhu.moviesapp;

import java.util.List;

public class CastHeirarchy {
    int id;
    List<CastBean> cast;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<CastBean> getCast() {
        return cast;
    }

    public void setCast(List<CastBean> cast) {
        this.cast = cast;
    }

    public static class CastBean
    {
        int cast_id;
        String character;
        String credit_id;
        String gender;
        int id;
        String name;
        int order;
        String profile_path;

        public int getCast_id() {
            return cast_id;
        }

        public void setCast_id(int cast_id) {
            this.cast_id = cast_id;
        }

        public String getCharacter() {
            return character;
        }

        public void setCharacter(String character) {
            this.character = character;
        }

        public String getCredit_id() {
            return credit_id;
        }

        public void setCredit_id(String credit_id) {
            this.credit_id = credit_id;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
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

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public String getProfile_path() {
            return profile_path;
        }

        public void setProfile_path(String profile_path) {
            this.profile_path = profile_path;
        }
    }
}
