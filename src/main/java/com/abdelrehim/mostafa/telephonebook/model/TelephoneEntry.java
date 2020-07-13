package com.abdelrehim.mostafa.telephonebook.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
@Data
public class TelephoneEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    String name;

    @Column(unique = true)
    @Pattern(regexp = "^([\\+][0-9]{1,3}([ \\.\\-])?)?([\\(]{1}[0-9]{3}[\\)])"
                    + "?([0-9A-Z \\.\\-]{1,32})((x|ext|extension)?[0-9]{1,4}?)$")
    @NotBlank(message = "phone number is mandatory")
    String phoneNumber;
}
