package org.burgas.identityserver.controller;

import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static java.lang.System.out;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class AuthorityControllerTest {

    MockMvc mockMvc;

    @Autowired
    public AuthorityControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    @Order(value = 1)
    @WithMockUser(username = "admin", password = "admin", roles = {"ADMIN"})
    void getAllAuthorities() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/authorities/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(result -> result.getResponse().setCharacterEncoding("UTF-8"))
                .andDo(
                        result -> out.println(
                                new String(result.getResponse().getContentAsByteArray())
                        )
                )
                .andReturn();
    }

    @Test
    @Order(value = 2)
    @WithMockUser(username = "admin", password = "admin", roles = {"ADMIN"})
    void getAuthorityById() throws Exception {
        mockMvc
                .perform(
                        MockMvcRequestBuilders.get("/authorities/by-id")
                                .param("authorityId", String.valueOf(1))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(result -> result.getResponse().setCharacterEncoding("UTF-8"))
                .andDo(
                        result -> out.println(
                                new String(result.getResponse().getContentAsByteArray())
                        )
                )
                .andReturn();
    }


    @Test
    @Order(value = 3)
    @WithMockUser(username = "admin", password = "admin", roles = {"ADMIN"})
    void getAuthorityByName() throws Exception {
        mockMvc
                .perform(
                        MockMvcRequestBuilders.get("/authorities/by-name")
                                .param("name", "ROLE_ADMIN")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(result -> result.getResponse().setCharacterEncoding("UTF-8"))
                .andDo(
                        result -> out.println(
                                new String(result.getResponse().getContentAsByteArray())
                        )
                )
                .andReturn();
    }

    @Test
    @Order(value = 4)
    @WithMockUser(username = "admin", password = "admin", roles = {"ADMIN"})
    void updateAuthority() throws Exception {
        @Language("JSON") String content = """
                {
                   "id": 4,
                   "name": "ROLE_MODERATOR"
                }""";
        mockMvc
                .perform(
                        MockMvcRequestBuilders.post("/authorities/update")
                                .content(content)
                                .contentType(APPLICATION_JSON)
                )
                .andExpect(result -> result.getResponse().setCharacterEncoding("UTF-8"))
                .andDo(
                        result -> out.println(
                                new String(result.getResponse().getContentAsByteArray())
                        )
                )
                .andReturn();
    }

    @Test
    @Order(value = 5)
    @WithMockUser(username = "admin", password = "admin", roles = {"ADMIN"})
    void deleteAuthority() throws Exception {
        mockMvc
                .perform(
                        MockMvcRequestBuilders.delete("/authorities/delete")
                                .param("authorityId", String.valueOf(3))
                )
                .andExpect(result -> result.getResponse().setCharacterEncoding("UTF-8"))
                .andDo(
                        result -> out.println(
                                new String(result.getResponse().getContentAsByteArray())
                        )
                )
                .andReturn();
    }
}