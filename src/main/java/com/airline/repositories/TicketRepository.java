package com.airline.repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.airline.entities.Ticket;


@Repository
public interface TicketRepository extends JpaRepository<Ticket,Integer> {

}
