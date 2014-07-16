package com.buildria.eai.component.xlsbeans;

import java.io.File;
import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class XLSBeansDataFormatConfigTrimSpringTest {

    @EndpointInject(uri = "mock:unmarshal")
    private MockEndpoint mock;

    @Produce(uri = "direct:unmarshal")
    private ProducerTemplate template;

    @Test
    public void testUnmarshal() throws Exception {
        mock.expectedMessageCount(1);
        mock.message(0).body().isInstanceOf(UserList.class);

        String fileName = getClass().getResource("userlist2.xlsx").getFile();
        template.sendBody(new File(fileName));
        mock.assertIsSatisfied();

        UserList userList = mock.getReceivedExchanges().get(0).getIn().getBody(UserList.class);
        assertThat(userList.title, is("ユーザリスト"));
        assertThat(userList.users, hasSize(5));

        User u1 = userList.getUser(1);
        assertThat(u1, notNullValue());
        assertThat(u1.name, is("コムぱぱ"));
        assertThat(u1.gender, is("男"));
        assertThat(u1.age, is(40));

        User u5 = userList.getUser(5);
        assertThat(u5, notNullValue());
        assertThat(u5.name, is("コム子"));
        assertThat(u5.gender, is("女"));
        assertThat(u5.age, is(8));

    }

}
