package com.buildria.eai.component.xlsbeans;

import java.io.File;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;
import org.junit.Test;

public class XLSBeansDataFormatTest extends CamelTestSupport {

    @Test
    public void testUnmarshal() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:unmarshal");
        mock.expectedMessageCount(1);
        mock.message(0).body().isInstanceOf(UserList.class);

        String fileName = getClass().getResource("userlist1.xlsx").getFile();
        template.sendBody("direct:unmarshal", new File(fileName));
        mock.assertIsSatisfied();

        UserList userList = mock.getReceivedExchanges().get(0).getIn().getBody(UserList.class);
        assertThat(userList.title, is("ユーザリスト"));
        assertThat(userList.users, hasSize(5));
        
        User u1 = userList.getUser(1);
        assertThat(u1, notNullValue());
        assertThat(u1.name, is(" コムぱぱ "));
        assertThat(u1.gender, is("男"));
        assertThat(u1.age, is(40));
     
        User u5 = userList.getUser(5);
        assertThat(u5, notNullValue());
        assertThat(u5.name, is("コム子"));
        assertThat(u5.gender, is("女"));
        assertThat(u5.age, is(8));
        
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {

            @Override
            public void configure() throws Exception {
                XLSBeansDataFormat format = new XLSBeansDataFormat();
                format.setObjectType(UserList.class);
                format.setBookType(XLSBeansDataFormat.TYPE_XSSF);
                from("direct:unmarshal").unmarshal(format).to("mock:unmarshal");
            }
            
        };
    }

}
