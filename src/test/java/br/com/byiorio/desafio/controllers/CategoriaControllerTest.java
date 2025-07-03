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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.ResourceUtils;

import br.com.byiorio.desafio.utils.ConfiguraMassa;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.config.location=classpath:application-test.properties")
@AutoConfigureMockMvc
class CategoriaControllerTest {
        @Autowired
        private MockMvc mvc;

        @BeforeEach
        void setup() throws Throwable {
                ConfiguraMassa.criaTodoBancoDeDados();
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
                                ResourceUtils.getFile("classpath:./categoria/PostRequestSucesso.json"),
                                StandardCharsets.UTF_8.name());

                String response = FileUtils.readFileToString(
                                ResourceUtils.getFile("classpath:./categoria/PostResponseSucesso.json"),
                                StandardCharsets.UTF_8.name());

                mvc.perform(MockMvcRequestBuilders.post("/categorias/")
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
                                ResourceUtils.getFile("classpath:./categoria/GetResponseAll.json"),
                                StandardCharsets.UTF_8.name());

                mvc.perform(MockMvcRequestBuilders.get("/categorias/")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                                .andExpect(MockMvcResultMatchers.content().json(response));
        }

        @Test
        void getTest() throws Exception {

                // le arquivo de request e response
                // e executa o get
                String response = FileUtils.readFileToString(
                                ResourceUtils.getFile("classpath:./categoria/PostResponseFixoSucesso.json"),
                                StandardCharsets.UTF_8.name());

                // Consulta categoria criada
                mvc.perform(MockMvcRequestBuilders.get("/categorias/2d4d2ab5-3a84-4249-a369-e68bcd43b30d")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                                .andExpect(MockMvcResultMatchers.content().json(response));
        }

        @Test
        void putTest() throws Exception {
                // le arquivo de request e response
                // e executa o post
                String request = FileUtils.readFileToString(
                                ResourceUtils.getFile("classpath:./categoria/PutRequestSucesso.json"),
                                StandardCharsets.UTF_8.name());

                String response = FileUtils.readFileToString(
                                ResourceUtils.getFile("classpath:./categoria/PutResponseSucesso.json"),
                                StandardCharsets.UTF_8.name());

                // Atualiza caregoria criada
                mvc.perform(MockMvcRequestBuilders.put("/categorias/2d4d2ab5-3a84-4249-a369-e68bcd43b30d")
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
                                ResourceUtils.getFile("classpath:./categoria/PutRequestError1.json"),
                                StandardCharsets.UTF_8.name());

                // Atualiza categori criada
                mvc.perform(MockMvcRequestBuilders.put("/categorias/99d44695-2b71-451a-97ee-1398a0ssa5")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].msg",
                                                Matchers.containsString("nao encontrado na base categorias")));
        }

        @Test
        void deleteTest() throws Exception {

                // Apaga Categoria
                mvc.perform(MockMvcRequestBuilders.delete("/categorias/1ff7459a-53c6-404f-aa92-89a2f0cea8ff")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        }

        @Test
        void deleteIdErrorTest() throws Exception {
                // le arquivo de request e response
                // e executa o post
                String response = FileUtils.readFileToString(
                                ResourceUtils.getFile("classpath:./categoria/DeleteResponseError1.json"),
                                StandardCharsets.UTF_8.name());

                // Apaga Categoria
                mvc.perform(MockMvcRequestBuilders.delete("/categorias/1ff7459a-53c6")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                                .andExpect(MockMvcResultMatchers.content().json(response));

        }

        @Test
        void deleteErrorTest() throws Exception {
                // Apaga Categoria
                mvc.perform(MockMvcRequestBuilders.delete("/categorias/1z17459a-53c6-404f-aa92-89a2f0cea8xx")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        }
}
