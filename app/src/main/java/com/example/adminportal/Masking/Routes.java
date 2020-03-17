package com.example.adminportal.Masking;

public class Routes {

        String RouteKey, RouteValue;

        public Routes() {
        }

        public String getRouteKey() {
            return RouteKey;
        }

        public void setRouteKey(String RouteKey) {
            this.RouteKey = RouteKey;
        }

        public String getRouteValue() {
            return RouteValue;
        }

        public void setRouteValue(String RouteValue) {
            this.RouteValue = RouteValue;
        }


        @Override
        public String toString () {
            return RouteValue;
        }
    }
