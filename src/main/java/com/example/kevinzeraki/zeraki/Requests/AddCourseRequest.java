package com.example.kevinzeraki.zeraki.Requests;

import com.example.kevinzeraki.zeraki.models.Institution;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class AddCourseRequest {

    private String name;
    private Institution institution;
}
