package com.api.auto.autoproduct.result;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ApiInfo {


    private ArrayList<List> outputs;
    private ArrayList<List> params;
    private ArrayList<List> list;
    private String apiname;
    private Api api;

}
