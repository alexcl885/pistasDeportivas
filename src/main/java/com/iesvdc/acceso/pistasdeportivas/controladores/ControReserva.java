package com.iesvdc.acceso.pistasdeportivas.controladores;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.iesvdc.acceso.pistasdeportivas.modelos.Instalacion;
import com.iesvdc.acceso.pistasdeportivas.modelos.Reserva;
import com.iesvdc.acceso.pistasdeportivas.repos.RepoHorario;
import com.iesvdc.acceso.pistasdeportivas.repos.RepoInstalacion;
import com.iesvdc.acceso.pistasdeportivas.repos.RepoReserva;
import com.iesvdc.acceso.pistasdeportivas.repos.RepoUsuario;

/**
 * Este es el controller de los admin por el cual
 * pueden crear, borrar y editar una reserva
 */
@Controller
@RequestMapping("/reservas")
public class ControReserva {

    @Autowired
    RepoReserva repoReserva;

    @Autowired
    RepoUsuario repoUsuario;

    @Autowired
    RepoHorario repoHorario;

    @Autowired
    RepoInstalacion repoInstalacion;

    @GetMapping("")
    public String getReserva(Model model) {
        List<Reserva> reservas = repoReserva.findAll();
        model.addAttribute("reservas", reservas);
        return "/reservas/reservas";
    }

    @GetMapping("/add")
    public String addReserva(Model modelo) {
        modelo.addAttribute("reserva", new Reserva());
        modelo.addAttribute("operacion", "ADD");
        modelo.addAttribute("instalaciones", repoInstalacion.findAll());
        return "/reservas/add";
    }

    @PostMapping("/add")
    public String addReserva(
            @ModelAttribute("reserva") Reserva reserva) {
        repoReserva.save(reserva);
        return "redirect:/reserva";
    }

    @GetMapping("/edit/{id}")
    public String editReserva(
            @PathVariable @NonNull Long id,
            Model modelo) {

        Optional<Reserva> oReserva = repoReserva.findById(id);
        if (oReserva.isPresent()) {
            modelo.addAttribute("reserva", oReserva.get());
            modelo.addAttribute("usuarios", repoUsuario.findAll());
            modelo.addAttribute("horarios", repoHorario.findAll());
            modelo.addAttribute("instalaciones", repoInstalacion.findAll());
            modelo.addAttribute("operacion", "EDIT");
            return "/reservas/add";
        } else {
            modelo.addAttribute("mensaje", "La reserva no exsiste");
            modelo.addAttribute("titulo", "Error editando reserva.");
            return "/error";
        }
    }

    @PostMapping("/edit/{id}")
    public String editReserva(
            @ModelAttribute("reserva") Reserva reserva) {
        repoReserva.save(reserva);
        return "redirect:/reservas";
    }

    @GetMapping("/del/{id}")
    public String delReserva(
            @PathVariable @NonNull Long id,
            Model modelo) {

        Optional<Reserva> oReserva = repoReserva.findById(id);

        if (oReserva.isPresent()) {
            modelo.addAttribute("borrando", "verdadero");
            modelo.addAttribute("reserva", oReserva.get());
            modelo.addAttribute("usuarios", repoUsuario.findAll());
            modelo.addAttribute("horarios", repoHorario.findAll());
            modelo.addAttribute("instalaciones", repoInstalacion.findAll());
            modelo.addAttribute("operacion", "DEL");
            return "/reservas/add";
        } else {
            modelo.addAttribute("mensaje", "La reserva no exsiste");
            modelo.addAttribute("titulo", "Error borrando reserva.");
            return "/error";
        }
    }

    @PostMapping("/del/{id}")
    public String delReserva(
            @ModelAttribute("reserva") Reserva reserva) {
        repoReserva.delete(reserva);
        return "redirect:/reservas";
    }

}
