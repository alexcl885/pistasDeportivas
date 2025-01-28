package com.iesvdc.acceso.pistasdeportivas.controladores;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.hibernate.type.descriptor.java.LocalDateJavaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.iesvdc.acceso.pistasdeportivas.modelos.Horario;
import com.iesvdc.acceso.pistasdeportivas.modelos.Instalacion;
import com.iesvdc.acceso.pistasdeportivas.modelos.Reserva;
import com.iesvdc.acceso.pistasdeportivas.modelos.Usuario;
import com.iesvdc.acceso.pistasdeportivas.repos.RepoHorario;
import com.iesvdc.acceso.pistasdeportivas.repos.RepoInstalacion;
import com.iesvdc.acceso.pistasdeportivas.repos.RepoReserva;
import com.iesvdc.acceso.pistasdeportivas.repos.RepoUsuario;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping("/mis-datos")
public class ControDatos {

    @Autowired
    RepoUsuario repoUsuario;
    @Autowired
    RepoReserva repoReserva;
    @Autowired
    RepoHorario repoHorario;
    @Autowired
    RepoInstalacion repoInstalacion;


    private Usuario getLoggedUser(){
        Authentication authentication = 
            SecurityContextHolder.getContext().getAuthentication();
        return repoUsuario.findByUsername(
            authentication.getName()).get(0);
    }
    
    @GetMapping("")
    public String misDatos(Model modelo) {
        modelo.addAttribute("usuario", getLoggedUser());

        return "mis-datos/mis-datos";
    }

    
    @GetMapping("/edit")
    public String getMisDatos(Model modelo) {
        modelo.addAttribute("usuario", getLoggedUser());

        return "mis-datos/mis-datos";
    }

    @PostMapping("/edit")
    public String postMisDatos(
        @ModelAttribute("usuario") Usuario u,
        Model modelo) {
        Usuario loggedUser = getLoggedUser();
        if (loggedUser.getId() == u.getId()) {

            u.setTipo(loggedUser.getTipo());
            u.setPassword(loggedUser.getPassword());
            u.setEnabled(loggedUser.isEnabled());

            repoUsuario.save(u);
            return "redirect:/mis-datos";
        } else {
            modelo.addAttribute("mensaje", "Error editando datos de usuario");
            modelo.addAttribute("titulo", "No ha sido posible editar sus datos.");
            return "/error";
        }
        
    }
    
    @GetMapping("/mis-reservas")
    public String getMisReservas(Model modelo) {
        modelo.addAttribute("reservas", repoReserva.findByUsuario(getLoggedUser()));
        modelo.addAttribute("localDate", LocalDate.now());
        return "mis-datos/mis-reservas";
    }
    
    
    @GetMapping("/ver-instalaciones")
    public String getInstalaciones(Model model) {
        List<Instalacion> instalaciones = repoInstalacion.findAll();
        model.addAttribute("instalaciones", instalaciones);
        return "mis-datos/ver-instalaciones";
    }
    

    @GetMapping("/reservar/{id}")
    public String addReserva(@PathVariable @NonNull Long id, Model modelo) {
        Optional<Instalacion> oInstalacion = repoInstalacion.findById(id);

        if (oInstalacion.isPresent()) {
            modelo.addAttribute("horarios", repoHorario.findByInstalacion(oInstalacion.get()));
            Reserva reserva = new Reserva();
            reserva.setUsuario(getLoggedUser());
            modelo.addAttribute("reserva", reserva);
            modelo.addAttribute("fechaMin", LocalDate.now().plusDays(1).toString());
            modelo.addAttribute("fechaMax", LocalDate.now().plusDays(7).toString());
            return "mis-datos/reservar";
        } else {
            modelo.addAttribute("mensaje", "La instalación no existe");
            return "/error";
        }

    }

    @PostMapping("/reservar/{id}")
    public String postMethodName(@ModelAttribute("reserva") Reserva reserva, Model modelo) {
        List<Reserva> reservas = repoReserva.findAll();
        List<Reserva> reservaUsuario = new ArrayList<Reserva>();
        
        for (Reserva reser : reservas) {
            if (reser.getUsuario().equals(getLoggedUser())){
                reservaUsuario.add(reser);
            }
        }

        for (Reserva reser2 : reservaUsuario){
            if (reser2.getFecha().equals(reserva.getFecha())){
                modelo.addAttribute("titulo", "No se puede reservar el mismo dia");
                modelo.addAttribute("mensaje", "No se puede reservar el mismo dia");
                return "/error";
            }
            
        }
        repoReserva.save(reserva);
        return "redirect:/mis-datos/mis-reservas";
    }

    @GetMapping("mis-reservas/del/{id}")
    public String delReserva(Model modelo, @PathVariable @NonNull Long id)  {
        Optional<Reserva> oReserva = repoReserva.findById(id);
        if (oReserva.isPresent()) {
            repoReserva.delete(oReserva.get());
        }else {
            modelo.addAttribute("mensaje", "La reserva no exsiste");
            modelo.addAttribute("titulo", "Error borrando reserva.");
            return "/error";
        }
        return "redirect:/mis-datos/mis-reservas";
    }
    
}
