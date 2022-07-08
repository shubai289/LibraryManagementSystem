package com.librarySys.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String Uuid;
    private String Uname;
    private String Ugender;
    private String Uinfo;
    private String password;
}
