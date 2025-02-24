package com.iesvdc.acceso.pistasdeportivas.repos;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iesvdc.acceso.pistasdeportivas.modelos.Reserva;
import com.iesvdc.acceso.pistasdeportivas.modelos.Usuario;

import java.util.List;



@Repository
public interface RepoReserva extends JpaRepository<Reserva,Long>{
    List<Reserva> findByUsuario(Usuario usuario);
}
