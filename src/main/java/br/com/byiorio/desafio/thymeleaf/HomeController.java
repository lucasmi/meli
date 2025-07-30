package br.com.byiorio.desafio.thymeleaf;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.byiorio.desafio.models.ProdutoDetalhadoResponse;
import br.com.byiorio.desafio.services.CategoriaService;
import br.com.byiorio.desafio.services.ProdutoDetalhadoService;
import br.com.byiorio.desafio.thymeleaf.dto.HomeRequest;

@Controller
public class HomeController {

    private static final String CATEGORIA2 = "categoria";
    private static final String CATEGORIAS = "categorias";
    private static final String BUSCA = "busca";
    private static final String LISTA_PRODUTOS = "listaProdutos";

    ProdutoDetalhadoService produtoDetalhadoService;
    CategoriaService categoriaService;

    public HomeController(ProdutoDetalhadoService produtoDetalhadoService, CategoriaService categoriaService) {
        this.produtoDetalhadoService = produtoDetalhadoService;
        this.categoriaService = categoriaService;
    }

    @GetMapping("/home")
    public String home(@RequestParam(required = false) String categoria,
            @ModelAttribute(BUSCA) String busca,
            @ModelAttribute(CATEGORIA2) String categoriaParam,
            Model model) {

        @SuppressWarnings("unchecked")
        // Se chegar produtos filtrados do redirect do get, pega essa variavel
        List<ProdutoDetalhadoResponse> listaProduto = (List<ProdutoDetalhadoResponse>) model
                .getAttribute(LISTA_PRODUTOS);

        // Listando produtos
        if (listaProduto == null) {
            if (categoria == null) {
                listaProduto = produtoDetalhadoService.buscarTodos(null);
            } else {
                listaProduto = produtoDetalhadoService.buscarTodos(categoria);
            }
            model.addAttribute(LISTA_PRODUTOS, listaProduto);
        } else {
            model.addAttribute(LISTA_PRODUTOS, listaProduto);
        }

        // Carrega todos os parametros
        model.addAttribute(BUSCA, busca);
        model.addAttribute(CATEGORIAS, categoriaService.buscarTodos());
        model.addAttribute(CATEGORIA2, (categoriaParam != null) ? categoriaParam : categoria);

        return "home";
    }

    @PostMapping("/home")
    public String homePost(@ModelAttribute HomeRequest request, RedirectAttributes redirectAttributes) {
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

        // Salva os paramestros para o redirect no get
        redirectAttributes.addFlashAttribute(BUSCA, request.getBusca());
        redirectAttributes.addFlashAttribute(LISTA_PRODUTOS, listaProduto);
        redirectAttributes.addFlashAttribute(CATEGORIAS, categoriaService.buscarTodos());
        redirectAttributes.addAttribute(CATEGORIA2, request.getCategoria());

        // Faz o redirect para o get
        return "redirect:/home";
    }

    @GetMapping("/produto")
    public String produto(@RequestParam(required = true) String id, Model model) {
        ProdutoDetalhadoResponse produto = produtoDetalhadoService.buscar(id);
        model.addAttribute("produto", produto);
        model.addAttribute(CATEGORIAS, categoriaService.buscarTodos());
        return "produto";
    }
}
