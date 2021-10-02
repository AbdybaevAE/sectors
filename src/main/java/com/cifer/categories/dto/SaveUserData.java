package com.cifer.categories.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Data that is gonna be rendered in user form and passed to service.
 */
@Data
public class SaveUserData {
    private String firstName;
    private boolean agreement;
    private List<String> sectors = new ArrayList<>();

}
