package com.example.featuretogglelibrary.model;

public class FeatureToggleItem {

    private String _id;
    private String beginning_date;
    private String created_at;
    private String description;
    private String expiration_date;
    private String name;
    private String updated_at;
    private String package_name;

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public FeatureToggleItem() {

    }

    public FeatureToggleItem(String beginning_date, String created_at, String description, String expiration_date,
                             String name, String updated_at,String package_name) {
        this.beginning_date = beginning_date;
        this.created_at = created_at;
        this.description = description;
        this.expiration_date = expiration_date;
        this.name = name;
        this.updated_at = updated_at;
        this.package_name = package_name;
    }

    public String get_id() {
        return _id;
    }

    public String getBeginning_date() {
        return beginning_date;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getDescription() {
        return description;
    }

    public String getExpiration_date() {
        return expiration_date;
    }

    public String getName() {
        return name;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setBeginning_date(String beginning_date) {
        this.beginning_date = beginning_date;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setExpiration_date(String expiration_date) {
        this.expiration_date = expiration_date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return  "FeatureToggleItem:\n" +
                "name: " + name + '\n' +
                "description: " + description + '\n' +
                "beginningDate: " + beginning_date + '\n' +
                "expirationDate: " + expiration_date + '\n' +
                "createdAt=: " + created_at + '\n' +
                "updatedAt=: " + updated_at + '\n' ;
    }
}
