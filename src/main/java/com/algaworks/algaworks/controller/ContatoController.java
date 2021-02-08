package com.algaworks.algaworks.controller;

import com.algaworks.algaworks.Contato;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@Controller
public class ContatoController {

    //Este array funciona como um banco de datos que guarda as informações dos contatos
    private static final ArrayList<Contato>  LISTA_CONTATOS = new ArrayList<>();

    //Método estático para adicionar alguns contatos
    static{
        LISTA_CONTATOS.add(new Contato("1", "Maria", "+55 11 9999 8888"));
        LISTA_CONTATOS.add(new Contato("2", "Pedro", "+55 34 1111 2222"));
        LISTA_CONTATOS.add(new Contato("3", "João", "+55  35 3333 0000"));
        LISTA_CONTATOS.add(new Contato("4", "Nicolas", "+55 34 7777 8888"));
        LISTA_CONTATOS.add(new Contato("5", "Whitney", "+55 34 6666 9923"));
    }

    //Criado método para invocar que no browser aparezca a página index
    //@ @GetMapping Configura que o framework saiba qual enderereço digitado no browser
    // vai disparar este método e renderizar a p[agina no browser
    //Isto é também o mapeamento da requisicão raíz
    @GetMapping("/")
    public String index(){
        return "index";
    }

    //new ModelAndView("listar"); listar é a p[agina html listar
    @GetMapping("/contatos")
    public ModelAndView listar(){
        ModelAndView mv = new ModelAndView("listar");
        mv.addObject("contatos", LISTA_CONTATOS);
        return mv;
    }

    @GetMapping("/contatos/novo")
    public ModelAndView novo()
    {
        ModelAndView mv = new ModelAndView("formulario");
        //Pessoa que usar o formulario preenchera o mesmo
        mv.addObject("contato", new Contato());
        return mv;
    }

}
