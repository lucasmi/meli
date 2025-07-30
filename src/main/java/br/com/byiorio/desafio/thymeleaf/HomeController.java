package br.com.byiorio.desafio.thymeleaf;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import br.com.byiorio.desafio.models.ProdutoDetalhadoResponse;
import br.com.byiorio.desafio.services.ProdutoDetalhadoService;

@Controller
public class HomeController {

    ProdutoDetalhadoService produtoDetalhadoService;

    public HomeController(ProdutoDetalhadoService produtoDetalhadoService) {
        this.produtoDetalhadoService = produtoDetalhadoService;
    }

    @GetMapping("/home")
    public String home(Model model) {
        List<ProdutoDetalhadoResponse> listaProduto = produtoDetalhadoService.buscarTodos(null);
        model.addAttribute("listaProdutos", listaProduto);
        return "home";
    }

    @GetMapping("/produto")
    public String produto(Model model) {
        List<ProdutoDetalhadoResponse> listaProduto = produtoDetalhadoService.buscarTodos(null);
        model.addAttribute("listaProdutos", listaProduto);
        return "produto";
    }
}
