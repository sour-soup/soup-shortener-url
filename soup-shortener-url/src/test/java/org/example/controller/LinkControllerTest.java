package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.controller.dto.LinkDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LinkControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testAddLink() throws Exception {
        String user = "soup";
        String link = "https://stackoverflow.com/questions/55496891/how-to-add-a-request-body-to-spring-mockmvc";
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writeValueAsString(new LinkDto(link, null));
        String response = mockMvc
                .perform(post("/api/v1/addLink?login=" + user)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        LinkDto linkDto = mapper.readValue(response, LinkDto.class);
        mockMvc.perform(get(linkDto.shortLink()))
                .andExpectAll(status().is3xxRedirection(), redirectedUrl(link));
    }

    @Test
    void testGetLinks() throws Exception {
        String user = "soup";
        String response = mockMvc
                .perform(get("/api/v1/getLinks?login=" + user))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        System.out.println(response);
    }
}
