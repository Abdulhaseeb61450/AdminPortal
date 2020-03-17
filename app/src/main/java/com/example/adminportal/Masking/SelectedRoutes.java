package com.example.adminportal.Masking;

public class SelectedRoutes {

    String SelectedRouteKey, SelectectedRouteValue, SelectedCode;

    public SelectedRoutes() {
    }

    public String getSelectedRouteKey() {
        return SelectedRouteKey;
    }

    public void setSelectedRouteKey(String SelectedRouteKey) {
        this.SelectedRouteKey = SelectedRouteKey;
    }

    public String getSelectectedRouteValue() {
        return SelectectedRouteValue;
    }

    public void setSelectectedRouteValue(String SelectectedRouteValue) {
        this.SelectectedRouteValue = SelectectedRouteValue;
    }

    public String getSelectedCode() {
        return SelectedCode;
    }

    public void setSelectedCode(String SelectedCode) {
        this.SelectedCode = SelectedCode;
    }

    @Override
    public String toString () {
        return SelectectedRouteValue;
    }
}
