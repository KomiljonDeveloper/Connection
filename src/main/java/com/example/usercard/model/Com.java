package com.example.usercard.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum Com {
    gmail("gmail"),
    mail("mail"),
    yahoo("yahoo");

    private String title;

}
