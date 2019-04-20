package com.example.vibhu.moviesapp;

import java.util.List;

public class ProductionHeirarchy {
    List<productionBean> production_companies;

    public List<productionBean> getProduction_companies() {
        return production_companies;
    }

    public void setProduction_companies(List<productionBean> production_companies) {
        this.production_companies = production_companies;
    }

    public static class productionBean{
        int id;
        String logo_path;
        String name;
        String origin_country;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLogo_path() {
            return logo_path;
        }

        public void setLogo_path(String logo_path) {
            this.logo_path = logo_path;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOrigin_country() {
            return origin_country;
        }

        public void setOrigin_country(String origin_country) {
            this.origin_country = origin_country;
        }
    }
}
