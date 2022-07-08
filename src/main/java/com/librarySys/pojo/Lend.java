package com.librarySys.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Lend {
    private int id;
    private int book_id;
    private String bookName;
    private String user_id;
    private String username;
    private String lend_date;
    private String back_date;
    private int state;
}
