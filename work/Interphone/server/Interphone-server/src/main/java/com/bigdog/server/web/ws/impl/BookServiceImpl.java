package com.bigdog.server.web.ws.impl;

import com.bigdog.server.web.ws.IBookService;
import org.springframework.stereotype.Component;

import javax.jws.WebService;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 8/13/13
 * Time: 9:02 AM
 */
@Component
@WebService(serviceName="BookService",
        endpointInterface = "com.bigdog.server.web.ws.IBookService")
public class BookServiceImpl implements IBookService {
    @Override
    public String getBook() {
        String b = "hello---";
        return b;
    }
}
