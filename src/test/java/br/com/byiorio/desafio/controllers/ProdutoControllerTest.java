package br.com.byiorio.desafio.controllers;

import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.byiorio.desafio.utils.ConfiguraMassa;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.config.location=classpath:application-test.properties")
@AutoConfigureMockMvc
class ProdutoControllerTest {
        @Autowired
        private MockMvc mvc;

        @BeforeEach
        void setup() throws Throwable {
                ConfiguraMassa.configuraMassaUsuario();
                ConfiguraMassa.configuraMassaProduto();
                ConfiguraMassa.configuraMassaMeioPagamento();
                ConfiguraMassa.configuraMassaAvaliacao();
        }

        @AfterAll
        static void finalizar() throws Throwable {
                ConfiguraMassa.apagaTodoBancoDeDados();
        }

        @Test
        void postTest() throws Exception {

                // le arquivos de request e response
                // e executa o post
                String request = FileUtils.readFileToString(
                                ResourceUtils.getFile("classpath:./produto/PostRequestSucesso.json"),
                                StandardCharsets.UTF_8.name());

                String response = FileUtils.readFileToString(
                                ResourceUtils.getFile("classpath:./produto/PostResponseSucesso.json"),
                                StandardCharsets.UTF_8.name());

                // Cria um produto
                // e verifica se o id foi gerado
                MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/produtos/")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                                .andExpect(MockMvcResultMatchers.content().json(response)).andReturn();

                // Pega o id do produto
                String responseBody = result.getResponse().getContentAsString();
                ObjectMapper mapper = new ObjectMapper();
                JsonNode jsonNode = mapper.readTree(responseBody);
                String idGerado = jsonNode.get("id").asText();

                // Verifica se o usuario tem o produto como referencia
                mvc.perform(MockMvcRequestBuilders.get("/usuarios/99d44695-2b71-451a-97ee-1398a0b439a5")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.idsProdutos").value(
                                                org.hamcrest.Matchers.hasItem(idGerado)));

        }

        @Test
        void postErrorNaoEncontradoest() throws Exception {
                // le arquivos de request e response
                // e executa o post
                String request = FileUtils.readFileToString(
                                ResourceUtils.getFile("classpath:./produto/PostRequestError1.json"),
                                StandardCharsets.UTF_8.name());

                String response = FileUtils.readFileToString(
                                ResourceUtils.getFile("classpath:./produto/PostResponseError1.json"),
                                StandardCharsets.UTF_8.name());

                // verifica se o usuario existe
                mvc.perform(MockMvcRequestBuilders.post("/produtos/")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                                .andExpect(MockMvcResultMatchers.content().json(response));
        }

        @Test
        void getAllTest() throws Exception {
                // le arquivo de response e executa o get
                String response = FileUtils.readFileToString(
                                ResourceUtils.getFile("classpath:./produto/GetResponseAll.json"),
                                StandardCharsets.UTF_8.name());

                mvc.perform(MockMvcRequestBuilders.get("/produtos/")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                                .andExpect(MockMvcResultMatchers.content().json(response));
        }

        @Test
        void getTest() throws Exception {
                // le arquivo de request e response
                // e executa o post
                String response = FileUtils.readFileToString(
                                ResourceUtils.getFile("classpath:./produto/PostResponseFixoSucesso.json"),
                                StandardCharsets.UTF_8.name());

                // Consulta produto
                mvc.perform(MockMvcRequestBuilders.get("/produtos/9bce8ac2-1ddf-48ee-8bd4-2b9e8e13fa95")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                                .andExpect(MockMvcResultMatchers.content().json(response));
        }

        @Test
        void putTest() throws Exception {
                // le arquivo de request e response
                // e executa o post
                String request = FileUtils.readFileToString(
                                ResourceUtils.getFile("classpath:./produto/PutRequestSucesso.json"),
                                StandardCharsets.UTF_8.name());

                String response = FileUtils.readFileToString(
                                ResourceUtils.getFile("classpath:./produto/PutResponseSucesso.json"),
                                StandardCharsets.UTF_8.name());

                // Atualiza produto
                mvc.perform(MockMvcRequestBuilders.put("/produtos/9bce8ac2-1ddf-48ee-8bd4-2b9e8e13fa95")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                                .andExpect(MockMvcResultMatchers.content().json(response));
        }

        @Test
        void putErrorTest() throws Exception {
                // le arquivo de request e response
                // e executa o post
                String request = FileUtils.readFileToString(
                                ResourceUtils.getFile("classpath:./produto/PutRequestSucesso.json"),
                                StandardCharsets.UTF_8.name());

                // Consulta o usuario criado
                mvc.perform(MockMvcRequestBuilders.put("/produtos/99d44695-2b71-451a-97ee-1398a0ssa5")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].msg",
                                                Matchers.containsString("nao encontrado na base produtos")));
        }

        @Test
        void putErrorAlteracaoTest() throws Exception {
                // le arquivo de request e response
                // e executa o post
                String request = FileUtils.readFileToString(
                                ResourceUtils.getFile("classpath:./produto/PutRequestError2.json"),
                                StandardCharsets.UTF_8.name());

                String response = FileUtils.readFileToString(
                                ResourceUtils.getFile("classpath:./produto/PutResponseError2.json"),
                                StandardCharsets.UTF_8.name());

                // Atualiza produto
                mvc.perform(MockMvcRequestBuilders.put("/produtos/9bce8ac2-1ddf-48ee-8bd4-2b9e8e13fa95")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                                .andExpect(MockMvcResultMatchers.content().json(response));
        }

        @Test
        void deleteTest() throws Exception {

                // Verifica se existe a avaliacao que o usuario fez
                mvc.perform(MockMvcRequestBuilders.get("/avaliacoes/1eb2bc27-7ae6-472f-9422-cd53fbce22f9")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

                // Verifica se o usuario tem o produto como referencia
                mvc.perform(MockMvcRequestBuilders.get("/usuarios/99d44695-2b71-451a-97ee-1398a0b439a5")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.idsProdutos").value(
                                                org.hamcrest.Matchers.hasItem("9bce8ac2-1ddf-48ee-8bd4-2b9e8e13fa95")))
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

                // Apaga produto
                mvc.perform(MockMvcRequestBuilders.delete("/produtos/9bce8ac2-1ddf-48ee-8bd4-2b9e8e13fa95")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

                // Verifica se existe a avaliacao que o usuario fez
                mvc.perform(MockMvcRequestBuilders.get("/avaliacoes/1eb2bc27-7ae6-472f-9422-cd53fbce22f9")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

                // Verifica se removeu o relacionamento
                mvc.perform(MockMvcRequestBuilders.get("/usuarios/99d44695-2b71-451a-97ee-1398a0b439a5")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.idsProdutos")
                                                .value(org.hamcrest.Matchers.not(org.hamcrest.Matchers
                                                                .hasItem("9bce8ac2-1ddf-48ee-8bd4-2b9e8e13fa95"))))
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        }
}
