package com.algaworks.algaworks.controller;

import com.algaworks.algaworks.Contato;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.UUID;

@Controller
public class ContatoController {

    //Este array funciona como um banco de datos que guarda as informações dos contatos
    private static final ArrayList<Contato> LISTA_CONTATOS = new ArrayList<>();

    //Método estático para adicionar alguns contatos
    static {
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
    public String index() {
        return "index";
    }

    //new ModelAndView("listar"); listar é a p[agina html listar
    @GetMapping("/contatos")
    public ModelAndView listar() {
        ModelAndView mv = new ModelAndView("listar");
        mv.addObject("contatos", LISTA_CONTATOS);
        return mv;
    }

    @GetMapping("/contatos/novo")
    public ModelAndView novo() {
        ModelAndView mv = new ModelAndView("formulario");
        //Pessoa que usar o formulario preenchera o mesmo
        mv.addObject("contato", new Contato());
        return mv;
    }

    @PostMapping("/contatos")
    public String cadastrar(Contato contato) {
        //UUID gera identificadores únicos
        //randomUUID: é um método
        String id = UUID.randomUUID().toString();
        contato.setId(id);
        LISTA_CONTATOS.add(contato);
        return "redirect:/contatos";
    }

    @GetMapping("/contatos/{id}/editar")
    public ModelAndView editar(@PathVariable String id) {
        ModelAndView mv = new ModelAndView("formulario");
        Contato contato = procurarContato(id);
        // o contato devolve todas as informacões preenchidas pelo usuário pra poder editar
        mv.addObject("contato", contato);
        return mv;
    }

    //Método para atualizar registro cadastrado

    @PostMapping("/contatos/{id}")
    public String atualizar(Contato contato) {
        Integer indice = procurarIndiceContato(contato.getId());
        Contato contatoVelho = LISTA_CONTATOS.get(indice);

        // Ao remover e adicionar, o ID do usuário é o mesmo
        // Apesar que ao ser aualizado, o registro aparece no final da lista
        LISTA_CONTATOS.remove(contatoVelho);
        LISTA_CONTATOS.add(indice, contato);
        return "redirect:/contatos";
    }

    @DeleteMapping("/contatos/{id}")
    public String remover(@PathVariable String id){
     Contato contato = procurarContato(id);
     LISTA_CONTATOS.remove(contato);
     return "redirect:/contatos";
    }

    //----------- Métodos Auxiliares são métodos que não responden diretamente
    //----------- as requisições que estão sendo feitas pelo verbos HTTP
    private Contato procurarContato(String id) {
        Integer indice = procurarIndiceContato(id);
        if (indice != null) {
            Contato contato = LISTA_CONTATOS.get(indice);
            return contato;
        }
        return null;
    }

    //Método para evitar que uma vez removido e adicionado o cadastro atualizado
    //éste aparezca na última linha da lista
        private Integer procurarIndiceContato(String id){
            //Como o LISTA_CONTATOS É UMA ARRAY SE USA A PROPIEDADE .SIZE
            for(int i = 0; i < LISTA_CONTATOS.size(); i++){
                Contato contato = LISTA_CONTATOS.get(i);
                if(contato.getId().equals(id)){
                    return i;
                }
            }
            return null;
    }



}
