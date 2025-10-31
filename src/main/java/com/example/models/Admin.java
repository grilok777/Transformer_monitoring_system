package com.example.models;

import com.example.entity.User;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import lombok.Data;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
public class Admin extends User {

}
