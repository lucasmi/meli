package br.com.byiorio.desafio.thymeleaf;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import br.com.byiorio.desafio.models.ProdutoDetalhadoResponse;
import br.com.byiorio.desafio.services.CategoriaService;
import br.com.byiorio.desafio.services.ProdutoDetalhadoService;

@Controller
public class HomeController {

    ProdutoDetalhadoService produtoDetalhadoService;
    CategoriaService categoriaService;

    public HomeController(ProdutoDetalhadoService produtoDetalhadoService, CategoriaService categoriaService) {
        this.produtoDetalhadoService = produtoDetalhadoService;
        this.categoriaService = categoriaService;
    }

    @GetMapping("/home")
    public String home(Model model) {
        List<ProdutoDetalhadoResponse> listaProduto = produtoDetalhadoService.buscarTodos(null);
        model.addAttribute("listaProdutos", listaProduto);

        model.addAttribute("categorias", categoriaService.buscarTodos());
        return "home";
    }

    @GetMapping("/produto")
    public String produto(Model model) {
        List<ProdutoDetalhadoResponse> listaProduto = produtoDetalhadoService.buscarTodos(null);
        model.addAttribute("listaProdutos", listaProduto);
        return "produto";
    }
}
