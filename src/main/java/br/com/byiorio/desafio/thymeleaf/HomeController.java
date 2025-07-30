package br.com.byiorio.desafio.thymeleaf;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String home(@RequestParam(required = false) String categoria, Model model) {

        List<ProdutoDetalhadoResponse> listaProduto = null;
        if (categoria == null){
            listaProduto = produtoDetalhadoService.buscarTodos(null);
        }else{
            listaProduto = produtoDetalhadoService.buscarTodos(categoria);
        }

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
