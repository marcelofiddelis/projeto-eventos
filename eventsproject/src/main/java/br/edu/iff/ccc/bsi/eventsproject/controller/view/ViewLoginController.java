package br.edu.iff.ccc.bsi.eventsproject.controller.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.edu.iff.ccc.bsi.eventsproject.entities.users.CommonUser;
import br.edu.iff.ccc.bsi.eventsproject.services.usersServices.commonServices.CommonService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping(path = "/login")
public class ViewLoginController {

    @Autowired
    private CommonService commonService; // Injetar o serviço de usuários
    
    @GetMapping
    public String getLoginPage(Model model) {
        model.addAttribute("message", "Login");
        return "login/login"; 
    }

    
    @PostMapping
    public String handleLogin(
        @RequestParam("cpf") String cpf,
        @RequestParam("password") String password,
        Model model,
        HttpSession session) {
        
        try {
            // Tenta autenticar o usuário
            CommonUser user = commonService.authenticate(cpf, password);

            // Se bem-sucedido, armazena o usuário na sessão
            session.setAttribute("loggedUser", user);

            return "redirect:/homeapp"; // Redireciona para a home
        } catch (Exception e) {
            model.addAttribute("error", "CPF ou senha inválidos.");
            return "login/login"; // Retorna ao formulário de login
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Invalida a sessão
        return "redirect:/home"; // Redireciona para a página pública
    }

}