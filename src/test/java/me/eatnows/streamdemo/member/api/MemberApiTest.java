package me.eatnows.streamdemo.member.api;

import me.eatnows.streamdemo.member.app.ConfirmMemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberApi.class)
class MemberApiTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private ConfirmMemberService confirmMemberService;
//    private ConfirmMemberService confirmMemberService
//            = mock(ConfirmMemberService.class);



    @Test
    void shouldCallService() throws Exception {
        // when
        mvc.perform(post("/members/{id}/confirm", "id"))
                .andExpect(status().isOk());

        // then
        then(confirmMemberService)
                .should()
                .confirm("id");
    }
}