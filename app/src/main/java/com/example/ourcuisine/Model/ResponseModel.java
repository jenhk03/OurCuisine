package com.example.ourcuisine.Model;

import java.util.List;

public class ResponseModel
{
    private String code, message;
    private List<CuisineModel> data;
    public String getCode()
    {
        return code;
    }
    public String getMessage()
    {
        return message;
    }
    public List<CuisineModel> getData()
    {
        return data;
    }
}