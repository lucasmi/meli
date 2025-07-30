package br.com.byiorio.desafio.thymeleaf;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.byiorio.desafio.models.ProdutoDetalhadoResponse;
import br.com.byiorio.desafio.services.CategoriaService;
import br.com.byiorio.desafio.services.ProdutoDetalhadoService;
import br.com.byiorio.desafio.thymeleaf.dto.HomeRequest;

@Controller
public class HomeController {

    private static final String LISTA_PRODUTOS = "listaProdutos";
    private static final String CATEGORIAS = "categorias";

    ProdutoDetalhadoService produtoDetalhadoService;
    CategoriaService categoriaService;

    public HomeController(ProdutoDetalhadoService produtoDetalhadoService, CategoriaService categoriaService) {
        this.produtoDetalhadoService = produtoDetalhadoService;
        this.categoriaService = categoriaService;
    }

    @GetMapping("/home")
    public String home(@RequestParam(required = false) String categoria, Model model) {

        List<ProdutoDetalhadoResponse> listaProduto = null;
        // Listando produtos
        if (categoria == null) {
            listaProduto = produtoDetalhadoService.buscarTodos(null);
        } else {
            listaProduto = produtoDetalhadoService.buscarTodos(categoria);
        }
        model.addAttribute(LISTA_PRODUTOS, listaProduto);

        model.addAttribute(CATEGORIAS, categoriaService.buscarTodos());
        return "home";
    }

    @PostMapping("/home")
    public String homePost(@ModelAttribute HomeRequest request, Model model) {
        List<ProdutoDetalhadoResponse> listaProduto = null;

        // Listando produtos
        if (request.getCategoria() == null) {
            listaProduto = produtoDetalhadoService.buscarTodos(null);
        } else {
            listaProduto = produtoDetalhadoService.buscarTodos(request.getCategoria());
        }

        // Filtrando busca
        if (request.getBusca() != null) {
            listaProduto = listaProduto.stream()
                    .filter(p -> p.getProduto().getTitulo().toUpperCase().contains(request.getBusca().toUpperCase()))
                    .toList();
        }
        model.addAttribute(LISTA_PRODUTOS, listaProduto);

        model.addAttribute(CATEGORIAS, categoriaService.buscarTodos());
        return "home";
    }

    @GetMapping("/produto")
    public String produto(Model model) {
        List<ProdutoDetalhadoResponse> listaProduto = produtoDetalhadoService.buscarTodos(null);
        model.addAttribute(LISTA_PRODUTOS, listaProduto);
        return "produto";
    }
}
