/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ati.cassandra.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author jocpi
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person implements Serializable {
    
    private String id;
    private String firstName;
    private String lastName;
    
}
