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
class UsuarioControllerTest {
        @Autowired
        private MockMvc mvc;

        @BeforeEach
        void setup() throws Throwable {
                ConfiguraMassa.configuraMassaUsuario();
                ConfiguraMassa.configuraMassaMeioPagamento();
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
                                ResourceUtils.getFile("classpath:./usuario/PostRequestSucesso.json"),
                                StandardCharsets.UTF_8.name());

                String response = FileUtils.readFileToString(
                                ResourceUtils.getFile("classpath:./usuario/PostResponseSucesso.json"),
                                StandardCharsets.UTF_8.name());

                mvc.perform(MockMvcRequestBuilders.post("/usuarios/")
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
                                ResourceUtils.getFile("classpath:./usuario/GetResponseAll.json"),
                                StandardCharsets.UTF_8.name());

                mvc.perform(MockMvcRequestBuilders.get("/usuarios/")
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
                                ResourceUtils.getFile("classpath:./usuario/PostResponseFixoSucesso.json"),
                                StandardCharsets.UTF_8.name());

                // Consulta o usuario criado
                mvc.perform(MockMvcRequestBuilders.get("/usuarios/99d44695-2b71-451a-97ee-1398a0b439a5")
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
                                ResourceUtils.getFile("classpath:./usuario/PutRequestSucesso.json"),
                                StandardCharsets.UTF_8.name());

                String response = FileUtils.readFileToString(
                                ResourceUtils.getFile("classpath:./usuario/PutResponseSucesso.json"),
                                StandardCharsets.UTF_8.name());

                // Consulta o usuario criado
                mvc.perform(MockMvcRequestBuilders.put("/usuarios/99d44695-2b71-451a-97ee-1398a0b439a5")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                                .andExpect(MockMvcResultMatchers.content().json(response));
        }

        @Test
        void postMeioPagamentoTest() throws Exception {
                // le arquivo de request e response
                // e executa o post
                String request = FileUtils.readFileToString(
                                ResourceUtils.getFile("classpath:./usuario/MeioPagamentoRequestSucesso.json"),
                                StandardCharsets.UTF_8.name());

                String response = FileUtils.readFileToString(
                                ResourceUtils.getFile("classpath:./usuario/MeioPagamentoResponseSucesso.json"),
                                StandardCharsets.UTF_8.name());

                // Consulta o usuario criado
                mvc.perform(MockMvcRequestBuilders
                                .post("/usuarios/99d44695-2b71-451a-97ee-1398a0b439a5/meios-pagamentos")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                                .andExpect(MockMvcResultMatchers.content().json(response));
        }

        @Test
        void putMeioPagamentoTest() throws Exception {
                // le arquivo de request e response
                // e executa o post
                String request = FileUtils.readFileToString(
                                ResourceUtils.getFile("classpath:./meiopagamento/PutRequestSucesso.json"),
                                StandardCharsets.UTF_8.name());

                String response = FileUtils.readFileToString(
                                ResourceUtils.getFile("classpath:./meiopagamento/PutResponseSucesso.json"),
                                StandardCharsets.UTF_8.name());

                // Consulta o usuario criado
                mvc.perform(MockMvcRequestBuilders.put(
                                "/usuarios/f33d57ea-d316-4167-92a8-8f2258b71abd/meios-pagamentos/114cbcfb-ec12-487e-b842-59fa878154ee")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                                .andExpect(MockMvcResultMatchers.content().json(response));
        }

        @Test
        void apagarUsuarioTest() throws Exception {
                // Consulta o usuario criado
                mvc.perform(MockMvcRequestBuilders.delete("/usuarios/99d44695-2b71-451a-97ee-1398a0b439a5")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        }
}
