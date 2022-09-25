//package com.coursework.oneWay.models;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.springframework.format.annotation.DateTimeFormat;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;
//import java.util.Date;
//
//@Entity
//@Table(name = "booked_hotel_room")
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class BookedHotelRoom {
//    @Id
//    @Column(name = "id")
//    private int id;
//    @Column(name = "hotel_room")
//    private int hotelRoom;
//    @Column(name = "hotel_id")
//    private int hotelId;
//    @Column(name = "date_start")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date dateStart;
//    @Column(name = "date_end")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date dateEnd;
//    @Column(name = "places_count")
//    private int placesCount;
//}
