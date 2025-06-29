package br.com.byiorio.desafio.controllers;

import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.ResourceUtils;

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
                mvc.perform(MockMvcRequestBuilders.post("/produtos/")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                                .andExpect(MockMvcResultMatchers.content().json(response));
        }

        @Test
        void getAllTest() throws Exception {
                // le arquivo de response e executa o get
                String response = FileUtils.readFileToString(
                                ResourceUtils.getFile("classpath:./produto/GetResponseAllProdutos.json"),
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

                // Consulta o usuario criado
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

                // Consulta o usuario criado
                mvc.perform(MockMvcRequestBuilders.put("/produtos/9bce8ac2-1ddf-48ee-8bd4-2b9e8e13fa95")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                                .andExpect(MockMvcResultMatchers.content().json(response));
        }

        @Test
        void deleteTest() throws Exception {
                // Consulta o usuario criado
                mvc.perform(MockMvcRequestBuilders.delete("/produtos/9bce8ac2-1ddf-48ee-8bd4-2b9e8e13fa95")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        }
}
