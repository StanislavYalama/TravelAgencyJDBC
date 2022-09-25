//package com.coursework.oneWay.models;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.springframework.format.annotation.DateTimeFormat;
//
//import javax.persistence.*;
//import java.util.Date;
//
//@Entity
//@Table(name = "previous_tour")
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class PreviousTour {
//    @Id
//    @Column(name = "id")
//    private int id;
//    @Column(name = "tour_id")
//    private int tourId;
//    @Column(name = "date_start")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date dateStart;
//    @Column(name = "date_end")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date dateEnd;
//    @Column(name = "price", columnDefinition = "numeric(7,2)")
//    private double price;
//    @Column(name = "date_of_changing")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date dateOfChanging;
//}
