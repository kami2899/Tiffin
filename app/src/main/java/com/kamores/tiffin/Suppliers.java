package com.kamores.tiffin;

public class Suppliers {
    String service_name;
    String day;
    String service_detail;
    String supplier_name;
    String supplier_contact;
    String supplier_location;
    String user_id;
    String supplier_id;

    public void setDay(String day) {
        this.day = day;
    }

    public void setSupplier_id(String supplier_id) {
        this.supplier_id = supplier_id;
    }

    public String getService_name() {
        return service_name;
    }

    public String getSupplier_name() {
        return supplier_name;
    }

    public String getSupplier_location() {
        return supplier_location;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public void setService_detail(String service_detail) {
        this.service_detail = service_detail;
    }

    public void setSupplier_name(String supplier_name) {
        this.supplier_name = supplier_name;
    }

    public void setSupplier_contact(String supplier_contact) {
        this.supplier_contact = supplier_contact;
    }

    public void setSupplier_location(String supplier_location) {
        this.supplier_location = supplier_location;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
