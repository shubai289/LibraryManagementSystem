package com.librarySys.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    private int id;
    private String bookName;
    private String author;
    private String publish;
    private String ISBN;
    private String introduction;
    private double price;
    private String pubdate;
    private int class_id;
    private String classname;
    private String pressmark;
    private int state;
    private String image;
}
